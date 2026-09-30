using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using PECJIrideService;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2
{
    internal class WsPostaWebClientServiceCreator : ServiceCreatorBase<WsPostaWebSoapClient>
    {
        private readonly string _url;
        private readonly string _proxy;

        public WsPostaWebClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string proxy, string url) : base(logger, bindingFactory)
        {
            this._url = url;
            this._proxy = proxy;
        }

        protected override WsPostaWebSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            if (!String.IsNullOrEmpty(_proxy))
            {
                binding.UseDefaultWebProxy = false;
                binding.ProxyAddress = new Uri(_proxy);

            }
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
