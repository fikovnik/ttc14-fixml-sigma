package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._
import fr.inria.spirals.sigma.ttc14.fixml.objlang.PrimitiveType

abstract class ObjLang2Code extends M2T with ObjLang {

  type Source = Class

  def main = {
    !s"class ${source.name}" curlyIndent {
      genFields

      !endl

      genConstructors
    }
  }

  def genFields = {
    source.attributes foreach genAttribute
    source.references foreach genReference
  }

  def genConstructors =
    source.constructors foreach genConstructor

  def genAttribute(c: Attribute) =
    !s"public ${toCode(c.type_)} ${c.name}${getFieldInitialization(c)};"

  def genReference(c: Reference) =
    !s"public ${c.type_.name} ${c.name}${getFieldInitialization(c)};"

  def getFieldInitialization(f: Field) = " = " + (f.initialValue map toCode getOrElse (""))

  def genConstructor(c: Constructor) = {
    val args = c.parameters map toCode mkString (", ")

    !s"public ${source.name}($args)" curlyIndent {
      c.initialisations foreach genFieldInitialization
    }

    !endl
  }

  def genFieldInitialization(fi: FieldInitialisiation) =
    !s"this.${fi.field.name} = ${toCode(fi.expression)};"

  protected def toCode(e: Expression): String = e match {
    case StringLiteral(value) ⇒ value.quoted
    case ParameterAccess(param) ⇒ param.name
    case ConstructorCall(c, args) ⇒ s"new ${c.parent.name}(${args map toCode mkString (", ")})"
    case _: NullLiteral ⇒ "null"
  }

  protected def toCode(p: PrimitiveParameter): String =
    s"${toCode(p.type_)} ${p.name}"

  protected def toCode(p: ReferenceParameter): String =
    s"${p.type_.name} ${p.name}"

  protected def toCode(p: Parameter): String = p match {
    case x: PrimitiveParameter ⇒ toCode(x)
    case x: ReferenceParameter ⇒ toCode(x)
  }

  protected def toCode(p: PrimitiveType): String
}