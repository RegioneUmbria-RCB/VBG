using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Exceptions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.SIGePro.Manager.DTO.Comuni;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;


namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche
{
    public partial class GestioneAnagrafiche : GestioneAnagraficheStepPage<TipoSoggetto>
    {
        [Inject]
        public CondizioniUscitaGestioneAnagrafiche _condizioneUscita { get; set; } = default!;

        [Inject]
        public IElenchiProfessionaliRepository _elenchiProfessionaliRepository { get; set; } = default!;

        [Inject]
        public ITipiSoggettoService TipiSoggettoService { get; set; } = default!;
        [CascadingParameter]
        protected PaginatoreStateService _paginatoreStateService { get; set; } = default!;


        public bool VerificaPresenzaUtenteLoggato { get; set; } = true;

        protected string _testoDescrizioneStep = String.Empty;
        private int _currentView = PageViews.Lista;
        private bool _firstRender = true;
        private IEnumerable<RichiedentiBindingItem> _richiedentiDataSource = Enumerable.Empty<RichiedentiBindingItem>();
        private PresentazioneIstanzaDbV2.ANAGRAFERow? _anagraficaDaModificare;
        private bool _permettiModificaDatiAnagrafici;
        private AnagraficaDomanda? _anagraficaDaEliminare;
        private IEnumerable<AnagraficaDomanda> _anagraficheCollegabili = Enumerable.Empty<AnagraficaDomanda>();
        private string _titoloModal = "Modifica soggetto";
        private int? _currentTipoSoggetto = null;
        private Dictionary<int, int> _tipiSoggettoPfPresenti = new Dictionary<int, int>();
        private Dictionary<int, int> _tipiSoggettoPgPresenti = new Dictionary<int, int>();


        public class ModelRichiedenti
        {
            public int? Id { get; set; }
            public string? Nominativo { get; set; }
            public string? InQualitaDi { get; set; }
            public DropDownItem? AziendaCollegata { get; set; }
            public bool EditMode { get; set; }
            // public bool ShowCommandButtons { get; set; }
        }

        protected override async Task OnInitializeStepAsync()
        {
            try
            {
                await base.OnInitializeStepAsync();

                await this._spinnerService.ShowSpinnerAsync(() =>
                {
                    this._condizioneUscita.FlagVerificaPecObbligatoria = this.VerificaPecObbligatoria;
                    this._condizioneUscita.MessaggioUtenteNonPresente = this.MessaggioUtenteNonPresente;

                    this.AnagraficheService.SincronizzaFlagsTipiSoggetto(this.IdDomanda.Value);
                    this.AnagraficheService.VerificaFlagsCittadiniExtracomunitari(this.IdDomanda.Value);

                    this._testoDescrizioneStep = this.TestoDescrizioneSteps;
                });
            }
            catch (Exception)
            {
                this.MessageContainer.AddError("Errore durante il recupero dei tipi soggetto");
                this._paginatoreStateService.NascondiBottoneAvanti();
            }

        }

        protected override void OnLoadStep()
        {
            base.OnLoadStep();

            if (this._firstRender)
            {
                this._firstRender = false;

                if (this.VerificaRichiedenteAutomatico())
                {
                    this.ImpostaDatiUtenteCorrente();
                }
                else
                {
                    this.DataBind();
                }
            }
        }

        protected override bool CanExitStep()
        {
            try
            {
                this.MessageContainer.ClearErrors();
                this._condizioneUscita.VerificaPresenzaUtenteLoggato = this.VerificaPresenzaUtenteLoggato;

                return this._condizioneUscita.Verificata();
            }
            catch (StepException ex)
            {
                this.MessageContainer.AddError(ex.ErrorMessages);
            }

            return false;
        }

        private bool GetGestioneSoggettoUnico()
        {
            return !(this.GestioneSoggettoUnico && this.DomandaCorrente.Anagrafiche.Anagrafiche.Any());
        }

        private void OnEndEdit()
        {
            this._currentView = PageViews.Lista;
            this.MessageContainer.ClearErrors();

            this.DataBind();
        }

        private void OnBeforeAcceptEdit()
        {
            this.MessageContainer.ClearErrors();
        }

        private void OnCancelEdit()
        {
            this.OnEndEdit();
        }

        protected virtual Task SalvaAnagraficaAsync(AnagraficaDomanda row)
        {
            return this.AnagraficheService.SalvaAnagraficaAsync(this.IdDomanda!.Value, row);
        }

        private async Task OnAcceptEditAsync(AnagraficaDomanda row)
        {
            try
            {
                await this._spinnerService.ShowSpinnerAsync(async () =>
                {
                    await this.SalvaAnagraficaAsync(row);

                    this.OnEndEdit();
                    this.MessageContainer.ClearErrors();
                });
            }
            catch (Exception ex)
            {
                this.m_logger.ErrorFormat("Errore in OnAcceptEdit: {0}", ex.ToString());

                this.MessageContainer.AddError("Errore durante il salvataggio dell'anagrafica");
                this.Logger.Error($"Errore durante il salvataggio dell'anagrafica: {ex.Message}");
            }
        }

        private bool ShowProvinciaAlbo(AnagraficaDomanda anagrafica)
        {
            if (anagrafica.DatiIscrizioneAlboProfessionale?.IdAlbo == null)
                return true;

            var limitaDatiAlbo = new int[0];

            if (!string.IsNullOrEmpty(this.DettagliPf_LimitaDatiAlbo))
            {
                limitaDatiAlbo = this.DettagliPf_LimitaDatiAlbo.Split(',').Select(x => Convert.ToInt32(x.Trim())).ToArray();
            }

            var list = this._elenchiProfessionaliRepository.GetList()
                        .Where(x => { return limitaDatiAlbo.Length == 0 || limitaDatiAlbo.Contains(x.EpId.Value); })
                        .ToList();

            return !list.Any(x => x.EpRegionale.GetValueOrDefault(0) == 1 && x.EpId == anagrafica.DatiIscrizioneAlboProfessionale.IdAlbo);
        }

        protected IEnumerable<DatiComuneCompatto> GetComuni(string match)
        {
            return this._comuniService.FindComuneDaMatchParziale(match);
        }

        protected IEnumerable<DatiProvinciaCompatto> GetProvincie(string match)
        {
            return this._comuniService.FindProvinciaDaMatchParziale(match);
        }


        protected async Task OnErroreInserimentoAsync(string message)
        {
            this.MessageContainer.AddError(message);

            await this.ScrollToTopAsync();
        }

        public class RichiedentiBindingItem
        {
            public required AnagraficaDomanda Anagrafica { get; set; }
            public required AnagraficaDomanda? AziendaCollegata { get; set; }
            public string InQualitaDi => this.Anagrafica.TipoSoggetto.ToString();
            public string Nominativo => this.Anagrafica.ToString();
            public TipoPersonaEnum TipoPersona => this.Anagrafica.TipoPersona;
            public string TestoLinkCollegaAzienda => this.AziendaCollegata != null ? "Modifica collegamento" : "Collega azienda";
        }

        public void DataBind()
        {
            this._anagraficheCollegabili = this.DomandaCorrente
                                               .Anagrafiche
                                               .GetAnagraficheCollegabili().ToArray();

            var anagrafiche = this.DomandaCorrente
                                                 .Anagrafiche
                                                 .Anagrafiche
                                                 .OrderBy(x => x.TipoPersona)
                                                 .ThenBy(x => x.Nominativo);

            this._richiedentiDataSource = anagrafiche
                                                 .Select(x => new RichiedentiBindingItem
                                                 {
                                                     Anagrafica = x,
                                                     AziendaCollegata = x.AnagraficaCollegata
                                                 });

            this._tipiSoggettoPfPresenti = anagrafiche
                                            .Where(x => x.TipoPersona == TipoPersonaEnum.Fisica &&
                                            x.TipoSoggetto is not null &&
                                            x.TipoSoggetto.Id is not null)
                                            .GroupBy(x => x.TipoSoggetto.Id.Value)
                                            .ToDictionary(g => g.Key, g => g.Count());

            this._tipiSoggettoPgPresenti = anagrafiche
                                            .Where(x => x.TipoPersona == TipoPersonaEnum.Giuridica &&
                                            x.TipoSoggetto is not null &&
                                            x.TipoSoggetto.Id is not null)
                                            .GroupBy(x => x.TipoSoggetto.Id.Value)
                                            .ToDictionary(g => g.Key, g => g.Count());
        }


        private void ImpostaDatiUtenteCorrente()
        {
            var newRow = new AnagrafeAdapter(this.UserAuthenticationResult.DatiUtente.ToWsAnagrafe(), this._comuniService).ToAnagrafeRow();

            this.Edit(AnagraficaDomanda.FromAnagrafeRow(newRow));
        }

        protected void Edit(AnagraficaDomanda row)
        {
            var nuovaAnagrafica = new NuovaAnagraficaSpecification().IsSatisfiedBy(row);

            var permettiModificheAdAnagrafiche = nuovaAnagrafica || (!nuovaAnagrafica && this.RendiModificabiliDatiAnagraficheEsistenti);

            this._currentTipoSoggetto = row.TipoSoggetto?.Id;
            this._currentView = row.TipoPersona == TipoPersonaEnum.Fisica ?
                    PageViews.EditPersonaFisica :
                    PageViews.EditPersonaGiuridica;

            this._permettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;
            this._anagraficaDaModificare = row.ToAnagrafeRow();
        }

        private void OnBtnNew()
        {
            this._currentView = PageViews.NuovaAnagrafica;
            this._titoloModal = "Aggiungi soggetto";
        }

        private async Task OnRowEditAsync(AnagraficaDomanda anagraficaDaModificare)
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this._titoloModal = "Modifica soggetto";
                this.Edit(anagraficaDaModificare);
            });
        }

        private void MostraConfermaEliminazione(AnagraficaDomanda anagraficaDaEliminare)
        {
            this._anagraficaDaEliminare = anagraficaDaEliminare;
        }

        private async Task OnConfermaEliminazioneAnagraficaAsync()
        {
            if (this._anagraficaDaEliminare == null)
            {
                return;
            }

            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this.AnagraficheService.RimuoviAnagrafica(this.IdDomanda.Value, this._anagraficaDaEliminare.Id!.Value);
                this._anagraficaDaEliminare = null;

                this.DataBind();
            });
        }

        private async Task OnAnagraficaCollegataAsync(CollegamentoAnagraficaEventArgs e)
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this.AnagraficheService.CollegaAziendaAdAnagrafica(this.IdDomanda.Value, e.IdAnagrafica, e.IdAnagraficaDaCollegare);

                this.DataBind();
            });
        }

        private async Task OnNuovaAnagrficaRichiestaAsync(RichiestaNuovaAnagraficaEventArgs e)
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                try
                {
                    var anagrafe = await this.RicercheAnagraficheService.RicercaAnagraficaAsync(this.IdDomanda!.Value, e.TipoPersona, e.CodiceFiscale, this.IgnoraRicercaBackofficePerPersoneFisiche);

                    this.Edit(anagrafe);
                }
                catch (Exception ex) // Errore di comunicazione con il web service... Come lo gestiamo?
                {
                    this.MessageContainer?.AddError("Si è verificato un errore durante la ricerca dell'anagrafica");
                    this.m_logger.ErrorFormat(ex.ToString());
                }
            });
        }

        protected override IEnumerable<TipoSoggetto> GetTipiSoggettoPersFisica()
        {
            return this.TipiSoggettoService.GetTipiSoggettoPersonaFisica(this.CodiceIntervento);
        }

        protected override IEnumerable<TipoSoggetto> GetTipiSoggettoPersGiuridica()
        {
            return this.TipiSoggettoService.GetTipiSoggettoPersonaGiurudica(this.CodiceIntervento); ;
        }

        protected override TipoSoggetto? GetTipoSoggetto(int idTipoSoggetto)
        {
            if (idTipoSoggetto == -1)
                return null;

            return this.TipiSoggettoService.GetById(idTipoSoggetto);
        }
    }
}