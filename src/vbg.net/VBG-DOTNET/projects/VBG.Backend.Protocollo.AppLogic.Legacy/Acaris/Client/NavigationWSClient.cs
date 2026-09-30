using Init.SIGePro.Protocollo.AcarisNavigationServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class NavigationWSClient : BaseAcarisWSClient
    {
        public NavigationWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public NavigationServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Navigation non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisNavigationServiceBinding", endPointAddress);

            return new NavigationServicePortClient(binding, endPointAddress);
        }
    }
}
