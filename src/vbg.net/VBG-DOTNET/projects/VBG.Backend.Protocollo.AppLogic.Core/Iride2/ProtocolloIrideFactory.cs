using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2
{
    public class ProtocolloIrideFactory
    {
        public static IProtocolloIrideService Create(string codiceAmministrazione, ProtocolloClientServiceCreator clientServerCreator, ProtocolloLogs logs)
        {
            if (String.IsNullOrEmpty(codiceAmministrazione))
                return new ProtocolloIrideService(clientServerCreator);
            else
                return new ProtocolloIrideMultiDbService(codiceAmministrazione, clientServerCreator, logs);
        }
    }
}
