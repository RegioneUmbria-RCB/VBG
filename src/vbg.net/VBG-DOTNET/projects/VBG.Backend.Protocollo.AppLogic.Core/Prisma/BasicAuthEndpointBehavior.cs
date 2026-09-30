using System.ServiceModel.Channels;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    public class BasicAuthEndpointBehavior : IEndpointBehavior
    {
        private readonly string _credentialInfo;

        public BasicAuthEndpointBehavior(string credentialInfo)
        {
            _credentialInfo = credentialInfo;
        }

        public void ApplyClientBehavior(ServiceEndpoint endpoint, ClientRuntime clientRuntime)
        {
            clientRuntime.ClientMessageInspectors.Add(
                new BasicAuthMessageInspector(_credentialInfo));
        }

        public void AddBindingParameters(ServiceEndpoint endpoint, BindingParameterCollection bindingParameters)
        {
        }

        public void ApplyDispatchBehavior(ServiceEndpoint endpoint, EndpointDispatcher endpointDispatcher)
        {
        }

        public void Validate(ServiceEndpoint endpoint)
        {
        }
    }
}
