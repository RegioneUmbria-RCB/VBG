using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Frontend.AppLogic.WsIntegrazioneLDPService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP
{
    public class IntegrazioneLDPServiceCreator : ServiceCreatorBase<WsIntegrazioneLDPServiceClient>
    {
        public IntegrazioneLDPServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsIntegrazioneLDPServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding) => new WsIntegrazioneLDPServiceClient(binding, endpoint);

        protected override string GetEndpointUrl(ParametriSigeproSecurity config) => config.UrlIntegrazioneLDP;
    }
}
