using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.PosteWeb
{
    [XmlRoot(ElementName = "messaggioOut")]
    public class MessaggioOut
    {
        [XmlElement(ElementName = "codice")]
        public string Codice { get; set; }

        [XmlElement(ElementName = "descrizione")]
        public string Descrizione { get; set; }
    }
}
