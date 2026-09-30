using Init.SIGePro.Protocollo.AcarisDocumentServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class DocumentWSClient : BaseAcarisWSClient
    {
        public DocumentWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public DocumentServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Document non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisDocumentServiceBinding",endPointAddress);

            return new DocumentServicePortClient(binding, endPointAddress);
        }
    }
}
