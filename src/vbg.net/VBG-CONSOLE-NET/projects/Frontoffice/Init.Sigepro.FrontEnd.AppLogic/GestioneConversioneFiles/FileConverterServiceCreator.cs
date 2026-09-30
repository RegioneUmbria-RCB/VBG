using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsFileConverterService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles
{
	public class FileConverterServiceCreator
	{
		private readonly IConfigurazione<ParametriSigeproSecurity> _configurazione;
		private readonly ITokenApplicazioneService _tokenApplicazioneService;

		public FileConverterServiceCreator(IConfigurazione<ParametriSigeproSecurity> configurazione, ITokenApplicazioneService tokenApplicazioneService)
		{
			this._configurazione = configurazione ?? throw new ArgumentNullException(nameof(configurazione));
			this._tokenApplicazioneService = tokenApplicazioneService ?? throw new ArgumentNullException(nameof(tokenApplicazioneService));
		}



		public ServiceInstance<fileconverterClient> CreateClient()
		{
			var endPoint = new EndpointAddress(_configurazione.Parametri.UrlConversioneFileService);

			var binding = new BasicHttpBinding("fileConverterServiceBinding");

			var ws = new fileconverterClient(binding, endPoint);
			var token = _tokenApplicazioneService.GetToken();

			return new ServiceInstance<fileconverterClient>(ws, token);
		}
	}
}
