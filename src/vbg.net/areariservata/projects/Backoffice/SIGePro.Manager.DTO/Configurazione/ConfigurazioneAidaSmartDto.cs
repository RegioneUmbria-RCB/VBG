using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ConfigurazioneAidaSmartDto
    {
        private string _baseUrl = "";

        [XmlElement(Order = 0)]
        public string CrossLoginUrl { get; set; }
        [XmlElement(Order = 1)]
        public string UrlNuovaDomanda { get; set; }
        [XmlElement(Order = 2)]
        public string UrlIstanzeInSospeso { get; set; }
    }
}
