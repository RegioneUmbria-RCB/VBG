using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class PrametriTriesteAccessoAtti
    {
        [XmlElement(Order = 1)]
        public string UrlTrasferimentoControllo { get; set; }
        [XmlElement(Order = 2)]
        public string UrlWebService { get; set; }

    }
}
