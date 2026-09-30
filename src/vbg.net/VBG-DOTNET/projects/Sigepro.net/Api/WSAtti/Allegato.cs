using Newtonsoft.Json;

namespace Sigepro.net.Api.WSAtti
{
    public class Allegato
    {
        [JsonProperty("tipofile")]
        public string TipoFile { get; set; }

        [JsonProperty("image")]
        public string Image { get; set; }

        [JsonProperty("nomeallegato")]
        public string NomeAllegato { get; set; }

    }
}