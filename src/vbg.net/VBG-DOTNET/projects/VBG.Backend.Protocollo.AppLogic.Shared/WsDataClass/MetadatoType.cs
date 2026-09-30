using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "MetadatoType")]
    public class MetadatoType
    {
        [DataMember(Order = 0)]
        public string Chiave { get; set; }
        [DataMember(Order = 1)]
        public string Valore { get; set; }
    }
}
