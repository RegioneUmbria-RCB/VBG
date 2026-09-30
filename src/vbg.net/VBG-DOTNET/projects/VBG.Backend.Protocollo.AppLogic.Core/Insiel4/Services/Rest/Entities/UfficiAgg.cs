using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities.Common;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class UfficiAgg : Molteplicita
    {
        [JsonPropertyName("uffici")]
        public IEnumerable<UfficioAgg> Uffici { get; set; }
    }
}
