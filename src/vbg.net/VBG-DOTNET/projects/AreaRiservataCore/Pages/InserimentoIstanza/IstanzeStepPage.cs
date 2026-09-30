using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.PropertyBinder;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Admin;
using Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore;
using Microsoft.AspNetCore.Components;
using VBG.AppLogic.SSU.GestioneCorrezioni;
using VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.InserimentoIstanza
{
    public class IstanzeStepPage : BasePage, IDisposable
    {
        [Inject]
        public StatoDomandaPresentataService _statoDomandaService { get; private set; } = default!;
        [Inject]
        private DomandeOnlineService _domandeOnlineService { get; set; } = default!;
        [Inject]
        private WorkflowStepPropertiesBinder _stepPropertiesBinder { get; set; } = default!;
        [Inject]
        private IDatiDomandaFoRepository _datiDomandaFoRepository { get; set; } = default!;
        [Inject]
        private IdDomandaResolver _idDomandaResolver { get; set; } = default!;
        [Inject]
        private GoToWorkflowButtonService _goToButtonService { get; set; } = default!;
        [Inject]
        private IGestioneCorrezioniSsuService _gestioneCorrezioniSsuService { get; set; } = default!;
        [Inject]
        private IGestioneRichiestaIntegrazioniService _gestioneRichiestaIntegrazioniService { get; set; } = default!;

        // Dichiarato con due tratti bassi per non interferire con le pagine derivate
        // che a loro volta potrebbero dichiarare un parametro con lo stesso nome
        [CascadingParameter]
        private PaginatoreStateService __paginatoreStateService { get; set; } = default!;
        [CascadingParameter]
        private InserimentoIstanzaLayout? _layout { get; set; }


        [Parameter]
        public int StepId { get; set; } = 0;
        [Parameter]
        public int? IdDomanda { get; set; } = -1;

        [CascadingParameter(Name = "Layout")]
        private MainLayout Layout { get; set; } = default!;

        protected bool RichiedeCorrezioniSsu { get; private set; }
        protected bool RichiedeIntegrazioniSsu { get; private set; }
        protected bool SolaLettura => this.RichiedeCorrezioniSsu || this.RichiedeIntegrazioniSsu;   // Per ora l'unico caso in cui una pratica è in sola lettura è quando richiede correzioni o integrazioni SSU,
                                                                                                    // ma in futuro potrebbero essercene altri     

        private bool _isInitialized = false;

        // Sealed per evitare che nelle classi derivate venga dimenticato di chiamare il base
        public override sealed async Task SetParametersAsync(ParameterView parameters)
        {
            await base.SetParametersAsync(parameters);
        }

        protected override sealed async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            this.InitializeBreadCrumbItems();

            this.__paginatoreStateService.OnBeforeNextStepAsync += this.OnBeforeNavigateToNextStepAsync;
            this._idDomandaResolver.IdDomanda = this.IdDomanda.Value;

            if (this._isInitialized)
            {
                await Task.CompletedTask;
                return;
            }

            this._isInitialized = true;

            if (this.IdDomanda <= 0)
            {
                return;
            }

            this._layout?.SetIdDomanda(this.IdDomanda.Value);

            this.__paginatoreStateService.SetIdDomanda(this.IdDomanda.Value);
            // Salvo l'ultimo step completato precedentemente in modo da poterlo ripristinare nel caso in cui lo step corrente venisse saltato (in avanti o indietro)
            // vd. controllo successivo su puoAccedere
            var lastCompletedStepOld = this.__paginatoreStateService.CurrentState.LastCompletedStep;
            this.__paginatoreStateService.SetCurrentStep(this.StepId);

            this.AssociaProprietaStepDaWorkflow();
            this.VerificaSeIstanzaPresentata();

            await this.TriggerOnInitializeStepAsync();

            var puoAccedere = await this.VerificaAccessoAllaPaginaAsync();

            if (puoAccedere)
            {
                this._goToButtonService.SetCodiceIntervento(this.DomandaCorrente.AltriDati.Intervento?.Codice);

                await this.TriggerLoadStepAsync();
            }
            else
            {
                this.__paginatoreStateService.SetCurrentStep(lastCompletedStepOld);

                await this.SkipCurrentStepAsync();
            }
        }

        private void InitializeBreadCrumbItems()
        {
            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>()
                {
                    new()
                    {
                        Label = "Nuova pratica",
                        Url = $"{baseUrl}/inserimento-istanza",
                        Active = true,
                    }
                };

            this.Layout.SetBreadcrumbItems(breadcrumbItems);
        }

        private async Task TriggerOnInitializeStepAsync()
        {
#pragma warning disable VSTHRD103 // Call async methods when in an async method

            this.OnInitializeStep();

            await this.OnInitializeStepAsync();

#pragma warning restore VSTHRD103 // Call async methods when in an async method
        }

        private async Task SkipCurrentStepAsync()
        {
            var shouldRedirect = await this.ShouldRedirectInternalAsync();

            if (shouldRedirect)
            {
                return;
            }

            var nextstep = this.__paginatoreStateService.CurrentState.LastCompletedStep < this.StepId ? this.StepId + 1 : this.StepId - 1;

            this.Logger.Debug($"La pagina {this.GetType()} ha negato l'accesso allo step, l'esecuzione proseguirà allo step {nextstep}. Step corrente {this.StepId}");

            this.__paginatoreStateService.GoToStep(nextstep);
        }

        private async Task<bool> ShouldRedirectInternalAsync()
        {
#pragma warning disable VSTHRD103 // Call async methods when in an async method
            var shouldRedirect = this.ShouldRedirect();

            if (shouldRedirect)
            {
                return true;
            }
#pragma warning restore VSTHRD103 // Call async methods when in an async method
            return await this.ShouldRedirectAsync();
        }

        private void AssociaProprietaStepDaWorkflow()
        {
            this._stepPropertiesBinder.BindProperties(this.IdDomanda!.Value, this.StepId, this);
        }

        private void VerificaSeIstanzaPresentata()
        {
            if (this.IdDomanda is not null && this._datiDomandaFoRepository.DomandaPresentata(this.IdDomanda.Value))
            {
                // Se sono dentro una domanda SSU che è già stata presentata ma che richiede correzioni o integrazioni
                // allora non rimando alla pagina di errore
                if (this._gestioneCorrezioniSsuService.DomandaDaCorreggere(this.IdDomanda.Value))
                {
                    this.RichiedeCorrezioniSsu = true;

                    return;
                }
                else if (this._gestioneRichiestaIntegrazioniService.DomandaDaIntegrare(this.IdDomanda.Value))
                {
                    this.RichiedeIntegrazioniSsu = true;

                    return;
                }

                this.Logger.Error($"L'utente {this.UserAuthenticationResult.DatiUtente.Codicefiscale} sta cercando di accedere alla domanda {this.IdDomanda} ma la domanda risulta essere già presentata (url={this._navigationManager.Uri})");

                this._statoDomandaService.MarcaDomandaComePresentata(this.IdDomanda.Value);

                var url = this.GetNavigationPathTo("inserimento-istanza/errors/domanda-gia-presentata");

                this._navigationManager.NavigateTo(url);
            }
        }

        private async Task<bool> VerificaAccessoAllaPaginaAsync()
        {
#pragma warning disable VSTHRD103

            if (!this.CanEnterStep())
            {
                return false;
            }

            return await this.CanEnterStepAsync();

#pragma warning restore VSTHRD103
        }

        private async Task OnBeforeNavigateToNextStepAsync(ExitStepEventArgs e)
        {
#pragma warning disable VSTHRD103 // Call async methods when in an async method

            this.OnBeforeExitStep();

            await this.OnBeforeExitStepAsync();

            if (!this.CanExitStep() || !(await this.CanExitStepAsync()))
            {
                e.Cancel();

                return;
            }

#pragma warning restore VSTHRD103 // Call async methods when in an async method
        }

        private async Task TriggerLoadStepAsync()
        {
#pragma warning disable VSTHRD103 // Call async methods when in an async method
            this.OnLoadStep();

            await this.OnLoadStepAsync();
#pragma warning restore VSTHRD103 // Call async methods when in an async method
        }

        protected virtual Task OnLoadStepAsync() => Task.CompletedTask;
        protected virtual void OnLoadStep() { }
        protected virtual void OnInitializeStep() { }
        protected virtual Task OnInitializeStepAsync() => Task.CompletedTask;
        protected virtual void OnBeforeExitStep() { }
        protected virtual Task OnBeforeExitStepAsync() => Task.CompletedTask;
        protected virtual bool CanEnterStep() => true;
        protected virtual Task<bool> CanEnterStepAsync() => Task.FromResult(true);
        protected virtual bool CanExitStep() => true;
        protected virtual Task<bool> CanExitStepAsync() => Task.FromResult(true);
        protected virtual bool ShouldRedirect() => false;
        protected virtual Task<bool> ShouldRedirectAsync() => Task.FromResult(false);

        protected virtual void OnDisposing() { }

        public sealed override void Dispose()
        {
            if (this.__paginatoreStateService is not null)
            {
                this.__paginatoreStateService.OnBeforeNextStepAsync -= this.OnBeforeNavigateToNextStepAsync;
            }

            this.OnDisposing();

            base.Dispose();
        }

        public IDomandaOnlineReadInterface DomandaCorrente
             => this.IdDomanda.HasValue ?
                this._domandeOnlineService.GetById(this.IdDomanda.Value).ReadInterface :
                throw new InvalidOperationException("Id domanda non ancora impostato");
    }
}
