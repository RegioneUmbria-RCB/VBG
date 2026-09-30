using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiFascicoloResponseType")]
    public class DatiFascicolo
    {
        public DatiFascicolo(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiFascicolo(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiFascicolo()
        {

        }

        /// <remarks/>
        [DataMember(Order=0)]
        public string Warning { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string AnnoFascicolo { get; set; }

        [DataMember(Order = 2)]
        public string DataFascicolo { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public string NumeroFascicolo { get; set; }

        /// <remarks/>
        [DataMember(Order = 4)]
        public ErroreProtocollo Errore { get; set; }
    }
}
