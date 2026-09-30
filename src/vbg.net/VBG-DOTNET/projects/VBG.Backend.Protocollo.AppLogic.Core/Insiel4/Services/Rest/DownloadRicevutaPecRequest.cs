using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class DownloadRicevutaPecRequest : DownloadBustaPecRequest
    {
        [JsonPropertyName("idRicevuta")]
        public string IdRicevuta { get; set; }
    }
}
