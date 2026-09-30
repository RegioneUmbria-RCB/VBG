using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiDestinatariType")]
    public class DatiDestinatariType
    {
        /// <remarks/>
        [DataMember(Order = 0)]
        public List<DatiAnagraficiType> Anagrafe = null;
        /// <remarks/>
        [DataMember(Order = 1)]
        public List<DatiAnagraficiType> Amministrazione = null;
    }
}
