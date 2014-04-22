package fr.inria.spirals.sigma.ttc14.fixml.objlang.support

import fr.inria.spirals.sigma.ttc14.fixml.objlang.Member;

import fr.unice.i3s.sigma.support.EMFProxyBuilder;
import fr.unice.i3s.sigma.support.EMFScalaSupport;

import org.eclipse.emf.common.util.EList;

trait ClassScalaSupport extends EMFScalaSupport {
  type Class = fr.inria.spirals.sigma.ttc14.fixml.objlang.Class
  
  protected implicit val _classProxyBuilder = new EMFProxyBuilder[Class](ObjLang._objlangBuilder)
  
  object Class {
    def apply(name: String = null, members: EList[Member] = null, superclass: fr.inria.spirals.sigma.ttc14.fixml.objlang.Class = null): Class = {
      val _instance = ObjLang._objlangBuilder.create[Class]
      
      if (name != null) _instance.setName(name)
      if (members != null) _instance.getMembers.addAll(members)
      if (superclass != null) _instance.setSuperclass(superclass)
      
      _instance
    }
    
    def unapply(that: Class): Option[(String,EList[Member],fr.inria.spirals.sigma.ttc14.fixml.objlang.Class)] =
      Some((that.getName,that.getMembers,that.getSuperclass))
  }
}

object ClassScalaSupport extends ClassScalaSupport
