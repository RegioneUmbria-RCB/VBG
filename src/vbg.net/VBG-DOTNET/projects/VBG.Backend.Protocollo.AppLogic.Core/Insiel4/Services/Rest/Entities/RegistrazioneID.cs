using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class RegistrazioneID
    {
        [JsonPropertyName("id")]
        public IdRegistrazioneProtocollo Id { get; set; }

        [JsonPropertyName("estremi")]
        public EstremiRegistrazioneProtocollo Estremi { get; set; }
    }
}
