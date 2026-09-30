using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class EstremiDocumento
    {
        [JsonPropertyName("anno")]
        public int Anno { get; set; }
        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }
        [JsonPropertyName("numero")]
        public string Numero { get; set; }
        [JsonPropertyName("tipo")]
        public string Tipologia { get; set; }
    }
}
