using AreaRiservataCore.Pages.InserimentoIstanza.Allegati;
using AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.InserimentoIstanza.AllegatiIntervento
{
    public partial class AllegatiIntervento
    {
        [Inject]
        public AllegatiInterventoService AllegatiInterventoService { get; set; } = default!;
        [CascadingParameter]
        private PaginatoreStateService _paginatoreStateService { get; set; } = default!;

        #region Parametri letti dal file di workflow
        [StepProperty]
        public bool SoloFirma { get; set; } = false;

        [StepProperty]
        public bool RichiediFirmaSuAllegatiLiberi { get; set; } = false;

        [StepProperty]
        public bool PermettiAllegatiMultipli { get; set; } = false;

        [StepProperty]
        public bool NascondiLegendaAttributi { get; set; } = false;

        [StepProperty]
        public bool NascondiAggiungiAllegato { get; set; } = false;

        [StepProperty]
        public int NumeroCategorie { get; set; } = 0;
        #endregion

        private List<AllegatiInterventoBindingItem>? _dataSource;
        private List<CategoriaBindingItem> _categorie;
        private AllegatoLiberoComponent? _modalAllegatiLiberi;

        protected override void OnInitializeStep()
        {
            this.AllegatiInterventoService.Sincronizza(this.IdDomanda.Value);
        }

        protected override void OnLoadStep()
        {
            this.DataBind();
        }

        protected override void OnParametersSet()
        {
            base.OnParametersSet();

            // this._leggendaAttributiVisible = !this.NascondiLegendaAttributi;
        }

        protected override bool CanEnterStep()
        {
            return this.DomandaCorrente.Documenti.Intervento.Documenti.Any(x => !x.RiepilogoDomanda);
        }

        protected override bool CanExitStep()
        {
            this.MessageContainer.ClearErrors();

            var listaNomiFilesNonPresenti = this.DomandaCorrente.Documenti.Intervento.GetNomiDocumentiRichiestiENonPresenti();

            foreach (var file in listaNomiFilesNonPresenti)
            {
                this.MessageContainer.AddError($"L'allegato \"{file}\" è obbligatorio", false);
            }

            var listaFilesNonFirmati = this.DomandaCorrente.Documenti.Intervento.Documenti.Where(x => x.RichiedeFirmaDigitale && x.AllegatoDellUtente != null && !x.AllegatoDellUtente.FirmatoDigitalmente);

            foreach (var allegato in listaFilesNonFirmati)
            {
                this.MessageContainer.AddError($"L'allegato \"{allegato.Descrizione}\" deve essere firmato digitalmente", false);
            }

            this.MessageContainer.Refresh();

            return listaNomiFilesNonPresenti.Count() == 0 && listaFilesNonFirmati.Count() == 0;
        }

        #region binding dei dati        

        public void DataBind()
        {
            this._dataSource = this.DomandaCorrente
                .Documenti
                .Intervento
                .GetListaCategorie()
                .Select(r => new AllegatiInterventoBindingItem
                {
                    Id = r.Codice,
                    Descrizione = r.Descrizione
                })
                .ToList();

            foreach (var categoria in this._dataSource)
            {
                var res = from r in this.DomandaCorrente.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(categoria.Id)
                          where !r.RiepilogoDomanda
                          select new GrigliaAllegatiBindingItem
                          {
                              Id = r.Id,
                              CodiceOggetto = r.AllegatoDellUtente?.CodiceOggetto,
                              CodiceOggettoModello = r.CodiceOggettoModello,
                              IdDomanda = this.IdDomanda,
                              Descrizione = r.Descrizione.Replace("\n", "<br />"),
                              CanDownloadAsDoc = r.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.DOC),
                              CanDownloadAsOdt = r.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.ODT),
                              CanDownloadAsPdf = r.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.PDF),
                              CanDownloadAsPdfCompilabile = r.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.PDFC),
                              CanDownloadAsRtf = r.SupportaPrecompilazioneNelFormato(DocumentoDomanda.TipoFormatoConversione.RTF),
                              CanDownloadAsFile = r.AllegatoDellUtente != null,
                              CanDownloadAsFileSenzaPrecompilazione = r.CodiceOggettoModello.HasValue && !r.SupportaPrecompilazione(),
                              NomeFile = r.AllegatoDellUtente == null ? string.Empty : r.AllegatoDellUtente.NomeFile,
                              Richiesto = r.Richiesto,
                              RichiedeFirmaDigitale = r.RichiedeFirmaDigitale,
                              FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
                              Ordine = r.Ordine,
                              Note = r.Note,
                              SoloFirma = this.SoloFirma,
                              DimensioneMassima = r.DimensioneMassima * 1024,
                              EstensioniAmmesse = r.EstensioniAmmesse,
                              TipoAllegato = categoria
                          };

                res = res.OrderBy(x => x.Ordine).ThenBy(x => x.Descrizione);

                categoria.Allegati.AddRange(res);
            }

            this.NumeroCategorie = this._dataSource.Count;

            if (!this._dataSource.Any(x => x.Id == -1))
            {
                this._dataSource.Add(new AllegatiInterventoBindingItem() { Id = -1, Descrizione = "Altri allegati" });
            }
            /*
            if (this.SoloFirma)
            {
                this._leggendaAttributiVisible = false;
            }
            */
            this._categorie = this._dataSource.Cast<CategoriaBindingItem>().ToList();
        }

        #endregion

        #region Gestione degli eventi delle gridview

        protected void OnAllegaDocumentiMultipli()
        {
            this.DataBind();
        }
        /*
        protected void onAllegaDocumento(UploaderItem u)
        {
            if (u.BinaryFile is null)
                return;

            try
            {
                this.AllegatiInterventoService.Salva(this.IdDomanda.Value, u.Id.Value, u.BinaryFile);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.messageContainer.AddError("Errore durante il salvataggio dell'allegato");
                this._log.Error($"Errore durante il salvataggio dell'allegato: {ex.Message}");
            }
        }
        */
        protected void OnRimuoviDocumento(int idAllegato)
        {
            try
            {
                this.AllegatiInterventoService.EliminaOggettoUtente(this.IdDomanda.Value, idAllegato);

                // Se è un allegato libero lo elimino
                this.DataBind();
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

            var goBackUrl = $"inserimento-istanza/allegati-intervento/{this.IdDomanda.Value}/{this.StepId}";

            //"/{IdComune}/{Software}/inserimento-istanza/editoggetti/edit/{IdDomanda:int}/{IdAllegato:int}/{TipoAllegato}/{Timestamp:int}/{GoBackUrl}";
            var url = $"inserimento-istanza/editoggetti/edit/{this.IdDomanda.Value}/{idAllegato}/{PathUtils.UrlParametersValues.TipoAllegatoIntervento}/{DateTime.Now.Millisecond}/{goBackUrl}";

            await this.GotoAsync(url);
        }

        protected void ErroreGriglia(object sender, string messaggioErrore)
        {
            this.MessageContainer.AddError(messaggioErrore);
        }

        #endregion

        protected FileValidationFlags GetValidationSpecification(int idAllegato)
        {
            var allegato = this.DomandaCorrente.Documenti.Intervento.Documenti.Where(x => x.Id == idAllegato).FirstOrDefault();

            FileValidationFlags validationFlags = new FileValidationFlags();

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

        //protected IValidPostedFileSpecificationAsync getValidationSpecification(int idAllegato)
        //{
        //    var allegato = this.DomandaCorrente.Documenti.Intervento.Documenti.Where(x => x.Id == idAllegato).FirstOrDefault();

        //    FileValidationFlags validationFlags = new FileValidationFlags();

        //    if (allegato.DimensioneMassima > 0)
        //    {
        //        validationFlags.DimensioneMassimaBytes = allegato.DimensioneMassima * 1024;
        //    }

        //    validationFlags.EstensioniAmmesse = String.IsNullOrEmpty(allegato.EstensioniAmmesse) ?
        //        new string[0] :
        //        allegato.EstensioniAmmesse.Split(',')
        //                        .Select(x => x.Trim().ToLower())
        //                        .Select(x => x.StartsWith(".") ? x.Substring(1) : x).ToArray();

        //    validationFlags.FirmatoDigitalmente = allegato.RichiedeFirmaDigitale;

        //    var specification = this._postedFileSpecificationFactoryAsync.Get(validationFlags);

        //    return specification;
        //}


        #region inserimento di un nuovo allegato
        private void CaricaNuovoAllegato()
        {
            this._modalAllegatiLiberi?.Show();
        }
        /*
        private void OnAllegatoLiberoCaricato(NuovoAllegatoLibero)
        {
            this._nuovoAllegato = new NuovoAllegato(this._categoriaDataSource.FirstOrDefault(), this._numeroPagineDataSource.FirstOrDefault());
            this._nuovoAllegato.Descrizione = String.Empty;
            this._nuovoAllegato.NomeAllegato = String.Empty;
            this._nuovoAllegato.Formato = null;

            // ???
            this.calcolaDimensioneMassima();

            this.modalNewRow?.Show();
            this._paginatoreStateService.NascondiPaginatore();
        }
        */
        private async Task OnAllegatoLiberoCaricatoAsync(NuovoAllegatoLibero nuovoAllegato)
        {
            try
            {
                await this._spinnerService.ShowSpinnerAsync(() =>
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

                        this.AllegatiInterventoService.AggiungiAllegatoLibero(idDomanda, descrizione, file, tipoAllegato, descrizioneTipoAllegto, richiedeFirma);

                        this.DataBind();
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