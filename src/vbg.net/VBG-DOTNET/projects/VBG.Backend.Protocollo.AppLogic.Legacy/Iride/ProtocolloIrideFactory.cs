using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Services;
using VBG.Backend.Protocollo.AppLogic.Legacy.Iride.Proxies;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Iride
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
