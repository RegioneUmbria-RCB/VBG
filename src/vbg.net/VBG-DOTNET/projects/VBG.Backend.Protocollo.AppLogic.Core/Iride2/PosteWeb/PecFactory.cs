using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.PosteWeb
{
    public class PecFactory
    {
        public static IPec Create(ProtocolloIrideEnumerators.VersioneEnum tipo, IEnumerable<string> seriali, string[] destinatari, string codiceAoo, bool invioInterop, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            logs.InfoFormat("VERSIONE: {0}", tipo.ToString());
            if (tipo == ProtocolloIrideEnumerators.VersioneEnum.J_IRIDE)
                return new JIridePec(destinatari, seriali, codiceAoo, logs, serializer, bindingFactory, invioInterop);
            else if (tipo == ProtocolloIrideEnumerators.VersioneEnum.IRIDE)
                return new IridePec(seriali, logs, serializer, bindingFactory);
            else
                return new IridePec(seriali, logs, serializer, bindingFactory);
        }
    }
}
