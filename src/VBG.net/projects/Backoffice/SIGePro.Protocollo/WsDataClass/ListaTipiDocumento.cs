using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaTipiDocumentoResponseType")]
    public class ListaTipiDocumento
    {
        public ListaTipiDocumento(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaTipiDocumento(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }


        public ListaTipiDocumento()
        {

        }

        [DataMember(Order = 0)]
        public ListaTipiDocumentoDocumento[] Documento { get; set; }

        /// <remarks/>
        [DataMember(Order=1)]
        public ErroreProtocollo Errore { get; set; }

    }
}
