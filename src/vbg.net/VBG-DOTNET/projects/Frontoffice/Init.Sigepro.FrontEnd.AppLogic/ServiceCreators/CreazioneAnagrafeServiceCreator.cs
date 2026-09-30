using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public class CreazioneAnagrafeServiceCreator : ServiceCreatorBase<AnagrafeClient>
    {
        public CreazioneAnagrafeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "defaultServiceBinding";
        protected override AnagrafeClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new AnagrafeClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlCreazioneAnagrafeService;
        }
    }
}