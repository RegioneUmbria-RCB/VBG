using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
	public class TokenApplicazioneRepository
	{
		ILog				 _log = LogManager.GetLogger(typeof(TokenApplicazioneRepository));
		SigeproSecurityProxy _sigeproSecurityProxy;
		TokenCache			 _tokenCache;


		public TokenApplicazioneRepository(SigeproSecurityProxy sigeproSecurityProxy, IConfigurazione<ParametriSigeproSecurity> configurazione)
		{
			this._sigeproSecurityProxy = sigeproSecurityProxy;
			this._tokenCache = new TokenCache(configurazione.Parametri.TokenTimeout);
		}


		public string GetTokenByAliasComune(string aliasComune)
		{
			var token = this._tokenCache.GetToken( aliasComune );

			if (token == null)
			{
				token = _sigeproSecurityProxy.GetApplicationToken(aliasComune);
				this._tokenCache.PutInCache(aliasComune, token);
			}

			return token;
		}


	}
}
