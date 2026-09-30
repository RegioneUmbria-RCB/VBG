using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Jesi
{
    public class JesiRestResponseError
    {
        [JsonPropertyName("mess")]
        public string Message { get; set; }

        [JsonPropertyName("fonte")]
        public string Source { get; set; }

        [JsonPropertyName("cod")]
        public string Code { get; set; }
    }
}
