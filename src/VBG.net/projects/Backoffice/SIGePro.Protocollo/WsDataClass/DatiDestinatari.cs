using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name="DatiDestinatariType")]
    public class DatiDestinatari
    {
        /// <remarks/>
        [DataMember(Order = 0)]
        public List<DatiAnagrafici> Anagrafe = null;
        /// <remarks/>
        [DataMember(Order = 1)]
        public List<DatiAnagrafici> Amministrazione = null;
    }
}
