using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Ninject.Modules;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
	internal class WorkflowConfigurationModule : NinjectModule
	{

		public override void Load()
		{
			//Bind<IWorkflowService>().To<WorkflowFromConfigurazioneService>();
			Bind<IWorkflowService>().To<WorkflowFromCodiceIntervento>();
			
		}
	}
}
