using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloKibernetesService;
using ProtocolloKibernetesV2Service;
using System;
using System.Collections.Generic;
using System.Net;
using System.ServiceModel;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public class AnagraficaClientServiceCreator : ServiceCreatorBase<WS_AnagraficaClient>
    {
        protected override string GetBindingName() => "kibernetesHttpBinding";
        private readonly string _url;

        public AnagraficaClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override WS_AnagraficaClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new WS_AnagraficaClient(binding, endpoint);

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
