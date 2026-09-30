using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "RegistrazioneResponseType")]
    public class RegistrazioneResponse
    {
        public RegistrazioneResponse()
        {

        }

        public RegistrazioneResponse(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };    
        }

        [DataMember(Order = 1)]
        public ErroreProtocolloType Errore { get; set; } 
    }
}
