using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Web;

namespace Sigepro.net.WsProtocollo
{
    public class ProtocolloServiceCreator : ServiceCreatorBase<ProtocollazioneServiceClient>
    {
        private readonly ConfigurazioneGenerale _configurazioneGenerale;

        public ProtocolloServiceCreator(ILog logger, IBindingFactory bindingFactory, ConfigurazioneGenerale configurazioneGenerale) : base(logger, bindingFactory)
        {
            this._configurazioneGenerale = configurazioneGenerale;
        }

        protected override string GetEndpointUrl()
        {
            return this._configurazioneGenerale.WsUrlProtocollo;
        }

        protected override ProtocollazioneServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            binding.MessageEncoding = WSMessageEncoding.Mtom;
            return new ProtocollazioneServiceClient(binding, endpoint);
        }
    }
}