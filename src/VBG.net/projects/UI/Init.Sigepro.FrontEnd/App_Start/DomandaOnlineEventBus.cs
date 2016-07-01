[assembly: WebActivatorEx.PostApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.DomandaOnlineEventBus), "Start")]

namespace Init.Sigepro.FrontEnd.App_Start
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;
	using Init.Sigepro.FrontEnd.AppLogic.IoC;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap;
	using Init.Sigepro.FrontEnd.AppLogic.AutomapperBootstrapper;

	public static class DomandaOnlineEventBus
	{
		public static void Start()
		{
			DomandaOnlineEventBusBootstrapper.Bootstrap();

			var config = (GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings)FoKernelContainer.Kernel.GetService(typeof(GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings));

			GestioneMovimentiBootstrapper.Bootstrap(config);

			AutomapperApplogicBootstrapper.Bootstrap();
		}
	}
}