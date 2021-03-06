package fr.inria.spirals.sigma.ttc14.fixml.objlang.impl

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._


class ClassImplDelegate extends ClassImpl with ClassDelegate

trait ClassDelegate extends Class with ObjLang {

  override def getConstructors = this.members collect { case e: Constructor ⇒ e }
  
  override def getFields = this.members collect { case e: Field ⇒ e }
    
}