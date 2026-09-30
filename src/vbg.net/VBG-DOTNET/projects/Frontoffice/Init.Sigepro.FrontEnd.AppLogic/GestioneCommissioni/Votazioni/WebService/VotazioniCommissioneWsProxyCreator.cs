using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.VotazioniCommissioniWs;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni.WebService
{
    public class VotazioniCommissioneWsProxyCreator : ServiceCreatorBase<WsVotazioniCommissioneClient>
    {
        public VotazioniCommissioneWsProxyCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsVotazioniCommissioneClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
        {
            return new WsVotazioniCommissioneClient(binding, address);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlServizioVotazioniCommissione;
        }
    }
}