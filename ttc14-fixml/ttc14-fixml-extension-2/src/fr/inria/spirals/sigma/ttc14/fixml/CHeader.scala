package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._

trait CHeader extends BaseObjLangMTT { 

  type Source >: Null <: Classifier
  
  override def header = {
    !s"#ifndef _${source.name}_H_"
    !s"#define _${source.name}_H_"
    
    !endl    
  }
  
  override def footer = {
    !endl

    !s"#endif // _${source.name}_H_"    
  }

}