using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Services
{
    public class BaseService
    {
        protected string Url { get; private set; }
        protected ProtocolloLogs Logs { get; private set; }
        protected ProtocolloSerializer Serializer { get; private set; }

        public BaseService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            Url = url;
            Logs = logs;
            Serializer = serializer;
        }
    }
}
