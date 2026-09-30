using System;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloLettoResponseType")]
    public class DatiProtocolloLettoResponseType
    {
        public DatiProtocolloLettoResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloLettoResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloLettoResponseType()
        {

        }

        /// <remarks/>
        [DataMember(Order = 0)]
        public string Warning { get; set; }

        /// <summary>
        /// Id del protocollo assegnato dal sistema di protocollazione
        /// </summary>
        [DataMember(Order = 1)]
        public string IdProtocollo { get; set; }

        /// <summary>
        /// Anno del protocollo
        /// </summary>
        [DataMember(Order = 2)]
        public string AnnoProtocollo { get; set; }

        /// <summary>
        /// Numero del protocollo
        /// </summary>
        [DataMember(Order = 3)]
        public string NumeroProtocollo { get; set; }

        /// <summary>
        /// Data del protocollo
        /// </summary>
        [DataMember(Order = 4)]
        public string DataProtocollo { get; set; }

        /// <summary>
        /// Oggetto descrittivo del protocollo
        /// </summary>
        [DataMember(Order = 5)]
        public string Oggetto { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public string Origine { get; set; }

        /// <summary>
        /// Codice della classifica del protocollo. Es. XIII.IV.I oppure un ID
        /// </summary>
        [DataMember(Order = 7)]
        public string Classifica { get; set; }

        /// <summary>
        /// Descrizione della classifica
        /// </summary>
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
        public MittDestOutType[] MittentiDestinatari { get; set; }

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
        public AllegatoResponseType[] Allegati { get; set; }

        /// <remarks/>
        [DataMember(Order = 26)]
        public ErroreProtocolloType Errore { get; set; }

    }
}