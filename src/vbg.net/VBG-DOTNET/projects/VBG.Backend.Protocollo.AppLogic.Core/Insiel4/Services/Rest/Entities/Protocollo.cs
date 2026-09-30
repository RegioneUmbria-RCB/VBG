using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Protocollo
    {
        [JsonPropertyName("codUff")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codReg")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("anno")]
        public string Anno { get; set; }

        [JsonPropertyName("num")]
        public string Numero { get; set; }

        [JsonPropertyName("subn")]
        public string SubNumero { get; set; }

        [JsonPropertyName("verso")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso? Verso { get; set; }

        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }

        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }

        [JsonPropertyName("idProt")]
        public IdRegistrazioneProtocollo IdProtocollo { get; set; }
    }
}
