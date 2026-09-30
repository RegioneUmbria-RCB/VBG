using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class ConvocazioneCommissioneDto
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Data { get; set; }

        [DataMember]
        public string Ora { get; set; }
    }
}
