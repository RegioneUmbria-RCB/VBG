using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.NumeraDetermina
{
    public class NumeraDeterminaRequest
    {
        [JsonProperty("iddocumento")]
        public int IdDocumento { get; set; }
    }
}