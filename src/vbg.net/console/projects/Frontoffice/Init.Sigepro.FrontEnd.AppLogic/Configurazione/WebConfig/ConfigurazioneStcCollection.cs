using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneStcCollection : ConfigurazioneCollectionBase<ConfigurazioneStc>
	{
		protected override object GetElementKey(ConfigurationElement element)
		{
			return ((ConfigurazioneStc)element).IdComune;
		}
	}
}
