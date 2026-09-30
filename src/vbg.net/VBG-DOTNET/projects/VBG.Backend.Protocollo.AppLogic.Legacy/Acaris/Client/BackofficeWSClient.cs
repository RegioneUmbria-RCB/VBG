using Init.SIGePro.Protocollo.AcarisBackofficeServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class BackofficeWSClient : BaseAcarisWSClient
    {
        public BackofficeWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public BackOfficeServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Backoffice non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisBackofficeServiceBinding",endPointAddress);

            return new BackOfficeServicePortClient(binding, endPointAddress);
        }
    }
}
