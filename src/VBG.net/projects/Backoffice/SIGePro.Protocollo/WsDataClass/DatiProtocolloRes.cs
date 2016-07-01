using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloResponseType")]
    public class DatiProtocolloRes
    {
        public DatiProtocolloRes(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloRes(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloRes()
        {

        }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string Warning { get; set; }

        /// <remarks/>
        [DataMember(Order = 2)]
        public string IdProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public string AnnoProtocollo { get; set; }

        [DataMember(Order = 4)]
        public string DataProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 5)]
        public string NumeroProtocollo { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public ErroreProtocollo Errore { get; set; }
        
    }
}
