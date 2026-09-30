using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class RegistrazioneFascicolo
    {
        [JsonPropertyName("id")]
        public IdRegistrazioneFascicolo Id { get; set; }

        [JsonPropertyName("estremi")]
        public EstremiFascicolo Estremi { get; set; }

    }
}
