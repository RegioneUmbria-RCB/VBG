using Init.Sigepro.Frontend.AppLogic.WsComuniService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{
    internal class ComuniServiceCreator : ServiceCreatorBase<WsComuniServiceClient>
    {
        public ComuniServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "defaultServiceBinding";
        protected override WsComuniServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsComuniServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsComuniService;
        }
    }
}