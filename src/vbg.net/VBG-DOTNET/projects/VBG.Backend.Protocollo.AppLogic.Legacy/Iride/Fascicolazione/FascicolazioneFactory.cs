using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Fascicolazione
{
    public class FascicolazioneFactory
    {
        public static IFascicolazione Create(ProtocolloIrideEnumerators.VersioneEnum versione, string url, string proxyAddress, ProtocolloLogs logs)
        {
            if (versione == ProtocolloIrideEnumerators.VersioneEnum.J_IRIDE)
                return new FascicolazioneJIride(url, proxyAddress, logs);
            else if(versione == ProtocolloIrideEnumerators.VersioneEnum.IRIDE)
                return new FascicolazioneIride(url, proxyAddress, logs);
            else
                return new FascicolazioneIride(url, proxyAddress, logs);
        }
    }
}
