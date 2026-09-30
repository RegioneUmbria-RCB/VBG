using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Registro
    {
        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("descrizioneUfficio")]
        public string DescrizioneUfficio { get; set; }

        [JsonPropertyName("descrizioneRegistro")]
        public string DescrizioneRegistro { get; set; }
    }

    public class RegistroClassificato : Registro
    {
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
