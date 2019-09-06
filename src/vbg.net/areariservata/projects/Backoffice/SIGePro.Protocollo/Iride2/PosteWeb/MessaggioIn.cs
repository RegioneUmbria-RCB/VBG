using System.Xml.Serialization;
using System.Collections.Generic;

namespace Init.SIGePro.Protocollo.Iride2.PosteWeb
{
    [XmlRoot(ElementName = "messaggioIn")]
    public class MessaggioIn
    {
        public MessaggioIn()
        {
            //this.DestinatariMail = new string[]();
            //this.DestinatariCCMail = new string[]();
        }

        [XmlElement(ElementName = "docId")]
        public string DocId { get; set; }

        [XmlElement(ElementName = "oggettoMail")]
        public string OggettoMail { get; set; }

        [XmlElement(ElementName = "testoMail")]
        public string TestoMail { get; set; }

        [XmlElement(ElementName = "mittenteMail")]
        public string MittenteMail { get; set; }

        [XmlArray(ElementName = "destinatariMail")]
        [XmlArrayItem(ElementName = "destinatarioMail")]
        public string[] DestinatariMail { get; set; }

        [XmlArray(ElementName = "destinatariCCMail")]
        [XmlArrayItem(ElementName = "destinatarioMail")]
        public string[] DestinatariCCMail { get; set; }

        [XmlElement(ElementName = "DMSerialPrincipale")]
        public string DMSerialPrincipale { get; set; }

        [XmlElement(ElementName = "DMSerialAllegati")]
        public string DMSerialAllegati { get; set; }

        [XmlElement(ElementName = "utente")]
        public string Utente { get; set; }

        [XmlElement(ElementName = "ruolo")]
        public string Ruolo { get; set; }

        [XmlElement(ElementName = "invioInteroperabile")]
        public string InvioInteroperabile { get; set; }
    }

}