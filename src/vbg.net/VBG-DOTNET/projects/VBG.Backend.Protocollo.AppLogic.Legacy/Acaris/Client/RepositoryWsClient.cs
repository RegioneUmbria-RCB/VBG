using Init.SIGePro.Protocollo.AcarisRepositoryServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class RepositoryWsClient : BaseAcarisWSClient
    {
        public RepositoryWsClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public RepositoryServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Repository non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisRepositoryServiceBinding", endPointAddress);

            return new RepositoryServicePortClient(binding, endPointAddress);
        }
    }
}
