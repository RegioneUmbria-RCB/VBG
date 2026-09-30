using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DataRange
    {
        [JsonPropertyName("da")]
        public DateTime? Da { get; set; }

        [JsonPropertyName("a")]
        public DateTime? A { get; set; }
    }
}
