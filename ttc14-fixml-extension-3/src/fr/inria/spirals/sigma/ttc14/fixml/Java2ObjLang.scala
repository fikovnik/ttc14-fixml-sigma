package fr.inria.spirals.sigma.ttc14.fixml

import collection.JavaConversions._
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.unice.i3s.sigma.m2m._
import fr.unice.i3s.sigma.m2m.emf._
import fr.inria.spirals.sigma.ttc14.fixml.javamm._
import fr.unice.i3s.sigma.m2m.annotations.Lazy
import fr.unice.i3s.sigma.m2m.annotations.LazyUnique
import scala.util.Try
import java.lang.reflect.Field
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.XmlElement
import java.lang.reflect.ParameterizedType
import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlElementRef
import java.lang.reflect.WildcardType
import fr.unice.i3s.sigma.m2m.annotations.Abstract

class Java2ObjLang extends M2MT with ObjLang {

  val ListClass = classOf[java.util.List[_]]

  sourceMetaModels = JavaMetaModel("java.lang")
  targetMetaModels = _objlang

  def ruleClass2Class(s: Class[_], t: _objlang.Class) = guardedBy {
    !s.isEnum && s.isAnnotationPresent(classOf[XmlType])
  } transform {
    t.name = s.simpleName
    t.abstract_ = s.isAbstract
    
    t.superclass = Option(s.getSuperclass) match {
      case Some(c) if c.isAnnotationPresent(classOf[XmlType]) => c.sTarget[_objlang.Class]
      case _ => null
    } 

    t.members ++= s.sTargets[_objlang.Constructor]
    t.members ++= s.allDeclaredField.sTarget[_objlang.Field]
  }

  @LazyUnique
  def ruleClass2DefaultConstructor(s: Class[_], t: _objlang.Constructor) {
  }

  @LazyUnique
  def ruleClass2NonDefaultConstructor(s: Class[_], t: _objlang.Constructor) = guardedBy {
    s.allDeclaredField.nonEmpty
  } transform {

    for (e ← s.allDeclaredField) {
      val param = e.sTarget[_objlang.Parameter]
      val field = e.sTarget[_objlang.Field]

      t.parameters += param
      t.initialisations += _objlang.FieldInitialisiation(
        field = field,
        expression = _objlang.ParameterAccess(parameter = param))
    }
  }

  @Lazy
  def ruleField2ConstructorParameter(s: Field, t: _objlang.Parameter) {
    val field = s.sTarget[_objlang.Field]

    t.name = field.name
    t.many = field.many
    t.type_ = field.type_
  }

  def ruleAttribute2Field(s: Field, t: _objlang.Field) = guardedBy {
    s.isAnnotationPresent(classOf[XmlAttribute])
  } transform {
    t.name = checkName(s.getAnnotation(classOf[XmlAttribute]).name)
    // can be both DataType or Enum
    t.type_ = s.getType.sTarget[_objlang.Classifier]
  }

  @Abstract
  def ruleAbstractElement2Field(s: Field, t: _objlang.Field) = guardedBy {
    s.isAnnotationPresent(classOf[XmlElement]) ||
      s.isAnnotationPresent(classOf[XmlElementRef])
  } transform {

    val name = Option(s getAnnotation (classOf[XmlElement])) match {
      case Some(a) => a.name
      case None => s.getAnnotation(classOf[XmlElementRef]).name
    }

    s.getType match {
      case ListClass =>
        t.many = true
        t.name = name + "_objects"
      case x =>
        t.name = name + "_object"
    }
  }

  def ruleElement2Field(s: Field, t: _objlang.Field) = guardedBy {
    s.isAnnotationPresent(classOf[XmlElement])
  } transform {
    s.getType match {
      case ListClass =>
        t.type_ = s.typeParameters(0).sTarget[_objlang.Classifier]
      case x =>
        t.type_ = x.sTarget[_objlang.Classifier]
    }
  }

  def ruleElementRef2Field(s: Field, t: _objlang.Field) = guardedBy {
    s.isAnnotationPresent(classOf[XmlElementRef])
  } transform {

    s.getType match {
      case ListClass =>
        // e.g.: protected List<JAXBElement<? extends AbstractMessageT>> message;

        val listParam = s.typeParameters(0).asInstanceOf[ParameterizedType]
        val jaxbParam = listParam.getActualTypeArguments()(0).asInstanceOf[WildcardType]
        t.type_ = jaxbParam.getUpperBounds()(0).sTarget[_objlang.Classifier]
      case x =>
        // e.g.: protected JAXBElement<? extends AbstractMessageT> message;

        val jaxbParam = s.typeParameters(0).asInstanceOf[WildcardType]
        t.type_ = jaxbParam.getUpperBounds()(0).sTarget[_objlang.Classifier]
    }
  }

  @LazyUnique
  def ruleClass2DataType(s: Class[_], t: _objlang.DataType) = guardedBy {
    !s.isAnnotationPresent(classOf[XmlType])
  } transform {
    t.name = s.name
  }

  @LazyUnique
  def ruleClass2Enum(s: Class[_], t: _objlang.Enum) = guardedBy {
    s.isAnnotationPresent(classOf[XmlEnum]) && s.isEnum
  } transform {
    t.name = s.simpleName
    t.items ++= s.getEnumConstants() map { e => _objlang.EnumItem(name = e.toString) }
  }

  // HELPERS

  def checkName(name: String): String = "_" + name

  implicit class FieldExtras(that: Field) {
    def typeParameters = that.getGenericType match {
      case x: ParameterizedType => x.getActualTypeArguments
      case _ => Array()
    }
  }

  implicit class ClassExtras(that: Class[_]) {

    private def traverse[A](s: Class[_])(fun: Class[_] => Iterable[A]): Iterable[A] = fun(s) ++ {
      s.getSuperclass match {
        case x if x.isInstanceOf[Object] => Seq()
        case x => traverse(x)(fun)
      }
    }

    def simpleName = that.getSimpleName
    def allDeclaredField = traverse(that)(_.getDeclaredFields)
  }

}