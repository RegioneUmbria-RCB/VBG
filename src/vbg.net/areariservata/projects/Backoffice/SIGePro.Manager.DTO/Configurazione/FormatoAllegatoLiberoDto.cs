using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class FormatoAllegatoLiberoDto
    {
        [XmlElement(Order = 1)]
        public int Id { get;  set; }
        [XmlElement(Order = 2)]
        public string Formato { get;  set; }
        [XmlElement(Order = 3)]
        public int DimensioneMaxPagina { get;  set; }
    }
}
