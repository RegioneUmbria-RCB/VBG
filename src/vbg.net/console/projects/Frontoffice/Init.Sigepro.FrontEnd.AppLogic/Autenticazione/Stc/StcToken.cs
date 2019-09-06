using System;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc
{
	public class StcToken
	{
		ILog m_logger = LogManager.GetLogger(typeof(StcToken));
		IConfigurazione<ParametriStcAidaSmart> _configurazione;
        

		internal StcToken(IConfigurazione<ParametriStcAidaSmart> configurazione)
		{
			Condition.Requires(configurazione, "configurazione").IsNotNull();

			_configurazione = configurazione;
		}

		public string GetToken(/*string aliasComune, string username, string password*/)
		{
			//var endPoint = new EndpointAddress(ConfigurazioneFrontEnd.WebConfig.GetUrlInvioStc(aliasComune));

			var urlInvio = _configurazione.Parametri.UrlStc;
			var username = _configurazione.Parametri.Username;
			var password = _configurazione.Parametri.Password;

			var endPoint = new EndpointAddress(urlInvio );

			var binding = new BasicHttpBinding("stcServiceBinding");
	
			using (var ws = new StcClient(binding, endPoint))
			{
				var loginRequest = new LoginRequest
				{
					username = username,
					password = password
				};

				m_logger.DebugFormat("Lettura di un nuovo token STC con le credenziali:\nusername={0}\npassword={1}",
						username,
						password);


				var response = ws.Login(loginRequest);

				if (response == null || !response.result)
				{
					m_logger.ErrorFormat("Impossibile leggere un token STC utilizzando le credenziali username={0} password={1}",
						username,
						password);

					throw new Exception("Impossibile leggere un token STC");
				}

				return response.token;
			}
		}
	}
}
