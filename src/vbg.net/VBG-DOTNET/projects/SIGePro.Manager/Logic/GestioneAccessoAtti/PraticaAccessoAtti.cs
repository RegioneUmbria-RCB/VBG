using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneAccessoAtti
{
    [DataContract]
    public class PraticaAccessoAtti
    {
        [DataMember(Order = 0)]
        public string NumeroProtocollo { get; set; }
        [DataMember(Order = 1)]
        public DateTime? DataProtocollo { get; set; }
        [DataMember(Order = 2)]
        public int CodiceIstanza { get; set; }
        [DataMember(Order = 3)]
        public string Localizzazione { get; set; }
        [DataMember(Order = 4)]
        public string Richiedente { get; set; }
        [DataMember(Order = 5)]
        public string TipoIntervento { get; set; }
        [DataMember(Order = 6)]
        public string Oggetto { get; set; }
        [DataMember(Order = 7)]
        public DateTime DataPresentazione { get; set; }
        [DataMember(Order = 8)]
        public string StatoLavorazione { get; set; }
        [DataMember(Order = 9)]
        public string UUID { get; set; }
        [DataMember(Order = 10)]
        public string NumeroIstanza { get; internal set; }
        [DataMember(Order = 11)]
        public string SoftwareCodice { get; internal set; }
        [DataMember(Order = 12)]
        public string SoftwareDescrizione { get; internal set; }
        [DataMember(Order = 13)]
        public bool MostraDocumentiNonValidi { get; internal set; }
        [DataMember(Order = 14)]
        public int IdAccessoAtti { get; internal set; }
        [DataMember(Order = 15)]
        public string CodiceIstanzaAccessoAtti { get; internal set; }
        [DataMember(Order = 16)]
        public DateTime? DataIstanzaAccessoAtti { get; internal set; }
        [DataMember(Order = 17)]
        public string DescrizioneAccessoAtti { get; internal set; }
    }
}
