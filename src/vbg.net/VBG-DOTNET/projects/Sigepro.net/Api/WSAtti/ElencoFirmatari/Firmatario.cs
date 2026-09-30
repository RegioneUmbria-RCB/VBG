using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.ElencoFirmatari
{
    public class Firmatario
    {
        [JsonProperty("codice")]
        public string Codice { get; internal set; }

        [JsonProperty("descrizione")]
        public string Descrizione { get; internal set; }
    }
}