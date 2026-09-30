using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneLocalizzata : ConfigurationSection
	{
		[ConfigurationProperty("idComune", DefaultValue = "default", IsRequired = true)]
		public string IdComune 
		{ 
			get { return (string)this["idComune"]; } 
			set { this["idComune"] = value; } 
		}

		[ConfigurationProperty("paginaIniziale", DefaultValue = "default", IsRequired = true)]
		public string PaginaIniziale
		{
			get { return (string)this["paginaIniziale"]; }
			set { this["paginaIniziale"] = value; }
		}

		[ConfigurationProperty("processFile", IsRequired = false)]
		public ConfigurazioneProcessFile ProcessFile
		{
			get { return this["processFile"] as ConfigurazioneProcessFile; }
			set { this["processFile"] = value; }
		}

	}
}
