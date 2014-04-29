package fr.inria.spirals.sigma.ttc14.fixml

import java.io.File
import java.net.URLClassLoader

import fr.inria.spirals.sigma.ttc14.fixml.objlang.Class
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.unice.i3s.sigma.m2t.M2TF
import fr.unice.i3s.sigma.support.ScalaSigmaSupport
import fr.unice.i3s.sigma.util.IOUtils

object Main extends App with ScalaSigmaSupport with ObjLang {

  val FixmlPackageName = "fixml"
  val JaxbHome = System.getProperty("jaxb_home", "jaxb-ri-2.2.7")
  val JavaBin = Option(System.getProperty("java_home", System.getenv("JAVA_HOME"))) match {
    case Some(path) => s"$path/bin/"
    case None => ""
  }
  val Java = JavaBin + "java"
  val Javac = JavaBin + "javac"

  // currently implemented drivers
  val drivers = Seq(
    ("java", M2TF(
      (new ObjLang2Java, { s: Class ⇒ s"${s.name}.java" }))),
    ("cs", M2TF(
      (new ObjLang2CSharp, { s: Class ⇒ s"${s.name}.cs" }))),
    ("cpp", M2TF(
      (new ObjLang2CPP, { s: Class ⇒ s"${s.name}.cpp" }),
      (new ObjLang2HPP, { s: Class ⇒ s"${s.name}.h" }))))

  def executeCommand(cmd: String) {
    println("Executing: " + cmd)
    IOUtils.SystemExecutor.execute(cmd).get
  }

  def timeStamp[T](msg: String)(thunk: => T): T = {
    val start = System.currentTimeMillis
    val res = thunk
    val time = System.currentTimeMillis - start

    println(s"$msg in ${time}ms")

    res
  }

  def generateSchemaBinding(src: File, dest: File) {
    val files = src.listFiles filter (_.getName endsWith ".xsd")
    val cmd = s"$Java -jar $JaxbHome/lib/jaxb-xjc.jar -cp $JaxbHome/lib/jaxb-impl.jar -d $dest -p $FixmlPackageName ${files mkString (" ")}"
    executeCommand(cmd)
  }

  def compileSchemaBinding(src: File, dest: File) {
    val files = new File(src, FixmlPackageName).listFiles filter (_.getName endsWith ".java")
    val cmd = s"$Javac ${files mkString (" ")} -d ${dest.getAbsolutePath}"
    executeCommand(cmd)
  }

  def execute(src: File, dest: File) {

    //    if (dest.isDirectory) {
    //      println(s"Cleaning directory ${dest}")
    //      IOUtils.rmdir(dest, false, true)
    //    } else {
    //      println(s"Creating output directory ${dest}")
    //      IOUtils.mkdirs(dest)
    //    }
    //
    val xjcOut = new File(dest, "schema-src")
    val xjcClasses = new File(dest, "schema-classes")
    //    IOUtils.mkdirs(xjcOut)
    //    IOUtils.mkdirs(xjcClasses)
    //    
    //    timeStamp("Generated XML Schema Bindings") {
    //      generateSchemaBinding(src, xjcOut)
    //    }
    //
    //    timeStamp("Compiled XML Schema Bindings") {
    //      compileSchemaBinding(xjcOut, xjcClasses)
    //    }

    // Load and instantiate compiled class.
    val classes = timeStamp(s"Loading classes") {
      val classLoader = URLClassLoader.newInstance(Array(xjcClasses.toURI.toURL))
      val files = new File(xjcClasses, FixmlPackageName).listFiles filter (_.getName.endsWith(".class"))
      files map { f => java.lang.Class.forName(s"$FixmlPackageName.${f.getName.dropRight(6)}", true, classLoader) }
    }

    timeStamp(s"Transforming all") {
      val objlang = new Java2ObjLang().transformAll(classes)

      for ((ext, m2tf) ← drivers) {
        val output = new File(new File(dest, "results"), ext)
        IOUtils.mkdirs(output)

        val classes = objlang collect { case c: Class ⇒ c }

        timeStamp(s"Transformed ObjLang to $ext (M2T)") {
          classes foreach (m2tf.transform(_, output))
        }
      }
    }
  }

  val Usage =
    s"""|Usage: Main <src> <dest>
        |
        |where:
        |- src     FIXML 4.4 XML Schema files
	    |- dest    is the output directory where the source code should generated.""".stripMargin

  if (args.size != 2) {
    println(Usage)
    sys.exit(1)
  }

  execute(new File(args(0)), new File(args(1)))

}