package fr.inria.spirals.sigma.ttc14.fixml

import java.net.URL
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.xml.Elem
import scala.xml.MetaData
import scala.xml.Node
import scala.xml.Source
import scala.xml.XML
import org.xml.sax.InputSource
import fr.inria.spirals.sigma.ttc14.fixml.xmlmm.support.XMLMM
import scala.xml.Utility

class FIXMLParserException(message: String, cause: Throwable = null) extends Exception(message, cause)

object FIXMLParser extends XMLMM {

  def parseFromText(text: String): Try[Iterable[_xmlmm.XMLNode]] =
    parseFromInputSource(Source.fromString(text))

  def parseFromFile(filename: String): Try[Iterable[_xmlmm.XMLNode]] =
    parseFromInputSource(Source.fromFile(filename))

  def parseFromURL(url: URL): Try[Iterable[_xmlmm.XMLNode]] =
    parseFromInputSource(Source.fromInputStream(url.openStream()))

  def parseFromInputSource(in: InputSource): Try[Iterable[_xmlmm.XMLNode]] =
    Try(XML.load(in)) match {
      case Success(fixml) ⇒ parse(fixml)
      case Failure(e) ⇒ Failure(new FIXMLParserException(s"Unable to parse XML file: $e", e))
    }

  def parse(root: Node): Try[Iterable[_xmlmm.XMLNode]] =
    Utility.trim(root) match {
      case <FIXML><FIXMLMessage>{ _* }</FIXMLMessage></FIXML> ⇒ Failure(new FIXMLParserException("FIXML DTD Version is not supported"))
      case <FIXML>{ tags @ _* }</FIXML> ⇒ Success(parseNodes(tags))
      case _ ⇒ Failure(new FIXMLParserException("No matching FIXML tag found"))
    }

  protected def parseAttributes(metaData: MetaData): Iterable[_xmlmm.XMLAttribute] =
    metaData collect {
      case e: xml.Attribute ⇒ _xmlmm.XMLAttribute(name = e.key, value = e.value.toString)
    }

  protected def parseNodes(nodes: Iterable[Node]): Iterable[_xmlmm.XMLNode] = {
    // discard any XML PCDATA nodes
    val elems = nodes collect { case e: Elem ⇒ e }

    // convert all nodes into XMLNode
    for (elem ← elems) yield _xmlmm.XMLNode(
      tag = elem.label,
      subnodes = parseNodes(elem.child),
      attributes = parseAttributes(elem.attributes))
  }

}