using AreaRiservataCore.Pages.MiePratiche.Componenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.CoreServices.Visura;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class FiltriVisuraControl : FiltriVisuraControlBase
    {
        [Inject]
        private ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; } = default!;

        [Parameter]
        public RichiestaListaPraticheV3? SelectedFilters { get; set; }

        [Parameter]
        public ViewMode Mode { get; set; } = ViewMode.IstanzePresentate;

        [Parameter]
        public EventCallback<RichiestaListaPraticheV3> OnCerca { get; set; }

        private bool _nascondiFiltroRichiedente;

        protected override void OnInitialized()
        {
            this._nascondiFiltroRichiedente = this.Mode == ViewMode.ArchivioPratiche;

            switch (this.Mode)
            {
                case ViewMode.IstanzePresentate:
                    this.OnInit(new FiltriVisuraControlProvider(this._campiRicercaVisuraRepository), typeof(FiltriVisuraControl));
                    break;

                case ViewMode.ArchivioPratiche:
                    this.OnInit(new FiltriArchivioIstanzeControlProvider(this._campiRicercaVisuraRepository), typeof(FiltriArchivioIstanzeControl));
                    break;
            }

            base.OnInitialized();
        }

        protected override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();
        }

        public override void SetModelValues()
        {
            base.SetModelValues();

            if (this.SelectedFilters == null)
                return;

            if (this.SelectedFilters.CodiceIntervento != null)
            {
                var intervento = this._alberoProcRepository.GetDettagliIntervento(
                    this.SelectedFilters.CodiceIntervento.Value,
                    new AmbitoRicercaAreaRiservata(this.UserAuthenticationResult?.DatiUtente.UtenteTester ?? false),
                    false);

                this.Model.TipoIntervento = new VBG.BlazorComponentsLibrary.EditFormComponents.AutocompleteFormResult(intervento.Codice.ToString(), intervento.Descrizione);
            }

            if (this.SelectedFilters.DatiCatastali != null)
            {
                // da spostare e unificare con quello presente in DatiCatastaliControl
                List<DropDownItem> _tipiCatasto = new List<DropDownItem>();
                _tipiCatasto.Add(new DropDownItem("Terreni", "T") { });
                _tipiCatasto.Add(new DropDownItem("Fabbricati", "F") { });

                this.Model.DatiCatasto.TipoCatasto = _tipiCatasto.FirstOrDefault(x => x.Value == this.SelectedFilters.DatiCatastali.TipoCatasto);
                this.Model.DatiCatasto.Foglio = this.SelectedFilters.DatiCatastali.Foglio;
                this.Model.DatiCatasto.Particella = this.SelectedFilters.DatiCatastali.Particella;
                this.Model.DatiCatasto.Subalterno = this.SelectedFilters.DatiCatastali.Subalterno;
            }

            if (this.SelectedFilters.DatiProtocollo != null)
            {
                this.Model.NumeroProtocollo = this.SelectedFilters.DatiProtocollo.Numero;
                this.Model.DataProtocollo = this.SelectedFilters.DatiProtocollo.Data;
            }

            if (this.SelectedFilters.Indirizzo != null)
            {
                var datiStradario = this._stradarioRepository.GetByCodiceStradario(this.SelectedFilters.Indirizzo.CodiceStradario);
                this.Model.Stradario = new VBG.BlazorComponentsLibrary.EditFormComponents.AutocompleteFormResult(datiStradario.CodiceStradario.ToString(), datiStradario.Descrizione);
                this.Model.Civico = this.SelectedFilters.Indirizzo.Civico;
            }

            this.Model.Fabbricato = this.SelectedFilters.Fabbricato;
            this.Model.Richiedente = this.SelectedFilters.NomeOCfRichiedente;
            this.Model.NumeroAutorizzazione = this.SelectedFilters.NumeroAutorizzazione;
            this.Model.CodiceIstanza = this.SelectedFilters.NumeroIstanza;
            this.Model.Oggetto = this.SelectedFilters.Oggetto;

            if (this.SelectedFilters.PeriodoPresentazione != null)
            {
                this.Model.Anno = this.SelectedFilters.PeriodoPresentazione.Anno;

                if (this.SelectedFilters.PeriodoPresentazione.Mese != null)
                    this.Model.Mese = this.mesiDataItems.FirstOrDefault(x => x.Value == (this.SelectedFilters.PeriodoPresentazione.Mese.HasValue ? this.SelectedFilters.PeriodoPresentazione.Mese.Value.ToString("00") : "00"));
            }

            if (this.SelectedFilters.StatoPratica != null)
                this.Model.StatoIstanza.CodiceStato = this.SelectedFilters.StatoPratica;

            if (this.SelectedFilters.PosizioneArchivio != null)
                this.Model.PosizioneArchivio = this.SelectedFilters.PosizioneArchivio;
        }

        private RichiestaListaPraticheV3 GetFiltri()
        {
            var req = new RichiestaListaPraticheV3();

            if (this.Model == null)
                return req;

            if (this.Model.TipoIntervento != null)
                req.CodiceIntervento = !String.IsNullOrEmpty(this.Model.TipoIntervento.Value) ? Convert.ToInt32(this.Model.TipoIntervento.Value) : null;

            if (!string.IsNullOrEmpty(this.Model.DatiCatasto.TipoCatasto?.Value) ||
                !string.IsNullOrEmpty(this.Model.DatiCatasto.Foglio) ||
                !string.IsNullOrEmpty(this.Model.DatiCatasto.Particella) ||
                !string.IsNullOrEmpty(this.Model.DatiCatasto.Subalterno))
            {
                req.DatiCatastali = new FiltriDatiCatastali
                {
                    TipoCatasto = this.Model.DatiCatasto.TipoCatasto?.Value,
                    Foglio = this.Model.DatiCatasto.Foglio,
                    Particella = this.Model.DatiCatasto.Particella,
                    Subalterno = this.Model.DatiCatasto.Subalterno
                };
            }

            if (!String.IsNullOrEmpty(this.Model.NumeroProtocollo) || this.Model.DataProtocollo != null)
            {
                req.DatiProtocollo = new FiltroDatiProtocollo
                {
                    Numero = this.Model.NumeroProtocollo,
                    Data = this.Model.DataProtocollo
                };
            }

            if (!String.IsNullOrEmpty(this.Model.Stradario?.Value))
            {
                req.Indirizzo = new FiltroIndirizzo
                {
                    CodiceStradario = Convert.ToInt32(this.Model.Stradario.Value),
                    Civico = this.Model.Civico
                };
            }

            req.Fabbricato = this.Model.Fabbricato;
            req.NomeOCfRichiedente = this.Model.Richiedente;
            req.NumeroAutorizzazione = this.Model.NumeroAutorizzazione;
            req.NumeroIstanza = this.Model.CodiceIstanza;
            req.Oggetto = this.Model.Oggetto;

            if (this.Model.Anno != null)
            {
                req.PeriodoPresentazione = new FiltroPeriodoPresentazione
                {
                    Anno = this.Model.Anno.Value,
                    Mese = this.Model.Mese == null ? null : Convert.ToInt32(this.Model.Mese.Value)
                };
            }

            req.StatoPratica = this.Model.StatoIstanza?.CodiceStato;

            req.PosizioneArchivio = this.Model.PosizioneArchivio;

            return req;
        }

        private async Task OnValidSubmitAsync()
        {
            var richiesta = this.GetFiltri();

            await this.OnCerca.InvokeAsync(richiesta);

            await this.ScrollToTopAsync();
        }
    }
}
