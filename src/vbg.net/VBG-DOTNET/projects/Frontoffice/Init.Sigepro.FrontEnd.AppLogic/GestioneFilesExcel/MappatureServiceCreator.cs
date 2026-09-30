using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsMappatureService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    public class MappatureServiceCreator : ServiceCreatorBase<WsMappatureServiceClient>
    {
        public MappatureServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsMappatureServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new WsMappatureServiceClient(binding, endpoint);

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlMappatureService;
    }
}
