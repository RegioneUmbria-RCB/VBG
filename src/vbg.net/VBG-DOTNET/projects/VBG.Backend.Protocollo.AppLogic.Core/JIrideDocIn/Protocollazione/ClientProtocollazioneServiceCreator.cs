using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocollazioneJIrideService;
using System;
using System.Collections.Generic;
using System.Net;
using System.ServiceModel;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ClientProtocollazioneServiceCreator : ServiceCreatorBase<ProtocolloSoapClient>
    {
        protected override string GetBindingName() => "defaultHttpBinding";
        private readonly string _url;

        public ClientProtocollazioneServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override ProtocolloSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new ProtocolloSoapClient(binding, endpoint);

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
