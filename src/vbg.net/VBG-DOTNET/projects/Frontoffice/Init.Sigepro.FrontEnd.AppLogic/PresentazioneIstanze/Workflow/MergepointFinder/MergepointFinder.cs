using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.MergepointFinder
{
    public class MergepointFinder : IMergepointFinder
    {
        public int FindMergePoint(IWorkflowDomandaOnline workflow)
        {
            for (var i = 0; i < workflow.NumeroSteps(); i++)
            {
                var stepId = workflow.GetStepIdentifier(i);
                var isMergePoint = WorkflowDescriptor.IsMergePoint(stepId);

                if (isMergePoint)
                {
                    return i;
                }
            }

            return -1;
            // throw new InvalidOperationException("Step di selezione interventi non trovato nel workflow corrente");
        }
    }
}
