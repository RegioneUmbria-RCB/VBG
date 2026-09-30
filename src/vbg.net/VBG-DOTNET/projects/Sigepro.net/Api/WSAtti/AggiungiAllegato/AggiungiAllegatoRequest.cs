using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.AggiungiAllegato
{
    public class AggiungiAllegatoRequest : Allegato
    {
        [JsonProperty("iddocumento")]
        public string IdDocumento { get; internal set; }

        [JsonProperty("principale")]
        public bool Principale { get; internal set; }

        [JsonProperty("serial")]
        public string Serial { get; internal set; }
    }
}