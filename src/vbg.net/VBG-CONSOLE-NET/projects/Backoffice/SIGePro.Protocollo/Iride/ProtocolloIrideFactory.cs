using Init.SIGePro.Protocollo.Iride.Proxies;
using Init.SIGePro.Protocollo.Iride.Proxies;
using Init.SIGePro.Protocollo.Iride.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride
{
    public class ProtocolloIrideFactory
    {
        public static IProtocolloIrideService Create(string codiceAmministrazione, ProxyProtIride proxyProtIride)
        {
            if (String.IsNullOrEmpty(codiceAmministrazione))
                return new ProtocolloIrideService(proxyProtIride);
            else
                return new ProtocolloIrideMultiDbService(codiceAmministrazione, proxyProtIride);
        }
    }
}
