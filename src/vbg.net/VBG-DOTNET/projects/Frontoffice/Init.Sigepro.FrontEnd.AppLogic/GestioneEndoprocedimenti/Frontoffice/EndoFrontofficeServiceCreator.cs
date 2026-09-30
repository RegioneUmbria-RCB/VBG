using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsEndoFrontoffice;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Frontoffice
{
    public class EndoFrontofficeServiceCreator : ServiceCreatorBase<WsEndoFrontofficeServiceClient>
    {
        public EndoFrontofficeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";
        protected override WsEndoFrontofficeServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsEndoFrontofficeServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsEndoFrontoffice;
        }
    }
}