using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseNumerazioneCivicaJSON
    {
        [JsonPropertyName("id_acc_pc")]
        public int Id { get; set; }

        [JsonPropertyName("den_completa")]
        public string Denominazione { get; set; }

        [JsonPropertyName("ncivico")]
        public int? NumeroCivico { get; set; }

        [JsonPropertyName("lettera")]
        public string Lettera { get; set; }

        [JsonPropertyName("inidataefficacia")]
        public string InizioDataEfficacia { get; set; }

        [JsonPropertyName("idstrada")]
        public string IdStrada { get; set; }
    }
}
