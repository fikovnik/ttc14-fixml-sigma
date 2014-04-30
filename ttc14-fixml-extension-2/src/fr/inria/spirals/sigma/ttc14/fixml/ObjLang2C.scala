package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

trait ObjLang2C extends BaseObjLangMTT {

  var cppHeaderSuffix = "h"

  implicit class ClassCPPUtil(that: Classifier) {
    def cppHeaderFile = s"${that.name}.${cppHeaderSuffix}"
  }

  def getNewDecl(clazz: Class) = s"${clazz.name}* ${clazz.name}_new()"
  def getDeleteDecl(clazz: Class) = s"void ${clazz.name}_delete(${clazz.name}* this)"
  def getConstructorName(c: Constructor) = {
    val clazz = c.parent

    val suffix = if (c.parameters.isEmpty()) ""
    else {
      val cs = c.parent.constructors.zipWithIndex.filter { case (c, _) => c.parameters.nonEmpty }
      if (cs.size == 1) "_custom"
      else "_custom_" + (cs.indexOf(c) + 1)
    }

    s"${clazz.name}_init${suffix}"
  }

  def getConstructorDecl(c: Constructor) = {
    val clazz = c.parent
    val args = (Parameter(name = "this", type_ = clazz) +: c.parameters) map param2Code mkString (", ")
    s"${clazz.name}* ${getConstructorName(c)}($args)"
  }

  override def toCode(e: NullLiteral) = "NULL"

  override def type2Code(e: TypedElement): String = (e.type_.isInstanceOf[Class], e.many) match {
    case (true, false) => class2Code(e.type_) + "*"
    case (true, true) => class2Code(e.type_) + "**"
    case (false, true) => class2Code(e.type_) + "[]"
    case (false, false) => class2Code(e.type_)
  }

  override def toCode(e: ConstructorCall): String = {
    val args = s"${e.arguments map toCode mkString (", ")}"
    if (args.length > 0)
      s"${getConstructorName(e.constructor)}(${e.constructor.parent.name}_new(), $args)"
    else
      s"${getConstructorName(e.constructor)}(${e.constructor.parent.name}_new())"
  }

  override def toCode(a: ArrayLiteral): String =
    s"(${class2Code(a.type_)}**) new_array(${a.elements.size}, ${a.elements map (toCode) mkString (", ")})"

  override def class2Code(p: DataType) = {
    import XMLMM2ObjLang._
    p match {
      case DTString => "char*"
      case DTDouble => "double"
      case DTLong => "long long"
      case DTInteger => "int"
    }
  }
}