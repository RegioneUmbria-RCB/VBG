using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;


namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public partial class RiepilogoEInvio : MovimentiBasePage
	{
		[Inject]
		protected RiepilogoMovimentoDaEffettuareViewModel _viewModel { get;set; }
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }


		protected bool MostraBottoniAllegato = false;

		protected DatiMovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			this.MovimentoDaEffettuare = this._viewModel.GetMovimentoDaEffettuare();
			this.Title = this.MovimentoDaEffettuare.NomeAttivita;

			rptSchedeCompilate.DataSource = this._viewModel.GetListaSchedeCompilate();
			rptSchedeCompilate.DataBind();

			base.DataBind();
		}

		protected void EliminaAllegato(object sender, EventArgs e)
		{
			try
			{
				var lb = (LinkButton)sender;

				var idAllegato = Convert.ToInt32(lb.CommandArgument);

				this._viewModel.EliminaAllegato( idAllegato);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante la cancellazione dell'allegato: " + ex.Message);
			}
		}

		protected void cmdConferma_Click(object sender, EventArgs e)
		{
			try
			{
				var erroriValidazione = this._viewModel.ValidaPerInvio();

				if (erroriValidazione.Count() > 0)
				{
					Errori.AddRange(erroriValidazione);
					return;
				}

				this._viewModel.Invia();

				Redirect("~/Reserved/GestioneMovimenti/DatiInviatiConSuccesso.aspx", qs => qs.Add("IdMovimento", IdMovimento));
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante la trasmissione dei dati al comune: " + ex.Message);
			}
		}

		protected void cmdCaricaAllegato_Click(object sender, EventArgs e)
		{
			try
			{
				var file = new BinaryFile(fuAllegato, this._validPostedFileSpecification);
				var descrizione = txtDescrizioneAllegato.Text;

				this._viewModel.CaricaAllegato( descrizione, file );

				txtDescrizioneAllegato.Text = String.Empty;

				DataBind();
			}
			catch (Exception ex)
			{
				MostraBottoniAllegato = true;
				Errori.Add("Si è verificato un errore durante il caricamento dell'allegato: " + ex.Message);
			}
		}

		protected void cmdSalvaNote_Clinck(object sender, EventArgs e)
		{
			try
			{
				var note = txtNote.Text;

				this._viewModel.AggiornaNoteMovimento( note);
			}
			catch (Exception ex)
			{
				this.Errori.Add("Si è verificato un errore durante l'aggiornamento delle note: " + ex.Message);
			}
		}

		protected void cmdTornaIndietro_Click(object sender, EventArgs e)
		{
            if( this._viewModel.GetListaSchedeCompilate().Count() > 0 )
				Redirect("~/Reserved/GestioneMovimenti/CaricamentoRiepiloghiSchede.aspx", qs => qs.Add("IdMovimento", IdMovimento));
            else
				Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", IdMovimento));
		}

		protected void OnRowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName == "Firma")
			{
				var codiceOggetto = e.CommandArgument;

				Redirect("~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaAllegatoMovimento.aspx", qs =>
				{
					qs.Add("IdMovimento", IdMovimento);
					qs.Add("CodiceOggetto", codiceOggetto);
					qs.Add("Tipo", "Allegato");
					qs.Add("ReturnTo", Request.Url.ToString());
				});
			}
		}
	}
}