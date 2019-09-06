using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Classificazione
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class ClassificheInXML
    {
        [XmlElement("CLASS_COD")]
        public string CodiceClassifica { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }

        [XmlElement("CODICE_AMMINISTRAZIONE")]
        public string CodiceAmministrazione { get; set; }

        [XmlElement("CODICE_AOO")]
        public string CodiceAoo { get; set; }
    }
}
