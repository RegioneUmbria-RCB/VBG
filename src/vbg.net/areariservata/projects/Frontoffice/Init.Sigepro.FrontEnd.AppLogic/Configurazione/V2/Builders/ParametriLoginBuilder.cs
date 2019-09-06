using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriLoginBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriLogin>
	{
		private static class Constants
		{
			public const string DefaultUrllogin ="AUTHENTICATION_GATEWAY_FO_URL"; 
		}

		IConfigurazione<ParametriSigeproSecurity> _sigeproSecurity;

		public ParametriLoginBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IConfigurazione<ParametriSigeproSecurity> sigeproSecurity)
			: base(aliasResolver, configurazioneAreaRiservataRepository)
		{
			this._sigeproSecurity = sigeproSecurity;
		}


        #region IBuilder<ParametriLogin> Members

        public ParametriLogin Build()
        {
            var cfg = GetConfig();
            var nomeParametroUrlLogin = cfg.NomeParametroUrlLogin;
            var usernameAnonimo = cfg.UsernameUtenteAnonimo;
            var passwordAnonimo = cfg.PasswordUtenteAnonimo;

            if (string.IsNullOrEmpty(nomeParametroUrlLogin))
                nomeParametroUrlLogin = Constants.DefaultUrllogin;

            var urlLogin = this._sigeproSecurity.Parametri.AltriParametri[nomeParametroUrlLogin];

            if (!String.IsNullOrEmpty(cfg.UrlAuthenticationOverride))
            {
                urlLogin = cfg.UrlAuthenticationOverride;
            }

            var usaUrlRelativiPerRedirectCfg = ConfigurationManager.AppSettings["UsaUrlRelativiPerRedirect"];

            var usaUrlRelativiPerRedirect = false;

            if (!String.IsNullOrEmpty(usaUrlRelativiPerRedirectCfg) && usaUrlRelativiPerRedirectCfg.ToUpper() == "TRUE")
            {
                usaUrlRelativiPerRedirect = true;
            }



            return new ParametriLogin(urlLogin, usernameAnonimo, passwordAnonimo, usaUrlRelativiPerRedirect);
		}

		#endregion
	}
}
