using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.ItCity
{
    public class CiviciJSON
    {
        [JsonPropertyName("Id")]
        public string Id { get; set; }

        [JsonPropertyName("Numero")]
        public string Numero { get; set; }

        [JsonPropertyName("Sub")]
        public string Sub { get; set; }

        [JsonPropertyName("Indir")]
        public string Indir { get; set; }

        [JsonPropertyName("CodQuartiere")]
        public string CodQuartiere { get; set; }

        [JsonPropertyName("DesQuartiere")]
        public string DesQuartiere { get; set; }

        [JsonPropertyName("Cap")]
        public string Cap { get; set; }

        [JsonPropertyName("ZonaAllerta")]
        public string ZonaAllerta { get; set; }

    }
}
