using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloDocErProtocolloService;
using log4net;
using System;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione.Protocollazione
{
    public class ProtocollazioneClientServiceCreator : ServiceCreatorBase<WSProtocollazionePortTypeClient>
    {
        protected override string GetBindingName() => "DocErHttpBinding";
        private readonly string _url;

        public ProtocollazioneClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override WSProtocollazionePortTypeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new WSProtocollazionePortTypeClient(binding, endpoint);

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
