package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

trait ObjLang2CPP extends BaseObjLangMTT {

  var cppHeaderSuffix = "h"

  implicit class ClassCPPUtil(that: Classifier) {
    def cppHeaderFile = s"${that.name}.${cppHeaderSuffix}"
  }

  def getClassDefinition(clazz: Class) = {
    val exten = clazz.superclass map (" : public " + _.name) getOrElse ""

    s"class ${clazz.name}${exten}"
  }

  override def toCode(e: NullLiteral) = "NULL"

  override def type2Code(e: TypedElement): String = (e.type_.isInstanceOf[Class], e.many) match {
    case (true, false) => class2Code(e.type_) + "*"
    case (true, true) => class2Code(e.type_) + "**"
    case (false, true) => class2Code(e.type_) + "[]"
    case (false, false) => class2Code(e.type_)
  }

  override def toCode(a: ArrayLiteral): String =
    s"new ${class2Code(a.type_)}*[${a.elements.size}] { ${a.elements map (toCode) mkString (", ")} }"

  override def class2Code(p: DataType) = p.name match {
    case "java.lang.Boolean" | "boolean" => "bool"
    case "java.lang.Char" | "char" => "char"
    case "java.lang.Byte" | "byte" => "char"
    case "java.lang.Short" | "short" => "short"
    case "java.lang.Integer" | "int" => "int"
    case "java.lang.Long" | "long" => "long long"
    case "java.lang.Float" | "float" => "float"
    case "java.lang.Double" | "double" => "double"
    case _ => "std::string"
  }
}