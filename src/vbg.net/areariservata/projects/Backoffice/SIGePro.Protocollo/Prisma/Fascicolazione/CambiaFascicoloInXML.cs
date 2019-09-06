
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class CambiaFascicoloInXML
    {
        [XmlElement("PROTOCOLLO_GRUPPO")]
        public ProtocolloGruppoInCambiaFascicoloXml ProtocolloGruppo { get; set; }

        [XmlElement("FASCICOLO_GRUPPO")]
        public FascicoloGruppoInCambiaFascicoloXml FascicoloGruppo { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }

        [XmlElement("CLASS_COD")]
        public string Classifica { get; set; }

        [XmlElement("OGGETTO")]
        public string Oggetto { get; set; }

    }

    public class ProtocolloGruppoInCambiaFascicoloXml
    {
        [XmlElement("ANNO")]
        public string Anno { get; set; }

        [XmlElement("NUMERO")]
        public string Numero { get; set; }

        [XmlElement("TIPO_REGISTRO")]
        public string TipoRegistro { get; set; }
    }

    public class FascicoloGruppoInCambiaFascicoloXml
    {
        [XmlElement("FASCICOLO_ANNO")]
        public string Anno { get; set; }

        [XmlElement("FASCICOLO_NUMERO")]
        public string Numero { get; set; }
    }
}
