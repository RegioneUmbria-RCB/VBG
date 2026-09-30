using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Mnemonico
    {
        [JsonPropertyName("codiceTipo")]
        public string CodiceTipo { get; set; }

        [JsonPropertyName("codiceLiv1")]
        public string CodiceLiv1 { get; set; }

        [JsonPropertyName("codiceLiv2")]
        public string CodiceLiv2 { get; set; }

        [JsonPropertyName("codiceLiv3")]
        public string CodiceLiv3 { get; set; }

        [JsonPropertyName("codiceLiv4")]
        public string CodiceLiv4 { get; set; }

        [JsonPropertyName("codiceLiv5")]
        public string CodiceLiv5 { get; set; }

        [JsonPropertyName("codiceLiv6")]
        public string CodiceLiv6 { get; set; }

        [JsonPropertyName("codiceLiv7")]
        public string CodiceLiv7 { get; set; }

        [JsonPropertyName("codiceLiv8")]
        public string CodiceLiv8 { get; set; }

    }

    public class MnemonicoView : Mnemonico
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }

        [JsonPropertyName("descrizioneTipo")]
        public string DescrizioneTipo { get; set; }

        [JsonPropertyName("descrizioneLiv1")]
        public string DescrizioneLiv1 { get; set; }

        [JsonPropertyName("descrizioneLiv2")]
        public string DescrizioneLiv2 { get; set; }

        [JsonPropertyName("descrizioneLiv3")]
        public string DescrizioneLiv3 { get; set; }

        [JsonPropertyName("descrizioneLiv4")]
        public string DescrizioneLiv4 { get; set; }

        [JsonPropertyName("descrizioneLiv5")]
        public string DescrizioneLiv5 { get; set; }

        [JsonPropertyName("descrizioneLiv6")]
        public string DescrizioneLiv6 { get; set; }

        [JsonPropertyName("descrizioneLiv7")]
        public string DescrizioneLiv7 { get; set; }

        [JsonPropertyName("descrizioneLiv8")]
        public string DescrizioneLiv8 { get; set; }
    }
}
