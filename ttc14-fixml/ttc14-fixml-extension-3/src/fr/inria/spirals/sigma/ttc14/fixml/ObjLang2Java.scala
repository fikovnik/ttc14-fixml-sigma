package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

trait ObjLang2Java extends BaseObjLangMTT {

  val BaseTypes = """^java\.lang+\.([^.]+)$""".r

  def getClassDefinition(clazz: Class) = {
    val abstr = if (clazz.abstract_) "abstract" else ""
    val exten = clazz.superclass map (" extends " + _.name) getOrElse ""
    
    s"${abstr} class ${clazz.name}${exten}"
  }

  override def class2Code(p: DataType) = p.name match {
    case BaseTypes(t) => t
    case x => x
  }

}