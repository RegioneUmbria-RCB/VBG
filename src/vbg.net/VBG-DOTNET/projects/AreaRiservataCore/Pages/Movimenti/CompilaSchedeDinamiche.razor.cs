using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.FileUpload;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Microsoft.AspNetCore.Components;
using System.Diagnostics;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;

namespace AreaRiservataCore.Pages.Movimenti
{
    public partial class CompilaSchedeDinamiche
    {
        [Inject]
        protected CompilazioneSchedeDinamicheViewModel _viewModel { get; set; } = default!;
        [Inject]
        protected IOggettiService _oggettiService { get; set; } = default!;

        private IDatiDinamiciFileUploadService? _datiDinamiciDomandaFileUpload = null;
        private IEnumerable<CompilazioneSchedeDinamicheViewModel.SchedaDinamica>? _dataSource;
        private IMascheraSolaLettura? _mascheraSolaLettura;
        private ModelloDinamicoIstanza? _schedaDaCompilare;

        protected override Task OnInitializedMovimentiAsync()
        {
            this._datiDinamiciDomandaFileUpload = new MovimentiFileUploadService(this._oggettiService);
            this._mascheraSolaLettura = new MascheraSolaLetturaVuota();
            this._dataSource = this._viewModel.GetListaSchedeDinamiche();

            return Task.CompletedTask;
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        private async Task ApriSchedaDinamicaAsync(int idScheda)
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                var scheda = this._viewModel.CaricaSchedadinamica(idScheda);

                // this.titoloModello = scheda.NomeModello;
                scheda.EseguiScriptCaricamento();

                this._schedaDaCompilare = scheda;
            });
        }

        private void OnBtnSave()
        {
            try
            {
                this._viewModel.SalvaSchedaDinamica(this.IdMovimento, this._schedaDaCompilare);

                this.GotoList();
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante il salvataggio: {ex.Message}");
                this.MessageContainer.ClearErrors();
                this.MessageContainer.AddError("Si sono verificati errori durante il salvataggio");
            }
        }

        private void OnBtnClose()
        {
            this.GotoList();
        }

        private void GotoList()
        {
            this._schedaDaCompilare = null;

            this.MessageContainer.ClearErrors();
        }

        private async Task OnBtnBackAsync()
        {
            this.MessageContainer.ClearErrors();
            await this.GoToPreviousStepAsync();
        }

        private async Task OnBtnNextAsync()
        {
            if (!this._viewModel.CanExitStep())
            {
                this.MessageContainer.AddError("Per poter proseguire è necessario compilare tutte le schede");
                return;
            }
            else
            {
                this.MessageContainer.ClearErrors();
            }

            await this.GoToNextStepAsync();
        }
    }
}
