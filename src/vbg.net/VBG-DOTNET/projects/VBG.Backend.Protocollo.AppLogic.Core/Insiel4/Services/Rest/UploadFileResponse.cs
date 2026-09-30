using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class UploadFileResponse
    {
        [JsonPropertyName("idFile")]
        public string IdFile { get; set; }
    }
}
