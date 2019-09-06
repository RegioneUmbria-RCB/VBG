using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ParametriFvgSolDto
    {
        [XmlElement(Order = 0)]
        public string WebServiceUrl { get; set; }
        [XmlElement(Order = 1)]
        public string WebServiceUsername { get; set; }
        [XmlElement(Order = 2)]
        public string WebServicePassword { get; set; }
    }
}
