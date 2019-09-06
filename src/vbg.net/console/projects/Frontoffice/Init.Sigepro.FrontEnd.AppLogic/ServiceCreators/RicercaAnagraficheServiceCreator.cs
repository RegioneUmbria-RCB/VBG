using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using log4net;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	public class RicercaAnagraficheServiceCreator
	{
		IConfigurazione<ParametriRicercaAnagrafiche> _config;
		ITokenApplicazioneService _tokenApplicazioneService;
		IAliasResolver _aliasResolver;

		public RicercaAnagraficheServiceCreator(IAliasResolver aliasResolver, IConfigurazione<ParametriRicercaAnagrafiche> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
			this._aliasResolver = aliasResolver;
		}

		public ServiceInstance<WsAnagrafe2SoapClient> CreateClient(TipoPersonaEnum tipoPersona)
		{
			if (tipoPersona == TipoPersonaEnum.Fisica)
				return GetPersonaFisicaClient();

			return GetPersonaGiuridicaClient();
		}

		private ServiceInstance<WsAnagrafe2SoapClient> GetPersonaFisicaClient()
		{
			return GetClient(this._config.Parametri.UrlRicercaPersoneFisiche);
		}

		private ServiceInstance<WsAnagrafe2SoapClient> GetPersonaGiuridicaClient()
		{
			return GetClient(this._config.Parametri.UrlRicercaPersoneGiuridiche);
		}

		private ServiceInstance<WsAnagrafe2SoapClient> GetClient(string url)
		{
			var log = LogManager.GetLogger(typeof(RicercaAnagraficheServiceCreator));

			log.DebugFormat("Inizializzazione del web service di ricerca anagrafiche all'endpoint {0} utilizzando il binding RicercaAnagraficheServiceBinding", url);


			var endPoint = new EndpointAddress(url);

			var binding = new BasicHttpBinding("ricercaAnagraficheServiceBinding");
			var ws = new WsAnagrafe2SoapClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(_aliasResolver.AliasComune);

			return new ServiceInstance<WsAnagrafe2SoapClient>(ws, token);
		}


	}
}
