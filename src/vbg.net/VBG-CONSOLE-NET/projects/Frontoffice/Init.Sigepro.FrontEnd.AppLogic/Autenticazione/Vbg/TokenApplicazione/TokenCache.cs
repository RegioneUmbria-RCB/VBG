using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Caching;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
	internal class TokenCache
	{
		private static class Constants
		{
			public const string CacheKeyPrefix = "TokenCache_";
		}

		int _tokenTimeout;
		ILog _log = LogManager.GetLogger(typeof(TokenCache));

		internal TokenCache(int tokenTimeout)
		{
			this._tokenTimeout = tokenTimeout;
		}

		internal string GetToken(string aliasComune)
		{
			if (HttpContext.Current == null)
			{
				_log.DebugFormat("impossibile leggere un token dalla cache http per l'alias {0}. L'oggetto cache non esiste", aliasComune);
				return null;
			}

			var cacheValue = HttpContext.Current.Cache.Get(Constants.CacheKeyPrefix + aliasComune);

			if (cacheValue == null)
			{
				_log.DebugFormat("impossibile trovare un token in cache per l'alias {0}, verrà letto un nuovo token applicativo", aliasComune);
				return null;
			}

			return String.IsNullOrEmpty( cacheValue.ToString() ) ? null : cacheValue.ToString();
		}

		internal void PutInCache(string aliasComune, string token)
		{
			if (HttpContext.Current == null)
				return;

			

			var tokenExpiration = DateTime.Now.AddMinutes(this._tokenTimeout);

			HttpContext.Current.Cache.Add(Constants.CacheKeyPrefix + aliasComune,
											token,
											null,
											tokenExpiration, 
											Cache.NoSlidingExpiration, CacheItemPriority.Normal, null);

			_log.DebugFormat("Token per l'alias {0} inserito in cache. [token={1}, data scadenza={2}]", aliasComune, token , tokenExpiration.ToLongDateString());
		}
	}
}
