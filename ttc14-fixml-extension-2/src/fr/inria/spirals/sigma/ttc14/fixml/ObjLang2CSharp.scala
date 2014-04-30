package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

trait ObjLang2CSharp extends BaseObjLangMTT {

  override def class2Code(p: DataType) = {
    import XMLMM2ObjLang._
    p match {
      case DTString => "string"
      case DTDouble => "double"
      case DTLong => "long"
      case DTInteger => "int"
    }
  }

}