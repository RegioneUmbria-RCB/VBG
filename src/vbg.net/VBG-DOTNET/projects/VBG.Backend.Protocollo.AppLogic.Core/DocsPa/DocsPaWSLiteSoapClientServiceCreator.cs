using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProxyDocsPa;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocsPa
{
    public class DocsPaWSLiteSoapClientServiceCreator: ServiceCreatorBase<DocsPaWSLiteSoapClient>
    {
        protected override string GetBindingName() => "docsPaHttpBinding";
        private readonly VerticalizzazioneProtocolloDocspa _vert;

        public DocsPaWSLiteSoapClientServiceCreator(ILog logger, IBindingFactory bindingFactory, VerticalizzazioneProtocolloDocspa vert) : base(logger, bindingFactory)
        {
            this._vert = vert;
        }

        protected override DocsPaWSLiteSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var service = new DocsPaWSLiteSoapClient(binding, endpoint);

            if (string.Equals(endpoint.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
                ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;
            }
            return service;
        }

        public override string GetEndpointUrl()
        {
            return this._vert.Url;
        }
    }
}
