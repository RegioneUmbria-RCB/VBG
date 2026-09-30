using Init.Sigepro.FrontEnd.AppLogic.Exceptions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using Microsoft.AspNetCore.Components;
using System.ComponentModel.DataAnnotations;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;


namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche
{
    public partial class DettagliAnagraficaPg
    {
        [Inject]
        public IFormeGiuridicheRepository _formeGiuridicheRepository { get; set; } = default!;

        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; } = default!;

        [Inject]
        public IInpsInailService _inpsInailRepository { get; set; } = default!;


        [Parameter, Required]
        public string IdComune { get; set; }

        [Parameter, Required]
        public string Software { get; set; }

        [Parameter, Required]
        public int? CodiceIntervento { get; set; }

        [Parameter]
        public bool UtenteTester { get; set; } = false;

        [Parameter]
        public bool PermettiModificaTipoSoggetto { get; set; } = true;

        [Parameter]
        public bool PartitaIvaVisibile { get; set; } = true;

        [Parameter]
        public bool DataCostituzioneVisibile { get; set; } = true;

        [Parameter]
        public bool SedeLegaleVisibile { get; set; } = true;

        [Parameter]
        public bool CciaaVisibile { get; set; } = true;

        [Parameter]
        public bool RegTribVisibile { get; set; } = true;

        [Parameter]
        public bool ReaVisibile { get; set; } = true;

        [Parameter]
        public bool InpsVisibile { get; set; } = true;

        [Parameter]
        public bool InailVisibile { get; set; } = true;

        [Parameter]
        public bool TelefonoVisible { get; set; } = true;

        [Parameter]
        public bool CellulareVisible { get; set; } = true;

        [Parameter]
        public bool FaxVisible { get; set; } = true;

        [Parameter]
        public bool EmailVisible { get; set; } = true;

        [Parameter]
        public bool PecVisible { get; set; } = true;

        [Parameter]
        public bool CorrispondenzaVisibile { get; set; } = true;

        [Parameter]
        public bool PartitaIvaObbligatoria { get; set; } = false;

        [Parameter]
        public bool DataCostituzioneObbligatoria { get; set; } = false;

        [Parameter]
        public bool SedeLegaleObbligatoria { get; set; } = false;

        [Parameter]
        public bool CciaaObbligatoria { get; set; } = false;

        [Parameter]
        public bool RegTribObbligatorio { get; set; } = false;

        [Parameter]
        public bool ReaObbligatoria { get; set; } = false;

        [Parameter]
        public bool InpsObbligatoria { get; set; } = false;

        [Parameter]
        public bool InailObbligatoria { get; set; } = false;

        [Parameter]
        public bool TelefonoObbligatorio { get; set; } = false;

        [Parameter]
        public bool CellulareObbligatorio { get; set; } = false;

        [Parameter]
        public bool FaxObbligatorio { get; set; } = false;

        [Parameter]
        public bool EmailObbligatoria { get; set; } = false;

        [Parameter]
        public bool PecObbligatoria { get; set; } = false;

        [Parameter]
        public bool TelefonoOCellulareObbligatori { get; set; } = false;

        [Parameter]
        public bool EmailOPecObbligatori { get; set; } = false;

        [Parameter]
        public bool CorrispondenzaObbligatoria { get; set; } = false;

        [Parameter]
        public bool PermettiModificaDatiAnagrafici { get; set; } = true;

        [Parameter, Required]
        public Func<int, AnagraficaDomanda> FetchAnagrafeData { get; set; }

        [Parameter, Required]
        public Func<string, IEnumerable<DatiComuneCompatto>> FetchComuni { get; set; }

        [Parameter, Required]
        public Func<string, IEnumerable<DatiProvinciaCompatto>> FetchProvincie { get; set; }

        [Parameter, Required]
        public Func<string, DatiComuneCompatto> FetchComuneData { get; set; }

        [Parameter, Required]
        public Func<string, DatiProvinciaCompatto> FetchProvinciaData { get; set; }

        [Parameter]
        public EventCallback OnBeforeAcceptEdit { get; set; }

        [Parameter, Required]
        public EventCallback<AnagraficaDomanda> OnAcceptEdit { get; set; }

        [Parameter, Required]
        public EventCallback OnCancelEdit { get; set; }

        [Parameter, Required]
        public EventCallback<string> OnError { get; set; }

        [Parameter, Required]
        public EventCallback OnClose { get; set; }

        [Parameter]
        public PresentazioneIstanzaDbV2.ANAGRAFERow InitialModel { get; set; }

        [Parameter, Required]
        public IEnumerable<TipoSoggetto> TipiSoggettoPg { get; set; }

        [Parameter]
        public Dictionary<int, int> NumOccorrenzeTipoSoggetto { get; set; } = new();

        [Parameter]
        public int? CurrentTipoSoggetto { get; set; } = null;

        private bool showDescrizioneEstesa = false;
        private bool maxOccorrenzeRaggiunte = false;
        private string privateDescrizioneEstesa = string.Empty;
        private readonly Model model = new();

        public class Model
        {
            //[Required(ErrorMessage = FormMessageStore.Messages.SELEZIONA_ELEMENTO)]
            public DropDownItem? TipoSoggetto { get; set; } // { Codice = -1, Descrizione = "" };

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            [MaxLength(128)]
            public string? DescrizioneEstesa { get; set; } = "vuoto";

            //[Required(ErrorMessage = FormMessageStore.Messages.SELEZIONA_ELEMENTO)]
            public DropDownItem? FormaGiuridica { get; set; }

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            [MaxLength(180)]
            public string? RagioneSociale { get; set; }

            [MaxLength(16)]
            public string? CCIAANumero { get; set; }

            public DateTime? CCIAAData { get; set; }

            public AutocompleteFormResult? CCIAAComune { get; set; }

            [MaxLength(16)]
            public string? RegTribNumero { get; set; }

            public DateTime? RegTribData { get; set; }

            public AutocompleteFormResult? RegTribComune { get; set; }

            [MaxLength(16)]
            public string? REANumero { get; set; }

            public DateTime? READata { get; set; }

            public AutocompleteFormResult? REAComune { get; set; }

            [MaxLength(16)]
            public string? INPSNumero { get; set; }

            public AutocompleteFormResult? INPSComune { get; set; }

            [MaxLength(16)]
            public string? INAILNumero { get; set; }

            public AutocompleteFormResult? INAILComune { get; set; }

            public AutocompleteFormResult? ComuneSedeLegale { get; set; }

            //[Required(ErrorMessage = "Specificare un valore")]
            [MaxLength(32)]
            public string? CodiceFiscale { get; set; }

            [MaxLength(32)]
            public string? PartitaIva { get; set; }

            public DateTime? DataNominativo { get; set; }

            [MaxLength(100)]
            public string? Indirizzo { get; set; }

            [MaxLength(100)]
            public string? Citta { get; set; }

            [MaxLength(100)]
            public string? CAP { get; set; }

            [MaxLength(15)]
            public string? Telefono { get; set; }

            [MaxLength(15)]
            public string? Cellulare { get; set; }

            [MaxLength(15)]
            public string? Fax { get; set; }

            [MaxLength(70)]
            public string? EMail { get; set; }

            [MaxLength(70)]
            public string? EMailPEC { get; set; }

            public AutocompleteFormResult? ComuneCorrispondenza { get; set; }

            [MaxLength(100)]
            public string? IndirizzoCorrispondenza { get; set; }

            [MaxLength(50)]
            public string? CittaCorrispondenza { get; set; }

            [MaxLength(5)]
            public string? CAPCorrispondenza { get; set; }

            public string? Anagrafe_PK { get; set; }
        }

        private List<DropDownItem> SoggettiDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> FormeGiuridicheDataItems { get; set; } = new List<DropDownItem>();

        protected override async Task OnInitializedAsync()
        {
            this.BindTipiSoggetto();
            this.BindFormeGiuridiche();
            //BindTitoli();
            //BindSesso();
            //BindCittadinanze();
            //BindElenchiProfessionali();

            await this.DataBindAsync();

            await base.OnInitializedAsync();
        }

        private async Task DataBindAsync()
        {
            if (this.InitialModel != null)
            {
                //var selectedCitta = TitoliDataItems.Find(x => x.Value == InitialModel.TITOLO);
                //var selectedSesso = SessoDataItems.Find(x => x.Value == InitialModel.SESSO);
                //var selectedCittadinanza = CittadinanzaDataItems.Find(x => x.Value == InitialModel.CODICECITTADINANZA.ToString());

                this.model.RagioneSociale = this.InitialModel.NOMINATIVO;
                this.model.Indirizzo = this.InitialModel.INDIRIZZO;
                this.model.Citta = this.InitialModel.CITTA;
                this.model.CAP = this.InitialModel.CAP;
                this.model.Fax = this.InitialModel.FAX;
                this.model.EMail = this.InitialModel.EMAIL;
                this.model.EMailPEC = this.InitialModel.Pec;
                this.model.CodiceFiscale = this.InitialModel.CODICEFISCALE;
                this.model.PartitaIva = this.InitialModel.PartitaIva;
                this.model.CCIAANumero = this.InitialModel.REGDITTE;
                this.model.RegTribNumero = this.InitialModel.REGTRIB;
                this.model.REANumero = this.InitialModel.NUMISCRREA;
                this.model.IndirizzoCorrispondenza = this.InitialModel.INDIRIZZOCORRISPONDENZA;
                this.model.CittaCorrispondenza = this.InitialModel.CITTACORRISPONDENZA;
                this.model.CAPCorrispondenza = this.InitialModel.CAPCORRISPONDENZA;
                this.model.Telefono = this.InitialModel.TELEFONO;
                this.model.Cellulare = this.InitialModel.TELEFONOCELLULARE;

                // Residenza
                string comuneSedeLegale = this.InitialModel.COMUNERESIDENZA;

                if (!String.IsNullOrEmpty(comuneSedeLegale))
                {
                    var comuneSLDto = await this.GetComuneDataItemsAsync(comuneSedeLegale);

                    if (comuneSLDto != null)
                    {
                        this.model.ComuneSedeLegale = new AutocompleteFormResult(comuneSLDto.CodiceComune, comuneSLDto.Comune + " (" + comuneSLDto.SiglaProvincia + ")");
                    }
                }
                else
                {
                    this.model.ComuneSedeLegale = new AutocompleteFormResult("", "");
                }


                // Corrispondenza
                string comuneCorrispondenza = this.InitialModel.COMUNECORRISPONDENZA;

                if (!String.IsNullOrEmpty(comuneCorrispondenza))
                {
                    var comuneCorrispondenzaDto = await this.GetComuneDataItemsAsync(comuneCorrispondenza);

                    if (comuneCorrispondenzaDto != null)
                    {
                        this.model.ComuneCorrispondenza = new AutocompleteFormResult(comuneCorrispondenza, comuneCorrispondenzaDto.Comune + " (" + comuneCorrispondenzaDto.SiglaProvincia + ")");
                    }
                }
                else
                {
                    this.model.ComuneCorrispondenza = new AutocompleteFormResult("", "");
                }

                this.model.DataNominativo = this.InitialModel.IsDATANOMINATIVONull() ? null : this.InitialModel.DATANOMINATIVO;
                this.model.CCIAAData = this.InitialModel.IsDATAREGDITTENull() ? null : this.InitialModel.DATAREGDITTE;
                this.model.RegTribData = this.InitialModel.IsDATAREGTRIBNull() ? null : this.InitialModel.DATAREGTRIB;
                this.model.READata = this.InitialModel.IsDATAISCRREANull() ? null : this.InitialModel.DATAISCRREA;


                // CCIAA
                var comuneRegDitte = this.InitialModel.CODCOMREGDITTE;

                if (!String.IsNullOrEmpty(comuneRegDitte))
                {
                    var comuneCciaaDto = await this.GetComuneDataItemsAsync(comuneRegDitte);

                    if (comuneCciaaDto != null)
                    {
                        this.model.CCIAAComune = new AutocompleteFormResult(comuneCciaaDto.CodiceComune, comuneCciaaDto.Comune + " (" + comuneCciaaDto.SiglaProvincia + ")");
                    }
                }
                else
                {
                    this.model.CCIAAComune = new AutocompleteFormResult("", "");
                }


                string comuneRegTrib = this.InitialModel.CODCOMREGTRIB;

                if (!String.IsNullOrEmpty(comuneRegTrib))
                {
                    var comuneRegTribDto = await this.GetComuneDataItemsAsync(comuneRegTrib);

                    if (comuneRegTribDto != null)
                    {
                        this.model.RegTribComune = new AutocompleteFormResult(comuneRegTribDto.CodiceComune, comuneRegTribDto.Comune + " (" + comuneRegTribDto.SiglaProvincia + ")");
                    }
                }
                else
                {
                    this.model.RegTribComune = new AutocompleteFormResult("", "");
                }


                string provinciaRea = this.InitialModel.PROVINCIAREA;

                if (!String.IsNullOrEmpty(provinciaRea))
                {
                    var provinciaReaDto = await this.GetProvinciaDataItemsAsync(provinciaRea);

                    if (provinciaReaDto != null)
                    {
                        this.model.REAComune = new AutocompleteFormResult(provinciaReaDto.SiglaProvincia, provinciaReaDto.Provincia);
                    }
                }
                else
                {
                    this.model.REAComune = new AutocompleteFormResult("", "");
                }


                var selectedSoggetto = this.SoggettiDataItems.Find(x => x.Value == this.InitialModel.TIPOSOGGETTO.ToString());
                this.model.TipoSoggetto = selectedSoggetto == null ? new DropDownItem("", "") { } : selectedSoggetto;

                this.VerificaTipoSoggetto(this.model.TipoSoggetto.Value);

                this.privateDescrizioneEstesa = this.InitialModel.DescrizioneTipoSoggetto;

                DropDownItem? selectedFormaGiuridica = null;

                if (this.InitialModel.IsFORMAGIURIDICANull())
                {
                    selectedFormaGiuridica = new DropDownItem("", "") { };
                }
                else
                {
                    selectedFormaGiuridica = this.FormeGiuridicheDataItems.Find(x => x.Value == this.InitialModel.FORMAGIURIDICA.ToString());
                }

                this.model.FormaGiuridica = selectedFormaGiuridica == null ? new DropDownItem("", "") { } : selectedFormaGiuridica;

                this.model.Anagrafe_PK = this.InitialModel.ANAGRAFE_PK.ToString();


                // Dati INPS
                this.model.INPSNumero = this.InitialModel.MatricolaInps;

                if (!string.IsNullOrEmpty(this.InitialModel.CodSedeIscrizioneInps))
                {
                    var sede = this._inpsInailRepository.GetSedeInpsByCodice(this.InitialModel.CodSedeIscrizioneInps);
                    this.model.INPSComune = new AutocompleteFormResult(sede.Codice, sede.Descrizione);
                }

                // Dati INAIL
                this.model.INAILNumero = this.InitialModel.MatricolaInail;

                if (!string.IsNullOrEmpty(this.InitialModel.CodSedeIscrizioneInail))
                {
                    var sede = this._inpsInailRepository.GetSedeInailByCodice(this.InitialModel.CodSedeIscrizioneInail);
                    this.model.INAILComune = new AutocompleteFormResult(sede.Codice, sede.Descrizione);
                }
            }
        }

        private void BindTipiSoggetto()
        {
            if (this.SoggettiDataItems.Count > 0)
                return;

            if (TipiSoggettoPg is null)
            {
                var list = this._tipiSoggettoRepository.GetTipiSoggettoPersonaGiurudica(this.CodiceIntervento).ToList();

                foreach (var item in list)
                {
                    this.SoggettiDataItems.Add(new DropDownItem(item.Id.ToString(), item.Descrizione) { });
                }
            }
            else
            {
                foreach (var item in this.TipiSoggettoPg)
                {
                    this.SoggettiDataItems.Add(new DropDownItem(item.Id.ToString(), item.Descrizione) { });
                }
            }
        }

        private void BindFormeGiuridiche()
        {
            if (this.FormeGiuridicheDataItems.Count > 0)
                return;

            var formeGiuridiche = this._formeGiuridicheRepository.GetList(this.IdComune);

            foreach (var item in formeGiuridiche)
            {
                this.FormeGiuridicheDataItems.Add(new DropDownItem(item.CodiceFormaGiuridica, item.FormaGiuridica) { });
            }
        }

        private Task<AnagraficaDomanda> GetAnagrafeRowDataItemsAsync(int value)
        {
            return Task.FromResult(this.FetchAnagrafeData.Invoke(value));
        }

        private Task<IEnumerable<DatiComuneCompatto>> GetComuniAsync(string value)
        {
            return Task.FromResult(this.FetchComuni.Invoke(value));
        }

        private Task<DatiComuneCompatto> GetComuneDataItemsAsync(string value)
        {
            return Task.FromResult(this.FetchComuneData.Invoke(value));
        }

        private AutocompleteFormResult? ComuneConvertMethod(DatiComuneCompatto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.CodiceComune, $"{dati.Comune} ({dati.SiglaProvincia})");
        }

        private Task<IEnumerable<DatiProvinciaCompatto>> GetProvincieAsync(string value)
        {
            return Task.FromResult(this.FetchProvincie.Invoke(value));
        }

        private Task<DatiProvinciaCompatto> GetProvinciaDataItemsAsync(string value)
        {
            return Task.FromResult(this.FetchProvinciaData.Invoke(value));
        }

        private AutocompleteFormResult? ProvinciaConvertMethod(DatiProvinciaCompatto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.SiglaProvincia, dati.Provincia);
        }

        private Task<IEnumerable<SedeInpsDto>> GetSediInpsAsync(string match)
        {
            return Task.FromResult(this._inpsInailRepository.GetSediInps(match));
        }

        private Task<IEnumerable<SedeInailDto>> GetSediInailAsync(string match)
        {
            return Task.FromResult(this._inpsInailRepository.GetSediInail(match));
        }

        private AutocompleteFormResult? InailConvertMethod(SedeInailDto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.Codice, dati.Descrizione);
        }

        private AutocompleteFormResult? InpsConvertMethod(SedeInpsDto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.Codice, dati.Descrizione);
        }

        private void VerificaTipoSoggetto(string codiceTipoSoggetto)
        {
            if (string.IsNullOrEmpty(codiceTipoSoggetto))
            {
                this.showDescrizioneEstesa =
                this.maxOccorrenzeRaggiunte = false;

                return;
            }

            int code = int.Parse(codiceTipoSoggetto);
            TipoSoggetto tipoSoggetto = null;

            if (TipiSoggettoPg is null)
            {
                tipoSoggetto = this._tipiSoggettoRepository.GetById(code);
            }
            else
            {
                tipoSoggetto = this.TipiSoggettoPg.FirstOrDefault(x => x.Id == code);
            }

            this.showDescrizioneEstesa = tipoSoggetto.RichiedeSpecificaDescrizione;

            if (!this.showDescrizioneEstesa)
                this.model.DescrizioneEstesa = string.Empty;

            //verifica che non sia raggiunto il limite dei tipi soggetto disponibili
            this.maxOccorrenzeRaggiunte = NumOccorrenzeTipoSoggetto.GetValueOrDefault(tipoSoggetto.Id) >= tipoSoggetto.OccorrenzeMax;

            //se il limite è stato raggiunto ma sono in editing devo permettere la modifica
            if (this.maxOccorrenzeRaggiunte && this.CurrentTipoSoggetto == tipoSoggetto.Id)
            {
                this.maxOccorrenzeRaggiunte = false;
            }
        }

        private async Task OnValidSubmitAsync()
        {
            if (this.OnBeforeAcceptEdit.HasDelegate)
                await this.OnBeforeAcceptEdit.InvokeAsync();

            int idAnagrafica = -1;

            int.TryParse(this.model.Anagrafe_PK, out idAnagrafica);

            if (!await this.ControllaCampoAsync(this.model.CodiceFiscale, true, "E'obbligatorio specificare un codice fiscale oppure una partita IVA"))
                return;

            var anagrafica = await this.GetAnagrafeRowDataItemsAsync(idAnagrafica);
            var row = anagrafica.ToAnagrafeRow();

            row.TIPOANAGRAFE = "G";

            if (this.model.TipoSoggetto == null || string.IsNullOrEmpty(this.model.TipoSoggetto.Value))
            {
                row.TIPOSOGGETTO = -1;
                row.DescrSoggetto = String.Empty;
            }
            else
            {
                row.TIPOSOGGETTO = Convert.ToInt32(this.model.TipoSoggetto.Value);
                row.DescrSoggetto = this.model.TipoSoggetto.Text;
            }

            if (this.showDescrizioneEstesa)
            {
                this.model.DescrizioneEstesa = this.privateDescrizioneEstesa;
            }
            else if (!string.IsNullOrEmpty(this.model.DescrizioneEstesa))
            {
                this.model.DescrizioneEstesa = string.Empty;
            }


            row.DescrizioneTipoSoggetto = this.model.DescrizioneEstesa;

            row.NOMINATIVO = this.model.RagioneSociale;
            row.CODICEFISCALE = this.model.CodiceFiscale.ToUpper();
            row.PartitaIva = this.model.PartitaIva;

            if (!String.IsNullOrEmpty(this.model.FormaGiuridica?.Value))
                row.FORMAGIURIDICA = Convert.ToDecimal(this.model.FormaGiuridica.Value);

            row.INDIRIZZO = this.model.Indirizzo;
            row.CITTA = this.model.Citta;
            row.CAP = this.model.CAP;
            row.INDIRIZZOCORRISPONDENZA = this.model.IndirizzoCorrispondenza;
            row.CITTACORRISPONDENZA = this.model.CittaCorrispondenza;
            row.CAPCORRISPONDENZA = this.model.CAPCorrispondenza;
            row.TELEFONO = this.model.Telefono;
            row.TELEFONOCELLULARE = this.model.Cellulare;
            row.FAX = this.model.Fax;
            row.EMAIL = this.model.EMail;
            row.Pec = this.model.EMailPEC;

            row.REGDITTE = this.model.CCIAANumero;
            row.REGTRIB = this.model.RegTribNumero;
            row.NUMISCRREA = this.model.REANumero;
            row.PROVINCIAREA = this.model.REAComune.Value;
            row.DATANOMINATIVO = this.model.DataNominativo == null ? DateTime.MinValue : this.model.DataNominativo.Value;
            row.DATAREGDITTE = this.model.CCIAAData == null ? DateTime.MinValue : this.model.CCIAAData.Value;
            row.DATAREGTRIB = this.model.RegTribData == null ? DateTime.MinValue : this.model.RegTribData.Value;
            row.DATAISCRREA = this.model.READata == null ? DateTime.MinValue : this.model.READata.Value;

            bool checkedControl = true;

            checkedControl = await this.ControllaCampoAsync(this.model.PartitaIva, this.PartitaIvaObbligatoria, "Specificare una partita IVA");
            checkedControl = await this.ControllaCampoAsync(this.model.DataNominativo, this.DataCostituzioneObbligatoria, "Specificare la data di costituzione") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.ComuneSedeLegale?.Value, this.SedeLegaleObbligatoria, "Specificare il comune della sede legale") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.Indirizzo, this.SedeLegaleObbligatoria, "Specificare l'indirizzo della sede legale") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.CAP, this.SedeLegaleObbligatoria, "Specificare il CAP") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.CCIAANumero, this.CciaaObbligatoria, "Specificare il numero di iscrizione alla camera di commercio") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.CCIAAData, this.CciaaObbligatoria, "Specificare la data di iscrizione alla camera di commercio") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.CCIAAComune?.Value, this.CciaaObbligatoria, "Specificare la provincia di iscrizione alla camera di commercio") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.RegTribNumero, this.RegTribObbligatorio, "Specificare il numero di iscrizione al Reg.Trib.") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.RegTribData, this.RegTribObbligatorio, "Specificare la data di iscrizione al Reg.Trib.") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.RegTribComune?.Value, this.RegTribObbligatorio, "Specificare il comune di iscrizione al Reg.Trib.") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.REANumero, this.ReaObbligatoria, "Specificare il numero di iscrizione al REA") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.READata, this.ReaObbligatoria, "Specificare la data di iscrizione al REA") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.REAComune?.Value, this.ReaObbligatoria, "Specificare la provincia di iscrizione al REA") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.INPSNumero, this.InpsObbligatoria, "Specificare la matricola INPS") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.INPSComune?.Value, this.InpsObbligatoria, "Specificare la sede di iscrizione INPS") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.INAILNumero, this.InailObbligatoria, "Specificare la matricola INAIL") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.INAILComune?.Value, this.InailObbligatoria, "Specificare la sede di iscrizione INAIL") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.Telefono, this.TelefonoObbligatorio, "Specificare il numero di telefono") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.Cellulare, this.CellulareObbligatorio, "Specificare il numero di cellulare") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.Fax, this.FaxObbligatorio, "Specificare il numero di Fax") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.EMail, this.EmailObbligatoria, "Specificare un indirizzo e-mail") && checkedControl;
            checkedControl = await this.ControllaCampoAsync(this.model.EMailPEC, this.PecObbligatoria, "Specificare un indirizzo PEC") && checkedControl;

            if (!checkedControl)
                return;

            if (this.EmailOPecObbligatori && (String.IsNullOrEmpty(this.model.EMailPEC) && String.IsNullOrEmpty(this.model.EMail)))
            {
                await this.OnError.InvokeAsync("Specificare l'indirizzo E-Mail o PEC");
                return;
            }

            if (this.TelefonoOCellulareObbligatori && (String.IsNullOrEmpty(this.model.Telefono) && String.IsNullOrEmpty(this.model.Cellulare)))
            {
                await this.OnError.InvokeAsync("Specificare il numero di telefono o cellulare");
                return;
            }


            // Validazione comuni e provincie
            row.CODPROVREGDITTE = row.CODCOMREGDITTE = String.Empty;

            await this.ValidaComuneAsync(this.model.CCIAAComune?.Value, "La provincia CCIAA non è valida", c =>
            {
                row.CODPROVREGDITTE = c.SiglaProvincia;
                row.CODCOMREGDITTE = c.CodiceComune;
            });


            row.CODPROVREGTRIB = row.CODCOMREGTRIB = String.Empty;

            await this.ValidaComuneAsync(this.model.RegTribComune?.Value, "Il comune Reg. Trib. non è valido", c =>
            {
                row.CODPROVREGTRIB = c.SiglaProvincia;
                row.CODCOMREGTRIB = c.CodiceComune;
            });


            row.PROVINCIA = row.COMUNERESIDENZA = String.Empty;

            await this.ValidaComuneAsync(this.model.ComuneSedeLegale?.Value, "Il comune della sede legale non è valido", c =>
            {
                row.PROVINCIA = c.SiglaProvincia;
                row.COMUNERESIDENZA = c.CodiceComune;
            });


            row.PROVINCIACORRISPONDENZA = row.COMUNECORRISPONDENZA;

            await this.ValidaComuneAsync(this.model.ComuneCorrispondenza?.Value, "Il comune per la corrispondenza non è valido", c =>
            {
                row.PROVINCIACORRISPONDENZA = c.SiglaProvincia;
                row.COMUNECORRISPONDENZA = c.CodiceComune;
            });


            // Dati INPS
            row.MatricolaInps = this.model.INPSNumero;
            row.CodSedeIscrizioneInps = this.model.INPSComune?.Value;
            row.DesSedeIscrizioneInps = this.model.INPSComune?.Text;


            // Dati INAIL
            row.MatricolaInail = this.model.INAILNumero;
            row.CodSedeIscrizioneInail = this.model.INAILComune?.Value;
            row.DesSedeIscrizioneInail = this.model.INAILComune?.Text;

            UpdateNumOccorrenzeTipoSoggetto(row.TIPOSOGGETTO, true);

            await this.OnAcceptEdit.InvokeAsync(AnagraficaDomanda.FromAnagrafeRow(row));
        }

        private void UpdateNumOccorrenzeTipoSoggetto(int? tipoSoggetto, bool add)
        {
            if (tipoSoggetto is null)
                return;

            // se la chiave non esiste, inizializza a 0
            if (!NumOccorrenzeTipoSoggetto.TryGetValue(tipoSoggetto.Value, out int count))
                count = 0;

            count += add ? 1 : -1;

            // se il valore scende a 0 o sotto rimuove la chiave altrimenti aggiorna il valore
            if (count <= 0)
                NumOccorrenzeTipoSoggetto.Remove(tipoSoggetto.Value);
            else
                NumOccorrenzeTipoSoggetto[tipoSoggetto.Value] = count;
        }

        private async Task<bool> ControllaCampoAsync(object? valore, bool obbligatorio, string errore)
        {
            if (!obbligatorio)
                return true;

            if (valore == null)
            {
                await this.OnError.InvokeAsync(errore);
                //throw new FormValidationException(errore);
                return false;
            }

            switch (Type.GetTypeCode(valore.GetType()))
            {
                case TypeCode.String:
                    if (valore.ToString() == "")
                    {
                        await this.OnError.InvokeAsync(errore);
                        //throw new FormValidationException(errore);
                        return false;
                    }

                    break;
                case TypeCode.DateTime:

                    break;
                case TypeCode.Boolean:

                    break;
                case TypeCode.Decimal:

                    break;
                case TypeCode.Int32:

                    break;
                default:

                    break;
            }

            return true;
        }

        private async Task ValidaComuneAsync(string? comune, string erroreComuneNonTrovato, Action<DatiComuneCompatto> comuneTrovatoCallback)
        {
            if (String.IsNullOrEmpty(comune))
            {
                return;
            }

            var data = await this.GetComuneDataItemsAsync(comune);

            if (data == null)
            {
                throw new FormValidationException(erroreComuneNonTrovato);
            }

            comuneTrovatoCallback(data);
        }

        //private async Task OnBtnConfirm()
        //{

        //}

        //private async Task OnBtnAnnullaAsync()
        //{
        //    if (this.OnCancelEdit.HasDelegate)
        //        await this.OnCancelEdit.InvokeAsync();
        //}

        private void OnCopiaResidenza()
        {
            this.model.ComuneCorrispondenza = this.model.ComuneSedeLegale;
            this.model.IndirizzoCorrispondenza = this.model.Indirizzo;
            this.model.CittaCorrispondenza = this.model.Citta;
            this.model.CAPCorrispondenza = this.model.CAP;
        }

        //private void OnValidateDynamicFields(FormMessageStore store)
        //{
        //    if (this.PartitaIvaObbligatoria && String.IsNullOrEmpty(this.model.PartitaIva))
        //        store.Add(() => this.model.PartitaIva, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.DataCostituzioneObbligatoria && this.model.DataNominativo == null)
        //        store.Add(() => this.model.DataNominativo, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.SedeLegaleObbligatoria)
        //    {
        //        if (this.model.ComuneSedeLegale == null || String.IsNullOrEmpty(this.model.ComuneSedeLegale.Value))
        //            store.Add(() => this.model.ComuneSedeLegale, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (String.IsNullOrEmpty(this.model.Indirizzo))
        //            store.Add(() => this.model.Indirizzo, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (String.IsNullOrEmpty(this.model.Citta))
        //            store.Add(() => this.model.Citta, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (String.IsNullOrEmpty(this.model.CAP))
        //            store.Add(() => this.model.CAP, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }


        //    if (this.CciaaObbligatoria)
        //    {
        //        if (String.IsNullOrEmpty(this.model.CCIAANumero))
        //            store.Add(() => this.model.CCIAANumero, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.CCIAAData == null)
        //            store.Add(() => this.model.CCIAAData, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.CCIAAComune == null || String.IsNullOrEmpty(this.model.CCIAAComune.Value))
        //            store.Add(() => this.model.CCIAAComune, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }

        //    if (this.RegTribObbligatorio)
        //    {
        //        if (String.IsNullOrEmpty(this.model.RegTribNumero))
        //            store.Add(() => this.model.RegTribNumero, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.RegTribData == null)
        //            store.Add(() => this.model.RegTribData, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.RegTribComune == null || String.IsNullOrEmpty(this.model.CCIAAComune.Value))
        //            store.Add(() => this.model.RegTribComune, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }

        //    if (this.ReaObbligatoria)
        //    {
        //        if (String.IsNullOrEmpty(this.model.REANumero))
        //            store.Add(() => this.model.REANumero, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.READata == null)
        //            store.Add(() => this.model.READata, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.REAComune == null || String.IsNullOrEmpty(this.model.CCIAAComune.Value))
        //            store.Add(() => this.model.REAComune, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }

        //    if (this.InpsObbligatoria)
        //    {
        //        if (String.IsNullOrEmpty(this.model.INPSNumero))
        //            store.Add(() => this.model.INPSNumero, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.INPSComune == null || String.IsNullOrEmpty(this.model.CCIAAComune.Value))
        //            store.Add(() => this.model.INPSComune, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }

        //    if (this.InailObbligatoria)
        //    {
        //        if (String.IsNullOrEmpty(this.model.INAILNumero))
        //            store.Add(() => this.model.INAILNumero, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (this.model.INAILComune == null || String.IsNullOrEmpty(this.model.CCIAAComune.Value))
        //            store.Add(() => this.model.INAILComune, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }

        //    if (this.TelefonoObbligatorio && String.IsNullOrEmpty(this.model.Telefono))
        //        store.Add(() => this.model.Telefono, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.CellulareObbligatorio && String.IsNullOrEmpty(this.model.Cellulare))
        //        store.Add(() => this.model.Cellulare, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.FaxObbligatorio && String.IsNullOrEmpty(this.model.Fax))
        //        store.Add(() => this.model.Fax, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.EmailObbligatoria && String.IsNullOrEmpty(this.model.EMail))
        //        store.Add(() => this.model.EMail, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.PecObbligatoria && String.IsNullOrEmpty(this.model.EMailPEC))
        //        store.Add(() => this.model.EMailPEC, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //    if (this.CorrispondenzaObbligatoria)
        //    {
        //        if (this.model.ComuneCorrispondenza == null || string.IsNullOrEmpty(this.model.ComuneCorrispondenza.Value))
        //            store.Add(() => this.model.ComuneCorrispondenza, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (String.IsNullOrEmpty(this.model.IndirizzoCorrispondenza))
        //            store.Add(() => this.model.IndirizzoCorrispondenza, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (String.IsNullOrEmpty(this.model.CittaCorrispondenza))
        //            store.Add(() => this.model.CittaCorrispondenza, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);

        //        if (String.IsNullOrEmpty(this.model.CAPCorrispondenza))
        //            store.Add(() => this.model.CAPCorrispondenza, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
        //    }
        //}
    }
}