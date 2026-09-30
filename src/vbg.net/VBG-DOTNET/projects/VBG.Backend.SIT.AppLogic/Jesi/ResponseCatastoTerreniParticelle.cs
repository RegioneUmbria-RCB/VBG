using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class ResponseCatastoTerreniParticelle
    {
        [JsonPropertyName("foglio")]
        public string Foglio { get; set; }

        [JsonPropertyName("numero")]
        public string NumeroParticella { get; set; }

        [JsonPropertyName("ettari")]
        public int Ettari { get; set; }

        [JsonPropertyName("are")]
        public int Are { get; set; }

        [JsonPropertyName("centiare")]
        public int Centiare { get; set; }

        [JsonPropertyName("qualita")]
        public string Qualita { get; set; }
    }
}
