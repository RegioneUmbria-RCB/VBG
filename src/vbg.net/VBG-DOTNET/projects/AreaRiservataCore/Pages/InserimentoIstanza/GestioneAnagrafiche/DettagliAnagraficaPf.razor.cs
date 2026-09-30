using DocumentFormat.OpenXml.Spreadsheet;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.Utils.CalcoloCF;
using Microsoft.AspNetCore.Components;
using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;
using static AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche.GestioneAnagrafiche;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche
{
    public partial class DettagliAnagraficaPf
    {

        [Inject]
        public ICittadinanzeService _cittadinanzeService { get; set; } = default!;

        [Inject]
        public ITitoliRepository _titoliRepository { get; set; } = default!;

        [Inject]
        public IElenchiProfessionaliRepository _elenchiProfessionaliRepository { get; set; } = default!;

        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; } = default!;

        [Parameter, Required]
        public string IdComune { get; set; }

        [Parameter, Required]
        public string Software { get; set; }

        [Parameter, Required]
        public int? CodiceIntervento { get; set; }

        [Parameter]
        public bool TitoloVisible { get; set; } = true;

        [Parameter]
        public bool TitoloObbligatorio { get; set; } = false;

        [Parameter]
        public bool CittadinanzaVisible { get; set; } = true;

        [Parameter]
        public bool CittadinanzaObbligatoria { get; set; } = false;

        [Parameter]
        public bool ResidenzaVisible { get; set; } = true;

        [Parameter]
        public bool ResidenzaObbligatoria { get; set; } = false;

        [Parameter]
        public bool CorrispondenzaVisibile { get; set; } = true;

        [Parameter]
        public bool CorrispondenzaObbligatoria { get; set; } = false;

        [Parameter]
        public bool TelefonoVisible { get; set; } = true;

        [Parameter]
        public bool TelefonoObbligatorio { get; set; } = false;

        [Parameter]
        public bool CellulareVisible { get; set; } = true;

        [Parameter]
        public bool CellulareObbligatorio { get; set; } = false;

        [Parameter]
        public bool TelefonoOCellulareObbligatori { get; set; } = false;

        [Parameter]
        public bool EmailOPecObbligatori { get; set; } = false;

        [Parameter]
        public bool EmailVisible { get; set; } = true;

        [Parameter]
        public bool EmailObbligatoria { get; set; } = false;

        [Parameter]
        public bool PecVisible { get; set; } = true;

        [Parameter]
        public bool PecObbligatoria { get; set; } = false;

        [Parameter]
        public bool GestioneSoggettoUnico { get; set; } = true;

        [Parameter, Required]
        public Func<string, IEnumerable<DatiComuneCompatto>> FetchComuni { get; set; } = (s) => Enumerable.Empty<DatiComuneCompatto>();

        [Parameter, Required]
        public Func<string, IEnumerable<DatiProvinciaCompatto>> FetchProvincie { get; set; } = (s) => Enumerable.Empty<DatiProvinciaCompatto>();

        [Parameter, Required]
        public Func<string, DatiComuneCompatto?> FetchComuneData { get; set; } = (s) => null;

        [Parameter, Required]
        public Func<string, DatiProvinciaCompatto> FetchProvinciaData { get; set; } = (s) => null;

        [Parameter, Required]
        public Func<int, AnagraficaDomanda> FetchAnagrafeData { get; set; } = (s) => null;

        [Parameter, Required]
        public Func<string, CittadinanzaCompatto?> FetchCittadinanzaData { get; set; } = (s) => null;

        [Parameter, Required]
        public Func<int, TipoSoggetto?> FetchTipoSoggettoData { get; set; } = (s) => null;



        [Parameter]
        public bool PermettiModificaDatiAnagrafici { get; set; } = true;

        [Parameter]
        public bool PermettiModificaTipoSoggetto { get; set; } = true;

        [Parameter]
        public bool RichiedeDatiAlbo { get; set; } = true;

        [Parameter]
        public string LimitaDatiAlbo { get; set; } = "";

        [Parameter]
        public EventCallback OnBeforeAcceptEdit { get; set; }

        [Parameter, Required]
        public EventCallback<AnagraficaDomanda> OnAcceptEdit { get; set; }
        //public Func<Task> OnAcceptEdit2 { get; set; } = () => Task.CompletedTask;

        [Parameter, Required]
        public EventCallback OnClose { get; set; }

        [Parameter, Required]
        public EventCallback OnCancelEdit { get; set; }

        [Parameter, Required]
        public EventCallback<string> OnError { get; set; }

        [Parameter, Required]
        public EventCallback<ModelRichiedenti> OnDelete { get; set; }

        [Parameter]
        public PresentazioneIstanzaDbV2.ANAGRAFERow? InitialModel { get; set; }

        [Parameter]
        public IEnumerable<TipoSoggetto> TipiSoggettoPf { get; set; } = null;

        [Parameter]
        public Dictionary<int, int> NumOccorrenzeTipoSoggetto { get; set; } = new();

        [Parameter]
        public int? CurrentTipoSoggetto { get; set; } = null;

        private bool showDescrizioneEstesa = false;
        private bool maxOccorrenzeRaggiunte = false;
        private bool showProvinciaAlbo = true;
        private readonly Model model = new();
        private string _cfCalcolato = "";

        public class Model
        {
            //[Required(ErrorMessage = FormMessageStore.Messages.SELEZIONA_ELEMENTO)]
            public DropDownItem? TipoSoggetto { get; set; } // { Codice = -1, Descrizione = "" };

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            [MaxLength(128)]
            public string? DescrizioneEstesa { get; set; }

            public int? IdRichiedente { get; set; }

            public DropDownItem? Titolo { get; set; }

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            [MaxLength(180)]
            public string? Cognome { get; set; }

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            [MaxLength(30)]
            public string? Nome { get; set; }

            public DropDownItem? Sesso { get; set; }

            public DropDownItem? Cittadinanza { get; set; }

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            public DateTime? DataDiNascita { get; set; }

            public AutocompleteFormResult? ComuneNascita { get; set; }

            public AutocompleteFormResult? ComuneResidenza { get; set; }

            //[Required(ErrorMessage = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)]
            [MaxLength(32)]
            public string? CodiceFiscale { get; set; }

            [MaxLength(100)]
            public string? Indirizzo { get; set; }

            [MaxLength(100)]
            public string? Citta { get; set; }

            [MaxLength(15)]
            public string? CAP { get; set; }

            public DropDownItem? AlboProfessionale { get; set; }

            [MaxLength(10)]
            public string? NumeroAlbo { get; set; }

            public AutocompleteFormResult? ProvinciaAlbo { get; set; }
            public AutocompleteFormResult? RegioneAlbo { get; set; }

            [MaxLength(15)]
            public string? Telefono { get; set; }

            [MaxLength(15)]
            public string? Cellulare { get; set; }

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
        private List<DropDownItem> TitoliDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> SessoDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> CittadinanzaDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> AlboDataItems { get; set; } = new List<DropDownItem>();

        private bool _showModalWarningCF;


        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            this.BindTipiSoggetto();
            this.BindTitoli();
            this.BindSesso();
            this.BindCittadinanze();
            this.BindElenchiProfessionali();

            await this.DataBindAsync();
        }

        private async Task DataBindAsync()
        {
            if (this.InitialModel != null)
            {
                var selectedTitolo = this.TitoliDataItems.Find(x => x.Value == this.InitialModel.TITOLO);
                var selectedSesso = this.SessoDataItems.Find(x => x.Value == this.InitialModel.SESSO);
                var selectedCittadinanza = this.InitialModel.CODICECITTADINANZA == null ? null : this.CittadinanzaDataItems.Find(x => x.Value == this.InitialModel.CODICECITTADINANZA.ToString());

                this.model.Cognome = this.InitialModel.NOMINATIVO;
                this.model.Nome = this.InitialModel.NOME;
                this.model.Titolo = selectedTitolo == null ? new DropDownItem("", "") { } : selectedTitolo;
                this.model.Sesso = selectedSesso == null ? new DropDownItem("", "") { } : selectedSesso;
                this.model.Cittadinanza = selectedCittadinanza == null ? new DropDownItem("", "") { } : selectedCittadinanza;

                // Residenza
                string comuneResidenza = this.InitialModel.COMUNERESIDENZA;

                if (!String.IsNullOrEmpty(comuneResidenza))
                {
                    var comuneresidenzaDto = this.GetComuneDataItems(comuneResidenza);

                    if (comuneresidenzaDto != null)
                    {
                        this.model.ComuneResidenza = new AutocompleteFormResult(comuneresidenzaDto.CodiceComune, comuneresidenzaDto.Comune + " (" + comuneresidenzaDto.SiglaProvincia + ")");
                    }
                }
                else
                {
                    this.model.ComuneResidenza = new AutocompleteFormResult("", "");
                }

                this.model.Indirizzo = this.InitialModel.INDIRIZZO;
                this.model.Citta = this.InitialModel.CITTA;
                this.model.CAP = this.InitialModel.CAP;

                // Corrispondenza
                string comuneCorrispondenza = this.InitialModel.COMUNECORRISPONDENZA;

                if (!String.IsNullOrEmpty(comuneCorrispondenza))
                {
                    var comuneCorrispondenzaDto = this.GetComuneDataItems(comuneCorrispondenza);

                    if (comuneCorrispondenzaDto != null)
                    {
                        this.model.ComuneCorrispondenza = new AutocompleteFormResult(comuneCorrispondenza, comuneCorrispondenzaDto.Comune + " (" + comuneCorrispondenzaDto.SiglaProvincia + ")");
                    }
                }
                else
                {
                    this.model.ComuneCorrispondenza = new AutocompleteFormResult("", "");
                }

                this.model.IndirizzoCorrispondenza = this.InitialModel.INDIRIZZOCORRISPONDENZA;
                this.model.CittaCorrispondenza = this.InitialModel.CITTACORRISPONDENZA;
                this.model.CAPCorrispondenza = this.InitialModel.CAPCORRISPONDENZA;

                this.model.CodiceFiscale = this.InitialModel.CODICEFISCALE;

                // comune nascita
                string comuneNascita = this.InitialModel.CODCOMNASCITA;

                if (!String.IsNullOrEmpty(comuneNascita))
                {
                    var comuneNascitaDto = this.GetComuneDataItems(comuneNascita);

                    if (comuneNascitaDto != null)
                    {
                        this.model.ComuneNascita = new AutocompleteFormResult(comuneNascita, comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")");
                    }
                    else
                    {
                        this.model.ComuneNascita = new AutocompleteFormResult("", "");
                    }
                }
                else
                {
                    // provo a ricavare il comune dal codice fiscale
                    this.model.ComuneNascita = new AutocompleteFormResult("", "");

                    if (!String.IsNullOrEmpty(this.model.CodiceFiscale) && this.model.CodiceFiscale.Length == 16)
                    {
                        var codComune = this.model.CodiceFiscale.Substring(11, 4);

                        var comuneNascitaDto = this.GetComuneDataItems(codComune);

                        if (comuneNascitaDto != null)
                        {
                            this.model.ComuneNascita = new AutocompleteFormResult(comuneNascitaDto.CodiceComune, comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")");
                        }
                    }
                }

                this.model.Telefono = this.InitialModel.TELEFONO;
                this.model.Cellulare = this.InitialModel.TELEFONOCELLULARE;
                this.model.EMail = this.InitialModel.EMAIL;
                this.model.EMailPEC = this.InitialModel.Pec;

                var selectedSoggetto = this.SoggettiDataItems.Find(x => x.Value == this.InitialModel.TIPOSOGGETTO.ToString());

                if (selectedSoggetto == null)
                    this.model.TipoSoggetto = new DropDownItem("", "") { };
                else
                    this.model.TipoSoggetto = selectedSoggetto;

                this.VerificaTipoSoggetto(this.model.TipoSoggetto.Value);
                this.CheckDatiAlbo();

                this.model.DataDiNascita = this.InitialModel.IsDATANASCITANull() ? null : this.InitialModel.DATANASCITA;
                this.model.Anagrafe_PK = this.InitialModel.ANAGRAFE_PK.ToString();

                await this.InizializzaDatiAlboAsync(this.InitialModel.IdAlbo, this.InitialModel.NumeroAlbo, this.InitialModel.ProvinciaAlbo);

                this.model.DescrizioneEstesa = this.InitialModel.DescrizioneTipoSoggetto;
            }
        }

        private async Task InizializzaDatiAlboAsync(string idAlbo, string numeroAlbo, string provinciaAlbo)
        {
            this.model.ProvinciaAlbo = new AutocompleteFormResult("", "");
            this.model.RegioneAlbo = new AutocompleteFormResult("", "");
            this.model.NumeroAlbo = String.Empty;
            this.model.AlboProfessionale = null;

            if (this.AlboDataItems.Select(x => x.Value == idAlbo).FirstOrDefault())
            {
                this.model.AlboProfessionale = this.AlboDataItems.Find(x => x.Value == idAlbo);
                this.model.NumeroAlbo = numeroAlbo;

                if (!String.IsNullOrEmpty(provinciaAlbo))
                {
                    var provinciaDto = await this.GetProvinciaAlboDataItemsAsync(provinciaAlbo);

                    if (provinciaDto != null)
                    {
                        if (this.showProvinciaAlbo)
                            this.model.ProvinciaAlbo = new AutocompleteFormResult(provinciaDto.SiglaProvincia, provinciaDto.Provincia);
                        else
                            this.model.RegioneAlbo = new AutocompleteFormResult(provinciaDto.SiglaProvincia, provinciaDto.Provincia);
                    }
                    else
                    {
                        if (this.showProvinciaAlbo)
                            this.model.ProvinciaAlbo = new AutocompleteFormResult(provinciaAlbo, provinciaAlbo);
                        else
                            this.model.RegioneAlbo = new AutocompleteFormResult(provinciaAlbo, provinciaAlbo);
                    }
                }
            }
        }

        private void BindSesso()
        {
            this.SessoDataItems = new List<DropDownItem>()
            {
                new("M", "Maschio") { },
                new("F", "Femmina") { }
            };
        }

        private void BindTipiSoggetto()
        {
            if (this.SoggettiDataItems.Count > 0)
                return;

            if (TipiSoggettoPf is null)
            {
                var list = this._tipiSoggettoRepository.GetTipiSoggettoPersonaFisica(this.CodiceIntervento).ToList();

                foreach (var item in list)
                {
                    this.SoggettiDataItems.Add(new DropDownItem(item.Id.ToString(), item.Descrizione) { });
                }
            }
            else
            {
                foreach (var item in this.TipiSoggettoPf)
                {
                    this.SoggettiDataItems.Add(new DropDownItem(item.Id.ToString(), item.Descrizione) { });
                }
            }
        }

        private void BindElenchiProfessionali()
        {
            if (this.AlboDataItems.Count > 0)
                return;

            var limitaDatiAlbo = new int[0];

            if (!string.IsNullOrEmpty(this.LimitaDatiAlbo))
            {
                limitaDatiAlbo = this.LimitaDatiAlbo.Split(',').Select(x => Convert.ToInt32(x.Trim())).ToArray();
            }

            var list = this._elenchiProfessionaliRepository.GetList()
                        .Where(x => { return limitaDatiAlbo.Length == 0 || limitaDatiAlbo.Contains(x.EpId.Value); })
                        .ToList();

            foreach (var item in list)
            {
                this.AlboDataItems.Add(new DropDownItem(item.EpId!.ToString(), item.EpDescrizione, new Dictionary<string, object>
                {{
                    "data-regionale", item.EpRegionale.GetValueOrDefault(0) == 1
                }}));
            }
        }

        private void BindCittadinanze()
        {
            if (this.CittadinanzaDataItems.Count > 0)
                return;

            var list = this._cittadinanzeService.GetListaCittadinanze(true);

            foreach (var item in list)
            {
                this.CittadinanzaDataItems.Add(new DropDownItem(item.Codice.ToString(), item.Descrizione) { });
            }
        }

        private void BindTitoli()
        {
            if (this.TitoliDataItems.Count > 0)
                return;

            var list = this._titoliRepository.GetList();

            foreach (var item in list)
            {
                this.TitoliDataItems.Add(new DropDownItem(item.CodiceTitolo, item.Titolo) { });
            }
        }

        private void VerificaTipoSoggetto(string codiceTipoSoggetto)
        {
            if (string.IsNullOrEmpty(codiceTipoSoggetto))
            {
                this.showDescrizioneEstesa =
                this.maxOccorrenzeRaggiunte =
                this.RichiedeDatiAlbo = false;

                return;
            }

            int code = int.Parse(codiceTipoSoggetto);
            TipoSoggetto tipoSoggetto = null;

            if (TipiSoggettoPf is null)
            {
                tipoSoggetto = this._tipiSoggettoRepository.GetById(code);
            }
            else
            {
                tipoSoggetto = this.TipiSoggettoPf.FirstOrDefault(x => x.Id == code);
            }

            this.showDescrizioneEstesa = tipoSoggetto.RichiedeSpecificaDescrizione;

            if (!this.showDescrizioneEstesa)
                this.model.DescrizioneEstesa = string.Empty;

            this.RichiedeDatiAlbo = tipoSoggetto.RichiedeDatiAlbo;

            //verifica che non sia raggiunto il limite dei tipi soggetto disponibili
            this.maxOccorrenzeRaggiunte = NumOccorrenzeTipoSoggetto.GetValueOrDefault(tipoSoggetto.Id) >= tipoSoggetto.OccorrenzeMax;

            //se il limite è stato raggiunto ma sono in editing devo permettere la modifica
            if (this.maxOccorrenzeRaggiunte && this.CurrentTipoSoggetto == tipoSoggetto.Id)
            {
                this.maxOccorrenzeRaggiunte = false;
            }
        }

        private void CheckDatiAlbo()
        {
            if (this.model.AlboProfessionale == null || string.IsNullOrEmpty(this.model.AlboProfessionale.Value))
            {
                this.showProvinciaAlbo = true;
            }
            else
            {
                this.showProvinciaAlbo = this.model.AlboProfessionale.GetExtraValue<bool>("data-regionale");
            }
        }

        private Task<IEnumerable<DatiComuneCompatto>> GetComuniAsync(string value)
        {
            return Task.FromResult(this.FetchComuni.Invoke(value));
        }

        private DatiComuneCompatto? GetComuneDataItems(string? value)
        {
            if (string.IsNullOrEmpty(value))
            {
                return null;
            }

            return this.FetchComuneData.Invoke(value);
        }

        private AutocompleteFormResult? ComuneConvertMethod(DatiComuneCompatto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.CodiceComune, $"{dati.Comune} ({dati.SiglaProvincia})");
        }

        private Task<IEnumerable<DatiProvinciaCompatto>> GetProvincieAsync(string value)
        {
            return Task.FromResult(this.FetchProvincie.Invoke(value));
        }

        private Task<DatiProvinciaCompatto?> GetProvinciaAlboDataItemsAsync(string value)
        {
            return Task.FromResult(this.FetchProvinciaData.Invoke(value));
        }

        private AutocompleteFormResult? ProvinciaAlboConvertMethod(DatiProvinciaCompatto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.SiglaProvincia, dati.Provincia);
        }

        private Task<IEnumerable<string>> GetRegioniAsync(string value)
        {
            var regioni = new[]{

                "Abruzzo",
                "Basilicata",
                "Calabria",
                "Campania",
                "Emilia Romagna",
                "Friuli",
                "Lazio",
                "Liguria",
                "Lombardia",
                "Marche",
                "Molise",
                "Piemonte",
                "Puglia",
                "Sardegna",
                "Sicilia",
                "Toscana",
                "Trentino",
                "Umbria",
                "Valle d'Aosta",
                "Veneto"
            };

            if (string.IsNullOrEmpty(value))
                return Task.FromResult(regioni.AsEnumerable());

            return Task.FromResult(regioni.Where(x => x.ToLower().StartsWith(value.ToLower())));
        }

        private AutocompleteFormResult RegioneAlboConvertMethod(string regione)
        {
            return new AutocompleteFormResult(regione, regione);
        }

        private Task<AnagraficaDomanda> GetAnagrafeRowDataItemsAsync(int value)
        {
            return Task.FromResult(this.FetchAnagrafeData.Invoke(value));
        }

        private Task<CittadinanzaCompatto?> GetCittadinanzaDataItemsAsync(string? value)
        {
            if (String.IsNullOrEmpty(value))
            {
                return Task.FromResult<CittadinanzaCompatto?>(null);
            }

            return Task.FromResult(this.FetchCittadinanzaData.Invoke(value));
        }

        private Task<TipoSoggetto?> GetTipoSoggettoDataItemsAsync(int value)
        {
            return Task.FromResult(this.FetchTipoSoggettoData.Invoke(value));
        }

        private bool ValidaEmail(string? text)
        {
            if (String.IsNullOrEmpty(text))
            {
                return true;
            }

            var parts = text.Split('@');

            if (parts.Length != 2)
            {
                return false;
            }

            if (parts[1].IndexOf(".") == -1)
            {
                return false;
            }

            return true;
        }

        private async Task<bool> ValidaCFAsync(string cognome, string nome, DateTime? dataNascita, string sesso, string comuneNascita, string codiceFiscale)
        {
            if (String.IsNullOrEmpty(cognome))
            {
                await this.OnError.InvokeAsync("Specificare il cognome");
                return false;
            }

            if (String.IsNullOrEmpty(nome))
            {
                await this.OnError.InvokeAsync("Specificare il nome");
                return false;
            }

            DateTime date = DateTime.MinValue;

            if (dataNascita != null)
                date = dataNascita.Value;

            if (String.IsNullOrEmpty(sesso))
            {
                await this.OnError.InvokeAsync("Specificare il sesso");
                return false;
            }

            if (String.IsNullOrEmpty(comuneNascita))
            {
                await this.OnError.InvokeAsync("Specificare il comune di nascita");
                return false;
            }

            var calcolatoreCf = new CodiceFiscale();
            this._cfCalcolato = calcolatoreCf.calcolaCF(cognome, nome, date, sesso, comuneNascita);

            // Dava errore a perugia con i dati provenienti dall'anagrafica comunale
            if (this._cfCalcolato.Length > 15)
            {
                // Modificata verifica del codice fiscale, prima era basata solo sulle prime 15 cifre, 
                // ora il controllo è stato esteso a tutte le 16 cifre visto che la verifica non è bloccante
                /* ERA:
                 * var cfCalcolatoOrig = cfCalcolato;
                 * cfCalcolato = cfCalcolato.Substring(0, 15);
                 * var cfConfronto = codiceFiscale.Substring(0, 15);
                 */

                var cfConfronto = codiceFiscale;

                if (this._cfCalcolato.ToUpperInvariant().CompareTo(cfConfronto.ToUpperInvariant()) != 0)
                {
                    this._showModalWarningCF = true;

                    //messageContainer.AddWarning($"Il codice fiscale calcolato in base ai dati immessi ({cfCalcolatoOrig.ToUpper()}) non coincide con il codice fiscale specificato ({codiceFiscale.ToUpper()}). <br//> E' comunque possibile proseguire con l'inserimento dell'anagrafica se si è certi della correttezza dei dati immessi.");
                    return true;
                }
            }

            return true;
        }

        private async Task OnBtnConfirmAsync()
        {
            var cfValido = await this.ValidaCFAsync(this.model.Cognome, this.model.Nome, this.model.DataDiNascita, this.model.Sesso.Value, this.model.ComuneNascita.Value, this.model.CodiceFiscale);

            if (cfValido)
            {
                if (this._showModalWarningCF)
                    return;

                await this.ConfermaDatiAnagraficaAsync();
            }
        }

        private void ValidationCallback(FormMessageStore store)
        {
            if (this.EmailOPecObbligatori && (String.IsNullOrEmpty(this.model.EMailPEC) && String.IsNullOrEmpty(this.model.EMail)))
            {
                var msg = "Specificare l'indirizzo E-Mail o PEC";

                store.Add(() => this.model!.EMailPEC!, msg);
                store.Add(() => this.model!.EMail!, msg);
                return;
            }
        }

        private async Task ConfermaDatiAnagraficaAsync()
        {
            if (this.OnBeforeAcceptEdit.HasDelegate)
                await this.OnBeforeAcceptEdit.InvokeAsync();

            this._showModalWarningCF = false;

            if (this.model.TipoSoggetto == null || string.IsNullOrEmpty(this.model.TipoSoggetto.Value))
            {
                await this.OnError.InvokeAsync("Specificare il tipo soggetto");
                return;
            }

            if (this.showDescrizioneEstesa && string.IsNullOrEmpty(this.model.DescrizioneEstesa))
            {
                await this.OnError.InvokeAsync("Specificare la descrizione estesa");
                return;
            }

            if (String.IsNullOrEmpty(this.model.Cognome))
            {
                await this.OnError.InvokeAsync("Specificare il cognome");
                return;
            }

            if (String.IsNullOrEmpty(this.model.Nome))
            {
                await this.OnError.InvokeAsync("Specificare il nome");
                return;
            }

            if (this.model.Sesso == null)
            {
                await this.OnError.InvokeAsync("Specificare il sesso");
                return;
            }

            if (!this.model.DataDiNascita.HasValue)
            {
                await this.OnError.InvokeAsync("Specificare la data di nascita");
                return;
            }

            if (this.model.ComuneNascita == null || String.IsNullOrEmpty(this.model.ComuneNascita.Value))
            {
                await this.OnError.InvokeAsync("Specificare il comune di nascita");
                return;
            }

            if (String.IsNullOrEmpty(this.model.CodiceFiscale))
            {
                await this.OnError.InvokeAsync("Specificare il codice fiscale");
                return;
            }

            if (!(await this.ValidaCFAsync(this.model.Cognome, this.model.Nome, this.model.DataDiNascita, this.model.Sesso.Value, this.model.ComuneNascita.Value, this.model.CodiceFiscale)))
            {
                return;
            }

            if (this.CittadinanzaObbligatoria && (this.model.Cittadinanza == null || String.IsNullOrEmpty(this.model.Cittadinanza.Value)))
            {
                await this.OnError.InvokeAsync("Specificare la cittadinanza");
                return;
            }

            if (this.TitoloObbligatorio && (this.model.Titolo == null || String.IsNullOrEmpty(this.model.Titolo.Value)))
            {
                await this.OnError.InvokeAsync("Specificare il titolo");
                return;
            }

            if (this.ResidenzaObbligatoria)
            {
                if (this.model.ComuneResidenza == null || String.IsNullOrEmpty(this.model.ComuneResidenza.Value))
                {
                    await this.OnError.InvokeAsync("Specificare il comune di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.Indirizzo))
                {
                    await this.OnError.InvokeAsync("Specificare l'indirizzo di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.Citta))
                {
                    await this.OnError.InvokeAsync("Specificare la città di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.CAP))
                {
                    await this.OnError.InvokeAsync("Specificare il CAP di residenza");
                    return;
                }
            }

            if (this.CorrispondenzaObbligatoria)
            {
                if (this.model.ComuneCorrispondenza == null || String.IsNullOrEmpty(this.model.ComuneCorrispondenza.Value))
                {
                    await this.OnError.InvokeAsync("Specificare il comune di corrispondenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.IndirizzoCorrispondenza))
                {
                    await this.OnError.InvokeAsync("Specificare l'indirizzo di corrispondenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.CittaCorrispondenza))
                {
                    await this.OnError.InvokeAsync("Specificare la città di corrispondenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.CAPCorrispondenza))
                {
                    await this.OnError.InvokeAsync("Specificare il CAP di corrispondenza");
                    return;
                }
            }

            if (this.TelefonoObbligatorio && String.IsNullOrEmpty(this.model.Telefono))
            {
                await this.OnError.InvokeAsync("Specificare il numero di telefono");
                return;
            }

            if (this.CellulareObbligatorio && String.IsNullOrEmpty(this.model.Cellulare))
            {
                await this.OnError.InvokeAsync("Specificare il numero di cellulare");
                return;
            }

            if (this.EmailObbligatoria && String.IsNullOrEmpty(this.model.EMail))
            {
                await this.OnError.InvokeAsync("Specificare l'indirizzo email");
                return;
            }

            if (this.PecObbligatoria && String.IsNullOrEmpty(this.model.EMailPEC))
            {
                await this.OnError.InvokeAsync("Specificare l'indirizzo PEC");
                return;
            }

            if (this.TelefonoOCellulareObbligatori && (String.IsNullOrEmpty(this.model.Telefono) && String.IsNullOrEmpty(this.model.Cellulare)))
            {
                await this.OnError.InvokeAsync("Specificare il numero di telefono o cellulare");
                return;
            }

            if (!this.ValidaEmail(this.model.EMail))
            {
                await this.OnError.InvokeAsync($"Il campo E-Mail contiene un indirizzo non valido");
                return;
            }

            if (!this.ValidaEmail(this.model.EMailPEC))
            {
                await this.OnError.InvokeAsync($"Il campo PEC contiene un indirizzo non valido");
                return;
            }

            int idAnagrafica = -1;

            int.TryParse(this.model.Anagrafe_PK, out idAnagrafica);

            var anagrafica = await this.GetAnagrafeRowDataItemsAsync(idAnagrafica);
            var row = anagrafica.ToAnagrafeRow();

            var datiComuneNascita = this.GetComuneDataItems(this.model.ComuneNascita.Value);

            if (datiComuneNascita == null)
            {
                await this.OnError.InvokeAsync("Il comune di nascita specificato non è valido");
                return;
            }

            row.TIPOANAGRAFE = "F";

            if (this.model.Titolo != null && !String.IsNullOrEmpty(this.model.Titolo.Value))
            {
                row.TITOLO = this.model.Titolo.Value;
            }

            row.NOMINATIVO = this.model.Cognome;
            row.NOME = this.model.Nome;

            row.SESSO = this.model.Sesso.Value;


            row.PROVINCIANASCITA = datiComuneNascita.SiglaProvincia;

            row.INDIRIZZO = this.model.Indirizzo;
            row.CITTA = this.model.Citta;
            row.CAP = this.model.CAP;

            row.INDIRIZZOCORRISPONDENZA = this.model.IndirizzoCorrispondenza;
            row.CITTACORRISPONDENZA = this.model.CittaCorrispondenza;
            row.CAPCORRISPONDENZA = this.model.CAPCorrispondenza;

            row.CODICEFISCALE = this.model.CodiceFiscale.ToUpper();
            row.TELEFONO = this.model.Telefono;
            row.TELEFONOCELLULARE = this.model.Cellulare;
            row.EMAIL = this.model.EMail;
            row.Pec = this.model.EMailPEC;

            row.DATANASCITA = this.model.DataDiNascita.GetValueOrDefault(DateTime.MinValue);

            // Cittadinanza
            var datiCittadinanza = await this.GetCittadinanzaDataItemsAsync(this.model.Cittadinanza?.Value);

            if (datiCittadinanza != null)
            {
                row.CODICECITTADINANZA = datiCittadinanza.Codice;
                row.IsCittadinoExtracomunitario = !datiCittadinanza.FlgPaeseComunitario;
            }

            // Residenza
            var datiComuneResidenza = this.GetComuneDataItems(this.model.ComuneResidenza?.Value);

            if (datiComuneResidenza != null)
            {
                row.COMUNERESIDENZA = datiComuneResidenza.CodiceComune;
                row.PROVINCIA = datiComuneResidenza.SiglaProvincia;
            }

            if (this.model.ComuneCorrispondenza != null && !String.IsNullOrEmpty(this.model.ComuneCorrispondenza.Value))
            {
                var datiComuneCorrispondenza = this.GetComuneDataItems(this.model.ComuneCorrispondenza.Value);

                if (datiComuneCorrispondenza != null)
                {
                    row.PROVINCIACORRISPONDENZA = datiComuneCorrispondenza.SiglaProvincia;
                    row.COMUNECORRISPONDENZA = datiComuneCorrispondenza.CodiceComune;
                }
            }

            row.CODCOMNASCITA = this.model.ComuneNascita.Value;

            row.TIPOSOGGETTO = this.model.TipoSoggetto == null ? -1 : Convert.ToInt32(this.model.TipoSoggetto.Value);

            var tipoSoggetto = await this.GetTipoSoggettoDataItemsAsync(row.TIPOSOGGETTO.Value);

            if (tipoSoggetto != null && tipoSoggetto.RichiedeDatiAlbo)
            {
                if (this.model.AlboProfessionale == null || String.IsNullOrEmpty(this.model.AlboProfessionale.Value))
                {
                    await this.OnError.InvokeAsync("Per il tipo soggetto selezionato è necessario specificare l'albo professionale di appartenenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.model.NumeroAlbo.Trim()))
                {
                    await this.OnError.InvokeAsync("Per il tipo soggetto selezionato è necessario specificare il numero di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                string provincia_regione = string.Empty;

                if (this.model.ProvinciaAlbo != null && !string.IsNullOrEmpty(this.model.ProvinciaAlbo.Value.Trim()))
                    provincia_regione = this.model.ProvinciaAlbo.Value.Trim();

                if (this.model.RegioneAlbo != null && !string.IsNullOrEmpty(this.model.RegioneAlbo.Value.Trim()))
                    provincia_regione = this.model.RegioneAlbo.Value.Trim();

                if (String.IsNullOrEmpty(provincia_regione))
                {
                    await this.OnError.InvokeAsync("Per il tipo soggetto selezionato è necessario specificare la provincia di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                row.IdAlbo = this.model.AlboProfessionale.Value;

                if (!String.IsNullOrEmpty(this.model.AlboProfessionale.Value))
                    row.DescrizioneAlbo = this.model.AlboProfessionale.Text;
                else
                    row.DescrizioneAlbo = "";

                row.NumeroAlbo = this.model.NumeroAlbo;
                row.ProvinciaAlbo = provincia_regione;
            }
            else
            {
                row.IdAlbo = String.Empty;
                row.DescrizioneAlbo = String.Empty;
                row.NumeroAlbo = String.Empty;
                row.ProvinciaAlbo = String.Empty;
            }

            row.DescrSoggetto = this.model.TipoSoggetto == null ? String.Empty : this.model.TipoSoggetto.Text;
            row.DescrizioneTipoSoggetto = this.model.DescrizioneEstesa;

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
    }
}