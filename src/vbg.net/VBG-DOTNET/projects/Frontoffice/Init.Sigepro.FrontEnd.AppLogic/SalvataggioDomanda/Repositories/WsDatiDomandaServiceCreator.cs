using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsDatiDomandaService;

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories
{
    public class WsDatiDomandaServiceCreator : ServiceCreatorBase<WsDatiDomandaServiceClient>
    {

        public WsDatiDomandaServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";

        protected override WsDatiDomandaServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsDatiDomandaServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsDatiDomanda;
        }
    }
}
