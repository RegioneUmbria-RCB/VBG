using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsAnagraficheService;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    public class WsAnagraficheServiceCreator : ServiceCreatorBase<WsAnagraficheServiceClient>
    {
        public WsAnagraficheServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsAnagraficheServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new WsAnagraficheServiceClient(binding, endpoint);

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlAnagraficheVBG;
    }
}
