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
		public IConfigurazione<ParametriMenu> _configurazioneMenu { get; set; }

		RedirectService _navigationService;

		public bool MostraMenu
		{
			get { return rptMenu.Visible; }
			set { rptMenu.Visible = value; }
		}

		protected string AnalyticsId
		{
			get { return ConfigurationManager.AppSettings["AnalyticsId"]; }
		}

		protected override void OnInit(EventArgs e)
		{
			base.OutputErrori = rptErrori;

			base.OnInit(e);
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			_navigationService = new RedirectService(HttpContext.Current);

			InizializzaMenu();

			if (!IsPostBack)
			{
				InizializzaDatiUtente();
				InizializzaNomeComune();
			}
		}


		private void InizializzaNomeComune()
		{
			lblNomeComune2.Text = lblNomeComune.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(IdComune, Software).DENOMINAZIONE;
			
		}

		private void InizializzaDatiUtente()
		{
			lblNomeUtente2.Text = 
			lblNomeUtente.Text = UserAuthenticationResult.DatiUtente.Nominativo + " " + UserAuthenticationResult.DatiUtente.Nome;
		}

		private void InizializzaMenu()
		{
			var vociMenu = _configurazioneMenu.Parametri.VociMenu.Select(x => new
			{
				Url = _navigationService.GetReservedAddress(ResolveUrl(x.Url), IdComune, Software, UserToken),
				Descrizione = x.Descrizione,
				Target = String.IsNullOrEmpty(x.Target) ? "_self" : x.Target
			});

			rptMenu.DataSource = vociMenu;// new MenuNavigazione(IdComune, Software, tecnico).GetListaVoci();
			rptMenu.DataBind();
		}

		protected void rptMenu_ItemCommand(object source, RepeaterCommandEventArgs e)
		{
			_navigationService.RedirectToReservedAddress(e.CommandArgument.ToString(), IdComune, Software, UserToken);
		}
		
		protected override void OnPreRender(EventArgs e)
		{
			lblTitoloPagina.Text = this.Page.Title;

			base.OnPreRender(e);
		}

		protected void cmdHome_Click(object sender, EventArgs e)
		{
			if (Request.Cookies["ReturnTo"] != null && !String.IsNullOrEmpty(Request.Cookies["ReturnTo"].Value))
			{
				_navigationService.Redirect(Request.Cookies["ReturnTo"].Value);
			}

			if (Request.Cookies["UrlReferrer"] != null && !String.IsNullOrEmpty(Request.Cookies["UrlReferrer"].Value))
			{
				_navigationService.Redirect(Request.Cookies["UrlReferrer"].Value);
			}
		}

		protected void lnkTornaAllaHome_Click(object sender, EventArgs e)
		{
			_navigationService.RedirectToHomeAreaRiservata(IdComune, Software, UserToken);
		}
	}
}
