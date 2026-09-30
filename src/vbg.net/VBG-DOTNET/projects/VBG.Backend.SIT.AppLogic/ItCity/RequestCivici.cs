using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.ItCity
{
    public class RequestCivici
    {
        [JsonPropertyName("q")]
        public string Indir { get; set; }

    }
}
