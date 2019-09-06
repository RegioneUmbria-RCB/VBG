using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public class WorkflowGestioneMovimenti : IWorkflowMovimenti
    {
        string[] _steps;

        public WorkflowGestioneMovimenti()
        {
            this._steps = new string[]{
                "EffettuaMovimento.aspx",
                "CompilaSchedeDinamiche.aspx",
                "CaricamentoRiepiloghiSchede.aspx",
                "SostituzioniDocumentali.aspx",
                "CaricamentoAllegati.aspx",
                "RiepilogoEInvio.aspx",
                "DatiInviatiConSuccesso.aspx"
            };
        }


        public string GetNextStep(int currentStepId)
        {
            if (currentStepId >= this._steps.Length)
            {
                throw new InvalidOperationException("Lo step " + (currentStepId + 1) + " non è definito");
            }

            return this._steps[currentStepId + 1];
        }

        public string GetPreviousStep(int currentStepId)
        {
            if (currentStepId <= 0)
            {
                throw new InvalidOperationException("il workflow è già allo step 0");
            }

            return this._steps[currentStepId - 1];
        }
    }
}