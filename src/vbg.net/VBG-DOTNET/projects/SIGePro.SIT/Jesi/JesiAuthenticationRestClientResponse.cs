using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Jesi
{
    public class JesiAuthenticationRestClientResponse
    {
        [JsonPropertyName("hx_succ")]
        public List<object> HxSucc { get; set; }

        [JsonPropertyName("hx_err")]
        public JesiRestResponseError ErrorResponse { get; set; }
    }
}
