using System.Text.Json.Serialization;

namespace VBG.AppLogic.SSU.GestioneCorrezioni
{
    public class SsuProcedimentoCorrezione
    {
        [JsonPropertyName("codice")]
        public required int CodiceProcedimento { get; set; }
        [JsonPropertyName("correzione")]
        public required string CorrezioneRichiesta { get; set; }

        public static IEnumerable<SsuProcedimentoCorrezione> FromBase64Json(string base64string)
        {
            var jsonString = System.Text.Encoding.UTF8.GetString(System.Convert.FromBase64String(base64string));
            return System.Text.Json.JsonSerializer.Deserialize<SsuProcedimentoCorrezione[]>(jsonString)!;
        }
    }
}
