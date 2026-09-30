using System.Collections.Generic;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Genova.Dto
{
    /// <summary>
    /// Risposta dei metodi GetElencoCivici/Esponenti/Colori/Scale/Interni/LettInterno:
    /// il payload è sempre Risposta.Civico[] con una sola proprietà valorizzata
    /// (NUMERO_CIVICO, ESPONENTE, COLORE, SCALA, INTERNO o LETTINTERNO).
    /// </summary>
    internal class ElementoCivicoDto
    {
        [JsonExtensionData]
        public Dictionary<string, JsonElement>? Valori { get; set; }
    }

    internal class ElencoCiviciContenutoDto
    {
        [JsonPropertyName("Civico")]
        public List<ElementoCivicoDto> Civici { get; set; } = [];
    }

    internal class ElencoCiviciRispostaDto
    {
        [JsonPropertyName("Risposta")]
        public ElencoCiviciContenutoDto Risposta { get; set; } = new();
    }
}
