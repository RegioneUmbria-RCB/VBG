using System.Runtime.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Metadati;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloResponseType")]
    public class DatiProtocolloResponseType
    {
        public DatiProtocolloResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloResponseType()
        {

        }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string Warning { get; set; }

        /// <remarks/>
        [DataMember(Order = 2)]
        public string IdProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public string AnnoProtocollo { get; set; }

        [DataMember(Order = 4)]
        public string DataProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 5)]
        public string NumeroProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public ErroreProtocolloType Errore { get; set; }

        /// <remarks/>
        [DataMember(Order = 7)]
        public string Messaggio { get; set; }

        [DataMember(Order = 8)]
        public int[] CodiciOggettoAllegati { get; set; }
        
        [IgnoreDataMember]
        public List<ProtocolloMetadati> Metadati { get; set; }
    }
}
