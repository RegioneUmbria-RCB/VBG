using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.LeggiDetermina
{
    public class LeggiDeterminaRequest
    {
        [JsonProperty("iddocumento")]
        public int IdDocumento { get; set; }

        [JsonProperty("anno")]
        public int? Anno { get; set; }

        [JsonProperty("numero")]
        public string Numero { get; set; }
    }
}