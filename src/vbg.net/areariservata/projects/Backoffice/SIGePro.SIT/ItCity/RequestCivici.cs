using Newtonsoft.Json;

namespace Init.SIGePro.Sit.ItCity
{
    public class RequestCivici
    {
        [JsonProperty(PropertyName = "q")]
        public string Indir { get; set; }

    }
}
