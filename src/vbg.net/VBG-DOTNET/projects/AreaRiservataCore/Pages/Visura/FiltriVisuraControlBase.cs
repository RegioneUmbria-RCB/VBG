using AreaRiservataCore.Shared;
using AreaRiservataCore.Shared.Localizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.Sigepro.FrontEnd.CoreServices.Visura;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.Visura;
using Init.SIGePro.Manager.DTO.Visura.V1;
using log4net;
using Microsoft.AspNetCore.Components;
using System.Globalization;
using System.Linq.Expressions;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.Visura
{
    public class FiltriVisuraControlBase : BaseSharedPage
    {
        public interface IDynamicComponentDescriptor
        {
            Type Type { get; }
            Dictionary<string, object> Parameters { get; }
        }
        public class DynamicComponentDescriptor<T> : IDynamicComponentDescriptor
        {
            public Type Type => typeof(T);
            public Dictionary<string, object> Parameters { get; set; } = new();
        }



        [Parameter, EditorRequired]
        public string IdComune { get; set; }

        protected string Software => this._softwareResolver.Software;

        [Parameter]
        public FiltriVisuraControlModel? Model { get; set; } = new();

        [Inject]
        private ISoftwareResolver _softwareResolver { get; set; } = default!;

        [Inject]
        private IComuniAssociatiService _comuniService { get; set; } = default!;

        [Inject]
        private IStatiIstanzaRepository _statiIstanzaRepository { get; set; } = default!;

        [Inject]
        protected IRisorseTestualiService _risorseTestualiService { get; set; } = default!;
        [Inject]
        public IInterventiRepository _alberoProcRepository { get; set; } = default!;
        [Inject]
        public IStradarioRepository _stradarioRepository { get; set; } = default!;

        protected IList<DropDownItem> mesiDataItems { get; set; }
        protected IList<StatoIstanzaDto> statoDataItems { get; set; }
        protected IList<DatiComuneCompatto> comuneDataItems { get; set; }
        protected bool contieneComuniAssociati { get => this.comuneDataItems?.Count > 0; }

        protected IFiltriVisuraControlProvider m_provider;
        private ILog m_logger;

        private Dictionary<int, IDynamicComponentDescriptor> _dictionaryControlli = new();
        protected Dictionary<int, IDynamicComponentDescriptor> SortedDictionary = new();

        protected void OnInit(IFiltriVisuraControlProvider provider, Type loggerClassType)
        {
            this.m_logger = LogManager.GetLogger(loggerClassType);
            this.mesiDataItems = this.GetMesi();
            this.comuneDataItems = this._comuniService.GetComuniAssociati().ToList();
            this.statoDataItems = this._statiIstanzaRepository.GetList(this.Software);
            this.m_provider = provider;
            this.SetModelValues();
            this._dictionaryControlli = this.FillDictionary();
            this.CostruisciControlli();
        }

        public virtual void SetModelValues()
        {
            // Method intentionally left empty.
        }

        private IList<DropDownItem> GetMesi()
        {
            IList<DropDownItem> retVal = new List<DropDownItem>();

            for (var i = 1; i < 13; i++)
            {
                var monthName = new DateTime(DateTime.Now.Year, i, 1).ToString("MMMM", CultureInfo.CreateSpecificCulture("it"));

                retVal.Add(new DropDownItem(i.ToString().PadLeft(2, '0'), monthName) { });
            }

            return retVal;
        }

        private async Task<IEnumerable<AutocompleteStradarioResultItem>> GetStradarioDataItemsAsync(string value)
        {
            var listaIndirizzi = await this._stradarioRepository.GetByMatchParzialeAsyncIncludiDisabilitateAsync(this.Model.CodiceComune?.Comune, "", value);
            var q = listaIndirizzi.Select(s => new AutocompleteStradarioResultItem
            {
                Codice = s.CodiceStradario,
                Descrizione = s.NomeVia,
                CodViario = s.CodViario
            });

            var rVal = new AutocompleteStradarioResult
            {
                ItemCount = q.Count(),
                Items = q.Take(30).ToArray()
            };

            return q;
        }

        private async Task<IEnumerable<InterventoBreveDto>> GetInterventoDataItemsAsync(string value)
        {
            // InterventoBreveDto[] response = await this.interventiClient.RicercaTestualeAsync(this.IdComune, this.Software, value, 9999, "", "", true, false);

            // return response.ToList();

            IAmbitoRicercaIntervento ambitoRicerca = new AmbitoRicercaAreaRiservata(this._authenticationDataResolver?.DatiAutenticazione?.DatiUtente?.UtenteTester ?? false);


            if (value.Length < 2)
                return new InterventoBreveDto[0];

            return await Task.FromResult(this._alberoProcRepository.RicercaTestuale2(value, 100, "", "", ambitoRicerca).ToArray());
        }

        private Dictionary<int, IDynamicComponentDescriptor> FillDictionary()
        {
            var retVal = new Dictionary<int, IDynamicComponentDescriptor>();

            // annoIstanza
            Expression<Func<int?>> exprAnno = () => this.Model.Anno;

            var annoIstanza = new DynamicComponentDescriptor<NumericInputForm<int?>>();
            annoIstanza.Parameters = new Dictionary<string, object>
                {
                    { nameof(NumericInputForm<int?>.Id), "anno" },
                    { nameof(NumericInputForm<int?>.ResourceId), "visura.anno" },
                    { nameof(NumericInputForm<int?>.Value), this.Model.Anno },
                    { nameof(NumericInputForm<int?>.ValueChanged), EventCallback.Factory.Create(this, (int? s) =>
                        {
                            this.Model.Anno = s;
                            annoIstanza.Parameters[nameof(NumericInputForm<int?>.Value)] = s;
                        })},
                    { nameof(NumericInputForm<int?>.ValueExpression), exprAnno },
                    { nameof(NumericInputForm<int?>.ValidationFor), exprAnno }
                };

            // meseIstanza
            Expression<Func<DropDownItem>> exprMese = () => this.Model.Mese;
            var meseIstanza = new DynamicComponentDescriptor<GenericDropDownForm<DropDownItem>>();
            meseIstanza.Parameters = new Dictionary<string, object>
            {
                { nameof(GenericDropDownForm<DropDownItem>.Id), "mese" },
                { nameof(GenericDropDownForm<DropDownItem>.ResourceId), "visura.mese" },
                { nameof(GenericDropDownForm<DropDownItem>.ValueField), "Value" },
                { nameof(GenericDropDownForm<DropDownItem>.TextField), "Text" },
                { nameof(GenericDropDownForm<DropDownItem>.Items), this.mesiDataItems },
                { nameof(GenericDropDownForm<DropDownItem>.ShowEmptyItem), true },
                { nameof(GenericDropDownForm<DropDownItem>.Selector), (DropDownItem x) => x.Text },
                { nameof(GenericDropDownForm<DropDownItem>.Value), this.Model.Mese },
                { nameof(GenericDropDownForm<DropDownItem>.OnChanged), EventCallback.Factory.Create(this, (DropDownItem s) =>
                    {
                        this.Model.Mese = s;
                        meseIstanza.Parameters[nameof(GenericDropDownForm<DropDownItem>.Value)] = s;
                    })},
                { nameof(GenericDropDownForm<DropDownItem>.ValueExpression), exprMese },
                { nameof(GenericDropDownForm<DropDownItem>.ValidationFor), exprMese }
            };

            // statoIstanza
            Expression<Func<StatoIstanzaDto>> exprStatoIstanza = () => this.Model.StatoIstanza;
            var statoIstanza = new DynamicComponentDescriptor<GenericDropDownForm<StatoIstanzaDto>>();
            statoIstanza.Parameters = new Dictionary<string, object>
            {
                { nameof(GenericDropDownForm<StatoIstanzaDto>.Id), "statoIstanza" },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.ValueField), "CodiceStato" },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.TextField), "Stato" },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.Items), this.statoDataItems },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.ShowEmptyItem), true },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.Selector), (StatoIstanzaDto x) => x.Stato },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.ResourceId), "visura.stati_istanze" },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.Value), this.Model.StatoIstanza },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.OnChanged), EventCallback.Factory.Create(this, (StatoIstanzaDto s) =>
                    {
                        this.Model.StatoIstanza = s;
                        statoIstanza.Parameters[nameof(GenericDropDownForm<StatoIstanzaDto>.Value)] = s;
                    })},
                { nameof(GenericDropDownForm<StatoIstanzaDto>.ValueExpression), exprStatoIstanza },
                { nameof(GenericDropDownForm<StatoIstanzaDto>.ValidationFor), exprStatoIstanza }
            };

            // oggetto
            Expression<Func<string>> exprOggetto = () => this.Model.Oggetto;
            var oggetto = new DynamicComponentDescriptor<TextInputForm>();
            oggetto.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "oggetto" },
                { nameof(TextInputForm.ResourceId), "visura.oggetto" },
                { nameof(TextInputForm.MaxLength), 80 },
                { nameof(TextInputForm.Value), this.Model.Oggetto },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.Oggetto = s;
                        oggetto.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprOggetto },
                { nameof(TextInputForm.ValidationFor), exprOggetto }
            };

            // civico
            Expression<Func<string>> exprCivico = () => this.Model.Civico;
            var civico = new DynamicComponentDescriptor<TextInputForm>();
            civico.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "civico" },
                { nameof(TextInputForm.ResourceId), "visura.civico" },
                { nameof(TextInputForm.MaxLength), 8 },
                { nameof(TextInputForm.Value), this.Model.Civico },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.Civico = s;
                        civico.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprCivico },
                { nameof(TextInputForm.ValidationFor), exprCivico }
            };

            // codiceIstanza
            Expression<Func<string>> exprCodiceIstanza = () => this.Model.CodiceIstanza;
            var codiceIstanza = new DynamicComponentDescriptor<TextInputForm>();
            codiceIstanza.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "codiceIstanza" },
                { nameof(TextInputForm.ResourceId), "visura.codice_Istanza" },
                { nameof(TextInputForm.MaxLength), 15 },
                { nameof(TextInputForm.Value), this.Model.CodiceIstanza },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.CodiceIstanza = s;
                        codiceIstanza.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprCodiceIstanza },
                { nameof(TextInputForm.ValidationFor), exprCodiceIstanza }
            };

            // numAutorizzazione
            Expression<Func<string>> exprNumeroAutorizzazione = () => this.Model.NumeroAutorizzazione;
            var numAutorizzazione = new DynamicComponentDescriptor<TextInputForm>();
            numAutorizzazione.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "numAutorizzazione" },
                { nameof(TextInputForm.ResourceId), "visura.numero_autorizzazione" },
                { nameof(TextInputForm.MaxLength), 8 },
                { nameof(TextInputForm.Value), this.Model.NumeroAutorizzazione },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.NumeroAutorizzazione = s;
                        numAutorizzazione.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprNumeroAutorizzazione },
                { nameof(TextInputForm.ValidationFor), exprNumeroAutorizzazione }
            };

            // numProtocollo
            Expression<Func<string>> exprNumeroProtocollo = () => this.Model.NumeroProtocollo;
            var numProtocollo = new DynamicComponentDescriptor<TextInputForm>();
            numProtocollo.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "numProtocollo" },
                { nameof(TextInputForm.ResourceId), "visura.numero_protocollo" },
                { nameof(TextInputForm.MaxLength), 30 },
                { nameof(TextInputForm.Value), this.Model.NumeroProtocollo },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.NumeroProtocollo = s;
                        numProtocollo.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprNumeroProtocollo },
                { nameof(TextInputForm.ValidationFor), exprNumeroProtocollo }
            };

            // stradario
            Expression<Func<AutocompleteFormResult>> exprStradario = () => this.Model.Stradario;
            var stradario = new DynamicComponentDescriptor<AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>>();
            stradario.Parameters = new Dictionary<string, object>
            {
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.Id), "stradario" },
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ResourceId), "visura.localizzazione" },
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.SearchMethod), this.GetStradarioDataItemsAsync },
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ConvertMethod), (AutocompleteStradarioResultItem x) => x == null ? null : new AutocompleteFormResult(x.CodViario, x.Descrizione) },
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.Value), this.Model.Stradario },
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ValueChanged), EventCallback.Factory.Create<AutocompleteFormResult>(this, (AutocompleteFormResult s) =>
                    {
                        this.Model.Stradario = s;
                        stradario.Parameters[nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.Value)] = s;
                    })},
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ValueExpression), exprStradario },
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.TextField), nameof(AutocompleteStradarioResultItem.Descrizione) },
                //{ nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ValueField), "Value");

                //parameters.Add(nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ItemFormatExpression), (RenderFragment<AutocompleteStradarioResultItem>)((item) => builder =>
                //{
                //    builder.AddContent(1, item.Descrizione);
                //}));
                { nameof(AutocompleteForm<AutocompleteStradarioResultItem, AutocompleteFormResult>.ItemFormatExpression), ((AutocompleteStradarioResultItem item) => item.Descrizione) }
            };

            // comuneLocalizzazione
            Expression<Func<DatiComuneCompatto>> exprCodiceComune = () => this.Model.CodiceComune;
            var comuneLocalizzazione = new DynamicComponentDescriptor<GenericDropDownForm<DatiComuneCompatto>>();
            comuneLocalizzazione.Parameters = new Dictionary<string, object>
            {
                { nameof(GenericDropDownForm<DatiComuneCompatto>.Id), "comuneLocalizzazione" },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.Label), "Comune" },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.ValueField), "CodiceComune" },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.TextField), "Comune" },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.Value), this.Model.CodiceComune },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.Items), this.comuneDataItems },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.ShowEmptyItem), true },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.Selector), (DatiComuneCompatto x) => x.Comune },
                { nameof(GenericDropDownForm<DatiComuneCompatto>.OnChanged), EventCallback.Factory.Create(this, (DatiComuneCompatto s) =>
                    {
                        this.Model.CodiceComune = s;
                        comuneLocalizzazione.Parameters[nameof(GenericDropDownForm<DatiComuneCompatto>.Value)] = s;
                    })},
                { nameof(GenericDropDownForm<DatiComuneCompatto>.ValueExpression), exprCodiceComune },
            };

            // fabbricato
            Expression<Func<string>> exprFabbricato = () => this.Model.Fabbricato;
            var fabbricato = new DynamicComponentDescriptor<TextInputForm>();
            fabbricato.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "fabbricato" },
                { nameof(TextInputForm.ResourceId), "visura.fabbricato" },
                { nameof(TextInputForm.MaxLength), 10 },
                { nameof(TextInputForm.Value), this.Model.Fabbricato },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.Fabbricato = s;
                        fabbricato.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprFabbricato },
                { nameof(TextInputForm.ValidationFor), exprFabbricato }
            };

            // dataProtocollo
            Expression<Func<DateTime?>> exprDataProtocollo = () => this.Model.DataProtocollo;
            var dataProtocollo = new DynamicComponentDescriptor<DateInputForm>();
            dataProtocollo.Parameters = new Dictionary<string, object>
            {
                { nameof(DateInputForm.Id), "dataProtocollo" },
                { nameof(DateInputForm.ResourceId), "visura.data_protocollo" },
                { nameof(DateInputForm.Value), this.Model.DataProtocollo },
                { nameof(DateInputForm.ValueChanged), EventCallback.Factory.Create(this, (DateTime? d) =>
                    {
                        this.Model.DataProtocollo = d;
                        dataProtocollo.Parameters[nameof(DateInputForm.Value)] = d;
                    })},
                { nameof(DateInputForm.ValueExpression), exprDataProtocollo },
                { nameof(DateInputForm.ValidationFor), exprDataProtocollo }
            };

            // datiCatasto
            Expression<Func<DatiCatasto>> exprDatiCatasto = () => this.Model.DatiCatasto;
            var datiCatasto = new DynamicComponentDescriptor<DatiCatastaliControl>();
            datiCatasto.Parameters = new Dictionary<string, object>
            {
                { nameof(DatiCatastaliControl.Id), "datiCatasto" },
                { nameof(DatiCatastaliControl.Model), this.Model.DatiCatasto },
                { nameof(DatiCatastaliControl.OnChanged), EventCallback.Factory.Create(this, (DatiCatasto d) =>
                    {
                        this.Model.DatiCatasto = d;
                        //_datiCatasto.Parameters[nameof(DatiCatastaliControl.Value)] = s;
                    })}
            };

            // richiedente
            Expression<Func<string>> exprRichiedente = () => this.Model.Richiedente;
            var richiedente = new DynamicComponentDescriptor<TextInputForm>();
            richiedente.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "richiedente" },
                { nameof(TextInputForm.ResourceId), "visura.richiedente" },
                { nameof(TextInputForm.Value), this.Model.Richiedente },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.Richiedente = s;
                        richiedente.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprRichiedente },
                { nameof(TextInputForm.ValidationFor), exprRichiedente }
            };

            // tipoIntervento
            Expression<Func<AutocompleteFormResult>> exprTipoIntervento = () => this.Model.TipoIntervento;
            var intervento = new DynamicComponentDescriptor<AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>>();
            intervento.Parameters = new Dictionary<string, object>
            {
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.Id), "tipoIntervento" },
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.ResourceId), "visura.tipo_intervento" },
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.SearchMethod), this.GetInterventoDataItemsAsync },
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.ConvertMethod), (InterventoBreveDto x) => x == null ? null : new AutocompleteFormResult(x.Codice.ToString(), x.Descrizione) },
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.Value), this.Model.TipoIntervento },
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.ValueChanged), EventCallback.Factory.Create<AutocompleteFormResult>(this, (AutocompleteFormResult s) =>
                    {
                        this.Model.TipoIntervento = s;
                        intervento.Parameters[nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.Value)] = s;
                    })},
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.ValueExpression), exprTipoIntervento },
                //parameters.Add(nameof(AutocompleteForm<InterventoBreveDto, string>.ItemFormatExpression), (RenderFragment<InterventoBreveDto>)((item) => builder =>
                //{
                //    builder.AddContent(1, item.Descrizione);
                //}));
                { nameof(AutocompleteForm<InterventoBreveDto, AutocompleteFormResult>.ItemFormatExpression), ((InterventoBreveDto item) => item.Descrizione) }
            };

            // posizioneArchivio
            Expression<Func<string>> exprPosizioneArchivio = () => this.Model.PosizioneArchivio;
            var posizioneArchivio = new DynamicComponentDescriptor<TextInputForm>();
            posizioneArchivio.Parameters = new Dictionary<string, object>
            {
                { nameof(TextInputForm.Id), "posizioneArchivio" },
                { nameof(TextInputForm.ResourceId), "visura.posizione_in_archivio" },
                { nameof(TextInputForm.MaxLength), 100 },
                { nameof(TextInputForm.Value), this.Model.PosizioneArchivio },
                { nameof(TextInputForm.ValueChanged), EventCallback.Factory.Create(this, (string s) =>
                    {
                        this.Model.PosizioneArchivio = s;
                        posizioneArchivio.Parameters[nameof(TextInputForm.Value)] = s;
                    })},
                { nameof(TextInputForm.ValueExpression), exprPosizioneArchivio },
                { nameof(TextInputForm.ValidationFor), exprPosizioneArchivio }
            };

            retVal.Add(this.m_provider.IdCodiceIstanza, codiceIstanza);
            retVal.Add(this.m_provider.IdAnnoIstanza, annoIstanza);
            retVal.Add(this.m_provider.IdMeseIstanza, meseIstanza);
            retVal.Add(this.m_provider.IdOggetto, oggetto);
            retVal.Add(this.m_provider.IdCivico, civico);
            retVal.Add(this.m_provider.IdNumeroAutorizzazione, numAutorizzazione);
            retVal.Add(this.m_provider.IdNumProtocollo, numProtocollo);
            retVal.Add(this.m_provider.IdStradario, stradario);
            retVal.Add(-1, comuneLocalizzazione);
            retVal.Add(this.m_provider.IdStatoIstanza, statoIstanza);
            retVal.Add(this.m_provider.IdDataProtocollo, dataProtocollo);
            retVal.Add(this.m_provider.IdDatiCatasto, datiCatasto);
            retVal.Add(this.m_provider.IdRichiedente, richiedente);
            retVal.Add(this.m_provider.IdIntervento, intervento);
            retVal.Add(this.m_provider.IdFabbricato, fabbricato);
            retVal.Add(this.m_provider.IdPoszioneArchivio, posizioneArchivio);

            return retVal;
        }

        private void CostruisciControlli()
        {
            this.m_logger.Debug("Inizio creazione controlli di ricerca");

            var filtri = this.m_provider.GetCampiFiltro(this.IdComune, this.Software).Where(x => !String.IsNullOrEmpty(x.Valore) && x.Valore != "0").OrderBy(x => x.Valore).ThenBy(x => x.Etichetta).ToArray();

            foreach (CampoVisuraFrontofficeDto campo in filtri)
            {
                if (!this._dictionaryControlli.ContainsKey(campo.Codice))
                {
                    this.m_logger.Debug("Il dizionario non contiene l'id " + campo.Codice);
                }
                else
                {
                    var dynComponent = this._dictionaryControlli[campo.Codice];
                    dynComponent.Parameters.Add("Label", this._risorseTestualiService.GetRisorsa(campo.IdRisorsa, campo.Etichetta));

                    if (campo.Codice == this.m_provider.IdStradario && this.contieneComuniAssociati)
                    {
                        this.SortedDictionary.Add(-1, this._dictionaryControlli.FirstOrDefault(x => x.Key == -1).Value);
                        this.SortedDictionary.Add(campo.Codice, dynComponent);
                    }
                    else
                    {
                        this.SortedDictionary.Add(campo.Codice, dynComponent);
                    }
                }
            }

            this.m_logger.Debug("Fine creazione controlli di ricerca");
        }
    }
}
