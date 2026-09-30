using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class CommissioneTestataDto
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Numero { get; set; }

        [DataMember]
        public string Data { get; set; }

        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public string Note { get; set; }

        [DataMember]
        public string Tipologia { get; set; }

        [DataMember]
        public ConvocazioneCommissioneDto Convocazione { get; set; }

        [DataMember]
        public string OraInizio { get; set; }

        [DataMember]
        public string OraFine { get; set; }

        [DataMember]
        public bool Asincrona { get; set; }

    }
}
