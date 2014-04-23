object xmltest {
  val filename = "/Users/krikava/Research/Publications/TTC14/solutions/fr.inria.spirals.sigma.ttc14.fixml/resources/test-cases/test1.xml"
                                                  //> filename  : String = /Users/krikava/Research/Publications/TTC14/solutions/fr
                                                  //| .inria.spirals.sigma.ttc14.fixml/resources/test-cases/test1.xml
  val fixml = xml.XML.loadFile(filename)          //> fixml  : scala.xml.Elem = <FIXML>
                                                  //|      <Order Acct="26522154" Px="93.25" OrdTyp="2" TransactTm="2001-09-11T09:
                                                  //| 30:47-05:00" Side="2" ClOrdID="123456">
                                                  //|          <Hdr SeqNum="521" PosRsnd="N" PosDup="N" Snt="2001-09-11T09:30:47-0
                                                  //| 5:00">
                                                  //|              <Sndr ID="AFUNDMGR"/>
                                                  //|              <Tgt ID="ABROKER"/>
                                                  //|          </Hdr>
                                                  //|          <Instrmt IDSrc="1" ID="459200101" Sym="IBM"/>
                                                  //|          <OrdQty Qty="1000"/>
                                                  //|        </Order>
                                                  //| </FIXML>
  fixml match {
    case <FIXML>{tags @ _*}</FIXML> =>
      val elems = tags collect { case e: xml.Elem => e }
      elems(0).child(1).attributes collect { case e: xml.Attribute => (e.key, e.value)}
    case _ =>
  }                                               //> res0: Any = List((SeqNum,521), (PosRsnd,N), (PosDup,N), (Snt,2001-09-11T09:3
                                                  //| 0:47-05:00))
  
}