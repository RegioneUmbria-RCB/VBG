using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AperturaFascicoloResponse
    {
        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }

        [JsonPropertyName("anno")]
        public int Anno { get; set; }

        [JsonPropertyName("numero")]
        public int Numero { get; set; }

        [JsonPropertyName("subNumero")]
        public string SubNumero { get; set; }

        [JsonPropertyName("dataApertura")]
        public DateTime? DataApertura { get; set; }

        [JsonPropertyName("progDoc")]
        public long ProgDoc { get; set; }

        [JsonPropertyName("progMovi")]
        public int ProgMovi { get; set; }
    }
}
