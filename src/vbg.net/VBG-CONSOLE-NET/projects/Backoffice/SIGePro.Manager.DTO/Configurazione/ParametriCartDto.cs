using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ParametriCartDto
    {
        [XmlElement(Order=0)]
        public string UrlAccettatore { get; set; }
    }
}
