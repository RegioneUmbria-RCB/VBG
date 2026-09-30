using Newtonsoft.Json;

namespace WSAtti.Sicraweb.ElencoFirmatari
{
    public class ElencoFirmatariResponse
    {
        [JsonProperty(PropertyName = "decodificheList")]
        public DecodificheList DecodificheList { get; set; }
    }
}
