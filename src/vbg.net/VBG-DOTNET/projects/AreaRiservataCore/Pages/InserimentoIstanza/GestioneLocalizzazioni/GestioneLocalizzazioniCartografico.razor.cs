using AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni;
using AreaRiservataCore.Shared.Localizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneLocalizzazioni
{
    public partial class GestioneLocalizzazioniCartografico
    {
        private static class Constants
        {
            public const int IdColonnaKm = 1;
            public const int IdColonnaCoordinate = 2;
        }

        [Inject]
        public ILocalizzazioniSICDomandaService _localizzazioniSicService { get; set; } = default!;

        [Inject]
        public ISitCartograficoService _sitCartograficoService { get; set; } = default!;

        [Inject]
        public IDatiDinamiciService _datiDinamiciService { get; set; } = default!;

        [Inject]
        public IStradarioRepository _stradarioRepository { get; set; } = default!;

        [Inject]
        protected CivicoValidoSpecification _civicoValidoSpecification { get; set; } = default!;

        [Inject]
        protected EsponenteValidoSpecification _esponenteValidoSpecification { get; set; } = default!;

        [Inject]
        protected IConfigurazione<ParametriLocalizzazioni> _configurazione { get; set; } = default!;

        protected bool CivicoNumerico { get { return this._configurazione.Parametri.UsaCiviciNumerici; } }

        protected bool EsponenteNumerico { get { return this._configurazione.Parametri.UsaEsponentiNumerici; } }

        [Parameter]
        public string? UuidLocalizzazione { get; set; } = default;

        #region dati letti dai parametri del workflow

        [StepProperty]
        public string TitoloUbicazioneIntervento { get; set; } = "Ubicazione intervento";

        [StepProperty]
        public string KmEtichetta
        {
            set { this._formLocalizzazioni.Km.Etichetta = value; }
        }

        [StepProperty]
        public string NoteEtichetta
        {
            set { this._formLocalizzazioni.Note.Etichetta = value; }
        }

        [StepProperty]
        public bool NoteObbligatorio
        {
            set { this._formLocalizzazioni.Note.Obbligatorio = value; }
        }

        //--------------------------------------------------
        [StepProperty]
        public string TipoLocalizzazione { get; set; } = String.Empty;

        //--------------------------------------------------

        private bool _coordinateVisibili = true;
        [StepProperty]
        public bool CoordinateVisibili
        {
            get { return this._coordinateVisibili; }
            set
            {
                this._coordinateVisibili = value;
                this._formLocalizzazioni.Longitudine.Visibile = value;
                this._formLocalizzazioni.Latitudine.Visibile = value;
            }
        }

        [StepProperty]
        public bool CoordinateObbligatorie
        {
            set
            {
                this._formLocalizzazioni.Longitudine.Etichetta = "Longitudine";
                this._formLocalizzazioni.Latitudine.Etichetta = "Latitudine";
                this._formLocalizzazioni.Longitudine.Obbligatorio = value;
                this._formLocalizzazioni.Latitudine.Obbligatorio = value;
            }
        }

        [StepProperty]
        public string CoordinateEtichettaLongitudine
        {
            set
            {
                this._formLocalizzazioni.Longitudine.Etichetta = value;
            }
        }

        [StepProperty]
        public string CoordinateEtichettaLatitudine
        {
            set
            {
                this._formLocalizzazioni.Latitudine.Etichetta = value;
            }
        }

        [StepProperty]
        public string CoordinateEspressioneRegolare
        {
            set
            {
                this._formLocalizzazioni.Longitudine.EspressioneRegolare = value;
                this._formLocalizzazioni.Latitudine.EspressioneRegolare = value;
            }
        }

        [StepProperty]
        public string CoordinateLongitudineValoreMin
        {
            set { this._formLocalizzazioni.Longitudine.ValoreMin = value; }
        }

        [StepProperty]
        public string CoordinateLatitudineValoreMin
        {
            set { this._formLocalizzazioni.Latitudine.ValoreMin = value; }
        }

        [StepProperty]
        public string CoordinateLongitudineValoreMax
        {
            set { this._formLocalizzazioni.Longitudine.ValoreMax = value; }
        }

        [StepProperty]
        public string CoordinateLatitudineValoreMax
        {
            set { this._formLocalizzazioni.Latitudine.ValoreMax = value; }
        }

        private string _coordinateTitoloBlocco = "Coordinate";
        [StepProperty]
        public string CoordinateTitoloBlocco
        {
            set
            {
                this._coordinateTitoloBlocco = value;
            }
        }

        private string _uuidTitoloBlocco = "Codice univoco localizzazione";
        [StepProperty]
        public string UuidTitoloBlocco
        {
            set
            {
                this._uuidTitoloBlocco = value;
            }
        }

        //--------------------------------------------------
        // non sono gestiti i dati catastali
        [StepProperty]
        public bool DatiCatastaliVisibili
        {
            get
            {
                return false;
            }
            set
            {
                this._formLocalizzazioni.TipoCatasto.Visibile = value;
                this._formLocalizzazioni.Foglio.Visibile = value;
                this._formLocalizzazioni.Particella.Visibile = value;
                this._formLocalizzazioni.Sub.Visibile = value;
            }
        }

        //--------------------------------------------------

        [StepProperty]
        public int NumeroMassimoIndirizzi { get; set; } = 999;

        //--------------------------------------------------

        [StepProperty]
        public string CodiceComune { get; set; } = string.Empty;

        //--------------------------------------------------

        [StepProperty]
        public string ModelloDinamicoJsonDatiAggiuntivi { get; set; } = string.Empty;

        //--------------------------------------------------

        [StepProperty]
        public string CampoDinamicoJsonDatiAggiuntivi { get; set; } = string.Empty;

        //--------------------------------------------------

        #endregion

        private readonly FormLocalizzazioni _formLocalizzazioni = new()
        {
            CodiceCivico = new CampoHidden(),
            CodiceViario = new CampoHidden(),
            Civico = new CampoLabeled() { Etichetta = "Civico" },
            Esponente = new CampoLabeled() { Etichetta = "Esponente" },
            Colore = new CampoDropDownLabeled() { Etichetta = "Colore" },
            Scala = new CampoLabeled() { Etichetta = "Scala" },
            Piano = new CampoLabeled() { Etichetta = "Piano" },
            Interno = new CampoLabeled() { Etichetta = "Interno" },
            EsponenteInterno = new CampoLabeled() { Etichetta = "Esponente Interno" },
            Fabbricato = new CampoLabeled() { Etichetta = "Fabbricato" },
            Km = new CampoLabeled() { Etichetta = "Km" },
            Latitudine = new CampoLabeled() { Etichetta = "Latitudine" },
            Longitudine = new CampoLabeled() { Etichetta = "Longitudine" },
            //TipoCatasto = new CampoDropDownLabeled() { Etichetta = "Tipo Catasto" },
            //Sezione = new CampoHidden(),
            //Foglio = new CampoLabeled() { Etichetta = "Foglio" },
            //Particella = new CampoLabeled() { Etichetta = "Particella" },
            //Sub = new CampoLabeled() { Etichetta = "Subalterno" },
            Note = new CampoLabeled() { Etichetta = "Note" },
            AccessoTipo = new CampoHidden(),
            AccessoNumero = new CampoHidden(),
            AccessoDescrizione = new CampoHidden()
        };

        private bool showDetails = false;
        private List<IndirizzoStradario> gridDataSource;
        private DatiComuneCompatto[] dataSourceComuni;

        private Model model = new();
        private List<DropDownItem> comuniDataItems { get; set; } = new List<DropDownItem>();
        //private List<DropDownItem> tipoCatastoDataItems { get; set; } = new List<DropDownItem>();
        private IndirizzoStradario? _deletingItem;
        private string _titoloModal = "";
        private bool _isBtnShowInMapVisible = true;

        public class Model
        {
            public DropDownItem? Comune { get; set; }
            public AutocompleteFormResult Indirizzo { get; set; }
            public int? CivicoNumerico { get; set; }
            public string Civico { get; set; } = "";
            public int? EsponenteNumerico { get; set; }
            public string Esponente { get; set; } = "";
            public DropDownItem? Colore { get; set; }
            public string Scala { get; set; } = "";
            public string Piano { get; set; } = "";
            public string Interno { get; set; } = "";
            public string EsponenteInterno { get; set; } = "";
            public string Fabbricato { get; set; } = "";
            public string Km { get; set; } = "";
            public string Note { get; set; } = "";
            public string Longitudine { get; set; } = "";
            public string Latitudine { get; set; } = "";
            //public DropDownItem? TipoCatasto { get; set; }
            //public string Sezione { get; set; } = "";
            //public string Foglio { get; set; } = "";
            //public string Particella { get; set; } = "";
            //public string Subalterno { get; set; } = "";
            public string Uuid { get; set; } = Guid.NewGuid().ToString();

            public IndirizzoStradario EditingItem { get; set; }
        }

        #region Ciclo della vita dello step

        protected override bool CanEnterStep()
        {
            return true;
        }

        protected override bool CanExitStep()
        {
            if (this.DomandaCorrente.Localizzazioni.Indirizzi.Count() == 0)
            {
                this.MessageContainer.ClearErrors();
                this.MessageContainer.AddError("Inserire almeno una localizzazione");
                return false;
            }

            return true;
        }

        #endregion

        protected override void OnLoadStep()
        {
            this.CodiceComune = this.DomandaCorrente.AltriDati.CodiceComune;

            //var listaColori = this._stradarioRepository.GetListaColori(this.IdComune).ToList();

            //foreach (var colore in listaColori)
            //    this.coloriDataItems.Add(new DropDownItem(colore.CodiceColore, colore.Colore) { });
        }

        protected override async Task OnLoadStepAsync()
        {
            await this.DataBindAsync();

            try
            {
                var feats = await this._sitCartograficoService.GetFeaturesAsync();
                this._isBtnShowInMapVisible = feats.MostraMappaPresentazioneIstanza;
            }
            catch (Exception ex)
            {
                this.Logger.Error(ex.Message);
                this.MessageContainer.AddWarning("Al momento non è possibile visualizzare la mappa. Riprovare più tardi");
                this._isBtnShowInMapVisible = false;
            }

            if (!string.IsNullOrEmpty(this.UuidLocalizzazione))
            {
                //1. Verifico se si tratta di localizzazione già presente nella domanda
                var indirizzo = this.DomandaCorrente.Localizzazioni.Indirizzi.FirstOrDefault(x => x.Uuid == this.UuidLocalizzazione);
                if (indirizzo == null)
                {
                    this.OnNewRow();
                    this.model.Uuid = this.UuidLocalizzazione;
                }
                else
                {
                    this.OnRowEdit(indirizzo);
                }

                //chiamata per parametri da spostare sul service
                await this.RecuperaInfoAggiuntiveAsync();
            }

            await base.OnLoadStepAsync();
        }

        private async Task DataBindAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this._deletingItem = null;

                this.gridDataSource = this.DomandaCorrente.Localizzazioni.Indirizzi.Where(x => x.TipoLocalizzazione == (this.TipoLocalizzazione ?? "")).ToList();

                this.dataSourceComuni = this._stradarioRepository.GetComuniStradario(this.CodiceComune).ToArray();

                this.comuniDataItems.Clear();

                foreach (var item in this.dataSourceComuni)
                {
                    this.comuniDataItems.Add(new DropDownItem(item.CodiceComune, item.Comune) { });
                }

                this.showDetails = false;
            });
        }

        private async Task RecuperaInfoAggiuntiveAsync()
        {
            var uuidLocalizzazione = this.model.Uuid;

            var response = await this._sitCartograficoService.RecuperaInformazioniAggiuntiveAsync(new RecuperaInformazioniAggiuntiveRequest
            {
                IdPratica = this.IdDomanda.Value,
                RiferimentoPratica = this.DomandaCorrente.AltriDati.IdentificativoDomanda,
                UuidLocalizzazione = uuidLocalizzazione
            });

            if (response.Exceptions.Count > 0)
            {
                foreach (var exception in response.Exceptions)
                {
                    this.MessageContainer.AddError(exception);
                }
                return;
            }

            // Rimosso dal metodo RecuperaInformazioniAggiuntive del service per separare le responsabilità
            this._localizzazioniSicService.SalvaInformazioniAggiuntive(this.IdDomanda.Value, uuidLocalizzazione, response);

            this.model.Indirizzo = new AutocompleteFormResult(response.CodiceStradario.ToString(), response.Stradario);
            this.model.Km = response.Km.Replace('+', ',');
            this.model.Latitudine = response.Latitudine;
            this.model.Longitudine = response.Longitudine;
        }

        private void OnNewRow()
        {
            this._titoloModal = "Aggiungi indirizzo";
            this.clearDettaglio();
            this.showDetails = true;

            if (string.IsNullOrEmpty(this.model.Uuid))
            {
                this.model.Uuid = Guid.NewGuid().ToString();
            }
        }

        private void OnRowEdit(IndirizzoStradario item)
        {
            if (this.model is null)
            {
                throw new Exception("Model is null");
            }

            this._titoloModal = "Modifica indirizzo";
            this.clearDettaglio();
            this.showDetails = true;

            var stradario = this._stradarioRepository.GetByCodiceStradario(this.IdComune, item.CodiceStradario);

            if (stradario != null && !String.IsNullOrEmpty(stradario.ComuneLocalizzazione?.CodiceComune))
            {
                this.model.Comune = this.comuniDataItems.Find(x => x.Value == stradario.ComuneLocalizzazione?.CodiceComune);
            }

            this.model.EditingItem = item;

            int civico = 0;
            int.TryParse(item.Civico, out civico);

            int esponente = 0;
            int.TryParse(item.Esponente, out esponente);

            this.model.Indirizzo = new AutocompleteFormResult(item.CodiceStradario.ToString(), item.Indirizzo);
            this.model.CivicoNumerico = string.IsNullOrEmpty(item.Civico) ? null : civico;
            this.model.Civico = item.Civico;
            this.model.EsponenteNumerico = string.IsNullOrEmpty(item.Esponente) ? null : esponente;
            this.model.Esponente = item.Esponente;
            this.model.Colore = new DropDownItem("", ""); //this.coloriDataItems.Find(x => x.Value == item.Colore);
            this.model.Scala = item.Scala;
            this.model.Piano = item.Piano;
            this.model.Interno = item.Interno;
            this.model.EsponenteInterno = item.EsponenteInterno;
            this.model.Fabbricato = item.Fabbricato;
            this.model.Km = item.Km;
            this.model.Note = item.Note;
            this.model.Longitudine = item.Longitudine;
            this.model.Latitudine = item.Latitudine;
            this.model.Uuid = item.Uuid;

            //if (item.RiferimentiCatastali.Count() > 0)
            //{
            //    var rc = item.RiferimentiCatastali.First();

            //    this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == rc.CodiceTipoCatasto);
            //    this.model.Sezione = rc.Sezione;
            //    this.model.Foglio = rc.Foglio;
            //    this.model.Particella = rc.Particella;
            //    this.model.Subalterno = rc.Sub;
            //}
        }

        private async Task OnRowDeleteAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                RiferimentiCampoDinamico riferimentoCampoDinamico = null;

                if (!String.IsNullOrEmpty(this.CampoDinamicoJsonDatiAggiuntivi) && !String.IsNullOrEmpty(this.ModelloDinamicoJsonDatiAggiuntivi))
                {
                    riferimentoCampoDinamico = new RiferimentiCampoDinamico
                    {
                        IdCampo = this._datiDinamiciService.GetIdCampoDaNome(this.CampoDinamicoJsonDatiAggiuntivi).Value,
                        IdModello = this._datiDinamiciService.GetIdModelloDaCodice(this.ModelloDinamicoJsonDatiAggiuntivi).Value,
                        NomeCampo = this.CampoDinamicoJsonDatiAggiuntivi
                    };
                }

                this._localizzazioniSicService.EliminaLocalizzazione(this.IdDomanda.Value, this._deletingItem.Id, riferimentoCampoDinamico);

                this.model.EditingItem = null;

                await this.DataBindAsync();
            });
        }

        private async Task OnBtnConfirmAsync()
        {
            this.MessageContainer.ClearErrors();

            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                StradarioEstesoDto stradarioTrovato = this.CodiceStradarioTrovato() ? this.TrovaStradarioDaCodiceStradario() : this.TrovaStradarioDaIndirizzo();

                if (stradarioTrovato is not null)
                    await this.InserisciVoceStradarioAsync(stradarioTrovato);
                else
                    this.MessageContainer.AddWarning($"Non è stato trovato lo stradario relativo al valore: {this.model.Indirizzo.Value}");

                await this.DataBindAsync();
            });
        }

        private void OnBtnAnnullaClick()
        {
            this.showDetails = false;
        }

        private void clearDettaglio()
        {
            this.model = new Model();

            //if (this.tipoCatastoDataItems.Count == 2)
            //{
            //    this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == "F");
            //}
        }

        private StradarioEstesoDto TrovaStradarioDaCodiceStradario()
        {
            return this._stradarioRepository.GetByCodiceStradario(this.IdComune, Convert.ToInt32(this.model.Indirizzo.Value));
        }

        private StradarioEstesoDto TrovaStradarioDaIndirizzo()
        {
            return this._stradarioRepository.GetByIndirizzo(this.IdComune, this.CodiceComune, this.model.Indirizzo.AdditionalValues[0]);
        }

        private bool CodiceStradarioTrovato()
        {
            return !String.IsNullOrEmpty(this.model.Indirizzo.Value);
        }

        private async Task InserisciVoceStradarioAsync(StradarioEstesoDto stradarioTrovato)
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                var nomeVia = stradarioTrovato.Prefisso + " " + stradarioTrovato.Descrizione;

                if (!String.IsNullOrEmpty(stradarioTrovato.LocFraz))
                    nomeVia += " (" + stradarioTrovato.LocFraz + ")";

                if (this.comuniDataItems.Count > 1)
                {
                    nomeVia = $"{this.model.Comune?.Text} - {nomeVia}";
                }

                var localizzazione = new NuovaLocalizzazione(stradarioTrovato.CodiceStradario, nomeVia, this.CivicoNumerico ? this.model.CivicoNumerico.ToString() : this.model.Civico)
                {
                    Colore = this.model.Colore?.Value,
                    Esponente = this.EsponenteNumerico ? this.model.EsponenteNumerico.ToString() : this.model.Esponente,
                    EsponenteInterno = this.model.EsponenteInterno,
                    Interno = this.model.Interno,
                    Note = this.model.Note,
                    Scala = this.model.Scala,
                    Piano = this.model.Piano,
                    Fabbricato = this.model.Fabbricato,
                    Km = this.model.Km,
                    Longitudine = this.model.Longitudine,
                    Latitudine = this.model.Latitudine,
                    TipoLocalizzazione = (this.TipoLocalizzazione ?? ""),
                    //Sezione = Sezione.Valore,
                    CodiceCivico = this.model.EditingItem?.CodiceCivico,
                    CodiceViario = this.model.EditingItem?.CodiceViario,
                    AccessoTipo = this.model.EditingItem?.AccessoTipo,
                    AccessoNumero = this.model.EditingItem?.AccessoNumero,
                    AccessoDescrizione = this.model.EditingItem?.AccessoDescrizione,
                    Uuid = this.model.Uuid ?? Guid.NewGuid().ToString()
                };

                // Se non sono stati configurati correttamente i parametri del modello dinamico, non si può procedere con il 
                // salvataggio del dato nella scheda. Valutare se sollevare un errore in fase di inizializzazione dello step
                RiferimentiCampoDinamico riferimentoCampoDinamico = null;

                if (!String.IsNullOrEmpty(this.CampoDinamicoJsonDatiAggiuntivi) && !String.IsNullOrEmpty(this.ModelloDinamicoJsonDatiAggiuntivi))
                {
                    riferimentoCampoDinamico = new RiferimentiCampoDinamico
                    {
                        IdCampo = this._datiDinamiciService.GetIdCampoDaNome(this.CampoDinamicoJsonDatiAggiuntivi).Value,
                        IdModello = this._datiDinamiciService.GetIdModelloDaCodice(this.ModelloDinamicoJsonDatiAggiuntivi).Value,
                        NomeCampo = this.CampoDinamicoJsonDatiAggiuntivi
                    };
                }

                if (!String.IsNullOrEmpty(this.model.EditingItem?.Id.ToString()))
                {
                    this._localizzazioniSicService.EliminaLocalizzazione(this.IdDomanda.Value, this.model.EditingItem.Id, riferimentoCampoDinamico);
                }

                this._localizzazioniSicService.AggiungiLocalizzazione(this.IdDomanda.Value, localizzazione, riferimentoCampoDinamico);
            });
        }

        private async Task<IEnumerable<AutocompleteStradarioResultItem>> GetStradarioDataItemsAsync(string value)
        {
            var listaIndirizzi = await this._stradarioRepository.GetByMatchParzialeAsync(this.CodiceComune, this.model.Comune?.Value, value);
            var q = listaIndirizzi.Select(s => new AutocompleteStradarioResultItem
            {
                Codice = s.CodiceStradario,
                Descrizione = s.NomeVia,
                CodViario = s.CodViario
            });

            return q;
        }

        private void OnIniziaEliminazione(IndirizzoStradario indirizzo)
        {
            this._deletingItem = indirizzo;
        }

        private void OnAnnullaEliminazione()
        {
            this._deletingItem = null;
        }

        public async Task OnMostraMappaAsync()
        {
            this.MessageContainer.ClearErrors();
            this.ValidaFormPerVisualizzazioneMappa();

            if (!this.MessageContainer.HasErrors())
            {
                await this.RedirectAllaMappaAsync();
            }
        }

        private void ValidaFormPerVisualizzazioneMappa()
        {
            if (this.model.Indirizzo == null || String.IsNullOrEmpty(this.model.Indirizzo.Value) || String.IsNullOrEmpty(this.model.Km))
            {
                this.MessageContainer.AddError("Per visualizzare la mappa è necessario indicare indirizzo e km");
            }
        }

        private async Task RedirectAllaMappaAsync()
        {
            var baseUri = new Uri(this._navigationManager.BaseUri);

            var scheme = baseUri.Scheme;
            var host = baseUri.Host;
            var port = baseUri.IsDefaultPort ? string.Empty : $":{baseUri.Port}";
            // Estrai i primi 3 segmenti del path come "appName"
            var segments = new Uri(this._navigationManager.Uri).AbsolutePath.Split('/', StringSplitOptions.RemoveEmptyEntries);
            var appName = "/" + string.Join('/', segments.Take(3));

            var token = this.UserAuthenticationResult.Token;
            var idDomanda = this.IdDomanda.Value;
            var idStep = this.StepId;
            var uuidLocalizzazione = this.model.Uuid;

            var response = await this._sitCartograficoService.GeneraURLMappaAsync(new GeneraURLMappaLocalizzazioneRequest
            {
                CallbackUrl = $"{scheme}://{host}{port}{appName}/cartografico-return/{idDomanda}/{idStep}/{uuidLocalizzazione}?1=1&Token={token}&AUTH_TYPE=AUTH_TYPE_LOGIN",
                CancelUrl = "",
                CodiceComune = this.CodiceComune,
                CodiceStradario = Convert.ToInt32(this.model.Indirizzo.Value),
                Km = this.model.Km,
                UuidLocalizzazione = uuidLocalizzazione,
                RiferimentoPratica = this.DomandaCorrente.AltriDati.IdentificativoDomanda
            });

            if (!String.IsNullOrEmpty(response.Errore))
            {
                this.MessageContainer.AddError(response.Errore);
                return;
            }

            this.GotoExternalUrl(response.Url);
        }
    }
}