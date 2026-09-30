using System;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    [DataContract]
    public class ETPraticaEnteTerzo
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public string NumeroProtocollo { get; set; }
        [DataMember]
        [XmlElement(Order = 1)]
        public string DataProtocollo { get; set; }
        [DataMember]
        [XmlElement(Order = 2)]
        public int CodiceIstanza { get; set; }
        [DataMember]
        [XmlElement(Order = 3)]
        public string Localizzazione { get; set; }
        [DataMember]
        [XmlElement(Order = 4)]
        public string Richiedente { get; set; }
        [DataMember]
        [XmlElement(Order = 5)]
        public string TipoIntervento { get; set; }
        [DataMember]
        [XmlElement(Order = 6)]
        public string Oggetto { get; set; }
        [DataMember]
        [XmlElement(Order = 7)]
        public string DataPresentazione { get; set; }
        [DataMember]
        [XmlElement(Order = 8)]
        public string StatoLavorazione { get; set; }
        [DataMember]
        [XmlElement(Order = 9)]
        public string UUID { get; set; }
        [DataMember]
        [XmlElement(Order = 10)]
        public string NumeroIstanza { get; internal set; }

        [DataMember]
        [XmlElement(Order = 11)]
        public string SoftwareCodice { get; internal set; }
        [DataMember]
        [XmlElement(Order = 12)]
        public string SoftwareDescrizione { get; internal set; }
    }
}