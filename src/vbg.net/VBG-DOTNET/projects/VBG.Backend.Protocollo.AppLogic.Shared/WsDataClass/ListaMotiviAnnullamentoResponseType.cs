using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaMotiviAnnullamentoResponseType")]
    public class ListaMotiviAnnullamentoResponseType
    {
        public ListaMotiviAnnullamentoResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaMotiviAnnullamentoResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public ListaMotiviAnnullamentoResponseType()
        {

        }

        [DataMember(Order = 0)]
        public ListaMotiviAnnullamentoMotivoAnnullamentoType[] MotivoAnnullamento { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public ErroreProtocolloType Errore { get; set; } 
    }
}
