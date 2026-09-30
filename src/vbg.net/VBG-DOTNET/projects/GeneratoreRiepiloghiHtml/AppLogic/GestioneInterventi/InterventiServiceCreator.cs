using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators;
using System.ServiceModel;
using WsInterventi;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi
{
    public class InterventiServiceCreator : ServiceCreatorBase<WsInterventiClient>
    {
        public InterventiServiceCreator(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger<InterventiServiceCreator> logger) : base(cfgService, tokenResolver, bindingFactory, logger)
        {
        }

        protected override WsInterventiClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new(binding, endpoint);

        protected override string GetEndpointUrl(ConfigurazioneEndpointUrl config) => config.UrlWsInterventi;
    }
}
