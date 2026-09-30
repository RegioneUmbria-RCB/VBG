using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class StringRange
    {
        [JsonPropertyName("da")]
        public string Da { get; set; }

        [JsonPropertyName("a")]
        public string A { get; set; }
    }
}
