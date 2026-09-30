using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using log4net;
using System;
using System.Collections.Generic;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriSigeproSecurityBuilder : AreaRiservataWebConfigBuilder, IConfigurazioneBuilder<ParametriSigeproSecurity>
    {
        private static class Constants
        {
            public const string APP_ASP = "APP_ASP";
            public const string APP_ASPNET = "APP_ASPNET";
            public const string APP_JAVA = "APP_JAVA";
            public const string AUDIT_SERVICE_URL = "AUDIT_SERVICE_URL";
            public const string AUTHENTICATION_GATEWAY_FO_URL = "AUTHENTICATION_GATEWAY_FO_URL";
            public const string AUTHENTICATION_GATEWAY_FO_URLCIE = "AUTHENTICATION_GATEWAY_FO_URLCIE";
            public const string AUTHENTICATION_GATEWAY_URL = "AUTHENTICATION_GATEWAY_URL";
            public const string BASE_URL = "BASE_URL";
            public const string MAIL_ACCEPT_INVALID_CERTIFICATES = "ACCEPT_INVALID_CERTIFICATES";
            public const string MAIL_LOGINNAME = "LOGINNAME";
            public const string MAIL_MAILSERVER = "MAILSERVER";
            public const string MAIL_PASSWORD = "PASSWORD";
            public const string MAIL_SENDER = "SENDER";
            public const string MAIL_SMTP_PORT = "SMTP_PORT";
            public const string MAIL_USE_AUTHENTICATION = "USE_AUTHENTICATION";
            public const string MAIL_USE_SSL = "USE_SSL";
            public const string TOKEN_TIMEOUT = "TOKEN_TIMEOUT";
            public const string WSHOSTURL_ASPNET = "WSHOSTURL_ASPNET";
            public const string WSHOSTURL_EXPORT = "WSHOSTURL_EXPORT";
            public const string WSHOSTURL_FILECONVERTER = "WSHOSTURL_FILECONVERTER";
            public const string WSHOSTURL_FIRMADIGITALE = "WSHOSTURL_FIRMADIGITALE";
            public const string WSHOSTURL_FIRMADIGITALE_REST = "WSHOSTURL_FIRMADIGITALE_REST";
            public const string WSHOSTURL_JAVA = "WSHOSTURL_JAVA";
            public const string WSHOSTURL_RENDER = "WSHOSTURL_RENDER";
            public const string WSHOSTURL_PDFUTILS = "WSHOSTURL_PDFUTILS";
            public const string WSHOSTURL_APIBACKEND = "WSHOSTURL_APIBACKEND";

        }

        private readonly ILog _log = LogManager.GetLogger(typeof(ParametriSigeproSecurityBuilder));
        private readonly Dictionary<string, string> _cacheParametri = new Dictionary<string, string>();

        public ParametriSigeproSecurityBuilder()
        {
        }

        #region IBuilder<ParametriSigeproSecurity> Members

        public ParametriSigeproSecurity Build()
        {
            this.BuildCacheParametri();

            var sigeproAspNetBaseUrl = this.GetValoreCache(Constants.WSHOSTURL_ASPNET);
            var sigeproJavaBaseUrl = this.GetValoreCache(Constants.WSHOSTURL_JAVA);
            var urlApiBackend = this.GetValoreCache(Constants.WSHOSTURL_APIBACKEND, false);
            var pdfUtilsServiceUrl = this.CaricaPdfutilsServiceUrl();

            var aspNetBaseUrlOverride = ConfigurationManager.AppSettings["overrideAspNetBaseUrl"];
            var javaBaseUrlOverride = ConfigurationManager.AppSettings["overrideJavaBaseUrl"];

            if (!string.IsNullOrEmpty(aspNetBaseUrlOverride))
                sigeproAspNetBaseUrl = aspNetBaseUrlOverride;

            if (!string.IsNullOrEmpty(javaBaseUrlOverride))
                sigeproJavaBaseUrl = javaBaseUrlOverride;

            var tokenTimeout = this.GetValoreTokenTimeout();

            var urlConversioneFileService = this.GetValoreCache(Constants.WSHOSTURL_FILECONVERTER);
            var urlVerificaFirmaService = this.GetValoreCache(Constants.WSHOSTURL_FIRMADIGITALE);
            var urlVerificaFirmaRestService = this.GetValoreCache(Constants.WSHOSTURL_FIRMADIGITALE_REST);

            return new ParametriSigeproSecurity(sigeproAspNetBaseUrl, sigeproJavaBaseUrl, urlApiBackend,
                                                urlConversioneFileService, urlVerificaFirmaService, tokenTimeout,
                                                this._cacheParametri, pdfUtilsServiceUrl, urlVerificaFirmaRestService);

        }

        private string CaricaPdfutilsServiceUrl()
        {
            return this.GetValoreCache(Constants.WSHOSTURL_PDFUTILS, false);
        }

        private string GetValoreCache(string nomeParametro, bool throwIfNowFound = true)
        {
            var valore = ConfigurationManager.AppSettings[nomeParametro];

            if (!String.IsNullOrEmpty(valore))
            {
                this._log.DebugFormat("il parametro {0} è stato letto dal web.config, valore={1}", nomeParametro, valore);
                return valore;
            }




            if (this._cacheParametri.ContainsKey(nomeParametro))
            {
                valore = this._cacheParametri[nomeParametro];

                if (throwIfNowFound && String.IsNullOrEmpty(valore))
                    throw new ConfigurationErrorsException("IBCSECURITY non ha un valore per il parametro " + nomeParametro);

                // _log.InfoFormat("il parametro {0} è stato letto dal servizio sigeprosecurity, valore={1}", nomeParametro, valore);

                return valore;
            }

            if (throwIfNowFound)
                throw new ConfigurationErrorsException("IBCSECURITY non contiene il parametro " + nomeParametro);

            return valore;
        }

        private int GetValoreTokenTimeout()
        {
            var valore = this.GetValoreCache(Constants.TOKEN_TIMEOUT);

            return String.IsNullOrEmpty(valore) ? 0 : Convert.ToInt32(valore);
        }


        private void BuildCacheParametri()
        {
            var appInfo = new SigeproSecurityProxy().GetApplicationInfo();

            for (var i = 0; i < appInfo.Length; i++)
            {
                this._cacheParametri.Add(appInfo[i].param, appInfo[i].value);
            }
        }

        #endregion
    }
}
