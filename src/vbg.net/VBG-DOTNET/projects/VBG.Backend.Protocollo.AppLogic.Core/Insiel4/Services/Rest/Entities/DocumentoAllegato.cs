using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DocumentoAllegato
    {
        [JsonPropertyName("nome")]
        public string Nome { get; set; }

        [JsonPropertyName("idDoc")]
        public long IdDoc { get; set; }

        [JsonPropertyName("tipoDoc")]
        public string TipoDoc { get; set; }

        [JsonPropertyName("tipo")]
        public short Tipo { get; set; }

        [JsonPropertyName("improntaSha1")]
        public string ImprontaSha1 { get; set; }

        [JsonPropertyName("improntaSha256")]
        public string ImprontaSha256 { get; set; }

        [JsonPropertyName("flIop")]
        public short FlIop { get; set; }

        //[JsonPropertyName("flDgr")]
        //public short FlDgr { get; set; }

        //[JsonPropertyName("gestDocId")]
        //public long GestDocId { get; set; }

        //[JsonPropertyName("flPrincipale")]
        //public short FlPrincipale { get; set; }

        //[JsonPropertyName("gestDocIndir")]
        //public string GestDocIndir { get; set; }

        [JsonPropertyName("lungh")]
        public long Lungh { get; set; }

        [JsonPropertyName("est")]
        public string Est { get; set; }

        //[JsonPropertyName("crc")]
        //public long Crc { get; set; }

        //[JsonPropertyName("flIteratti")]
        //public short FlIteratti { get; set; }

        [JsonPropertyName("flTestoMsg")]
        public short FlTestoMsg { get; set; }

    }
}
