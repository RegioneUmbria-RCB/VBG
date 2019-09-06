using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;
using Init.Sigepro.FrontEnd.Reserved;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd
{
    public partial class AreaRiservataMaster : BaseAreaRiservataMaster
    {
        [Inject]
        public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }
        [Inject]
        public IConfigurazione<ParametriVbg> _configurazioneAspetto { get; set; }
        [Inject]
        public MenuService _menuService { get; set; }
        [Inject]
        public IsUtenteAnonimoSpecification IsUtenteAnonimo { get; set; }
        [Inject]
        public IConfigurazione<ParametriUrlAreaRiservata> _urlAreaRiservata { get; set; }
        [Inject]
        public IRisorseTestualiService _risorseService { get; set; }
        [Inject]
        public RedirectService _navigationService { get; set; }

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
                return IsUtenteAnonimo.IsSatisfiedBy(UserAuthenticationResult);

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
                return this.UserAuthenticationResult.DatiUtente.UtenteTester;
            }
        }

		protected override void OnInit(EventArgs e)
		{
			base.OutputErrori = rptErrori;

			base.OnInit(e);
		}

		protected void Page_Load(object sender, EventArgs e)
		{

			InizializzaMenu();

			if (!IsPostBack)
			{
				InizializzaDatiUtente();
				InizializzaNomeComune();
			}
		}


		private void InizializzaNomeComune()
		{
			//lblNomeComune2.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(Software).DENOMINAZIONE;
			
		}

		private void InizializzaDatiUtente()
		{
			//lblNomeUtente2.Text = UserAuthenticationResult.DatiUtente.Nominativo + " " + UserAuthenticationResult.DatiUtente.Nome;
		}

		private void InizializzaMenu()
		{
            var menu = this._menuService.LoadMenu();

            //rptMenu.DataSource = menu.Sezioni;
            //rptMenu.DataBind();

            //rptMenuUtente.DataSource = menu.MenuUtente;
            //rptMenuUtente.DataBind();
        }

		protected void rptMenu_ItemCommand(object source, RepeaterCommandEventArgs e)
		{
            var url = UrlBuilder.Url(e.CommandArgument.ToString(), x =>
            {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
            });

            Response.Redirect(url);
		}
		
		protected override void OnPreRender(EventArgs e)
		{
			lblTitoloPagina.Text = this.Page.Title;

			base.OnPreRender(e);
		}

		protected void lnkTornaAllaHome_Click(object sender, EventArgs e)
		{
			_navigationService.RedirectToHomeAreaRiservata();
		}

        public string BuildClientUrl(string url)
        {
            return (this.Page as ReservedBasePage).ResolvePlaceholders(url);
        }

        protected void rptMenu_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if(e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptSubMenu = (Repeater)e.Item.FindControl("rptSubMenu");
                var dataItem = (SezioneMenu)e.Item.DataItem;

                rptSubMenu.DataSource = dataItem.Items;
                rptSubMenu.DataBind();
            }
        }

        protected void bmModificaRisorse_OkClicked(object sender, EventArgs e)
        {
            try
            {
                this._risorseService.AggiornaRisorsa(txtIdRisorsa.Text, txtNuovoTesto.Text);
            }
            catch (Exception ex)
            {
                this.MostraErrori(new[] { ex.Message });
                throw;
            }
        }

        protected void lnkAccedi_Click(object sender, EventArgs e)
        {
            FormsAuthentication.SignOut();
            Response.Redirect(Request.Url.ToString().Replace(UserToken, String.Empty));
        }
    }
}
