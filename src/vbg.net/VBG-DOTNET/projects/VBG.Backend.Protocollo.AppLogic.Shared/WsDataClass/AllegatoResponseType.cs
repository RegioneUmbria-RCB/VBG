using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "AllegatoResponseType")]
    public class AllegatoResponseType
    {
        public AllegatoResponseType(Exception ex)
        {
            this.Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public AllegatoResponseType(string messaggio, string stackTrace)
        {
            this.Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public AllegatoResponseType()
        {
        }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string Serial { get; set; }

        [DataMember(Order = 2)]
        public string TipoFile { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public string ContentType { get; set; }

        /// <remarks/>
        [DataMember(Order = 4)]
        public Byte[] Image { get; set; }

        /// <remarks/>
        [DataMember(Order = 5)]
        public string Commento { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public string IDBase { get; set; }

        /// <remarks/>
        [DataMember(Order = 7)]
        public string Versione { get; set; }

        [DataMember(Order = 8)]
        public ErroreProtocolloType Errore { get; set; }

        [DataMember(Order = 9)]
        public string Uo { get; set; }

        [DataMember(Order = 10)]
        public string Ruolo { get; set; }
    }
}
