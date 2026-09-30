using Newtonsoft.Json;
using WSAtti;

namespace Sigepro.net.Api.WSAtti
{
    public class Esito
    {
        [JsonProperty("ok")]
        public bool OK { get; internal set; }

        [JsonProperty("messaggio")]
        public string Messaggio { get; internal set; }

        public static Esito FromWSEsito(WSEsito esito)
        {
            return new Esito
            {
                OK = esito.OK,
                Messaggio = esito.Messaggio
            };
        }
    }
}