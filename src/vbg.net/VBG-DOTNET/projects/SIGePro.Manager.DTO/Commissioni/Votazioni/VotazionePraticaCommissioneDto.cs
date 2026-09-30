using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni.Votazioni
{
    [DataContract]
    public class VotazionePraticaCommissioneDto
    {
        [DataMember]
        public bool RichiedeFirmaDigitale { get; set; }

        [DataMember]
        public VotoPraticaCommissioneDto Voto { get; set; }
    }
}
