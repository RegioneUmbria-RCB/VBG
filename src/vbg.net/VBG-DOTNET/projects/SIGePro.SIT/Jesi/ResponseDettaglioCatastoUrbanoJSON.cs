using System;
using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Jesi
{
    public class ResponseDettaglioCatastoUrbanoJSON
    {
        [JsonPropertyName("immobile")]
        public int Immobile { get; set; }

        [JsonPropertyName("prog_immobile")]
        public int ProgressivoImmobile { get; set; }

        [JsonPropertyName("foglio")]
        public string Foglio { get; set; }

        [JsonPropertyName("numero")]
        public string NumeroParticella { get; set; }

        [JsonPropertyName("sub")]
        public string Subalterno { get; set; }

        [JsonPropertyName("categoria")]
        public string Categoria { get; set; }

        [JsonPropertyName("superficie")]
        public int Superficie { get; set; }

        [JsonPropertyName("piano1")]
        public string Piano1 { get; set; }

        [JsonPropertyName("piano2")]
        public string Piano2 { get; set; }

        [JsonPropertyName("piano3")]
        public string Piano3 { get; set; }

        [JsonPropertyName("piano4")]
        public string Piano4 { get; set; }

        [JsonPropertyName("data_fine")]
        public DateTime? DataFine { get; set; }
    }
}
