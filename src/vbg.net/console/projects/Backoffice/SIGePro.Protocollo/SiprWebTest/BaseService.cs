using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.SiprWebTest
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
