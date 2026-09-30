using System.Collections.Generic;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione")]
    public class ListaFirmatari
    {
        [DataMember(Order = 0)]
        public IEnumerable<Firmatario> Firmatari { get; set; }
    }
}
