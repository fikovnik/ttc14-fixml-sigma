package fr.inria.spirals.sigma.ttc14.fixml.objlang.support

import fr.inria.spirals.sigma.ttc14.fixml.objlang.EnumItem;

import fr.unice.i3s.sigma.support.EMFProxyBuilder;
import fr.unice.i3s.sigma.support.EMFScalaSupport;

import org.eclipse.emf.common.util.EList;

trait EnumScalaSupport extends EMFScalaSupport {
  type Enum = fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum
  
  protected implicit val _enumProxyBuilder = new EMFProxyBuilder[Enum](ObjLang._objlangBuilder)
  
  object Enum {
    def apply(name: String = null, items: EList[EnumItem] = null): Enum = {
      val _instance = ObjLang._objlangBuilder.create[Enum]
      
      if (name != null) _instance.setName(name)
      if (items != null) _instance.getItems.addAll(items)
      
      _instance
    }
    
    def unapply(that: Enum): Option[(String,EList[EnumItem])] =
      Some((that.getName,that.getItems))
  }
}

object EnumScalaSupport extends EnumScalaSupport
