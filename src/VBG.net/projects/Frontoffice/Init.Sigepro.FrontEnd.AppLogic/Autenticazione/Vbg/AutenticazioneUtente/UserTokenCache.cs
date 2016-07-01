using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using System.Web;
using System.Web.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
	internal class UserTokenCache
	{
		private static class Constants
		{
			public const string CacheKeyPrefix = "UserTokenCache_";
		}

		int _tokenTimeout;
		ILog _log = LogManager.GetLogger(typeof(UserTokenCache));

		internal UserTokenCache(int tokenTimeout)
		{
			this._tokenTimeout = tokenTimeout;
		}

		internal UserAuthenticationResult CheckToken(string token)
		{
			if (HttpContext.Current == null)
			{
				_log.DebugFormat("Impossibile validare il token {0}. L'oggetto cache non esiste", token);
				return null;
			}

			var cacheValue = HttpContext.Current.Cache.Get(Constants.CacheKeyPrefix + token);

			if (cacheValue == null)
			{
				_log.DebugFormat("Impossibile validare il token {0}. L'oggetto non esiste nella cache http.", token);
				return null;
			}

			return (UserAuthenticationResult)cacheValue;
		}

		internal void PutInCache(string token, UserAuthenticationResult uar)
		{
			if (HttpContext.Current == null)
				return;

			var tokenExpiration = DateTime.Now.AddMinutes(this._tokenTimeout);

			HttpContext.Current.Cache.Add(Constants.CacheKeyPrefix + token,
											uar,
											null,
											tokenExpiration,
											Cache.NoSlidingExpiration, CacheItemPriority.Normal, null);

			_log.DebugFormat("Token utente {0} inserito in cache. [data scadenza={1}]", token, token, tokenExpiration.ToString("dd/MM/yyyy HH:mm:ss"));
		}
	}
}
