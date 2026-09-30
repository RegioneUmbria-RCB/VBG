using Init.SIGePro.Protocollo.AcarisRelationshipsServicePort;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public class RelationshipsWSClient : BaseAcarisWSClient
    {
        public RelationshipsWSClient(string url, string accessToken) : base(url, accessToken)
        {
        }

        public RelationshipsServicePortClient CreaWebService()
        {
            if (String.IsNullOrEmpty(Url))
                throw new Exception("Il parametro URL del servizio Relationships non è valorizzato.");

            var endPointAddress = CreaEndpointAddress();
            var binding = CreaBasicHttpBinding("acarisRelationshipsServiceBinding", endPointAddress);

            return new RelationshipsServicePortClient(binding, endPointAddress);
        }
    }
}
