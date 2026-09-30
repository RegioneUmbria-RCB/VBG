using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DestinatarioAgg : DestinatarioIOPInsProto
    {
        [JsonPropertyName("elimina")]
        public bool Elimina { get; set; }
    }
}
