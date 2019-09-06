using Init.SIGePro.Protocollo.Iride.Proxies;
using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride.Fascicolazione
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
