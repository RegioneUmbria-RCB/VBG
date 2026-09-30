using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaTipiDocumentoResponseType")]
    public class ListaTipiDocumentoResponseType
    {
        public ListaTipiDocumentoResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaTipiDocumentoResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }


        public ListaTipiDocumentoResponseType()
        {

        }

        [DataMember(Order = 0)]
        public ListaTipiDocumentoDocumentoType[] Documento { get; set; }

        /// <remarks/>
        [DataMember(Order=1)]
        public ErroreProtocolloType Errore { get; set; }

    }
}
