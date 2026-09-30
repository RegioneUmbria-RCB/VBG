using System.Xml.Serialization;
using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiFascicoloResponseType")]
    public class DatiFascicoloResponseType
    {
        public DatiFascicoloResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiFascicoloResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiFascicoloResponseType()
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
        public ErroreProtocolloType Errore { get; set; }
    }
}
