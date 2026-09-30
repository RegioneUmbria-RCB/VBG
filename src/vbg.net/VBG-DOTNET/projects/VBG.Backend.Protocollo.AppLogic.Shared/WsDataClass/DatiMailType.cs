using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiMailType")]
    public class DatiMailType
    {
        [DataMember(Order = 0)]
        public string Oggetto = null;
        
        [DataMember(Order = 1)]
        public string Corpo = null;
    }
}
