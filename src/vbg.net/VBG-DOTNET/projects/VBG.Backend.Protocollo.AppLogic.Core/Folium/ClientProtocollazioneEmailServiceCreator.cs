using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloFoliumEmailService;
using System;
using System.Collections.Generic;
using System.Net;
using System.ServiceModel;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium
{
    public class ClientProtocollazioneEmailServiceCreator : ServiceCreatorBase<ProtocolloEmailWebServiceClient>
    {
        protected override string GetBindingName() => !string.IsNullOrEmpty(this._bindingName) ? this._bindingName : "defaultHttpBinding";
        private readonly string _url;
        private readonly string _bindingName;

        public ClientProtocollazioneEmailServiceCreator(ILog logger, IBindingFactory bindingFactory, string url, string bindingName) : base(logger, bindingFactory)
        {
            this._url = url;
            this._bindingName = bindingName;
        }

        protected override ProtocolloEmailWebServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new ProtocolloEmailWebServiceClient(binding, endpoint);
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
