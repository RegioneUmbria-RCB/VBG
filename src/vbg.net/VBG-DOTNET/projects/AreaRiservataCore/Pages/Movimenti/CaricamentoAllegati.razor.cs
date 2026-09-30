using DocumentFormat.OpenXml.Spreadsheet;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Forms;
using VBG.BlazorComponentsLibrary.DesignComuni.Modals;
using VBG.BlazorComponentsLibrary.EditFormComponents.FileUpload;

namespace AreaRiservataCore.Pages.Movimenti
{
    public partial class CaricamentoAllegati
    {
        [Inject]
        protected CaricamentoAllegatiMovimentoViewModel _viewModel { get; set; } = default!;

        [Inject]
        public IPostedFileSpecificationFactoryAsync _postedFileSpecificationFactoryAsync { get; set; } = default!;

        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazione { get; set; } = default!;

        [Inject]
        protected IVerificaFirmaDigitaleService _verificaFirmaDigitaleService { get; set; } = default!;

        [Inject]
        public ILogger<CaricamentoAllegati> _logger { get; set; } = default!;

        private bool _showConfirmationModal;
        private int _idAllegatoDaEliminare;
        private bool _showUploadModal;

        public class Attachment
        {
            //public string? FileName { get; set; }
            //public IBrowserFile? File { get; set; }
            public int? CodiceOggetto { get; set; }
            public string? Note { get; set; }
        }

        private Attachment AttachmentModel = new Attachment();
        BinaryFile? _binaryFile = null;

        public IValidPostedFileSpecificationAsync validPostedFileSpecificationAsync { get; set; }


        protected bool PermettiNoteAllegato
        {
            get { return !this._parametriIntegrazione.Parametri.MovimentoDaEffettuare.InibisciNoteAllegati; }
        }

        protected MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

        protected bool RichiedeFirmaDigitale
        {
            get
            {
                return this._viewModel.RichiedeFirmaDigitale;
            }
        }

        protected override void OnInitializedMovimenti()
        {
            this.validPostedFileSpecificationAsync = this._postedFileSpecificationFactoryAsync.Get(
               new FileValidationFlags
               {
                   FirmatoDigitalmente = this.RichiedeFirmaDigitale,
                   Obbligatorio = true
               });

            this.DataBind();
        }

        private void DataBind()
        {
            this.MovimentoDaEffettuare = this._viewModel.GetMovimentoDaEffettuare();
        }

        //private void UploadFile_OnChanged(FileValidationSuccesfulEventArgs result)
        //{
        //    this.AttachmentModel.File = result.File;
        //    this.AttachmentModel.FileName = result.File.Name;
        //}

        private void AddAttachment()
        {
            try
            {
                this.MessageContainer.ClearErrors();

                this._showUploadModal = false;

                //var file = await this.AttachmentModel.File.ToBinaryFileAsync(this.validPostedFileSpecificationAsync);

                var descrizione = _binaryFile?.FileName;

                if (this.PermettiNoteAllegato)
                {
                    descrizione = this.AttachmentModel.Note;
                }

                this._viewModel.CaricaAllegato(descrizione, _binaryFile);

                this.AttachmentModel = new Attachment();

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.MessageContainer.AddError("Si è verificato un errore durante il caricamento dell'allegato");
                this._logger.LogError($"Il metodo {nameof(AddAttachment)} ha generato la seguente eccezione: {ex.Message}");
                this.AttachmentModel = new Attachment();
            }
        }

        private void RemoveAttachment(int idAllegato)
        {
            //if (!await this.confirm("Eliminare l'allegato selezionato?"))
            //    return;

            try
            {
                this.MessageContainer.ClearErrors();

                this._viewModel.EliminaAllegato(idAllegato);

                this.DataBind();

                _showConfirmationModal = false;
            }
            catch (Exception ex)
            {
                this.MessageContainer.AddError("Si è verificato un errore durante la cancellazione dell'allegato: " + ex.Message);
            }
        }


        private void ShowConfirmationModal(int idAllegato)
        {
            _idAllegatoDaEliminare = idAllegato;
            _showConfirmationModal = true;
        }

        private async Task OnBtnBackAsync()
        {
            await this.GoToPreviousStepAsync();
        }

        private async Task OnBtnNextAsync()
        {
            if (this._viewModel.CanExitStep())
            {
                await this.GoToNextStepAsync();
                return;
            }

            this.MessageContainer.AddError(this._viewModel.GetErroriFilesNonFirmati());
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        private async Task OnValidateAsync(FileUploadChangedEventArgs e)
        {
            await _spinnerService.ShowSpinnerAsync(async () =>
            {
                if (this.RichiedeFirmaDigitale)
                {
                    var firmaSpecs = new FirmatoDigitalmentePostedFileSpecification(this._verificaFirmaDigitaleService);

                    if (!await firmaSpecs.IsSatisfiedByAsync(e.File))
                    {
                        this.MessageContainer.AddError("Il documento deve essere firmato digitalmente");
                    }
                }
            });
        }

        private void EliminaFileCaricato()
        {
            //AttachmentModel.FileName = string.Empty;
            //AttachmentModel.File = null;
            AttachmentModel.CodiceOggetto = null;
        }

        private void CloseUpdateModal() 
        {
            //this.AttachmentModel = new Attachment();
            this.AttachmentModel.Note = string.Empty;
            this.AttachmentModel.CodiceOggetto = null;
            this._showUploadModal = false;
        }
    }
}
