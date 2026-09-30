using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.FascicolaDetermina
{
    public class DeterminaFascicolataRequest
    {
        [JsonProperty("iddocumento")]
        public int IdDocumento { get; set; }

    }
}