using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InterrogaProtocolloResponse
    {
        [JsonPropertyName("numRegEstratte")]
        public int NumRegEstratte { get; set; }

        [JsonPropertyName("numRegEstratte")]
        public int NumRegTotale { get; set; }

        [JsonPropertyName("registrazioni")]
        public DettagliProtocollo[] Registrazioni { get; set; }

    }
}
