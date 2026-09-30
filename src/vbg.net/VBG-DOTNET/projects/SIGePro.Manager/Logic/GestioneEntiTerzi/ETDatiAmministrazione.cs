using System;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    [DataContract]
    public class ETDatiAmministrazione
    {
        [DataMember]
        [XmlElement(Order = 1)]
        public int? Codice { get; internal set; }
        [DataMember]
        [XmlElement(Order = 2)]
        public string Descrizione { get; internal set; }
        [DataMember]
        [XmlElement(Order = 3)]
        public string PartitaIva { get; internal set; }
    }
}