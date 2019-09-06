using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiFascType")]
    public class DatiFasc
    {
        [DataMember(Order = 0)]
        public string NumeroFascicolo = "";

        [DataMember(Order = 1)]
        public string DataFascicolo = "";

        [DataMember(Order = 2)]
        public string ClassificaFascicolo = "";

        [DataMember(Order = 3)]
        public string OggettoFascicolo = "";

        [DataMember(Order = 4)]
        public string AnnoFascicolo = "";
    }
}
