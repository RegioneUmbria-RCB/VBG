[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.AuthenticationHttpModule), "PreApplicationStart")]

namespace Init.Sigepro.FrontEnd.App_Start
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;
	using Init.Sigepro.FrontEnd.HttpModules;
	using Microsoft.Web.Infrastructure.DynamicModuleHelper;

	public class AuthenticationHttpModule
	{
		public static void PreApplicationStart()
		{

			DynamicModuleUtility.RegisterModule(typeof(AuthenticationModule));
            DynamicModuleUtility.RegisterModule(typeof(ScrivaniaEntiTerziModule));
		}
	}
}