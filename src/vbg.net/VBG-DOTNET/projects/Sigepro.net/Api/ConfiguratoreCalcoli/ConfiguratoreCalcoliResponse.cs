using Newtonsoft.Json;

namespace Sigepro.net.Api.ConfiguratoreCalcoli
{
    public class ConfiguratoreCalcoliPutResponse
    {
        [JsonProperty("esito")]
        public string Esito { get; internal set; }

        [JsonProperty("descrizione")]
        public string Descrizione { get; internal set; }

        public static ConfiguratoreCalcoliPutResponse OK()
        {
            return new ConfiguratoreCalcoliPutResponse
            {
                Esito = "OK",
                Descrizione = string.Empty
            };
        }

        public static ConfiguratoreCalcoliPutResponse KO(string message)
        {
            return new ConfiguratoreCalcoliPutResponse
            {
                Esito = "KO",
                Descrizione = message
            };
        }
    }
}