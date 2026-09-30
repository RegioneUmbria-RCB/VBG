using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators;
using System.ServiceModel;
using VbgDatiDinamici;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici
{
    public class StrutturaSchedeDinamicheServiceCreator : ServiceCreatorBase<WsDatiDinamiciClient>
    {
        public StrutturaSchedeDinamicheServiceCreator(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger<StrutturaSchedeDinamicheServiceCreator> logger) : base(cfgService, tokenResolver, bindingFactory, logger)
        {
        }

        protected override WsDatiDinamiciClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new(binding, endpoint);

        protected override string GetEndpointUrl(ConfigurazioneEndpointUrl config) => config.UrlWsDatiDinamici;
    }
}
