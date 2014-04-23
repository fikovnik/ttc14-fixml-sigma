package fr.inria.spirals.sigma.ttc14.fixml

import java.io.File
import scala.util.Failure
import scala.util.Success
import com.typesafe.scalalogging.log4j.Logging
import fr.unice.i3s.sigma.util.IOUtils
import fr.unice.i3s.sigma.support.ScalaSigmaSupport
import fr.inria.spirals.sigma.ttc14.fixml.objlang.Class
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.unice.i3s.sigma.m2t.M2TF

object Main extends App with Logging with ScalaSigmaSupport with ObjLang {

  // currently implemented drivers
  val drivers = Seq(
    ("java", new XMLMM2Java, M2TF(
      (new ObjLang2Java, { s: Class ⇒ s"${s.name}.java" })
    )),
    ("cs", new XMLMM2CSharp, M2TF(
      (new ObjLang2CSharp, { s: Class ⇒ s"${s.name}.cs" })
    )),
    ("cpp", new XMLMM2CPP, M2TF(
      (new ObjLang2CPPWithInitializerList, { s: Class ⇒ s"${s.name}.cpp" }),
      (new ObjLang2HPP, { s: Class ⇒ s"${s.name}.h" })
    ))
  )

  def execute(src: File, dest: File) {

    if (dest.isDirectory) {
      logger info s"Cleaning directory ${dest}"
      IOUtils.rmdir(dest, false, true)
    } else {
      logger info s"Creating output directory ${dest}"
      IOUtils.mkdirs(dest)
    }

    val fixml = FIXMLParser.parseFromFile(src) orCrash s"Unable to load FIXML from ${src}"

    for ((ext, m2m, m2tf) ← drivers) {
      logger info s"Generating $ext"

      val output = new File(dest, ext)
      IOUtils.mkdirs(output)
      
      val targets = m2m.transform(fixml)
      targets collect { case c: Class ⇒ c } foreach { m2tf.transform(_, output) }
    }
  }

  val Usage =
    s"""|Usage: Main <src> <dest>
        |
        |where:
        |- src     is is FIXML 4.4 message file.
	    |- dest    is the output dir where the source code should generated.""".stripMargin

  if (args.size != 2) {
    println(Usage)
    sys.exit(1)
  }

  execute(new File(args(0)), new File(args(1)))

}