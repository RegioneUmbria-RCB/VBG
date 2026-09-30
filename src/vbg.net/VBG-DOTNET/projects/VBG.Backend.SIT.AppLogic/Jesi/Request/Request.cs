using System.Text.Json.Serialization;

namespace VBG.Backend.SIT.Jesi.Request
{
    public class RequestS : IRequest
    {
        [JsonPropertyName("S")]
        public string S { get; set; }
    }

    public class RequestSC : RequestS
    {
        [JsonPropertyName("C")]
        public string C { get; set; }
    }

    public class RequestSCL : RequestSC
    {
        [JsonPropertyName("L")]
        public string L { get; set; }
    }

    public class RequestF : IRequest
    {
        [JsonPropertyName("F")]
        public string F { get; set; }
    }

    public class RequestFN : RequestF
    {
        [JsonPropertyName("N")]
        public string N { get; set; }
    }

    public class RequestFNS : RequestFN
    {
        [JsonPropertyName("S")]
        public string S { get; set; }
    }
}
