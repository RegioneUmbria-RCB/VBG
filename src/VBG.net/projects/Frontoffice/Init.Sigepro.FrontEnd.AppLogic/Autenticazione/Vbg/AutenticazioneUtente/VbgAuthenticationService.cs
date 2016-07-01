using System;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente
{
	public class VbgAuthenticationService
	{
		IAnagraficheService _anagraficheService;
		IConfigurazione<ParametriLogin> _configurazione;
		SigeproSecurityProxy _sigeproSecurityProxy;

		ILog _log = LogManager.GetLogger(typeof(VbgAuthenticationService));

		UserTokenCache _tokenCache;


		public VbgAuthenticationService(IAnagraficheService anagraficheService, IConfigurazione<ParametriLogin> configurazione, SigeproSecurityProxy sigeproSecurityProxy, IConfigurazione<ParametriSigeproSecurity> parametriSigeproSecurity)
		{
			this._anagraficheService = anagraficheService;
			this._configurazione = configurazione;
			this._sigeproSecurityProxy = sigeproSecurityProxy;
			this._tokenCache = new UserTokenCache(parametriSigeproSecurity.Parametri.TokenTimeout);
		}


		public void RedirectToAuthenticationPage(string idComune, string software, string returnTo)
		{
			string authUrl =  this._configurazione.Parametri.UrlLogin;

			var queryStringArgs = "?idcomunealias={0}&software={1}&contesto=UTE&return_to={2}";

			authUrl += String.Format(queryStringArgs, idComune, software, HttpContext.Current.Server.UrlEncode(returnTo));

			_log.DebugFormat("Redirect alla pagina di login {0} per idcomune={1} e software={2}", authUrl, idComune, software);

			HttpContext.Current.Response.Redirect(authUrl);
		}

		public UserAuthenticationResult CheckToken(string token)
		{
			var valoreInCache = _tokenCache.CheckToken(token);

			if (valoreInCache == null)
			{
				_log.DebugFormat("il token utente {0} non è stato trovato in cache e verrà riletto dal servizio sigeprosecurity", token);

				valoreInCache = LeggiDatiToken( token );

				if (valoreInCache != null)
					_tokenCache.PutInCache(token, valoreInCache);
			}

			return valoreInCache;
		}

		private UserAuthenticationResult LeggiDatiToken(string token)
		{
			var result = this._sigeproSecurityProxy.CheckToken(token);

			if (!result.valid)
				return null;

			var datiAnagrafici = _anagraficheService.GetByUserId(result.tokenInfo.alias, result.tokenInfo.userid);

			if (datiAnagrafici == null)
			{
				_log.ErrorFormat("Non è stato possibile leggere i dati dell'anagrafica con codice {0}", result.tokenInfo.userid);

				return null;
			}

			var anagraficaAdattata = new AnagrafeAdapter(datiAnagrafici).ToAnagraficaUtente();

            var livelloAutenticazione = this._sigeproSecurityProxy.GetLivelloAutenticazione(token);

            return new UserAuthenticationResult(token, result.tokenInfo.alias, anagraficaAdattata, livelloAutenticazione);
		}
	}
}
