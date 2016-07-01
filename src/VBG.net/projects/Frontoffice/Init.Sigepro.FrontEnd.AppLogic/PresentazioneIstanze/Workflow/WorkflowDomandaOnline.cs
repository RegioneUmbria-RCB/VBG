using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
	internal class WorkflowDomandaOnline : IWorkflowDomandaOnline
	{
		ILog _log = LogManager.GetLogger(typeof(WorkflowDomandaOnline));
		StepCollectionType _steps;

		public WorkflowDomandaOnline(StepCollectionType steps)
		{
			this._steps = steps;
		}

		public bool IsFirstStep(int stepId)
		{
			return this._steps.IsFirstStep(stepId);
		}

		public bool IsLastStep(int stepId)
		{
			return this._steps.IsLastStep( stepId );
		}

		public string GetStepUrl(int stepId)
		{
			return this._steps.GetStepUrl( stepId );
		}

		public IEnumerable<string> GetTitoliSteps()
		{
			return this._steps.GetTitoliSteps();
		}

		public IEnumerable<ProprietaStep> GetProprietaStep(int stepId)
		{
			VerificaEsistenzaStep(stepId);

			if (this._steps.Step[stepId].ControlProperty == null)
				return new List<ProprietaStep>();

			return this._steps.Step[stepId]
							  .ControlProperty
							  .Select(x => new ProprietaStep(x.name, x.Value));
		}

		public string GetTitoloStep(int stepId)
		{
			VerificaEsistenzaStep(stepId);

			return this._steps.Step[stepId].Title;
		}

		public string GetDescrizioneStep(int stepId)
		{
			VerificaEsistenzaStep(stepId);

			return this._steps.Step[stepId].Description;
		}

		private void VerificaEsistenzaStep(int stepId)
		{
			if (stepId >= this._steps.Step.Length)
				throw new IndexOutOfRangeException("Il workflow corrente non contiene uno step all'indice " + stepId);
		}

		public int NumeroSteps()
		{
			return this._steps.Step.Length;
		}

		
		internal StepType GetStep(int stepId)
		{
			return this._steps.Step[stepId];
		}

		public IWorkflowDomandaOnline MergeWith(StepCollectionType stepsToMerge, int mergePoint)
		{
			_log.DebugFormat("Unione del workflow: mergepoint={0}, numero di steps da unire={1} ", mergePoint , stepsToMerge.Step.Length);				

			var newSteps = new List<StepType>();

			for (int i = 0; i < mergePoint ; i++)
			{
				var newStep = this._steps.Step[i].Clone();

				newSteps.Add(newStep);
			}

			for (int i = 0; i < stepsToMerge.Step.Length; i++ )
			{
				var newStep = stepsToMerge.Step[i].Clone();

				newSteps.Add(newStep);
			}

			var stepsCollection = new StepCollectionType{
				Step = newSteps.ToArray()
			};

			_log.Debug("Unione del workflow terminata");	

			return new WorkflowDomandaOnline(stepsCollection);
		}



        public int TrovaIndiceStepDaUrlParziale(string testoParziale)
        {
            for (int i = 0; i < this._steps.Step.Length; i++)
			{
			    var step = this._steps.Step[i];

                if (step.Control.ToUpperInvariant().Contains(testoParziale.ToUpperInvariant()))
                {
                    return i;
                }

			}

            return -1;
        }
    }
}
