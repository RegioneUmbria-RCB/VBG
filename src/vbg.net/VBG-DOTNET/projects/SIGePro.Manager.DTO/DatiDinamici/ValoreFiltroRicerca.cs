using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
    [DataContract]
    public class ValoreFiltroRicercaDto
    {
        [DataMember]
        public string nome { get; set; }
        [DataMember]
        public string val { get; set; }
    }
}
