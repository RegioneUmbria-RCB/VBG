using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Ninject.Modules;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
	public static class ModuliWebAreaRiservata
	{
		public static NinjectModule[] Get()
		{
			return new NinjectModule[]{
				new ConfigurazioneAreaRiservataModule(),
				new ServiceCreatorsModule(),
				new WorkflowConfigurationModule(),
				new BariNinjectModule(),
                new PagamentiNinjectModule(),
				new IoCConfigurationModule()	// Deve sempre essere l'ultimo 				
			};
		}
	}
}
