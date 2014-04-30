package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

class ObjLang2CClassHeader extends BaseObjLang2Class with CHeader with ObjLang2C {

  override def header = {
    super.header

    !"#include <stdlib.h>"

    !endl

    source.fields map (_.type_) collect {
      case x: Class => x
    } map (_.cppHeaderFile) foreach { hdr ⇒
      !s"#include ${hdr.quoted}"
    }

    !endl
  }

  override def main = {
    header

    !s"typedef struct" curlyIndent {
      genFields
    } 
    !s" ${source.name};"

    !endl

    genConstructors

    footer
  }

  override def genFields = super.genFields
  override def genField(c: Field) = !s"${type2Code(c)} ${c.name};"
  override def genConstructors = {
    !(getNewDecl(source) + ";")

    super.genConstructors

    !(getDeleteDecl(source) + ";")
  }

  override def genConstructor(c: Constructor) = {
    !(getConstructorDecl(c) + ";")
  }

}