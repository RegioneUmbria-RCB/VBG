using System;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Ninject;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneUtenzeTares : IstanzeStepPage
	{
		[Inject]
		protected TaresBariService _taresBariService { get; set; }
		[Inject]
		protected IComuniService _comuniService{ get; set; }

		public int CodiceTiposoggettoRichiedente
		{
			get { object o = this.ViewState["CodiceTiposoggettoRichiedente"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["CodiceTiposoggettoRichiedente"] = value; }
		}

		public string TestoRicerca
		{
			get { return ltrTestoRicerca.Text; }
			set { ltrTestoRicerca.Text = value; }
		}

		public string TestoDettaglio
		{
			get { return ltrTestoDettaglio.Text; }
			set { ltrTestoDettaglio.Text = value; }
		}

		public int NumeroMassimoUtenzeGestibili
		{
			get { object o = this.ViewState["NumeroMassimoUtenzeGestibili"]; return o == null ? 99 : (int)o; }
			set { this.ViewState["NumeroMassimoUtenzeGestibili"] = value; }
		}

		public string MessaggioErroreLimiteUtenzeSuperato
		{
			get { object o = this.ViewState["MessaggioErroreLimiteUtenzeSuperato"]; return o == null ? "Numero massimo utenze superato" : (string)o; }
			set { this.ViewState["MessaggioErroreLimiteUtenzeSuperato"] = value; }
		}
		

		private static class Constants
		{
			public const int IdViewRicerca = 0;
			public const int IdViewDettaglio = 1;
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				DataBind();
			}
		}

		public override void DataBind()
		{
			if (ReadFacade.Domanda.TaresBari.DatiContribuente != null)
				MostraVistaDettaglio();
			else
				MostraVistaRicerca();
		}

		private void MostraVistaRicerca()
		{
			multiView.ActiveViewIndex = Constants.IdViewRicerca;
			this.Master.MostraBottoneAvanti = false;

			cmdAnnullaRicerca.Visible = false;

			if (ReadFacade.Domanda.TaresBari.DatiContribuente != null)
			{
				cmdAnnullaRicerca.Visible = true;
				txtCfUtenza.Text = ReadFacade.Domanda.TaresBari.DatiContribuente.IdentificativoContribuente.ToString();
				cmdCerca_Click(this, EventArgs.Empty);
			}
		}

		private void MostraVistaDettaglio()
		{
			multiView.ActiveViewIndex = Constants.IdViewDettaglio;

			dettagliUtenza.DataSource = ReadFacade.Domanda.TaresBari.DatiContribuente;
			dettagliUtenza.DataBind();

			this.Master.MostraBottoneAvanti = true;
		}

		protected void rptUtenze_ItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType != ListItemType.Item && e.Item.ItemType != ListItemType.AlternatingItem)
				return;

			var control = (GestioneUtenzeTares_DettagliUtenza)e.Item.FindControl("dettagliUtenzeCtrl");

			control.NumeroMassimoUtenzeGestibili = this.NumeroMassimoUtenzeGestibili;
			control.MessaggioErroreLimiteUtenzeSuperato = this.MessaggioErroreLimiteUtenzeSuperato;

			control.DataSource = (DatiContribuenteDto)e.Item.DataItem;
			control.DataBind();

		}

		protected void cmdCerca_Click(object sender, EventArgs e)
		{
			try
			{
				this.rptUtenze.DataSource = this._taresBariService.TrovaUtenze(UserAuthenticationResult.DatiUtente.Codicefiscale, txtCfUtenza.Text);
				this.rptUtenze.DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}

		}

		protected void OnUtenzaSelezionata(object sender, GestioneUtenzeTares_DettagliUtenza.UtenzaSelezionataeventArgs e)
		{
			var utenza = this._taresBariService.GetDettagliUtenza(UserAuthenticationResult.DatiUtente.Codicefiscale, e.IdentificativoContribuente);

			foreach (var map in e.TipiUtenze)
			{
				utenza
					.ElencoUtenzeAttive
					.Where(x => x.IdentificativoUtenza == map.IdentificativoUtenza)
					.First()
					.TipoUtenza = map.TipoUtenza;
			}

			var cmd = new TaresBariService.ImpostaUtenzaCommand(IdDomanda, utenza, CodiceTiposoggettoRichiedente, _comuniService);

			this._taresBariService.ImpostaUtenza(cmd);

			this.Master.cmdNextStep_Click(this, EventArgs.Empty);
		}

		protected void cmdSelzionaAltraUtenza_Click(object sender, EventArgs e)
		{
			MostraVistaRicerca();
		}


		protected void cmdAnnullaRicerca_Click(object sender, EventArgs e)
		{
			MostraVistaDettaglio();
		}

		protected void OnErroreSelezione(object seder, string errore)
		{
			this.Errori.Add(errore);
		}
		
	}
}