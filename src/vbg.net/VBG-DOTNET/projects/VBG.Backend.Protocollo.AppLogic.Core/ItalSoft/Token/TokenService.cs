using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.Token
{
    public class TokenService : ItalSoftClient
    {
        private readonly ProtocolloClientServiceCreator _clientCreator;

        public TokenService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string urlProtocollazione) : base(logger, serializer, urlProtocollazione, "")
        {
            _clientCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, urlProtocollazione);
;        }

        public string GetToken(GetTokenRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                base.Serialize("GetTokenRequest.xml", request);

                var response = client.Service.GetItaEngineContextToken(request.UserName, request.UserPassword, request.DomaniCode);

                base.Serialize("GetTokenResponse.xml", response);

                return response;
            }
        }

        public void DestroyToken(DestroyTokenRequest request)
        {
            using (var client = _clientCreator.CreateClient())
            {
                client.Service.DestroyItaEngineContextToken(request.Token, request.DomaniCode);
            }
        }
    }
}
