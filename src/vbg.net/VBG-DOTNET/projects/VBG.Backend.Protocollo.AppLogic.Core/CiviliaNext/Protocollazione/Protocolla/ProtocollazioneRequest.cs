using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla
{
    public class ProtocollazioneRequest
    {
        [JsonProperty(PropertyName = "Pratica")]
        public Pratica Pratica { get; set; }

        [JsonProperty(PropertyName = "IdRegistro")]
        public long IdRegistro { get; set; }

        [JsonProperty(PropertyName = "IdAutore")]
        public string IdAutore { get; set; }

        [JsonProperty(PropertyName = "CodiceLivelloOrganigramma")]
        public string CodiceLivelloOrganigramma { get; set; }
    }
}
