using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AnnullamentoProtocolloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("registrazione")]
        public RegistrazioneID Registrazione { get; set; }

        [JsonPropertyName("provvedimento")]
        public EstremiProvvedimento Provvedimento { get; set; }
    }
}
