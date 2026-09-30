
using Newtonsoft.Json;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class AuthenticationRestServiceResponse
    {
        [JsonProperty(PropertyName = "access_token")]
        public string AccessToken { get; set; }

        [JsonProperty(PropertyName = "scope")]
        public string Scope { get; set; }

        [JsonProperty(PropertyName = "token_type")]
        public string TokenType { get; set; }

        [JsonProperty(PropertyName = "expires_in")]
        public int? ExpiresIn { get; set; }
    }
}
