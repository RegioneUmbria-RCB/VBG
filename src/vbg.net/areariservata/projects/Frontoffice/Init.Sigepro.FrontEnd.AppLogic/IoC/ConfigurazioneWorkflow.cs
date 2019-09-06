using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneWorkflow
    {
        public static IKernel RegistraWorkflow(this IKernel k)
        {
            k.Bind<IWorkflowService>().To<WorkflowFromCodiceIntervento>();

            return k;
        }


        
			
    }
}
