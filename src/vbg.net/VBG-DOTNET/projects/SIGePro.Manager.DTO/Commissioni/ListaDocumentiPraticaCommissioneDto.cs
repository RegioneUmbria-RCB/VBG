using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class ListaDocumentiPraticaCommissioneDto
    {
        [DataMember]
        public IEnumerable<DocumentoPraticaCommissioneDto> Istanza { get; set; }

        [DataMember]
        public IEnumerable<DocumentoPraticaCommissioneDto> Endoprocedimenti { get; set; }

        [DataMember]
        public IEnumerable<DocumentoPraticaCommissioneDto> Movimenti { get; set; }
    }
}
