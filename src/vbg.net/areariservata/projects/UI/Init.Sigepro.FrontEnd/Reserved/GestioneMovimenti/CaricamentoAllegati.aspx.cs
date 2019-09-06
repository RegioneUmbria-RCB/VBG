using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class CaricamentoAllegati : MovimentiBasePage
    {
        [Inject]
        protected CaricamentoAllegatiMovimentoViewModel _viewModel { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazione { get; set; }

        protected bool PermettiNoteAllegato
        {
            get { return !this._parametriIntegrazione.Parametri.MovimentoDaEffettuare.InibisciNoteAllegati; }
        }


        protected bool MostraBottoniAllegato = false;

        protected MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

        protected bool RichiedeFirmaDigitale
        {
            get
            {
                return this._viewModel.RichiedeFirmaDigitale;
            }
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
            this.MovimentoDaEffettuare = this._viewModel.GetMovimentoDaEffettuare();

            base.DataBind();
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



        protected void cmdCaricaAllegato_Click(object sender, EventArgs e)
        {
            try
            {
                var file = new BinaryFile(fuAllegato.Inner, this._validPostedFileSpecification);
                var descrizione = file.FileName;
				
				if (this.PermettiNoteAllegato) {
					descrizione = txtDescrizioneAllegato.Text;
				}

                this._viewModel.CaricaAllegato(descrizione, file);

                txtDescrizioneAllegato.Text = String.Empty;

                DataBind();
            }
            catch (Exception ex)
            {
                MostraBottoniAllegato = true;
                Errori.Add("Si è verificato un errore durante il caricamento dell'allegato: " + ex.Message);
            }
        }

        protected void EliminaAllegato(object sender, EventArgs e)
        {
            try
            {
                var lb = (LinkButton)sender;

                var idAllegato = Convert.ToInt32(lb.CommandArgument);

                this._viewModel.EliminaAllegato(idAllegato);

                DataBind();
            }
            catch (Exception ex)
            {
                Errori.Add("Si è verificato un errore durante la cancellazione dell'allegato: " + ex.Message);
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

        protected void cmdProcedi_Click(object sender, EventArgs e)
        {
            if (this._viewModel.CanExitStep())
            {
                GoToNextStep();
                return;
            }

            this.Errori.AddRange(this._viewModel.GetErroriFilesNonFirmati());
        }
    }
}