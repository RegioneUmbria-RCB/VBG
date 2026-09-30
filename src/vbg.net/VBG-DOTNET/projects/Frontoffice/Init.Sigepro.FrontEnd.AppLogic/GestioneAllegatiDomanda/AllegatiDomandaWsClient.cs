using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomandaWs;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{
    public class AllegatiDomandaWsClient : ServiceCreatorBase<WsAllegatiDomandaClient>
    {
        public AllegatiDomandaWsClient(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsAllegatiDomandaClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
        {
            return new WsAllegatiDomandaClient(binding, address);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlAllegatiDomandaWs;
        }
    }
}