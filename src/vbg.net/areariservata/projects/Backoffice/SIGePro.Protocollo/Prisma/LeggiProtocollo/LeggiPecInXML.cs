using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class LeggiPecInXML
    {
        [XmlElement("PROTOCOLLO_GRUPPO")]
        public ProtocolloGruppoInXml ProtocolloGruppo { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }
    }
}
