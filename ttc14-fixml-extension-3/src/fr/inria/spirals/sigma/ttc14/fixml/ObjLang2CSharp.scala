package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2t.M2T
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

trait ObjLang2CSharp extends BaseObjLangMTT {

  override def header = {
    !"using System;"
    !endl
  }

  def getClassDefinition(clazz: Class) = {
    val abstr = if (clazz.abstract_) "abstract" else ""
    val exten = clazz.superclass map (" : " + _.name) getOrElse ""

    s"${abstr} class ${clazz.name}${exten}"
  }

  override def class2Code(p: DataType) = p.name match {
    case "java.lang.Boolean" | "boolean" => "bool"
    case "java.lang.Char" | "char" => "char"
    case "java.lang.Byte" | "byte" => "byte"
    case "java.lang.Short" | "short" => "short"
    case "java.lang.Integer" | "int" => "int"
    case "java.lang.Long" | "long" => "long"
    case "java.lang.Float" | "float" => "float"
    case "java.lang.Double" | "double" => "double"
    case "java.lang.String" => "string"
    case "javax.xml.datatype.XMLGregorianCalendar" => "DateTime"
    case "java.math.BigDecimal" => "decimal"
    case "java.math.BigInteger" => "ulong"
    case _ => sys.error(s"Unknown data type: ${p.name}")
  }

}