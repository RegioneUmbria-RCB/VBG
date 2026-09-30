using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriSigeproSecurityBuilder : IConfigurazioneBuilder<ParametriSigeproSecurity>
    {
        private static class Constants
        {
            public const string TOKEN_TIMEOUT = "TOKEN_TIMEOUT";
            public const string WSHOSTURL_ASPNET = "WSHOSTURL_ASPNET";
            public const string WSHOSTURL_ASPNET_CORE = "WSHOSTURL_ASPNET_CORE";
            public const string WSHOSTURL_FILECONVERTER = "WSHOSTURL_FILECONVERTER";
            ///public const string WSHOSTURL_FIRMADIGITALE = "WSHOSTURL_FIRMADIGITALE";
            public const string WSHOSTURL_FIRMADIGITALE_REST = "WSHOSTURL_FIRMADIGITALE_REST";
            public const string WSHOSTURL_JAVA = "WSHOSTURL_JAVA";
            public const string WSHOSTURL_PDFUTILS = "WSHOSTURL_PDFUTILS";
            public const string WSHOSTURL_APIBACKEND = "WSHOSTURL_APIBACKEND";
            public const string WSHOSTURL_SERVIZI_REST_SECURITY = "WSHOSTURL_SERVIZI_REST_SECURITY";
            public const string WSHOSTURL_CARTOGRAFICO = "WSHOSTURL_CARTOGRAFICO";
            public const string WSHOSTURL_MAILSERVICE = "WSHOSTURL_MAILSERVICE";
            public const string WSHOSTURL_SIT = "WSHOSTURL_SIT";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(ParametriSigeproSecurityBuilder));
        private readonly CacheParametriSigeproSecurity _cacheParametriSigeproSecurity;
        private readonly IAppConfigurationReader _appConfigurationReader;

        public ParametriSigeproSecurityBuilder(CacheParametriSigeproSecurity cacheParametriSigeproSecurity, IAppConfigurationReader appConfigurationReader)
        {
            this._cacheParametriSigeproSecurity = cacheParametriSigeproSecurity;
            this._appConfigurationReader = appConfigurationReader;
        }

        #region IBuilder<ParametriSigeproSecurity> Members

        public ParametriSigeproSecurity Build()
        {
            var aspNetBaseUrl = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_ASPNET);
            var aspNetCoreBaseUrl = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_ASPNET_CORE, false);

            var sigeproJavaBaseUrl = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_JAVA);
            var urlApiBackend = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_APIBACKEND, false);
            var pdfUtilsServiceUrl = this.CaricaPdfutilsServiceUrl();

            var aspNetBaseUrlOverride = this._appConfigurationReader.GetSetting("overrideAspNetBaseUrl");
            var javaBaseUrlOverride = this._appConfigurationReader.GetSetting("overrideJavaBaseUrl");

            if (!string.IsNullOrEmpty(aspNetBaseUrlOverride))
                aspNetBaseUrl = aspNetBaseUrlOverride;

            if (!string.IsNullOrEmpty(javaBaseUrlOverride))
                sigeproJavaBaseUrl = javaBaseUrlOverride;

            var tokenTimeout = this.GetValoreTokenTimeout();

            var urlConversioneFileService = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_FILECONVERTER);
            var urlVerificaFirmaService = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_FIRMADIGITALE_REST);


            var urlServiziRestSecurity = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_SERVIZI_REST_SECURITY, false) ?? "";
            var urlServizioRestCartografico = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_CARTOGRAFICO, false) ?? "";
            var urlMailService = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_MAILSERVICE, false) ?? "";
            var urlSIT = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_SIT, false) ?? "";

            return new ParametriSigeproSecurity(aspNetBaseUrl, aspNetCoreBaseUrl, sigeproJavaBaseUrl, urlApiBackend,
                                                urlConversioneFileService, urlVerificaFirmaService, tokenTimeout,
                                                pdfUtilsServiceUrl, urlServiziRestSecurity, urlServizioRestCartografico,
                                                urlMailService, urlSIT, this._appConfigurationReader);

        }

        private string CaricaPdfutilsServiceUrl()
        {
            return this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_PDFUTILS, false);
        }



        private int GetValoreTokenTimeout()
        {
            var valore = this._cacheParametriSigeproSecurity.GetValoreCache(Constants.TOKEN_TIMEOUT, false);

            return String.IsNullOrEmpty(valore) ? 20 : Convert.ToInt32(valore);
        }




        #endregion
    }
}
