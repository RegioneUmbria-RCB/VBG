using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using VBG.Pagamenti.Legacy.ESED;

namespace VBG.Pagamenti.Legacy.MIP.Client
{
    public class PayServerClientFactory
    {
        private readonly IGetStatoPagamento _getStatoPagamento;

        public PayServerClientFactory(IGetStatoPagamento getStatoPagamento)
        {
            this._getStatoPagamento = getStatoPagamento;
        }

        public IPayServerClient CreateClient(PayServerClientSettings settings, IUrlEncoder urlEncoder, string clientType)
        {
            if (!String.IsNullOrEmpty(clientType))
            {
                clientType = clientType.ToUpperInvariant();
            }

            if (clientType == "NATIVE")
            {
                return new PayServerClientWrapperNative(settings, urlEncoder);
            }

            if (clientType == "ESED")
            {
                return new PayServerClientWrapperESED(settings, urlEncoder, this._getStatoPagamento);
            }

            return new PayServerClientWrapper(settings, urlEncoder);
        }
    }
}
