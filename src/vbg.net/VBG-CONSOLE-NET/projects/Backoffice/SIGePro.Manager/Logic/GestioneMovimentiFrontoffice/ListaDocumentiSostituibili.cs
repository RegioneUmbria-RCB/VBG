using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    [Serializable]
    public class ListaDocumentiSostituibili
    {
        [XmlElement(Order = 0)]
        public string Descrizione { get; set; }
        
        [XmlElement(Order=1)]
        public List<DocumentoSostituibileMovimentoDto> Documenti { get; set; }

        public ListaDocumentiSostituibili()
        {
            this.Documenti = new List<DocumentoSostituibileMovimentoDto>();
        }
    }
}
