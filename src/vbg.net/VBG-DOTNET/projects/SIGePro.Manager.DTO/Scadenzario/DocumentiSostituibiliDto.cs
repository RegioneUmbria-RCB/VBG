using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{


    [DataContract]
    public class DocumentiIstanzaSostituibiliDto
    {
        [DataMember]
        public ListaDocumentiSostituibiliDto DocumentiIntervento { get; set; }

        [DataMember]
        public List<ListaDocumentiSostituibiliDto> DocumentiEndo { get; set; }

        public DocumentiIstanzaSostituibiliDto()
        {
            this.DocumentiEndo = new List<ListaDocumentiSostituibiliDto>();
        }
    }
}
