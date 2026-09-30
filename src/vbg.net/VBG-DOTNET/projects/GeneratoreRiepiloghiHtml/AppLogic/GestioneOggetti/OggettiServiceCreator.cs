using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators;
using System.ServiceModel;
using WsOggetti;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti
{
    public class OggettiServiceCreator : ServiceCreatorBase<OggettiClient>
    {
        public OggettiServiceCreator(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger<OggettiServiceCreator> logger) : base(cfgService, tokenResolver, bindingFactory, logger)
        {
        }

        protected override OggettiClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            binding.MessageEncoding = WSMessageEncoding.Mtom;

            return new(binding, endpoint);
        }

        protected override string GetEndpointUrl(ConfigurazioneEndpointUrl config) => config.OggettiServiceUrl;
    }
}
