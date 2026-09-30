using System;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloLettoResponseType")]
    public class DatiProtocolloLetto
    {
        public DatiProtocolloLetto(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloLetto(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloLetto()
        {

        }

        /// <remarks/>
        [DataMember(Order = 0)]
        public string Warning { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string IdProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 2)]
        public string AnnoProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public string NumeroProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 4)]
        public string DataProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 5)]
        public string Oggetto { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public string Origine { get; set; }

        /// <remarks/>
        [DataMember(Order = 7)]
        public string Classifica { get; set; }

        /// <remarks/>
        [DataMember(Order = 8)]
        public string Classifica_Descrizione { get; set; }

        /// <remarks/>
        [DataMember(Order = 9)]
        public string TipoDocumento { get; set; }

        /// <remarks/>
        [DataMember(Order = 10)]
        public string TipoDocumento_Descrizione { get; set; }

        /// <remarks/>
        [DataMember(Order = 11)]
        public string MittenteInterno { get; set; }

        /// <remarks/>
        [DataMember(Order = 12)]
        public string MittenteInterno_Descrizione { get; set; }

        /// <remarks/>
        [DataMember(Order = 13)]
        public string InCaricoA { get; set; }

        /// <remarks/>
        [DataMember(Order = 14)]
        public string InCaricoA_Descrizione { get; set; }

        /// <remarks/>
        [DataMember(Order = 15)]
        public string DocAllegati { get; set; }

        /// <remarks/>
        [DataMember(Order = 16)]
        public string Annullato { get; set; }

        /// <remarks/>
        [DataMember(Order = 17)]
        public string MotivoAnnullamento { get; set; }

        /// <remarks/>
        [DataMember(Order = 18)]
        public string DataAnnullamento { get; set; }

        //[XmlArray("MittentiDestiantari", Order=19), XmlArrayItem("MittenteDestinatario", IsNullable = false)]
        [DataMember(Order = 19)]
        public MittDestOut[] MittentiDestinatari { get; set; }

        /// <remarks/>
        [DataMember(Order = 20)]
        public string NumeroPratica { get; set; }

        /// <remarks/>
        [DataMember(Order = 21)]
        public string AnnoNumeroPratica { get; set; }

        /// <remarks/>
        [DataMember(Order = 22)]
        public string DataInserimento { get; set; }

        /// <remarks/>
        [DataMember(Order = 23)]
        public string NumeroProtocolloMittente { get; set; }

        /// <remarks/>
        [DataMember(Order = 24)]
        public string DataProtocolloMittente { get; set; }

        [DataMember(Order = 25)]
        public AllOut[] Allegati { get; set; }

        /// <remarks/>
        [DataMember(Order = 26)]
        public ErroreProtocollo Errore { get; set; }

    }
}