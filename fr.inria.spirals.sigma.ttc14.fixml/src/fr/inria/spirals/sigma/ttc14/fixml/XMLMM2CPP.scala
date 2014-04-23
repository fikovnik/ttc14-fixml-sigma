package fr.inria.spirals.sigma.ttc14.fixml

import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang
import fr.inria.spirals.sigma.ttc14.fixml.objlang.support.ObjLang._objlang._
import fr.inria.spirals.sigma.ttc14.fixml.xmlmm.support.XMLMM
import fr.inria.spirals.sigma.ttc14.fixml.xmlmm.support.XMLMM._xmlmm._

class XMLMM2CPP extends XMLMM2ObjLang {

  // http://en.cppreference.com/w/cpp/keyword
  val ReservedKeywords = Seq(
    "alignas",
    "alignof",
    "and",
    "and_eq",
    "asm",
    "auto",
    "bitand",
    "bitor",
    "bool",
    "break",
    "case",
    "catch",
    "char",
    "char16_t",
    "char32_t",
    "class",
    "compl",
    "const",
    "constexpr",
    "const_cast",
    "continue",
    "decltype",
    "default",
    "delete",
    "do",
    "double",
    "dynamic_cast",
    "else",
    "enum",
    "explicit",
    "export",
    "extern",
    "false",
    "float",
    "for",
    "friend",
    "goto",
    "if",
    "inline",
    "int",
    "long",
    "mutable",
    "namespace",
    "new",
    "noexcept",
    "not",
    "not_eq",
    "nullptr",
    "operator",
    "or",
    "or_eq",
    "private",
    "protected",
    "public",
    "register",
    "reinterpret_cast",
    "return",
    "short",
    "signed",
    "sizeof",
    "static",
    "static_assert",
    "static_cast",
    "struct",
    "switch",
    "template",
    "this",
    "thread_local",
    "throw",
    "true",
    "try",
    "typedef",
    "typeid",
    "typename",
    "union",
    "unsigned",
    "using",
    "virtual",
    "void",
    "volatile",
    "wchar_t",
    "while",
    "xor",
    "xor_eq")

  override def ruleXMLAttribute2Attribute(s: XMLAttribute, t: Attribute) {
    super.ruleXMLAttribute2Attribute(s, t)

    t.setInitialValue(null)
  }

  override def ruleXMLNode2Reference(s: XMLNode, t: Reference) {
    super.ruleXMLNode2Reference(s, t)
    
    t.setInitialValue(null)
  }  
  
  override def ruleXMLNode2DefaultConstructor(s: XMLNode, t: Constructor) {
    super.ruleXMLNode2DefaultConstructor(s, t)

    t.initialisations ++= s.allAttributes map { attr ⇒
      val init = FieldInitialisiation()
      init.field = ~attr
      init.expression = StringLiteral(attr.value)
      init
    }

    t.initialisations ++= s.allSubnodes map { ref ⇒
      val init = FieldInitialisiation()
      init.field = ~ref
      init.expression = ~ref
      init
    }
  }

}