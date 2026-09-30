using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class MittenteInsProto
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }
        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }
        [JsonPropertyName("dataRiferim")]
        public string DataRiferimento { get; set; }
        [JsonPropertyName("numRiferim")]
        public string NumeroRiferimento { get; set; }
        [JsonPropertyName("note")]
        public string Note { get; set; }
        [JsonPropertyName("modalitaTrasmissione")]
        public string ModalitaTrasmissione { get; set; }
        [JsonPropertyName("tipo")]
        public string Tipo { get; set; }
    }
}
