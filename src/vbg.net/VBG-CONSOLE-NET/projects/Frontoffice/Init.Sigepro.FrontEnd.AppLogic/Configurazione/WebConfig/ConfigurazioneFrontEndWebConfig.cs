using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneFrontEndWebConfig  : ConfigurationSection
	{
		[ConfigurationProperty("sigeproSecurity")]
		public ConfigurazioneSigeproSecurity SigeproSecurity
		{
			get{return this["sigeproSecurity"] as ConfigurazioneSigeproSecurity;}
			set{this["sigeproSecurity"] = value;}
		}

		[ConfigurationProperty("modalitaInvio",IsRequired=true,DefaultValue="VBG")]
		public string ModalitaInvio
		{
			get { return this["modalitaInvio"].ToString(); }
			set { this["modalitaInvio"] = value; }
		}

		[ConfigurationProperty("parametriPerIdComune")]
		public ConfigurazioneLocalizzataCollection ParametriPerIdComune
		{
			get 
			{
				return this["parametriPerIdComune"] as ConfigurazioneLocalizzataCollection;
			}
		}

		[ConfigurationProperty("parametriStc", IsRequired = false)]
		public ConfigurazioneStcCollection ParametriStc
		{
			get
			{
				return this["parametriStc"] as ConfigurazioneStcCollection;
			}
		}
	}
}
