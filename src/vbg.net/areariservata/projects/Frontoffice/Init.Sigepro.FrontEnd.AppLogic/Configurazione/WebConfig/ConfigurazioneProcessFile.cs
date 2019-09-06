using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneProcessFile: ConfigurationElement
	{
		[ConfigurationProperty("default", IsRequired=true)]
		public string Default
		{
			get { return this["default"].ToString(); }
			set{this["default"] = value;}
		}


		[ConfigurationProperty("specializzazioni", IsRequired = false)]
		public ConfigurazioneSpecializzazioneProcessFileCollection Specializzazioni
		{
			get
			{
				return this["specializzazioni"] as ConfigurazioneSpecializzazioneProcessFileCollection;
			}
		}



	}
}
