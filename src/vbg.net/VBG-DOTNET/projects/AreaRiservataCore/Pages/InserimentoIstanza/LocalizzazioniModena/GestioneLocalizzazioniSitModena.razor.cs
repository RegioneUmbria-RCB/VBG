using AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni;
using AreaRiservataCore.Shared.Localizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.LocalizzazioniModena
{
    public partial class GestioneLocalizzazioniSitModena
    {
        [Inject]
        protected LocalizzazioniService _localizzazioniService { get; set; } = default!;

        [Inject]
        protected ISitService _sitService { get; set; } = default!;

        [Inject]
        public IStradarioRepository _stradarioRepository { get; set; } = default!;

        [Inject]
        protected CivicoValidoSpecification _civicoValidoSpecification { get; set; } = default!;

        [Inject]
        protected EsponenteValidoSpecification _esponenteValidoSpecification { get; set; } = default!;
        [CascadingParameter]
        private PaginatoreStateService _paginatoreStateService { get; set; } = default!;

        #region dati letti dai parametri del workflow
        [StepProperty]
        public string CivicoEtichetta
        {
            set { this._formLocalizzazioni.Civico.Etichetta = value; }
        }

        [StepProperty]
        public bool CivicoObbligatorio
        {
            set { this._formLocalizzazioni.Civico.Obbligatorio = value; }
        }
        //---------------------------------------

        [StepProperty]
        public string EsponenteEtichetta
        {
            set { this._formLocalizzazioni.Esponente.Etichetta = value; }
        }

        [StepProperty]
        public bool EsponenteObbligatorio
        {
            set { this._formLocalizzazioni.Esponente.Obbligatorio = value; }
        }

        //-------------------------------------------

        [StepProperty]
        public string ColoreEtichetta
        {
            set { this._formLocalizzazioni.Colore.Etichetta = value; }
        }

        [StepProperty]
        public bool ColoreObbligatorio
        {
            set { this._formLocalizzazioni.Colore.Obbligatorio = value; }
        }

        //-------------------------------------------------


        [StepProperty]
        public string ScalaEtichetta
        {
            set { this._formLocalizzazioni.Scala.Etichetta = value; }
        }

        [StepProperty]
        public bool ScalaObbligatorio
        {
            set { this._formLocalizzazioni.Scala.Obbligatorio = value; }
        }

        //--------------------------------------------------


        [StepProperty]
        public string PianoEtichetta
        {
            set { this._formLocalizzazioni.Piano.Etichetta = value; }
        }

        [StepProperty]
        public bool PianoObbligatorio
        {
            set { this._formLocalizzazioni.Piano.Obbligatorio = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string InternoEtichetta
        {
            set { this._formLocalizzazioni.Interno.Etichetta = value; }
        }

        [StepProperty]
        public bool InternoObbligatorio
        {
            set { this._formLocalizzazioni.Interno.Obbligatorio = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string EsponenteInternoEtichetta
        {
            set { this._formLocalizzazioni.EsponenteInterno.Etichetta = value; }
        }

        [StepProperty]
        public bool EsponenteInternoObbligatorio
        {
            set { this._formLocalizzazioni.EsponenteInterno.Obbligatorio = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string FabbricatoEtichetta
        {
            set { this._formLocalizzazioni.Fabbricato.Etichetta = value; }
        }

        [StepProperty]
        public bool FabbricatoObbligatorio
        {
            set { this._formLocalizzazioni.Fabbricato.Obbligatorio = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string KmEtichetta
        {
            set { this._formLocalizzazioni.Km.Etichetta = value; }
        }

        [StepProperty]
        public bool KmObbligatorio
        {
            set { this._formLocalizzazioni.Km.Obbligatorio = value; }
        }

        //--------------------------------------------------

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
        public string TipoLocalizzazione { get; set; } = "";


        //--------------------------------------------------



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


        //--------------------------------------------------

        private bool _datiCatastaliVisibili = true;
        [StepProperty]
        public bool DatiCatastaliVisibili
        {
            get
            {
                return this._datiCatastaliVisibili;
            }
            set
            {
                this._datiCatastaliVisibili = value;
                this._formLocalizzazioni.TipoCatasto.Visibile = value;
                this._formLocalizzazioni.Foglio.Visibile = value;
                this._formLocalizzazioni.Particella.Visibile = value;
                this._formLocalizzazioni.Sub.Visibile = value;
            }
        }

        private bool _datiCatastaliObbligatori = true;
        [StepProperty]
        public bool DatiCatastaliObbligatori
        {
            get
            {
                return this._datiCatastaliObbligatori;
            }

            set
            {
                this._datiCatastaliObbligatori = value;
                this._formLocalizzazioni.TipoCatasto.Etichetta = "TipoCatasto";
                this._formLocalizzazioni.Foglio.Etichetta = "Foglio";
                this._formLocalizzazioni.Particella.Etichetta = "Particella";
                this._formLocalizzazioni.Sub.Etichetta = "Subalterno";

                this._formLocalizzazioni.TipoCatasto.Obbligatorio = value;
                this._formLocalizzazioni.Foglio.Obbligatorio = value;
                this._formLocalizzazioni.Particella.Obbligatorio = value;
                this._formLocalizzazioni.Sub.Obbligatorio = value;
            }
        }

        //--------------------------------------------------

        [StepProperty]
        public string AttivaConEndo { get; set; } = "";

        #endregion

        public string CodiceComune { get; set; } = "";

        public bool MostraLocalizzazioneDaIndirizzo { get; set; } = false;

        public bool MostraLocalizzazioneDaMappali { get; set; } = false;

        public string UrlLocalizzazioneDaIndirizzo { get; set; } = "";

        public string UrlLocalizzazioneDaMappali { get; set; } = "";

        private static class Constants
        {
            public const int ListView = 0;
            public const int DetailView = 1;
        }

        private int currentView = 0;
        private readonly FormLocalizzazioni _formLocalizzazioni = new()
        {
            Civico = new CampoLabeled { Etichetta = "Civico" },
            Esponente = new CampoLabeled { Etichetta = "Esponente" },
            Colore = new CampoLabeled { Etichetta = "Colore" },
            Scala = new CampoLabeled { Etichetta = "Scala" },
            Piano = new CampoLabeled { Etichetta = "Piano" },
            Interno = new CampoLabeled { Etichetta = "Interno" },
            EsponenteInterno = new CampoLabeled { Etichetta = "Esponente interno" },
            Fabbricato = new CampoLabeled { Etichetta = "Fabbricato" },
            Km = new CampoLabeled { Etichetta = "Km" },
            Latitudine = new CampoLabeled { Etichetta = "Latitudine" },
            Longitudine = new CampoLabeled { Etichetta = "Longitudine" },
            TipoCatasto = new CampoDropDownLabeled { Etichetta = "Tipo Catasto" },
            Foglio = new CampoLabeled { Etichetta = "Foglio" },
            Particella = new CampoLabeled { Etichetta = "Particella" },
            Sub = new CampoLabeled { Etichetta = "Subalterno" },
            Note = new CampoNull(),
            Sezione = new CampoHidden(),
            CodiceCivico = new CampoHidden(),
            CodiceViario = new CampoHidden(),

            AccessoTipo = new CampoHidden(),
            AccessoNumero = new CampoHidden(),
            AccessoDescrizione = new CampoHidden()
        };
        private List<IndirizzoStradario> gridDataSource;
        //private DatiComuneCompatto[] dataSourceComuni;
        private VbgForm? myForm;
        private Model model = new();

        public class Model
        {
            public DropDownItem? Comune { get; set; }
            public AutocompleteFormResult Indirizzo { get; set; }
            public string Civico { get; set; }
            public string Esponente { get; set; }
            public DropDownItem? Colore { get; set; }
            public string Scala { get; set; }
            public string Piano { get; set; }
            public string Interno { get; set; }
            public string EsponenteInterno { get; set; }
            public string Fabbricato { get; set; }
            public string Km { get; set; }
            public string Note { get; set; }
            public string Longitudine { get; set; }
            public string Latitudine { get; set; }
            public DropDownItem? TipoCatasto { get; set; }
            public string Sezione { get; set; }
            public string Foglio { get; set; }
            public string Particella { get; set; }
            public string Subalterno { get; set; }

            public IndirizzoStradario? EditingItem { get; set; }
        }
        private List<DropDownItem> comuniDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> coloriDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> tipoCatastoDataItems { get; set; } = new List<DropDownItem>();

        protected override bool CanEnterStep()
        {
            if (String.IsNullOrEmpty(this.AttivaConEndo))
                return true;

            var codiciEndoSelezionati = this.DomandaCorrente.Endoprocedimenti.Endoprocedimenti.Select(x => x.Codice);
            var codiciEndoAttivazioneStep = this.AttivaConEndo.Split(',').Select(x => Convert.ToInt32(x.Trim()));

            foreach (var endoSelezionato in codiciEndoSelezionati)
            {
                if (codiciEndoAttivazioneStep.Contains(endoSelezionato))
                    return true;
            }

            return false;
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

        protected override async Task OnInitializeStepAsync()
        {
            await base.OnInitializeStepAsync();

            this.CodiceComune = this.DomandaCorrente.AltriDati.CodiceComune;

            this.tipoCatastoDataItems.Add(new DropDownItem("", "") { });
            this.tipoCatastoDataItems.Add(new DropDownItem("F", "Fabbricati") { });
            this.tipoCatastoDataItems.Add(new DropDownItem("T", "Terreni") { });

            var features = this._sitService.GetFeatures();
            var campiSupportati = features.CampiSupportati;

            this._formLocalizzazioni.Civico.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Civico);
            this._formLocalizzazioni.Esponente.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Esponente);
            this._formLocalizzazioni.Colore.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Colore);
            this._formLocalizzazioni.Scala.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Scala);
            this._formLocalizzazioni.Piano.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Piano);
            this._formLocalizzazioni.Interno.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Interno);
            this._formLocalizzazioni.EsponenteInterno.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.EsponenteInterno);
            this._formLocalizzazioni.Fabbricato.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Fabbricato);
            this._formLocalizzazioni.Km.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Km);
            this._formLocalizzazioni.Latitudine.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Coordinate);
            this._formLocalizzazioni.Longitudine.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Coordinate);


            this._formLocalizzazioni.Foglio.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Foglio);
            this._formLocalizzazioni.Particella.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Particella);
            this._formLocalizzazioni.Sub.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Sub);

            this._formLocalizzazioni.TipoCatasto.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.TipoCatasto);

            this.MostraLocalizzazioneDaIndirizzo = features.VisualizzazioniSupportate.SupportaVisualizzazioneMappaDaIndirizzo();
            this.MostraLocalizzazioneDaMappali = features.VisualizzazioniSupportate.SupportaVisualizzazioneMappaDaMappale();
            this.UrlLocalizzazioneDaIndirizzo = features.VisualizzazioniSupportate.UrlVisualizzazioneMappaDaIndirizzo();
            this.UrlLocalizzazioneDaMappali = features.VisualizzazioniSupportate.UrlVisualizzazioneMappaDaMappale();
        }

        protected override async Task OnLoadStepAsync()
        {
            await this.DataBindAsync();

            await base.OnLoadStepAsync();
        }

        private async Task DataBindAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this.ViewMode();

                this.gridDataSource = this.DomandaCorrente.Localizzazioni.Indirizzi.Where(x => x.TipoLocalizzazione == this.TipoLocalizzazione).ToList();

                if (this.DomandaCorrente.Localizzazioni.Indirizzi.Count() > 0)
                    this._paginatoreStateService.MostraBottoneAvanti();
                else
                    this._paginatoreStateService.NascondiBottoneAvanti();
            });
        }

        private void ViewMode()
        {
            this.currentView = Constants.ListView;
            this._paginatoreStateService.MostraPaginatore();
        }

        private void EditMode()
        {
            this.currentView = Constants.DetailView;
            this._paginatoreStateService.NascondiPaginatore();
        }

        private void ClearDettaglio()
        {
            //this.acIndirizzo.Value = String.Empty;
            //this.acIndirizzo.Text = String.Empty;

            //this._formLocalizzazioni.SvuotaCampiEdit();
            this.model = new Model();
            /*
			txtCivico.Value = String.Empty;
			txtEsponente.Value = String.Empty;
			ddlColore.Item.SelectedIndex = 0;
			txtScala.Value = String.Empty;
			txtInterno.Value = String.Empty;
			txtEsponenteInterno.Value = String.Empty;
			txtPiano.Value = String.Empty;
			txtFabbricato.Value = String.Empty;
			txtKm.Value = String.Empty;
			*/

            if (this.tipoCatastoDataItems.Count == 2)
            {
                this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == "F");
            }
        }

        private void MostraSubalterno(string tipoCatasto)
        {
            this._formLocalizzazioni.Sub.Visibile = tipoCatasto.ToUpper() == "F";

            if (!this._formLocalizzazioni.Sub.Visibile)
                this.model.Subalterno = null;
        }

        private void OnNewRow()
        {
            this.ClearDettaglio();
            this.EditMode();
        }

        private void OnRowEdit(IndirizzoStradario item)
        {
            this.ClearDettaglio();
            this.EditMode();

            var stradario = this._stradarioRepository.GetByCodiceStradario(this.IdComune, item.CodiceStradario);

            if (stradario != null && !String.IsNullOrEmpty(stradario.ComuneLocalizzazione?.CodiceComune))
            {
                this.model.Comune = this.comuniDataItems.Find(x => x.Value == stradario.ComuneLocalizzazione?.CodiceComune);
            }

            this.model.EditingItem = item;

            var civico = 0;
            int.TryParse(item.Civico, out civico);

            var esponente = 0;
            int.TryParse(item.Esponente, out esponente);

            this.model.Indirizzo = new AutocompleteFormResult(item.CodiceStradario.ToString(), item.Indirizzo);
            this.model.Civico = item.Civico;
            this.model.Esponente = item.Esponente;
            this.model.Colore = this.coloriDataItems.Find(x => x.Value == item.Colore);
            this.model.Scala = item.Scala;
            this.model.Piano = item.Piano;
            this.model.Interno = item.Interno;
            this.model.EsponenteInterno = item.EsponenteInterno;
            this.model.Fabbricato = item.Fabbricato;
            this.model.Km = item.Km;
            this.model.Note = item.Note;
            this.model.Longitudine = item.Longitudine;
            this.model.Latitudine = item.Latitudine;

            if (item.RiferimentiCatastali.Count() > 0)
            {
                var rc = item.RiferimentiCatastali.First();

                this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == rc.CodiceTipoCatasto);
                this.model.Sezione = rc.Sezione;
                this.model.Foglio = rc.Foglio;
                this.model.Particella = rc.Particella;
                this.model.Subalterno = rc.Sub;
            }

            this.MostraSubalterno(this.model.TipoCatasto?.Value);
        }

        private async Task OnRowDeleteAsync(IndirizzoStradario item)
        {
            if (!await this.ConfirmAsync("Eliminare l'elemento selezionato?"))
                return;

            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda.Value, item.Id);

                this.model.EditingItem = null;

                await this.DataBindAsync();
            });
        }
        private async Task OnBtnConfirmAsync()
        {
            if (!(this.myForm?.EditContext?.Validate() ?? false))
                return;

            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                StradarioEstesoDto stradarioTrovato = this.CodiceStradarioTrovato() ? this.TrovaStradarioDaCodiceStradario() : this.TrovaStradarioDaIndirizzo();

                await this.InserisciVoceStradarioAsync(stradarioTrovato);
            });
        }

        private void OnBtnAnnullaClick()
        {
            this.ViewMode();
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
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                this.MessageContainer.ClearErrors();

                if (stradarioTrovato != null)
                {
                    //var civicoValido = this._civicoValidoSpecification.IsSatisfiedBy(this.model.Civico);
                    //var esponenteValido = this._esponenteValidoSpecification.IsSatisfiedBy(this.model.Esponente);

                    //if (!civicoValido)
                    //{
                    //    this.messageContainer.AddError("Il civico immesso non è valido. Il campo può contenere solamente valori numerici");
                    //}

                    //if (!esponenteValido)
                    //{
                    //    this.messageContainer.AddError("L'esponente immesso non è valido. Il campo può contenere solamente valori numerici");
                    //}

                    //if (!(civicoValido && esponenteValido))
                    //{
                    //    return;
                    //}

                    var nomeVia = stradarioTrovato.Prefisso + " " + stradarioTrovato.Descrizione;

                    if (!String.IsNullOrEmpty(stradarioTrovato.LocFraz))
                        nomeVia += " (" + stradarioTrovato.LocFraz + ")";

                    //if (this.comuniDataItems.Count > 1)
                    //{
                    //    nomeVia = $"{this.model.Comune.Text} - {nomeVia}";
                    //}

                    var localizzazione = new NuovaLocalizzazione(stradarioTrovato.CodiceStradario, nomeVia, this.model.Civico)
                    {
                        Colore = this.model.Colore?.Value,
                        Esponente = this.model.Esponente,
                        EsponenteInterno = this.model.EsponenteInterno,
                        Interno = this.model.Interno,
                        Note = this.model.Note,
                        Scala = this.model.Scala,
                        Piano = this.model.Piano,
                        Fabbricato = this.model.Fabbricato,
                        Km = this.model.Km,
                        Longitudine = this.model.Longitudine,
                        Latitudine = this.model.Latitudine,
                        TipoLocalizzazione = this.TipoLocalizzazione,
                        //Sezione = Sezione.Valore,
                        CodiceCivico = this.model.EditingItem?.CodiceCivico,
                        CodiceViario = this.model.EditingItem?.CodiceViario,
                        AccessoTipo = this.model.EditingItem?.AccessoTipo,
                        AccessoNumero = this.model.EditingItem?.AccessoNumero,
                        AccessoDescrizione = this.model.EditingItem?.AccessoDescrizione
                    };

                    var rifCatastali = this._formLocalizzazioni.GetRiferimentiCatastali(this.model.TipoCatasto?.Value, this.model.TipoCatasto?.Text, this.model.Foglio, this.model.Particella, this.model.Subalterno);

                    if (!String.IsNullOrEmpty(this.model.EditingItem?.Id.ToString()))
                    {
                        this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda.Value, this.model.EditingItem.Id);
                    }

                    this._localizzazioniService.AggiungiLocalizzazione(this.IdDomanda.Value, localizzazione, rifCatastali);

                    await this.DataBindAsync();

                }
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
    }
}