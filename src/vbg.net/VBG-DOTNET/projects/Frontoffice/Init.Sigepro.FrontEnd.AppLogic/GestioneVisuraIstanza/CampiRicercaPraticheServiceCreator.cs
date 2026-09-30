using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsRicercheVisuraService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class CampiRicercaPraticheServiceCreator : ServiceCreatorBase<WsRicercaVisuraServiceClient>
    {
        public CampiRicercaPraticheServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";

        protected override WsRicercaVisuraServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsRicercaVisuraServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlCampiRicercaPraticheService;
    }
}
