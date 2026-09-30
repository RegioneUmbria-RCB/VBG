using AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni;
using AreaRiservataCore.Shared.Localizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Forms;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneLocalizzazioni
{
    public partial class GestioneLocalizzazioniSit
    {
        private static class Constants
        {
            public const int GRID_VIEW = 0;
            public const int DETAILS_VIEW = 1;

            public const int IdColonnaCivico = 0;
            // public const int IdColonnaEsponente = 1;
            // public const int IdColonnaColore = 2;
            public const int IdColonnaAltriDati = 1;
            public const int IdColonnaKm = 2;
            public const int IdColonnaNote = 3;
            public const int IdColonnaCoordinate = 4;
            public const int IdColonnaRiferimentiCatastali = 5;
        }

        [Inject]
        public IStradarioRepository _stradarioRepository { get; set; } = default!;

        [Inject]
        protected LocalizzazioniService _localizzazioniService { get; set; } = default!;

        [Inject]
        protected ISitService _sitService { get; set; } = default!;
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
        public string TipoLocalizzazione { get; set; } = String.Empty;


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
        public string AttivaConEndo { get; set; } = String.Empty;


        [StepProperty]
        public string CodiceComune { get; set; } = String.Empty;

        [StepProperty]
        public bool MostraLocalizzazioneDaIndirizzo { get; set; } = false;

        [StepProperty]
        public bool MostraLocalizzazioneDaMappali { get; set; } = false;

        [StepProperty]
        public string UrlLocalizzazioneDaIndirizzo { get; set; } = String.Empty;

        [StepProperty]
        public string UrlLocalizzazioneDaMappali { get; set; } = String.Empty;

        #endregion

        private readonly FormLocalizzazioni _formLocalizzazioni = new()
        {
            CodiceCivico = new CampoHidden(),
            CodiceViario = new CampoHidden(),
            Civico = new CampoLabeled() { Etichetta = "Civico" },
            Esponente = new CampoLabeled() { Etichetta = "Esponente" },
            Colore = new CampoLabeled() { Etichetta = "Colore" },
            Scala = new CampoLabeled() { Etichetta = "Scala" },
            Piano = new CampoLabeled() { Etichetta = "Piano" },
            Interno = new CampoLabeled() { Etichetta = "Interno" },
            EsponenteInterno = new CampoLabeled() { Etichetta = "Esponente Interno" },
            Fabbricato = new CampoLabeled() { Etichetta = "Fabbricato" },
            Km = new CampoLabeled() { Etichetta = "Km" },
            Latitudine = new CampoLabeled() { Etichetta = "Latitudine" },
            Longitudine = new CampoLabeled() { Etichetta = "Longitudine" },
            TipoCatasto = new CampoDropDownLabeled() { Etichetta = "Tipo Catasto" },
            Sezione = new CampoHidden(),
            Foglio = new CampoLabeled() { Etichetta = "Foglio" },
            Particella = new CampoLabeled() { Etichetta = "Particella" },
            Sub = new CampoLabeled() { Etichetta = "Subalterno" },
            Note = new CampoLabeled() { Etichetta = "Note" },
            AccessoTipo = new CampoLabeled() { Etichetta = "Tipo" },
            AccessoNumero = new CampoLabeled() { Etichetta = "Numero" },
            AccessoDescrizione = new CampoLabeled() { Etichetta = "Descrizione" }
        };
        private int currentStep = 0;
        private VbgForm myForm;
        private Model model = new();
        private List<IndirizzoStradario> _gridDataSource;
        private List<DropDownItem> tipoCatastoDataItems { get; set; } = new List<DropDownItem>();
        private IndirizzoStradario? _deletingItem;

        public class Model
        {
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
            public string AccessoTipo { get; set; }
            public string AccessoNumero { get; set; }
            public string AccessoDescrizione { get; set; }
            public string Note { get; set; }
            public string Longitudine { get; set; }
            public string Latitudine { get; set; }
            public DropDownItem? TipoCatasto { get; set; }
            public string Sezione { get; set; }
            public string Foglio { get; set; }
            public string Particella { get; set; }
            public string Sub { get; set; }

            public IndirizzoStradario EditingItem { get; set; }
        }

        protected override void OnInitializeStep()
        {
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

            this._formLocalizzazioni.AccessoTipo.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.AccessoTipo);
            this._formLocalizzazioni.AccessoNumero.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.AccessoNumero);
            this._formLocalizzazioni.AccessoDescrizione.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.AccessoDescrizione);

            this.MostraLocalizzazioneDaMappali = features.VisualizzazioniSupportate.SupportaVisualizzazioneMappaDaMappale();
            this.UrlLocalizzazioneDaIndirizzo = features.VisualizzazioniSupportate.UrlVisualizzazioneMappaDaIndirizzo();
            this.UrlLocalizzazioneDaMappali = features.VisualizzazioniSupportate.UrlVisualizzazioneMappaDaMappale();


            this.DataBind();
        }

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

        private string GetFormattedUrl(string url, IndirizzoStradario item)
        {
            Dictionary<string, string> sostituzioni = new()
            {
                { "$CIVICO$", item.Civico },
                { "$ESPONENTE$", item.Esponente },
                { "$CODVIARIO$", item.CodiceViario },
                { "$TIPOCATASTO$", item.PrimoRiferimentoCatastale?.CodiceTipoCatasto },
                { "$FOGLIO$", item.PrimoRiferimentoCatastale?.Foglio },
                { "$PARTICELLA$", item.PrimoRiferimentoCatastale?.Particella },
                { "$SUB$", item.PrimoRiferimentoCatastale?.Sub },
                { "$CODCIVICO$", item.CodiceCivico },
                { "$SEZIONE$", item.PrimoRiferimentoCatastale?.Sezione },
                { "$FABBRICATO$", item.Fabbricato }
            };

            foreach (var par in sostituzioni)
            {
                url = url.Replace(par.Key, par.Value);
            }

            return url;
        }

        private void ViewMode()
        {
            this.currentStep = Constants.GRID_VIEW;
            this._paginatoreStateService.MostraPaginatore();

            if (this.DomandaCorrente.Localizzazioni.Indirizzi.Count() > 0)
                this._paginatoreStateService.MostraBottoneAvanti();
            else
                this._paginatoreStateService.NascondiBottoneAvanti();

            this.StateHasChanged();
        }

        private void EditMode()
        {
            this.currentStep = Constants.DETAILS_VIEW;
            this._paginatoreStateService.NascondiPaginatore();
        }

        private void ClearDettaglio()
        {
            this.model = new Model();

            if (this.tipoCatastoDataItems.Count == 2)
            {
                this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == "F");
            }
        }

        private void DataBind()
        {
            this._deletingItem = null;

            this.ViewMode();

            this._gridDataSource = this.DomandaCorrente.Localizzazioni.Indirizzi.Where(x => (x.TipoLocalizzazione ?? "") == (this.TipoLocalizzazione ?? "")).ToList();

            if (this.DomandaCorrente.Localizzazioni.Indirizzi.Count() > 0)
                this._paginatoreStateService.MostraBottoneAvanti();
            else
                this._paginatoreStateService.NascondiBottoneAvanti();
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

            this.model.EditingItem = item;

            this.model.Indirizzo = new AutocompleteFormResult(item.CodiceStradario.ToString(), item.Indirizzo, new[] { item.CodiceViario });
            this.model.Civico = item.Civico;
            this.model.Esponente = item.Esponente;
            //model.Colore = 
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

                //model.TipoCatasto = tipoCatastoDataItems.Find(x => x.Value == rc.CodiceTipoCatasto);
                //model.Sezione = rc.Sezione;
                this.model.Foglio = rc.Foglio;
                this.model.Particella = rc.Particella;
                this.model.Sub = rc.Sub;
            }
        }

        private async Task OnRowDeleteAsync()
        {
            await this._spinnerService.ShowSpinnerAsync(() =>
            {
                this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda.Value, this._deletingItem.Id);

                this.model.EditingItem = null;

                this.DataBind();
            });
        }

        private void OnBtnConfirm()
        {
            if (!this.myForm.EditContext.Validate())
                return;


            //var erroriCompilazione = this._formLocalizzazioni.GetErroriValidazione();
            //var erroriEspressioniRegolari = this._formLocalizzazioni.GetErroriEspressioniRegolari();
            //var erroriValidazioneRange = this._formLocalizzazioni.GetErroriValidazioneRange();

            //var erroriValidazione = erroriCompilazione.Union(erroriEspressioniRegolari).Union(erroriValidazioneRange);

            //if (erroriValidazione.Count() > 0)
            //{
            //    this.Errori.Add(erroriValidazione);

            //    return;
            //}

            var stradarioTrovato = this.CodiceStradarioTrovato() ? this.TrovaStradarioDaCodiceStradario() : this.TrovaStradarioDaIndirizzo();

            this.InserisciVoceStradario(stradarioTrovato);
        }

        private void OnAnnullaClick()
        {
            this.ViewMode();
        }

        private StradarioEstesoDto TrovaStradarioDaCodiceStradario()
        {
            return this._stradarioRepository.GetByCodiceStradario(this.IdComune, Convert.ToInt32(this.model.Indirizzo.Value));
        }

        private StradarioEstesoDto TrovaStradarioDaIndirizzo()
        {
            return this._stradarioRepository.GetByIndirizzo(this.IdComune, this.CodiceComune, this.model.Indirizzo.Text);
        }

        private bool CodiceStradarioTrovato()
        {
            return !String.IsNullOrEmpty(this.model.Indirizzo.Value);
        }

        private void InserisciVoceStradario(StradarioEstesoDto stradarioTrovato)
        {
            if (stradarioTrovato != null)
            {
                var nomeVia = stradarioTrovato.Prefisso + " " + stradarioTrovato.Descrizione;

                if (!String.IsNullOrEmpty(stradarioTrovato.LocFraz))
                    nomeVia += " (" + stradarioTrovato.LocFraz + ")";

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
                    CodiceViario = stradarioTrovato.CodViario,
                    AccessoTipo = this.model.EditingItem?.AccessoTipo,
                    AccessoNumero = this.model.EditingItem?.AccessoNumero,
                    AccessoDescrizione = this.model.EditingItem?.AccessoDescrizione
                };

                var rifCatastali = this._formLocalizzazioni.GetRiferimentiCatastali(this.model.TipoCatasto?.Value, this.model.TipoCatasto?.Text, this.model.Foglio, this.model.Particella, this.model.Sub);

                if (!String.IsNullOrEmpty(this.model.EditingItem?.Id.ToString()))
                {
                    this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda.Value, this.model.EditingItem.Id);
                }

                this._localizzazioniService.AggiungiLocalizzazione(this.IdDomanda.Value, localizzazione, rifCatastali);

                this.DataBind();
            }
        }

        private async Task<IEnumerable<AutocompleteStradarioResultItem>> GetStradarioDataItemsAsync(string value)
        {
            var listaIndirizzi = await this._stradarioRepository.GetByMatchParzialeAsync(this.CodiceComune, "", value);
            var q = listaIndirizzi.Select(s => new AutocompleteStradarioResultItem
            {
                Codice = s.CodiceStradario,
                Descrizione = s.NomeVia,
                CodViario = s.CodViario
            });

            return q;
        }

        private async Task<IEnumerable<string>> GetCivicoDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Civico", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetEsponenteDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Esponente", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetScalaDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Scala", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetPianoDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Piano", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetInternoDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Interno", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetEsponenteInternoDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("EsponenteInterno", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetFabbricatoDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Fabbricato", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetKmDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Km", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetAccessoTipoDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("AccessoTipo", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetAccessoNumeroDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("AccessoNumero", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetAccessoDescrizioneDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("AccessoDescrizione", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetFoglioDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Foglio", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetParticellaDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Particella", value);

            return await Task.FromResult(listaCampi);
        }

        private async Task<IEnumerable<string>> GetSubDataItemsAsync(string value)
        {
            var listaCampi = this.GetListaCampi("Sub", value);

            return await Task.FromResult(listaCampi);
        }

        private string[] GetListaCampi(string nomeCampo, string value)
        {
            if (this.model.Indirizzo == null)
            {
                return null;
            }

            string civico = nomeCampo == "Civico" ?
                String.IsNullOrEmpty(value) ?
                    "" : value :
                String.IsNullOrEmpty(this.model.Civico) ?
                    "" : this.model.Civico;
            string esponente = nomeCampo == "Esponente" ?
                String.IsNullOrEmpty(value) ?
                    "" : value :
                String.IsNullOrEmpty(this.model.Esponente) ?
                    "" : this.model.Esponente;
            string tipoCatasto = this.model.TipoCatasto != null ? this.model.TipoCatasto.Value : "F";
            string sezione = nomeCampo == "Sezione" ?
                String.IsNullOrEmpty(value) ?
                    "" : value :
                String.IsNullOrEmpty(this.model.Sezione) ?
                    "" : this.model.Sezione;
            string foglio = nomeCampo == "Foglio" ?
                String.IsNullOrEmpty(value) ?
                    "" : value :
                String.IsNullOrEmpty(this.model.Foglio) ?
                    "" : this.model.Foglio;
            string particella = nomeCampo == "Particella" ?
                String.IsNullOrEmpty(value) ?
                    "" : value :
                String.IsNullOrEmpty(this.model.Particella) ?
                    "" : this.model.Particella;
            string sub = nomeCampo == "Sub" ?
                String.IsNullOrEmpty(value) ?
                    "" : value :
                String.IsNullOrEmpty(this.model.Sub) ?
                    "" : this.model.Sub;

            var parametriRicerca = new ParametriRicercaLocalizzazione
            {
                CodViario = this.model.Indirizzo.AdditionalValues.FirstOrDefault(),
                Civico = civico,
                Esponente = esponente,
                TipoCatasto = tipoCatasto,
                Sezione = sezione,
                Foglio = foglio,
                Particella = particella,
                Sub = sub,
                AccessoTipo = this.model.AccessoTipo,
                CodiceComune = this.CodiceComune,
                Fabbricato = this.model.Fabbricato
            };

            var listaCampi = this._sitService.RicercaValori(nomeCampo, parametriRicerca);

            if ((listaCampi?.Length ?? 0) == 0)
            {
                return Array.Empty<string>();
            }

            return listaCampi ?? Array.Empty<string>();
        }

        public void ValidaCampo(string nomeCampo)
        {
            if (this.model.Indirizzo == null)
            {
                this.myForm.AddValidationMessage(new FieldIdentifier(this.model, "Indirizzo"), "E' necessario selezionare una via");
                this.ClearData("ALL");
                return;
            }

            string tipoCatasto = this.model.TipoCatasto != null ? this.model.TipoCatasto.Value : "F";

            var parametriRicerca = new ParametriRicercaLocalizzazione
            {
                CodViario = this.model.Indirizzo.AdditionalValues.FirstOrDefault(),
                Civico = this.model.Civico,
                Esponente = this.model.Esponente,
                TipoCatasto = tipoCatasto,
                Sezione = this.model.Sezione,
                Foglio = this.model.Foglio,
                Particella = this.model.Particella,
                Sub = this.model.Sub,
                AccessoTipo = this.model.AccessoTipo,
                CodiceComune = this.CodiceComune,
                Fabbricato = this.model.Fabbricato
            };

            var r = this._sitService.ValidaCampo(nomeCampo, parametriRicerca);

            this.ClearData(nomeCampo);

            if (r == null)
            {
                this.myForm.AddValidationMessage(new FieldIdentifier(this.model, nomeCampo), "Il valore immesso non è stato trovato negli stradari comunali");
                return;
            }


            this.model.Civico = r.Civico;
            this.model.Esponente = r.Esponente;
            this.model.Foglio = r.Foglio;
            this.model.Particella = r.Particella;
            this.model.Sub = r.Sub;
            this.model.Sezione = r.Sezione;
            this.model.Fabbricato = r.Fabbricato;
            this.model.AccessoTipo = r.AccessoTipo;
            this.model.AccessoNumero = r.AccessoNumero;
            this.model.AccessoDescrizione = r.AccessoDescrizione;
            this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == r.TipoCatasto);
        }

        private void ClearData(string nomeCampo)
        {
            switch (nomeCampo)
            {
                case "ALL":
                    this.model.Civico = null;
                    this.model.Esponente = null;
                    this.model.Foglio = string.Empty;
                    this.model.Particella = string.Empty;
                    this.model.Sub = string.Empty;
                    this.model.Sezione = string.Empty;
                    this.model.Fabbricato = string.Empty;
                    this.model.AccessoTipo = string.Empty;
                    this.model.AccessoNumero = string.Empty;
                    this.model.AccessoDescrizione = string.Empty;
                    this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == "F");
                    break;
                case "Civico":
                    this.model.Esponente = null;
                    this.model.Foglio = string.Empty;
                    this.model.Particella = string.Empty;
                    this.model.Sub = string.Empty;
                    this.model.Sezione = string.Empty;
                    this.model.Fabbricato = string.Empty;
                    this.model.AccessoTipo = string.Empty;
                    this.model.AccessoNumero = string.Empty;
                    this.model.AccessoDescrizione = string.Empty;
                    this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == "F");
                    break;
                case "Esponente":
                    this.model.Foglio = string.Empty;
                    this.model.Particella = string.Empty;
                    this.model.Sub = string.Empty;
                    this.model.Sezione = string.Empty;
                    this.model.Fabbricato = string.Empty;
                    this.model.AccessoTipo = string.Empty;
                    this.model.AccessoNumero = string.Empty;
                    this.model.AccessoDescrizione = string.Empty;
                    this.model.TipoCatasto = this.tipoCatastoDataItems.Find(x => x.Value == "F");
                    break;
            }
        }

        private AutocompleteFormResult? IndirizzoConvertMethod(AutocompleteStradarioResultItem item)
        {
            return item == null ? null : new AutocompleteFormResult()
            {
                Value = item.Codice.ToString(),
                Text = item.Descrizione,
                AdditionalValues = new List<string>()
                {
                    item.CodViario
                }
            };
        }

        private void OnIniziaEliminazione(IndirizzoStradario indirizzo)
        {
            this._deletingItem = indirizzo;
        }

        private void OnAnnullaEliminazione()
        {
            this._deletingItem = null;
        }
    }
}