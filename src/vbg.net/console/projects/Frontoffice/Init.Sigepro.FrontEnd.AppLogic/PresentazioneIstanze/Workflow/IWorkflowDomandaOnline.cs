using System;
using System.Collections.Generic;
namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
	public interface IWorkflowDomandaOnline
	{
		string GetDescrizioneStep(int stepId);
		IEnumerable<ProprietaStep> GetProprietaStep(int stepId);
		string GetStepUrl(int stepId);
		IEnumerable<string> GetTitoliSteps();
		string GetTitoloStep(int stepId);
		bool IsFirstStep(int stepId);
		bool IsLastStep(int stepId);
		int NumeroSteps();
		IWorkflowDomandaOnline MergeWith(StepCollectionType stepsToMerge, int mergePoint);
        int TrovaIndiceStepDaUrlParziale(string testoParziale);
    }
}
