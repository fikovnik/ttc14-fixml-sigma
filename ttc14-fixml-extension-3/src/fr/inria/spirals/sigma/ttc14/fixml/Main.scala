package fr.inria.spirals.sigma.ttc14.fixml

import java.io.File
import java.net.URLClassLoader

import fr.inria.spirals.sigma.ttc14.fixml.objlang.Class
import fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.unice.i3s.sigma.m2t.M2TF
import fr.unice.i3s.sigma.support.SigmaSupport
import fr.unice.i3s.sigma.util.IOUtils
import java.net.URL

object Main extends App with SigmaSupport with ObjLang {

  val FixmlPackageName = "fixml"
  val JaxbHome = System.getProperty("jaxb_home", "jaxb-ri-2.2.7")
  val JavaBin = Option(System.getProperty("java_home", System.getenv("JAVA_HOME"))) match {
    case Some(path) => s"$path/bin/"
    case None => ""
  }
  val Java = JavaBin + "java"
  val Javac = JavaBin + "javac"

  case class Driver(name: String, m2class: M2TF[Class], m2enum: M2TF[Enum], resources: Set[URL] = Set())

  // currently implemented drivers
  val drivers = Seq(
    Driver(
      "java",
      M2TF((new BaseObjLang2Class with ObjLang2Java, { s: Class ⇒ s"${s.name}.java" })),
      M2TF((new BaseObjLang2Enum with ObjLang2Java, { s: Enum ⇒ s"${s.name}.java" }))
    ),
    Driver(
      "cs",
      M2TF((new BaseObjLang2Class with ObjLang2CSharp, { s: Class ⇒ s"${s.name}.cs" })),
      M2TF((new BaseObjLang2Enum with ObjLang2CSharp, { s: Enum ⇒ s"${s.name}.cs" }))
    ),
    Driver(
      "cpp",
      M2TF(
        (new ObjLang2CPPClassHeader, { s: Class ⇒ s"${s.name}.h" }),
        (new ObjLang2CPPClassImpl, { s: Class ⇒ s"${s.name}.cpp" })
      ),
      M2TF((new ObjLang2CPPEnumHeader, { s: Enum ⇒ s"${s.name}.h" }))
    ),
    Driver(
      "c",
      M2TF(
        (new ObjLang2CClassHeader, { s: Class ⇒ s"${s.name}.h" }),
        (new ObjLang2CClassImpl, { s: Class ⇒ s"${s.name}.c" })
      ),
      M2TF((new ObjLang2CEnumHeader, { s: Enum ⇒ s"${s.name}.h" })),
      Set(
        getClass.getResource("resources/c/arrays.h"),
        getClass.getResource("resources/c/arrays.c")
      )
    )
  )

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

    if (dest.isDirectory) {
      println(s"Cleaning directory ${dest}")
      IOUtils.rmdir(dest, false, true)
    } else {
      println(s"Creating output directory ${dest}")
      IOUtils.mkdirs(dest)
    }

    val xjcOut = new File(dest, "schema-src")
    val xjcClasses = new File(dest, "schema-classes")

    IOUtils.mkdirs(xjcOut)
    IOUtils.mkdirs(xjcClasses)

    timeStamp("Generated XML Schema Bindings") {
      generateSchemaBinding(src, xjcOut)
    }

    timeStamp("Compiled XML Schema Bindings") {
      compileSchemaBinding(xjcOut, xjcClasses)
    }

    // Load and instantiate compiled class.
    val classes = timeStamp(s"Loading classes") {
      val classLoader = URLClassLoader.newInstance(Array(xjcClasses.toURI.toURL))
      val files = new File(xjcClasses, FixmlPackageName).listFiles filter (_.getName.endsWith(".class"))
      files map { f => java.lang.Class.forName(s"$FixmlPackageName.${f.getName.dropRight(6)}", true, classLoader) }
    }

    timeStamp(s"Transforming all") {
      val objlang = timeStamp(s"Trasformed Java -> ObjLang (M2M)") {
        new Java2ObjLang().transformAll(classes)
      }

      for (Driver(ext, m2class, m2enum, resources) ← drivers) {
        val output = new File(new File(dest, "results"), ext)
        IOUtils.mkdirs(output)

        val classes = objlang collect { case c: Class ⇒ c }
        val enums = objlang collect { case c: Enum ⇒ c }

        timeStamp(s"Transformed ObjLang to $ext (M2T)") {
          classes foreach (m2class.transform(_, output))
          enums foreach (m2enum.transform(_, output))
        }

        resources foreach { r =>
          val file = r.getFile
          r.openStream() >> new File(output, file.substring(file.lastIndexOf('/')))
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