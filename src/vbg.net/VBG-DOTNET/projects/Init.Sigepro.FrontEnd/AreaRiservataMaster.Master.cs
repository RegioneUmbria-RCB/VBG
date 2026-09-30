using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.Reserved;
using Ninject;
using System;
using System.Configuration;
using System.Linq;
using System.Web.UI.WebControls;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd
{
    public partial class AreaRiservataMaster : BaseAreaRiservataMaster
    {
        [Inject]
        public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }
        [Inject]
        public IConfigurazione<ParametriAspetto> _configurazioneAspetto { get; set; }
        [Inject]
        public IMenuService _menuService { get; set; }
        [Inject]
        public IsUtenteAnonimoSpecification IsUtenteAnonimo { get; set; }
        [Inject]
        public IConfigurazione<ParametriUrlAreaRiservata> _urlAreaRiservata { get; set; }
        [Inject]
        public IRisorseTestualiService _risorseService { get; set; }
        [Inject]
        public IRedirectService _navigationService { get; set; }
        [Inject]
        public ICheckBrowserService _checkBrowserService { get; set; }

        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        public bool MostraIntestazione
        {
            get { object o = this.ViewState["MostraIntestazione"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraIntestazione"] = value; }
        }

        public bool MostraFooter
        {
            get { object o = this.ViewState["MostraFooter"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraFooter"] = value; }
        }

        public bool NascondiTitoloPagina
        {
            get { object o = this.ViewState["NascondiTitoloPagina"]; return o == null ? false : (bool)o; }
            set { this.ViewState["NascondiTitoloPagina"] = value; }
        }

        public bool ResetValidatorsOnLoad
        {
            get { object o = this.ViewState["ResetValidatorsOnLoad"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ResetValidatorsOnLoad"] = value; }
        }

        public bool UtenteAnonimo
        {
            get
            {
                if (this._authenticationDataResolver.IsAuthenticated)
                {
                    return this.IsUtenteAnonimo.IsSatisfiedBy(this.UserAuthenticationResult);
                }

                return false;
            }
        }

        protected string AnalyticsId
        {
            get { return ConfigurationManager.AppSettings["AnalyticsId"]; }
        }

        protected bool UtenteTester
        {
            get
            {
                if (this._authenticationDataResolver.IsAuthenticated)
                {
                    return this.UserAuthenticationResult.DatiUtente.UtenteTester;
                }

                return false;
            }
        }

        public string SottotitoloPagina
        {
            get { var obj = this.ViewState["SottotitoloPagina"]; return obj == null ? "" : obj.ToString(); }
            set { this.ViewState["SottotitoloPagina"] = value; }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OutputErrori = this.rptErrori;
            base.OutputMessaggiInformativi = this.rptMessaggi;
            base.OutputMessaggiSuccesso = this.rptMessaggiSuccesso;

            base.OnInit(e);
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.CheckBrowserVersion();

            this.InizializzaMenu();

            if (!this.IsPostBack)
            {
                this.InizializzaDatiUtente();

                this.SottotitoloPagina = this._configurazioneVbgRepository.LeggiConfigurazioneComune(this.Software).DENOMINAZIONE;

            }
        }

        private void CheckBrowserVersion()
        {
            var userAgent = this.Request.Headers["User-Agent"];
            if (this._checkBrowserService.IsInternetExplorer(userAgent))
            {
                this._navigationService.RedirectToAvvisoInternetExplorer();
            }
        }

        private void InizializzaDatiUtente()
        {
            if (this._authenticationDataResolver.IsAuthenticated)
            {
                this.lblNomeUtente2.Text = this.UserAuthenticationResult.DatiUtente.Nominativo + " " + this.UserAuthenticationResult.DatiUtente.Nome;
            }
        }

        private void InizializzaMenu()
        {
            if (!(this.Page is ReservedBasePage))
            {
                this.rptMenu.Visible = false;
                return;
            }
            var menu = this._menuService.LoadMenu();

            this.rptMenu.DataSource = menu.Sezioni;
            this.rptMenu.DataBind();

            this.rptMenuUtente.DataSource = menu.MenuUtente;
            this.rptMenuUtente.DataBind();

            this.rptMenuDestra.DataSource = menu.MenuDestra;
            this.rptMenuDestra.DataBind();

            this.rptMenuDestra.Visible = menu.MenuDestra.Any();
        }

        protected void rptMenu_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            var url = UrlBuilder.Url(e.CommandArgument.ToString(), x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
            });

            this.Response.Redirect(url);
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.lblTitoloPagina.Text = this.Page.Title;

            base.OnPreRender(e);
        }

        protected void lnkTornaAllaHome_Click(object sender, EventArgs e)
        {
            this._navigationService.RedirectToHomeAreaRiservata();
        }

        protected void rptMenu_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptSubMenu = (Repeater)e.Item.FindControl("rptSubMenu");
                var dataItem = (SezioneMenuModel)e.Item.DataItem;

                rptSubMenu.DataSource = dataItem.SubMenu;
                rptSubMenu.DataBind();
            }
        }

        protected void bmModificaRisorse_OkClicked(object sender, EventArgs e)
        {
            try
            {
                var testoRisorsa = this.txtNuovoTesto.Text.Replace("&lt;", "<").Replace("&gt;", ">");
                this._risorseService.AggiornaRisorsa(this.txtIdRisorsa.Text, testoRisorsa);
            }
            catch (Exception ex)
            {
                this.MostraMessaggi(this.rptErrori, new[] { ex.Message });
                throw;
            }
        }

        protected void lnkAccedi_Click(object sender, EventArgs e)
        {
            this._areaRiservataAuthenticationService.SignOut();
            this.Response.Redirect(this.Request.Url.ToString().Replace(this.UserToken, String.Empty));
        }


    }
}
