using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class PraticaCommissioneEstesaDto
    {
        [DataMember]
        public PraticaCommissioneBreveDto DatiPratica { get; set; }

        [DataMember]
        public IEnumerable<LocalizzazionePraticaCommissioneDto> Localizzazioni { get; set; }

        [DataMember]
        public ListaDocumentiPraticaCommissioneDto Documenti { get; set; } = new ListaDocumentiPraticaCommissioneDto();
    }
}
