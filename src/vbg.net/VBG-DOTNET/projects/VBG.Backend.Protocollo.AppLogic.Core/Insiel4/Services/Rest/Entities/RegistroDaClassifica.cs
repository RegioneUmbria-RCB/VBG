using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class RegistroDaClassifica
    {
        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }

        [JsonPropertyName("versione")]
        public int Versione { get; set; }

        [JsonPropertyName("codiceLiv1")]
        public string CodiceLiv1 { get; set; }

        [JsonPropertyName("codiceLiv2")]
        public string CodiceLiv2 { get; set; }

        [JsonPropertyName("codiceLiv3")]
        public string CodiceLiv3 { get; set; }

        [JsonPropertyName("codiceLiv4")]
        public string CodiceLiv4 { get; set; }

        [JsonPropertyName("codiceLiv5")]
        public string CodiceLiv5 { get; set; }

        [JsonPropertyName("codiceLiv6")]
        public string CodiceLiv6 { get; set; }

        [JsonPropertyName("codiceLiv7")]
        public string CodiceLiv7 { get; set; }

        [JsonPropertyName("codiceLiv8")]
        public string CodiceLiv8 { get; set; }

    }

    //public class RegistroDaClassificaView : RegistroDaClassifica
    //{
    //    [JsonPropertyName("descrizioneUfficio")]
    //    public string DescrizioneUfficio { get; set; }

    //    [JsonPropertyName("descrizioneRegistro")]
    //    public string DescrizioneRegistro { get; set; }
    //}
}
