using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.PEC
{
    [XmlRoot(ElementName = "messaggioIn")]
    public class VerificaInvioIn
    {
        public VerificaInvioIn()
        {

        }

        [XmlElement(ElementName = "annoProt")]
        public string AnnoProt { get; set; }

        [XmlElement(ElementName = "numProt")]
        public string NumProt { get; set; }

        [XmlElement(ElementName = "docId")]
        public string DocId { get; set; }

        [XmlElement(ElementName = "utente")]
        public string Utente { get; set; }

        [XmlElement(ElementName = "ruolo")]
        public string Ruolo { get; set; }

    }
}
