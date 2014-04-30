package fr.inria.spirals.sigma.ttc14.fixml

import fr.unice.i3s.sigma.m2m.DefaultTransformable
import fr.unice.i3s.sigma.m2m.M2MContext
import fr.unice.i3s.sigma.m2m.MetaClass
import fr.unice.i3s.sigma.m2m.MetaModel
import fr.unice.i3s.sigma.m2m.SigmaM2MSequenceSupport
import fr.unice.i3s.sigma.m2m.Transformable
import fr.unice.i3s.sigma.support.SigmaSupport

package object javamm {
  implicit def class2transformable(that: java.lang.Class[_])(implicit context: M2MContext) = new DefaultTransformable(context, that)
  implicit def type2transformable(that: java.lang.reflect.Type)(implicit context: M2MContext) = new DefaultTransformable(context, that)
  implicit def field2transformable(that: java.lang.reflect.Field)(implicit context: M2MContext) = new DefaultTransformable(context, that)
  implicit def array2TransformableSeq[A <% Transformable](that: Array[A]) = new SigmaM2MSequenceSupport(that)
}

package javamm {

  object JavaMetaModel {
    def apply(pkg: String) = new JavaMetaModel(pkg)
  }
  class JavaMetaModel(val pkg: String) extends MetaModel {

    import SigmaSupport._

    case class JavaMetaClass(val clazz: Class[_]) extends MetaClass {

      def isTypeOf(m: MetaClass): Boolean = m match {
        case x: JavaMetaClass => x.clazz == clazz
        case _ => false
      }

      def isKindOf(m: MetaClass): Boolean = m match {
        case x: JavaMetaClass => clazz isAssignableFrom (x.clazz)
        case _ => false
      }

      def isTypeOf(m: AnyRef) = clazz == m.getClass
      def isKindOf(m: AnyRef) = clazz isAssignableFrom (m.getClass)
      def newInstance: AnyRef = clazz.newInstance.asInstanceOf[AnyRef]
      def isAbstract = clazz.isAbstract
      def name = clazz.name
      def contents(elem: AnyRef): Seq[AnyRef] =
        elem.getClass.getDeclaredFields ++ elem.getClass.getDeclaredMethods
    }

    type MC = JavaMetaClass

    def findMetaClass(elem: AnyRef) = findMetaClass(elem.getClass)
    def findMetaClass(clazz: Class[_]) = if (containsClass(clazz)) Some(JavaMetaClass(clazz)) else None

    def containsClass(clazz: Class[_]) = clazz.getPackage.getName startsWith (pkg)
    def containsElement(elem: AnyRef) = containsClass(elem.getClass)

    override def toString = s"JavaMetaModel($pkg)"

  }

}