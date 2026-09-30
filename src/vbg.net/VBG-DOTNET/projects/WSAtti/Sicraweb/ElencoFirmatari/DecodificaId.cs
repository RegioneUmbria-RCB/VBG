using Newtonsoft.Json;

namespace WSAtti.Sicraweb.ElencoFirmatari
{
    public class DecodificaId
    {
        [JsonProperty(PropertyName = "codice")]
        public int Codice { get; set; }

        [JsonProperty(PropertyName = "idcomune")]
        public string Idcomune { get; set; }
    }
}