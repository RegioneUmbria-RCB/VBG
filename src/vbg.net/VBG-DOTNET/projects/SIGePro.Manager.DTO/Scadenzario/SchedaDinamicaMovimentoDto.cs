using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class SchedaDinamicaMovimentoDto
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Titolo { get; set; }

        [DataMember]
        public List<ValoreDatoDinamicoMovimentoDto> Valori { get; set; }

        [DataMember]
        public List<int> IdCampiContenuti { get; set; }

        public SchedaDinamicaMovimentoDto()
        {
            this.Valori = new List<ValoreDatoDinamicoMovimentoDto>();
        }
    }
}