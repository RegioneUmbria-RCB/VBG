using System;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.PropertyBinder
{
    public class WorkflowStepPropertiesBinder
    {
        private readonly IWorkflowService _workflowService;

        public WorkflowStepPropertiesBinder(IWorkflowService workflowService)
        {
            this._workflowService = workflowService;
        }

        public void BindProperties(int idDomanda, int idStep, object stepPage)
        {
            var proprietaStep = this._workflowService.GetWorkflowByIdDomanda(idDomanda).GetProprietaStep(idStep);
            var type = stepPage.GetType();

            foreach (var prop in proprietaStep)
            {
                var propName = prop.Nome;
                var propValue = prop.Valore;

                var pi = type.GetProperty(propName);

                if (pi != null)
                    pi.SetValue(stepPage, Convert.ChangeType(propValue, pi.PropertyType), null);
            }
        }
    }
}
