using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class RiferimentoAnagrafico
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }

        [JsonPropertyName("codTipo")]
        public string CodTipo { get; set; }

        [JsonPropertyName("data")]
        public DateTime Data { get; set; }
    }
}
