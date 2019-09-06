using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT;
using Init.Sigepro.FrontEnd.Pagamenti.ESED;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP.Client
{
    internal class PayServerClientFactory
    {
        public IPayServerClient CreateClient(PayServerClientSettings settings, IUrlEncoder urlEncoder)
        {
            var clientType = ConfigurationManager.AppSettings["PayServerClientWrapper"];

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
                var getStatoPagamento = FoKernelContainer.GetService<IGetStatoPagamento>();
                return new PayServerClientWrapperESED(settings, urlEncoder, getStatoPagamento);
            }

            return new PayServerClientWrapper(settings, urlEncoder);
        }
    }
}
