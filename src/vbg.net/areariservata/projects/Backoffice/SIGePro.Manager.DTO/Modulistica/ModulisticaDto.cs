using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Modulistica
{
    [XmlRoot]
    public class ModulisticaDto
    {
        [XmlElement(Order=0)]
        public int Codice { get; set; }

        [XmlElement(Order = 1)]
        public string Titolo { get; set; }

        [XmlElement(Order = 2)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 3)]
        public string Url { get; set; }

        [XmlElement(Order = 4)]
        public int? CodiceOggetto { get; set; }

        [XmlElement(Order = 5)]
        public string NomeFile { get; set; }

        [XmlElement(Order = 6)]
        public int Ordine { get; set; }
    }
}
