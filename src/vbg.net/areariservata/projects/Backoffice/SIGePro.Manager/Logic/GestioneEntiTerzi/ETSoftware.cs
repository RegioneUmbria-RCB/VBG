using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    public class ETSoftware
    {
        [XmlElement(Order=0)]
        public string Descrizione { get; internal set; }
        [XmlElement(Order=1)]
        public string Codice { get; internal set; }
    }
}