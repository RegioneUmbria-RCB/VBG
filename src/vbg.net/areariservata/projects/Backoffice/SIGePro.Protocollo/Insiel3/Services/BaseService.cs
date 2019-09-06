using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Insiel3.Services
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
