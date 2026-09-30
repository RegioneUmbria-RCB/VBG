using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;

namespace AreaRiservataCore.Pages.Movimenti
{
    public class WorkflowGestioneMovimenti : IWorkflowMovimenti
    {
        string[] _steps;

        public WorkflowGestioneMovimenti()
        {
            this._steps = new string[]{
                "effettuamovimento",
                "integrazionesit",
                "compilaschededinamiche",
                "caricamentoriepiloghischede",
                "sostituzionidocumentali",
                "caricamentoallegati",
                "riepilogoeinvio",
                "datiinviaticonsuccesso"
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
