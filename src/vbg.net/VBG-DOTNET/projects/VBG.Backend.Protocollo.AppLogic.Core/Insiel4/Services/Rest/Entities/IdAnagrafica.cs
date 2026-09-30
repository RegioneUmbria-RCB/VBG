using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class IdAnagrafica
    {
        [JsonPropertyName("codice")]
        public string CodiceAnagrafica { get; set; }

        [JsonPropertyName("descrizione")]
        public string DescrizioneAnagrafica { get; set; }
    }
}
