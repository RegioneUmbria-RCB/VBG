using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Genova.Dto
{
    internal class StradaDto
    {
        [JsonPropertyName("CODICE_VIARIO")]
        public string CodiceViario { get; set; } = "";

        [JsonPropertyName("TOPONIMO")]
        public string Toponimo { get; set; } = "";

        [JsonPropertyName("DESCRIZIONE")]
        public string Descrizione { get; set; } = "";

        [JsonPropertyName("LOCALITA")]
        public string Localita { get; set; } = "";

        [JsonPropertyName("DATA_FINE_VALIDITA")]
        public string DataFineValidita { get; set; } = "";
    }

    internal class ElencoStradeContenutoDto
    {
        [JsonPropertyName("Strada")]
        public List<StradaDto> Strade { get; set; } = [];
    }

    internal class ElencoStradeRispostaDto
    {
        [JsonPropertyName("Risposta")]
        public ElencoStradeContenutoDto Risposta { get; set; } = new();
    }
}
