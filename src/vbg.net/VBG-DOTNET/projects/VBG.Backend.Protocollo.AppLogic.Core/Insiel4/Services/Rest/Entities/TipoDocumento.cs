using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class TipoDocumento
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }

        [JsonPropertyName("categoria")]
        [JsonConverter(typeof(CategoriaConverter))]
        public Categoria Categoria { get; set; }

    }
}
