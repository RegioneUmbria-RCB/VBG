using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    [Serializable]
    public class DocumentiIstanzaSostituibili
    {
        [XmlElement(Order = 0)]
        public ListaDocumentiSostituibili DocumentiIntervento { get; set; }

        [XmlElement(Order = 1)]
        public List<ListaDocumentiSostituibili> DocumentiEndo { get; set; }

        public DocumentiIstanzaSostituibili()
        {
            this.DocumentiEndo = new List<ListaDocumentiSostituibili>();
        }
    }
}
