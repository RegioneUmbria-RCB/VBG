using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Pagamenti
{
    [Serializable]
    public class ConfigurazionePagamentiEntraNext
    {
        [XmlElement(Order = 0)]
        public string NomeVerticalizzazione { get; set; }

        [XmlElement(Order = 1)]
        public string UrlWs { get; set; }

        [XmlElement(Order = 2)]
        public string IdentificativoConnettore { get; set; }

        [XmlElement(Order = 3)]
        public string CodiceFiscaleEnte { get; set; }

        [XmlElement(Order = 4)]
        public string Versione { get; set; }

        [XmlElement(Order = 5)]
        public string Identificativo { get; set; }

        [XmlElement(Order = 6)]
        public string Username { get; set; }

        [XmlElement(Order = 7)]
        public string PasswordMd5 { get; set; }

        [XmlElement(Order = 8)]
        public string CodiceTipoPagamento { get; set; }
    }
}
