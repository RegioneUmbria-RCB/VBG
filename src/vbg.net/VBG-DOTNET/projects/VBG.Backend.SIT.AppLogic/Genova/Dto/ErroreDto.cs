using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Genova.Dto
{
    internal class ErroreContenutoDto
    {
        [JsonPropertyName("detail")]
        public string Detail { get; set; } = "";

        [JsonPropertyName("status")]
        public int Status { get; set; }

        [JsonPropertyName("title")]
        public string Title { get; set; } = "";
    }

    internal class ErroreDto
    {
        [JsonPropertyName("Errore")]
        public ErroreContenutoDto Errore { get; set; } = new();
    }
}
