using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.RicercaPraticheWs;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.ServiceModel;
using System;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche
{
    public class RicercaPraticheServiceCreator : ServiceCreatorBase<WsRicercaPraticheServiceClient>
    {
        private readonly IConfigurazione<ParametriStcConsole> _stcConfig;
        private readonly IConfigurazione<ParametriServiziAreaRiservata> _configurazione;

        public RicercaPraticheServiceCreator(IConfigurazione<ParametriStcConsole> stcConfig, IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver, IBindingFactory bindingFactory, IConfigurazione<ParametriServiziAreaRiservata> configurazione) : base(cfg, tokenApplicazioneService, aliasResolver, bindingFactory)
        {
            this._stcConfig = stcConfig;
            this._configurazione = configurazione;
        }

        protected override WsRicercaPraticheServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsRicercaPraticheServiceClient(binding, endpoint);
        }
        protected override string ResolveServiceAlias()
        {
            return this._stcConfig.Parametri.SportelloDestinatario.IdEnte;
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            if (String.IsNullOrEmpty(this._configurazione.Parametri.UrlRicercaPraticaConsole))
            {
                throw new ArgumentException("Parametro di verticalizzazione SERVIZI_CONSOLE_AREARISERVATA.URL_RICERCA_PRATICA_CONSOLE non configurato. Non è possibile effettuare la ricerca pratiche");
            }

            return this._configurazione.Parametri.UrlRicercaPraticaConsole;
        }
    }
}
