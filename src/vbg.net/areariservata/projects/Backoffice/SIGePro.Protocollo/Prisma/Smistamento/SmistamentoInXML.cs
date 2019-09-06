using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Smistamento
{
    [XmlRoot(Namespace = "", ElementName = "Segnatura", IsNullable = false)]
    public class SmistamentoInXML
    {
        [XmlElement("Intestazione")]
        public IntestazioneInXml Intestazione { get; set; }

        [XmlElement("ApplicativoProtocollo")]
        public ApplicativoProtocolloInXml ApplicativoProtocollo { get; set; }
    }

    public class IntestazioneInXml
    {
        [XmlElement("Identificatore")]
        public IdentificatoreInXml Identificatore { get; set; }

    }

    public class IdentificatoreInXml
    {
        [XmlElement("CodiceAmministrazione")]
        public string CodiceAmministrazione { get; set; }

        [XmlElement("CodiceAOO")]
        public string CodiceAOO { get; set; }

        [XmlElement("NumeroProtocollo")]
        public string NumeroProtocollo { get; set; }

        [XmlElement("AnnoProtocollo")]
        public string AnnoProtocollo { get; set; }

        [XmlElement("TipoRegistroProtocollo")]
        public string TipoRegistroProtocollo { get; set; }
    }

    public class ApplicativoProtocolloInXml
    {
        [XmlElement("Parametro")]
        public ParametroInXml[] Parametro;

        /// <remarks/>
        [XmlAttribute("nome")]
        public string Nome;
    }

    public class ParametroInXml
    {
        [XmlAttribute("nome")]
        public string Nome;

        /// <remarks/>
        [XmlAttribute("valore")]
        public string Valore;
    }
}
