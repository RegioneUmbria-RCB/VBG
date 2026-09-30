using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using CuttingEdge.Conditions;
using log4net;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.SigeproAutorizzazioniService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{
	internal class AutorizzazioniMercatiServiceCreator
	{
		IAliasResolver _aliasResolver;
		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

		public AutorizzazioniMercatiServiceCreator(IAliasResolver aliasResolver, IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			this._aliasResolver = aliasResolver;
			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

		public ServiceInstance<AutorizzazioniWebServiceSoapClient> CreateClient()
		{
			ILog log = LogManager.GetLogger(this.GetType());

			log.DebugFormat("Inizializzazione del web service di gestione autorizzazioni mercati all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._config.Parametri.UrlAreaRiservataService);


			var endPoint = new EndpointAddress(this._config.Parametri.UrlAutorizzazioniMercatiService);

			var binding = new BasicHttpBinding("areaRiservataServiceBinding");
			var ws = new AutorizzazioniWebServiceSoapClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);

			return new ServiceInstance<AutorizzazioniWebServiceSoapClient>(ws, token);
		}
	}
}
