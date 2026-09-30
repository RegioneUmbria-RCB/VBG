using AreaRiservataCore.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Forms;

namespace AreaRiservataCore.Pages.Movimenti
{
    public partial class CaricamentoRiepiloghiSchede
    {
        [Inject]
        protected CaricamentoRiepiloghiSchedeViewModel _viewModel { get; set; } = default!;
        [Inject]
        protected FileDownloadHelper _fileDownloadHelper { get; set; } = default!;

        public bool RichiedeFirmaDigitale
        {
            get { return this._viewModel.RichiedeFirmaDigitale; }
        }

        private IEnumerable<RiepilogoSchedaDinamica> _dataSource { get; set; } = Enumerable.Empty<RiepilogoSchedaDinamica>();

        private int? _idSchedaDaEliminare;

        protected override async Task OnInitializedMovimentiAsync()
        {
            await this._viewModel.RigeneraRiepiloghiSenzaOggettoAsync();

            this._dataSource = this._viewModel.GetListaRiepiloghi();
        }

        private async Task DownloadDocumentAsync(int documentId)
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                var fileName = $"RiepilogoScheda{documentId}.pdf";
                var f = await this._viewModel.GeneraHtmlSchedaAsync(documentId, fileName);

                await this._fileDownloadHelper.DownloadFileAsync(f);
            });
        }

        // questo metodo non viene migrato perchè il link che lo dovrebbe invocare è commentato nel codice .NET Framework
        private async Task SignDocumentAsync(int documentId)
        {
            //if (e.CommandName == "Firma")
            //{
            //    var codiceOggetto = e.CommandArgument;

            //    this.Redirect("~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaAllegatoMovimento.aspx", qs =>
            //    {
            //        qs.Add("IdMovimento", this.IdMovimento);
            //        qs.Add("CodiceOggetto", codiceOggetto);
            //        qs.Add("ReturnTo", this.Request.Url.ToString());
            //    });
            //}

            await this.GotoAsync("FirmaAllegatoMovimento");
        }

        private void UploadFile_OnChanged(int idScheda, FormFileChangedEventArgs e)
        {
            switch (e.Event)
            {
                case FromEvent.OnChange:
                    try
                    {
                        if (e.FileRef is null)
                        {
                            return;
                        }

                        var file = BinaryFile.FromFileData(e.FileRef.FileName, e.FileRef.ContentType, e.FileRef.FileContent);

                        this._viewModel.CaricaRiepilogoScheda(idScheda, file);
                    }
                    catch (Exception ex)
                    {
                        this.MessageContainer.ClearErrors();
                        this.MessageContainer.AddError($"Si è verificato un errore durante il caricamento del file {e.FileRef?.FileName}: {ex.Message}");
                    }

                    this._dataSource = this._viewModel.GetListaRiepiloghi();

                    break;
            }


        }

        private async Task OnBtnBackAsync()
        {
            await this.GoToPreviousStepAsync();
        }

        private async Task OnBtnNextAsync()
        {
            if (!this._viewModel.CanExitStep())
            {
                foreach (var nomeScheda in this._viewModel.GetNomiSchedeNonCompilate())
                {
                    this.MessageContainer.AddError($"Scaricare {(this.RichiedeFirmaDigitale ? ", firmare" : "")} e ricaricare il riepilogo della scheda \"{nomeScheda}\"", false);
                }

                this.MessageContainer.Refresh();

                return;
            }

            this.MessageContainer.ClearErrors();

            await this.GoToNextStepAsync();
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        private void EliminaRiepilogo()
        {
            if (this._idSchedaDaEliminare is null)
            {
                return;
            }

            try
            {
                this._viewModel.EliminaRiepilogoScheda(this._idSchedaDaEliminare.Value);

                this._idSchedaDaEliminare = null;
            }
            catch (Exception ex)
            {
                this.MessageContainer.ClearErrors();
                this.MessageContainer.AddError($"Si è verificato un errore durante l'eliminazione del file della scheda {this._idSchedaDaEliminare!.Value}: {ex.Message}");
            }
        }

        private void ShowConfirmationModal(int idScheda)
        {
            this._idSchedaDaEliminare = idScheda;
        }
    }
}
