using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "AllegatoResponseType")]
    public class AllOut
    {
        public AllOut(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public AllOut(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public AllOut()
        {

        }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string Serial { get; set; }

        [DataMember(Order = 2)]
        public string TipoFile { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public string ContentType { get; set; }

        /// <remarks/>
        [DataMember(Order = 4)]
        public Byte[] Image { get; set; }

        /// <remarks/>
        [DataMember(Order = 5)]
        public string Commento { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public string IDBase { get; set; }

        /// <remarks/>
        [DataMember(Order = 7)]
        public string Versione { get; set; }

        [DataMember(Order = 8)]
        public ErroreProtocollo Errore { get; set; } 
    }
}
