using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using PECJIrideService;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Client
{
    public class WsPostaWebClientServiceCreator : ServiceCreatorBase<WsPostaWebSoapClient>
    {
        protected override string GetBindingName() => "jIrideHttpBinding";
        private readonly string _url;

        public WsPostaWebClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override WsPostaWebSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new WsPostaWebSoapClient(binding, endpoint);
            if (string.Equals(endpoint.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
                ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;
            }
            return client;
        }

        public override string GetEndpointUrl()
        {
            return _url;
        }
    }
}
