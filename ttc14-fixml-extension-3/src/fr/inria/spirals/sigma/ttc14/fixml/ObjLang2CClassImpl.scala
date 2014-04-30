package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

class ObjLang2CClassImpl extends BaseObjLang2Class with ObjLang2C {

  override def header = {
    super.header

    !("#include " + "arrays.h".quoted)

    !endl

  }

  override def content = {
    !s"#include ${source.cppHeaderFile.quoted}"

    !endl

    genConstructors
  }

  override def genConstructors = {
    genNew

    !endl

    super.genConstructors

    !endl

    genDelete
  }

  def genNew = {
    !getNewDecl(source) curlyIndent {
      !s"return (${source.name}*) malloc(sizeof(${source.name}));"
    }
  }

  def genDelete {
    !getDeleteDecl(source) curlyIndent {
      !s"free(this);"
    }
  }

  override def genConstructor(c: Constructor) = {
    !getConstructorDecl(c) curlyIndent {
      for (field <- c.parent.fields) {
        c.initialisations
          .find(_.field == field) // try to find field initialization within a constructor
          .map(_.expression)
          .orElse(field.initialValue) // if there is none try field itself
          .foreach { e =>
            !s"this->${field.name} = ${toCode(e)};"
          }
      }
      !"return this;"
    }
    !endl
    !endl
  }

}