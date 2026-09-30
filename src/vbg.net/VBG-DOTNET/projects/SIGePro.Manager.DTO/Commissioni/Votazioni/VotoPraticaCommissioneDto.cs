using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni.Votazioni
{
    [DataContract]
    public class VotoPraticaCommissioneDto
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public int? CodiceParere { get; set; }

        [DataMember]
        public string DescrizioneParere { get; set; }

        [DataMember]
        public string Note { get; set; }

        [DataMember]
        public int? CodiceOggetto { get; set; }

        [DataMember]
        public string NomeFile { get; set; }

        [DataMember]
        public string NumeroProtocollo { get; set; }

        [DataMember]
        public string DataProtocollo { get; set; }
    }
}
