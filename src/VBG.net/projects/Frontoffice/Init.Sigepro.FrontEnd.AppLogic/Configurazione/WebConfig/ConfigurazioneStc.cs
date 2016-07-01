using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneStc : ConfigurationSection
	{
		[ConfigurationProperty("idComune", DefaultValue = "default", IsRequired = true,IsKey=true)]
		public string IdComune
		{
			get { return (string)this["idComune"]; }
			set { this["idComune"] = value; }
		}

		[ConfigurationProperty("urlInvio", IsRequired = true)]
		public string UrlInvio
		{
			get { return (string)this["urlInvio"]; }
			set { this["urlInvio"] = value; }
		}

		[ConfigurationProperty("idNodoMittente",  IsRequired = true)]
		public string IdNodoMittente
		{
			get { return (string)this["idNodoMittente"]; }
			set { this["idNodoMittente"] = value; }
		}

		[ConfigurationProperty("idEnteMittente",  IsRequired = true)]
		public string IdEnteMittente
		{
			get { return (string)this["idEnteMittente"]; }
			set { this["idEnteMittente"] = value; }
		}

		[ConfigurationProperty("idSportelloMittente",  IsRequired = true)]
		public string IdSportelloMittente
		{
			get { return (string)this["idSportelloMittente"]; }
			set { this["idSportelloMittente"] = value; }
		}

		[ConfigurationProperty("idNodoDestinatario", IsRequired = true)]
		public string IdNodoDestinatario
		{
			get { return (string)this["idNodoDestinatario"]; }
			set { this["idNodoDestinatario"] = value; }
		}

		[ConfigurationProperty("idEnteDestinatario", IsRequired = true)]
		public string IdEnteDestinatario
		{
			get { return (string)this["idEnteDestinatario"]; }
			set { this["idEnteDestinatario"] = value; }
		}

		[ConfigurationProperty("idSportelloDestinatario", IsRequired = true)]
		public string IdSportelloDestinatario
		{
			get { return (string)this["idSportelloDestinatario"]; }
			set { this["idSportelloDestinatario"] = value; }
		}



		[ConfigurationProperty("username", IsRequired = true)]
		public string Username
		{
			get { return (string)this["username"]; }
			set { this["username"] = value; }
		}

		[ConfigurationProperty("password", IsRequired = true)]
		public string Password
		{
			get { return (string)this["password"]; }
			set { this["password"] = value; }
		}

		[ConfigurationProperty("specializzazioni", IsRequired = false)]
		public ConfigurazioneSpecializzazioneStcCollection Specializzazioni
		{
			get
			{
				return this["specializzazioni"] as ConfigurazioneSpecializzazioneStcCollection;
			}
		}

	}
}
