using Init.SIGePro.Protocollo.Iride;
using Init.SIGePro.Protocollo.Iride2.Proxies;
using Init.SIGePro.Protocollo.Iride2.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride.Configuration;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.Iride2.PosteWeb
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
