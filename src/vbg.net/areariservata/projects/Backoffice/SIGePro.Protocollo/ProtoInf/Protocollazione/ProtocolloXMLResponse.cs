using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.ProtoInf.Protocollazione
{
    [XmlRoot(Namespace = "", ElementName = "RISPOSTA", IsNullable = false)]
    public class ProtocolloXMLResponse
    {
        [XmlElement(ElementName = "ESITO", Order = 1)]
        public string Esito { get; set; }

        [XmlElement(ElementName = "DATI", Order = 2, IsNullable = true)]
        public DatiRisposta Dati { get; set; }

        public ProtocolloXMLResponse()
        {

        }

        public class DatiRisposta
        {
            [XmlElement(ElementName = "CODAMM", Order = 1)]
            public string CodiceAmministrazione { get; set; }

            [XmlElement(ElementName = "CODAOO", Order = 2)]
            public string CodiceAoo { get; set; }

            [XmlElement(ElementName = "ANNO", Order = 3)]
            public string AnnoProtocollo { get; set; }

            [XmlElement(ElementName = "NUMERO", Order = 4)]
            public string NumeroProtocollo { get; set; }
        }
    }
}
