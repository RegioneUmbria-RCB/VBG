using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione")]
    public class ListaTipiClassifica
    {
        public ListaTipiClassifica(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaTipiClassifica(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public ListaTipiClassifica()
        {

        }

        [DataMember(Order = 0)]
        public ListaTipiClassificaClassifica[] Classifica { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public ErroreProtocollo Errore { get; set; }
    }
}
