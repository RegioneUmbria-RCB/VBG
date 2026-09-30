//using AreaRiservataCore.Pages.MiePratiche;
//using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1;
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
//using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
//using Init.Sigepro.FrontEnd.CoreServices.Visura;
//using Microsoft.AspNetCore.Components;
//using VBG.AreaRiservataCore.Controls;
//using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

//namespace AreaRiservataCore.Pages.Visura
//{
//    public partial class FiltriArchivioIstanzeControl : FiltriVisuraControlBase
//    {
//        [Inject]
//        private ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; } = default!;
//        [Inject]
//        private RichiestaListaPraticheService _richiestaListaPraticheService { get; set; } = default!;

//        [Parameter, EditorRequired]
//        public ComponentsComunicationService ComunicationService { get; set; }

//        [Parameter]
//        public RichiestaListaPraticheV3? SelectedFilters { get; set; }

//        protected override void OnInitialized()
//        {
//            OnInit(new FiltriArchivioIstanzeControlProvider(_campiRicercaVisuraRepository), typeof(FiltriArchivioIstanzeControl));

//            base.OnInitialized();
//        }

//        protected override Task OnInitializedAsync()
//        {
//            OnInitAsync();

//            return base.OnInitializedAsync();
//        }

//        public override void SetModelValues()
//        {
//            base.SetModelValues();

//            if (SelectedFilters == null)
//                return;

//            if (SelectedFilters.CodiceIntervento != null)
//            {
//                var intervento = _alberoProcRepository.GetDettagliIntervento(
//                    SelectedFilters.CodiceIntervento.Value,
//                    new AmbitoRicercaAreaRiservata(UserAuthenticationResult?.DatiUtente.UtenteTester ?? false),
//                    false);

//                Model.TipoIntervento = new VBG.BlazorComponentsLibrary.EditFormComponents.AutocompleteFormResult(intervento.Codice.ToString(), intervento.Descrizione);
//            }

//            if (SelectedFilters.DatiCatastali != null)
//            {
//                // da spostare e unificare con quello presente in DatiCatastaliControl
//                List<DropDownItem> _tipiCatasto = new List<DropDownItem>();
//                _tipiCatasto.Add(new DropDownItem("Terreni", "T") { });
//                _tipiCatasto.Add(new DropDownItem("Fabbricati", "F") { });

//                Model.DatiCatasto.TipoCatasto = _tipiCatasto.FirstOrDefault(x => x.Value == SelectedFilters.DatiCatastali.TipoCatasto);
//                Model.DatiCatasto.Foglio = SelectedFilters.DatiCatastali.Foglio;
//                Model.DatiCatasto.Particella = SelectedFilters.DatiCatastali.Particella;
//                Model.DatiCatasto.Subalterno = SelectedFilters.DatiCatastali.Subalterno;
//            }

//            if (SelectedFilters.DatiProtocollo != null)
//            {
//                Model.NumeroProtocollo = SelectedFilters.DatiProtocollo.Numero;
//                Model.DataProtocollo = SelectedFilters.DatiProtocollo.Data;
//            }

//            if (SelectedFilters.Indirizzo != null)
//            {
//                var datiStradario = _stradarioRepository.GetByCodiceStradario(SelectedFilters.Indirizzo.CodiceStradario);
//                Model.Stradario = new VBG.BlazorComponentsLibrary.EditFormComponents.AutocompleteFormResult(datiStradario.CodiceStradario.ToString(), datiStradario.Descrizione);
//                Model.Civico = SelectedFilters.Indirizzo.Civico;
//            }

//            Model.Fabbricato = SelectedFilters.Fabbricato;
//            Model.Richiedente = SelectedFilters.NomeOCfRichiedente;
//            Model.NumeroAutorizzazione = SelectedFilters.NumeroAutorizzazione;
//            Model.CodiceIstanza = SelectedFilters.NumeroIstanza;
//            Model.Oggetto = SelectedFilters.Oggetto;

//            if (SelectedFilters.PeriodoPresentazione != null)
//            {
//                Model.Anno = SelectedFilters.PeriodoPresentazione.Anno;

//                if (SelectedFilters.PeriodoPresentazione.Mese != null)
//                    Model.Mese = mesiDataItems.FirstOrDefault(x => x.Value == (SelectedFilters.PeriodoPresentazione.Mese.HasValue ? SelectedFilters.PeriodoPresentazione.Mese.Value.ToString("00") : "00"));
//            }

//            if (SelectedFilters.StatoPratica != null)
//                Model.StatoIstanza.CodiceStato = SelectedFilters.StatoPratica;

//            if (SelectedFilters.PosizioneArchivio != null)
//                Model.PosizioneArchivio = SelectedFilters.PosizioneArchivio;
//        }

//        private RichiestaListaPraticheV3 getFiltri()
//        {
//            var req = new RichiestaListaPraticheV3();

//            req.Software = Software;

//            if (Model == null)
//                return req;

//            if (Model.TipoIntervento != null)
//                req.CodiceIntervento = !String.IsNullOrEmpty(Model.TipoIntervento.Value) ? Convert.ToInt32(Model.TipoIntervento.Value) : (int?)null;

//            if (!string.IsNullOrEmpty(Model.DatiCatasto.TipoCatasto?.Value) ||
//                !string.IsNullOrEmpty(Model.DatiCatasto.Foglio) ||
//                !string.IsNullOrEmpty(Model.DatiCatasto.Particella) ||
//                !string.IsNullOrEmpty(Model.DatiCatasto.Subalterno))
//            {
//                req.DatiCatastali = new FiltriDatiCatastali
//                {
//                    TipoCatasto = Model.DatiCatasto.TipoCatasto?.Value,
//                    Foglio = Model.DatiCatasto.Foglio,
//                    Particella = Model.DatiCatasto.Particella,
//                    Subalterno = Model.DatiCatasto.Subalterno
//                };
//            }

//            if (!String.IsNullOrEmpty(Model.NumeroProtocollo) || Model.DataProtocollo != null)
//            {
//                req.DatiProtocollo = new FiltroDatiProtocollo
//                {
//                    Numero = Model.NumeroProtocollo,
//                    Data = Model.DataProtocollo
//                };
//            }

//            if (!String.IsNullOrEmpty(Model.Stradario?.Value))
//            {
//                req.Indirizzo = new FiltroIndirizzo
//                {
//                    CodiceStradario = Convert.ToInt32(Model.Stradario.Value),
//                    Civico = Model.Civico
//                };
//            }

//            req.Fabbricato = Model.Fabbricato;
//            req.NomeOCfRichiedente = Model.Richiedente;
//            req.NumeroAutorizzazione = Model.NumeroAutorizzazione;
//            req.NumeroIstanza = Model.CodiceIstanza;
//            req.Oggetto = Model.Oggetto;

//            if (Model.Anno != null)
//            {
//                req.PeriodoPresentazione = new FiltroPeriodoPresentazione
//                {
//                    Anno = Model.Anno.Value,
//                    Mese = Model.Mese == null ? (int?)null : Convert.ToInt32(Model.Mese.Value)
//                };
//            }

//            req.StatoPratica = Model.StatoIstanza?.CodiceStato;

//            req.PosizioneArchivio = Model.PosizioneArchivio;

//            return req;
//        }

//        private async Task onValidSubmit()
//        {
//            ComunicationService.CallRequestRefresh();

//            var richiesta = getFiltri();

//            await ComunicationService.CallPassDataAsync(richiesta);

//            await scrollToTop();
//        }

//        private async Task onInvalidSubmit()
//        {

//        }
//    }
//}
