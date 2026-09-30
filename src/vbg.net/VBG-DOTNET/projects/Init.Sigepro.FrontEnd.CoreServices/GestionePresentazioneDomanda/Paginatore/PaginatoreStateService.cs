using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.CoreServices.GestioneUrl;
using Init.Sigepro.FrontEnd.CoreServices.Markdown;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Microsoft.AspNetCore.Components;

namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore
{

    public class PaginatoreStateService
    {
        private readonly IWorkflowService _workflowService;
        private readonly IUrlBuilder _urlBuilder;
        private readonly NavigationManager _navigationManager;
        private readonly IBaseUrl _baseUrl;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public event Action? OnStateChanged;
        //public event Action? OnStatePropertyChanged;
        public event Action<ExitStepEventArgs>? OnBeforeNextStep;
        public event Func<ExitStepEventArgs, Task>? OnBeforeNextStepAsync;
        public EventCallback OnSend;

        public PaginatoreState CurrentState { get; private set; } = new PaginatoreState();

        private int _idDomanda = 0;
        //private bool _spinnerVisible = false;

        public PaginatoreStateService(IWorkflowService workflowService, IUrlBuilder urlBuilder, NavigationManager navigationManager, IBaseUrl baseUrl,
            IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver _authenticationDataResolver)
        {
            this._workflowService = workflowService;
            this._urlBuilder = urlBuilder;
            this._navigationManager = navigationManager;
            this._baseUrl = baseUrl;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._authenticationDataResolver = _authenticationDataResolver;
            this._navigationManager.LocationChanged += this.OnLocationChanged;
        }

        private void OnLocationChanged(object? sender, Microsoft.AspNetCore.Components.Routing.LocationChangedEventArgs e)
        {
            //if (this._spinnerVisible)
            //{
            //    this._spinnerService.Hide();
            //}
        }

        public void SetCurrentStep(int stepId)
        {
            if (this.CurrentState?.CurrentStepId == stepId)
            {
                return;
            }

            var current = this._workflowService.GetWorkflowByIdDomanda(this._idDomanda);
            var titoli = current.GetTitoliSteps();

            var newState = new PaginatoreState
            {
                CurrentStepId = stepId,
                TitoloPagina = current.GetTitoloStep(stepId),
                DescrizionePagina = new MarkdownString(current.GetDescrizioneStep(stepId)).ToHtml(),
                LastCompletedStep = stepId,
                Steps = titoli.Select((item, idx) => new Step
                {
                    IndiceStepZeroBased = idx,
                    IsCurrent = stepId == idx,
                    IsEnabled = stepId > idx,
                    IsFirst = idx == 0,
                    IsLast = titoli.Count() == idx + 1,
                    NomeStep = item,
                    DescrizioneStep = new MarkdownString(current.GetDescrizioneStep(idx)).ToHtml(),
                }),
                IsStepDisabilitato = current.IsStepDisabilitato(stepId)
            };

            var stepIdHasChanged = (this.CurrentState?.CurrentStepId ?? -1) != stepId;

            if (stepIdHasChanged)
            {
                newState.ResetStatoVisibilita();
            }
            else
            {
                newState.CopiaStatoVisibilitaDa(this.CurrentState);
            }

            this.CurrentState = newState;

            if (stepIdHasChanged)
            {
                this.OnStateChanged?.Invoke();
            }
        }

        public void SetIdDomanda(int idDomanda)
        {
            this._idDomanda = idDomanda;
        }

        public void GoToStep(int nextstep)
        {
            //this._spinnerService.ShowAsync();
            //this._spinnerVisible = true;

            var stepUrl = this._workflowService.GetWorkflowByIdDomanda(this._idDomanda).GetStepUrl(nextstep);
            var url = stepUrl.StartsWith("~") ? this.GeneraUrlFramework(stepUrl, nextstep) : this.GeneraUrlCore(stepUrl, nextstep);

            this._navigationManager.NavigateTo(url);
        }

        private string GeneraUrlCore(string stepUrl, int nextstep)
        {
            return this._urlBuilder.Build(stepUrl, this._idDomanda, nextstep);
        }

        private string GeneraUrlFramework(string stepUrl, int nextstep)
        {
            var url = $"{this._baseUrl.BaseUrlFramework}{stepUrl.Substring(1)}?" +
                $"idcomune={this._aliasSoftwareResolver.AliasComune}&" +
                $"software={this._aliasSoftwareResolver.Software}&" +
                $"IdPresentazione={this._idDomanda}&" +
                $"token={this._authenticationDataResolver.DatiAutenticazione.Token}&" +
                $"stepid={nextstep}";

            return url;
        }

        public async Task GoToNextStepAsync()
        {
            var eventArgs = new ExitStepEventArgs();

            if (this.OnBeforeNextStep is not null)
            {
                this.OnBeforeNextStep.Invoke(eventArgs);
            }

            if (!eventArgs.IsCanceled && this.OnBeforeNextStepAsync is not null)
            {
                await this.OnBeforeNextStepAsync.Invoke(eventArgs);
            }

            if (!eventArgs.IsCanceled)
            {
                this.GoToStep(this.CurrentState.LastCompletedStep + 1);
            }
        }

        public void GoToPrevStep()
        {
            this.GoToStep(this.CurrentState.LastCompletedStep - 1);
        }

        public void MostraPaginatore()
        {
            this.setVisibility(true);
        }

        public void NascondiPaginatore()
        {
            this.setVisibility(false);
        }

        private void setVisibility(bool show)
        {
            this.CurrentState.IsVisible = show;
            this.OnStateChanged?.Invoke();
        }

        public void NascondiBottoneAvanti()
        {
            this.CurrentState.MostraBottoneAvanti = false;
            this.OnStateChanged?.Invoke();
            //this.OnStatePropertyChanged?.Invoke();
        }

        public void NascondiDescrizioneStep()
        {
            this.CurrentState.MostraDescrizioneStep = false;
            this.OnStateChanged?.Invoke();
            //this.OnStatePropertyChanged?.Invoke();
        }

        public void MostraBottoneAvanti()
        {
            this.CurrentState.MostraBottoneAvanti = true;
            this.OnStateChanged?.Invoke();
            //this.OnStatePropertyChanged?.Invoke();
        }

        public void MostraBottoneInviaDomanda(string text = "Invia Domanda")
        {
            this.CurrentState.MostraBottoneInviaDomanda = true;
            this.CurrentState.TestoBottoneInviaDomanda = text;
            this.OnStateChanged?.Invoke();
        }

        public void NascondiBottoneInviaDomanda()
        {
            this.CurrentState.MostraBottoneInviaDomanda = false;
            this.OnStateChanged?.Invoke();
        }

        public void SetTestoBottoneInviaDomanda(string text)
        {
            this.CurrentState.TestoBottoneInviaDomanda = text;
            this.OnStateChanged?.Invoke();
            //this.OnStatePropertyChanged?.Invoke();
        }

        public async Task SendAsync()
        {
            if (this.OnSend.HasDelegate)
            {
                await this.OnSend.InvokeAsync();
            }
        }
    }
}
