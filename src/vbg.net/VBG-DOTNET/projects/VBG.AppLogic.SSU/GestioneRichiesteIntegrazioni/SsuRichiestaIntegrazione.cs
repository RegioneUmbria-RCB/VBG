using System.Text.Json;
using System.Text.Json.Serialization;

namespace VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni
{
    public class SsuRichiestaIntegrazione
    {
        [JsonPropertyName("amministrazione")]
        public required SsuAmministrazione Amministrazione { get; set; }

        [JsonPropertyName("procedimenti")]
        public required IEnumerable<SsuProcedimentoRichiesta> Procedimenti { get; set; }

        [JsonPropertyName("allegati")]
        public required SsuAllegati Allegati { get; set; }

        public static IEnumerable<SsuRichiestaIntegrazione> FromBase64Json(string base64String)
        {
            var jsonString = System.Text.Encoding.UTF8.GetString(Convert.FromBase64String(base64String));
            return JsonSerializer.Deserialize<SsuRichiestaIntegrazione[]>(jsonString)!;
        }
    }

    public class SsuAmministrazione
    {
        [JsonPropertyName("codice")]
        public required int Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public required string Descrizione { get; set; }
    }

    public class SsuProcedimentoRichiesta
    {
        [JsonPropertyName("codice")]
        public required int Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public required string Descrizione { get; set; }

        [JsonPropertyName("richieste")]
        public required IEnumerable<string> Richieste { get; set; }
    }

    public class SsuAllegati
    {
        [JsonPropertyName("codiciOggetto")]
        public required IEnumerable<int> CodiciOggetto { get; set; }
    }
}
