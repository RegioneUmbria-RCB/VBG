using Newtonsoft.Json;
using System;

namespace Sigepro.net.Api.WSAtti.InserisciDetermina
{
    public class InserisciDeterminaRequest
    {
        [JsonProperty("oggetto")]
        public string Oggetto { get; set; }

        [JsonProperty("trattamento")]
        public string Trattamento { get; set; }

        [JsonProperty("proponente")]
        public string Proponente { get; set; }

        [JsonProperty("dirigente")]
        public string Dirigente { get; set; }

        [JsonProperty("data")]
        public DateTime Data { get; set; }

        [JsonProperty("classifica")]
        public string Classifica { get; set; }

        [JsonProperty("pubblicare")]
        public bool Pubblicare { get; set; }

        [JsonProperty("note")]
        public string Note { get; set; }

        [JsonProperty("ruolo")]
        public string Ruolo { get; set; }
    }
}