using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloKibernetesService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Kibernetes.Protocollazione
{
    public interface IProtocollazione
    {
        StatusProtocollo Protocolla(WS_AnagraficaClient ws, ProtocolloLogs logs, ProtocolloSerializer serializer);
    }
}
