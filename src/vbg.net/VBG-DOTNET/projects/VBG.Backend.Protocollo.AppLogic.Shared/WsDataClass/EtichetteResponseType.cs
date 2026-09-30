using System.Xml.Serialization;
using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EtichetteResponseType")]
    public class EtichetteResponseType
    {
        public EtichetteResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public EtichetteResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public EtichetteResponseType()
        {

        }

        /// <remarks/>
        [DataMember(Order = 0)]
        public string Warning { get; set; }

        [DataMember(Order = 1)]
        public string IdEtichetta { get; set; }

        [DataMember(Order = 2)]
        public ErroreProtocolloType Errore { get; set; } 
    }
}
