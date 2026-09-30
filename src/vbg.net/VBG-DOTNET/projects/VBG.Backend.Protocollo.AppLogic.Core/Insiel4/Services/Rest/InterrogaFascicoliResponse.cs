using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InterrogaFascicoliResponse
    {
        [JsonPropertyName("fascicoli")]
        public DettagliFascicolo[] Fascicoli { get; set; }

        [JsonPropertyName("numFasc")]
        public int NumFasc { get; set; }

        [JsonPropertyName("numFascTotale")]
        public long NumFascTotale { get; set; }
    }

}
