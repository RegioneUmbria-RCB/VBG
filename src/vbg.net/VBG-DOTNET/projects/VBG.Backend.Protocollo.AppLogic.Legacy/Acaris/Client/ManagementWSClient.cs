using System;
using Init.SIGePro.Protocollo.AcarisManagementServicePort;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class ManagementWSClient : BaseAcarisWSClient
    {
        public ManagementWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public ManagementServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Management non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisManagementServiceBinding", endPointAddress);

            return new ManagementServicePortClient(binding, endPointAddress);
        }
    }
}
