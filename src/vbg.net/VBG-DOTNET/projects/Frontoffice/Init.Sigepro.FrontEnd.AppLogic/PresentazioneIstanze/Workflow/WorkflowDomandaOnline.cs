using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.MergepointFinder;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    internal class WorkflowDomandaOnline : IWorkflowDomandaOnline
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WorkflowDomandaOnline));
        protected readonly StepCollectionType _steps;

        public WorkflowDomandaOnline(StepCollectionType steps)
        {
            this._steps = steps;
        }

        private WorkflowDomandaOnline(IEnumerable<StepType> steps)
        {
            this._steps = new StepCollectionType(steps);
        }

        public string ToXmlString() => this._steps.ToXmlString();

        public bool IsFirstStep(int stepId)
        {
            return this._steps.IsFirstStep(stepId);
        }

        public bool IsLastStep(int stepId)
        {
            return this._steps.IsLastStep(stepId);
        }

        public bool IsStepDisabilitato(int stepId)
        {
            return this._steps.IsStepDisabilitato(stepId);
        }

        public string GetStepUrl(int stepId)
        {
            var identifier = this.GetStepIdentifier(stepId);

            if (identifier == WorkflowSteps.Undefined)
            {
                return this._steps.Steps[stepId].Control;
            }

            return WorkflowDescriptor.GetStepUrlById(identifier);
        }

        public WorkflowSteps GetStepIdentifier(int stepId)
        {
            this.VerificaEsistenzaStep(stepId);
            var step = this._steps.Steps[stepId];

            return step.StepId ?? throw new InvalidOperationException($"Lo step con indice {stepId} non ha un identificativo valido.");
        }

        public IEnumerable<string> GetTitoliSteps()
        {
            return this.MapSteps(x => x.Title);
        }

        public IEnumerable<ProprietaStep> GetProprietaStep(int stepId)
        {
            this.VerificaEsistenzaStep(stepId);

            return this._steps.Steps[stepId]
                              .ControlProperties?
                              .Select(x => new ProprietaStep(x.name, x.Value))
                              ?? Enumerable.Empty<ProprietaStep>();
        }

        public string GetTitoloStep(int stepId)
        {
            this.VerificaEsistenzaStep(stepId);

            return this._steps.Steps[stepId].Title;
        }

        public string GetDescrizioneStep(int stepId)
        {
            this.VerificaEsistenzaStep(stepId);

            return this._steps.Steps[stepId].Description;
        }

        private void VerificaEsistenzaStep(int stepId)
        {
            if (stepId < 0)
                throw new IndexOutOfRangeException($"Indice di step {stepId} non valido");

            if (stepId >= this._steps.Steps.Length)
                throw new IndexOutOfRangeException("Il workflow corrente non contiene uno step all'indice " + stepId);
        }

        public int NumeroSteps()
        {
            return this._steps.Steps.Length;
        }

        public IWorkflowDomandaOnline MergeWith(IWorkflowDomandaOnline workflowToMerge, IMergepointFinder mergePointFinder)
        {
            var mergePointId = mergePointFinder.FindMergePoint(this);

            if (mergePointId == -1)
            {
                return workflowToMerge;
            }

            return this.MergeWith(((WorkflowDomandaOnline)workflowToMerge)._steps, mergePointId);
        }

        private IWorkflowDomandaOnline MergeWith(StepCollectionType stepsToMerge, int mergePoint)
        {
            this._log.DebugFormat($"Unione del workflow: mergepoint={mergePoint}, numero di steps da unire={stepsToMerge.Steps.Length}");

            var newSteps = new List<StepType>();

            for (var i = 0; i <= mergePoint; i++)
            {
                var newStep = this._steps.Steps[i].Clone();

                newSteps.Add(newStep);
            }

            for (var i = 0; i < stepsToMerge.Steps.Length; i++)
            {
                var newStep = stepsToMerge.Steps[i].Clone();

                newSteps.Add(newStep);
            }

            this._log.Debug("Unione del workflow terminata");

            return new WorkflowDomandaOnline(newSteps);
        }

        public IEnumerable<T> MapSteps<T>(Func<StepType, T> map)
        {
            return this._steps.Steps.Select(map);
        }

        public int GetIndiceStepByIdentifier(WorkflowSteps identifier)
        {
            for (var i = 0; i < this._steps.Steps.Length; i++)
            {
                var step = this._steps.Steps[i];

                if (step.StepId == identifier)
                {
                    return i;
                }
            }
            return -1;
        }
    }
}
