using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class RicevutePecResponse
    {
        [JsonPropertyName("ricevute")]
        public IEnumerable<RicevutaPec> Ricevute { get; set; }
    }

    public class RicevutaPec
    {
        [JsonPropertyName("tipo")]
        public string Tipo { get; set; }

        [JsonPropertyName("testo")]
        public string Testo { get; set; }

        [JsonPropertyName("id")]
        public string Id { get; set; }

        [JsonPropertyName("dataOra")]
        public string DataOra { get; set; }

        [JsonPropertyName("emailDestinatario")]
        public string EmailDestinatario { get; set; }
    }
}
