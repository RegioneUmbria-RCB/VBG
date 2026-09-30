using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConfigurazioneAreaRiservataWs;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference
{
    public class ConfigurazioneAreaRiservataServiceCreator : ServiceCreatorBase<WsConfigurazioneAreaRiservataClient>
    {
        public ConfigurazioneAreaRiservataServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsConfigurazioneAreaRiservataClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsConfigurazioneAreaRiservataClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsConfigurazioneAreaRiservata;
        }
    }
}