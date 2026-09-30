using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;

namespace AreaRiservataCore.Pages.InserimentoIstanza.LocalizzazioniModena
{
    public partial class GestioneLocalizzazioniModena
    {
        [Inject]
        public ILocalizzazioniModenaService _localizzazioniService { get; set; } = default!;
        [CascadingParameter]
        private PaginatoreStateService _paginatoreStateService { get; set; } = default!;

        #region Parametri letti dal file di workflow
        [StepProperty]
        public int IdStradarioDefault { get; set; } = 0;
        [StepProperty]
        public string CodiceCatastoDefault { get; set; } = "";
        [StepProperty]
        public string IdCatastoDefault { get; set; } = "T";
        [StepProperty]
        public string NomeCatastoDefault { get; set; } = "Terreni";
        [StepProperty]
        public int IdCampoParticelleManualiPresenti { get; set; } = -1;
        #endregion

        private IJSObjectReference main_module = null;

        protected override void OnInitializeStep()
        {
            this._paginatoreStateService.NascondiBottoneAvanti();
        }

        protected override void OnLoadStep()
        {
            this._paginatoreStateService.NascondiBottoneAvanti();
        }

        [JSInvokable]
        public static void MostraBottoneAvanti(HttpContext httpContext)
        {
            // Get an instance of the service using the HttpContext
            var paginatoreStateService = httpContext.RequestServices.GetRequiredService<PaginatoreStateService>();

            paginatoreStateService.MostraBottoneAvanti();
        }

        protected override async Task OnAfterRenderAsync(bool firstRender)
        {
            if (firstRender)
            {
                var jQuery = await this._jsRuntime.InvokeAsync<IJSObjectReference>("import", "https://code.jquery.com/jquery-3.7.1.min.js");
                // var bootstrapSelect = await this._jsRuntime.InvokeAsync<IJSObjectReference>("import", "https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.14/js/bootstrap-select.min.js");
                var openLayers = await this._jsRuntime.InvokeAsync<IJSObjectReference>("import", "https://cdn.rawgit.com/openlayers/openlayers.github.io/master/en/v5.3.0/build/ol.js");
                var proj4 = await this._jsRuntime.InvokeAsync<IJSObjectReference>("import", "./localizzazioni-modena/resources/src/js/proj4js.mjs");
                this.main_module = await this._jsRuntime.InvokeAsync<IJSObjectReference>("import", "./localizzazioni-modena/resources/src/js/main.mjs");
                var projectFunctions_module = await this._jsRuntime.InvokeAsync<IJSObjectReference>("import", "./localizzazioni-modena/resources/src/js/projectFunctions.mjs");

                await projectFunctions_module.InvokeVoidAsync("setArrayParticelleInputByString", this.GetArrayJsPerInizializzazione());

                await this.main_module.InvokeVoidAsync("run");
            }
        }

        //protected override bool CanEnterStep()
        //{
        //    return base.CanEnterStep();
        //}

        //protected override bool CanExitStep()
        //{
        //    return base.CanExitStep();
        //}

        public string GetArrayJsPerInizializzazione()
        {
            return this._localizzazioniService.GetStringaArrayParticelleSelezionateCore(this.IdDomanda.Value, this.CodiceCatastoDefault);
        }

        protected async Task CmdConferma_ClickAsync()
        {
            try
            {
                var selectedValues = await this.main_module.InvokeAsync<string>("getSelectedValues");
                var localizzazioni = LocalizzazioniMappaModenaSelezionate.FromJsonString(selectedValues);

                var opzioni = new OpzioniSalvataggioLocalizzazioniModena(this.IdStradarioDefault, this.IdCatastoDefault, this.NomeCatastoDefault, this.IdCampoParticelleManualiPresenti);

                this._localizzazioniService.AggiornaLocalizzazioniModenaDaMappa(this.IdDomanda.Value, localizzazioni, opzioni);
            }
            catch (Exception)
            {
                //this.Errori.Add(ex.Message);

                return;
            }

            this._paginatoreStateService.MostraBottoneAvanti();
            await this._paginatoreStateService.GoToNextStepAsync();
        }
    }
}