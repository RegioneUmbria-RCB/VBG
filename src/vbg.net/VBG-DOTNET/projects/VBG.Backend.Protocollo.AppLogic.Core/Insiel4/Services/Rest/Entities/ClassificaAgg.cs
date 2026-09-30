using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class ClassificaAgg : Classifica
    {
        [JsonPropertyName("elimina")]
        public bool Elimina { get; set; }
    }
}
