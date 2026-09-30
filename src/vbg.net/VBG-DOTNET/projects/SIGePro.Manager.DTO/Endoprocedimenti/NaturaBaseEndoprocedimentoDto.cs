using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
    public class NaturaBaseEndoprocedimentoDto
    {
        [DataMember(Order = 1)]
        public string Natura { get; set; }
    }
}
