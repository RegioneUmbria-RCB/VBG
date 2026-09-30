using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.InvioProtocollo
{
    public class InvioProtocolloRequest
    {
        [JsonProperty(PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "idOperatore")]
        public long IdOperatore { get; set; }

        [JsonProperty(PropertyName = "idCasellaEmail")]
        public long? IdCasellaEmail { get; set; }

        [JsonProperty(PropertyName = "body")]
        public string Body { get; set; }

        [JsonProperty(PropertyName = "generaSegnatura")]
        public bool GeneraSegnatura { get; set; }

        [JsonProperty(PropertyName = "destinatari")]
        public List<DestinatariWS> Destinatari { get; set; }
    }
}
