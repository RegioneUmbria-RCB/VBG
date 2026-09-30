using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriLogin : IParametriConfigurazione
	{
		public readonly string UrlLogin;
        public readonly string UsernameUtenteAnonimo;
        public readonly string PasswordUtenteAnonimo;
        public readonly bool UsaUrlRelativiPerRedirect;

        internal ParametriLogin(string urlLogin, string usernameUtenteAnonimo, string passwordUtenteAnonimo, bool usaUrlRelativiPerRedirect)
		{
			if (String.IsNullOrEmpty(urlLogin))
				throw new ArgumentNullException("urlLogin");

			this.UrlLogin = urlLogin;
            this.UsernameUtenteAnonimo = usernameUtenteAnonimo;
            this.PasswordUtenteAnonimo = passwordUtenteAnonimo;
            this.UsaUrlRelativiPerRedirect = usaUrlRelativiPerRedirect;
		}
	}
}
