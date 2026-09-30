using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Classificazione
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

        [XmlElement("VALIDA")]
        public string Valida { get; set; }
    }
}
