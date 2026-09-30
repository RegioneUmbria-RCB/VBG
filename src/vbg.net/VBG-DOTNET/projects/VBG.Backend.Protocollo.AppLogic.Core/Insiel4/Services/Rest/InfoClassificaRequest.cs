using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InfoClassificaRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }
    }
}
