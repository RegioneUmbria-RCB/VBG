using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.Utils.CalcoloCF;
using Microsoft.AspNetCore.Components;
using System.ComponentModel.DataAnnotations;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche.Components
{
    public partial class EditorPersonaFisica
    {

        public class Model
        {
            //[Required(ErrorMessage = FormMessageStore.Messages.SELEZIONA_ELEMENTO)]
            public DropDownItem? TipoSoggetto { get; set; } // { Codice = -1, Descrizione = "" };

            [MaxLength(128)]
            public string? DescrizioneEstesa { get; set; }

            public DropDownItem? Titolo { get; set; }

            //[Required]
            [MaxLength(180)]
            public string? Cognome { get; set; }

            //[Required]
            [MaxLength(30)]
            public string? Nome { get; set; }

            public DropDownItem? Sesso { get; set; }

            public DropDownItem? Cittadinanza { get; set; }

            //[Required]
            public DateTime? DataDiNascita { get; set; }

            public AutocompleteFormResult? ComuneNascita { get; set; }

            public AutocompleteFormResult? ComuneResidenza { get; set; }

            //[Required]
            [MaxLength(32)]
            public string? CodiceFiscale { get; set; }

            [MaxLength(100)]
            public string? Indirizzo { get; set; }

            [MaxLength(100)]
            public string? Citta { get; set; }

            [MaxLength(100)]
            public string? CAP { get; set; }

            public DropDownItem AlboProfessionale { get; set; } = new();

            [MaxLength(10)]
            public string NumeroAlbo { get; set; } = "";

            public AutocompleteFormResult ProvinciaAlbo { get; set; } = new();
            public AutocompleteFormResult RegioneAlbo { get; set; } = new();

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

            public AutocompleteFormResult ComuneCorrispondenza { get; set; }

            [MaxLength(100)]
            public string? IndirizzoCorrispondenza { get; set; }

            [MaxLength(50)]
            public string? CittaCorrispondenza { get; set; }

            [MaxLength(5)]
            public string? CAPCorrispondenza { get; set; }

            public string? Anagrafe_PK { get; set; }
        }

        [Inject]
        public ICittadinanzeService _cittadinanzeService { get; set; } = default!;

        [Inject]
        public ITitoliRepository _titoliRepository { get; set; } = default!;

        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; } = default!;

        [Inject]
        public IElenchiProfessionaliRepository _elenchiProfessionaliRepository { get; set; } = default!;

        [Parameter, EditorRequired]
        public EventCallback<AnagraficaDomanda> OnAcceptEdit { get; set; }

        [Parameter, EditorRequired]
        public PresentazioneIstanzaDbV2.ANAGRAFERow? InitialModel { get; set; }

        private bool _mostraDescrizioneEstesaTipoSoggetto = false;
        private bool _mostraProvinciaAlbo = true;
        private readonly Model _model = new();
        private bool _richiedeDatiAlbo = true;



        private List<DropDownItem> TitoliDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> SessoDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> CittadinanzaDataItems { get; set; } = new List<DropDownItem>();
        private List<DropDownItem> AlboDataItems { get; set; } = new List<DropDownItem>();


        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            this.BindTitoli();
            this.BindSesso();
            this.BindCittadinanze();
            this.BindElenchiProfessionali();

            await this.DataBindAsync();

            if (this.IdTipoSoggettoDefault.HasValue)
            {
                var soggetto = this.TipiSoggetto.FirstOrDefault(x => x.Value == this.IdTipoSoggettoDefault.ToString());

                if (soggetto is not null)
                {
                    this._model.TipoSoggetto = soggetto;
                }
            }
        }

        private async Task DataBindAsync()
        {
            if (this.InitialModel != null)
            {
                this._model.Cognome = this.InitialModel.NOMINATIVO;
                this._model.Nome = this.InitialModel.NOME;
                this._model.Titolo = this.TitoliDataItems.FirstOrDefault(x => x.Value == this.InitialModel.TITOLO) ?? new DropDownItem();// selectedTitolo == null ? new DropDownItem() { Value = "" } : selectedTitolo;
                this._model.Sesso = this.SessoDataItems.FirstOrDefault(x => x.Value == this.InitialModel.SESSO) ?? new DropDownItem();
                this._model.Cittadinanza = this.CittadinanzaDataItems.Find(x => x.Value == this.InitialModel.CODICECITTADINANZA?.ToString()) ?? new DropDownItem(); //selectedCittadinanza == null ? new DropDownItem() { Value = "" } : selectedCittadinanza;

                // Residenza
                this._model.ComuneResidenza = await this.ComuneToAutocompleteResultAsync(this.InitialModel.COMUNERESIDENZA) ?? new AutocompleteFormResult();

                this._model.Indirizzo = this.InitialModel.INDIRIZZO;
                this._model.Citta = this.InitialModel.CITTA;
                this._model.CAP = this.InitialModel.CAP;

                // Corrispondenza
                this._model.ComuneCorrispondenza = await this.ComuneToAutocompleteResultAsync(this.InitialModel.COMUNECORRISPONDENZA) ?? new AutocompleteFormResult();
                this._model.IndirizzoCorrispondenza = this.InitialModel.INDIRIZZOCORRISPONDENZA;
                this._model.CittaCorrispondenza = this.InitialModel.CITTACORRISPONDENZA;
                this._model.CAPCorrispondenza = this.InitialModel.CAPCORRISPONDENZA;

                this._model.CodiceFiscale = this.InitialModel.CODICEFISCALE;

                // comune nascita
                if (!String.IsNullOrEmpty(this.InitialModel.CODCOMNASCITA))
                {
                    this._model.ComuneNascita = await this.ComuneToAutocompleteResultAsync(this.InitialModel.CODCOMNASCITA) ?? new AutocompleteFormResult();
                }
                else
                {
                    // provo a ricavare il comune dal codice fiscale
                    if (!String.IsNullOrEmpty(this._model.CodiceFiscale) && this._model.CodiceFiscale.Length == 16)
                    {
                        var codComune = this._model.CodiceFiscale.Substring(11, 4);

                        this._model.ComuneNascita = await this.ComuneToAutocompleteResultAsync(codComune) ?? new AutocompleteFormResult();
                    }
                }

                this._model.Telefono = this.InitialModel.TELEFONO;
                this._model.Cellulare = this.InitialModel.TELEFONOCELLULARE;
                this._model.Fax = this.InitialModel.FAX;
                this._model.EMail = this.InitialModel.EMAIL;
                this._model.EMailPEC = this.InitialModel.Pec;

                this._model.TipoSoggetto = this.TipiSoggetto.FirstOrDefault(x => x.Value == this.InitialModel.TIPOSOGGETTO?.ToString()) ?? new DropDownItem();

                this.VerificaSeTipoSoggettoRichiedeDatiAlboEDescrizioneEstesa(this._model.TipoSoggetto.Value);
                this.VerificaSeAlboRichiedeProvincia();

                this._model.DataDiNascita = this.InitialModel.IsDATANASCITANull() ? null : this.InitialModel.DATANASCITA;
                this._model.Anagrafe_PK = this.InitialModel.ANAGRAFE_PK.ToString();

                await this.InizializzaDatiAlboAsync(this.InitialModel.IdAlbo, this.InitialModel.NumeroAlbo, this.InitialModel.ProvinciaAlbo);

                this._model.DescrizioneEstesa = this.InitialModel.DescrizioneTipoSoggetto;
            }
        }

        private async Task InizializzaDatiAlboAsync(string idAlbo, string numeroAlbo, string provinciaAlbo)
        {
            var alboSelezionato = this.AlboDataItems.FirstOrDefault(x => x.Value == idAlbo);

            if (alboSelezionato != null)
            {
                this._model.AlboProfessionale = alboSelezionato;
                this._model.NumeroAlbo = numeroAlbo;

                if (!String.IsNullOrEmpty(provinciaAlbo))
                {
                    var provinciaDto = await this.GetProvinciaByIdAsync(provinciaAlbo);

                    // ???
                    if (provinciaDto != null)
                    {
                        if (this._mostraProvinciaAlbo)
                            this._model.ProvinciaAlbo = new AutocompleteFormResult(provinciaDto.SiglaProvincia, provinciaDto.Provincia);
                        else
                            this._model.RegioneAlbo = new AutocompleteFormResult(provinciaDto.SiglaProvincia, provinciaDto.Provincia);
                    }
                    else
                    {
                        if (this._mostraProvinciaAlbo)
                            this._model.ProvinciaAlbo = new AutocompleteFormResult(provinciaAlbo, provinciaAlbo);
                        else
                            this._model.RegioneAlbo = new AutocompleteFormResult(provinciaAlbo, provinciaAlbo);
                    }
                }
            }
        }

        private void BindSesso()
        {
            this.SessoDataItems = new List<DropDownItem>()
            {
                new DropDownItem("M", "Maschio"),
                new DropDownItem("F", "Femmina")           };
        }


        private void BindElenchiProfessionali()
        {
            if (this.AlboDataItems.Count > 0)
                return;

            var limitaDatiAlbo = new int[0];

            if (!string.IsNullOrEmpty(this.ImpostazioniStep.DettagliPf.LimitaDatiAlbo))
            {
                limitaDatiAlbo = this.ImpostazioniStep.DettagliPf.LimitaDatiAlbo.Split(',').Select(x => Convert.ToInt32(x.Trim())).ToArray();
            }

            var list = this._elenchiProfessionaliRepository.GetList()
                        .Where(x => { return limitaDatiAlbo.Length == 0 || limitaDatiAlbo.Contains(x.EpId!.Value); })
                        .ToList();

            foreach (var item in list)
            {
                this.AlboDataItems.Add(new DropDownItem(item.EpId!.ToString(), item.EpDescrizione, new[]
                {
                new KeyValuePair<string, object>("data-regionale", item.EpRegionale.GetValueOrDefault(0))
            }));
            }
        }

        private void BindCittadinanze()
        {
            if (this.CittadinanzaDataItems.Count > 0)
                return;

            var list = this._cittadinanzeService.GetListaCittadinanze(true);

            foreach (var item in list)
            {
                this.CittadinanzaDataItems.Add(new DropDownItem(item.Codice.ToString(), item.Descrizione));
            }
        }

        private void BindTitoli()
        {
            if (this.TitoliDataItems.Count > 0)
                return;

            var list = this._titoliRepository.GetList();

            foreach (var item in list)
            {
                this.TitoliDataItems.Add(new DropDownItem(item.CodiceTitolo, item.Titolo));
            }
        }

        private bool TipoSoggettoRichiedeDescrizioneEstesa(string? idTipoSoggettoAsString)
        {
            if (String.IsNullOrEmpty(idTipoSoggettoAsString))
            {
                return false;
            }

            if (!int.TryParse(idTipoSoggettoAsString, out var idTipoSoggetto))
            {
                return false;
            }

            var soggetto = this._tipiSoggettoRepository.GetById(idTipoSoggetto);

            return soggetto.RichiedeSpecificaDescrizione;
        }

        private void VerificaSeTipoSoggettoRichiedeDatiAlboEDescrizioneEstesa(string codiceTipoSoggetto)
        {
            if (string.IsNullOrEmpty(codiceTipoSoggetto))
            {
                this._mostraDescrizioneEstesaTipoSoggetto = false;
                this._richiedeDatiAlbo = false;

                return;
            }

            int code = int.Parse(codiceTipoSoggetto);
            var soggetto = this._tipiSoggettoRepository.GetById(code);

            this._mostraDescrizioneEstesaTipoSoggetto = soggetto.RichiedeSpecificaDescrizione;

            if (!this._mostraDescrizioneEstesaTipoSoggetto)
                this._model.DescrizioneEstesa = string.Empty;

            this._richiedeDatiAlbo = soggetto.RichiedeDatiAlbo;
        }

        private void VerificaSeAlboRichiedeProvincia()
        {
            this._mostraProvinciaAlbo = true;

            if (!string.IsNullOrEmpty(this._model.AlboProfessionale?.Value))
            {
                var regionale = this._model.AlboProfessionale.GetExtraValue<string>("data-regionale");

                if (regionale is not null)
                    this._mostraProvinciaAlbo = regionale.ToString() != "1";
                else
                    this._mostraProvinciaAlbo = false;
            }
        }

        private Task<IEnumerable<string>> GetRegioniAsync(string value)
        {
            string[] _regioni = {

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
                return Task.FromResult(_regioni.AsEnumerable());

            return Task.FromResult(_regioni.Where(x => x.StartsWith(value, StringComparison.OrdinalIgnoreCase)));
        }

        private AutocompleteFormResult regioneAlboConvertMethod(string regione)
        {
            return new AutocompleteFormResult(regione, regione);
        }

        private Task<CittadinanzaCompatto?> GetCittadinanzaDaIdAsync(string value)
        {
            if (String.IsNullOrEmpty(value))
                return Task.FromResult((CittadinanzaCompatto?)null);

            return Task.FromResult((CittadinanzaCompatto?)this._cittadinanzeService.GetCittadinanzaDaId(Convert.ToInt32(value)));
        }

        private string ClasseCampo(bool visible)
        {
            return visible ? "form-show" : "form-hide";
        }

        private bool ValidaEmail(string text)
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

        private bool ValidaCF(string cognome, string nome, DateTime? dataNascita, string sesso, string comuneNascita, string codiceFiscale)
        {
            if (String.IsNullOrEmpty(cognome))
            {
                // await this.OnError.InvokeAsync("Specificare il cognome");
                return false;
            }

            if (String.IsNullOrEmpty(nome))
            {
                // await this.OnError.InvokeAsync("Specificare il nome");
                return false;
            }

            if (String.IsNullOrEmpty(sesso))
            {
                // await this.OnError.InvokeAsync("Specificare il sesso");
                return false;
            }

            if (String.IsNullOrEmpty(comuneNascita))
            {
                // await this.OnError.InvokeAsync("Specificare il comune di nascita");
                return false;
            }

            var calcolatoreCf = new CodiceFiscale();
            var cfCalcolato = calcolatoreCf.calcolaCF(cognome, nome, dataNascita.GetValueOrDefault(DateTime.MinValue), sesso, comuneNascita);

            // Dava errore a perugia con i dati provenienti dall'anagrafica comunale
            if (cfCalcolato.Length > 15)
            {
                // Modificata verifica del codice fiscale, prima era basata solo sulle prime 15 cifre, 
                // ora il controllo è stato esteso a tutte le 16 cifre visto che la verifica non è bloccante
                /* ERA:
                 * var cfCalcolatoOrig = cfCalcolato;
                 * cfCalcolato = cfCalcolato.Substring(0, 15);
                 * var cfConfronto = codiceFiscale.Substring(0, 15);
                 */
                var cfCalcolatoOrig = cfCalcolato;
                var cfConfronto = codiceFiscale;

                if (cfCalcolato.ToUpperInvariant().CompareTo(cfConfronto.ToUpperInvariant()) != 0)
                {
                    // await this.OnError.InvokeAsync($"Il codice fiscale calcolato in base ai dati immessi ({cfCalcolatoOrig.ToUpper()}) non coincide con il codice fiscale specificato ({codiceFiscale.ToUpper()}). <br//><br//> E' comunque possibile proseguire con l'inserimento dell'anagrafica se si è certi della correttezza dei dati immessi.");
                    return true;
                }
            }

            return true;
        }

        private async Task ValidationCallbackAsync(FormMessageStore store)
        {
            await Task.CompletedTask;

            if (this._model == null)
            {
                return;
            }
            /*
            if (this._model.TipoSoggetto == null || string.IsNullOrEmpty(this._model.TipoSoggetto.Value))
            {
                store.Add(() => this._model.TipoSoggetto, "Specificare il tipo soggetto");
            }

            if (String.IsNullOrEmpty(this._model.Cognome))
            {
                store.Add(() => this._model.Cognome, "Specificare il cognome");
            }

            if (String.IsNullOrEmpty(this._model.Nome))
            {
                store.Add(() => this._model.Nome, "Specificare il nome");
            }

            if (this._model.Sesso == null)
            {
                store.Add(() => this._model.Sesso, "Specificare il sesso");
            }

            if (!this._model.DataDiNascita.HasValue)
            {
                store.Add(() => this._model.DataDiNascita, "Specificare la data di nascita");
            }

            if (this._model.ComuneNascita == null || String.IsNullOrEmpty(this._model.ComuneNascita.Value))
            {
                store.Add(() => this._model.ComuneNascita, "Specificare il comune di nascita");
            }

            if (String.IsNullOrEmpty(this._model.CodiceFiscale))
            {
                store.Add(() => this._model.CodiceFiscale, "Specificare il codice fiscale");
            }
            */
            var cfValido = this.ValidaCF(this._model.Cognome, this._model.Nome, this._model.DataDiNascita, this._model.Sesso?.Value, this._model.ComuneNascita?.Value, this._model.CodiceFiscale);


            if (!cfValido)
            {
                // TODO... Come mi comporto?
            }
            /*
            if (this.ImpostazioniStep.DettagliPf.CittadinanzaObbligatoria && (this._model.Cittadinanza == null || String.IsNullOrEmpty(this._model.Cittadinanza.Value)))
            {
                store.Add(() => this._model.Cittadinanza, "Specificare la cittadinanza");
            }

            if (this.ImpostazioniStep.DettagliPf.TitoloObbligatorio && (this._model.Titolo == null || String.IsNullOrEmpty(this._model.Titolo.Value)))
            {
                store.Add(() => this._model.Titolo, "Specificare il titolo");
            }

            if (this.ImpostazioniStep.DettagliPf.ResidenzaObbligatoria)
            {
                if (this._model.ComuneResidenza == null || String.IsNullOrEmpty(this._model.ComuneResidenza.Value))
                {
                    store.Add(() => this._model.ComuneResidenza, "Specificare il comune di residenza");
                }

                if (String.IsNullOrEmpty(this._model.Indirizzo))
                {
                    store.Add(() => this._model.Indirizzo, "Specificare l'indirizzo di residenza");
                }

                //if (String.IsNullOrEmpty(this._model.Citta))
                //{
                //    store.Add(() => this._model.Citta, "Specificare la città di residenza");
                //}

                if (String.IsNullOrEmpty(this._model.CAP))
                {
                    store.Add(() => this._model.CAP, "Specificare il CAP di residenza");
                }
            }
            */
            /*
            if (this.ImpostazioniStep.DettagliPf.CorrispondenzaObbligatoria)
            {
                if (this._model.ComuneCorrispondenza == null || String.IsNullOrEmpty(this._model.ComuneCorrispondenza.Value))
                {
                    store.Add(() => this._model.ComuneCorrispondenza, "Specificare il comune di corrispondenza");
                }

                if (String.IsNullOrEmpty(this._model.IndirizzoCorrispondenza))
                {
                    store.Add(() => this._model.IndirizzoCorrispondenza, "Specificare l'indirizzo di corrispondenza");
                }

                //if (String.IsNullOrEmpty(this._model.CittaCorrispondenza))
                //{
                //    store.Add(() => this._model.CittaCorrispondenza, "Specificare la città di corrispondenza");
                //}

                if (String.IsNullOrEmpty(this._model.CAPCorrispondenza))
                {
                    store.Add(() => this._model.CAPCorrispondenza, "Specificare il CAP di corrispondenza");
                }
            }
            */
            /*
            if (this.ImpostazioniStep.DettagliPf.TelefonoObbligatorio && String.IsNullOrEmpty(this._model.Telefono))
            {
                store.Add(() => this._model.Telefono, "Specificare il numero di telefono");
            }

            if (this.ImpostazioniStep.DettagliPf.CellulareObbligatorio && String.IsNullOrEmpty(this._model.Cellulare))
            {
                store.Add(() => this._model.Cellulare, "Specificare il numero di cellulare");
            }

            if (this.ImpostazioniStep.DettagliPf.FaxObbligatorio && String.IsNullOrEmpty(this._model.Fax))
            {
                store.Add(() => this._model.Fax, "Specificare il numero di fax");
            }

            if (this.ImpostazioniStep.DettagliPf.EmailObbligatoria && String.IsNullOrEmpty(this._model.EMail))
            {
                store.Add(() => this._model.EMail, "Specificare l'indirizzo email");
            }

            if (this.ImpostazioniStep.DettagliPf.PecObbligatoria && String.IsNullOrEmpty(this._model.EMailPEC))
            {
                store.Add(() => this._model.EMailPEC, "Specificare l'indirizzo PEC");
            }
            */
            if (this.ImpostazioniStep.EmailoPecObbligatori && (String.IsNullOrEmpty(this._model.EMailPEC) && String.IsNullOrEmpty(this._model.EMail)))
            {
                store.Add(() => this._model.EMailPEC, "Specificare l'indirizzo E-Mail o PEC");
                store.Add(() => this._model.EMail, "Specificare l'indirizzo E-Mail o PEC");
            }

            if (this.ImpostazioniStep.TelefonooCellulareObbligatori && (String.IsNullOrEmpty(this._model.Telefono) && String.IsNullOrEmpty(this._model.Cellulare)))
            {
                store.Add(() => this._model.Telefono, "Specificare il numero di telefono o cellulare");
                store.Add(() => this._model.Cellulare, "Specificare il numero di telefono o cellulare");
            }

            if (!this.ValidaEmail(this._model.EMail))
            {
                store.Add(() => this._model.EMail, $"Il campo E-Mail contiene un indirizzo non valido");
            }

            if (!this.ValidaEmail(this._model.EMailPEC))
            {
                store.Add(() => this._model.EMailPEC, $"Il campo PEC contiene un indirizzo non valido");
            }

            if (this.TipoSoggettoRichiedeDescrizioneEstesa(this._model.TipoSoggetto?.Value) && string.IsNullOrEmpty(this._model.DescrizioneEstesa))
            {
                store.Add(() => this._model.DescrizioneEstesa, FormMessageStore.Messages.CAMPO_OBBLIGATORIO);
            }

        }


        private async Task OnValidSubmitAsync()
        {

            if (!int.TryParse(this._model.Anagrafe_PK, out var idAnagrafica))
            {
                idAnagrafica = -1;
            }

            var anagrafica = await this.GetAnagraficaDomandaByIdAsync(idAnagrafica);

            var row = anagrafica.ToAnagrafeRow();

            var datiComuneNascita = await this.GetComuneDaIdAsync(this._model.ComuneNascita.Value);

            if (datiComuneNascita == null)
            {
                // await this.OnError.InvokeAsync("Il comune di nascita specificato non è valido");
                return;
            }

            row.TIPOANAGRAFE = "F";

            if (this._model.Titolo != null && !String.IsNullOrEmpty(this._model.Titolo.Value))
            {
                row.TITOLO = this._model.Titolo.Value;
            }

            row.NOMINATIVO = this._model.Cognome;
            row.NOME = this._model.Nome;

            row.SESSO = this._model.Sesso.Value;


            row.PROVINCIANASCITA = datiComuneNascita.SiglaProvincia;

            row.INDIRIZZO = this._model.Indirizzo;
            row.CITTA = this._model.Citta;
            row.CAP = this._model.CAP;

            row.INDIRIZZOCORRISPONDENZA = this._model.IndirizzoCorrispondenza;
            row.CITTACORRISPONDENZA = this._model.CittaCorrispondenza;
            row.CAPCORRISPONDENZA = this._model.CAPCorrispondenza;

            row.CODICEFISCALE = this._model.CodiceFiscale.ToUpper();
            row.TELEFONO = this._model.Telefono;
            row.TELEFONOCELLULARE = this._model.Cellulare;
            row.FAX = this._model.Fax;
            row.EMAIL = this._model.EMail;
            row.Pec = this._model.EMailPEC;

            row.SetColumnDateValue(nameof(row.DATANASCITA), this._model.DataDiNascita);

            // Cittadinanza
            var datiCittadinanza = await this.GetCittadinanzaDaIdAsync(this._model.Cittadinanza.Value);

            if (datiCittadinanza != null)
            {
                row.CODICECITTADINANZA = datiCittadinanza.Codice;
                row.IsCittadinoExtracomunitario = !datiCittadinanza.FlgPaeseComunitario;
            }

            // Residenza
            var datiComuneResidenza = await this.GetComuneDaIdAsync(this._model.ComuneResidenza.Value);

            if (datiComuneResidenza != null)
            {
                row.COMUNERESIDENZA = datiComuneResidenza.CodiceComune;
                row.PROVINCIA = datiComuneResidenza.SiglaProvincia;
            }

            if (this._model.ComuneCorrispondenza != null && !String.IsNullOrEmpty(this._model.ComuneCorrispondenza.Value))
            {
                datiComuneNascita = await this.GetComuneDaIdAsync(this._model.ComuneCorrispondenza.Value);

                if (datiComuneNascita != null)
                {
                    row.PROVINCIACORRISPONDENZA = datiComuneNascita.SiglaProvincia;
                    row.COMUNECORRISPONDENZA = datiComuneNascita.CodiceComune;
                }
            }

            row.CODCOMNASCITA = this._model.ComuneNascita.Value;

            row.TIPOSOGGETTO = this._model.TipoSoggetto == null ? -1 : Convert.ToInt32(this._model.TipoSoggetto.Value);

            var tipoSoggetto = this._tipiSoggettoRepository.GetById(row.TIPOSOGGETTO.Value);

            if (tipoSoggetto != null && tipoSoggetto.RichiedeDatiAlbo)
            {
                if (this._model.AlboProfessionale == null || String.IsNullOrEmpty(this._model.AlboProfessionale.Value))
                {
                    // await this.OnError.InvokeAsync("Per il tipo soggetto selezionato è necessario specificare l'albo professionale di appartenenza");
                    return;
                }

                if (String.IsNullOrEmpty(this._model.NumeroAlbo.Trim()))
                {
                    // await this.OnError.InvokeAsync("Per il tipo soggetto selezionato è necessario specificare il numero di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                string provincia_regione = string.Empty;

                if (this._model.ProvinciaAlbo != null && !string.IsNullOrEmpty(this._model.ProvinciaAlbo.Value.Trim()))
                    provincia_regione = this._model.ProvinciaAlbo.Value.Trim();

                if (this._model.RegioneAlbo != null && !string.IsNullOrEmpty(this._model.RegioneAlbo.Value.Trim()))
                    provincia_regione = this._model.RegioneAlbo.Value.Trim();

                if (String.IsNullOrEmpty(provincia_regione))
                {
                    // await this.OnError.InvokeAsync("Per il tipo soggetto selezionato è necessario specificare la provincia di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                row.IdAlbo = this._model.AlboProfessionale.Value;

                if (!String.IsNullOrEmpty(this._model.AlboProfessionale.Value))
                    row.DescrizioneAlbo = this._model.AlboProfessionale.Text;
                else
                    row.DescrizioneAlbo = "";

                row.NumeroAlbo = this._model.NumeroAlbo;
                row.ProvinciaAlbo = provincia_regione;
            }
            else
            {
                row.IdAlbo = String.Empty;
                row.DescrizioneAlbo = String.Empty;
                row.NumeroAlbo = String.Empty;
                row.ProvinciaAlbo = String.Empty;
            }

            row.DescrSoggetto = this._model.TipoSoggetto == null ? String.Empty : this._model.TipoSoggetto.Text;
            row.DescrizioneTipoSoggetto = this._model.DescrizioneEstesa;

            await this.OnAcceptEdit.InvokeAsync(AnagraficaDomanda.FromAnagrafeRow(row));
        }
    }
}