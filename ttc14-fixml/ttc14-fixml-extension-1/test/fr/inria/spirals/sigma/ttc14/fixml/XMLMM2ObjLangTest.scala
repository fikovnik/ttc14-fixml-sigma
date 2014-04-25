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

object XMLMM2ObjLangTest extends App with XMLMM with ScalaSigmaSupport {

  val test =
    <FIXML>
      <PosRpt RptID="541386431">
        <Pty ID="OCC" R="21"/>
        <Pty ID="C" R="38">
          <Sub ID="ZZZ" Typ="2"/>
        </Pty>
        <Pty ID="C" R="38">
          <Sub ID="ZZZ" Typ="2"/>
        </Pty>
      </PosRpt>
    </FIXML>

  val fixml = FIXMLParser.parse(test).get
  fixml foreach (_.sDump())

  val m2m = new XMLMM2ObjLang

  val targets = m2m.transform(fixml.head)

  targets foreach (_.sDump())

  println("\nNo container\n")

  targets filter { _.eContainer == null } foreach (println(_))

  EMFUtils.IO.registerDefaultFactories
  EMFUtils.IO.saveToFile(new File("test1.xmi"), targets)

  val m2t = new ObjLang2Java
  val code = targets collect { case x: Class ⇒ m2t.transform(x) }
  println(code)
}