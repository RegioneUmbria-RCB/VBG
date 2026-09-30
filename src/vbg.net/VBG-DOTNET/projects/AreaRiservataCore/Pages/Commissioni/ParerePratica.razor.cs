using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Forms;
using System.ComponentModel.DataAnnotations;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;


namespace AreaRiservataCore.Pages.Commissioni
{
    public partial class ParerePratica
    {
        private readonly Model model = new();

        public class Model
        {
            [Required(ErrorMessage = "Seleziona un elemento nell'elenco.")]
            public CommissioniVotiBaseDto? CommissioniVotiBase { get; set; }
            public string? Note { get; set; }
            public int? CodiceOggetto { get; set; }
            public string? FileName { get; set; }
            public IBrowserFile? File { get; set; }
            public BinaryFile BinaryFile { get; set; }
            public bool IsUploaderRequired { get; set; } = false;
            public bool ModalitaInserimento { get; set; } = true;
            public DropDownItem? CommissioniVotiBaseDropDownItem { get; set; }
        }

        [Inject]
        public IVotazioniCommissioniService _votazioniCommissioniService { get; set; } = default!;

        [Inject]
        public IOggettiService _oggettiService { get; set; } = default!;

        [Inject]
        public IPostedFileSpecificationFactoryAsync _postedFileSpecificationFactoryAsync { get; set; } = default!;

        [Parameter]
        public int idCommissione { get; set; }

        [Parameter]
        public string idPratica { get; set; }

        public IValidPostedFileSpecificationAsync validPostedFileSpecificationAsync { get; set; }


        private int currentStep = 0;
        private bool RichiedeFirmaDigitale { get; set; } = false;
        private bool isBtnConfermaParereVisible { get; set; } = true;

        private List<CommissioniVotiBaseDto> ParereDataItems { get; set; }

        private VbgForm? myForm;
        private bool _showModal;

        private List<DropDownItem> ParereDataDropDownItems { get; set; } = new List<DropDownItem> { };

        protected override async Task OnInitializedAsync()
        {
            await _spinnerService.ShowSpinnerAsync(() =>
            {
                this.LoadData();
            });
        }

        private void LoadData()
        {
            if (!this._votazioniCommissioniService.UtenteLoggatoPuoEsprimereVoto(this.idCommissione, this.idPratica))
            {
                this.MessageContainer.AddError("Non è possibile esprimere un voto per questa pratica");
                this.isBtnConfermaParereVisible = false;
            }
            else
            {
                this.MessageContainer.ClearErrors();
            }

            this.ParereDataItems = this._votazioniCommissioniService.GetListaPareri().ToList();

            var voto = this._votazioniCommissioniService.GetVotoUtenteLoggato(this.idCommissione, this.idPratica);

            this.RichiedeFirmaDigitale = voto.RichiedeFirmaDigitale;

            this.validPostedFileSpecificationAsync = this._postedFileSpecificationFactoryAsync.Get(
                new FileValidationFlags
                {
                    FirmatoDigitalmente = this.RichiedeFirmaDigitale,
                    Obbligatorio = this.RichiedeFirmaDigitale
                });

            if (voto.Voto != null)
            {
                this.model.CommissioniVotiBase = this.ParereDataItems.Find(x => x.Id == voto.Voto.CodiceParere);
                this.model.Note = voto.Voto.Note;
                this.model.IsUploaderRequired = this.RichiedeFirmaDigitale;

                if (voto.Voto.CodiceOggetto.HasValue)
                {
                    this.model.ModalitaInserimento = false;
                    this.model.CodiceOggetto = voto.Voto.CodiceOggetto.Value;
                    this.model.FileName = voto.Voto.NomeFile;
                }
                else
                {
                    this.model.ModalitaInserimento = true;
                }

                this.isBtnConfermaParereVisible = false;

                this.MessageContainer.AddAlert("Hai già espresso un parere per questa pratica. Il parere può essere espresso una sola volta, contatta l'ufficio di competenza per poter modificare il parere.");
            }
            else
            {
                this.MessageContainer.ClearAlerts();
            }
        }

