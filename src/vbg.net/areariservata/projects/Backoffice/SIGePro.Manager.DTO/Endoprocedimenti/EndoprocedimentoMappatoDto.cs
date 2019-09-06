using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using Init.SIGePro.Manager.DTO.DatiDinamici;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [Serializable]
    public class EndoprocedimentoMappatoDto
    {
        [XmlElement(Order = 0)]
        public int Id { get; set; }

        [XmlElement(Order = 1)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 2)]
        public List<SchedaDinamicaDto> Schede { get; set; }

        [XmlElement(Order = 3)]
        public List<AllegatoDto> Allegati { get; set; }
        [XmlElement(Order = 4)]
        public DateTime? DataUltimaModifica { get; set; }

        public EndoprocedimentoMappatoDto()
        {
            this.Schede = new List<SchedaDinamicaDto>();
            this.Allegati = new List<AllegatoDto>();
        }
    }
}
