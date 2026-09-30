using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura
{
    [XmlRoot]
    public class RisultatoVisuraListaDto
    {
        [XmlElement(Order = 0)]
        public List<VisuraListItemDto> ListaPratiche { get; set; }

        [XmlElement(Order = 1)]
        public bool? LimiteRecordsSuperato { get; set; }

        public RisultatoVisuraListaDto()
        {
            this.ListaPratiche = new List<VisuraListItemDto>();
        }
    }
}
