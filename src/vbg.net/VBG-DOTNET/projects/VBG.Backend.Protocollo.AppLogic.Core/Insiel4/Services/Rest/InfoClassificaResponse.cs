using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InfoClassificaResponse
    {
        [JsonPropertyName("descLiv1")]
        public string DescLiv1 { get; set; }

        [JsonPropertyName("descLiv2")]
        public string DescLiv2 { get; set; }

        [JsonPropertyName("descLiv3")]
        public string DescLiv3 { get; set; }

        [JsonPropertyName("descLiv4")]
        public string DescLiv4 { get; set; }

        [JsonPropertyName("descLiv5")]
        public string DescLiv5 { get; set; }

        [JsonPropertyName("descLiv6")]
        public string DescLiv6 { get; set; }

        [JsonPropertyName("descLiv7")]
        public string DescLiv7 { get; set; }

        [JsonPropertyName("descLiv8")]
        public string DescLiv8 { get; set; }

        [JsonPropertyName("lengthLiv1")]
        public int LengthLiv1 { get; set; }

        [JsonPropertyName("lengthLiv2")]
        public int LengthLiv2 { get; set; }

        [JsonPropertyName("lengthLiv3")]
        public int LengthLiv3 { get; set; }

        [JsonPropertyName("lengthLiv4")]
        public int LengthLiv4 { get; set; }

        [JsonPropertyName("lengthLiv5")]
        public int LengthLiv5 { get; set; }

        [JsonPropertyName("lengthLiv6")]
        public int LengthLiv6 { get; set; }

        [JsonPropertyName("lengthLiv7")]
        public int LengthLiv7 { get; set; }

        [JsonPropertyName("lengthLiv8")]
        public int LengthLiv8 { get; set; }
    }

}
