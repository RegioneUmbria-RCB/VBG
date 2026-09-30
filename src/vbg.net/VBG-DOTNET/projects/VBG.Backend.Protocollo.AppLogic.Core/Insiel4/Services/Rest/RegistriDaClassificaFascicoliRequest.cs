using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class RegistriDaClassificaFascicoliRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("filtro")]
        public FiltroRegistri Filtro { get; set; }
    }
}
