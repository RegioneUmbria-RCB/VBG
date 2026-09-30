using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione")]
    public class Firmatario
    {
        [DataMember(Order = 0)]
        public string Codice { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string Descrizione { get; set; }
    }
}
