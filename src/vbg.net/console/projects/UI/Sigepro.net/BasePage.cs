using System;
using System.Web.UI;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Utils;
using Init.Utils;
using PersonalLib2.Data;
using SIGePro.Net.Navigation;
using System.Web.UI.WebControls;
using System.Globalization;
using System.Threading;
using Init.SIGePro.Exceptions.Token;
using System.Web;
using System.Collections.Generic;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Manager.Manager;
using Sigepro.net;
using Init.SIGePro;
using Init.SIGePro.Manager.Authentication;
using log4net;

namespace SIGePro.Net
{
	/// <summary>
	/// Descrizione di riepilogo per BasePage.
	/// </summary>
	public class BasePage : Page
	{
		ILog _log = LogManager.GetLogger(typeof(BasePage));

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

				return AuthenticationManager.GetApplicationInfoValue("BASE_URL");
			}
		}

		protected string JavaBaseUrl
		{
			get
			{
				//return Request.QueryString["BaseUrl"];

				var baseUrl = AuthenticationManager.GetApplicationInfoValue("BASE_URL");
				var javaUrl = AuthenticationManager.GetApplicationInfoValue("APP_JAVA");

				if (!baseUrl.EndsWith("/"))
					baseUrl += "/";

				if (!javaUrl.EndsWith("/"))
					javaUrl += "/";

				return baseUrl + javaUrl;
			}
		}

		private NavigationManager m_navigationManager;

		private NavigationManager NavigationManager
		{
			get 
			{
				return m_navigationManager; 
			}
		}

		private DataBase m_db = null;

        private bool verificaPermessi = true;

        public bool VerificaPermessi
        {
            get { return verificaPermessi; }
            set { verificaPermessi = value; }
        }

		private AuthenticationInfo m_authenticationInfo = null;

		public DataBase Database
		{
			get
			{
				if (m_db == null)
					m_db = AuthenticationInfo.CreateDatabase();

				return m_db;
			}
		}

		protected bool IsInserting
		{
			get { object o = this.ViewState["IsInserting"]; return o == null ? true : (bool)o; }
			set { this.ViewState["IsInserting"] = value; }
		}

		protected bool IsInPopup
		{
			get { string isInPopup = Request.QueryString["Popup"]; return String.IsNullOrEmpty(isInPopup) ? false : Convert.ToBoolean(isInPopup); }
		}

		public AuthenticationInfo AuthenticationInfo
		{
			get
			{
				if (m_authenticationInfo == null)
				{
                    m_authenticationInfo = new CurrentRequestFromHttpContext(this.Context).GetCurrentUser();//AuthenticationManager.CheckToken( Token );

					if (m_authenticationInfo == null)
					{
						//NavigationManager.RedirectToSigeproPage("sessionescaduta.asp", "");
					}
				}

				return m_authenticationInfo;
			}
		}

		public string IdComune
		{
			get { return AuthenticationInfo.IdComune; }
		}

        public string IdComuneAlias
        {
            get { return AuthenticationInfo.Alias; }
        }

		public BasePage()
		{
			this.Load +=new EventHandler(BasePage_Load);

			this.Error += new EventHandler(BasePage_Error);

			CultureInfo uiCulture = new CultureInfo("it-IT");
			uiCulture.NumberFormat.NumberDecimalSeparator = ",";
			uiCulture.NumberFormat.NumberGroupSeparator = ".";
			uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
			uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

			Thread.CurrentThread.CurrentUICulture = uiCulture;
			Thread.CurrentThread.CurrentCulture = uiCulture;
		}

		protected string Token
		{
			get { return Request.QueryString["Token"]; }
		}

		public virtual string Software
		{
			get { return Request.QueryString["Software"]; }
		}

        public virtual string CodiceComune
        {
            get { return Request.QueryString["CodiceComune"]; }
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
            m_navigationManager = new NavigationManager(BaseUrl, Request.QueryString["ReturnTo"]);
            
            MenuMgr m = new MenuMgr(Database);
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
			/*if( !StringChecker.IsStringEmpty( Request.QueryString["ReturnTo"] ) )
				NavigationManager.CallingPage = Request.QueryString["ReturnTo"];
			 */

			Response.Cache.SetCacheability( HttpCacheability.NoCache);
			Response.Cache.SetNoServerCaching();
			Response.Cache.SetNoStore();
			Response.Cache.SetExpires(DateTime.Now.AddDays(-1));



		}

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            

            if (String.IsNullOrEmpty(Token))
                throw new EmptyTokenException();

            if (AuthenticationInfo == null)
            {
#if DEBUG 
                throw new InvalidTokenException(Token);
#else
                RedirectToSigeproPage("sessionescaduta.asp", "");
#endif
			}

            if (/*VerificaSoftware && */String.IsNullOrEmpty(Software))
                throw new ArgumentException("Software non impostato");

			// Inizializzazione degli elementi salvati nel contesto http
			HttpContext.Current.Items["Token"] = Token;
			HttpContext.Current.Items["IdComune"] = IdComune;
			HttpContext.Current.Items["Software"] = Software;
        }

		public string GetUrlStili()
		{
			string defaultUrl = "~/Stili/Sigepro.css";

			if (AuthenticationInfo == null) return defaultUrl;

            string nomeStile = new ConfigurazioneUtenteMgr(AuthenticationInfo.CreateDatabase()).GetValoreParametro(IdComune, AuthenticationInfo.CodiceResponsabile.GetValueOrDefault(int.MinValue), "StileBO", "standard.css");

			nomeStile = nomeStile.ToLower().Replace(".css", "");

			string urlCss = "~/stili/" + nomeStile + "/Sigepro.css";

			return urlCss;
		}


		protected override void OnPreRender(EventArgs e)
		{
			base.OnPreRender(e);

			// Aggiunge il foglio di stile
			


			// Registra lo script di avvio

			ScriptManager sm = ScriptManager.GetCurrent(this.Page);

			if (sm != null)
				ScriptManager.RegisterStartupScript(this.Page, typeof(string), "FixLayout", "FixLayout();", true);
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
			ILog log = LogManager.GetLogger(GetType());

			log.ErrorFormat(ex.ToString());

            if (!(ex is ThreadAbortException))
            {
                string pagina = HttpContext.Current.Request.AppRelativeCurrentExecutionFilePath.Substring(2);

                Session["EXCEPTION"] = ex;
                Session["QUERYSTRING"] = HttpContext.Current.Request.AppRelativeCurrentExecutionFilePath.Substring(2) + "?" + Request.QueryString.ToString();

                Logger.LogEvent(AuthenticationInfo, Session["QUERYSTRING"].ToString(), ex.ToString(), "ASP_NET");

                //Response.Redirect("~/ErroriSistema.aspx?token=" + Token + "&software=" + Software, true);
            }
        }

		protected virtual void MostraErrore(string messaggio , Exception ex)
		{
            
			string script = "alert(\"{0}\");";

			script = String.Format( script , messaggio.Replace("'", "\\'").Replace("\n", "\\n").Replace("\r", "").Replace("\"","\\\"") );
			
			ScriptManager sm = ScriptManager.GetCurrent( this.Page );

			if (sm == null)
			{
				Page.ClientScript.RegisterStartupScript(this.GetType(), "errore", script, true);
			}
			else
			{
				ScriptManager.RegisterStartupScript(this.Page, this.GetType(), "errore", script, true);
			}

			this._log.Error(ex.ToString());

            if (!(ex is ThreadAbortException))
            {
                Exception exc = new Exception(messaggio, ex);
                MostraErrore(exc);
            }
		}

		public void MostraErrore(AmbitoErroreEnum ambito, Exception ex)
		{
			string errMsg = "Errore durante ";

			switch (ambito)
			{
				case( AmbitoErroreEnum.Ricerca ):
					errMsg += "la ricerca";
					break;
				case(AmbitoErroreEnum.Inserimento):
					errMsg += "l'inserimento";
					break;
				case(AmbitoErroreEnum.Aggiornamento):
					errMsg += "l'aggiornamento";
					break;
				case(AmbitoErroreEnum.Cancellazione):
					errMsg += "la cancellazione";
					break;
				default:
					errMsg += "Si è verificato un errore";
					break;
			}

			errMsg += ": ";
			errMsg += ex.Message;

			MostraErrore(errMsg, ex);			
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
                object o = this.ViewState["SelectedTab"];
                return (o == null) ? IntestazionePaginaTipiTabEnum.Ricerca : (IntestazionePaginaTipiTabEnum)o;
            }

            set { this.ViewState["SelectedTab"] = value;}
		}

		#region Navigazione

		/// <summary>
		/// Chiude la pagina corrente effettuando il redirect alla pagina di sigepro da cui è stata chiamata
		/// Se in popup chiude la finestra corrente
		/// </summary>
		protected void CloseCurrentPage()
		{
			if (IsInPopup)
			{
				string scriptKey = "closePage";
				if (!Page.ClientScript.IsClientScriptBlockRegistered(this.GetType(), scriptKey))
				{
					Page.ClientScript.RegisterStartupScript(this.GetType(), scriptKey, "self.close();", true);
				}

				Response.End();
				return;
			}
			else
			{
				NavigationManager.RedirectToCallingPage();
			}

		}

		/// <summary>
		/// Effettua il redirect verso una pagina di Sigepro
		/// </summary>
		/// <param name="pageUrl">percorso della pagina relativo al baseurl di Sigepro</param>
		/// <param name="queryString">Eventuale querystring della pagina</param>
		public void RedirectToSigeproPage(string pageUrl, string queryString)
		{
			NavigationManager.RedirectToSigeproPage(pageUrl, queryString);
		}

		/// <summary>
		/// Costruisce il path di una pagina asp di sigepro
		/// </summary>
		/// <param name="pageUrl">percorso della pagina relativo al baseurl di Sigepro</param>
		/// <param name="queryString">Eventuale querystring della pagina</param>
		/// <returns>path della pagina di sigepro</returns>
		public string BuildSigeproPath(string pageUrl, string queryString)
		{
			return NavigationManager.BuildSigeproPath(pageUrl, queryString);
		}
		
		#endregion


		public string BuildVbg2Path(string path)
		{
            string javaBaseUrl = SigeproSecurityProxy.GetValoreParametro("BASE_URL");

            if (String.IsNullOrEmpty(javaBaseUrl))
            {
                var scheme = HttpContext.Current.Request.ServerVariables["HTTPS"] == "0" ? "http" : "https";
                var server = HttpContext.Current.Request.ServerVariables["HTTP_HOST"];
                javaBaseUrl = String.Format("{0}://{1}", scheme, server);
            }

			if (!javaBaseUrl.EndsWith("/"))
				javaBaseUrl += "/";

            javaBaseUrl += SigeproSecurityProxy.GetValoreParametro("APP_JAVA");

            if (!javaBaseUrl.EndsWith("/"))
                javaBaseUrl += "/";

			if(path.StartsWith("/"))
				path = path.Substring(1);

			return javaBaseUrl + path;
		}

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
