using FascicolazioneItalSoftService;
using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    internal class FascicolazioneClientServiceCreator : ServiceCreatorBase<proWsFascicoloPortTypeClient>
    {
        private readonly string _url;

        public FascicolazioneClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override proWsFascicoloPortTypeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new proWsFascicoloPortTypeClient(binding, endpoint);
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
