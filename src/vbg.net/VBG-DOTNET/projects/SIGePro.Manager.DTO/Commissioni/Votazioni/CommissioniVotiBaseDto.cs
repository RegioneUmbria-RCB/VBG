using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni.Votazioni
{
    [DataContract]
    public class CommissioniVotiBaseDto
    {
        [DataMember]
        public int Id { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
    }
}
