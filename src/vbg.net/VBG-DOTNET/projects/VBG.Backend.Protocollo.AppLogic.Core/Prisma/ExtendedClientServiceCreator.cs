using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloExtendedPrismaService;
using System.Net;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    internal class ExtendedClientServiceCreator : ServiceCreatorBase<ProtocolloExtendedServiceClient>
    {
        protected override string GetBindingName() => "prismaHttpBinding";
        private readonly string _url;
        private readonly CredentialsInfo _credentialInfo;

        public ExtendedClientServiceCreator(ILog logger, IBindingFactory bindingFactory, CredentialsInfo credentials, string url) : base(logger, bindingFactory)
        {
            this._url = url;
            this._credentialInfo = credentials;
        }

        protected override ProtocolloExtendedServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new ProtocolloExtendedServiceClient(binding, endpoint);
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
