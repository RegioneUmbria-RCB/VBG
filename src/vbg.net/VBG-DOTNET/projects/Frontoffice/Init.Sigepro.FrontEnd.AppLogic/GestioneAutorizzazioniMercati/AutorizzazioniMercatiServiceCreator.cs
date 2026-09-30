using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.SigeproAutorizzazioniService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{
    public class AutorizzazioniMercatiServiceCreator : ServiceCreatorBase<WsAutorizzazioniServiceClient>
    {
        public AutorizzazioniMercatiServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsAutorizzazioniServiceClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
        {
            return new WsAutorizzazioniServiceClient(binding, address);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlAutorizzazioniMercatiService.ToString();
        }
    }
}