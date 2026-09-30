using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebControls;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Descrizione di riepilogo per BasePage.
    /// </summary>
    public abstract class BasePage : Ninject.Web.PageBase, IIdComunePage
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(BasePage));

        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }


        public virtual string IdComune { get { return this._aliasSoftwareResolver.AliasComune; } }
        public virtual string Software { get { return this._aliasSoftwareResolver.Software; } }

        public List<string> Errori { get; private set; }
        public List<string> MessaggiInformativi = new List<string>();
        public List<string> MessaggiSuccesso = new List<string>();

        protected string LoadScripts(string[] scripts)
        {
            var s = scripts.Select(x => $"<script type='text/javascript' src='{this.ResolveClientUrl(x)}'></script>");

            return String.Join(Environment.NewLine, s.ToArray());
        }

        protected string LoadScript(string script)
        {
            return $"<script type='text/javascript' src='{this.ResolveClientUrl(script)}'></script>";
        }

        public BasePage()
        {
            this.Errori = new List<string>();

            this.ImpostaFormatiNumerici();

            //#if ! DEBUG
            this.Error += new EventHandler(this.BasePage_Error);
            //#endif
            this.Load += new EventHandler(this.BasePage_Load);
        }

        private void BasePage_Load(object sender, EventArgs e)
        {
            this.ImpostaCacheabilitaPagina();
        }

        private void BasePage_Error(object sender, EventArgs e)
        {
            Exception ex = this.Server.GetLastError();

            try
            {
                if (ex != null)
                {
                    if (ex.GetType() == typeof(ComuneNonAttivoException))
                    {
                        this._log.Error($"Tentativo di accedere ad un comune non attivo: " + ex.ToString());
                        try
                        {
                            HttpContext.Current.Response.Redirect("404.aspx");
                        }
                        catch (ThreadAbortException)
                        {
                            /// ...
                        }
                        return;
                    }

                    var errorId = Guid.NewGuid().ToString();
                    this._log.ErrorFormat("Errore nella pagina {0} (id errore: {2}):\r\n {1}", HttpContext.Current.Request.Path + HttpContext.Current.Request.QueryString, ex.ToString(), errorId);

                    HttpContext.Current.Response.Write("<b>Si è verificato un errore durante l'elaborazione della pagina</b><br /><br /> Dettagli dell'errore: <br><pre>" + ex.Message + "<br /><br />Riferimento errore: " + errorId + "</pre>");

                    this.Server.ClearError();
                }

            }
            catch (Exception)
            {
                this.Server.ClearError();
                throw ex;
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.GeneraTHead(this.Controls);

            base.OnPreRender(e);

            this.MostraErrori();
        }

        private void GeneraTHead(ControlCollection controlCollection)
        {
            foreach (Control ctrl in controlCollection)
            {
                this.GeneraTHead(ctrl.Controls);

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
            if (this.Errori.Count == 0 && this.MessaggiInformativi.Count == 0 && this.MessaggiSuccesso.Count == 0)
                return;

            var mp = this.Master;

            while (mp != null)
            {
                var master = mp as IMostraErroriPage;

                if (master != null)
                {
                    master.MostraMessaggi(this.Errori, this.MessaggiInformativi, this.MessaggiSuccesso);
                }

                mp = mp.Master;
            }
        }

        protected virtual void ImpostaCacheabilitaPagina()
        {
            this.Response.Cache.SetCacheability(HttpCacheability.NoCache);
            this.Response.Cache.SetNoServerCaching();
            this.Response.Cache.SetNoStore();
            this.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
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
            this.Response.ContentType = file.MimeType;
            this.Response.AddHeader("content-disposition", $"attachment; filename=\"{file.FileName}\"");
            this.Response.BinaryWrite(file.FileContent);
        }
    }
}