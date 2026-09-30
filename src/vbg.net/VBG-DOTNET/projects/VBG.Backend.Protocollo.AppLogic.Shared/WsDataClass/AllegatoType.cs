using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "AllegatoType")]
    public class AllegatoType
    {
        [DataMember(Order = 0)]
        public string Cod = "";

        [DataMember(Order = 1)]
        public string Descrizione = "";

        [DataMember(Order = 2)]
        public bool? InviaTramitePec = true;
    }    
}
