using Init.SIGePro.Protocollo.Iride2.Proxies;
using Init.SIGePro.Protocollo.Iride2.Services;
using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride2
{
    public class ProtocolloIrideFactory
    {
        public static IProtocolloIrideService Create(string codiceAmministrazione, ProxyProtIride proxyProtIride, ProtocolloLogs logs)
        {
            if (String.IsNullOrEmpty(codiceAmministrazione))
                return new ProtocolloIrideService(proxyProtIride);
            else
                return new ProtocolloIrideMultiDbService(codiceAmministrazione, proxyProtIride, logs);
        }
    }
}
