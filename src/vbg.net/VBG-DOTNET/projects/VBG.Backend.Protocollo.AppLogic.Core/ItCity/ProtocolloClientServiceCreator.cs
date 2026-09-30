using VBG.Shared.Infrastructure.ServiceModel;
using ItCityService;
using log4net;
using System;
using System.Collections.Generic;
using System.Net;
using System.ServiceModel;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity
{
    public class ProtocolloClientServiceCreator : ServiceCreatorBase<ProtocollazioneClient>
    {
        protected override string GetBindingName() => "defaultHttpBinding";
        private readonly string _url;

        public ProtocolloClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override ProtocollazioneClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new ProtocollazioneClient(binding, endpoint);

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
