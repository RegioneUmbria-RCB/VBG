using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;

namespace AreaRiservataCore.Pages.Movimenti
{
    public partial class RiepilogoEInvio
    {
        [Inject]
        protected RiepilogoMovimentoDaEffettuareViewModel _viewModel { get; set; } = default!;
        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazione { get; set; } = default!;

        //private bool _noteModificate;
        private string? _editingNote;
        private bool _showModalModifiche;
        private bool _showConfirmationModal;


        private class RiepilogoModel
        {
            private bool _hasNoteChanged;

            private string _noteText;
            public string NoteText
            {
                get => this._noteText;
                set
                {
                    this._noteText = value;

                    this._hasNoteChanged = true;
                }
            }

            public bool HasNoteChanged { get; }

            public void Clear()
            {
                this._hasNoteChanged = false;
                this._noteText = String.Empty;
            }
        }

        private readonly RiepilogoModel model = new();
        private readonly ValueTask<IJSObjectReference> objectReference;

        protected bool MostraBottoniAllegato = false;

        protected MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

        protected bool PermettiModificaNote
        {
            get { return !this._parametriIntegrazione.Parametri.MovimentoDaEffettuare.InibisciNoteMovimento; }
        }

        private string getTitle()
        {
            return this._viewModel.GetMovimentoDaEffettuare().NomeAttivita;
        }

        //private string getFadingClass(bool show)
        //{
        //    return show ? "fade-in" : "hidden";
        //}

        //protected override async Task OnAfterRenderAsync(bool firstRender)
        //{
        //    if (firstRender)
        //    {
        //        pageJsModule = await _jsRuntime.InvokeAsync<IJSObjectReference>("import", "./Pages/Movimenti/RiepilogoEInvio.razor.js");
        //        await pageJsModule.InvokeVoidAsync("pageJs.init");
        //    }
        //}

        protected override void OnInitializedMovimenti()
        {
            this.DataBind();
        }

        private void DataBind()
        {
            this.MovimentoDaEffettuare = this._viewModel.GetMovimentoDaEffettuare();

            this.model.Clear();
            this.model.NoteText = this.MovimentoDaEffettuare.Note;
        }

        private void OnSaveNote()
        {
            try
            {
                this.model.NoteText = this._editingNote ?? "";

                this._viewModel.AggiornaNoteMovimento(this.model.NoteText);
                this.DataBind();
                this.CloseModalModifiche();
                //_noteModificate = false;
            }
            catch (Exception ex)
            {
                this.MessageContainer.ClearErrors();
                this.MessageContainer.AddError("Si è verificato un errore durante l'aggiornamento delle note: " + ex.Message);
            }
        }

        private async Task OnBtnSendAsync()
        {
            //if (!await ConfirmAsync("Proseguendo con l'invio non sarà più possibile apportare modifiche ai dati immessi.\r\nContinuare?"))
            //    return;

            try
            {
                await this._spinnerService.ShowSpinnerAsync(async () =>
                {
                    this._showConfirmationModal = false;
                    this.MessageContainer.ClearErrors();

                    var erroriValidazione = this._viewModel.ValidaPerInvio();

                    if (erroriValidazione.Count() > 0)
                    {
                        this.MessageContainer.AddError(erroriValidazione);

                        this.DataBind();

                        return;
                    }

                    this._viewModel.Invia();

                    await this.GoToNextStepAsync();
                });
            }
            catch (Exception ex)
            {
                this.MessageContainer.AddError("Si è verificato un errore durante la trasmissione dei dati al comune");
                this._log.Error($"il metodo {nameof(OnBtnSendAsync)} ha generato il seguente errore: {ex.Message}");
            }
        }

        private async Task OnBtnBackAsync()
        {
            await this.GoToPreviousStepAsync();
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        //private void OnNoteChanged()
        //{
        //    if (!_noteModificate)
        //        _noteModificate = true;
        //}

        private void OpenModalModifiche()
        {
            this._editingNote = this.model.NoteText;
            this._showModalModifiche = true;
        }

        private void CloseModalModifiche()
        {
            this._editingNote = null;
            this._showModalModifiche = false;
        }

    }
}
