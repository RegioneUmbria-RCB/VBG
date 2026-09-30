using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Pagamenti.Legacy.ServiceReferences.ESED;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.ESED
{
    public class ESEDServiceCreator : ServiceCreatorBase<WsPagamentiESEDServiceClient>
    {
        public ESEDServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";

        protected override WsPagamentiESEDServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsPagamentiESEDServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlWsPagamentiESED;
    }
}
