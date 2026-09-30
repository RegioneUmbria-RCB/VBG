using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Genova.Dto
{
    internal class ValidazioneContenutoDto
    {
        [JsonPropertyName("esitoValidazione")]
        public string EsitoValidazione { get; set; } = "";
    }

    internal class ValidazioneRispostaDto
    {
        [JsonPropertyName("Risposta")]
        public ValidazioneContenutoDto Risposta { get; set; } = new();
    }
}
