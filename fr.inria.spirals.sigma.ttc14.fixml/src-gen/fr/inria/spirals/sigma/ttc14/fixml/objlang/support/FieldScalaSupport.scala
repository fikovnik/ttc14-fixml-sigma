package fr.inria.spirals.sigma.ttc14.fixml.objlang.support

import fr.inria.spirals.sigma.ttc14.fixml.objlang.Field;

import fr.unice.i3s.sigma.support.EMFScalaSupport;

trait FieldScalaSupport extends EMFScalaSupport {
  type Field = fr.inria.spirals.sigma.ttc14.fixml.objlang.Field
  
  object Field {
  }
}

object FieldScalaSupport extends FieldScalaSupport
