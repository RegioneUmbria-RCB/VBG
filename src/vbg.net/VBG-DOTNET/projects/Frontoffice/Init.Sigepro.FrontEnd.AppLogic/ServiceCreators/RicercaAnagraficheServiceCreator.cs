using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public class RicercaAnagraficheServiceCreator
    {
        public class RicercaPersonaServiceCreator : ServiceCreatorWithConfig<WsAnagrafe2SoapClient, ParametriRicercaAnagrafiche>
        {
            private readonly string _url;
            public RicercaPersonaServiceCreator(string url, IConfigurazione<ParametriRicercaAnagrafiche> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
            {
                this._url = url;
            }

            protected override string GetBindingName() => "ricercaAnagraficheServiceBinding";
            protected override WsAnagrafe2SoapClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
            {
                return new WsAnagrafe2SoapClient(binding, endpoint);
            }

            protected override string GetEndpointUrl(ParametriRicercaAnagrafiche config)
            {
                return this._url;
            }
        }

        private readonly IConfigurazione<ParametriRicercaAnagrafiche> _config;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IBindingFactory _bindingFactory;
        public RicercaAnagraficheServiceCreator(IConfigurazione<ParametriRicercaAnagrafiche> config, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory)
        {
            if (config == null)
                throw new System.ArgumentNullException(nameof(config));
            if (tokenApplicazioneService == null)
                throw new System.ArgumentNullException(nameof(tokenApplicazioneService));
            //Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();
            //Condition.Requires(config, "config").IsNotNull();
            //Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();
            this._config = config;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._bindingFactory = bindingFactory;
        }

        public RicercaPersonaServiceCreator CreateClient(TipoPersonaEnum tipoPersona)
        {
            if (tipoPersona == TipoPersonaEnum.Fisica)
                return this.GetPersonaFisicaClient();
            return this.GetPersonaGiuridicaClient();
        }

        private RicercaPersonaServiceCreator GetPersonaFisicaClient()
        {
            return this.GetClient(this._config.Parametri.UrlRicercaPersoneFisiche);
        }

        private RicercaPersonaServiceCreator GetPersonaGiuridicaClient()
        {
            return this.GetClient(this._config.Parametri.UrlRicercaPersoneGiuridiche);
        }

        private RicercaPersonaServiceCreator GetClient(string url)
        {
            return new RicercaPersonaServiceCreator(url, this._config, this._tokenApplicazioneService, this._bindingFactory);
        }
    }
}