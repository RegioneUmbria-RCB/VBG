using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Genova.Authentication
{
    public class GenovaGetTokenResponse
    {
        [JsonPropertyName("access_token")]
        public string AccessToken { get; set; }
        [JsonPropertyName("scope")]
        public string Scope { get; set; }
        [JsonPropertyName("token_type")]
        public string TokenType { get; set; }
    }
}