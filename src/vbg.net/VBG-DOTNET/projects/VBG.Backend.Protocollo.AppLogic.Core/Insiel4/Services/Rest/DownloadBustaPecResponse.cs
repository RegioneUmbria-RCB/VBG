using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class DownloadBustaPecResponse
    {
        [JsonPropertyName("improntaMd5")]
        public string ImprontaMd5 { get; set; }

        [JsonPropertyName("file")]
        public byte[] File { get; set; }
    }
}
