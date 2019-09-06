using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    public class ETFiltriPraticheEntiTerzi
    {
        [XmlElement(Order = 0)]
        public int CodiceAnagrafe { get; set; }
        [XmlElement(Order = 10)]
        public DateTime DallaData { get; set; }
        [XmlElement(Order = 20)]
        public DateTime AllaData { get; set; }
        [XmlElement(Order = 30)]
        public string NumeroProtocollo { get; set; }
        [XmlElement(Order = 40)]
        public string NumeroIstanza { get; set; }
        [XmlElement(Order = 50)]
        public string Modulo { get; set; }
        [XmlElement(Order = 60)]
        public bool? Elaborata { get; set; }
    }
}