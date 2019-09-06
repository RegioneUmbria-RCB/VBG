using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "AllegatoType")]
    public class Allegato
    {
        [DataMember(Order = 0)]
        public string Cod = "";

        [DataMember(Order = 1)]
        public string Descrizione = "";
    }    
}
