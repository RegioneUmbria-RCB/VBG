using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Caching;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune
{
	public class AliasToIdComuneRepository
	{
		SigeproSecurityProxy _sigeproSecurityProxy;
		ILog _log = LogManager.GetLogger(typeof(AliasToIdComuneRepository));

		public AliasToIdComuneRepository(SigeproSecurityProxy sigeproSecurityProxy)
		{
			this._sigeproSecurityProxy = sigeproSecurityProxy;
		}

		public string GetIdComuneDaAliasComune(string aliasComune)
		{
			if (HttpContext.Current == null)
			{
				_log.Debug("Il contesto http non esiste, la traduzione dell'alias verrà effettuata dal web service");
				return LeggiIdComune(aliasComune);
			}
			var cacheKey = "aliasComuneToIdComune_" + aliasComune;
			var idComune = HttpContext.Current.Cache[cacheKey];

			if (idComune == null || String.IsNullOrEmpty(idComune.ToString()))
			{
				_log.Debug("La traduzione dell'alias non è stata trovata in cache, verrà effettuata dal web service");

				idComune = LeggiIdComune(aliasComune);

				HttpContext.Current.Cache.Add(cacheKey, idComune, null, DateTime.Now.AddDays(1), Cache.NoSlidingExpiration, CacheItemPriority.High, null);
				_log.DebugFormat("La traduzione dell'alias {0} è stata inserita in cache", aliasComune);
			}
			else
			{
				_log.DebugFormat("La traduzione dell'alias {0} è stata trovata in cache", aliasComune);
			}


			return idComune.ToString();
		}

		private string LeggiIdComune(string aliasComune)
		{
			var token = this._sigeproSecurityProxy.GetApplicationToken(aliasComune);
			var appInfo = this._sigeproSecurityProxy.CheckToken(token);

			return appInfo.tokenInfo.idcomune;
		}
	}
}
