using AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Microsoft.AspNetCore.Components;
using VBG.AreaRiservataCore.Controls;
using VBG.BlazorComponentsLibrary.DesignComuni.Forms;
using VBG.BlazorComponentsLibrary.DesignComuni.Spinner;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati
{
    public partial class GrigliaAllegati
    {
        [Parameter, EditorRequired]
        public List<GrigliaAllegatiBindingItem> DataSource { get; set; } = new();

        [Parameter, EditorRequired]
        public EventCallback<int> OnDeleteFile { get; set; }

        [Parameter]
        public EventCallback<int> OnCompileDocument { get; set; }

        [Parameter, EditorRequired]
        public EventCallback OnEditFile { get; set; }

        [Parameter, EditorRequired]
        public Func<int, FileValidationFlags>? GetValidPostedFileSpecification { get; set; }

        [Parameter]
        public bool SoloFirma { get; set; } = false;

        [Parameter]
        public bool PermettiAllegatiMultipili { get; set; } = false;

        [Parameter]
        public EventCallback OnUploadFileMultipli { get; set; }

        [Parameter]
        public ProveninzaAllegatoEnum ProvenienzaAllegato { get; set; } = ProveninzaAllegatoEnum.Intervento;

        [Parameter]
        public IEnumerable<CategoriaBindingItem> Categorie { get; set; } = default!;

        [Inject]
        public AllegatiInterventoService AllegatiInterventoService { get; set; } = default!;

        [Inject]
        public IAllegatiEndoprocedimentiService AllegatiEndoService { get; set; } = default!;

        [Inject]
        public SpinnerService _spinnerService { get; set; } = default!;

        private List<UploaderItem> _multiUploaders = new();

        private GrigliaAllegatiBindingItem? _modalItem { get; set; }
        private List<GrigliaAllegatiBindingItem>? oldDataSource = null;

        private GrigliaAllegatiBindingItem? _editingItem;

        private bool _mostraConfermaEliminazione;
        private bool _mostraModalAllegatiMultipli;

        private int _deletingId;

        protected override void OnInitialized()
        {
            base.OnInitialized();

            if (this.PermettiAllegatiMultipili)
            {
                this._multiUploaders = new List<UploaderItem>();
            }
        }

        protected override void OnParametersSet()
        {
            base.OnParametersSet();

            this.LoadUploaders();
        }

        private void LoadUploaders()
        {
            if (this.oldDataSource == this.DataSource)
            {
                return;
            }

            this.oldDataSource = this.DataSource;

            //this.uploaders = this.DataSource.Select(item => new UploaderItem
            //{
            //    Id = item.Id,
            //    NomeFile = item.NomeFile,
            //    RichiedeFirma = item.FirmatoDigitalmente,
            //    CodiceOggetto = item.CodiceOggetto
            //}).ToList();
        }

        private async Task FillDocumentAsync(int documentId)
        {
            if (this.OnCompileDocument.HasDelegate)
                await this.OnCompileDocument.InvokeAsync(documentId); ;
        }

        private void OpenMultipleAttachment(GrigliaAllegatiBindingItem item)
        {
            this._modalItem = item;

            this._multiUploaders = [new UploaderItem()];

            this._mostraModalAllegatiMultipli = true;
        }

        private async Task upload_OnDeleteAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                try
                {
                    if (this.OnDeleteFile.HasDelegate)
                    {
                        await this.OnDeleteFile.InvokeAsync(this._deletingId);
                    }

                    this._mostraConfermaEliminazione = false;
                }
                catch (Exception ex)
                {
                    //this.errorContainerModel.Add($"Si è verificato un errore durante l'eliminazione del file {uploaders[index].FileName}: {ex.Message}");
                }
            });
        }

        private void ShowConfirmationModal(int documentId)
        {
            this._deletingId = documentId;
            this._mostraConfermaEliminazione = true;
        }

        private void multiUpload_OnChanged(FormFileChangedEventArgs e, int index)
        {
            try
            {
                this._multiUploaders[index].NomeFile = e.FileName;
                this._multiUploaders[index].CodiceOggetto = e.CodiceOggetto;

                if (e.CodiceOggetto != null)
                    this._multiUploaders.Add(new UploaderItem());
            }
            catch (Exception ex)
            {
                //this.errorContainerModel.Add($"Si è verificato un errore durante il caricamento del file {uploaders[index].FileName}: {ex.Message}");
            }
        }

        private void multiUpload_OnDelete(int index)
        {
            this._multiUploaders.RemoveAt(index);
        }

        private void OnMultiUploadChange(FormFileChangedEventArgs e, int index)
        {
            if (e.Event == FromEvent.OnChange)
                this.multiUpload_OnChanged(e, index);

            if (e.Event == FromEvent.OnDelete)
                this.multiUpload_OnDelete(index);
        }

        private async Task AddMultipleFilesAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                var filesValidi = new List<int>();
                var uploaderToRemove = new List<UploaderItem>();

                foreach (var uploader in this._multiUploaders)
                {
                    if (uploader.CodiceOggetto is null)
                    {
                        uploaderToRemove.Add(uploader);
                        continue;
                    }

                    filesValidi.Add(uploader.CodiceOggetto.Value);
                }

                this._multiUploaders.RemoveAll(x => uploaderToRemove.Contains(x));

                try
                {
                    var uploaderFactory = new AllegatiMultipliUploaderFactory(this._modalItem.IdDomanda.Value, this.AllegatiInterventoService, this.AllegatiEndoService);

                    var uploader = uploaderFactory.Get(this.ProvenienzaAllegato);
                    var allegatoOrigine = uploader.GetById(this._modalItem.Id);

                    for (int i = 0; i < filesValidi.Count; i++)
                    {
                        var file = filesValidi[i];

                        if (i == 0)
                        {
                            uploader.AggiungiAllegatoPrincipale(allegatoOrigine, file);
                        }
                        else
                        {
                            uploader.AggiungiAllegatoSecondario(allegatoOrigine, (i + 1), file);
                        }
                    }

                    if (this.OnUploadFileMultipli.HasDelegate)
                        await this.OnUploadFileMultipli.InvokeAsync();

                    this._mostraModalAllegatiMultipli = false;

                }
                catch (Exception ex)
                {
                    //this.Errori.Add("Si è verificato un errore durante il caricamento: " + ex.Message);

                    //this._log.ErrorFormat("Errore durante il caricamento di allegati multipli: " + ex.ToString());
                }
            });
        }



        private void ModificaAllegato(GrigliaAllegatiBindingItem item)
        {
            this._editingItem = item;
        }

        private async Task SalvaModificheAsync(GrigliaAllegatiBindingItem editingItem)
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {

                if (editingItem.CodiceOggetto == null)
                {
                    this.AllegatiInterventoService.EliminaOggettoUtente(this._editingItem.IdDomanda.Value, this._editingItem.Id);
                }
                else
                {
                    this.AllegatiInterventoService.Salva(editingItem.IdDomanda.Value, editingItem.Id, editingItem.CodiceOggetto.Value);
                }

                this._editingItem = null;

                await this.OnEditFile.InvokeAsync();
            });
        }

    }
}