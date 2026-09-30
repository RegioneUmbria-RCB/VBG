using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsTabelleDiBaseService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase
{
    public class TabelleDiBaseServiceCreator : ServiceCreatorBase<WsTabelleDiBaseServiceClient>
    {
        public TabelleDiBaseServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsTabelleDiBaseServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new WsTabelleDiBaseServiceClient(binding, endpoint);

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlTabelleDiBaseService;
    }
}
