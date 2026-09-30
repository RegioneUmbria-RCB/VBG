[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.Log4Net), "Start")]

namespace Init.Sigepro.FrontEnd.App_Start
{

	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;


	public static class Log4Net
	{
		public static void Start()
		{
			log4net.Config.XmlConfigurator.Configure();
		}
	}
}