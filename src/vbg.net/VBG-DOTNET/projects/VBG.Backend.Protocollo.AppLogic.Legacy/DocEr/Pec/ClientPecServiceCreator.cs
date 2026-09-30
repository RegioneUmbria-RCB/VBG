using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloDocErLoginService;
using Init.SIGePro.Protocollo.ProtocolloDocErPecService;
using log4net;
using System;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Pec
{
    public class ClientPecServiceCreator : ServiceCreatorBase<WSPECPortTypeClient>
    {
        protected override string GetBindingName() => "DocErHttpBinding";
        private readonly string _url;

        public ClientPecServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override WSPECPortTypeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new WSPECPortTypeClient(binding, endpoint);

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
