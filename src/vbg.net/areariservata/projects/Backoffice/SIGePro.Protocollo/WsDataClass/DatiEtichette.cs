using System.Xml.Serialization;
using System;
using Init.SIGePro.Protocollo.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EtichetteResponseType")]
    public class DatiEtichette
    {
        public DatiEtichette(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiEtichette(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiEtichette()
        {

        }

        /// <remarks/>
        [DataMember(Order = 0)]
        public string Warning { get; set; }

        [DataMember(Order = 1)]
        public string IdEtichetta { get; set; }

        [DataMember(Order = 2)]
        public ErroreProtocollo Errore { get; set; } 
    }
}
