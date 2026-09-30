using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ConfigurazioneSitLDP
    {
        [XmlElement(Order = 1)]
        public string UrlPresentazioneDomandaLdp { get; set; }
        [XmlElement(Order = 2)]
        public string UrlServiziDomandaLdp { get; set; }

        [XmlElement(Order = 3)]
        public string Username{ get; set; }

        [XmlElement(Order = 4)]
        public string Password{ get; set; }

        [XmlElement(Order = 5)]
        public string UrlGenerazionePdfDomanda { get; set; }
    }
}
