using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using log4net;
using Ninject;
using PersonalLib2.Data;
using SIGePro.Net.Navigation;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Threading;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SIGePro.Net
{
    /// <summary>
    /// Descrizione di riepilogo per BasePage.
    /// </summary>
    public class BasePage : Ninject.Web.PageBase
    {

        [Inject]
        public IConfigurazioneGenerale _configurazioneGenerale { get; set; }
        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        [Inject]
        public IBindingFactory _bindingFactory { get; set; }

        [Inject]
        public IAuthenticationInfoResolver AuthenticationInfoResolver { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(BasePage));

        public enum AmbitoErroreEnum
        {
            Ricerca,
            Inserimento,
            Aggiornamento,
            Cancellazione
        }

        protected string BaseUrl
        {
            get
            {
                //return Request.QueryString["BaseUrl"];

                return this._configurazioneGenerale.GetApplicationInfoValue("BASE_URL");
            }
        }

        public AuthenticationInfo CheckToken(string token) => this._authenticationManager.CheckToken(token);

        protected string JavaBaseUrl => this._configurazioneGenerale.AppJava;

        private NavigationManager NavigationManager { get; set; }

        private DataBase m_db = null;

        public bool VerificaPermessi { get; set; } = true;

        private AuthenticationInfo m_authenticationInfo = null;

        public DataBase Database
        {
            get
            {
                if (this.m_db == null)
                    this.m_db = this.AuthenticationInfo.CreateDatabase();

                return this.m_db;
            }
        }

        protected bool IsInserting
        {
            get { var o = this.ViewState["IsInserting"]; return o == null ? true : (bool)o; }
            set { this.ViewState["IsInserting"] = value; }
        }

        protected bool IsInPopup
        {
            get { var isInPopup = this.Request.QueryString["Popup"]; return String.IsNullOrEmpty(isInPopup) ? false : Convert.ToBoolean(isInPopup); }
        }

        public AuthenticationInfo AuthenticationInfo
        {
            get
            {
                if (this.m_authenticationInfo == null)
                {
                    this.m_authenticationInfo = this.AuthenticationInfoResolver.Resolve();

                    if (this.m_authenticationInfo == null)
                    {
                        //NavigationManager.RedirectToSigeproPage("sessionescaduta.asp", "");
                    }
                }

                return this.m_authenticationInfo;
            }
        }

        public string IdComune
        {
            get { return this.AuthenticationInfo.IdComune; }
        }

        public string IdComuneAlias
        {
            get { return this.AuthenticationInfo.Alias; }
        }

        public BasePage()
        {
            this.Load += new EventHandler(this.BasePage_Load);

            this.Error += new EventHandler(this.BasePage_Error);

            var uiCulture = new CultureInfo("it-IT");
            uiCulture.NumberFormat.NumberDecimalSeparator = ",";
            uiCulture.NumberFormat.NumberGroupSeparator = ".";
            uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
            uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

            Thread.CurrentThread.CurrentUICulture = uiCulture;
            Thread.CurrentThread.CurrentCulture = uiCulture;
        }

        protected string Token
        {
            get { return this.Request.QueryString["Token"]; }
        }

        public virtual string Software
        {
            get { return this.Request.QueryString["Software"]; }
        }

        public virtual string CodiceComune
        {
            get { return this.Request.QueryString["CodiceComune"]; }
        }

        private void BasePage_Error(object sender, EventArgs e)
        {
            /*
			Exception ex = Server.GetLastError();

			try
			{
				if (ex != null)
				{
					Logger.LogEvent(AuthenticationInfo, this.ToString(), ex.ToString(), "BO_NET");

					Server.ClearError();
				}

				Response.Redirect("~/Error.aspx");
			}
			catch (Exception exc)
			{
				Response.Write(exc.ToString() + "<br><br><b>From</b>" + ex.ToString());
			}
			*/
        }


        private void BasePage_Load(object sender, EventArgs e)
        {
            this.NavigationManager = new NavigationManager(this.BaseUrl, this.Request.QueryString["ReturnTo"]);

            //MenuMgr m = new MenuMgr(this.Database);
            /*
            try
            {
                if (verificaPermessi)
                {
                    bool seAbilitato = m.SeAbilitato(HttpContext.Current.Request.AppRelativeCurrentExecutionFilePath.Substring(2).ToUpper(), AuthenticationInfo.CodiceResponsabile, Software, IdComune);

                    if (!seAbilitato)
                        throw new AccessoNegatoException("L'accesso alla pagina richiesta è stato negato");
                }
            }
            catch (Exception ex)
            {
                MostraErrore(ex);
            }
			*/
            /*if( !String.IsNullOrEmpty( Request.QueryString["ReturnTo"] ) )
				NavigationManager.CallingPage = Request.QueryString["ReturnTo"];
			 */

            this.Response.Cache.SetCacheability(HttpCacheability.NoCache);
            this.Response.Cache.SetNoServerCaching();
            this.Response.Cache.SetNoStore();
            this.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));



        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);



            if (String.IsNullOrEmpty(this.Token))
                throw new EmptyTokenException();

            if (this.AuthenticationInfo == null)
            {
#if DEBUG 
                throw new InvalidTokenException(this.Token);
#else
                RedirectToSigeproPage("sessionescaduta.asp", "");
#endif
            }

            if (/*VerificaSoftware && */String.IsNullOrEmpty(this.Software))
                throw new ArgumentException("Software non impostato");

            // Inizializzazione degli elementi salvati nel contesto http
            HttpContext.Current.Items["Token"] = this.Token;
            HttpContext.Current.Items["IdComune"] = this.IdComune;
            HttpContext.Current.Items["Software"] = this.Software;
        }

        public string GetUrlStili()
        {
            var defaultUrl = "~/Stili/Sigepro.css";

            if (this.AuthenticationInfo == null) return defaultUrl;

            var nomeStile = new ConfigurazioneUtenteMgr(this.AuthenticationInfo.CreateDatabase()).GetValoreParametro(this.IdComune, this.AuthenticationInfo.CodiceResponsabile.GetValueOrDefault(int.MinValue), "StileBO", "standard.css");

            nomeStile = nomeStile.ToLower().Replace(".css", "");

            var urlCss = "~/stili/" + nomeStile + "/Sigepro.css";

            return urlCss;
        }


        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            // Aggiunge il foglio di stile
            foreach (var control in this.Controls)
            {
                this.ConfiguraGridView((Control)control);
            }


            // Registra lo script di avvio

            var sm = ScriptManager.GetCurrent(this.Page);

            if (sm != null)
                ScriptManager.RegisterStartupScript(this.Page, typeof(string), "FixLayout", "FixLayout();", true);
        }

        private void ConfiguraGridView(Control c)
        {
            if (c is GridView)
            {
                var gv = (GridView)c;

                if (gv.HeaderRow?.TableSection != null)
                {
                    gv.HeaderRow.TableSection = TableRowSection.TableHeader;
                }
            }

            foreach (var ctrl in c.Controls)
            {
                this.ConfiguraGridView((Control)ctrl);
            }
        }

        private ScriptManager FindScriptManager(ControlCollection controlCollection)
        {
            return ScriptManager.GetCurrent(this.Page);
            /*
			if (controlCollection == null) return null;

			foreach (Control c in controlCollection)
			{
				if (c is ScriptManager)
					return (ScriptManager)c;

				ScriptManager childSm = FindScriptManager( c.Controls );

				if (childSm != null) return childSm;
			}

			return null;*/
        }

        protected virtual void MostraErrore(Exception ex)
        {
            if (!(ex is ThreadAbortException))
            {
                this._log.ErrorFormat($"url={HttpContext.Current.Request.AppRelativeCurrentExecutionFilePath.Substring(2)}, qs={this.Request.QueryString}  =>  {ex}");

                var pagina = HttpContext.Current.Request.AppRelativeCurrentExecutionFilePath.Substring(2);

                this.Session["EXCEPTION"] = ex;
                this.Session["QUERYSTRING"] = HttpContext.Current.Request.AppRelativeCurrentExecutionFilePath.Substring(2) + "?" + this.Request.QueryString.ToString();
            }
        }

        protected virtual void MostraErrore(string messaggio, Exception ex)
        {

            var script = "alert(\"{0}\");";

            script = String.Format(script, messaggio.Replace("'", "\\'").Replace("\n", "\\n").Replace("\r", "").Replace("\"", "\\\""));

            var sm = ScriptManager.GetCurrent(this.Page);

            if (sm == null)
            {
                this.Page.ClientScript.RegisterStartupScript(this.GetType(), "errore", script, true);
            }
            else
            {
                ScriptManager.RegisterStartupScript(this.Page, this.GetType(), "errore", script, true);
            }

            if (!(ex is ThreadAbortException))
            {
                var exc = new Exception(messaggio, ex);
                this.MostraErrore(exc);
            }
        }

        public void MostraErrore(AmbitoErroreEnum ambito, Exception ex)
        {
            var errMsg = "Errore durante ";

            switch (ambito)
            {
                case (AmbitoErroreEnum.Ricerca):
                    errMsg += "la ricerca";
                    break;
                case (AmbitoErroreEnum.Inserimento):
                    errMsg += "l'inserimento";
                    break;
                case (AmbitoErroreEnum.Aggiornamento):
                    errMsg += "l'aggiornamento";
                    break;
                case (AmbitoErroreEnum.Cancellazione):
                    errMsg += "la cancellazione";
                    break;
                default:
                    errMsg += "Si è verificato un errore";
                    break;
            }

            errMsg += ": ";
            errMsg += ex.Message;

            this.MostraErrore(errMsg, ex);
        }

        protected void ImpostaScriptEliminazione(WebControl ctrl)
        {
            ImpostaScriptEliminazione(this.Page, ctrl);
        }

        public static void ImpostaScriptEliminazione(Page page, WebControl ctrl)
        {
            ctrl.Attributes.Add("onclick", "return confermaEliminazione()");
        }

        public IntestazionePaginaTipiTabEnum TabSelezionato
        {
            get
            {
                var o = this.ViewState["SelectedTab"];
                return (o == null) ? IntestazionePaginaTipiTabEnum.Ricerca : (IntestazionePaginaTipiTabEnum)o;
            }

            set { this.ViewState["SelectedTab"] = value; }
        }

        #region Navigazione

        /// <summary>
        /// Chiude la pagina corrente effettuando il redirect alla pagina di sigepro da cui è stata chiamata
        /// Se in popup chiude la finestra corrente
        /// </summary>
        protected void CloseCurrentPage()
        {
            if (this.IsInPopup)
            {
                var scriptKey = "closePage";
                if (!this.Page.ClientScript.IsClientScriptBlockRegistered(this.GetType(), scriptKey))
                {
                    this.Page.ClientScript.RegisterStartupScript(this.GetType(), scriptKey, "self.close();", true);
                }

                this.Response.End();
                return;
            }
            else
            {
                this.NavigationManager.RedirectToCallingPage(this._configurazioneGenerale);
            }

        }

        /// <summary>
        /// Effettua il redirect verso una pagina di Sigepro
        /// </summary>
        /// <param name="pageUrl">percorso della pagina relativo al baseurl di Sigepro</param>
        /// <param name="queryString">Eventuale querystring della pagina</param>
        public void RedirectToSigeproPage(string pageUrl, string queryString)
        {
            this.NavigationManager.RedirectToSigeproPage(pageUrl, queryString);
        }

        /// <summary>
        /// Costruisce il path di una pagina asp di sigepro
        /// </summary>
        /// <param name="pageUrl">percorso della pagina relativo al baseurl di Sigepro</param>
        /// <param name="queryString">Eventuale querystring della pagina</param>
        /// <returns>path della pagina di sigepro</returns>
        public string BuildSigeproPath(string pageUrl, string queryString)
        {
            return this.NavigationManager.BuildSigeproPath(pageUrl, queryString);
        }

        #endregion


        //public string BuildVbg2Path(string path)
        //{
        //    var sigeproSecurityProxy = new SigeproSecurityProxy();

        //    var javaBaseUrl = sigeproSecurityProxy.GetValoreParametro("BASE_URL");

        //    if (String.IsNullOrEmpty(javaBaseUrl))
        //    {
        //        var scheme = HttpContext.Current.Request.ServerVariables["HTTPS"] == "0" ? "http" : "https";
        //        var server = HttpContext.Current.Request.ServerVariables["HTTP_HOST"];
        //        javaBaseUrl = String.Format("{0}://{1}", scheme, server);
        //    }

        //    if (!javaBaseUrl.EndsWith("/"))
        //        javaBaseUrl += "/";

        //    javaBaseUrl += sigeproSecurityProxy.GetValoreParametro("APP_JAVA");

        //    if (!javaBaseUrl.EndsWith("/"))
        //        javaBaseUrl += "/";

        //    if (path.StartsWith("/"))
        //        path = path.Substring(1);

        //    return javaBaseUrl + path;
        //}

        /// <summary>
        /// Controlla se il è stato effettutato un postback asincrono con ajax.
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        protected bool IsAjaxPostBack(System.Web.HttpRequest request)
        {
            if (request.ServerVariables["HTTP_X_MICROSOFTAJAX"] != null ||
                    request.Form["__CALLBACKID"] != null)
                return true;
            else
                return false;
        }

        #region gestione degli errori nella pagina
        protected List<string> Errori
        {
            get { return (this.Master as SigeproNetMaster).Errori; }
        }

        #endregion
    }
}
