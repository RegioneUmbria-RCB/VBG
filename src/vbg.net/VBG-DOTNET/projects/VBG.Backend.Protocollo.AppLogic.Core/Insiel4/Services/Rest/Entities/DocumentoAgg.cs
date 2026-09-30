using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DocumentoAgg
    {
        [JsonPropertyName("elimina")]
        public bool Elimina { get; set; }

        [JsonPropertyName("id")]
        public string Id { get; set; }

        [JsonPropertyName("nome")]
        public string Nome { get; set; }

        [JsonPropertyName("primario")]
        public bool Primario { get; set; }

        [JsonPropertyName("idDoc")]
        public long IdDoc { get; set; }
    }
}
