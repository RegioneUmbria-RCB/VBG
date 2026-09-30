using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiMittentiType")]
    public class DatiMittentiType
    {
        /// <remarks/>
        [DataMember(Order = 0)]
        public List<DatiAnagraficiType> Anagrafe { get; set; } = null;
        /// <remarks/>
        [DataMember(Order = 1)]
        public List<DatiAnagraficiType> Amministrazione { get; set; } = null;
    }

}
