// -----------------------------------------------------------------------
// <copyright file="MovimentiBackofficeServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
	using System.ServiceModel;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
	using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Common;
    using System;

	/// <summary>
	/// CLasse utilizzata per istanziare un client per l'accesso ai dati di un movimento effettuato nel backoffice
	/// </summary>
	public class MovimentiBackofficeServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;
        IAliasResolver _aliasResolver;

		public MovimentiBackofficeServiceCreator(IAliasResolver aliasResolver, IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
		}

        public ServiceInstance<MovimentiFrontofficeSvcSoapClient> CreateClient()
        {
            var log = LogManager.GetLogger(typeof(MovimentiBackofficeServiceCreator));

            log.DebugFormat("Inizializzazione del web service di gestione dati dell'area riservata all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._config.Parametri.UrlAreaRiservataService);


            var endPoint = new EndpointAddress(this._config.Parametri.UrlWebServiceMovimenti);

            var binding = new BasicHttpBinding("areaRiservataServiceBinding");
            var ws = new MovimentiFrontofficeSvcSoapClient(binding, endPoint);

            var token = this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);

            return new ServiceInstance<MovimentiFrontofficeSvcSoapClient>(ws, token);
        }

        [Obsolete]
		public ServiceInstance<MovimentiFrontofficeSvcSoapClient> CreateClient(string aliasComune)
		{
			var log = LogManager.GetLogger(typeof(MovimentiBackofficeServiceCreator));

			log.DebugFormat("Inizializzazione del web service di gestione dati dell'area riservata all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._config.Parametri.UrlAreaRiservataService);


			var endPoint = new EndpointAddress(this._config.Parametri.UrlWebServiceMovimenti);

			var binding = new BasicHttpBinding("areaRiservataServiceBinding");
            var ws = new MovimentiFrontofficeSvcSoapClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(aliasComune);

            return new ServiceInstance<MovimentiFrontofficeSvcSoapClient>(ws, token);
		}
	}
}
