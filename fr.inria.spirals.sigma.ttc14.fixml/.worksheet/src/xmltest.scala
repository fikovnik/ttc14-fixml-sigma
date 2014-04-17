object xmltest {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(154); 
  val filename = "/Users/krikava/Research/Publications/TTC14/solutions/fr.inria.spirals.sigma.ttc14.fixml/resources/test-cases/test1.xml";System.out.println("""filename  : String = """ + $show(filename ));$skip(41); 
  val fixml = xml.XML.loadFile(filename);System.out.println("""fixml  : scala.xml.Elem = """ + $show(fixml ));$skip(218); val res$0 = 
  fixml match {
    case <FIXML>{tags @ _*}</FIXML> =>
      val elems = tags collect { case e: xml.Elem => e }
      elems(0).child(1).attributes collect { case e: xml.Attribute => (e.key, e.value)}
    case _ =>
  };System.out.println("""res0: Any = """ + $show(res$0))}
  
}
