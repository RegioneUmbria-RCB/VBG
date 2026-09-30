using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;

namespace Init.SIGePro.Manager.Configuration
{
	public class ParametriConfigurazione
	{
		protected ParametriConfigurazione(){}

		public static ConfigurazioneGenerale Get
		{
			get
			{
				if (HttpContext.Current != null)
					return GetConfigurazioneDaContext();

				return GetConfigurazioneStatic();
			}
		}

		private static ConfigurazioneGenerale GetConfigurazioneStatic()
		{
			return new ConfigurazioneGenerale(SigeproSecurityProxy.GetApplicationInfo(new GetApplicationInfoRequest()));
		}

		private static ConfigurazioneGenerale GetConfigurazioneDaContext()
		{
			if (HttpContext.Current.Items["Configurazione"] == null)
			{
				HttpContext.Current.Items["Configurazione"] = new ConfigurazioneGenerale(SigeproSecurityProxy.GetApplicationInfo(new GetApplicationInfoRequest()));
			}

			return (ConfigurazioneGenerale)HttpContext.Current.Items["Configurazione"];
		}

		
	}
}
