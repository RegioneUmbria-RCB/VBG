using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;


namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public partial class RiepilogoEInvio : MovimentiBasePage
	{
		[Inject]
		protected RiepilogoMovimentoDaEffettuareViewModel _viewModel { get;set; }
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazione { get; set; }


		protected bool MostraBottoniAllegato = false;
        protected bool SostituzioniDocumentaliPresenti = false;

		protected MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

        protected bool PermettiModificaNote
        {
            get { return !this._parametriIntegrazione.Parametri.MovimentoDaEffettuare.InibisciNoteMovimento; }
        }



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

            var sostituzioniDocumentali = this._viewModel.GetListaSostituzioni();

            rptSostituzioniDocumentali.DataSource = sostituzioniDocumentali;
            rptSostituzioniDocumentali.DataBind();

            this.SostituzioniDocumentaliPresenti = sostituzioniDocumentali.Count() > 0;

			base.DataBind();
		}

		protected void cmdConferma_Click(object sender, EventArgs e)
		{
			try
			{
				var erroriValidazione = this._viewModel.ValidaPerInvio();

				if (erroriValidazione.Count() > 0)
				{
					Errori.AddRange(erroriValidazione);

                    DataBind();

					return;
				}

				this._viewModel.Invia();

                GoToNextStep();
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante la trasmissione dei dati al comune: " + ex.Message);
			}
		}


		protected void cmdSalvaNote_Clinck(object sender, EventArgs e)
		{
			try
			{
				var note = txtNote.Text;

				this._viewModel.AggiornaNoteMovimento( note);

                this.DataBind();
			}
			catch (Exception ex)
			{
				this.Errori.Add("Si è verificato un errore durante l'aggiornamento delle note: " + ex.Message);
			}
		}

		protected void cmdTornaIndietro_Click(object sender, EventArgs e)
		{
            GoToPreviousStep();
		}

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }
    }
}