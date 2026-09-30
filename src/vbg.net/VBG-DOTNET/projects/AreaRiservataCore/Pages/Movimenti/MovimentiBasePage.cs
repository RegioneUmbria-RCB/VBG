using AreaRiservataCore.Shared;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using log4net;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.AccessibilityComponents;

namespace AreaRiservataCore.Pages.Movimenti
{
    public abstract class MovimentiBasePage : BasePage
    {
        new protected ILog _log = LogManager.GetLogger(typeof(MovimentiBasePage));
        private readonly WorkflowGestioneMovimenti _workflow = new();

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; } = default!;

        //[Inject]
        //public IdMovimentoResolver _idMovimentoResolver { get; set; } = default!;

        [Inject]
        public GestioneMovimentiHttpDataContext _gestioneMovimentiHttpDataContext { get; set; } = default!;

        [Inject]
        private LastStepService _lastStepService { get; set; } = default!;

        [Inject]
        protected IIdMovimentoResolver _idMovimentoResolver { get; set; } = default!;

        [Parameter]
        public int Step { get; set; } = 0;

        [Parameter]
        public int IdMovimento { get; set; } = 0;

        [Parameter]
        public string GoBackUrl { get; set; }

        [CascadingParameter(Name = "Layout")]
        public MainLayout Layout { get; set; } = default!;

        public string GoBackUrlQueryString { get; set; }

        protected abstract IStepViewModel GetViewmodel();

        protected sealed override async Task OnInitializedAsync()
        {
            await base.OnInitializedAsync();

            var baseUrl = $"{this._navigationManager.BaseUri}{this._authenticationDataResolver.DatiAutenticazione.Alias}/{this.Software}";
            var breadcrumbItems = new List<BreadcrumbsItem>();

            if (this.GoBackUrl == "scadenzario")
            {
                breadcrumbItems = new List<BreadcrumbsItem>()
                {
                    new() {
                        Label = "Le mie scadenze",
                        Url = $"{baseUrl}/{this.GoBackUrl}",
                    }
                };
            }
            else
            {
                breadcrumbItems = new List<BreadcrumbsItem>()
                {
                    new() {
                        Label = "Le mie pratiche",
                        Url = $"{baseUrl}/istanzepresentate",
                    },
                    new() {
                        Label = "Dettagli istanza",
                        Url = $"{baseUrl}/{this.GoBackUrl}",
                    }
                };
            }

            breadcrumbItems.Add(new BreadcrumbsItem()
            {
                Label = "Effettua movimento",
                Url = $"{baseUrl}/effettuamovimento/{this.IdMovimento}/{this.Step}",
                Active = true
            });

            this.Layout.SetBreadcrumbItems(breadcrumbItems);

            this._idMovimentoResolver.SetIdMovimento(this.IdMovimento);

            this._gestioneMovimentiHttpDataContext.Begin();

            this.GetViewmodel().SetIdMovimento(this.IdMovimento);

            if (this.GetViewmodel().CanEnterStep())
            {
                this._lastStepService.Set(this.Step);

                this.GoBackUrlQueryString = new Uri(this._navigationManager.Uri).Query;

#pragma warning disable VSTHRD103 // Call async methods when in an async method
                this.OnInitializedMovimenti();
#pragma warning restore VSTHRD103 // Call async methods when in an async method
                await this.OnInitializedMovimentiAsync();
            }
            else
            {
                if (this._lastStepService.Get() <= this.Step)
                {
                    await this.GoToNextStepInternalAsync();
                }
                else
                {
                    await this.GoToPreviousStepAsync();
                }
            }
        }

        protected sealed override void OnInitialized()
        {
            base.OnInitialized();
        }

        protected virtual void OnInitializedMovimenti()
        {

        }

        protected virtual Task OnInitializedMovimentiAsync()
        {
            return Task.CompletedTask;
        }

        protected override void OnAfterRender(bool firstRender)
        {
            base.OnAfterRender(firstRender);

            if (!firstRender)
                this._gestioneMovimentiHttpDataContext.Commit();
        }

        protected async Task GoToNextStepAsync()
        {
            if (!this.GetViewmodel().CanExitStep())
            {
                throw new InvalidOperationException("Richiesto di passaggio allo step successivo con verifica condizioni fallita");
            }

            await this.GoToNextStepInternalAsync();
        }

        private async Task GoToNextStepInternalAsync()
        {
            var url = this._workflow.GetNextStep(this.Step);
            await this.GotoAsync(url, $"{this.IdMovimento}/{this.Step + 1}/{this.GoBackUrl}{this.GoBackUrlQueryString}");
        }

        protected async Task GoToPreviousStepAsync()
        {
            var url = this._workflow.GetPreviousStep(this.Step);
            await this.GotoAsync(url, $"{this.IdMovimento}/{this.Step - 1}/{this.GoBackUrl}{this.GoBackUrlQueryString}");
        }

        protected async Task GoTo_GoBackURLAsync()
        {
            if (!string.IsNullOrEmpty(this.GoBackUrl))
                await this.GotoAsync($"{this.GoBackUrl}{this.GoBackUrlQueryString}");
        }

        //public string? UrlDownload(object codiceOggetto)
        //{
        //    if (codiceOggetto == null)
        //    {
        //        return null;
        //    }
        //    return this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(codiceOggetto));
        //}
    }
}
