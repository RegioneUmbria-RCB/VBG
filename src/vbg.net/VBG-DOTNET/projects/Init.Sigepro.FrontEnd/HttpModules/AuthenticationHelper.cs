using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.Navigation;
using Init.Utils;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Web;
using VBG.Frontend.AppLogic.WsAnagraficheService;

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
        public IAnagraficheBackendService _anagrafeService { get; set; }
        [Inject]
        public IVbgAuthenticationService _authenticationService { get; set; }
        [Inject]
        public ITokenApplicazioneService _tokenApplicazioneService { get; set; }
        [Inject]
        public IUserCredentialsStorage _userCredentialsStorage { get; set; }
        [Inject]
        public ITokenResolver _tokenResolver { get; set; }
        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }

        [Inject]
        public IAuthenticationUrlBuilder _authUrlBuilder { get; set; }

        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        private readonly bool _usaPathRelativo = false;
        private readonly bool _usaNginx = false;
        private readonly HttpContext _context;
        private readonly ILog _log = LogManager.GetLogger(typeof(AuthenticationModule));

        public AuthenticationHelper(HttpContext context)
        {
            this._usaPathRelativo = this.GetConfigValue("UsaUrlRelativiPerRedirect");
            this._usaNginx = this.GetConfigValue("Nginx.Enabled");

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
            var localPath = new ApplicationPath(this._context.Request.Url.LocalPath);

            if (localPath.IsReserved)
            {
                this.ForceCheckAuthentication();
            }
        }

        public void ForceCheckAuthentication()
        {
            FoKernelContainer.Inject(this);

            var fullPath = this._context.Request.Url.ToString();

            // Testo l'autenticazione
            var token = this._tokenResolver.Token;
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

            this._log.Debug($"Accesso al path riservato {fullPath}. IdComune={alias}, Token={token}, Software={software}, ClientIP={remoteIp}, UserAgent={userAgent}");

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
                    var esito = this.CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());

                    if (esito.Esito)
                    {
                        this._redirectService.RedirectToUrlRegistrazioneCompletata();
                    }
                    else
                    {
                        this._context.Items["errore-registrazione-codice"] = esito.CodiceErrore;
                        this._context.Items["errore-registrazione-descrizione"] = esito.DescrizioneErrore;
                        this._log.ErrorFormat($"errore in fase di registrazione dell'anagrafica. Codice errore {esito.CodiceErrore}, descrizione: {esito.DescrizioneErrore}");

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
                    this.CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());
                    this._redirectService.RedirectToUrlRegistrazioneCompletataCie();
                    return;
                }

                if (authType.IsRegistrazioneSSO)
                {
                    this.CreaNuovaAnagrafica(alias, this._context.Request.Form, authType.ToString());
                    var redirUrl = this.RigeneraPathCompletoSenzaTokenReturnToEAuthType();
                    var loginUrl = this._authUrlBuilder.BuildAuthenticationUrl(alias, software, redirUrl);
                    this._context.Response.Redirect(loginUrl);
                    return;
                }

                if (authType.IsLogin)
                {
                    token = this._context.Request.Form["token"];

                    if (String.IsNullOrEmpty(token))
                    {
                        token = this._context.Request.QueryString["token"];
                    }
                    this._areaRiservataAuthenticationService.AuthenticateUser(token);
                }
                else
                {
                    var tmpToken = this._context.Request.QueryString["token"];

                    if (!String.IsNullOrEmpty(tmpToken))
                    {
                        this._areaRiservataAuthenticationService.AuthenticateUser(token);

                        token = tmpToken;
                    }
                }
            }

            var uar = this._authenticationService.CheckToken(token);

            if (uar == null)
            {
                this._areaRiservataAuthenticationService.SignOut();

                var redirUrl = this.RigeneraPathCompletoSenzaTokenReturnToEAuthType();

                this._log.InfoFormat("Token {0} non valido o scaduto, redirect alla pagina di login, ClientIP={1}, UserAgent={2}, redirUrl={3}", token, remoteIp, userAgent, redirUrl);


                // Token non valido o non passato: errore o redirect alla pagina di login
                var loginUrl = this._authUrlBuilder.BuildAuthenticationUrl(alias, software, redirUrl);
                this._context.Response.Redirect(loginUrl);
                return;
            }

            this._context.Items[ContextConstants.Token] = this._tokenApplicazioneService.GetToken(alias);
            this._context.Items[ContextConstants.IdComune] = alias;
            this._context.Items[ContextConstants.UserAuthenticationResult] = uar;
            this._context.Items[ContextConstants.Software] = software;

            this._userCredentialsStorage.Set(uar);
        }

        private CreazioneAnagraficaResult CreaNuovaAnagrafica(string idComune, NameValueCollection formData, string authType)
        {
            var anagrafe = this.CreaAnagraficaDaValoriForm(formData);

            anagrafe.IDCOMUNE = idComune;

            return this._anagrafeService.CreaAnagrafica(new RichiestaCreazioneAnagraficaDto(anagrafe, authType));
        }


        private Anagrafe CreaAnagraficaDaValoriForm(NameValueCollection formData)
        {
            this._log.DebugFormat("Inizio adattamento dati anagrafici da request-form: {0}", this._context.Request.Form);

            var rVal = new Anagrafe
            {
                NOME = formData[CampiAnagrafeConstants.Nome],
                NOMINATIVO = formData[CampiAnagrafeConstants.Cognome],
                SESSO = formData[CampiAnagrafeConstants.Sesso],
                DATANASCITA = new DataDaStringParser(formData[CampiAnagrafeConstants.DataNascita]).Parse(),
                CODCOMNASCITA = formData[CampiAnagrafeConstants.ComuneNascita],
                CODICEFISCALE = formData[CampiAnagrafeConstants.CodiceFiscale],
                EMAIL = formData[CampiAnagrafeConstants.Email],
                INDIRIZZO = formData[CampiAnagrafeConstants.IndirizzoResidenza],
                COMUNERESIDENZA = formData[CampiAnagrafeConstants.ComuneResidenza],
                CITTA = formData[CampiAnagrafeConstants.LocalitaResidenza],
                CAP = formData[CampiAnagrafeConstants.CapResidenza],
                Username = formData[CampiAnagrafeConstants.Username],
                INDIRIZZOCORRISPONDENZA = formData[CampiAnagrafeConstants.IndirizzoCorrispondenza],
                COMUNECORRISPONDENZA = formData[CampiAnagrafeConstants.ComuneCorrispondenza],
                CAPCORRISPONDENZA = formData[CampiAnagrafeConstants.CapCorrispondenza],
                CITTACORRISPONDENZA = formData[CampiAnagrafeConstants.CittaCorrispondenza],
                TELEFONO = formData[CampiAnagrafeConstants.Telefono],
                TELEFONOCELLULARE = formData[CampiAnagrafeConstants.Cellulare],
                Pec = formData[CampiAnagrafeConstants.Pec],
                NOTE = formData[CampiAnagrafeConstants.Note]
            };

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Adattamento dati anagrafici da request-form terminato: {0}", StreamUtils.SerializeClass(rVal));

            return rVal;

        }

        private string RigeneraPathCompletoSenzaTokenReturnToEAuthType()
        {
            var url = HttpContext.Current.Request.Url.ToString();
            var baseUrl = HttpContext.Current.Request.CurrentExecutionFilePath.ToString();

            //if (!this._usaNginx)
            //{
            if (this._usaPathRelativo)
            {
                this._log.Debug("Applicazione configurata per utilizzare i path relativi");
            }
            else
            {
                //}
                //else
                //{
                var serverVars = HttpContext.Current.Request.ServerVariables;
                // var scheme = serverVars["HTTPS"] == "on" ? "https://" : "http://";
                // var port = serverVars["SERVER_PORT"];
                var scriptName = serverVars["SCRIPT_NAME"];
                var host = serverVars["HTTP_HOST"];

                this._log.Debug($"Host per redirect: {host}");

                if (!string.IsNullOrEmpty(serverVars["X-Forwarded-Host"]))
                {
                    host = serverVars["X-Forwarded-Host"];

                    this._log.Debug($"X-Forwarded-Host trovato nelle server variables, nuovo host per redirect: {host}");
                }

                if (!string.IsNullOrEmpty(serverVars["HTTP_X_FORWARDED_HOST"]))
                {
                    host = serverVars["HTTP_X_FORWARDED_HOST"];

                    this._log.Debug($"HTTP_X_FORWARDED_HOST trovato nelle server variables, nuovo host per redirect: {host}");
                }

                var scheme = "//";

                baseUrl = scheme + host + scriptName;
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

            return baseUrl + "?" + qs;
        }

        private bool IsReservedPath(string path)
        {
            var upath = path.ToUpper();

            if (upath.EndsWith(PathConstants.AppletCompilazioneOggetti))
                return false;

            if (upath.EndsWith(PathConstants.JavascriptFileExtension))
                return false;

            if (upath.IndexOf(PathConstants.DatiDinamiciScriptServicePath) > -1)
                return false;

            if (upath.IndexOf(PathConstants.FirmaAppletPath) > -1)
                return false;

            return (upath.IndexOf(PathConstants.ReservedPath) != -1);
        }

    }
}