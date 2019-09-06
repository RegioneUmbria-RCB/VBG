using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class LeggiProtocolloInXML
    {
        [XmlElement("PROTOCOLLO_GRUPPO")]
        public ProtocolloGruppoInXml ProtocolloGruppo { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }
    }

    public class ProtocolloGruppoInXml
    {
        [XmlElement("ANNO")]
        public string Anno { get; set; }

        [XmlElement("NUMERO")]
        public string Numero { get; set; }

        [XmlElement("TIPO_REGISTRO")]
        public string TipoRegistro { get; set; }
    }
}