        //private IValidPostedFileSpecificationAsync getValidPostedFileSpecification()
        //{
        //    return this._postedFileSpecificationFactoryAsync.Get(
        //        new FileValidationFlags
        //        {
        //            FirmatoDigitalmente = this.RichiedeFirmaDigitale,
        //            Obbligatorio = this.RichiedeFirmaDigitale
        //        });
        //}

        //private void LoadFiles(InputFileChangeEventArgs e)
        //{
        //    IBrowserFile uploadedFile = e.File;

        //    bool uploadValid = uploader.ValidaFile(this._postedFileSpecificationFactory.Get(
        //        new FileValidationFlags
        //        {
        //            FirmatoDigitalmente = this.RichiedeFirmaDigitale,
        //            Obbligatorio = this.RichiedeFirmaDigitale
        //        }),
        //        uploadedFile);
        //}

        //private async Task UploadFile_OnChanged(UploadInputFormResult result)
        //{
        //    this.model.File = result.file;

        //    this.model.BinaryFile = await result.file.ToBinaryFile(this.validPostedFileSpecificationAsync);
        //    this.model.ModalitaInserimento = false;

        //    this.myForm.ClearValidationMessage(new FieldIdentifier(this.model, "FileName"));
        //}

        private void OnValidSubmit()
        {
            if (this.model.IsUploaderRequired && this.model.File == null)
            {
                this.myForm.AddValidationMessage(new FieldIdentifier(this.model, "FileName"), "E' necessario allegare un file");

                return;
            }

            _showModal = true;
        }

        private async Task OnBtnSalvaVotoAsync()
        {
            await _spinnerService.ShowSpinnerAsync(() =>
            {
                this.MessageContainer.ClearAll();

                try
                {
                    var codiceParere = this.model.CommissioniVotiBase.Id.ToString();
                    var note = this.model.Note;
                    //int? codiceOggetto = null;
                    var codiceOggetto = model.CodiceOggetto;

                    if (String.IsNullOrEmpty(codiceParere))
                    {
                        this.MessageContainer.AddAlert("Selezionare un parere dalla lista");
                        return;
                    }

                    //using (var memStream = new MemoryStream())
                    //{
                    //    await this.model.File.OpenReadStream().CopyToAsync(memStream);

                    //    codiceOggetto = this._oggettiService.InserisciOggetto(this.model.File.Name, this.model.File.ContentType, memStream.GetBuffer());
                    //}

                    //if (this.model.BinaryFile != null)
                    //{
                    //    codiceOggetto = this._oggettiService.InserisciOggetto(this.model.BinaryFile);
                    //}

                    if (this.RichiedeFirmaDigitale && codiceOggetto == null)
                    {
                        this.MessageContainer.AddError("Per esprimere un voto è necessario caricare un file contenente le motivazioni del parere");
                        return;
                    }

                    var voto = new VotoPraticaCommissioneDto
                    {
                        CodiceParere = Convert.ToInt32(codiceParere),
                        DescrizioneParere = this.model.CommissioniVotiBase.Descrizione,
                        CodiceOggetto = codiceOggetto,
                        Note = note
                    };

                    this._votazioniCommissioniService.EsprimiVotoPerUtenteLoggato(this.idCommissione, this.idPratica, voto);

                    this.currentStep = 1;

                }
                catch (Exception ex)
                {
                    this.MessageContainer.AddError("Errore durante il salvataggio del voto");
                    Logger.Error($"Errore durante il salvataggio del voto: {ex.Message}");
                }
            });
        }

        private async Task OnBtnCloseAsync()
        {
            await this.GotoAsync($"praticadettaglio/{this.idCommissione}/{this.idPratica}");
        }

    }
}
