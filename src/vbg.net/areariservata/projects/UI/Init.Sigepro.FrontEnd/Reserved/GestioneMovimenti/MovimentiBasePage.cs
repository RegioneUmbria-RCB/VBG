using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public abstract class MovimentiBasePage : ReservedBasePage
	{
        private static class Constants
        {
            public const string CurrentStepId = "step";
            public const string LastStepKey = "Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.MovimentiBasePage.LastStep";
        }

		ILog _log = LogManager.GetLogger(typeof(MovimentiBasePage));
        WorkflowGestioneMovimenti _workflow = new WorkflowGestioneMovimenti();

		[Inject]
		protected IIdMovimentoResolver _idMovimentoResolver { get; set; }

        protected abstract IStepViewModel GetViewmodel();

        private int LastStep
        {
            get
            {
                var lastStep = Session[Constants.LastStepKey];

                return lastStep == null ? 0 : (int)lastStep;
            }

            set
            {
                Session[Constants.LastStepKey] = value;
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
                var csi = Request.QueryString[Constants.CurrentStepId];

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

            this.GetViewmodel().SetIdMovimento(IdMovimento);

            if (this.GetViewmodel().CanEnterStep())
            {
                this.LastStep = this.CurrentStepId;
            }
            else
            {
                if (this.LastStep <= this.CurrentStepId)
                {
                    GoToNextStepInternal();
                } 
                else
                {
                    GoToPreviousStep();
                }
                
            }
        }

        protected void GoToNextStep()
        {
            if (!this.GetViewmodel().CanExitStep())
            {
                throw new InvalidOperationException("Richiesto di passaggio allo step successivo con verifica condizioni fallita");
            }

            GoToNextStepInternal();
        }

        private void GoToNextStepInternal()
        {
            var url = this._workflow.GetNextStep(CurrentStepId);

            Redirect("~/Reserved/GestioneMovimenti/" + url, qs =>
            {
                qs.Add("IdMovimento", IdMovimento);
                qs.Add(Constants.CurrentStepId, CurrentStepId + 1);
            });
        }

        protected void GoToPreviousStep()
        {
            var url = this._workflow.GetPreviousStep(CurrentStepId);

            Redirect("~/Reserved/GestioneMovimenti/" + url, qs =>
            {
                qs.Add("IdMovimento", IdMovimento);
                qs.Add(Constants.CurrentStepId, CurrentStepId - 1);
            });
        }
	}
}