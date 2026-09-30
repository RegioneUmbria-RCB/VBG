using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.FascicolaDetermina
{
    public class FascicolaDeterminaRequest
    {
        [JsonProperty("classifica")]
        public string Classifica { get; set; }

        [JsonProperty("oggetto")]
        public string Oggetto { get; set; }

        [JsonProperty("iddocumento")]
        public int IdDocumento { get; set; }
    }
}