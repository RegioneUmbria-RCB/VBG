using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class Corrispondente
    {
        [JsonPropertyName("codAna")]
        public string CodiceAnagrafica { get; set; }

        [JsonPropertyName("descAna")]
        public string DescrizioneAnagrafica { get; set; }

        [JsonPropertyName("codCatRife")]
        public string CodCatRife { get; set; }

        [JsonPropertyName("codTipoRife")]
        public string CodTipoRife { get; set; }

        [JsonPropertyName("descTipoRife")]
        public string DescTipoRife { get; set; }

        [JsonPropertyName("codTipoDoc")]
        public string CodTipoDoc { get; set; }

        [JsonPropertyName("descTipoDoc")]
        public string DescTipoDoc { get; set; }

        [JsonPropertyName("dataRiferim")]
        public DateTime? DataRiferim { get; set; }

        [JsonPropertyName("numRiferim")]
        public string NumRiferim { get; set; }

        [JsonPropertyName("numCopie")]
        public int NumCopie { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("giaInviato")]
        public bool GiaInviato { get; set; }
    }
}
