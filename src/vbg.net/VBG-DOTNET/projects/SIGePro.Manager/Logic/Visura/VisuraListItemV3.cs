using System;
using System.Linq;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.Visura
{
    [DataContract]
    public class RisultatoVisuraPraticaV3
    {
        [XmlElement(Order = 0)]
        [DataMember]
        public bool LimiteRecordsSuperato { get; internal set; } = true;
        [XmlElement(Order = 1)]
        [DataMember]
        public VisuraListItemV3[] Pratiche { get; internal set; } = Enumerable.Empty<VisuraListItemV3>().ToArray();
        [XmlElement(Order = 2)]
        [DataMember]
        public int PageSize { get; set; } = 0;
        [XmlElement(Order = 3)]
        [DataMember]
        public int CurrentPage { get; set; } = 0;
        [XmlElement(Order = 4)]
        [DataMember]
        public int TotalPages { get; set; } = 0;
        [XmlElement(Order = 5)]
        [DataMember]
        public int RecordCount { get; set; } = 0;
    }

    [DataContract]
    public class VisuraListItemV3
    {
        [XmlElement(Order = 0)]
        [DataMember]
        public int CodiceIstanza { get; internal set; }
        [XmlElement(Order = 1)]
        [DataMember]
        public string Uuid { get; internal set; }
        [XmlElement(Order = 2)]
        [DataMember]
        public string Oggetto { get; internal set; }
        [XmlElement(Order = 3)]
        [DataMember]
        public string Progressivo { get; internal set; }
        [XmlElement(Order = 4)]
        [DataMember]
        public string TipoIntervento { get; internal set; }
        [XmlElement(Order = 5)]
        [DataMember]
        public string NumeroProtocollo { get; internal set; }
        [XmlElement(Order = 6)]
        [DataMember]
        public string Operatore { get; internal set; }
        [XmlElement(Order = 7)]
        [DataMember]
        public string CodiceArea { get; internal set; }
        [XmlElement(Order = 8)]
        [DataMember]
        public string Civico { get; internal set; }
        [XmlElement(Order = 9)]
        [DataMember]
        public string Stato { get; internal set; }
        [XmlElement(Order = 10)]
        [DataMember]
        public DateTime DataPresentazione { get; internal set; }
        [XmlElement(Order = 11)]
        [DataMember]
        public string Subalterno { get; internal set; }
        [XmlElement(Order = 12)]
        [DataMember]
        public string NumeroIstanza { get; internal set; }
        [XmlElement(Order = 13)]
        [DataMember]
        public string TipoProcedura { get; internal set; }
        [XmlElement(Order = 14)]
        [DataMember]
        public string LocalizzazioneConCivico { get; internal set; }
        [XmlElement(Order = 15)]
        [DataMember]
        public DateTime? DataProtocollo { get; internal set; }
        [XmlElement(Order = 16)]
        [DataMember]
        public string Particella { get; internal set; }
        [XmlElement(Order = 17)]
        [DataMember]
        public string Software { get; internal set; }
        [XmlElement(Order = 18)]
        [DataMember]
        public string Richiedente { get; internal set; }
        [XmlElement(Order = 19)]
        [DataMember]
        public string Foglio { get; internal set; }
        [XmlElement(Order = 20)]
        [DataMember]
        public string TipoCatasto { get; internal set; }
        [XmlElement(Order = 21)]
        [DataMember]
        public string Azienda { get; internal set; }
        [XmlElement(Order = 22)]
        [DataMember]
        public string PosizioneArchivio { get; internal set; }
    }
}
