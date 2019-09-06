using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ConfigurazioneTaresBariDto
    {
        [XmlElement(Order = 0)]
        public string UrlServizioAgevolazioniTares { get; set; }

        [XmlElement(Order = 1)]
        public string IdentificativoUtente { get; set; }

        [XmlElement(Order = 2)]
        public string Password { get; set; }

        [XmlElement(Order = 3)]
        public string IndirizzoPec { get; set; }

        [XmlElement(Order = 4)]
        public string UrlServizioAgevolazioniTasi { get; set; }

        [XmlElement(Order = 5)]
        public string UrlServizioAgevolazioniImu { get; set; }

        [XmlElement(Order = 6)]
        public string CodiceFiscaleCafFittizio { get; set; }

        [XmlElement(Order = 7)]
        public string EmailCafFittizio { get; set; }

		[XmlElement(Order = 8)]
		public string UrlServizioFirmaCid { get; set; }
	}
}
