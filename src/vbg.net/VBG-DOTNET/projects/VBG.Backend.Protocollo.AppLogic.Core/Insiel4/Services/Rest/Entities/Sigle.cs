using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Sigle
    {
        [JsonPropertyName("operatoreCodice")]
        public string OperatoreCodice { get; set; }

        [JsonPropertyName("operatoreDescrizione")]
        public string OperatoreDescrizione { get; set; }

        [JsonPropertyName("estensoreCodice")]
        public string EstensoreCodice { get; set; }

        [JsonPropertyName("estensoreDescrizione")]
        public string EstensoreDescrizione { get; set; }
    }
}
