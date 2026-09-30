using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators;
using System.ServiceModel;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.Visura
{
    public class VisuraServiceCreator : ServiceCreatorBase<WsIstanzeServiceClient>
    {
        public VisuraServiceCreator(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger<VisuraServiceCreator> logger) : base(cfgService, tokenResolver, bindingFactory, logger)
        {
        }

        protected override WsIstanzeServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new(binding, endpoint);

        protected override string GetEndpointUrl(ConfigurazioneEndpointUrl config) => config.UrlVisura;
    }
}
