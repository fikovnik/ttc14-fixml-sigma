package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

abstract class BaseObjLang2Class extends BaseObjLangMTT {

  type Source = Class

  def main = {
    header
    
    !getClassDefinition(source) curlyIndent {
      genFields

      !endl

      genConstructors
    }
    
    footer
  }
  
  def genConstructors =
    source.constructors foreach genConstructor

  def genFields =
    source.fields foreach genField

  def genField(c: Field) =
    !s"public ${type2Code(c)} ${c.name};"

  def genConstructor(c: Constructor) = {
    val args = c.parameters map param2Code mkString (", ")

    !s"public ${source.name}($args)" curlyIndent {
      c.initialisations foreach genFieldInitialization
    }

    !endl
  }

  def genFieldInitialization(fi: FieldInitialisiation) =
    !s"this.${fi.field.name} = ${toCode(fi.expression)};"
    
}