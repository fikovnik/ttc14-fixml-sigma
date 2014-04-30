package fr.inria.spirals.sigma.ttc14.fixml

import java.io.File
import fr.inria.spirals.sigma.ttc14.fixml.objlang.Class
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.unice.i3s.sigma.m2t.M2TF
import fr.unice.i3s.sigma.support.SigmaSupport
import fr.unice.i3s.sigma.util.IOUtils
import fr.inria.spirals.sigma.ttc14.fixml.xmlmm.XMLNode
import java.net.URL

object Main extends App with SigmaSupport with ObjLang {

  case class Driver(name: String, m2class: M2TF[Class], resources: Set[URL] = Set())

  // currently implemented drivers
  val drivers = Seq(
    Driver(
      "java",
      M2TF((new BaseObjLang2Class with ObjLang2Java, { s: Class ⇒ s"${s.name}.java" }))
    ),
    Driver(
      "cs",
      M2TF((new BaseObjLang2Class with ObjLang2CSharp, { s: Class ⇒ s"${s.name}.cs" }))
    ),
    Driver(
      "cpp",
      M2TF(
        (new ObjLang2CPPClassHeader, { s: Class ⇒ s"${s.name}.h" }),
        (new ObjLang2CPPClassImpl, { s: Class ⇒ s"${s.name}.cpp" })
      )
    ),
    Driver(
      "c",
      M2TF(
        (new ObjLang2CClassHeader, { s: Class ⇒ s"${s.name}.h" }),
        (new ObjLang2CClassImpl, { s: Class ⇒ s"${s.name}.c" })
      ),
      Set(
        getClass.getResource("resources/c/arrays.h"),
        getClass.getResource("resources/c/arrays.c")
      )
    )
  )

  def execute(src: File, dest: File) {

    if (dest.isDirectory) {
      println(s"Cleaning directory ${dest}")
      IOUtils.rmdir(dest, false, true)
    } else {
      println(s"Creating output directory ${dest}")
      IOUtils.mkdirs(dest)
    }

    val files = if (src.isDirectory) {
      src.listFiles filter (_.getName.endsWith("xml"))
    } else {
      Array(src)
    }

    def timeStamp[T](msg: String)(thunk: => T): T = {
      val start = System.currentTimeMillis
      val res = thunk
      val time = System.currentTimeMillis - start

      println(s"$msg in ${time}ms")

      res
    }

    timeStamp(s"Transforming all") {
      for (file ← files) {

        timeStamp(s"Transformed $file") {
          val fixml = timeStamp(s"\t$file: Transformed XML -> XMLMM (T2M)") {
            FIXMLParser.parseFromFile(file) orCrash s"Unable to load FIXML from ${src}"
          }

          val objlang = timeStamp(s"\t$file: Trasformed XMLMM -> ObjLang (M2M)") {
            new XMLMM2ObjLang().transformAll(fixml)
          }

          for (Driver(ext, m2class, resources) ← drivers) {
            val output = new File(new File(dest, file.getName), ext)
            IOUtils.mkdirs(output)

            val classes = objlang collect { case c: Class ⇒ c }

            timeStamp(s"\t$file: Transformed ObjLang to $ext (M2T)") {
              classes foreach (m2class.transform(_, output))
            }

            resources foreach { r =>
              val file = r.getFile
              r.openStream() >> new File(output, file.substring(file.lastIndexOf('/')))
            }
          }
        }
      }
    }
  }

  val Usage =
    s"""|Usage: Main <src> <dest>
        |
        |where:
        |- src     is is FIXML 4.4 message file or a directory with multiple FIXML 4.4 messages
	    |- dest    is the output directory where the source code should generated.""".stripMargin

  if (args.size != 2) {
    println(Usage)
    sys.exit(1)
  }

  execute(new File(args(0)), new File(args(1)))

}