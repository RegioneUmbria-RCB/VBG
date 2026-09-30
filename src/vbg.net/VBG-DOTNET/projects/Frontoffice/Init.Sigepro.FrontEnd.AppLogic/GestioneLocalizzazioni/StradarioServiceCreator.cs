using Init.Sigepro.Frontend.AppLogic.WsStradario;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    internal class StradarioServiceCreator : ServiceCreatorBase<WsStradarioServiceClient>
    {
        public StradarioServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }
        protected override string GetBindingName() => "defaultServiceBinding";

        protected override WsStradarioServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            binding.MaxBufferSize = int.MaxValue;
            binding.MaxReceivedMessageSize = int.MaxValue;
            return new WsStradarioServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsStradario;
        }
    }
}
