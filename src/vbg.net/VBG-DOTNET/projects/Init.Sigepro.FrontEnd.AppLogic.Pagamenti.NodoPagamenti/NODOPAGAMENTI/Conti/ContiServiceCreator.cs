using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using WsConti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Conti
{
    public class ContiServiceCreator : ServiceCreatorBase<WsContiServiceClient>
    {
        public ContiServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override string GetBindingName() => "contiServiceBinding";
        protected override WsContiServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsContiServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlContiService;
        }
    }
}
