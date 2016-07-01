using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public partial class CompilaSchedeDinamiche : MovimentiBasePage
	{
		private static class Constants
		{
			public const int IdPannelloLista = 0;
			public const int IdPannelloDettaglio = 1;
		}

		[Inject]
		protected CompilazioneSchedeDinamicheViewModel _viewModel { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Page.Title = this._viewModel.GetTitoloMovimentoDaEffettuare();

			if (!IsPostBack)
			{
				DataBind();
			}
		}

		public override void DataBind()
		{
			rptSchedeDaCompilare.DataSource = this._viewModel.GetListaSchedeDinamiche();
			rptSchedeDaCompilare.DataBind();
		}

		protected void lnkSchedaDinamica_Click(object sender, EventArgs e)
		{
			var lnkSchedaDinamica = (LinkButton)sender;

			var idScheda = Convert.ToInt32(lnkSchedaDinamica.CommandArgument);

			multiView.ActiveViewIndex = Constants.IdPannelloDettaglio;

			renderer.RicaricaModelloDinamico += (s,ea) => this._viewModel.RicaricaModelloDinamico(s,ea);
			renderer.DataSource = this._viewModel.CaricaSchedadinamica(idScheda);
			renderer.DataBind();

		}

		protected void cmdProcedi_Click(object sender, EventArgs e)
		{
			if (!this._viewModel.CanExitStep())
			{
				Errori.Add("Per poter proseguire è necessario compilare tutte le schede");
				return;
			}

			Redirect("~/Reserved/GestioneMovimenti/CaricamentoRiepiloghiSchede.aspx", qs => qs.Add("IdMovimento" , IdMovimento));
		}

		protected void cmdTornaIndietro_Click(object sender, EventArgs e)
		{
			Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", IdMovimento));
		}

		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			try
			{
				this._viewModel.SalvaSchedaDinamica( renderer.DataSource );

				cmdChiudi_Click(this, EventArgs.Empty);
			}
			catch (Exception )
			{
				MostraErroreSalvataggio();
			}
		}

		private void MostraErroreSalvataggio()
		{
			Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
			//DataBind();
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = Constants.IdPannelloLista;

			DataBind();
		}
		
	}
}