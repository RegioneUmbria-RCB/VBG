using Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Linq;
using System.Web;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class vbg_istanze_in_sospeso : ReservedBasePage
    {
        [Inject]
        protected IVbgConsoleService _vbgConsoleService { get; set; }

        public class RepeaterBindingItem
        {
            public string Comune { get; set; }
            public string CodiceComune { get; set; }
            public bool ServizioAttivo { get; set; } = false;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.NascondiTitoloPagina = true;
            this.Page.Title = this.titoloPagina.Text;

            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            multiView.ActiveViewIndex = 0;

            var consoleUrl = _vbgConsoleService.GetUrlIstanzeInSospeso();

            if (consoleUrl.Count() == 1)
            {
                RedirToConsole(consoleUrl.First());
                return;
            }

            rptListaUrl.DataSource = consoleUrl.Select(x => new RepeaterBindingItem
            {
                CodiceComune = x.CodiceComune,
                Comune = x.Comune,
                ServizioAttivo = !String.IsNullOrEmpty(x.Url)
            });
            rptListaUrl.DataBind();
        }

        private void RedirToConsole(UrlAccessoConsole urlAccessoConsole)
        {
            var returnToUrl = "//" + HttpContext.Current.Request.Url.Authority + HttpContext.Current.Request.ApplicationPath + UrlBuilder.Url("/reserved/benvenuto.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
            });

            var qsParameters = new IQuerystringParameter[] {
                new QsUrlScrivaniaVirtuale(returnToUrl),
                new QsSelezionaIntervento(Request.QueryString) 
                // Aggiungere qui eventuali parametri che vanno passati in querystring...
            };

            var url = this._vbgConsoleService.GenerateConsoleUrl(urlAccessoConsole, this.UserAuthenticationResult.DatiUtente, qsParameters);

            hlRedirUrl.NavigateUrl = url.ToString();
            multiView.ActiveViewIndex = 1;
        }

        protected void onComuneSelezionato(object sender, EventArgs e)
        {
            var lb = (LinkButton)sender;
            var arg = lb.CommandArgument;
            var url = this._vbgConsoleService.GetUrlIstanzeInSospeso(arg);

            RedirToConsole(url);

        }
    }
}