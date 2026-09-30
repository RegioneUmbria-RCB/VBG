using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities.Common;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class FascicoliAgg : Molteplicita
    {
        [JsonPropertyName("fascicolo")]
        public IEnumerable<FascicoloAgg> Fascicoli { get; set; }
    }
}
