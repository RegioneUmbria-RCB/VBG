using Init.Sigepro.FrontEnd.AppLogic.ConnectedServices.SIC;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public class InformazioniAggiuntive
    {
        [JsonPropertyName("geojson")]
        public GeoJSON? GeoJSON { get; set; }

        [JsonPropertyName("additionalinfo")]
        public ICollection<Parametro> AdditionalInfo { get; set; } = new List<Parametro>();

        [JsonPropertyName("longitudine")]
        public string Longitudine { get; set; } = "";

        [JsonPropertyName("latitudine")]
        public string Latitudine { get; set; } = "";

        [JsonPropertyName("codicestradario")]
        public int CodiceStradario { get; set; }

        [JsonPropertyName("stradario")]
        public string Stradario { get; set; } = "";

        [JsonPropertyName("km")]
        public string Km { get; set; } = "";

        [JsonPropertyName("uuid")]
        public string Uuid { get; set; } = "";

        [JsonPropertyName("exception")]
        public List<string> Exceptions { get; set; } = new List<string>();


        internal static InformazioniAggiuntive FromKO(IEnumerable<string> errori)
        {
            return new InformazioniAggiuntive
            {
                Exceptions = errori.ToList()
            };
        }
    }
}
