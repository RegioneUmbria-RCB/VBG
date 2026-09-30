using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Microsoft.AspNetCore.Components;
// using System.Web;

namespace AreaRiservataCore.Pages.Movimenti
{
    public partial class IntegrazioneSIT
    {
        [Inject]
        protected IntegrazioneSITDaScadenzarioViewModel _viewModel { get; set; } = default!;

        [Inject]
        protected IIntegrazioneSITDaScadenzario _ldpService { get; set; } = default!;
        [Inject]
        protected IUrlEncoder _urlEncoder { get; set; } = default!;

        [Parameter]
        public bool? Returning { get; set; } = false;

        private readonly string DescrizioneStep = "";
        private string MessaggioRisposta = "";

        public override Task SetParametersAsync(ParameterView parameters)
        {
            this.Returning = this.Returning ?? false;

            return base.SetParametersAsync(parameters);
        }

        protected override async Task OnInitializedMovimentiAsync()
        {
            await base.OnInitializedMovimentiAsync();

            if (!this.CanEnterStep())
            {
                await this.GoToNextStepAsync();
                return;
            }

            if (!(this.Returning ?? false))
            {
                var movimentoDiOrigine = this._viewModel.GetMovimentoDiOrigine();
                var returnTo = _urlEncoder.UrlEncode(this._navigationManager.Uri + "/true");

                var redirUrl = this._ldpService.GetUrlCompilazioneMovimento(movimentoDiOrigine.DatiIstanza.CodiceIstanza, returnTo);

                await this.GotoAsync(redirUrl);
            }
            else
            {
                this.MessaggioRisposta = "Aggiornamento dei dati avvenuto correttamente, è ora possibile proseguire";
                await this.GoToNextStepAsync();
            }
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        protected bool CanEnterStep()
        {
            return this._viewModel.CanEnterStep();
        }

        private async Task OnBtnBackAsync()
        {
            await this.GoToPreviousStepAsync();
        }

        private async Task OnBtnNextAsync()
        {
            if (this.Returning ?? false)
                await this.GoToNextStepAsync();
        }
    }
}
