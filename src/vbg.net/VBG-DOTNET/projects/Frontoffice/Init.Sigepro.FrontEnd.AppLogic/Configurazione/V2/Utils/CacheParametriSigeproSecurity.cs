using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils
{
    public class CacheParametriSigeproSecurity
    {
        private readonly Dictionary<string, string> _cacheParametri = new Dictionary<string, string>();
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly ILog _log = LogManager.GetLogger(typeof(CacheParametriSigeproSecurity));

        public CacheParametriSigeproSecurity(SigeproSecurityProxy sigeproSecurityProxy, IAppConfigurationReader appConfigurationReader)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;
            this._appConfigurationReader = appConfigurationReader;
        }

        public string GetValoreCache(string nomeParametro, bool throwIfNowFound = true)
        {
            if (!this._cacheParametri.Any())
            {
                this.BuildCacheParametri();
            }

            var valore = this._appConfigurationReader.GetSetting(nomeParametro);

            if (!String.IsNullOrEmpty(valore))
            {
                this._log.DebugFormat("il parametro {0} è stato letto dal web.config, valore={1}", nomeParametro, valore);
                return valore;
            }

            if (this._cacheParametri.ContainsKey(nomeParametro))
            {
                valore = this._cacheParametri[nomeParametro];

                if (throwIfNowFound && String.IsNullOrEmpty(valore))
                    throw new Exception("IBCSECURITY non ha un valore per il parametro " + nomeParametro);

                // _log.InfoFormat("il parametro {0} è stato letto dal servizio sigeprosecurity, valore={1}", nomeParametro, valore);

                return valore;
            }

            if (throwIfNowFound)
                throw new Exception("IBCSECURITY non contiene il parametro " + nomeParametro);

            return valore;
        }

        private void BuildCacheParametri()
        {
            var appInfo = this._sigeproSecurityProxy.GetApplicationInfo();

            for (int i = 0; i < appInfo.Length; i++)
            {
                this._cacheParametri.Add(appInfo[i].param, appInfo[i].value);
            }
        }
    }
}
