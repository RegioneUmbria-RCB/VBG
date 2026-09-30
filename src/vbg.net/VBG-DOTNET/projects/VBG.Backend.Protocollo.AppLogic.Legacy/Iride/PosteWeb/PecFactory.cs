using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.PosteWeb
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
