using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class EstremiRegistrazioneProtocollo
    {
        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("anno")]
        public string Anno { get; set; }

        [JsonPropertyName("numero")]
        public string Numero { get; set; }

        [JsonPropertyName("verso")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso Verso { get; set; }

        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }
    }
}
