using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloDeltaService;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Delta.Services
{
    public class ProtocolloClientServiceCreator : ServiceCreatorBase<PROTOCOLLOWSDLPortTypeClient>
    {
        protected override string GetBindingName() => "deltaHttpBinding";
        private readonly string _url;
        private readonly string _proxy;

        public ProtocolloClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url, string proxy) : base(logger, bindingFactory)
        {
            this._url = url;
            this._proxy = proxy;
        }

        protected override PROTOCOLLOWSDLPortTypeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            if (!String.IsNullOrEmpty(this._proxy))
            {
                binding.UseDefaultWebProxy = false;
                binding.ProxyAddress = new Uri(this._proxy);
            }

            var client = new PROTOCOLLOWSDLPortTypeClient(binding, endpoint);
            if (string.Equals(endpoint.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
                ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;
            }
            return client;
        }

        public override string GetEndpointUrl()
        {
            return this._url;
        }
    }
}
