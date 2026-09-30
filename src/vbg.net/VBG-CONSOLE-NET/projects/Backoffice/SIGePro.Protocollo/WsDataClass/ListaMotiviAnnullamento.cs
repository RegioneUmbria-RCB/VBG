using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaMotiviAnnullamentoResponseType")]
    public class ListaMotiviAnnullamento
    {
        public ListaMotiviAnnullamento(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaMotiviAnnullamento(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public ListaMotiviAnnullamento()
        {

        }

        [DataMember(Order = 0)]
        public ListaMotiviAnnullamentoMotivoAnnullamento[] MotivoAnnullamento { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public ErroreProtocollo Errore { get; set; } 
    }
}
