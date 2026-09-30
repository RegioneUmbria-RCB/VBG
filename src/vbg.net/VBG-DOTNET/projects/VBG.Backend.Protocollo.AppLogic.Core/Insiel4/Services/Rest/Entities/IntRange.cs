using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class IntRange
    {
        [JsonPropertyName("da")]
        public int Da { get; set; }

        [JsonPropertyName("a")]
        public int A { get; set; }
    }
}
