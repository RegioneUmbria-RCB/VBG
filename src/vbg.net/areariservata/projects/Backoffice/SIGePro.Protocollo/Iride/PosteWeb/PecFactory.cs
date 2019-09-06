using Init.SIGePro.Protocollo.Iride.Proxies;
using Init.SIGePro.Protocollo.Iride.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride.PosteWeb
{
    public class PecFactory
    {
        public static IPec Create(ProtocolloIrideEnumerators.VersioneEnum tipo, IEnumerable<string> seriali, string[] destinatari, string codiceAoo, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            logs.InfoFormat("VERSIONE: {0}", tipo.ToString());
            if (tipo == ProtocolloIrideEnumerators.VersioneEnum.J_IRIDE)
                return new JIridePec(destinatari, seriali, codiceAoo, logs, serializer);
            else if (tipo == ProtocolloIrideEnumerators.VersioneEnum.IRIDE)
                return new IridePec(seriali, logs, serializer);
            else
                return new IridePec(seriali, logs, serializer);
        }
    }
}
