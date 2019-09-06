using Init.SIGePro.Protocollo.Logs;
using System.Net;

namespace Init.SIGePro.Protocollo.Pal
{
    public class BaseProtocolloServiceWrapper
    {
        protected ProtocolloLogs _logs;
        protected string _baseUrlWs;
        protected enum _wsMethodType { POST, GET };

        protected BaseProtocolloServiceWrapper(ProtocolloLogs logs, string baseUrlWs)
        {
            _logs = logs;
            _baseUrlWs = baseUrlWs;
        }

        protected WebClient GetHttpClient(string token)
        {
            var client = new WebClient();
            client.Headers.Add(HttpRequestHeader.Authorization, token);
            return client;
        }
    }
}
