using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DestinatarioInsProto : MittenteInsProto
    {
        [JsonPropertyName("giaInviato")]
        public bool GiaInviato { get; set; }
    }
}
