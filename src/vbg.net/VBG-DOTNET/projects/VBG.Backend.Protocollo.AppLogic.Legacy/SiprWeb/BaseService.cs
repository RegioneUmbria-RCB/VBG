
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb
{
    public class BaseService
    {
        protected readonly string Url;
        protected readonly ProtocolloLogs Logs;
        protected readonly ProtocolloSerializer Serializer;

        public BaseService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            Url = url;
            Logs = logs;
            Serializer = serializer;
        }
    }
}
