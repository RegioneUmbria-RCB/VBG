using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
	internal class PdfUtilsServiceCreator
	{
		IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

		public PdfUtilsServiceCreator(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}



		public ServiceInstance<PdfUtilsClient> CreateClient(string aliasComune)
		{
			var endPoint = new EndpointAddress(_config.Parametri.UrlPdfUtilsService);

			var binding = new BasicHttpBinding("pdfUtilsBinding");
			var ws		= new PdfUtilsClient(binding, endPoint);
			var token	= this._tokenApplicazioneService.GetToken(aliasComune);

			return new ServiceInstance<PdfUtilsClient>(ws, token);
		}
	}
}
