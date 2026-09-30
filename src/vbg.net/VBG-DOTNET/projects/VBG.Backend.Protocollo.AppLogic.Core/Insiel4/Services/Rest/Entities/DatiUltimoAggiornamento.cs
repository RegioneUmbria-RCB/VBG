using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DatiUltimoAggiornamento
    {
        [JsonPropertyName("codLoginAgg")]
        public string CodLoginAgg { get; set; }

        [JsonPropertyName("descLoginAgg")]
        public string DescLoginAgg { get; set; }

        [JsonPropertyName("dataOraAgg")]
        public DateTime DataOraAgg { get; set; }
    }
}
