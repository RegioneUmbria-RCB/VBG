using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class EstremiProvvedimento
    {
        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }

        [JsonPropertyName("estremi")]
        public string Estremi { get; set; }

        [JsonPropertyName("motivo")]
        public string Motivo { get; set; }
    }
}
