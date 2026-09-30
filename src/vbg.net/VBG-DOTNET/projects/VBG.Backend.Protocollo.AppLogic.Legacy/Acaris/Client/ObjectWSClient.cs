using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class ObjectWSClient : BaseAcarisWSClient
    {
        public ObjectWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public ObjectServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Object non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisObjectServiceBinding",endPointAddress);

            return new ObjectServicePortClient(binding, endPointAddress);
        }
    }
}
