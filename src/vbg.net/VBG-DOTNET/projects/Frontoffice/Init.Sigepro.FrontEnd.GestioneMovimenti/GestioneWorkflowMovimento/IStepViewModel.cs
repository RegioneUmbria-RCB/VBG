using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento
{
    public interface IStepViewModel
    {
        void SetIdMovimento(int idMovimento);

        bool CanEnterStep();
        bool CanExitStep();
    }
}
