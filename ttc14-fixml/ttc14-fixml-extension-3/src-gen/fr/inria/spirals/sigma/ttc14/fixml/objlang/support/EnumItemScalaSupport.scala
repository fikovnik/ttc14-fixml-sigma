package fr.inria.spirals.sigma.ttc14.fixml.objlang.support

import fr.inria.spirals.sigma.ttc14.fixml.objlang.EnumItem;

import fr.unice.i3s.sigma.support.EMFProxyBuilder;
import fr.unice.i3s.sigma.support.EMFScalaSupport;

trait EnumItemScalaSupport extends EMFScalaSupport {
  type EnumItem = fr.inria.spirals.sigma.ttc14.fixml.objlang.EnumItem
  
  protected implicit val _enumitemProxyBuilder = new EMFProxyBuilder[EnumItem](ObjLang._objlangBuilder)
  
  object EnumItem {
    def apply(name: String = null, parent: fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum = null): EnumItem = {
      val _instance = ObjLang._objlangBuilder.create[EnumItem]
      
      if (name != null) _instance.setName(name)
      if (parent != null) _instance.setParent(parent)
      
      _instance
    }
    
    def unapply(that: EnumItem): Option[(String,fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum)] =
      Some((that.getName,that.getParent))
  }
}

object EnumItemScalaSupport extends EnumItemScalaSupport
