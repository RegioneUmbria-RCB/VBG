using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	public class FileConverterServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _configurazione;
		ITokenApplicazioneService _tokenApplicazioneService;

		public FileConverterServiceCreator(IConfigurazione<ParametriSigeproSecurity> configurazione, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(configurazione, "configurazione").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();
			

			this._configurazione = configurazione;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}



		public ServiceInstance<fileconverterClient> CreateClient(string aliasComune)
		{
			var endPoint = new EndpointAddress(_configurazione.Parametri.UrlConversioneFileService);

			var binding = new BasicHttpBinding("fileConverterServiceBinding");

			var ws = new fileconverterClient(binding, endPoint);
			var token = _tokenApplicazioneService.GetToken(aliasComune);

			return new ServiceInstance<fileconverterClient>(ws, token);
		}
	}
}
