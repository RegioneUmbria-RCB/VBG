using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaFascicoliResponseType")]
    public class ListaFascicoli
    {
        public ListaFascicoli(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public ListaFascicoli(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }
        
        public ListaFascicoli()
        {

        }

        [DataMember(Order = 0)]
        public DatiFasc[] Fascicolo { get; set; }

        [DataMember(Order = 1)]
        public ErroreProtocollo Errore { get; set; } 
    }
}