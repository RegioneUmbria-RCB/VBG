using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class CreaFascicoloInXML
    {
        [XmlElement("CLASS_COD")]
        public string CodiceClassifica { get; set; }

        [XmlElement("FASCICOLO_ANNO")]
        public string AnnoFascicolo { get; set; }

        [XmlElement("DATA_APERTURA")]
        public string DataApertura { get; set; }

        [XmlElement("OGGETTO")]
        public string Oggetto { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }

        [XmlElement("UNITA_COMPETENZA")]
        public string UnitaCompetenza { get; set; }

        [XmlElement("UNITA_CREAZIONE")]
        public string UnitaCreazione { get; set; }

    }
}
