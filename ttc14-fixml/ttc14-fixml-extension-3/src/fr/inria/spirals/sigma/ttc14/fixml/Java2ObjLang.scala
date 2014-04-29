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

class Java2ObjLang extends M2MT with ObjLang {

  sourceMetaModels = JavaMetaModel("java.lang")
  targetMetaModels = _objlang

  def ruleClass2Class(s: Class[_], t: _objlang.Class) {
    t.name = s.simpleName
    
    t.members ++= s.sTargets[_objlang.Constructor]
    t.members ++= s.allDeclaredField.sTarget[_objlang.Field]
  }

  def ruleClass2DefaultConstructor(s: Class[_], t: _objlang.Constructor) {
  }

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
    t.name = s.getAnnotation(classOf[XmlAttribute]).name
    t.type_ = s.getType.sTarget[_objlang.DataType]
  }

  def ruleElement2Field(s: Field, t: _objlang.Field) = guardedBy {
    s.isAnnotationPresent(classOf[XmlElement])
  } transform {
    t.name = s.getAnnotation(classOf[XmlElement]).name

    val ListClass = classOf[java.util.List[_]]

    s.getType match {
      case ListClass =>
        val genType = s.getGenericType.asInstanceOf[ParameterizedType]
        val params = genType.getActualTypeArguments

        t.many = true
        t.type_ = params(0).sTarget[_objlang.Classifier]
      case x =>
        t.type_ = x.sTarget[_objlang.Classifier]
    }
  }

  @LazyUnique
  def ruleClass2DataType(s: Class[_], t: _objlang.DataType) = guardedBy {
    !s.isAnnotationPresent(classOf[XmlType])
  } transform {
    t.name = s.name
  }

  // HELPERS

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