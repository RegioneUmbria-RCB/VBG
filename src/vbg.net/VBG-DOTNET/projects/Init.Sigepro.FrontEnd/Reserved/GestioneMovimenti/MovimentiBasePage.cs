using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using log4net;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public abstract class MovimentiBasePage : ReservedBasePage
    {
        private static class Constants
        {
            public const string CurrentStepId = "step";
            public const string LastStepKey = "Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.MovimentiBasePage.LastStep";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(MovimentiBasePage));
        private readonly WorkflowGestioneMovimenti _workflow = new WorkflowGestioneMovimenti();

        [Inject]
        protected IIdMovimentoResolver _idMovimentoResolver { get; set; }

        //[Inject]
        //public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        protected abstract IStepViewModel GetViewmodel();

        private int LastStep
        {
            get
            {
                var lastStep = this.Session[Constants.LastStepKey];

                return lastStep == null ? 0 : (int)lastStep;
            }

            set
            {
                this.Session[Constants.LastStepKey] = value;
            }
        }

        protected int IdMovimento
        {
            get
            {
                return this._idMovimentoResolver.IdMovimento;
            }
        }

        protected int CurrentStepId
        {
            get
            {
                var csi = this.Request.QueryString[Constants.CurrentStepId];

                if (String.IsNullOrEmpty(csi))
                {
                    return 0;
                }

                return Convert.ToInt32(csi);
            }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            this.GetViewmodel().SetIdMovimento(this.IdMovimento);

            if (this.GetViewmodel().CanEnterStep())
            {
                this.LastStep = this.CurrentStepId;
            }
            else
            {
                if (this.LastStep <= this.CurrentStepId)
                {
                    this.GoToNextStepInternal();
                }
                else
                {
                    this.GoToPreviousStep();
                }

            }
        }

        protected void GoToNextStep()
        {
            if (!this.GetViewmodel().CanExitStep())
            {
                throw new InvalidOperationException("Richiesto di passaggio allo step successivo con verifica condizioni fallita");
            }

            this.GoToNextStepInternal();
        }

        private void GoToNextStepInternal()
        {
            var url = this._workflow.GetNextStep(this.CurrentStepId);

            this.Redirect("~/Reserved/GestioneMovimenti/" + url, qs =>
            {
                qs.Add("IdMovimento", this.IdMovimento);
                qs.Add(Constants.CurrentStepId, this.CurrentStepId + 1);
            });
        }

        protected void GoToPreviousStep()
        {
            var url = this._workflow.GetPreviousStep(this.CurrentStepId);

            this.Redirect("~/Reserved/GestioneMovimenti/" + url, qs =>
            {
                qs.Add("IdMovimento", this.IdMovimento);
                qs.Add(Constants.CurrentStepId, this.CurrentStepId - 1);
            });
        }

        //public string UrlDownload(object codiceOggetto)
        //{
        //    if (codiceOggetto == null)
        //    {
        //        return null;
        //    }
        //    return this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(codiceOggetto));
        //}
    }
}