using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using System.Collections.Specialized;
using Init.Utils;
using System.Text;

namespace Init.Sigepro.FrontEnd.HttpModules
{
	public class AuthenticationHelper
	{
		#region Constants
		private static class PathConstants
		{
			public const string ReservedPath = "/RESERVED/";
			public const string DatiDinamiciScriptServicePath = "/HELPER/";
			public const string FirmaAppletPath = "/FIRMADIGITALE/APPLETS/";
			public const string JavascriptFileExtension = ".JS";
			public static string AppletCompilazioneOggetti = "/AREARISERVATA/RESERVED/INSERIMENTOISTANZA/EDITOGGETTI/APPLET/INIT-EDITDOCS-APPLET.JAR";
		}

		private static class AuthenticationTypeConstants
		{
			public const string AuthenticationType = "AUTH_TYPE";
			public const string AuthenticationTypeLogin = "AUTH_TYPE_LOGIN";
			public const string AuthenticationTypeSmartCard = "AUTH_TYPE_LOGIN_SC";
			public const string AuthenticationTypeSmartSso = "AUTH_TYPE_LOGIN_SSO";
			public const string AuthenticationTypeReg = "AUTH_TYPE_REG";
			public const string AuthenticationTypeRegSc = "AUTH_TYPE_REG_SC";
			public const string AuthenticationTypeRegSso = "AUTH_TYPE_REG_SSO";
		}

		private static class CampiAnagrafeConstants
		{
			public const string Nome = "Nome";
			public const string Cognome = "Cognome";
			public const string Sesso = "Sesso";
			public const string DataNascita = "DataNascita";
			public const string ComuneNascita = "ComuneNascita";
			public const string CodiceFiscale = "CodiceFiscale";
			public const string Email = "Email";
			public const string IndirizzoResidenza = "IndirizzoResidenza";
			public const string ComuneResidenza = "ComuneResidenza";
			public const string CapResidenza = "CapResidenza";
			public const string LocalitaResidenza = "LocalitaResidenza";		
			public const string Username = "USERNAME";
			public const string Cellulare = "Cellulare";
			public const string Telefono = "Telefono";
			public const string IndirizzoCorrispondenza = "IndirizzoCorrispondenza";
			public const string ComuneCorrispondenza = "ComuneCorrispondenza";
			public const string CapCorrispondenza = "CapCorrispondenza";
			public const string CittaCorrispondenza = "CittaCorrispondenza";
			public const string Pec = "Pec";			
		}

		private static class ContextConstants
		{
			public const string Token = "Token";
			public const string IdComune = "IdComune";
			public const string UserAuthenticationResult = "UserAuthenticationResult";
			public const string Software = "Software";
		}

		private static class QuerystringConstants
		{
			public const string Token = "Token";
			public const string IdComune = "IdComune";
			public const string ReturnTo = "ReturnTo";
			public const string Software = "Software";
		}
		#endregion


		[Inject]
		public IAnagraficheService _anagrafeService { get;set; }
		[Inject]
		public VbgAuthenticationService _authenticationService{ get; set; }
		[Inject]
		public ITokenApplicazioneService _tokenApplicazioneService { get; set; }

		HttpContext _context;
		ILog logger = LogManager.GetLogger(typeof(AuthenticationModule));

		public AuthenticationHelper(HttpContext context)
		{
			this._context = context;
		}

