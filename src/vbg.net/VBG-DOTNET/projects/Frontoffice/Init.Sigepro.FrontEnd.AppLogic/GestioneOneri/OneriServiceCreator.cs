using Init.Sigepro.Frontend.AppLogic.WsOneri;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    public class OneriServiceCreator : ServiceCreatorBase<WsOneriServiceClient>
    {
        public OneriServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "oneriServiceBinding";
        protected override WsOneriServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsOneriServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlOneriService;
        }
    }
}
