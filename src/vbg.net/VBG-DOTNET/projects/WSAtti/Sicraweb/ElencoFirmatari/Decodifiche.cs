using Newtonsoft.Json;

namespace WSAtti.Sicraweb.ElencoFirmatari
{
    public class Decodifiche
    {

        [JsonProperty(PropertyName = "chiave")]
        public string Chiave { get; set; }

        [JsonProperty(PropertyName = "flgDisabilitato")]
        public bool FlgDisabilitato { get; set; }

        [JsonProperty(PropertyName = "id")]
        public DecodificaId Id { get; set; }

        [JsonProperty(PropertyName = "tabella")]
        public string Tabella { get; set; }

        [JsonProperty(PropertyName = "valore")]
        public string Valore { get; set; }
    }
}