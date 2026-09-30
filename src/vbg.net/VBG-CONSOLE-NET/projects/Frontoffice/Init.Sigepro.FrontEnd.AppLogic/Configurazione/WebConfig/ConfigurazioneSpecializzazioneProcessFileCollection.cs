using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneSpecializzazioneProcessFileCollection : ConfigurazioneCollectionBase<ConfigurazioneSpecializzazioneProcessFile>
	{
		protected override object GetElementKey(ConfigurationElement element)
		{
			return ((ConfigurazioneSpecializzazioneProcessFile)element).Software;
		}
	}
}
