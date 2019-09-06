[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.RegistrazioneModuloHttpMovimenti), "Start")]


namespace Init.Sigepro.FrontEnd.App_Start
{

	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;
	using Microsoft.Web.Infrastructure.DynamicModuleHelper;
	using Init.Sigepro.FrontEnd.HttpModules;

	// 
	public static class RegistrazioneModuloHttpMovimenti
	{
		/// <summary>
		/// Starts the application
		/// </summary>
		public static void Start()
		{
			DynamicModuleUtility.RegisterModule(typeof(MovimentiUnitOfWorkModule));
		}
	}
}