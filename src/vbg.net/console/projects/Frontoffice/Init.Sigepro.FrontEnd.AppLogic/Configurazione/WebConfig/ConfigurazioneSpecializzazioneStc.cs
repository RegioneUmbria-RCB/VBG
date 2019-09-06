using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public class ConfigurazioneSpecializzazioneStc : ConfigurationElement
	{
		[ConfigurationProperty("software", IsRequired = true)]
		public string Software
		{
			get { return (string)this["software"]; }
			set { this["software"] = value; }
		}

		[ConfigurationProperty("idNodoMittente", IsRequired = false)]
		public string IdNodoMittente
		{
			get { return (string)this["idNodoMittente"]; }
			set { this["idNodoMittente"] = value; }
		}

		[ConfigurationProperty("idEnteMittente", IsRequired = false)]
		public string IdEnteMittente
		{
			get { return (string)this["idEnteMittente"]; }
			set { this["idEnteMittente"] = value; }
		}

		[ConfigurationProperty("idSportelloMittente", IsRequired = false)]
		public string IdSportelloMittente
		{
			get { return (string)this["idSportelloMittente"]; }
			set { this["idSportelloMittente"] = value; }
		}

		[ConfigurationProperty("idNodoDestinatario", IsRequired = false)]
		public string IdNodoDestinatario
		{
			get { return (string)this["idNodoDestinatario"]; }
			set { this["idNodoDestinatario"] = value; }
		}

		[ConfigurationProperty("idEnteDestinatario", IsRequired = false)]
		public string IdEnteDestinatario
		{
			get { return (string)this["idEnteDestinatario"]; }
			set { this["idEnteDestinatario"] = value; }
		}

		[ConfigurationProperty("idSportelloDestinatario", IsRequired = false)]
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
	}
}
