using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]

    public class ListaDocumentiSostituibiliDto
    {
        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public List<DocumentoSostituibileMovimentoDto> Documenti { get; set; }

        public ListaDocumentiSostituibiliDto()
        {
            this.Documenti = new List<DocumentoSostituibileMovimentoDto>();
        }
    }
}
