using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneLocalizzataCollection : ConfigurazioneCollectionBase<ConfigurazioneLocalizzata>
	{
		protected override object GetElementKey(ConfigurationElement element)
		{
			return ((ConfigurazioneLocalizzata)element).IdComune;
		}
	}
}
