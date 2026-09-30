using AreaRiservataCore.Pages.InserimentoIstanza.Allegati;
using AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Modals;

namespace AreaRiservataCore.Pages.InserimentoIstanza.AllegatiEndo
{
    public partial class AllegatiEndo
    {
        [Inject]
        public IAllegatiEndoprocedimentiService AllegatiEndoprocedimentiService { get; set; } = default!;
        [CascadingParameter]
        private PaginatoreStateService _paginatoreStateService { get; set; } = default!;

        #region Parametri letti dal file di workflow
        [StepProperty]
        public bool RichiediFirmaSuAllegatiLiberi { get; set; } = false;
        #endregion

        private List<AllegatiEndoBindingItem>? _dataSource;
        private List<CategoriaBindingItem> _categorie = new();
        private BasicModal? _modalNote { get; set; }
        private AllegatoLiberoComponent? _modalAllegatiLiberi;
        private string _noteAllegato = "";

        protected override async Task OnLoadStepAsync()
        {
            await base.OnLoadStepAsync();

            await this.DataBindAsync();
        }


        protected override void OnInitializeStep()
        {
            base.OnInitializeStep();
            this.AllegatiEndoprocedimentiService.SincronizzaAllegati(this.IdDomanda.Value);

            // this.getNewFileValidationSpecification();
        }

        protected override bool CanEnterStep()
        {
            return this.DomandaCorrente.Documenti.Endo.Documenti.Count() > 0;
        }

        protected override bool CanExitStep()
        {
            this.MessageContainer.ClearErrors();

            var listaNomiFilesNonPresenti = this.DomandaCorrente.Documenti.Endo.GetNomiDocumentiRichiestiENonPresenti();

            this.MessageContainer.AddError(listaNomiFilesNonPresenti.Select(x => string.Format("L'allegato \"{0}\" è obbligatorio", x)));

            var listaFilesNonFirmati = this.DomandaCorrente.Documenti.Endo.Documenti.Where(x => x.RichiedeFirmaDigitale && x.AllegatoDellUtente != null && !x.AllegatoDellUtente.FirmatoDigitalmente);

            this.MessageContainer.AddError(listaFilesNonFirmati.Select(x => string.Format("L'allegato \"{0}\" deve essere firmato digitalmente", x.Descrizione)));
            this.MessageContainer.Refresh();

            return listaNomiFilesNonPresenti.Count() == 0 && listaFilesNonFirmati.Count() == 0;
        }

        public async Task DataBindAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this._dataSource = new List<AllegatiEndoBindingItem>();

