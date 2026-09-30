using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class OggettoDocumento
    {
        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }
        [JsonPropertyName("importo")]
        public string Importo { get; set; }
        [JsonPropertyName("valuta")]
        public string Valuta { get; set; }
    }
}
