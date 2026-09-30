using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.AlboPretorioService;

using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	internal class AlboPretorioServiceCreator
	{

		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

		public AlboPretorioServiceCreator(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}



		public ServiceInstance<AlboPretorioClient> CreateClient(string aliasComune)
		{
			var endPoint = new EndpointAddress(_config.Parametri.UrlAlboPretorioService);

			var binding = new BasicHttpBinding("alboPretorioServiceBinding");
			var ws = new AlboPretorioClient(binding, endPoint);
			var token = this._tokenApplicazioneService.GetToken(aliasComune);

			return new ServiceInstance<AlboPretorioClient>(ws, token);
		}
	}
}
