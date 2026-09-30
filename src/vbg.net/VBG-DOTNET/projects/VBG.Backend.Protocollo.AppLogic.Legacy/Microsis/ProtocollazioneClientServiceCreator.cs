using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloMicrosisServiceProxy;
using log4net;
using System;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis
{
    public class ProtocollazioneClientServiceCreator : ServiceCreatorBase<WS_ProtocolloSoapClient>
    {
        protected override string GetBindingName() => "defaultHttpBinding";
        private readonly string _url;

        public ProtocollazioneClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override WS_ProtocolloSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new WS_ProtocolloSoapClient(binding, endpoint);

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
