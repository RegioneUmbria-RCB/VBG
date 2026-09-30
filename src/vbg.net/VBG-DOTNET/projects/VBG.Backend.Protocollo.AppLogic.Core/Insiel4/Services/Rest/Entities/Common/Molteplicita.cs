using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities.Common
{
    public class Molteplicita
    {
        [JsonPropertyName("statoParziale")]
        public bool? StatoParziale { get; set; } = false;
    }
}
