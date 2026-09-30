using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
    public class AllegatiPerEndoprocedimentoDto
    {
        [DataMember]
        public int CodiceInventario { get; set; }

        [DataMember]
        public List<AllegatoPerAreaRiservataDto> Allegati { get; set; }
    }
}
