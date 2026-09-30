using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Utente
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }
        [JsonPropertyName("codiceFiscale")]
        public string CodiceFiscale { get; set; }
    }
}
