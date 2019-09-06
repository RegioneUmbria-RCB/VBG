using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneSpecializzazioneProcessFile: ConfigurationElement
	{
		[ConfigurationProperty("software", IsRequired = true)]
		public string Software
		{
			get { return (string)this["software"]; }
			set { this["software"] = value; }
		}

		[ConfigurationProperty("file", IsRequired = true)]
		public string File
		{
			get { return (string)this["file"]; }
			set { this["file"] = value; }
		}
	}
}
