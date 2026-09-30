using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsConfigurazioneContenutiService;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConfigurazioneContenuti
{
    public class ConfigurazioneContenutiServiceCreator : ServiceCreatorBase<WsConfigurazioneContenutiServiceClient>
    {
        public ConfigurazioneContenutiServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsConfigurazioneContenutiServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new WsConfigurazioneContenutiServiceClient(binding, endpoint);

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlConfigurazioneContenuti;
    }
}
