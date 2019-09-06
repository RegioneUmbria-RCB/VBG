using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneAccessoAtti.Siena
{
    [Serializable]
    public class PraticaAccessoAtti
    {
        [XmlElement(Order = 0)]
        public string NumeroProtocollo { get; set; }
        [XmlElement(Order = 1)]
        public DateTime? DataProtocollo { get; set; }
        [XmlElement(Order = 2)]
        public int CodiceIstanza { get; set; }
        [XmlElement(Order = 3)]
        public string Localizzazione { get; set; }
        [XmlElement(Order = 4)]
        public string Richiedente { get; set; }
        [XmlElement(Order = 5)]
        public string TipoIntervento { get; set; }
        [XmlElement(Order = 6)]
        public string Oggetto { get; set; }
        [XmlElement(Order = 7)]
        public DateTime DataPresentazione { get; set; }
        [XmlElement(Order = 8)]
        public string StatoLavorazione { get; set; }
        [XmlElement(Order = 9)]
        public string UUID { get; set; }
        [XmlElement(Order = 10)]
        public string NumeroIstanza { get; internal set; }
        [XmlElement(Order = 11)]
        public string SoftwareCodice { get; internal set; }
        [XmlElement(Order = 12)]
        public string SoftwareDescrizione { get; internal set; }
        [XmlElement(Order = 13)]
        public bool MostraDocumentiNonValidi { get; internal set; }
        [XmlElement(Order = 14)]
        public int IdAccessoAtti { get; internal set; }
        [XmlElement(Order = 15)]
        public string CodiceIstanzaAccessoAtti { get; internal set; }
        [XmlElement(Order = 16)]
        public DateTime? DataIstanzaAccessoAtti { get; internal set; }
        [XmlElement(Order = 17)]
        public string DescrizioneAccessoAtti { get; internal set; }
    }
}