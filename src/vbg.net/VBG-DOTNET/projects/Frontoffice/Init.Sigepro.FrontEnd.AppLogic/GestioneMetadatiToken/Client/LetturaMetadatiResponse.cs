using System;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken.Client
{
    public class MetadatoToken
    {
        [JsonPropertyName("nome")]
        [Required]
        public string Nome { get; set; } = "";

        [JsonPropertyName("valore")]
        [Required]
        public string Valore { get; set; } = "";
    }

    //public class SalvataggioMetadatiRequest
    //{
    //    [JsonPropertyName("token")]
    //    [Required]
    //    public string Token { get; set; } = "";
    //    [JsonPropertyName("metadati")]
    //    [Required]
    //    public MetadatoToken[] Metadati { get; set; } = [];
    //}

    public class LetturaMetadatiResponse
    {
        [JsonPropertyName("metadati")]
        [Required]
        public MetadatoToken[] Metadati { get; set; } = Array.Empty<MetadatoToken>();
    }
}