                foreach (var endo in this.DomandaCorrente.Endoprocedimenti.NonAcquisiti)
                {
                    var allegatiEndo = this.DomandaCorrente.Documenti.Endo.GetByIdEndo(endo.Codice);

                    if (allegatiEndo.Count() == 0)
                    {
                        continue;
                    }

                    var endoBindingItem = new AllegatiEndoBindingItem
                    {
                        Id = endo.Codice,
                        Descrizione = endo.Descrizione
                    };

                    endoBindingItem.Allegati = allegatiEndo.Select(allegatoEndo => new GrigliaAllegatiBindingItem
                    {
                        Id = allegatoEndo.Id,
                        CodiceOggetto = allegatoEndo.AllegatoDellUtente == null ? null : allegatoEndo.AllegatoDellUtente.CodiceOggetto,
                        CodiceOggettoModello = allegatoEndo.CodiceOggettoModello,
                        IdDomanda = this.IdDomanda,
                        Descrizione = allegatoEndo.Descrizione,
                        CanDownloadAsDoc = allegatoEndo.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.DOC),
                        CanDownloadAsOdt = allegatoEndo.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.ODT),
                        CanDownloadAsPdf = allegatoEndo.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.PDF),
                        CanDownloadAsPdfCompilabile = allegatoEndo.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.PDFC),
                        CanDownloadAsRtf = allegatoEndo.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.RTF),
                        CanDownloadAsFile = allegatoEndo.AllegatoDellUtente != null,
                        CanDownloadAsFileSenzaPrecompilazione = allegatoEndo.CodiceOggettoModello.HasValue && !allegatoEndo.SupportaPrecompilazione(),
                        NomeFile = allegatoEndo.AllegatoDellUtente == null ? string.Empty : allegatoEndo.AllegatoDellUtente.NomeFile,
                        Richiesto = allegatoEndo.Richiesto,
                        RichiedeFirmaDigitale = allegatoEndo.RichiedeFirmaDigitale,
                        FirmatoDigitalmente = allegatoEndo.AllegatoDellUtente == null ? false : allegatoEndo.AllegatoDellUtente.FirmatoDigitalmente,
                        Ordine = allegatoEndo.Ordine,
                        Note = allegatoEndo.Note,
                        DimensioneMassima = allegatoEndo.DimensioneMassima * 1024,
                        EstensioniAmmesse = allegatoEndo.EstensioniAmmesse
                    })
                        .OrderBy(x => x.Ordine)
                        .ThenBy(x => x.Descrizione)
                        .ToList();

                    this._dataSource.Add(endoBindingItem);
                }

                this._dataSource.Sort((a, b) => a.Descrizione.CompareTo(b.Descrizione));
                this._categorie = this._dataSource.Cast<CategoriaBindingItem>().ToList();
            });
        }

        private void OnShowNote(int idAllegato)
        {
            this._noteAllegato = this.DomandaCorrente.Documenti.Endo.Documenti.Where(x => x.Id == idAllegato).FirstOrDefault()?.Note ?? "";

            if (!String.IsNullOrEmpty(this._noteAllegato))
            {
                this._modalNote?.Show();
            }
        }

        protected FileValidationFlags GetValidationSpecification(int idAllegato)
        {
            var allegato = this.DomandaCorrente.Documenti.Endo.Documenti.Where(x => x.Id == idAllegato).FirstOrDefault();

            if (allegato is null)
            {
                throw new InvalidOperationException($"L'allegato con id {idAllegato} non è stato trovato");
            }

            var validationFlags = new FileValidationFlags();

            if (allegato.DimensioneMassima > 0)
            {
                validationFlags.DimensioneMassimaBytes = allegato.DimensioneMassima * 1024;
            }

            validationFlags.EstensioniAmmesse = String.IsNullOrEmpty(allegato.EstensioniAmmesse) ?
                new string[0] :
                allegato.EstensioniAmmesse.Split(',')
                                .Select(x => x.Trim().ToLower())
                                .Select(x => x.StartsWith(".") ? x.Substring(1) : x).ToArray();

            validationFlags.FirmatoDigitalmente = allegato.RichiedeFirmaDigitale;

            return validationFlags;
        }


        //protected async Task OnAllegaDocumentoAsync(UploaderItem u)
        //{
        //    if (u.Id is null)
        //    {
        //        return;
        //    }

        //    try
        //    {
        //        this.AllegatiEndoprocedimentiService.AggiungiAllegatoAEndo(this.IdDomanda.Value, u.Id.Value, u.BinaryFile);

        //        await this.DataBindAsync();
        //    }
        //    catch (Exception ex)
        //    {
        //        this.messageContainer.AddError("Errore durante il salvataggio dell'allegato");
        //        this._log.Error($"Errore durante il salvataggio dell'allegato: {ex.Message}");
        //    }
        //}

        protected async Task OnRimuoviDocumentoAsync(int idAllegato)
        {
            try
            {
                this.AllegatiEndoprocedimentiService.EliminaOggettoUtente(this.IdDomanda.Value, idAllegato);

                await this.DataBindAsync();
            }
            catch (Exception ex)
            {
                this.MessageContainer.AddError("Errore durante l'eliminazione dell'allegato");
                this.Logger.Error($"Errore durante l'eliminazione dell'allegato: {ex.Message}");
            }
        }

        protected async Task OnCompilaDocumentoAsync(int idAllegato)
        {
            if (idAllegato <= 0)
                throw new ArgumentNullException(nameof(idAllegato));

            string goBackUrl = $"inserimento-istanza/allegati-endo/{this.IdDomanda.Value}/{this.StepId}";

            //"/{IdComune}/{Software}/inserimento-istanza/editoggetti/edit/{IdDomanda:int}/{IdAllegato:int}/{TipoAllegato}/{Timestamp:int}/{GoBackUrl}";
            string url = $"inserimento-istanza/editoggetti/edit/{this.IdDomanda.Value}/{idAllegato}/{PathUtils.UrlParametersValues.TipoAllegatoEndo}/{DateTime.Now.Millisecond}/{goBackUrl}";

            await this.GotoAsync(url);
        }

        private void AggiungiNuovoAllegato()
        {
            this._modalAllegatiLiberi?.Show();
        }

        #region inserimento di un nuovo allegato

        private async Task OnAllegatoLiberoCaricatoAsync(NuovoAllegatoLibero nuovoAllegato)
        {
            try
            {
                await this._spinnerService.ShowSpinnerAsync(async () =>
                {

                    this.MessageContainer.ClearAll();

                    try
                    {
                        var file = nuovoAllegato.FileAllegato;

                        var idDomanda = this.IdDomanda.Value;
                        var descrizione = nuovoAllegato.Descrizione;
                        var tipoAllegato = nuovoAllegato.TipoAllegato.Id;
                        var descrizioneTipoAllegto = nuovoAllegato.TipoAllegato.Descrizione;
                        var richiedeFirma = this.RichiediFirmaSuAllegatiLiberi;

                        this.AllegatiEndoprocedimentiService.AggiungiAllegatoLibero(idDomanda, tipoAllegato, descrizione, file, richiedeFirma);

                        await this.DataBindAsync();
                    }
                    catch (Exception ex)
                    {
                        this.MessageContainer.AddError("Errore durante il salvataggio dell'allegato libero");
                        this.Logger.Error($"Errore durante il salvataggio dell'allegato libero: {ex.Message}");
                    }
                });
            }
            finally
            {
                this._paginatoreStateService.MostraPaginatore();
            }
        }
        private void OnCaricamentoAllegatoAnnullato()
        {
            this._paginatoreStateService.MostraPaginatore();
        }

        #endregion
    }
}