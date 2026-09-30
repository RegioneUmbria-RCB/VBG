using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class FascicoloId
    {
        [JsonPropertyName("estremi")]
        public EstremiFascicolo Estremi { get; set; }

        [JsonPropertyName("id")]
        public IdRegistrazione IdRegistrazione { get; set; }
    }
}
