using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneDecodifiche
{
    [Serializable]
    public class DecodificaDTO
    {
        [XmlElement(Order = 0)]
        public string Idcomune { get; set; }
        [XmlElement(Order = 1)]
        public string Tabella { get; set; }
        [XmlElement(Order = 2)]
        public string Chiave { get; set; }
        [XmlElement(Order = 3)]
        public string Valore { get; set; }
        [XmlElement(Order = 4)]
        public bool FlgDisabilitato { get; set; }
    }
}
