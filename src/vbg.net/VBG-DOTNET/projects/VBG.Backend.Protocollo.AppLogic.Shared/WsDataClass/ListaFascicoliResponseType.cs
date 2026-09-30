using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaFascicoliResponseType")]
    public class ListaFascicoliResponseType
    {
        public ListaFascicoliResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaFascicoliResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }
        
        public ListaFascicoliResponseType()
        {

        }

        [DataMember(Order = 0)]
        public DatiFascType[] Fascicolo { get; set; }

        [DataMember(Order = 1)]
        public ErroreProtocolloType Errore { get; set; } 
    }
}