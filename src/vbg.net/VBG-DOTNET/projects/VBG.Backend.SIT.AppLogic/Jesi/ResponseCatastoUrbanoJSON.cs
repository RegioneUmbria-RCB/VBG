using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class ResponseCatastoUrbanoJSON
    {
        [JsonPropertyName("foglio")]
        public int? Foglio { get; set; }

        [JsonPropertyName("numero")]
        public string NumeroParticella { get; set; }

        [JsonPropertyName("idstrada")]
        public int CodiceVia { get; set; }

        [JsonPropertyName("denominazione")]
        public string DenominazioneVia { get; set; }

        [JsonPropertyName("ncivico")]
        public int NumeroCivico { get; set; }

        [JsonPropertyName("esponente")]
        public string Esponente { get; set; }
    }
}
