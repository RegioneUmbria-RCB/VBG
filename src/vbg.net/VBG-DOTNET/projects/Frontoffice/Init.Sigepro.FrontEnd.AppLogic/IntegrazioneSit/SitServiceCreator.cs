// -----------------------------------------------------------------------
// <copyright file="SitServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------
namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
    using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
    using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;
    using VBG.Shared.Infrastructure.ServiceModel;
    using log4net;
    using System;
    using System.ServiceModel;

    public class SitServiceCreator : ServiceCreatorBase<WsSitSoapClient>, ISitServiceCreator
    {
        private readonly ILog log = LogManager.GetLogger(typeof(SitServiceCreator));
        private readonly IConfigurazione<ParametriSIT> _configurazione;
        public SitServiceCreator(IConfigurazione<ParametriSIT> configurazioneSit, IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
            this._configurazione = configurazioneSit;
        }

        protected override string GetBindingName() => "areaRiservataServiceBinding";
        protected override WsSitSoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsSitSoapClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            if (!this._configurazione.Parametri.Attivo)
            {
                throw new Exception("Impossibile stabilire una connessione con il WS SIT, verificare che l'integrazione con il SIT sia attiva per il modulo corrente");
            }

            return this._configurazione.Parametri.UrlWsSit;
        }
    }
}