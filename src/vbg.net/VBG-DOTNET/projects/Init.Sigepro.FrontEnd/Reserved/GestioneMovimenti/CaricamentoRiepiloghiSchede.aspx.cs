using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Ninject;
using System;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class CaricamentoRiepiloghiSchede : MovimentiBasePage
    {
        [Inject]
        protected CaricamentoRiepiloghiSchedeViewModel _viewModel { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        public bool RichiedeFirmaDigitale
        {
            get { return this._viewModel.RichiedeFirmaDigitale; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this._viewModel.RigeneraRiepiloghiSenzaOggetto();

                this.DataBind();
            }
        }

        public override void DataBind()
        {
            this.Title = this._viewModel.GetNomeAttivitaDaEffettuare();

            this.gvRiepiloghi.DataSource = this._viewModel.GetListaRiepiloghi();
            this.gvRiepiloghi.DataBind();
        }

        protected void gvRiepiloghi_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "DownloadModello")
            {
                var idScheda = Convert.ToInt32(e.CommandArgument);
                var fileName = $"RiepilogoScheda{idScheda}.pdf";
                var file = this._viewModel.GeneraHtmlScheda(idScheda, fileName);

                this.Response.Clear();
                this.Response.ContentType = file.MimeType;
                this.Response.AddHeader("content-disposition", $"attachment;filename=\"{fileName}\"");
                this.Response.BinaryWrite(file.FileContent);
                this.Response.End();
            }

            if (e.CommandName == "Firma")
            {
                var codiceOggetto = e.CommandArgument;

                this.Redirect("~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaAllegatoMovimento.aspx", qs =>
                {
                    qs.Add("IdMovimento", this.IdMovimento);
                    qs.Add("CodiceOggetto", codiceOggetto);
                    qs.Add("ReturnTo", this.Request.Url.ToString());
                });
            }
        }

        protected void gvRiepiloghi_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            var gridRow = this.gvRiepiloghi.Rows[e.RowIndex];
            var idScheda = Convert.ToInt32(this.gvRiepiloghi.DataKeys[e.RowIndex][0]);

            try
            {
                this._viewModel.EliminaRiepilogoScheda(idScheda);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante l'eliminazione del file: " + ex.Message);
            }
        }

        protected void gvRiepiloghi_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            var gridRow = this.gvRiepiloghi.Rows[e.RowIndex];
            var fileUpload = (FileUpload)gridRow.FindControl("fuAllegato");
            var idScheda = Convert.ToInt32(this.gvRiepiloghi.DataKeys[e.RowIndex][0]);

            try
            {
                this._viewModel.CaricaRiepilogoScheda(idScheda, new WebFormsBinaryFile(fileUpload, this._validPostedFileSpecification));

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante il caricamento del file: " + ex.Message);
            }
        }

        protected void cmdProcedi_Click(object sender, EventArgs e)
        {
            if (!this._viewModel.CanExitStep())
            {
                // Errori.Add("Per poter proseguire è necessario caricare il riepilogo di tutte le schede che sono state compilate");

                foreach (var nomeScheda in this._viewModel.GetNomiSchedeNonCompilate())
                    this.Errori.Add($"Scaricare {(this.RichiedeFirmaDigitale ? ", firmare" : "")} e ricaricare il riepilogo della scheda \"{nomeScheda}\"");

                return;
            }

            this.GoToNextStep();
        }

        protected void cmdTornaIndietro_Click(object sender, EventArgs e)
        {
            this.GoToPreviousStep();
        }

        protected override FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento.IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }
    }
}