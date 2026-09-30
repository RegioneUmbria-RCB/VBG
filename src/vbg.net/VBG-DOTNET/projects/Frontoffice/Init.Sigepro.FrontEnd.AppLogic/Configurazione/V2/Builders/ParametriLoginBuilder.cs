using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriLoginBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriLogin>
    {
        private static class Constants
        {
            public const string DefaultUrllogin = "AUTHENTICATION_GATEWAY_FO_URL";
        }

        private readonly IConfigurazione<ParametriSigeproSecurity> _sigeproSecurity;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly CacheParametriSigeproSecurity _cacheParametriSigeproSecurity;

        public ParametriLoginBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository,
                                    IConfigurazione<ParametriSigeproSecurity> sigeproSecurity, IAppConfigurationReader appConfigurationReader, CacheParametriSigeproSecurity cacheParametriSigeproSecurity)
                                    : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._sigeproSecurity = sigeproSecurity;
            this._appConfigurationReader = appConfigurationReader;
            this._cacheParametriSigeproSecurity = cacheParametriSigeproSecurity;
        }


        #region IBuilder<ParametriLogin> Members

        public ParametriLogin Build()
        {
            var cfg = this.GetConfig();
            var nomeParametroUrlLogin = cfg.NomeParametroUrlLogin;
            var usernameAnonimo = cfg.UsernameUtenteAnonimo;
            var passwordAnonimo = cfg.PasswordUtenteAnonimo;

            if (string.IsNullOrEmpty(nomeParametroUrlLogin))
                nomeParametroUrlLogin = Constants.DefaultUrllogin;

            var urlLogin = this._cacheParametriSigeproSecurity.GetValoreCache(nomeParametroUrlLogin, true);

            if (!String.IsNullOrEmpty(cfg.UrlAuthenticationOverride))
            {
                urlLogin = cfg.UrlAuthenticationOverride;
            }

            var usaUrlRelativiPerRedirectCfg = this._appConfigurationReader.GetSetting("UsaUrlRelativiPerRedirect");

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
