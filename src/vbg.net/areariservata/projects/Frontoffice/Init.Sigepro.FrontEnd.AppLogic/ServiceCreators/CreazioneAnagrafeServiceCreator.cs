using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	public class CreazioneAnagrafeServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

		public CreazioneAnagrafeServiceCreator(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			_config = config;
			_tokenApplicazioneService = tokenApplicazioneService;
		}


		public ServiceInstance<AnagrafeClient> CreateClient(string aliasComune)
		{
			var endPoint = new EndpointAddress(_config.Parametri.UrlCreazioneAnagrafeService);

			var binding = new BasicHttpBinding("defaultServiceBinding");

			var ws = new AnagrafeClient(binding, endPoint);
			var token = _tokenApplicazioneService.GetToken(aliasComune);

			return new ServiceInstance<AnagrafeClient>(ws, token);
		}
	}
}
