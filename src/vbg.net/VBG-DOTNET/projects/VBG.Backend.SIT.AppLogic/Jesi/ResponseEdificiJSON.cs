using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    internal class ResponseEdificiJSON
    {
        //[JsonPropertyName("fkfoglio")]
        //public string Foglio { get; set; }

        //[JsonPropertyName("fkmappale")]
        //public string Numero { get; set; }

        //[JsonPropertyName("cod_edificio")]
        //public string CodiceEdificio { get; set; }

        [JsonPropertyName("fkstrada")]
        public int Strada { get; set; }

        [JsonPropertyName("ncivico")]
        public int Civico { get; set; }

        [JsonPropertyName("lettera")]
        public string Esponente { get; set; }

        [JsonPropertyName("codiceedificio")]
        public string CodiceEdificio { get; set; }
    }
}
