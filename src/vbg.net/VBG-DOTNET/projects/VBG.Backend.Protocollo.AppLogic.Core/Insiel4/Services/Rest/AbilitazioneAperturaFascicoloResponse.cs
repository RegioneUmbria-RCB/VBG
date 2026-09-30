using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AbilitazioneAperturaFascicoloResponse
    {
        [JsonPropertyName("abilitato")]
        public bool Abilitato { get; set; }
    }
}
