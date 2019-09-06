//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using System.Runtime.CompilerServices;

//namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
//{
//    /// <summary>
//    /// Responsabile del merge tra due workflows
//    /// </summary>
//    internal interface IWorkflowMergeService
//    {
//        /// <summary>
//        /// Unisce gli steps di due workflows
//        /// </summary>
//        /// <param name="sourceSteps">Steps di origine</param>
//        /// <param name="stepsToMerge">Steps da unire</param>
//        /// <param name="startMergeAtStep">Indice dello step a cui inizia l'unione (lo step contenuto all'indice specificato viene incluso nel nuovo workflow)</param>
//        /// <returns>Nuovo workflow generato dall'unione dei due set di steps</returns>
//        IWorkflowDomandaOnline Merge(StepCollectionType sourceSteps, StepCollectionType stepsToMerge, int startMergeAtStep);
//    }

//    internal class WorkflowMergeService : IWorkflowMergeService
//    {
//        #region IWorkflowMergeService Members

//        public IWorkflowDomandaOnline Merge(StepCollectionType sourceSteps, StepCollectionType stepsToMerge, int startMergeAtStep)
//        {
//            var newSteps = new List<StepType>();

//            for (int i = 0; i < startMergeAtStep; i++)
//            {
//                var newStep = sourceSteps.Step[i].Clone();

//                newSteps.Add(newStep);
//            }

//            for (int i = 0; i < stepsToMerge.Step.Length; i++ )
//            {
//                var newStep = stepsToMerge.Step[i].Clone();

//                newSteps.Add(newStep);
//            }

//            var stepsCollection = new StepCollectionType{
//                Step = newSteps.ToArray()
//            };

//            return new WorkflowDomandaOnline(stepsCollection);
//        }

//        #endregion
//    }

//}
