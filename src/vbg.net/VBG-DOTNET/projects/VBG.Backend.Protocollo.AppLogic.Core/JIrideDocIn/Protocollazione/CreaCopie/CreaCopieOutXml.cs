using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.CreaCopie
{
    [XmlRoot(Namespace = "", ElementName = "CreaCopieOut", IsNullable = false)]
    public class CreaCopieOutXml
    {
        public string Messaggio { get; set; }
        public string Errore { get; set; }
        public int IdDocumentoSorgente { get; set; }

        [XmlArrayItemAttribute("CopiaCreata", IsNullable = false)]
        public CopiaCreataXml[] CopieCreate { get; set; }
    }

    public class CopiaCreataXml
    {
        public int IdDocumentoCopia { get; set; }
        
        public string Carico { get; set; }
    }
}
