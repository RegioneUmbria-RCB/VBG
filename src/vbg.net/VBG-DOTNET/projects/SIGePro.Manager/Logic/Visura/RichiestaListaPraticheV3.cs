using System;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.Visura
{
    [DataContract]
    public class FiltroPersonaAventeTitoloDiVisura
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public string CodiceFiscale { get; set; }
        /*
        [DataMember][XmlElement(Order = 1)]
        public bool CercaNeiSoggettiCollegati { get; set; }
        */
    }

    [DataContract]
    public class FiltriDatiCatastali
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public string TipoCatasto { get; set; }
        [DataMember]
        [XmlElement(Order = 1)]
        public string Foglio { get; set; }
        [DataMember]
        [XmlElement(Order = 2)]
        public string Particella { get; set; }
        [DataMember]
        [XmlElement(Order = 3)]
        public string Subalterno { get; set; }
        [XmlIgnore]
        public bool AlmenoUnDatoSpecificato => !String.IsNullOrEmpty(this.TipoCatasto) || !String.IsNullOrEmpty(this.Foglio) || !String.IsNullOrEmpty(this.Particella) || !string.IsNullOrEmpty(this.Subalterno);
    }

    [DataContract]
    public class FiltroPeriodoPresentazione
    {
        public class DateRange
        {
            public readonly DateTime Min;
            public readonly DateTime Max;

            public DateRange(DateTime min, DateTime max)
            {
                this.Min = min;
                this.Max = max;
            }
        }

        [DataMember]
        [XmlElement(Order = 0)]
        public int Anno { get; set; } = 0;
        [DataMember]
        [XmlElement(Order = 1)]
        public int? Mese { get; set; }

        public bool ContainsValidValues => this.Anno > 0 || this.Mese.GetValueOrDefault(0) > 0;

        public DateRange ToDateRange()
        {
            if (this.Mese.HasValue && this.Anno == 0)
            {
                this.Anno = DateTime.Now.Year;
            }

            var min = new DateTime(this.Anno, this.Mese.GetValueOrDefault(1), 1, 0, 0, 0);
            var max = new DateTime(this.Anno, this.Mese.GetValueOrDefault(12), DateTime.DaysInMonth(this.Anno, this.Mese.GetValueOrDefault(12)), 23, 59, 59);

            return new DateRange(min, max);
        }
    }

    [DataContract]
    public class FiltroIndirizzo
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public int CodiceStradario { get; set; }
        [DataMember]
        [XmlElement(Order = 1)]
        public string Civico { get; set; }
    }

    [DataContract]
    public class FiltroDatiProtocollo
    {
        [DataMember]
        [XmlElement(Order = 0)]
        public DateTime? Data { get; set; }

        [DataMember]
        [XmlElement(Order = 1)]
        public string Numero { get; set; }

        [XmlIgnore]
        public bool AlmenoUnDatoSpecificato => this.Data.HasValue || !String.IsNullOrEmpty(this.Numero);
    }

    [DataContract]
    public enum OrderByDateEnum
    {
        [EnumMember]
        Descending,
        [EnumMember]
        Ascending
    }

    [DataContract]
    public class RichiestaListaPraticheV3
    {
        [DataMember]
        [XmlElement(Order = 1)]
        public string Software { get; set; }
        [DataMember]
        [XmlElement(Order = 2)]
        public string NumeroIstanza { get; set; }
        [DataMember]
        [XmlElement(Order = 3)]
        public FiltriDatiCatastali DatiCatastali { get; set; }
        [DataMember]
        [XmlElement(Order = 4)]
        public FiltroPersonaAventeTitoloDiVisura PersonaAventeTitolo { get; set; }
        [DataMember]
        [XmlElement(Order = 5)]
        public FiltroPeriodoPresentazione PeriodoPresentazione { get; set; }
        [DataMember]
        [XmlElement(Order = 6)]
        public FiltroIndirizzo Indirizzo { get; set; }
        [DataMember]
        [XmlElement(Order = 7)]
        public FiltroDatiProtocollo DatiProtocollo { get; set; }
        [DataMember]
        [XmlElement(Order = 8)]
        public int? CodiceIntervento { get; set; }
        [DataMember]
        [XmlElement(Order = 9)]
        public string NumeroAutorizzazione { get; set; }
        [DataMember]
        [XmlElement(Order = 10)]
        public string StatoPratica { get; set; }
        [DataMember]
        [XmlElement(Order = 11)]
        public string Oggetto { get; set; }
        [DataMember]
        [XmlElement(Order = 13)]
        public string NomeOCfRichiedente { get; set; }
        [DataMember]
        [XmlElement(Order = 14)]
        public string Fabbricato { get; set; }
        [DataMember]
        [XmlElement(Order = 15)]
        public string PosizioneArchivio { get; set; }

        [DataMember]
        [XmlElement(Order = 16)]
        public OrderByDateEnum OrderByDate { get; set; } = OrderByDateEnum.Descending;
    }
}
