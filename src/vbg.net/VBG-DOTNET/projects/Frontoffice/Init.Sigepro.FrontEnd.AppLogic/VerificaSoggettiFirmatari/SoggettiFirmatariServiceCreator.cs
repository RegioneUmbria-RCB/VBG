using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsSoggettiFirmatariService;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class SoggettiFirmatariServiceCreator : ServiceCreatorBase<WsSoggettiFirmatariServiceClient>
    {
        public SoggettiFirmatariServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsSoggettiFirmatariServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsSoggettiFirmatariServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlWsSoggettiFirmatari;
    }
}
