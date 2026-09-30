using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities.Common;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class PrecedentiAgg : Molteplicita
    {
        [JsonPropertyName("precedente")]
        public IEnumerable<PrecedenteAgg> Precedenti { get; set; }

        [JsonPropertyName("statoGlobale")]
        public bool? StatoGlobale { get; set; } = false;
    }
}
