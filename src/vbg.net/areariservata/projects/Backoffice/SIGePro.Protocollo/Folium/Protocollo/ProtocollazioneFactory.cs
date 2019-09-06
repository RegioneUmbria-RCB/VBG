using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Folium.Protocollo
{
    public static class ProtocollazioneFactory
    {
        public static IProtocollazione Create(RequestInfo info, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            if (!info.Regole.UsaWsMail || info.Flusso == "I")
            {
                return new ProtocollazioneService(info, logs, serializer);
            }
            else
            {
                return new ProtocollazioneEmailService(info, logs, serializer);
            }
        }
    }
}
