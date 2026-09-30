using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "CreaUnitaDocumentaleResponseType")]
    public class CreaUnitaDocumentaleResponseType
    {
        public CreaUnitaDocumentaleResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public CreaUnitaDocumentaleResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public CreaUnitaDocumentaleResponseType()
        {

        }

        [DataMember(Order = 0)]
        public string UnitaDocumentale { get; set; }

        [DataMember(Order = 1)]
        public ErroreProtocolloType Errore { get; set; } 

    }
}
