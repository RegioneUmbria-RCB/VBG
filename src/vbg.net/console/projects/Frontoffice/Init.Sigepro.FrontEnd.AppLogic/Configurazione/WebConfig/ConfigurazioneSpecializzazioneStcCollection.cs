using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneSpecializzazioneStcCollection: ConfigurazioneCollectionBase<ConfigurazioneSpecializzazioneStc>
	{

		protected override object GetElementKey(ConfigurationElement element)
		{
			return ((ConfigurazioneSpecializzazioneStc)element).Software;
		}
	}
}
