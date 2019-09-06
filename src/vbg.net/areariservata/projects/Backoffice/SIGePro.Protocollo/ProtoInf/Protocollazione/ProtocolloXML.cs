using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.ProtoInf.Protocollazione
{
    [XmlRoot(Namespace = "", ElementName = "ProtocolloXML", IsNullable = false)]
    public class ProtocolloXML
    {
        [XmlElement(ElementName = "ENTE")]
        public string Ente { get; set; }
        [XmlElement(ElementName = "USER")]
        public string User { get; set; }
        [XmlElement(ElementName = "PASSWORD")]
        public string Password { get; set; }
        [XmlElement(ElementName = "TIPOPROTOCOLLO")]
        public string TipoProtocollo { get; set; }
        [XmlElement(ElementName="CODAMM")]
        public string CodAmm { get; set; }
        [XmlElement(ElementName = "CODAOO")]
        public string CodAoo { get; set; }
        [XmlElement(ElementName = "OGGETTO")]
        public string Oggetto { get; set; }
        [XmlElement(ElementName = "PROPRIETARIO")]
        public string Proprietario { get; set; }
        [XmlElement(ElementName = "PROTOCOLLATORE")]
        public string Protocollatore { get; set; }
        [XmlElement(ElementName = "DATACARICO")]
        public string DataCarico { get; set; }
        [XmlElement(ElementName = "ORACARICO")]
        public string OraCarico { get; set; }
        [XmlElement(ElementName = "RISERVATO")]
        public string Riservato { get; set; }
        [XmlElement(ElementName = "DATISENSIBILI")]
        public string DatiSensibili { get; set; }
        [XmlElement(ElementName = "REPNUM")]
        public string RepNum { get; set; }
        [XmlElement(ElementName = "FASCICOLI")]
        public string Fascicoli { get; set; }
        [XmlElement(ElementName = "POSTA")]
        public string Posta { get; set; }
        [XmlElement(ElementName = "TIPOSTA")]
        public string TiPosta { get; set; }
        [XmlElement(ElementName = "TIPOCON")]
        public string TipoCon { get; set; }
        [XmlElement(ElementName = "NOTE")]
        public string Note { get; set; }
        [XmlElement(ElementName = "NUMERORIFERIMENTO")]
        public string NumeroRiferimento { get; set; }
        [XmlElement(ElementName = "DATARIFERIMENTO")]
        public string DataRiferimento { get; set; }
        [XmlElement(ElementName = "ANNOEMERGENZA")]
        public string AnnoEmergenza { get; set; }
        [XmlElement(ElementName = "NUMEROEMERGENZA")]
        public string NumeroEmergenza { get; set; }
        [XmlElement(ElementName = "DATAEMERGENZA")]
        public string DataEmergenza { get; set; }
        [XmlElement(ElementName = "ORIGINEPROTOCOLLO")]
        public string OrigineProtocollo { get; set; }
    }
}
