using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.ServiceModel;
// using Init.Sigepro.FrontEnd.AppLogic.Configuration;

using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	internal class AreaRiservataServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

		public AreaRiservataServiceCreator(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();
			
			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

		public ServiceInstance<AreaRiservataServiceSoapClient> CreateClient(string aliasComune)
		{
			ILog log = LogManager.GetLogger(typeof(AreaRiservataServiceCreator));

			log.DebugFormat("Inizializzazione del web service di gestione dati dell'area riservata all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._config.Parametri.UrlAreaRiservataService);


			var endPoint = new EndpointAddress(this._config.Parametri.UrlAreaRiservataService);

			var binding = new BasicHttpBinding("areaRiservataServiceBinding");
			var ws = new AreaRiservataServiceSoapClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(aliasComune);

			return new ServiceInstance<AreaRiservataServiceSoapClient>(ws, token);
		}
	}
}
