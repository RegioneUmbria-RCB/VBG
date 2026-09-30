using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Exceptions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.PropertiesResolver;
using Init.SIGePro.Manager.DTO.Comuni;
using log4net;
using Microsoft.AspNetCore.Components;
using System.ComponentModel.DataAnnotations;
using System.Text;
using System.Text.RegularExpressions;
using VBG.BlazorComponentsLibrary.DesignComuni.Modals;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche
{
    public partial class GestioneAnagraficheSemplificata
    {
        [Inject]
        public CondizioniUscitaGestioneAnagraficheSemplificata _condizioneUscita { get; set; } = default!;

        [Inject]
        protected IConfigurazione<ParametriPresentazioneDomanda> _configurazione { get; set; } = default!;

        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; } = default!;
        [CascadingParameter]
        private PaginatoreStateService _paginatoreStateService { get; set; } = default!;

        public string TestoInserimentoUtenteLoggato { get; set; }

        public string TestoInserimentoAziendaIntermediario { get; set; }

        public string TestoInserimentoRichiedente { get; set; }

        public string TestoInserimentoAziendaRichiedente { get; set; }

        public string TestoTuttiISoggettiPresenti { get; set; }

        public bool UtenteLoggatoPresente
        {
            get
            {
                return this.DomandaCorrente.Anagrafiche.Anagrafiche.Where(x => x.Codicefiscale.ToUpper() == this.UserAuthenticationResult.DatiUtente.Codicefiscale.ToUpper()).Any();
            }
        }


        public bool VerificaPresenzaUtenteLoggato { get; set; } = true;

        private string _testoDescrizioneStep = String.Empty;
        private int currentView = 0;
        private bool tipoPersonaVisible = false;
        private bool cmdNuovoVisible = true;
        private string cmdNuovoText = "";
        private string ltrTestoSostitutivo = "";

        private IEnumerable<RichiedentiBindingItem> RichiedentiDataSource;
        private PresentazioneIstanzaDbV2.ANAGRAFERow DettagliAnagrafica_DataSource;
        private bool DettagliAnagrafica_PermettiModificaDatiAnagrafici;
        private bool DettagliAnagrafica_PermettiModificaTipoSoggetto;
        //private string labelVerifica;
        private List<DropDownItem> TipoPersonaDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> TipoSoggettoDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> AziendeDataItems { get; set; } = new List<DropDownItem>();

        private readonly ModelAnagrafica modelNuovaAnagrafica = new();
        private readonly List<ModelRichiedenti> modelRichiedentiList = new List<ModelRichiedenti>();
        private ModelRichiedenti richiedenteDaEliminare = default!;
        private bool _showConfirmationModal;
        private BasicModal? _editModal;
        private FormModel _model = new();
        private string _titoloModal = "Modifica soggetto";
        private string labelVerifica;
        private bool firstRender = true;


        private class FormModel
        {
            [Required]
            public int? IdAnagraficaCollegata = null;

        }

        public class ModelAnagrafica
        {
            public DropDownItem? TipoPersona { get; set; }
            public DropDownItem? TipoSoggetto { get; set; }
            public string? CodiceFiscale { get; set; }
        }
        public class ModelRichiedenti
        {
            public int? Id { get; set; }
            public string? Nominativo { get; set; }
            public string? InQualitaDi { get; set; }
            public DropDownItem? AziendaCollegata { get; set; }
            public bool EditMode { get; set; }
            public bool ShowCommandButtons { get; set; }
        }

        protected override void OnInitializeStep()
        {
            this._condizioneUscita.FlagVerificaPecObbligatoria = this.VerificaPecObbligatoria;
            this._condizioneUscita.MessaggioUtenteNonPresente = this.MessaggioUtenteNonPresente;

            this.AnagraficheService.SincronizzaFlagsTipiSoggetto(this.IdDomanda.Value);
            this.AnagraficheService.VerificaFlagsCittadiniExtracomunitari(this.IdDomanda.Value);

            this._testoDescrizioneStep = this.TestoDescrizioneSteps;
        }

        protected override void OnLoadStep()
        {
            base.OnLoadStep();

            this.BindTipoPersona();
            //this.TipoSoggettoDataBind("F", null);

            if (!this.UtenteLoggatoPresente)
            {
                this.ImpostaDatiUtenteCorrente();
            }
            else
            {
                this.DataBind();
            }

            this.tipoPersonaVisible = !this._configurazione.Parametri.RichiedenteSoloPersoneFisiche;

            this.BindTipoSoggetto();

            this.SettaTestoSostitutivo();
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

        private void InizializzaBottoneAggiungiSoggetto()
        {
            if (this.GetTipoSoggettoDaInserire() != null)
            {
                this.cmdNuovoVisible = true;
                this.SettaTestoBottoneAggiungiSoggetto();
            }
            else
            {
                this.cmdNuovoVisible = false;
            }
        }

        private RuoloTipoSoggettoDomandaEnum? GetTipoSoggettoDaInserire()
        {
            var anagrafiche = new List<AnagraficaDomanda>();

            var soggettoLoggatoPresente = this.DomandaCorrente.Anagrafiche.Anagrafiche.FirstOrDefault(x => x.Codicefiscale.Equals(this.UserAuthenticationResult.DatiUtente.Codicefiscale, StringComparison.InvariantCultureIgnoreCase));

            if (soggettoLoggatoPresente == null)
            {
                return RuoloTipoSoggettoDomandaEnum.Richiedente;
            }
            else if (soggettoLoggatoPresente.TipoSoggetto.RichiedeAnagraficaCollegata && !soggettoLoggatoPresente.IdAnagraficaCollegata.HasValue)
            {
                return (soggettoLoggatoPresente.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente) ? RuoloTipoSoggettoDomandaEnum.Azienda : RuoloTipoSoggettoDomandaEnum.Altro;
            }



            var richiedentePresente = this.DomandaCorrente.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente).Any();

            if (!richiedentePresente)
                return RuoloTipoSoggettoDomandaEnum.Richiedente;

            var aziendaRichiesta = this.DomandaCorrente.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente && x.TipoSoggetto.RichiedeAnagraficaCollegata).Any();

            if (!aziendaRichiesta)
                return null;

            var aziendaPresente = this.DomandaCorrente.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Azienda).Any();

            if (!aziendaPresente)
                return RuoloTipoSoggettoDomandaEnum.Azienda;

            return null;
        }

        private void SettaTestoBottoneAggiungiSoggetto()
        {

            if (!this.UtenteLoggatoPresente)
            {
                this.cmdNuovoText = Constants.TestoAggiungiUtenteLoggato;
            }
            else
            {
                var t = this.GetTipoSoggettoDaInserire();

                if (t.HasValue)
                {
                    if (t.Value == RuoloTipoSoggettoDomandaEnum.Azienda)
                    {
                        this.cmdNuovoText = Constants.TestoAggiungiAzienda;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Richiedente)
                    {
                        this.cmdNuovoText = Constants.TestoAggiungiRichiedente;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Altro)
                    {
                        this.cmdNuovoText = Constants.TestoAggiungiAziendaUtenteLoggato;
                    }
                }
            }
        }

        private void SettaTestoSostitutivo()
        {
            //this._paginatoreStateService.NascondiBottoneAvanti();

            if (!this.UtenteLoggatoPresente)
            {
                this.ltrTestoSostitutivo = this.TestoInserimentoUtenteLoggato;
            }
            else
            {
                var t = this.GetTipoSoggettoDaInserire();

                if (t.HasValue)
                {
                    if (t.Value == RuoloTipoSoggettoDomandaEnum.Azienda)
                    {
                        this.ltrTestoSostitutivo = this.TestoInserimentoAziendaRichiedente;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Richiedente)
                    {
                        this.ltrTestoSostitutivo = this.TestoInserimentoRichiedente;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Altro)
                    {
                        this.ltrTestoSostitutivo = this.TestoInserimentoAziendaIntermediario;
                    }
                }
                else
                {
                    this.ltrTestoSostitutivo = this.TestoTuttiISoggettiPresenti;
                    this._paginatoreStateService.MostraBottoneAvanti();
                }
            }

            this.ltrTestoSostitutivo = new SostituisciStringaResolver(this.ltrTestoSostitutivo).Risolvi(this);
        }

        private void BindTipoPersona()
        {
            if (this.TipoPersonaDataItems != null && this.TipoPersonaDataItems.Count > 0)
                return;

            var d1 = new Dictionary<string, object>();
            d1.Add("data-verifica", "CF");

            var d2 = new Dictionary<string, object>();
            d2.Add("data-verifica", "PI");

            var d3 = new Dictionary<string, object>();
            d3.Add("data-verifica", "CF");

            this.TipoPersonaDataItems = new List<DropDownItem>()
            {
                new DropDownItem("F", "Persona fisica", d1) { },
                new DropDownItem("G1", "Società", d2) { },
                new DropDownItem("G2", "Impresa individuale", d3) { }
            };
        }

        private bool getGestioneSoggettoUnico()
        {
            return !(this.GestioneSoggettoUnico && this.DomandaCorrente.Anagrafiche.Anagrafiche.Any());
        }

        private void OnEndEdit()
        {
            this.currentView = PageViews.Lista;
            //this._paginatoreStateService.MostraPaginatore();
            this.MessageContainer.ClearErrors();

            this.DataBind();
        }

        private async Task OnAcceptEditAsync(AnagraficaDomanda row)
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                try
                {
                    var idAnagrafica = this.AnagraficheService.SalvaAnagrafica(this.IdDomanda.Value, row);

                    if (row.TipoPersona == TipoPersonaEnum.Giuridica)
                    {
                        this.CercaECollegaPersonaFisica(idAnagrafica);
                    }

                    this.InizializzaRicercaUtenti();

                    //if (!this.GetTipoSoggettoDaInserire().HasValue)
                    //{
                    this.OnEndEdit();
                    //}

                    this.SettaTestoSostitutivo();
                }
                catch (Exception ex)
                {
                    this.m_logger.ErrorFormat("Errore in OnAcceptEdit: {0}", ex.ToString());

                    this.MessageContainer.AddError("Errore durante il salvataggio dell'anagrafica");
                    this.Logger.Error($"Errore durante il salvataggio dell'anagrafica: {ex.Message}");
                }
            });
        }

        private void CercaECollegaPersonaFisica(int idAnagrafica)
        {
            var anagraficaCorrente = this.AnagraficheService.GetById(this.IdDomanda.Value, idAnagrafica);
            var anagraficaCollegabile = (anagraficaCorrente.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Altro) ? this.AnagraficheService.GetTecnico(this.IdDomanda.Value) : this.AnagraficheService.GetRichiedente(this.IdDomanda.Value);

            if (anagraficaCorrente.Id != anagraficaCollegabile.Id)
            {
                this.AnagraficheService.CollegaAziendaAdAnagrafica(this.IdDomanda.Value, anagraficaCollegabile.Id.Value, anagraficaCorrente.Id.Value);
            }
        }

        private void InizializzaRicercaUtenti()
        {
            if (!this.UtenteLoggatoPresente)
            {
                this.ImpostaDatiUtenteCorrente();
            }
            else
            {
                var tsdi = this.GetTipoSoggettoDaInserire();

                if (tsdi.HasValue)
                {
                    var tsd = new TipoSoggettoDomanda();
                    tsd.Ruolo = tsdi.Value;

                    if (tsd.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente || tsd.Ruolo == RuoloTipoSoggettoDomandaEnum.Tecnico)
                        this.modelNuovaAnagrafica.TipoPersona = this.TipoPersonaDataItems.Find(x => x.Value == "F");
                    else
                        this.modelNuovaAnagrafica.TipoPersona = this.TipoPersonaDataItems.Find(x => x.Value == "G1");


                    this.BindTipoSoggetto(tsd.Ruolo);

                    this.currentView = PageViews.NuovaAnagrafica;
                }
            }
        }

        private void BindTipoSoggetto()
        {
            var tsdi = this.GetTipoSoggettoDaInserire();

            if (tsdi.HasValue)
                this.BindTipoSoggetto(tsdi.Value);
        }

        private void BindTipoSoggetto(RuoloTipoSoggettoDomandaEnum ruolo)
        {
            if (this.modelNuovaAnagrafica.TipoPersona == null)
                this.modelNuovaAnagrafica.TipoPersona = this.TipoPersonaDataItems.Find(x => x.Value == "F");

            this.TipoSoggettoDataBind(this.modelNuovaAnagrafica.TipoPersona.Value, ruolo);
        }

        private void TipoSoggettoDataBind(string tipoSoggetto, RuoloTipoSoggettoDomandaEnum? ruolo)
        {
            this.TipoSoggettoDataItems.Clear();

            IEnumerable<TipoSoggetto> list = tipoSoggetto == "F" ?
                this._tipiSoggettoRepository.GetTipiSoggettoPersonaFisica(this.CodiceIntervento) :
                this._tipiSoggettoRepository.GetTipiSoggettoPersonaGiurudica(this.CodiceIntervento);

            if (ruolo != null && ruolo.HasValue)
            {
                var tsd = new TipoSoggettoDomanda();
                tsd.Ruolo = ruolo.Value;

                if (tsd.Ruolo != RuoloTipoSoggettoDomandaEnum.Altro)
                {
                    list = list.Where(x => x.FlagTipoDato == tsd.RuoloAsCodiceBackoffice());
                }
                else
                {
                    list = list.Where(x => String.IsNullOrEmpty(x.FlagTipoDato));
                }

            }

            foreach (var item in list)
            {
                this.TipoSoggettoDataItems.Add(new DropDownItem(item.Id.ToString(), item.Descrizione) { });
            }

            //this.TipoSoggettoDataItems.Insert(0, new DropDownItem("", "Selezionare...") { });
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
            public int Id { get; set; }
            public string Nominativo { get; set; } = "";
            public string InQualitaDi { get; set; } = "";
            public string AziendaCollegata { get; set; } = "";
            public string TestoLinkCollegaAzienda { get; set; } = "";
            public bool MostraLinkCollegaAzienda { get; set; }
            public IEnumerable<KeyValuePair<int, string>> AziendeCollegabili { get; set; } = Enumerable.Empty<KeyValuePair<int, string>>();
            public TipoPersonaEnum TipoPersona { get; set; }
        }

        public void DataBind()
        {
            var aziendeCollegabili = this.DomandaCorrente
                                               .Anagrafiche
                                               .GetAnagraficheCollegabili()
                                               .Select(x => new KeyValuePair<int, string>(x.Id.Value, x.ToString()))
                                               .ToList();

            if (aziendeCollegabili.Count == 0)
                aziendeCollegabili.Add(new KeyValuePair<int, string>(-1, "Tra i soggetti dell'istanza non sono presenti aziende"));

            this.RichiedentiDataSource = this.DomandaCorrente
                                                 .Anagrafiche
                                                 .Anagrafiche
                                                 .OrderBy(x => x.TipoPersona)
                                                 .ThenBy(x => x.Nominativo)
                                                 .Select(x => new RichiedentiBindingItem
                                                 {
                                                     Id = x.Id.Value,
                                                     Nominativo = x.ToString(),
                                                     InQualitaDi = x.TipoSoggetto.ToString(),
                                                     AziendaCollegata = x.AnagraficaCollegata != null ? x.AnagraficaCollegata.ToString() : String.Empty,
                                                     MostraLinkCollegaAzienda = x.TipoSoggetto.RichiedeAnagraficaCollegata,
                                                     TestoLinkCollegaAzienda = x.IdAnagraficaCollegata.HasValue ? "Modifica collegamento" : "Collega azienda",
                                                     AziendeCollegabili = aziendeCollegabili,
                                                     TipoPersona = x.TipoPersona
                                                 });


            this.InizializzaBottoneAggiungiSoggetto();
            this.BindTipoSoggetto();
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

            if (row.TipoPersona == TipoPersonaEnum.Fisica) // PersonaFisica
            {
                this.currentView = PageViews.EditPersonaFisica;

                //if (VerificaPecObbligatoria)
                //    DettagliAnagraficaPf_MessaggioVerificaPec = MessaggioAvvertimentoVerificaPEC;
            }
            else
            {
                this.currentView = PageViews.EditPersonaGiuridica;
            }

            this.DettagliAnagrafica_PermettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;

            //this.DettagliAnagrafica_PermettiModificaTipoSoggetto = (this.modelNuovaAnagrafica.TipoSoggetto == null || String.IsNullOrEmpty(this.modelNuovaAnagrafica.TipoSoggetto.Value)) && row.TipoSoggetto.Id == -1;
            this.DettagliAnagrafica_PermettiModificaTipoSoggetto = true;

            this.DettagliAnagrafica_DataSource = row.ToAnagrafeRow();

            //this._paginatoreStateService.NascondiPaginatore();
        }

        private void OnBtnNew()
        {
            this.currentView = PageViews.NuovaAnagrafica;
            //this._paginatoreStateService.NascondiPaginatore();

            this.InizializzaRicercaUtenti();

            //this._paginatoreStateService.NascondiBottoneAvanti();
            this.labelVerifica = "CFF";
            this._titoloModal = "Aggiungi soggetto";
            this.modelNuovaAnagrafica.CodiceFiscale = "";
            this.modelNuovaAnagrafica.TipoPersona = this.TipoPersonaDataItems.Find(x => x.Value == "F");
        }

        private async Task OnBtnFindAsync()
        {
            this.MessageContainer.ClearAll();

            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                if (String.IsNullOrEmpty(this.modelNuovaAnagrafica.CodiceFiscale))
                {
                    if (this.modelNuovaAnagrafica.TipoPersona?.Value == "F")
                        this.MessageContainer.AddError("Inserire un codice fiscale");
                    else
                        this.MessageContainer.AddError("Inserire un codice fiscale o una partita iva");

                    await this.ScrollToTopAsync();

                    return;
                }

                this.modelNuovaAnagrafica.CodiceFiscale = this.modelNuovaAnagrafica.CodiceFiscale.ToUpper();

                if (!Regex.IsMatch(this.modelNuovaAnagrafica.CodiceFiscale, "^[a-zA-Z0-9]+$"))
                {
                    var messaggioErrore = new StringBuilder();

                    messaggioErrore.Append(this.modelNuovaAnagrafica.TipoPersona?.Value == "F" ? "Il codice fiscale immesso" : "La partita iva immessa");
                    messaggioErrore.Append(" contiene caratteri non validi. Verificare i dati immessi.");

                    this.MessageContainer.AddError(messaggioErrore.ToString());

                    await this.ScrollToTopAsync();

                    return;
                }

                try
                {
                    var tipoPersonaEnum = this.modelNuovaAnagrafica.TipoPersona?.Value == "F" ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica;

                    var anagrafe = await this.RicercheAnagraficheService.RicercaAnagraficaAsync(this.IdDomanda.Value, tipoPersonaEnum, this.modelNuovaAnagrafica.CodiceFiscale, this.IgnoraRicercaBackofficePerPersoneFisiche);

                    //anagrafe.TipoSoggetto = this.GetTipoSoggetto(Convert.ToInt32(this.modelNuovaAnagrafica.TipoSoggetto?.Value)).ToTipoSoggettoDomanda();

                    this.Edit(anagrafe);
                }
                catch (Exception ex) // Errore di comunicazione con il web service... Come lo gestiamo?
                {
                    this.MessageContainer.AddError("Si è verificato un errore durante la ricerca dell'anagrafica");
                    await this.ScrollToTopAsync();
                    LogManager.GetLogger(this.GetType()).Error(ex.ToString());
                    this.m_logger.ErrorFormat(ex.ToString());
                }
            });

        }

        private async Task OnBtnAnnulAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this.OnEndEdit();
            });
        }

        //private async Task onLinkCompany(ModelRichiedenti r)
        //{
        //    r.EditMode = true;
        //    this.modelRichiedentiList.FindAll(x => x.Id != r.Id).ForEach(x => x.ShowCommandButtons = false);
        //    //this._paginatoreStateService.NascondiPaginatore();
        //}

        private async Task OnRowEditAsync(ModelRichiedenti r)
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this._titoloModal = "Modifica soggetto";
                this.Edit(this.DomandaCorrente.Anagrafiche.GetById(r.Id.Value));
            });
        }

        private async Task OnRowDeleteAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                if (this.richiedenteDaEliminare != null)
                {
                    this.AnagraficheService.RimuoviAnagrafica(this.IdDomanda.Value, this.richiedenteDaEliminare.Id.Value);
                    this.modelRichiedentiList.Remove(this.richiedenteDaEliminare);
                    this.DataBind();
                    this._showConfirmationModal = false;
                }
            });
        }

        private async Task OnRowConfirmAsync(ModelRichiedenti r)
        {
            //if (r.AziendaCollegata == null || string.IsNullOrEmpty(r.AziendaCollegata.Value)
            if (this._model.IdAnagraficaCollegata == null)
                return;

            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                //r.EditMode = false;
                this.modelRichiedentiList.ForEach(x => x.ShowCommandButtons = true);
                //this._paginatoreStateService.MostraPaginatore();

                this.AnagraficheService.CollegaAziendaAdAnagrafica(this.IdDomanda.Value, r.Id.Value, Convert.ToInt32(this._model.IdAnagraficaCollegata));

                this.DataBind();

                this._editModal?.Hide();
            });
        }

        //private async Task onRowUndo(ModelRichiedenti r)
        //{
        //    r.EditMode = false;
        //    this.modelRichiedentiList.ForEach(x => x.ShowCommandButtons = true);
        //    this._paginatoreStateService.MostraPaginatore();
        //}

        private void ShowConfirmationModal(ModelRichiedenti r)
        {
            this.richiedenteDaEliminare = r;
            this._showConfirmationModal = true;
        }

        private void ModificaCollegamento()
        {
            this._editModal?.Show();
        }

        private void ValidationCallBackCollegamento(FormMessageStore store)
        {
            //this._errors = Array.Empty<string>();
            this.MessageContainer.ClearErrors();

            if (this._model.IdAnagraficaCollegata is null)
            {
                //var errMsg = $"Selezionare un'azienda da collegare all'anagrafica {Anagrafica.NomeEsteso}";
                var errMsg = $"Selezionare un'azienda da collegare all'anagrafica";


                store.Add(() => this._model.IdAnagraficaCollegata, errMsg);

                this.MessageContainer.AddError(errMsg);
                //_errors = new[] { errMsg };
            }
        }

        private void setLabelVerifica(DropDownItem item)
        {
            object verifica = null;

            item.AdditionalValues.TryGetValue("data-verifica", out verifica);

            this.labelVerifica = verifica.ToString() + item.Value;
        }

        private string getValueByLabelVerifica()
        {
            switch (this.labelVerifica.Substring(0, 3))
            {
                case "CFF":
                    return "Codice fiscale";
                case "PIG":
                    return "Codice fiscale impresa";
                case "CFG":
                    return "Codice fiscale titolare";
                default:
                    return string.Empty;
            }
        }

        private string getResourceIdByLabelVerifica()
        {
            switch (this.labelVerifica)
            {
                case "CFF":
                    return "gestione-anagrafiche.ricerca.codice-fiscale";
                case "PIG":
                    return "gestione-anagrafiche.ricerca.codice-fiscale-impresa";
                case "CFG":
                    return "gestione-anagrafiche.ricerca.codice-fiscale-titolare";
                default:
                    return string.Empty;
            }
        }

        // Implementati metodi astratti non utilizzati in questa pagina
        protected override TipoSoggetto GetTipoSoggetto(int id)
        {
            // Add logic to retrieve and return a TipoSoggetto based on the id  
            throw new NotImplementedException();
        }

        protected override IEnumerable<TipoSoggetto> GetTipiSoggettoPersGiuridica()
        {
            // Add logic to retrieve and return a collection of TipoSoggetto for Persone Giuridiche  
            throw new NotImplementedException();
        }

        protected override IEnumerable<TipoSoggetto> GetTipiSoggettoPersFisica()
        {
            // Add logic to retrieve and return a collection of TipoSoggetto for Persone Fisiche  
            throw new NotImplementedException();
        }
    }
}