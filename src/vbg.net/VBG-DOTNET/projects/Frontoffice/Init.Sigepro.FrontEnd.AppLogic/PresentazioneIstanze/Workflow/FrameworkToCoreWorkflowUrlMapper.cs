using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public class FrameworkToCoreWorkflowUrlMapper : IFrameworkToCoreWorkflowUrlMapper
    {
        public void AssicuraEsistenzaStepId(StepType step)
        {
            if (step.StepId != null)
            {
                return; // Lo step è già stato mappato, non serve fare nulla
            }

            if (String.IsNullOrEmpty(step.Control))
            {
                step.StepId = WorkflowSteps.Undefined;
                step.Disabled = true;

                return;
            }

            if (step.Control.EndsWith(".aspx", StringComparison.OrdinalIgnoreCase))
            {
                step.StepId = WorkflowDescriptor.GetStepIdByFrameworkPath(step.Control);

                return;
            }

            step.StepId = WorkflowDescriptor.GetStepIdByCorePath(step.Control);
        }
    }
}
