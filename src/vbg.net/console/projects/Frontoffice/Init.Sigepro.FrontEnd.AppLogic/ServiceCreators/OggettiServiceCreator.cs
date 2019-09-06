using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggettiService;

using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	internal class OggettiServiceCreator
	{
		ILog log = LogManager.GetLogger(typeof(OggettiServiceCreator));

		IConfigurazione<ParametriSigeproSecurity>	_configurazione;
		ITokenApplicazioneService					_tokenApplicazioneService;

		public OggettiServiceCreator(IConfigurazione<ParametriSigeproSecurity> configurazione, ITokenApplicazioneService tokenApplicazioneService)
		{
			this._configurazione = configurazione;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

		internal ServiceInstance<OggettiClient> CreateClient(string aliasComune)
		{
			log.DebugFormat("Inizializzazione del web service di gestione oggetti all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._configurazione.Parametri.UrlOggettiService);

			var endPoint = new EndpointAddress(this._configurazione.Parametri.UrlOggettiService);

			var binding = new BasicHttpBinding("oggettiServiceBinding");

			var ws = new OggettiClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(aliasComune);

			return new ServiceInstance<OggettiClient>(ws, token);
		}
	}
}
