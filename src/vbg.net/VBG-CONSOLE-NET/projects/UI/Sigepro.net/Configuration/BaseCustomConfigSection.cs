using System;
using System.Configuration;

namespace SIGePro.Net.Configuration
{
	/// <summary>
	/// Descrizione di riepilogo per BaseCustomConfigSection.
	/// </summary>
	public class BaseCustomConfigSection
	{
		public BaseCustomConfigSection()
		{
		}

		protected static object GetSection( string idComune , string groupName , string sectionName )
		{
			//object conf = System.Configuration.ConfigurationSettings.GetConfig(groupName + "_" + idComune + "/" + sectionName);
			object conf = ConfigurationManager.GetSection(groupName + "_" + idComune + "/" + sectionName);

			if ( conf == null )
				conf = ConfigurationManager.GetSection(groupName + "_Common/" + sectionName);
				//conf = System.Configuration.ConfigurationSettings.GetConfig(groupName + "_Common/" + sectionName);

			if (conf == null)
				throw new ConfigurationErrorsException(String.Format("Impossibile caricare il tipo della sezione {0}, verificare il file di configurazione.",groupName + "_Common/" + sectionName));

			return conf;
		}
	}
}
