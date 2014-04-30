package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

class ObjLang2CPPEnumHeader extends BaseObjLang2Enum with ObjLang2CPP with CHeader {
  override def content = {
    !s"enum class ${source.name}" curlyIndent {
      genEnumItems
    }
    !s";"
  }
}