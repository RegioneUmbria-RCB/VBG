using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;
using Init.SIGePro.Manager.DTO.DatiDinamici;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
    public class EndoprocedimentoMappatoDto
    {
        [DataMember(Order = 0)]
        public int Id { get; set; }

        [DataMember(Order = 1)]
        public string Descrizione { get; set; }

        [DataMember(Order = 2)]
        public List<SchedaDinamicaDto> Schede { get; set; }

        [DataMember(Order = 3)]
        public List<AllegatoDto> Allegati { get; set; }

        [DataMember(Order = 4)]
        public DateTime? DataUltimaModifica { get; set; }

        public EndoprocedimentoMappatoDto()
        {
            this.Schede = new List<SchedaDinamicaDto>();
            this.Allegati = new List<AllegatoDto>();
        }
    }
}
