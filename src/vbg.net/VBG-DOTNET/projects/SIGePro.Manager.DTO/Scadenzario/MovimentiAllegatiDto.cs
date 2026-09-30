using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class MovimentiAllegatiDto
    {
        [DataMember]
        public int? CodiceOggetto { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
        [DataMember]
        public string Note { get; set; }
    }
}