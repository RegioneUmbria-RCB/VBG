using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class LeggiFascicoliInXML
    {
        [XmlElement("CLASS_COD")]
        public string Classifica { get; set; }

        [XmlElement("FASCICOLO_NUMERO")]
        public string NumeroFascicolo { get; set; }

        [XmlElement("FASCICOLO_ANNO")]
        public string AnnoFascicolo { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }
    }
}
