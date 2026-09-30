using System.Runtime.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiMittentiXmlType")]
    public class DatiMittentiXmlType
    {
        /// <remarks/>
        [DataMember(Order = 0)]
        public List<ProtocolloAnagrafe>? Anagrafe { get; set; }
        /// <remarks/>
        [DataMember(Order = 1)]
        public List<ProtocolloAmministrazioni>? Amministrazione { get; set; }
    }

}