		public void CheckAuthentication()
		{
			var completePath = this._context.Request.Url.ToString();
			var path		 = this._context.Request.Url.LocalPath;


			if (IsReservedPath(path))
			{
				FoKernelContainer.Inject(this);

				var navigationService = new RedirectService(this._context);

				// Testo l'autenticazione
				var token = this._context.Request.QueryString[QuerystringConstants.Token];
				var idComune = this._context.Request.QueryString[QuerystringConstants.IdComune];
				var software = this._context.Request.QueryString[QuerystringConstants.Software];
				var returnTo = this._context.Request.QueryString[QuerystringConstants.ReturnTo];

				if (String.IsNullOrEmpty(idComune))
					throw new ArgumentException("IdComune non impostato");

				if (!String.IsNullOrEmpty(returnTo))
					this._context.Response.Cookies.Add(new HttpCookie(QuerystringConstants.ReturnTo, returnTo));

				var remoteIp = this._context.Request.UserHostAddress;
				var userAgent = this._context.Request.UserAgent;

				logger.DebugFormat("Accesso al path riservato {3}. IdComune={0}, Token={1}, Software={2}, ClientIP={4}, UserAgent={5}", idComune, token, software, completePath, remoteIp, userAgent);

				if (String.IsNullOrEmpty(software))
				{
					// Software non impostato: errore
					throw new ArgumentException("Software non impostato");
				}

				var authType = this._context.Request.QueryString[AuthenticationTypeConstants.AuthenticationType];


				// L'utente non si è ancora autenticato, effettuo il redirect alla pagina di autenticazione
				if (String.IsNullOrEmpty(token))
				{
					switch (authType)
					{
						// Nuova registrazione utente: inserisco l'anagrafica e 
						// faccio il redirect verso la pagina di avvenuta registrazione
						case AuthenticationTypeConstants.AuthenticationTypeReg:
							if (CreaNuovaAnagrafica(idComune, this._context.Request.Form, authType))
								navigationService.RedirectToUrlRegistrazioneCompletata(idComune, software);
							return;
						// Nuova registrazione con cie: inserisco l'anagrafica e 
						// faccio il redirect verso la pagina di avvenuta registrazione
						case AuthenticationTypeConstants.AuthenticationTypeRegSc:
							if (CreaNuovaAnagrafica(idComune, this._context.Request.Form, authType))
								navigationService.RedirectToUrlRegistrazioneCompletataCie(idComune, software);
							return;
						// Nuova registrazione con SSO (fed o simili): inserisco l'anagrafica e 
						// chiamo di nuovo la pagina di login
						case AuthenticationTypeConstants.AuthenticationTypeRegSso:
							CreaNuovaAnagrafica(idComune, this._context.Request.Form, authType);
							break;
					}

					var redirUrl = RigeneraPathCompletoSenzaTokenReturnToEAuthType();

					// TODO: al momento l'AG restituisce il token tramite post, finchè non lo passa in get sono costretto a fare 
					// un'ulteriore redirect alla stessa pagina accodando il token in querystring
					logger.DebugFormat("Token non presente in querystring per la richiesta a {0}, redirect alla pagina di login", redirUrl);

					_authenticationService.RedirectToAuthenticationPage(idComune, software, redirUrl);
					return;
				}

				var uar = _authenticationService.CheckToken(token);

				if (uar == null)
				{
					logger.InfoFormat("Token {0} non valido o scaduto, redirect alla pagina di login, ClientIP={1}, UserAgent={2}", token, remoteIp, userAgent);

					var redirUrl = RigeneraPathCompletoSenzaTokenReturnToEAuthType();

					// Token non valido o non passato: errore o redirect alla pagina di login
					_authenticationService.RedirectToAuthenticationPage(idComune, software, redirUrl);
					return;
				}

				this._context.Items[ContextConstants.Token] = _tokenApplicazioneService.GetToken(idComune);
				this._context.Items[ContextConstants.IdComune] = idComune;
				this._context.Items[ContextConstants.UserAuthenticationResult] = uar;
				this._context.Items[ContextConstants.Software] = software;
			}
		}



		private bool CreaNuovaAnagrafica(string idComune,NameValueCollection formData, string authType)
		{
			var anagrafe = CreaAnagraficaDaValoriForm(formData);

			anagrafe.IDCOMUNE = idComune;

			if (authType == AuthenticationTypeConstants.AuthenticationTypeRegSso || authType == AuthenticationTypeConstants.AuthenticationTypeRegSc )
				_anagrafeService.CreaAnagrafica( anagrafe);
			else
				_anagrafeService.NuovaRegistrazione(idComune, anagrafe);

			// TODO: valutare se implementare comportamenti diversi a seconda del tipo di autenticazione

			return true;
		}


