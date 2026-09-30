using Newtonsoft.Json;

namespace WSAtti
{
    public class WsAttiFirmatario
    {
        [JsonProperty(PropertyName = "codice")]
        public string Codice { get; set; }

        [JsonProperty(PropertyName = "descrizione")]
        public string Descrizione { get; set; }
    }
}
