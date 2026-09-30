using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Fascicolazione
{
    public class FascicolazioneFactory
    {
        public static IFascicolazione Create(ProtocolloIrideEnumerators.VersioneEnum versione, string url, string proxyAddress, ProtocolloLogs logs, FascicolazioneClientServiceCreator serviceCreator)
        {
            switch (versione)
            {
                case ProtocolloIrideEnumerators.VersioneEnum.J_IRIDE:
                    return new FascicolazioneJIride(logs, serviceCreator);
                case ProtocolloIrideEnumerators.VersioneEnum.IRIDE:
                    return new FascicolazioneIride(logs, serviceCreator);

                default:
                    return new FascicolazioneIride(logs, serviceCreator);
            }
        }
    }
}
