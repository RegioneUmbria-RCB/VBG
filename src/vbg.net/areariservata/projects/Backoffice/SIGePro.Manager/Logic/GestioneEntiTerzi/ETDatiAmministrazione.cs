using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    [Serializable]
    public class ETDatiAmministrazione
    {
        [XmlElement(Order = 1)]
        public int? Codice { get; internal set; }
        [XmlElement(Order = 2)]
        public string Descrizione { get; internal set; }
        [XmlElement(Order = 3)]
        public string PartitaIva { get; internal set; }
    }
}