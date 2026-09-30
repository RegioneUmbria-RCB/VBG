using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    /// <remarks/>
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ErroreProtocolloType")]
    public class ErroreProtocolloType
    {
        [DataMember(Order = 1)]
        public string Descrizione { get; set; }

        [DataMember(Order = 2)]
        public string StackTrace { get; set; }
    }
}
