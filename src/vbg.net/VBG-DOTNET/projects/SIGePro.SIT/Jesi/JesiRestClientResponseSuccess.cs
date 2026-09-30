using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Jesi
{
    public class JesiRestClientResponseSuccess<TResponse>
    {
        [JsonPropertyName("hx_succ")]
        public IEnumerable<IEnumerable<TResponse>> HxSucc { get; set; }
    }
}
