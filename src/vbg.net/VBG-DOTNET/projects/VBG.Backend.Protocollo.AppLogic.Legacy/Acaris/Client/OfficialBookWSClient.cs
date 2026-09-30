using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class OfficialBookWSClient : BaseAcarisWSClient
    {
        public OfficialBookWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public OfficialBookServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio OfficialBook non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisOfficialBookServiceBinding", endPointAddress);

            return new OfficialBookServicePortClient(binding, endPointAddress);
        }
    }
}
