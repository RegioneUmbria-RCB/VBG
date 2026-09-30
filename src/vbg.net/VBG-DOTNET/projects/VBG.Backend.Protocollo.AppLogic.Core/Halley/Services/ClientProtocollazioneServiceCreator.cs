using EliosWSFascicolazioneSoapClient;
using HalleyProtoService;
using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System;
using System.Collections.Generic;
using System.Net;
using System.ServiceModel;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Services
{
    public class ClientProtocollazioneServiceCreator : ServiceCreatorBase<DOCAREAProtoSoapClient>
    {
        protected override string GetBindingName() => "HalleyProtoSoap";
        private readonly string _url;
        private readonly string _proxy;

        public ClientProtocollazioneServiceCreator(ILog logger, IBindingFactory bindingFactory, string url, string proxy) : base(logger, bindingFactory)
        {
            this._url = url;
            this._proxy = proxy;
        }

        protected override DOCAREAProtoSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            if (!String.IsNullOrEmpty(this._proxy))
            {
                binding.UseDefaultWebProxy = false;
                binding.ProxyAddress = new Uri(this._proxy);
            }
            var client = new DOCAREAProtoSoapClient(binding, endpoint);
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
