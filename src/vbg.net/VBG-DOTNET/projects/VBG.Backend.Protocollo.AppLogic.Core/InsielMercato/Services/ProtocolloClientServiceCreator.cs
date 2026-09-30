using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloInsielMercatoService;
using System;
using System.Collections.Generic;
using System.Net;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Services
{
    public class ProtocolloClientServiceCreator : ServiceCreatorBase<ProtocolloServicePortTypeClient>
    {
        protected override string GetBindingName() => "insielMercatoHttpBinding";
        private readonly string _url;

        public ProtocolloClientServiceCreator(ILog logger, IBindingFactory bindingFactory, string url) : base(logger, bindingFactory)
        {
            this._url = url;
        }

        protected override ProtocolloServicePortTypeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            var client = new ProtocolloServicePortTypeClient(binding, endpoint);

            foreach (var operation in client.Endpoint.Contract.Operations)
            {
                var behavior = operation.Behaviors.Find<DataContractSerializerOperationBehavior>() as DataContractSerializerOperationBehavior;
                if (behavior != null)
                    behavior.MaxItemsInObjectGraph = int.MaxValue;
            }

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
