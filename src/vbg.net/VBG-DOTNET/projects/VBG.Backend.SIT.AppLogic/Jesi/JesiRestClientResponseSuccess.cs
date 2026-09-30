using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class JesiRestClientResponseSuccess<TResponse>
    {
        [JsonPropertyName("hx_succ")]
        public IEnumerable<IEnumerable<TResponse>> HxSucc { get; set; }
    }
}
