using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name="DatiAnagraficiType")]
    public class DatiAnagraficiType
    {
        [DataMember(Order = 0)]
        public string Cod = "";

        [DataMember(Order = 1)]
        public string Mezzo = "";

        [DataMember(Order = 2)]
        public string ModalitaTrasmissione = "";

        [DataMember(Order = 3)]
        public string Email = "";
    }
}
