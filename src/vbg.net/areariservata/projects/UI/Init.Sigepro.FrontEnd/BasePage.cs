using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebControls;
using log4net;
using Ninject;


namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Descrizione di riepilogo per BasePage.
	/// </summary>
	public abstract class BasePage : Ninject.Web.PageBase, IIdComunePage
	{
		ILog _log = LogManager.GetLogger(typeof(BasePage));

		[Inject]
		public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }


		public virtual string IdComune { get { return this._aliasSoftwareResolver.AliasComune; } }
		public virtual string Software { get { return this._aliasSoftwareResolver.Software; } }

        public List<string> Errori{ get; private set; }


        protected string LoadScripts(string[] scripts)
        {
            var s = scripts.Select(x => $"<script type='text/javascript' src='{ResolveClientUrl(x)}'></script>");

            return String.Join(Environment.NewLine, s.ToArray());
        }

        protected string LoadScript(string script)
        {
            return $"<script type='text/javascript' src='{ResolveClientUrl(script)}'></script>";
        }

        public BasePage()
		{
			Errori = new List<string>();

			ImpostaFormatiNumerici();

//#if ! DEBUG
			this.Error += new EventHandler(BasePage_Error);
//#endif
			this.Load += new EventHandler(BasePage_Load);
		}

		void BasePage_Load(object sender, EventArgs e)
		{
			ImpostaCacheabilitaPagina();
		}

		private void BasePage_Error(object sender, EventArgs e)
		{
			Exception ex = Server.GetLastError();

			try
			{
				if (ex != null)
				{
                    var errorId = Guid.NewGuid().ToString();
					_log.ErrorFormat("Errore nella pagina {0} (id errore: {2}):\r\n {1}", HttpContext.Current.Request.Path + HttpContext.Current.Request.QueryString, ex.ToString(), errorId);

					HttpContext.Current.Response.Write("<b>Si è verificato un errore durante l'elaborazione della pagina</b><br /><br /> Dettagli dell'errore: <br><pre>" + ex.Message + "<br /><br />Riferimento errore: " + errorId + "</pre>");

					Server.ClearError();
				}

			}
			catch (Exception)
			{
				Server.ClearError();
				throw ex;
			}
		}

        protected override void OnPreRender(EventArgs e)
        {
			GeneraTHead(this.Controls);

            base.OnPreRender(e);

            MostraErrori();
		}

		private void GeneraTHead(ControlCollection controlCollection)
		{
			foreach (Control ctrl in controlCollection)
			{
				GeneraTHead(ctrl.Controls);

				if (!(ctrl is GridView))
					continue;

				var gv = (GridView)ctrl;

				if (gv.Rows.Count > 0)
				{
					gv.UseAccessibleHeader = true;
					gv.HeaderRow.TableSection = TableRowSection.TableHeader;
                    gv.FooterRow.TableSection = TableRowSection.TableFooter;
                }
			}
		}

        protected virtual void MostraErrori()
        {
			if (Errori.Count == 0)
				return;

            MasterPage mp = this.Master;

            while (mp != null)
            {
                IMostraErroriPage master = mp as IMostraErroriPage;

                if (master != null)
					master.MostraErrori(Errori);

                mp = mp.Master;

            }
        }

		protected virtual void ImpostaCacheabilitaPagina()
		{
			Response.Cache.SetCacheability(HttpCacheability.NoCache);
			Response.Cache.SetNoServerCaching();
			Response.Cache.SetNoStore();
			Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
		}

		protected virtual void ImpostaFormatiNumerici()
		{
			CultureInfo uiCulture = new CultureInfo("it-IT");
			uiCulture.NumberFormat.NumberDecimalSeparator = ",";
			uiCulture.NumberFormat.NumberGroupSeparator = ".";
			uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
			uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

			Thread.CurrentThread.CurrentUICulture = uiCulture;
			Thread.CurrentThread.CurrentCulture = uiCulture;
		}
        
		protected string GetBaseUrlAssoluto()
		{
			var req = HttpContext.Current.Request;
			var baseUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

			if (!string.IsNullOrEmpty(req.ApplicationPath))
				baseUrl += req.ApplicationPath;

			if (!baseUrl.EndsWith("/"))
				baseUrl += "/";

			return baseUrl;
		}


        protected void BinaryWrite(BinaryFile file)
        {

        }
	}
}