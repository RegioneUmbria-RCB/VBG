using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DocumentoInsProto
    {
        [JsonPropertyName("id")]
        public string Id { get; set; }
        [JsonPropertyName("nome")]
        public string Nome { get; set; }
        [JsonPropertyName("primario")]
        public bool Primario { get; set; }
        [JsonPropertyName("inviaIOP")]
        public bool InviaIOP { get; set; }
        [JsonPropertyName("testoMessaggio")]
        public string TestoMessaggio { get; set; }
    }
}
