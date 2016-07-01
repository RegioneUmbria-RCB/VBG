using System;
using System.ServiceModel;
using System.ServiceModel.Channels;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;
using Init.Sigepro.FrontEnd.AppLogic.SigeproSecurityService;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using log4net;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
	public class SigeproSecurityProxy
	{
		private static class Constants
		{
			public const string BindingName = "defaultServiceBinding";
			public const string ConfigSectionName = "sigepro/frontEnd";
		}

		string _username;
		string _password;
		string _url;

		public SigeproSecurityProxy()
		{
			var cfg = GetConfigurazione();

			var username = cfg.SigeproSecurity.Username;
			var password = cfg.SigeproSecurity.Password;
			var url		 = cfg.SigeproSecurity.LoginServiceUrl;

			Condition.Requires(username, "username").IsNotNullOrEmpty();
			Condition.Requires(password, "password").IsNotNullOrEmpty();
			Condition.Requires(url, "url").IsNotNullOrEmpty();

			this._username = username;
			this._password = password;
			this._url = url;
		}


		/*
		public static string TraduciIdComune(string idComune)
		{
			var cacheKey = "ID_COMUNE_TRADOTTO_" + idComune;

			if (CacheHelper.KeyExists(cacheKey))
				return CacheHelper.GetEntry<string>(cacheKey);

			string token = VbgTokenReader.GetToken(idComune);
			var res = new SigeproSecurityProxy().CheckToken(token);

			return CacheHelper.AddEntry( cacheKey , res.tokenInfo.idcomune );

		}
		*/
		ILog m_logger = LogManager.GetLogger(typeof(SigeproSecurityProxy));

		public string GetApplicationToken(string aliasComune)
		{
			using (var ws = CreateClient())
			{
				var req = new LoginRequest
				{
					alias = aliasComune,
					username = this._username,
					password = this._password,
					ipAddress = "127.0.0.1",
					contesto = ContestoType.APP
				};

				try
				{
					m_logger.DebugFormat("Lettura di un nuovo token con le credenziali:\nusername={0}\npassword={1}\ncontesto={2}\nalias={3}\nip={4}",
											req.username,
											req.password,
											req.contesto,
											req.alias,
											req.ipAddress);

					var scope = new OperationContextScope(ws.InnerChannel);

					OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

					var res = ws.Login(req);

					m_logger.DebugFormat("Nuovo token = {0}", res.token);

					return res.token;
				}
				catch (Exception ex)
				{
					// TODO: log dell'errore
					m_logger.ErrorFormat("Errore durante il login:{0}\r\n Username: {1}, Password: {2}, Contesto: {3}, Alias: {4} ", ex.ToString(), req.username, req.password, req.contesto, req.alias);

					throw;
				}
			}
		}

		public CheckTokenResponse CheckToken(string token)
		{
			return CheckToken(token, true);
		}

		public CheckTokenResponse CheckToken(string token, bool leggiTokenInfo)
		{
			using (var ws = CreateClient())
			{
				try
				{
					m_logger.DebugFormat("Verifica del token {0} con tokenInfo = {1}", token, leggiTokenInfo);

					var req = new CheckTokenRequest
					{
						token = token,
						tokenInfo = leggiTokenInfo
					};

					OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

					OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

					var res = ws.CheckToken(req);

					m_logger.DebugFormat("Esito della verifica: {0} ", res.valid);

					return res;
				}
				catch (Exception ex)
				{
					// TODO: log dell'errore
					m_logger.ErrorFormat("Errore durante la verifica del token {0}\r\n {1}", token, ex.ToString());

					throw;
				}
			}
		}

        public LivelloAutenticazioneEnum GetLivelloAutenticazione(string token)
        {
            using (var ws = CreateClient())
            {
                try
                {
                    OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    var livelloAutenticazione = ws.GetAuthLevel(new GetAuthLevelRequest { 
                        token = token
                    });

                    var lvl = String.IsNullOrEmpty(livelloAutenticazione.authlevel) ? 1 : Convert.ToInt32(livelloAutenticazione.authlevel);

                    return (LivelloAutenticazioneEnum)lvl;
                }
                catch (Exception ex)
                {
                    m_logger.ErrorFormat("Errore durante la chiamata a GetApplicationInfo {0}", ex.ToString());

                    throw;
                }
            }
        }

		internal ApplicationInfoType[] GetApplicationInfo()
		{
			using (var ws = CreateClient())
			{
				try
				{
					var req = new GetApplicationInfoRequest
					{
						param = String.Empty
					};
					OperationContextScope scope = new OperationContextScope(ws.InnerChannel);

					OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

					return ws.GetApplicationInfo(req);
				}
				catch (Exception ex)
				{
					m_logger.ErrorFormat("Errore durante la chiamata a GetApplicationInfo {0}", ex.ToString());

					throw;
				}
			}
		}

		private MessageHeader CreateHeader()
		{
			return UserNameSecurityTokenHeader.FromUserNamePassword( this._username, this._password);
		}

		private sigeproSecurityClient CreateClient()
		{
			var endPoint = new EndpointAddress(this._url);

			var binding = new BasicHttpBinding(Constants.BindingName);

			return new sigeproSecurityClient(binding, endPoint);
		}

		protected ConfigurazioneFrontEndWebConfig GetConfigurazione()
		{
			ConfigurazioneFrontEndWebConfig config = (ConfigurazioneFrontEndWebConfig)System.Configuration.ConfigurationManager.GetSection(Constants.ConfigSectionName);

			return config;
		}
	}
}
