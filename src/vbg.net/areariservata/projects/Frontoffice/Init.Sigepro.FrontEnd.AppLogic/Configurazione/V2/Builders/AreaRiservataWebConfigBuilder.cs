using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class AreaRiservataWebConfigBuilder
	{
		private static class Constants
		{
			public const string SectionName = "sigepro/frontEnd";
		}


		protected ConfigurazioneFrontEndWebConfig GetConfigurazione()
		{
			ConfigurazioneFrontEndWebConfig config = (ConfigurazioneFrontEndWebConfig)System.Configuration.ConfigurationManager.GetSection(Constants.SectionName);

			return config;
		}
	}
}
