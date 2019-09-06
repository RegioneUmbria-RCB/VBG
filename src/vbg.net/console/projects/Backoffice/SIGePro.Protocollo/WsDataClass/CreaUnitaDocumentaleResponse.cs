using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "CreaUnitaDocumentaleResponseType")]
    public class CreaUnitaDocumentaleResponse
    {
        public CreaUnitaDocumentaleResponse(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public CreaUnitaDocumentaleResponse(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public CreaUnitaDocumentaleResponse()
        {

        }

        [DataMember(Order = 0)]
        public string UnitaDocumentale { get; set; }

        [DataMember(Order = 1)]
        public ErroreProtocollo Errore { get; set; } 

    }
}
