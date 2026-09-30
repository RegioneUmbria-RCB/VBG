using System;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    [DataContract]
    public class ETFiltriPraticheEntiTerzi
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public int CodiceAnagrafe { get; set; }
        [DataMember]
        [XmlElement(Order = 10)]
        public DateTime DallaData { get; set; }
        [DataMember]
        [XmlElement(Order = 20)]
        public DateTime AllaData { get; set; }
        [DataMember]
        [XmlElement(Order = 30)]
        public string NumeroProtocollo { get; set; }
        [DataMember]
        [XmlElement(Order = 40)]
        public string NumeroIstanza { get; set; }
        [DataMember]
        [XmlElement(Order = 50)]
        public string Modulo { get; set; }
        [DataMember]
        [XmlElement(Order = 60)]
        public bool? Elaborata { get; set; }
    }
}