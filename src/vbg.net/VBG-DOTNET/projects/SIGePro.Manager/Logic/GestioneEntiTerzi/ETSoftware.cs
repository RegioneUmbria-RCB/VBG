using System;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    [DataContract]
    public class ETSoftware
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public string Descrizione { get; internal set; }
        [DataMember]
        [XmlElement(Order = 1)]
        public string Codice { get; internal set; }
    }
}