using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.MergepointFinder;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using System;
using System.Collections.Generic;
namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public interface IWorkflowDomandaOnline
    {
        IEnumerable<T> MapSteps<T>(Func<StepType, T> map);
        string GetDescrizioneStep(int stepId);
        IEnumerable<ProprietaStep> GetProprietaStep(int stepId);
        string GetStepUrl(int stepId);
        WorkflowSteps GetStepIdentifier(int stepId);
        IEnumerable<string> GetTitoliSteps();
        string GetTitoloStep(int stepId);
        bool IsFirstStep(int stepId);
        bool IsLastStep(int stepId);
        int NumeroSteps();
        bool IsStepDisabilitato(int stepId);
        IWorkflowDomandaOnline MergeWith(IWorkflowDomandaOnline workflowToMerge, IMergepointFinder mergePointFinder);
        int GetIndiceStepByIdentifier(WorkflowSteps identifier);
        string ToXmlString();
    }
}
