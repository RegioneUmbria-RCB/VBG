using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsTipiSoggettoService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{
    public class TipiSoggettoServiceCreator : ServiceCreatorBase<WsTipiSoggettoServiceClient>
    {
        public TipiSoggettoServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsTipiSoggettoServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsTipiSoggettoServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlTipiSoggettoService;
        }
    }
}
