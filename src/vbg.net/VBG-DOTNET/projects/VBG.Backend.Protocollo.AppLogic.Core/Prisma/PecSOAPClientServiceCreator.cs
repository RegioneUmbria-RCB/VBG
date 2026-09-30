using VBG.Shared.Infrastructure.ServiceModel;
using InvioPecAdsService;
using log4net;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    internal class PecSOAPClientServiceCreator : ServiceCreatorBase<PecSOAPImplClient>
    {
        protected override string GetBindingName() => "prismaHttpBinding";
        private readonly string _url;
        private readonly CredentialsInfo _credentialInfo;

        public PecSOAPClientServiceCreator(ILog logger, IBindingFactory bindingFactory, CredentialsInfo credentialsInfo, string url) : base(logger, bindingFactory)
        {
            this._url = url;
            this._credentialInfo = credentialsInfo;
        }

        protected override PecSOAPImplClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new PecSOAPImplClient(binding, endpoint);
            if (string.Equals(endpoint.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                ServicePointManager.SecurityProtocol = (SecurityProtocolType)3072;
                ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;
            }

            client.Endpoint.EndpointBehaviors.Add(
                new BasicAuthEndpointBehavior(_credentialInfo.CredentialInfo));

            return client;
        }

        public override string GetEndpointUrl()
        {
            return _url;
        }
    }
}
