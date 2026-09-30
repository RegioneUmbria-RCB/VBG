using Newtonsoft.Json;

namespace Init.SIGePro.Manager.Logic.Cosap.Default
{
    public class CalcoloResponse
    {
        [JsonProperty(PropertyName = "importo")]
        public double Importo { get; set; }

        [JsonProperty(PropertyName = "calcolo")]
        public string Calcolo { get; set; }
    }
}