package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

abstract class BaseObjLang2Enum extends BaseObjLangMTT {

  type Source = Enum

  override def content = {
    !s"enum ${source.name}" curlyIndent {
      genEnumItems
    }
  }
  
  def genEnumItems = {
    !(source.items map (_.name) mkString(", "))
  }
  
}