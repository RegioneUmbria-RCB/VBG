using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriLogin : IParametriConfigurazione
	{
		public readonly string UrlLogin;

		internal ParametriLogin(string urlLogin)
		{
			if (String.IsNullOrEmpty(urlLogin))
				throw new ArgumentNullException("urlLogin");

			this.UrlLogin = urlLogin;
		}
	}
}
