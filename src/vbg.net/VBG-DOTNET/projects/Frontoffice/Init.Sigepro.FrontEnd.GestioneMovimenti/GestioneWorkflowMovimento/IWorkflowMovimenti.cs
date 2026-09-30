using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento
{
    public interface IWorkflowMovimenti
    {
        string GetNextStep(int currentStepId);
        string GetPreviousStep(int currentStepId);
    }
}
