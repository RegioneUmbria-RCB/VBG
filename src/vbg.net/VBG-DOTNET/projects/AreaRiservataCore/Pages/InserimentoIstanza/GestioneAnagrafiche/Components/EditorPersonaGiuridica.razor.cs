using Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using Microsoft.AspNetCore.Components;
using System.ComponentModel.DataAnnotations;
using System.Linq.Expressions;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche.Components
{
    public partial class EditorPersonaGiuridica
    {
        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; } = default!;

        [Inject]
        public IFormeGiuridicheRepository _formeGiuridicheRepository { get; set; } = default!;

        [Inject]
        public IInpsInailService _inpsInailRepository { get; set; } = default!;

        [Parameter, EditorRequired]
        public int? CodiceIntervento { get; set; }

        [Parameter]
        public EventCallback OnBeforeAcceptEdit { get; set; }

        [Parameter, EditorRequired]
        public EventCallback<AnagraficaDomanda> OnAcceptEdit { get; set; }

        [Parameter]
        public PresentazioneIstanzaDbV2.ANAGRAFERow? InitialModel { get; set; }

        private bool showDescrizioneEstesa = false;
        private Model _model = new();

        public class Model
        {
            //[Required]
            public DropDownItem? TipoSoggetto { get; set; } // { Codice = -1, Descrizione = "" };

            [MaxLength(128)]
            public string? DescrizioneEstesa { get; set; }

            //[Required]
            public DropDownItem? FormaGiuridica { get; set; }

            //[Required]
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

        private List<DropDownItem> FormeGiuridicheDataItems { get; set; } = new List<DropDownItem>();

        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            this.BindFormeGiuridiche();
            //BindTitoli();
            //BindSesso();
            //BindCittadinanze();
            //BindElenchiProfessionali();
            await this.DataBindAsync();
        }


        private async Task OnValidSubmitAsync()
        {

            if (!int.TryParse(this._model.Anagrafe_PK, out var idAnagrafica))
            {
                idAnagrafica = -1;
            }

            var anagrafica = await this.GetAnagraficaDomandaByIdAsync(idAnagrafica);
            var row = anagrafica.ToAnagrafeRow();

            row.TIPOANAGRAFE = "G";

            row.TIPOSOGGETTO = this._model.TipoSoggetto == null ? -1 : Convert.ToInt32(this._model.TipoSoggetto.Value);
            row.DescrSoggetto = this._model.TipoSoggetto?.Text ?? "";
            row.DescrizioneTipoSoggetto = this._model.DescrizioneEstesa;

            row.NOMINATIVO = this._model.RagioneSociale;
            row.CODICEFISCALE = this._model.CodiceFiscale!.ToUpper();
            row.PartitaIva = this._model.PartitaIva;

            if (int.TryParse(this._model.FormaGiuridica?.Value ?? "", out var idFormaGiuridica))
                row.FORMAGIURIDICA = idFormaGiuridica;

            row.DescrSoggetto = this._model.TipoSoggetto?.Text ?? "";

            row.INDIRIZZO = this._model.Indirizzo;
            row.CITTA = this._model.Citta;
            row.CAP = this._model.CAP;
            row.INDIRIZZOCORRISPONDENZA = this._model.IndirizzoCorrispondenza;
            row.CITTACORRISPONDENZA = this._model.CittaCorrispondenza;
            row.CAPCORRISPONDENZA = this._model.CAPCorrispondenza;
            row.TELEFONO = this._model.Telefono;
            row.TELEFONOCELLULARE = this._model.Cellulare;
            row.FAX = this._model.Fax;
            row.EMAIL = this._model.EMail;
            row.Pec = this._model.EMailPEC;

            row.REGDITTE = this._model.CCIAANumero;
            row.REGTRIB = this._model.RegTribNumero;
            row.NUMISCRREA = this._model.REANumero;
            row.PROVINCIAREA = this._model.REAComune?.Value ?? "";

            row.SetColumnDateValue(nameof(row.DATANOMINATIVO), this._model.DataNominativo);
            row.SetColumnDateValue(nameof(row.DATAREGDITTE), this._model.CCIAAData);
            row.SetColumnDateValue(nameof(row.DATAREGTRIB), this._model.RegTribData);
            row.SetColumnDateValue(nameof(row.DATAISCRREA), this._model.READata);

            // Validazione comuni e provincie
            var comuneCciaa = await this.GetComuneDaIdAsync(this._model.CCIAAComune?.Value);

            row.CODPROVREGDITTE = comuneCciaa?.SiglaProvincia ?? "";
            row.CODCOMREGDITTE = comuneCciaa?.CodiceComune ?? "";

            var comuneRegTrib = await this.GetComuneDaIdAsync(this._model.RegTribComune?.Value);

            row.CODPROVREGTRIB = comuneRegTrib?.SiglaProvincia ?? "";
            row.CODCOMREGTRIB = comuneRegTrib?.CodiceComune ?? "";

            var comuneSede = await this.GetComuneDaIdAsync(this._model.ComuneSedeLegale?.Value);

            row.PROVINCIA = comuneSede?.SiglaProvincia ?? "";
            row.COMUNERESIDENZA = comuneSede?.CodiceComune ?? "";

            var comuneCorrispondenza = await this.GetComuneDaIdAsync(this._model.ComuneCorrispondenza?.Value);

            row.PROVINCIACORRISPONDENZA = comuneCorrispondenza?.SiglaProvincia;
            row.COMUNECORRISPONDENZA = comuneCorrispondenza?.CodiceComune;

            // Dati INPS
            row.MatricolaInps = this._model.INPSNumero;
            row.CodSedeIscrizioneInps = this._model.INPSComune?.Value ?? "";
            row.DesSedeIscrizioneInps = this._model.INPSComune?.Text ?? "";


            // Dati INAIL
            row.MatricolaInail = this._model.INAILNumero;
            row.CodSedeIscrizioneInail = this._model.INAILComune?.Value ?? "";
            row.DesSedeIscrizioneInail = this._model.INAILComune?.Text ?? "";

            await this.OnAcceptEdit.InvokeAsync(AnagraficaDomanda.FromAnagrafeRow(row));
        }

        private async Task ValidationCallbackAsync(FormMessageStore store)
        {
            if (this._model is null)
            {
                return;
            }

            /*
            this.VerificaCompilazioneCampo(store, () => this._model.TipoSoggetto, true);
            this.VerificaCompilazioneCampo(store, () => this._model.CodiceFiscale, true);
            this.VerificaCompilazioneCampo(store, () => this._model.PartitaIva, this.ImpostazioniStep.DettagliPg.PartitaIvaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.DataNominativo, this.ImpostazioniStep.DettagliPg.DataCostituzioneObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.ComuneSedeLegale.Value, this.ImpostazioniStep.DettagliPg.SedeLegaleObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.Indirizzo, this.ImpostazioniStep.DettagliPg.SedeLegaleObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.CAP, this.ImpostazioniStep.DettagliPg.SedeLegaleObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.CCIAANumero, this.ImpostazioniStep.DettagliPg.CciaaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.CCIAAData, this.ImpostazioniStep.DettagliPg.CciaaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.CCIAAComune.Value, this.ImpostazioniStep.DettagliPg.CciaaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.RegTribNumero, this.ImpostazioniStep.DettagliPg.RegTribObbligatorio);
            this.VerificaCompilazioneCampo(store, () => this._model.RegTribData, this.ImpostazioniStep.DettagliPg.RegTribObbligatorio);
            this.VerificaCompilazioneCampo(store, () => this._model.RegTribComune.Value, this.ImpostazioniStep.DettagliPg.RegTribObbligatorio);
            this.VerificaCompilazioneCampo(store, () => this._model.REANumero, this.ImpostazioniStep.DettagliPg.ReaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.READata, this.ImpostazioniStep.DettagliPg.ReaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.REAComune.Value, this.ImpostazioniStep.DettagliPg.ReaObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.INPSNumero, this.ImpostazioniStep.DettagliPg.InpsObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.INPSComune.Value, this.ImpostazioniStep.DettagliPg.InpsObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.INAILNumero, this.ImpostazioniStep.DettagliPg.InailObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.INAILComune.Value, this.ImpostazioniStep.DettagliPg.InailObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.Telefono, this.ImpostazioniStep.DettagliPg.TelefonoObbligatorio);
            this.VerificaCompilazioneCampo(store, () => this._model.Cellulare, this.ImpostazioniStep.DettagliPg.CellulareObbligatorio);
            this.VerificaCompilazioneCampo(store, () => this._model.Fax, this.ImpostazioniStep.DettagliPg.FaxObbligatorio);
            this.VerificaCompilazioneCampo(store, () => this._model.EMail, this.ImpostazioniStep.DettagliPg.EmailObbligatoria);
            this.VerificaCompilazioneCampo(store, () => this._model.EMailPEC, this.ImpostazioniStep.DettagliPg.PecObbligatoria);
            */
            if (this.ImpostazioniStep.EmailoPecObbligatori && (String.IsNullOrEmpty(this._model.EMailPEC) && String.IsNullOrEmpty(this._model.EMail)))
            {
                var errMsg = "Specificare alternativamente un indirizzo E-Mail o un indirizzo PEC";
                store.Add(() => this._model.EMail, errMsg);
                store.Add(() => this._model.EMailPEC, errMsg);
            }

            if (this.ImpostazioniStep.TelefonooCellulareObbligatori && (String.IsNullOrEmpty(this._model.Telefono) && String.IsNullOrEmpty(this._model.Cellulare)))
            {
                var errMsg = "Specificare alternativamente un numero di telefono o un numero di celulare";
                store.Add(() => this._model.Telefono, errMsg);
                store.Add(() => this._model.Cellulare, errMsg);
            }

            await Task.CompletedTask;
        }

        private async Task DataBindAsync()
        {
            if (this.InitialModel != null)
            {
                //var selectedCitta = TitoliDataItems.Find(x => x.Value == InitialModel.TITOLO);
                //var selectedSesso = SessoDataItems.Find(x => x.Value == InitialModel.SESSO);
                //var selectedCittadinanza = CittadinanzaDataItems.Find(x => x.Value == InitialModel.CODICECITTADINANZA.ToString());

                this._model = new Model();

                this._model.RagioneSociale = this.InitialModel.NOMINATIVO;
                this._model.Indirizzo = this.InitialModel.INDIRIZZO;
                this._model.Citta = this.InitialModel.CITTA;
                this._model.CAP = this.InitialModel.CAP;
                this._model.Fax = this.InitialModel.FAX;
                this._model.EMail = this.InitialModel.EMAIL;
                this._model.EMailPEC = this.InitialModel.Pec;
                this._model.CodiceFiscale = this.InitialModel.CODICEFISCALE;
                this._model.PartitaIva = this.InitialModel.PartitaIva;
                this._model.CCIAANumero = this.InitialModel.REGDITTE;
                this._model.RegTribNumero = this.InitialModel.REGTRIB;
                this._model.REANumero = this.InitialModel.NUMISCRREA;
                this._model.IndirizzoCorrispondenza = this.InitialModel.INDIRIZZOCORRISPONDENZA;
                this._model.CittaCorrispondenza = this.InitialModel.CITTACORRISPONDENZA;
                this._model.CAPCorrispondenza = this.InitialModel.CAPCORRISPONDENZA;
                this._model.Telefono = this.InitialModel.TELEFONO;
                this._model.Cellulare = this.InitialModel.TELEFONOCELLULARE;

                // Residenza

                this._model.ComuneSedeLegale = await this.ComuneToAutocompleteResultAsync(this.InitialModel.COMUNERESIDENZA) ?? new AutocompleteFormResult();
                this._model.ComuneCorrispondenza = await this.ComuneToAutocompleteResultAsync(this.InitialModel.COMUNECORRISPONDENZA) ?? new AutocompleteFormResult();

                this._model.DataNominativo = this.InitialModel.IsDATANOMINATIVONull() ? null : this.InitialModel.DATANOMINATIVO;
                this._model.CCIAAData = this.InitialModel.IsDATAREGDITTENull() ? null : this.InitialModel.DATAREGDITTE;
                this._model.RegTribData = this.InitialModel.IsDATAREGTRIBNull() ? null : this.InitialModel.DATAREGTRIB;
                this._model.READata = (this.InitialModel.IsDATAISCRREANull()) ? null : this.InitialModel.DATAISCRREA;


                // CCIAA
                this._model.CCIAAComune = await this.ComuneToAutocompleteResultAsync(this.InitialModel.CODCOMREGDITTE) ?? new AutocompleteFormResult();

                this._model.RegTribComune = await this.ComuneToAutocompleteResultAsync(this.InitialModel.CODCOMREGTRIB) ?? new AutocompleteFormResult();

                this._model.REAComune = await this.ProvinciaToAutocompleteResultAsync(this.InitialModel.PROVINCIAREA) ?? new AutocompleteFormResult();

                this._model.TipoSoggetto = this.TipiSoggetto.FirstOrDefault(x => x.Value == this.InitialModel.TIPOSOGGETTO.ToString()) ?? new DropDownItem();

                this.MostraNasconiDescrizioneEstesa(this._model.TipoSoggetto.Value);

                this._model.DescrizioneEstesa = this.InitialModel.DescrizioneTipoSoggetto;

                if (!this.InitialModel.IsFORMAGIURIDICANull())
                {
                    this._model.FormaGiuridica = this.FormeGiuridicheDataItems
                                                     .FirstOrDefault(x => x.Value == this.InitialModel.FORMAGIURIDICA.ToString()) ?? new DropDownItem();
                }

                this._model.Anagrafe_PK = this.InitialModel.ANAGRAFE_PK.ToString();

                // Dati INPS
                this._model.INPSNumero = this.InitialModel.MatricolaInps;

                //string comuneINPS = InitialModel.CodSedeIscrizioneInps;

                //if (!String.IsNullOrEmpty(comuneINPS))
                //{
                //    var datiComune = await getComuneDataItems(comuneINPS);

                //    if (datiComune != null)
                //    {
                //        model.INPSComune = new AutocompleteFormResult(datiComune.CodiceComune, datiComune.Comune + " (" + datiComune.SiglaProvincia + ")");
                //    }
                //}
                //else
                //{
                //    model.INPSComune = new AutocompleteFormResult("", "");
                //}
                this._model.INPSComune = new AutocompleteFormResult(this.InitialModel.CodSedeIscrizioneInps, this.InitialModel.DesSedeIscrizioneInps);


                // Dati INAIL
                this._model.INAILNumero = this.InitialModel.MatricolaInail;

                //string comuneINAIL = InitialModel.CodSedeIscrizioneInail;

                //if (!String.IsNullOrEmpty(comuneINAIL))
                //{
                //    var datiComune = await getComuneDataItems(comuneINAIL);

                //    if (datiComune != null)
                //    {
                //        model.INAILComune = new AutocompleteFormResult(datiComune.CodiceComune, datiComune.Comune + " (" + datiComune.SiglaProvincia + ")");
                //    }
                //}
                //else
                //{
                //    model.INAILComune = new AutocompleteFormResult("", "");
                //}
                this._model.INAILComune = new AutocompleteFormResult(this.InitialModel.CodSedeIscrizioneInail, this.InitialModel.DesSedeIscrizioneInail);
            }
        }

        private void BindFormeGiuridiche()
        {
            if (this.FormeGiuridicheDataItems.Count > 0)
                return;

            var formeGiuridiche = this._formeGiuridicheRepository.GetList();

            foreach (var item in formeGiuridiche)
            {
                this.FormeGiuridicheDataItems.Add(new DropDownItem(item.CodiceFormaGiuridica, item.FormaGiuridica));
            }
        }

        private Task<IEnumerable<SedeInpsDto>> FindSediInpsAsync(string match)
        {
            return Task.FromResult(this._inpsInailRepository.GetSediInps(match));
        }

        private Task<IEnumerable<SedeInailDto>> FindSediInailAsync(string match)
        {
            return Task.FromResult(this._inpsInailRepository.GetSediInail(match));
        }

        private AutocompleteFormResult? InpsToAutocompleteResult(SedeInpsDto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.Codice, dati.Descrizione);
        }

        private AutocompleteFormResult? InailToAutocompleteResult(SedeInailDto dati)
        {
            return dati == null ? null : new AutocompleteFormResult(dati.Codice, dati.Descrizione);
        }



        private bool TipoSoggettoRichiedeDescrizioneEstesa(string? codiceTipoSoggetto)
        {
            if (String.IsNullOrEmpty(codiceTipoSoggetto))
            {
                return false;
            }

            if (!int.TryParse(codiceTipoSoggetto, out var idTipoSoggetto))
            {
                return false;
            }

            var tipoSoggetto = this._tipiSoggettoRepository.GetById(idTipoSoggetto);

            return tipoSoggetto.RichiedeSpecificaDescrizione;
        }

        private void MostraNasconiDescrizioneEstesa(string codiceTipoSoggetto)
        {
            this.showDescrizioneEstesa = false;

            if (!this.TipoSoggettoRichiedeDescrizioneEstesa(codiceTipoSoggetto))
            {
                this._model.DescrizioneEstesa = string.Empty;
                return;
            }

            this.showDescrizioneEstesa = true;
        }

        /*
        private async Task ValidaComuneAsync(string? comune, string erroreComuneNonTrovato, Action<DatiComuneCompatto> comuneTrovatoCallback)
        {
            if (String.IsNullOrEmpty(comune))
            {
                return;
            }

            var data = await this.GetComuneDaIdAsync(comune);

            if (data == null)
            {
                throw new FormValidationException(erroreComuneNonTrovato);
            }

            comuneTrovatoCallback(data);
        }
        */
        //private async Task OnBtnConfirm()
        //{


        //}

        //private async Task VerificaCompilazioneComune(FormMessageStore store, Expression<Func<object>> codiceComuneExpr, string messaggioErrore)
        //{
        //    var value = codiceComuneExpr.Compile().Invoke()?.ToString();

        //    if (String.IsNullOrEmpty(value))
        //    {
        //        store.Add(codiceComuneExpr, messaggioErrore);
        //        return;
        //    }

        //    var data = await this.GetComuneDaIdAsync(value);

        //    if (data == null)
        //    {
        //        store.Add(codiceComuneExpr, messaggioErrore);
        //    }
        //}

        private bool VerificaCompilazioneCampo(FormMessageStore store, Expression<Func<object>> value, bool obbligatorio, string messaggioErrore = FormMessageStore.Messages.CAMPO_OBBLIGATORIO)
        {
            if (!obbligatorio)
            {
                return true;
            }

            var result = value.Compile().Invoke();

            if (result == null)
            {
                store.Add(value, messaggioErrore);

                return false;
            }

            if (result.GetType() == typeof(string))
            {
                if (String.IsNullOrEmpty((string)result))
                {
                    store.Add(value, messaggioErrore);
                    return false;
                }
            }

            if (result.GetType() == typeof(DropDownItem))
            {
                if (String.IsNullOrEmpty(((DropDownItem)result).Value))
                {
                    store.Add(value, messaggioErrore);
                    return false;
                }
            }

            return true;
        }

        private async Task CopiaDatiResienzaInCorrispondenzaAsync()
        {
            this._model.ComuneCorrispondenza = this._model.ComuneSedeLegale;
            this._model.IndirizzoCorrispondenza = this._model.Indirizzo;
            this._model.CittaCorrispondenza = this._model.Citta;
            this._model.CAPCorrispondenza = this._model.CAP;

            await Task.CompletedTask;
        }

    }
}