		private Anagrafe CreaAnagraficaDaValoriForm(NameValueCollection formData)
		{
			logger.DebugFormat("Inizio adattamento dati anagrafici da request-form: {0}", this._context.Request.Form);

			var rVal = new Anagrafe
			{
				NOME			= formData[CampiAnagrafeConstants.Nome],
				NOMINATIVO		= formData[CampiAnagrafeConstants.Cognome],
				SESSO			= formData[CampiAnagrafeConstants.Sesso],
				DATANASCITA		= String.IsNullOrEmpty(formData[CampiAnagrafeConstants.DataNascita]) ? (DateTime?)null : DateTime.ParseExact(formData[CampiAnagrafeConstants.DataNascita], "dd/MM/yyyy", null),
				CODCOMNASCITA	= formData[CampiAnagrafeConstants.ComuneNascita],
				CODICEFISCALE	= formData[CampiAnagrafeConstants.CodiceFiscale],
				EMAIL			= formData[CampiAnagrafeConstants.Email],
				INDIRIZZO		= formData[CampiAnagrafeConstants.IndirizzoResidenza],
				COMUNERESIDENZA = formData[CampiAnagrafeConstants.ComuneResidenza],
				CITTA			= formData[CampiAnagrafeConstants.LocalitaResidenza],
				CAP				= formData[CampiAnagrafeConstants.CapResidenza],
				Username		= formData[CampiAnagrafeConstants.Username],
				INDIRIZZOCORRISPONDENZA = formData[CampiAnagrafeConstants.IndirizzoCorrispondenza],
				COMUNECORRISPONDENZA	= formData[CampiAnagrafeConstants.ComuneCorrispondenza],
				CAPCORRISPONDENZA		= formData[CampiAnagrafeConstants.CapCorrispondenza],
				CITTACORRISPONDENZA		= formData[CampiAnagrafeConstants.CittaCorrispondenza],
				TELEFONO			= formData[CampiAnagrafeConstants.Telefono],
				TELEFONOCELLULARE	= formData[CampiAnagrafeConstants.Cellulare],
				Pec					= formData[CampiAnagrafeConstants.Pec]
			};

			if(logger.IsDebugEnabled)
				logger.DebugFormat("Adattamento dati anagrafici da request-form terminato: {0}", StreamUtils.SerializeClass( rVal ));

			return rVal;

		}

		private string RigeneraPathCompletoSenzaTokenReturnToEAuthType()
		{
			var url = HttpContext.Current.Request.Url.ToString();
			var parts = url.Split('?');

			var sbQueryString = new StringBuilder();

			var qsKeys = HttpContext.Current.Request.QueryString.AllKeys;

			var chiaviDaEscludere = new List<string>{
				"AUTH_TYPE",
				"TOKEN",
				"RETURNTO"
			};

			for (int i = 0; i <qsKeys.Length; i++)
			{
				if (sbQueryString.Length > 0)
					sbQueryString.Append("&");

				var key = qsKeys[i];

				if (key == null)
					continue;

				var value = HttpContext.Current.Request.QueryString[key];

				if (chiaviDaEscludere.Contains(key.ToUpper()))
					continue;

				sbQueryString.Append(key).Append("=").Append( HttpContext.Current.Server.UrlEncode(  value ) );
			}

			var qs = sbQueryString.ToString();

			if (qs.EndsWith("&"))
				qs = qs.Substring(0, qs.Length - 1);
			
			return parts[0] + "?" + qs;
		}

		private bool IsReservedPath(string path)
		{
			var upath = path.ToUpper();

			if (upath.EndsWith(PathConstants.AppletCompilazioneOggetti))
				return false;

			if (upath.EndsWith(PathConstants.JavascriptFileExtension))
				return false;

			if (upath.IndexOf(PathConstants.DatiDinamiciScriptServicePath ) > -1)
				return false;

			if (upath.IndexOf(PathConstants.FirmaAppletPath) > -1)
				return false;

			return (upath.IndexOf(PathConstants.ReservedPath ) != -1);
		}

	}
}