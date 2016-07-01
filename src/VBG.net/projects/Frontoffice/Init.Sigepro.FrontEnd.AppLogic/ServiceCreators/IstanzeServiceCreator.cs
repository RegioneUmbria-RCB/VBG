using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	internal class IstanzeServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

		public IstanzeServiceCreator(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}


		public ServiceInstance<IstanzeWsSoapClient> CreateClient(string aliasComune)
		{
			var endPoint = new EndpointAddress(_config.Parametri.UrlIstanzeService );

			var binding = new BasicHttpBinding("istanzeVisuraServiceBinding");

			var ws = new IstanzeWsSoapClient(binding, endPoint);
			var token = this._tokenApplicazioneService.GetToken(aliasComune);


			return new ServiceInstance<IstanzeWsSoapClient>(ws, token);
		}


	}
}
