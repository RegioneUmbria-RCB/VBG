using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class PrecedenteAgg
    {
        [JsonPropertyName("precedente")]
        public RegistrazioneID Precedente { get; set; }

        [JsonPropertyName("tipoLegame")]
        public string TipoLegame { get; set; }

        [JsonPropertyName("elimina")]
        public bool Elimina { get; set; }
    }
}
