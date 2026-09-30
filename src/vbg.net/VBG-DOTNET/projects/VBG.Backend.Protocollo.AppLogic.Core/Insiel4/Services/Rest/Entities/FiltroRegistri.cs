using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class FiltroRegistri
    {
        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("codiceRegistroLiv1")]
        public string CodiceRegistroLiv1 { get; set; }

        [JsonPropertyName("codiceRegistroLiv2")]
        public string CodiceRegistroLiv2 { get; set; }

        [JsonPropertyName("codiceRegistroLiv3")]
        public string CodiceRegistroLiv3 { get; set; }

        [JsonPropertyName("codiceRegistroLiv4")]
        public string CodiceRegistroLiv4 { get; set; }

        [JsonPropertyName("codiceRegistroLiv5")]
        public string CodiceRegistroLiv5 { get; set; }

        [JsonPropertyName("codiceRegistroLiv6")]
        public string CodiceRegistroLiv6 { get; set; }

        [JsonPropertyName("codiceRegistroLiv7")]
        public string CodiceRegistroLiv7 { get; set; }

        [JsonPropertyName("codiceRegistroLiv8")]
        public string CodiceRegistroLiv8 { get; set; }
    }
}
