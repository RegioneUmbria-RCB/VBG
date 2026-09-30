using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti
{
    public class Response
    {
        [JsonProperty("esito")]
        public Esito Esito { get; set; }
    }
}