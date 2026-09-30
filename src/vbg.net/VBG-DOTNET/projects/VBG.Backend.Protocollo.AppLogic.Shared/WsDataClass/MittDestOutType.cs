using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "MittDestOutType")]
    public class MittDestOutType
    {
        /// <remarks/>
        [DataMember(Order = 0)]
        public string IdSoggetto { get; set; }

        /// <remarks/>
        [DataMember(Order=1)]
        public string CognomeNome { get; set; }
    }
}
