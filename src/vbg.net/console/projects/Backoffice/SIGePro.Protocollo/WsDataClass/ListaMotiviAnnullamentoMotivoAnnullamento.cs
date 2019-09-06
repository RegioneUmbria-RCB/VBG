using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "ListaMotiviAnnullamentoMotivoAnnullamentoType")]
    public class ListaMotiviAnnullamentoMotivoAnnullamento
    {
        /// <remarks/>
        [DataMember(Order = 0)]
        public string Codice { get; set; }

        /// <remarks/>
        [DataMember(Order = 1)]
        public string Descrizione { get; set; }
    }
}
