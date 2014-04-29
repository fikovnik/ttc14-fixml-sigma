package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.xmlmm.support.XMLMM
import fr.inria.spirals.sigma.ttc14.fixml.xmlmm.support.XMLMM._xmlmm._
import fr.unice.i3s.sigma.util.EMFUtils
import java.io.File
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._
import fr.unice.i3s.sigma.support.ScalaSigmaSupport
import scala.util.Success
import scala.util.Failure
import fr.unice.i3s.sigma.util.IOUtils
import org.eclipse.emf.ecore.EObject

object XMLMM2ObjLangTest extends App with XMLMM with ScalaSigmaSupport {

  val test =
    <FIXML>
      <PosRpt RptID="541386431">
        <Pty ID="OCC" R="21"/>
        <Pty ID="C" R="38">
          <Sub ID="ZZZ" Typ="2"/>
          <Mnn Q="A" />
        </Pty>
        <Pty ID="C" R="38" Z="Q">
          <Sub ID="ZZZ" Typ="2"/>
		  <Sub ID="ZZZ" Typ="3" Oed="X"/>
        </Pty>
      </PosRpt>
    </FIXML>

  val fixml = FIXMLParser.parse(test).get
  fixml foreach (_.sDump())

  val m2m = new XMLMM2ObjLang

  val targets = m2m.transform(fixml.head) map (_.asInstanceOf[EObject])

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