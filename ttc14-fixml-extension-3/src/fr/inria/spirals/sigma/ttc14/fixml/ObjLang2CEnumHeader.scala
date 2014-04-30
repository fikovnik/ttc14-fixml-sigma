package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

class ObjLang2CEnumHeader extends BaseObjLang2Enum with ObjLang2CPP with CHeader {
  override def content = {
    !s"typedef enum" curlyIndent {
      genEnumItems
    }
    !s" ${source.name};"
  }

  override def genEnumItems = {
    !(source.items map (source.name + "_" + _.name) mkString (", "))
  }

}