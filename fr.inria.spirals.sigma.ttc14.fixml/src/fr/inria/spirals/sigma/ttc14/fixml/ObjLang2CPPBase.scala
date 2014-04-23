package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._
import fr.inria.spirals.sigma.ttc14.fixml.objlang.PrimitiveType

trait ObjLang2CPPBase extends ObjLang2Code {

  var cppHeaderSuffix = "h"

  implicit class ClassCPPUtil(that: Class) {
    def cppHeaderFile = s"${that.name}.${cppHeaderSuffix}"
  }

  override def toCode(p: ReferenceParameter): String =
    s"${p.type_.name}* ${p.name}"

  protected def toCode(p: PrimitiveType) = p match {
    case PrimitiveType.STRING ⇒ "std::string"
  }

}