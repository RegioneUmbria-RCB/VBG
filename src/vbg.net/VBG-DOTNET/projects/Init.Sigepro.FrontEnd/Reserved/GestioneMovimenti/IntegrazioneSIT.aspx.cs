using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class IntegrazioneSIT : MovimentiBasePage
    {
        [Inject]
        protected IntegrazioneSITDaScadenzarioViewModel _viewModel { get; set; }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }

        protected bool ReturningFromCallingPage
        {
            get
            {
                return !String.IsNullOrEmpty(Request.QueryString["returning"]);
            }
        }

        [Inject]
        protected IIntegrazioneSITDaScadenzario _ldpService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!CanEnterStep())
            {
                GoToNextStep();
            }

            if (!ReturningFromCallingPage)
            {

                var movimentoDiOrigine = this._viewModel.GetMovimentoDiOrigine();
                var returnTo = HttpUtility.UrlEncode(HttpContext.Current.Request.Url.AbsoluteUri + "&returning=true");

                var redirUrl = this._ldpService.GetUrlCompilazioneMovimento(movimentoDiOrigine.DatiIstanza.CodiceIstanza, returnTo);

                Response.Redirect(redirUrl);
                Response.End();
            }
            else
            {
                this.ltrMessaggioRisposta.Text = "Aggiornamento dei dati avvenuto correttamente, è ora possibile proseguire";
                GoToNextStep();
            }
        }

        protected bool CanEnterStep()
        {
            return this._viewModel.CanEnterStep();
        }

        protected void cmdProcedi_Click(object sender, EventArgs e)
        {
            if (ReturningFromCallingPage)
                GoToNextStep();
        }

        protected void cmdTornaIndietro_Click(object sender, EventArgs e)
        {
            GoToPreviousStep();
        }
    }
}