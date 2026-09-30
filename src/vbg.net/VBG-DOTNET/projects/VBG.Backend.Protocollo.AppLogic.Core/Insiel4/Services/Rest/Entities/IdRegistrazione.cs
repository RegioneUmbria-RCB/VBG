using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class IdRegistrazione
    {
        [JsonPropertyName("progDoc")]
        public string ProgressivoDocumento { get; set; }

        [JsonPropertyName("progMovi")]
        public string ProgressivoMovimento { get; set; }
    }

    public class IdRegistrazioneFascicolo : IdRegistrazione
    {

    }

    public class IdRegistrazioneProtocollo : IdRegistrazione
    {

    }
}
