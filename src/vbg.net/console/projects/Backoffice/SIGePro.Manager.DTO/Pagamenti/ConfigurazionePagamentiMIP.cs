using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Pagamenti
{
    [Serializable]
    public class ConfigurazionePagamentiMIP
    {
        [XmlElement(Order=0)]
        public string NomeVerticalizzazione { get; set; }
        [XmlElement(Order = 1)]
        public string WindowMinutes { get; set; }
        [XmlElement(Order = 2)]
        public string UrlServerPagamento { get; set; }
        [XmlElement(Order = 3)]
        public string PortaProxy { get; set; }
        [XmlElement(Order = 4)]
        public string PortaleID { get; set; }
        [XmlElement(Order = 5)]
        public string EmailPortale { get; set; }
        [XmlElement(Order = 6)]
        public string IndirizzoProxy { get; set; }
        [XmlElement(Order = 7)]
        public string IdServizio { get; set; }
        [XmlElement(Order = 8)]
        public string IdentificativoComponente { get; set; }
        [XmlElement(Order = 9)]
        public string PasswordChiamate { get; set; }
        [XmlElement(Order = 10)]
        public string CodiceTipoPagamento { get; set; }
        [XmlElement(Order = 11)]
        public string IntestazioneRicevuta { get; set; }
        [XmlElement(Order = 12)]
        public string CodiceUtente { get; set; }
        [XmlElement(Order = 13)]
        public string CodiceEnte { get; set; }
        [XmlElement(Order = 14)]
        public string TipoUfficio { get; set; }
        [XmlElement(Order = 15)]
        public string CodiceUfficio { get; set; }
        [XmlElement(Order = 16)]
        public string TipologiaServizio { get; set; }
        [XmlElement(Order = 17)]
        public string ChiaveIV { get; set; }
        [XmlElement(Order = 18)]
        public string UrlNotifica { get; set; }


    }
}
