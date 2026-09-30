using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class DettaglioCommissioneDto
    {
        [DataMember]
        public CommissioneTestataDto Testata { get; set; }
        
        [DataMember]
        public ConvocazioneCommissioneDto[] Convocazioni { get; set; }
        
        [DataMember]
        public PraticaCommissioneBreveDto[] Pratiche { get; set; }
        
        [DataMember]
        public AllegatoCommissioneDao[] Documenti { get; set; }
    }
}
