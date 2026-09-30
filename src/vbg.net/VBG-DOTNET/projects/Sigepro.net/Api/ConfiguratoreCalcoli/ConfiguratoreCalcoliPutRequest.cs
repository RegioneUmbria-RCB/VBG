using Newtonsoft.Json;

namespace Sigepro.net.Api.ConfiguratoreCalcoli
{
    public class ConfiguratoreCalcoliPutRequest
    {
        [JsonProperty("descrizione")]
        public string Descrizione { get; set; }
    }
}