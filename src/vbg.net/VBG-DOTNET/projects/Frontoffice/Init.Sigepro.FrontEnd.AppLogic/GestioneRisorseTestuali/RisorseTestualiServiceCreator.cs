using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsRisorseTestualiService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali
{
    public class RisorseTestualiServiceCreator : ServiceCreatorBase<WsRisorseTestualiServiceClient>
    {
        public RisorseTestualiServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsRisorseTestualiServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new WsRisorseTestualiServiceClient(binding, endpoint);

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlRisorseTestualiService;
    }
}
