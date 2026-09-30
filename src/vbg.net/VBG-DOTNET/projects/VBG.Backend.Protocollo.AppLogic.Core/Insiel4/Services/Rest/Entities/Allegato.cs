using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Allegato
    {
        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }

        [JsonPropertyName("importo")]
        public double Importo { get; set; }

        [JsonPropertyName("valuta")]
        public string Valuta { get; set; }

        [JsonPropertyName("valutaDesc")]
        public string ValutaDesc { get; set; }

        [JsonPropertyName("tipoAllegato")]
        public string TipoAllegato { get; set; }

        [JsonPropertyName("tipoAllegatoDesc")]
        public string TipoAllegatoDesc { get; set; }
    }
}
