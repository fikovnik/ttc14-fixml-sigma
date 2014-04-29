package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.util.EMFUtils
import java.io.File
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._
import fr.unice.i3s.sigma.support.ScalaSigmaSupport
import scala.util.Success
import scala.util.Failure
import fr.unice.i3s.sigma.util.IOUtils
import org.eclipse.emf.ecore.EObject
import java.net.URLClassLoader
import fixml.PartiesBlockT

object Java2ObjLangTest extends App with ScalaSigmaSupport {

  val test = classOf[PartiesBlockT]

  val m2m = new Java2ObjLang

  val targets = m2m.transform(test) map (_.asInstanceOf[EObject])

  targets foreach (_.sDump())

  println("\nNo container\n")

  val nonContained = targets filter ( _.eContainer == null )
  nonContained foreach (println(_))
  

  EMFUtils.IO.registerDefaultFactories
  EMFUtils.IO.saveToFile(new File("test1.xmi"), nonContained)
  
  println()
  
  val m2t = new ObjLang2Java
  val code = targets collect { case x: Class ⇒ m2t.transform(x) }
  println(code)
}