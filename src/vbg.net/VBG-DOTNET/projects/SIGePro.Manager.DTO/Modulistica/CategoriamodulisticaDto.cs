using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Modulistica
{
    [XmlRoot]
    public class CategoriaModulisticaDto
    {
        [XmlElement(Order = 0)]
        public int Codice { get; set; }

        [XmlElement(Order = 1)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 2)]
        public List<ModulisticaDto> Modulistica{ get; set; }

        public CategoriaModulisticaDto()
        {
            this.Modulistica = new List<ModulisticaDto>();
        }
    }
}
