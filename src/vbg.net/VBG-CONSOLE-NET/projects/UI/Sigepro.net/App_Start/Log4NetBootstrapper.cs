[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Sigepro.net.App_Start.Log4NetBootstrapper), "Start")]

namespace Sigepro.net.App_Start
{
    using log4net;
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;


	public static class Log4NetBootstrapper
	{
		public static void Start()
		{
			log4net.Config.XmlConfigurator.Configure();

            var log = LogManager.GetLogger(typeof(Sigepro.net.App_Start.Log4NetBootstrapper));

            log.Info("Applicazione avviata");
		}
	}
}