using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti.LeggiDetermina
{
    public class AllegatoDetermina
    {
        [JsonProperty("idesterno")]
        public string IdEsterno { get; internal set; }
        [JsonProperty("idvbg")]
        public string IdVBG { get; internal set; }
        [JsonProperty("image")]
        public byte[] Image { get; internal set; }
        [JsonProperty("nome")]
        public string Nome { get; internal set; }
    }
}