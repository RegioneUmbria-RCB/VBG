using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class EstremiFascicolo
    {
        [JsonPropertyName("codiceUfficio")]
        public string Ufficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string Registro { get; set; }

        [JsonPropertyName("anno")]
        public int Anno { get; set; }

        [JsonPropertyName("numero")]
        public int Numero { get; set; }

        [JsonPropertyName("subNumero")]
        public string SubNumero { get; set; }
    }
}
