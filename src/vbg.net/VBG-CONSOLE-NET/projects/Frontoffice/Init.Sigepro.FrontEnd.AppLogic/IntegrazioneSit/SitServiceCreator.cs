// -----------------------------------------------------------------------
// <copyright file="SitServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using log4net;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;
	using System.ServiceModel;

	public class SitServiceCreator : ISitServiceCreator
	{
		private readonly ILog log = LogManager.GetLogger(typeof(SitServiceCreator));
		private readonly IConfigurazione<ParametriSIT> _configurazione;
		private readonly ITokenApplicazioneService _tokenApplicazioneService;

		public SitServiceCreator(IConfigurazione<ParametriSIT> configurazione, ITokenApplicazioneService tokenApplicazioneService)
		{
			this._configurazione = configurazione;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

		public ServiceInstance<WsSitSoapClient> CreateClient(string codiceComune)
		{
			log.DebugFormat("Invoco il metodo GetConfigurazioneSIT con codiceComune: ", codiceComune);

            var config = this._configurazione.Parametri.GetConfigurazioneSIT(codiceComune);

            if (config == null)
            {
                throw new Exception($"il metodo GetConfigurazioneSIT ha restituito il valore null per il codiceComune {codiceComune}");
            }

            var endPoint = new EndpointAddress(config.UrlWsSit);

			var binding = new BasicHttpBinding("areaRiservataServiceBinding");

            if (config.UrlWsSit.StartsWith("HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = new WsSitSoapClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(config.AliasBackendLocale);

			return new ServiceInstance<WsSitSoapClient>(ws, token);
		}

	}
}
