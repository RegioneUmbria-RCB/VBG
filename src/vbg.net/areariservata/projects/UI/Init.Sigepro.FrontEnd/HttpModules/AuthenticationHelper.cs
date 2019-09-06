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
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using System.Web.Security;

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
            public const string Note = "Note";
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
			// public const string Token = "Token";
			// public const string IdComune = "IdComune";
			public const string ReturnTo = "ReturnTo";
			// public const string Software = "Software";
		}
		#endregion


		[Inject]
		public IAnagraficheService _anagrafeService { get;set; }
		[Inject]
		public VbgAuthenticationService _authenticationService{ get; set; }
		[Inject]
		public ITokenApplicazioneService _tokenApplicazioneService { get; set; }
        [Inject]
        public IUserCredentialsStorage _userCredentialsStorage { get; set; }
        [Inject]
        public ITokenResolver _tokenResolver { get; set; }
        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }

        bool _usaPathRelativo = false;
        bool _usaNginx = false;

		HttpContext _context;
		ILog logger = LogManager.GetLogger(typeof(AuthenticationModule));

		public AuthenticationHelper(HttpContext context)
		{
            this._usaPathRelativo = GetConfigValue("UsaUrlRelativiPerRedirect");
            this._usaNginx = GetConfigValue("Nginx.Enabled");

			this._context = context;
		}

        private bool GetConfigValue(string configKey)
        {
            var val = ConfigurationManager.AppSettings[configKey];

            if (!String.IsNullOrEmpty(val) && val.ToUpper() == "TRUE")
            {
                return true;
            }

            return false;
        }

		public void CheckAuthentication()
		{
			var fullPath  = this._context.Request.Url.ToString();
			var localPath = new ApplicationPath(this._context.Request.Url.LocalPath);


			if (localPath.IsReserved)
			{
				FoKernelContainer.Inject(this);
               

				// Testo l'autenticazione
				var token    = this._tokenResolver.Token;
				var alias = this._aliasSoftwareResolver.AliasComune;
				var software = this._aliasSoftwareResolver.Software;
				var returnTo = this._context.Request.QueryString[QuerystringConstants.ReturnTo];

				if (String.IsNullOrEmpty(alias))
					throw new ArgumentException("Alias/IdComune non impostato");

                if (String.IsNullOrEmpty(software))
                {
                    // Software non impostato: errore
                    throw new ArgumentException("Software non impostato");
                }

                if (!String.IsNullOrEmpty(returnTo))
					this._context.Response.Cookies.Add(new HttpCookie(QuerystringConstants.ReturnTo, returnTo));

				var remoteIp = this._context.Request.UserHostAddress;
				var userAgent = this._context.Request.UserAgent;

				logger.Debug($"Accesso al path riservato {fullPath}. IdComune={alias}, Token={token}, Software={software}, ClientIP={remoteIp}, UserAgent={userAgent}");
                
				var authType = ApplicationAuthenticationType.FromRequest(this._context.Request);
                
				// L'utente non si è ancora autenticato, effettuo il redirect alla pagina di autenticazione
				if (String.IsNullOrEmpty(token))
				{
                    //this._context.Items["descrizione-errore-registrazione"] = "";
                    //this._context.Items["descrizione-errore-codice"] ="";



                    // Nuova registrazione utente: inserisco l'anagrafica e 
                    // faccio il redirect verso la pagina di avvenuta registrazione
                    if (authType.IsRegistrazione)
                    {
                        var esito = CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());

                        if (esito.Esito)
                        {
                            this._redirectService.RedirectToUrlRegistrazioneCompletata();
                        }
                        else
                        {
                            this._context.Items["errore-registrazione-codice"] = esito.CodiceErrore;
                            this._context.Items["errore-registrazione-descrizione"] = esito.DescrizioneErrore;
                            this.logger.ErrorFormat($"errore in fase di registrazione dell'anagrafica. Codice errore {esito.CodiceErrore}, descrizione: {esito.DescrizioneErrore}");

                            this._context.Server.Transfer(RedirectService.Constants.Urls.RegistrazioneCompletata, true);
                        }

                        // CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());
                        // this._redirectService.RedirectToUrlRegistrazioneCompletata();
                        return;
                    }

                    // Nuova registrazione con cie: inserisco l'anagrafica e 
                    // faccio il redirect verso la pagina di avvenuta registrazione
                    if (authType.IsRegistrazioneSmartCard)
                    {
                        CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());
                        this._redirectService.RedirectToUrlRegistrazioneCompletataCie();
                        return;
                    }

                    if (authType.IsRegistrazioneSSO)
                    {
                        CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());
                        var redirUrl = RigeneraPathCompletoSenzaTokenReturnToEAuthType();
                        this._authenticationService.RedirectToAuthenticationPage(alias, software, redirUrl);

                        return;
                    }

                    if (authType.IsLogin)
                    {
                        token = this._context.Request.Form["token"];

                        if (String.IsNullOrEmpty(token))
                        {
                            token = this._context.Request.QueryString["token"];
                        }
                        FormsAuthentication.SetAuthCookie(token, false);
                    }
                    else
                    {
                        var tmpToken = this._context.Request.QueryString["token"];

                        if (!String.IsNullOrEmpty(tmpToken))
                        {
                            FormsAuthentication.SetAuthCookie(token, false);

                            token = tmpToken;
                        }
                    }
				}

				var uar = _authenticationService.CheckToken(token);

				if (uar == null)
				{
                    FormsAuthentication.SignOut();

                    var redirUrl = RigeneraPathCompletoSenzaTokenReturnToEAuthType();

                    logger.InfoFormat("Token {0} non valido o scaduto, redirect alla pagina di login, ClientIP={1}, UserAgent={2}, redirUrl={3}", token, remoteIp, userAgent, redirUrl);


					// Token non valido o non passato: errore o redirect alla pagina di login
					_authenticationService.RedirectToAuthenticationPage(alias, software, redirUrl);
					return;
				}

				this._context.Items[ContextConstants.Token] = _tokenApplicazioneService.GetToken(alias);
				this._context.Items[ContextConstants.IdComune] = alias;
				this._context.Items[ContextConstants.UserAuthenticationResult] = uar;
				this._context.Items[ContextConstants.Software] = software;

                this._userCredentialsStorage.Set(uar);
			}
		}



		private CreazioneAnagraficaResult CreaNuovaAnagrafica(string idComune,NameValueCollection formData, string authType)
		{
			var anagrafe = CreaAnagraficaDaValoriForm(formData);

			anagrafe.IDCOMUNE = idComune;

            return this._anagrafeService.CreaAnagrafica( new RichiestaCreazioneAnagraficaDto(anagrafe, authType));
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
				Pec					= formData[CampiAnagrafeConstants.Pec],
                NOTE                = formData[CampiAnagrafeConstants.Note]
			};

			if(logger.IsDebugEnabled)
				logger.DebugFormat("Adattamento dati anagrafici da request-form terminato: {0}", StreamUtils.SerializeClass( rVal ));

			return rVal;

		}

		private string RigeneraPathCompletoSenzaTokenReturnToEAuthType()
		{
            var url = HttpContext.Current.Request.Url.ToString();
            var parts = url.Split('?');

            //if (!this._usaNginx)
            //{
                if (this._usaPathRelativo)
                {
                    parts = new[]{
                        HttpContext.Current.Request.CurrentExecutionFilePath.ToString(),
                        HttpContext.Current.Request.QueryString.ToString()
                    };
                }
            else
                {
            //}
            //else
            //{
                var serverVars = HttpContext.Current.Request.ServerVariables;
                var scheme = serverVars["HTTPS"] == "on" ? "https://" : "http://";
                var port = serverVars["SERVER_PORT"];
                var scriptName = serverVars["SCRIPT_NAME"];
                var host = serverVars["HTTP_HOST"];

                if (port == "80" || port == "443")
                {
                    port = String.Empty;
                }

                if (!String.IsNullOrEmpty(port))
                {
                    port = ":" + port;
                }


                port = "";
                scheme = "//";

                parts = new string[] {
                    scheme + host + port + scriptName
                };
            }

			var sbQueryString = new StringBuilder();

			var qsKeys = HttpContext.Current.Request.QueryString.AllKeys;

			var chiaviDaEscludere = new List<string>{
				"AUTH_TYPE",
				"TOKEN",
				"RETURNTO"
			};

            var newQsParts = qsKeys.Where(x => x != null && !chiaviDaEscludere.Contains(x.ToUpper()))
                                    .Select(x => $"{x}={HttpContext.Current.Server.UrlEncode(HttpContext.Current.Request.QueryString[x])}");

            var qs = String.Join("&", newQsParts.ToArray());
			
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