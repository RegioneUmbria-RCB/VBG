using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class ParametriSigeproSecurityBuilder : AreaRiservataWebConfigBuilder,IConfigurazioneBuilder<ParametriSigeproSecurity>
	{
		private static class Constants
		{
			public const string WS_AREA_RISERVATA		= "/WebServices/WsAreaRiservata/AreaRiservataService.asmx";
			public const string WS_ISTANZE				= "/WebServices/WsSIGePro/Istanze.asmx";
			public const string WS_SIT					= "/WebServices/WsSIGePro/wssit.asmx";
            public const string WS_MODULISTICA          = "/WebServices/WsSIGePro/WsModulistica.asmx";
			public const string WS_AUTORIZZAZIONI		= "/WebServices/wsautorizzazioni/autorizzazioniwebservice.asmx";
			public const string WS_LETTURA_MOVIMENTI	= "/WebServices/WsAreaRiservata/MovimentiFrontofficeService.asmx";
            public const string WS_BOOKMARKS            = "/WebServices/WsAreaRiservata/BookmarksService.asmx";
			public const string WS_ALBO_PRETORIO		= "/services/alboPretorio";
			public const string WS_OGGETTI_SERVICE		= "/services/oggetti";
			public const string WS_CREAZIONE_ANAGRAFE	= "/services/anagrafe";
            public const string WS_CREAZIONE_QRCODE     = "/services/qrcode";


            public const string APP_ASP                         = "APP_ASP";
			public const string APP_ASPNET                      = "APP_ASPNET";
			public const string APP_JAVA                        = "APP_JAVA";
			public const string AUDIT_SERVICE_URL               = "AUDIT_SERVICE_URL";
			public const string AUTHENTICATION_GATEWAY_FO_URL   = "AUTHENTICATION_GATEWAY_FO_URL";
			public const string AUTHENTICATION_GATEWAY_FO_URLCIE= "AUTHENTICATION_GATEWAY_FO_URLCIE";
			public const string AUTHENTICATION_GATEWAY_URL      = "AUTHENTICATION_GATEWAY_URL";
			public const string BASE_URL                        = "BASE_URL";
			public const string MAIL_ACCEPT_INVALID_CERTIFICATES= "ACCEPT_INVALID_CERTIFICATES";
			public const string MAIL_LOGINNAME					= "LOGINNAME";
			public const string MAIL_MAILSERVER                 = "MAILSERVER";
			public const string MAIL_PASSWORD                   = "PASSWORD";
			public const string MAIL_SENDER                     = "SENDER";
			public const string MAIL_SMTP_PORT                  = "SMTP_PORT";
			public const string MAIL_USE_AUTHENTICATION         = "USE_AUTHENTICATION";
			public const string MAIL_USE_SSL                    = "USE_SSL";
			public const string TOKEN_TIMEOUT                   = "TOKEN_TIMEOUT";
			public const string WSHOSTURL_ASPNET                = "WSHOSTURL_ASPNET";
			public const string WSHOSTURL_EXPORT                = "WSHOSTURL_EXPORT";
			public const string WSHOSTURL_FILECONVERTER         = "WSHOSTURL_FILECONVERTER";
			public const string WSHOSTURL_FIRMADIGITALE         = "WSHOSTURL_FIRMADIGITALE";
			public const string WSHOSTURL_JAVA                  = "WSHOSTURL_JAVA";
			public const string WSHOSTURL_RENDER                = "WSHOSTURL_RENDER";
			public const string WSHOSTURL_PDFUTILS				= "WSHOSTURL_PDFUTILS";

			public const string WS_TARES_BARI = "/WebServices/WsTaresBari/TaresService.asmx";
			public const string WS_CONFIG_BARI = "/WebServices/WsBari/BariConfigService.asmx";
		}

		ILog _log = LogManager.GetLogger(typeof(ParametriSigeproSecurityBuilder));
		IAliasResolver _aliasResolver;

		Dictionary<string,string> _cacheParametri = new Dictionary<string, string>();

		public ParametriSigeproSecurityBuilder(IAliasResolver aliasSoftwareResolver)
		{
			if (aliasSoftwareResolver == null)
				throw new ArgumentNullException("aliasSoftwareResolver");

			this._aliasResolver = aliasSoftwareResolver;
		}

		#region IBuilder<ParametriSigeproSecurity> Members

		public ParametriSigeproSecurity Build()
		{
			BuildCacheParametri();

			var sigeproAspNetBaseUrl	= GetValoreCache(Constants.WSHOSTURL_ASPNET, false);
			var sigeproJavaBaseUrl		= GetValoreCache(Constants.WSHOSTURL_JAVA);
			var pdfUtilsServiceUrl		= CaricaPdfutilsServiceUrl();

			var aspNetBaseUrlOverride	= ConfigurationManager.AppSettings["overrideAspNetBaseUrl"];
			var javaBaseUrlOverride		= ConfigurationManager.AppSettings["overrideJavaBaseUrl"];

			if (!string.IsNullOrEmpty(aspNetBaseUrlOverride))
				sigeproAspNetBaseUrl = aspNetBaseUrlOverride;

			if (!string.IsNullOrEmpty(javaBaseUrlOverride))
				sigeproJavaBaseUrl = javaBaseUrlOverride;

			var urlAreaRiservataService = sigeproAspNetBaseUrl + Constants.WS_AREA_RISERVATA;
			var urlIstanzeService		= sigeproAspNetBaseUrl + Constants.WS_ISTANZE;
			var urlSitService			= sigeproAspNetBaseUrl + Constants.WS_SIT;
            var urlWsModulistica        = sigeproAspNetBaseUrl + Constants.WS_MODULISTICA;
			var urlAutorizzazioniService = sigeproAspNetBaseUrl + Constants.WS_AUTORIZZAZIONI;
			var urlLetturaMovimenti		= sigeproAspNetBaseUrl + Constants.WS_LETTURA_MOVIMENTI;
			var urlServizioTaresBari	= sigeproAspNetBaseUrl + Constants.WS_TARES_BARI;
			var urlServizioConfigBari		= sigeproAspNetBaseUrl + Constants.WS_CONFIG_BARI;
            var urlBookmarks            = sigeproAspNetBaseUrl + Constants.WS_BOOKMARKS;
            

			var urlConversioneFileService	= GetValoreCache(Constants.WSHOSTURL_FILECONVERTER, false);
			var urlVerificaFirmaService		= GetValoreCache(Constants.WSHOSTURL_FIRMADIGITALE, false);

			var urlAlboPretorioService	= sigeproJavaBaseUrl + Constants.WS_ALBO_PRETORIO;
			var urlOggettiService		= sigeproJavaBaseUrl + Constants.WS_OGGETTI_SERVICE;
            var urlGenerazioneQrCode = sigeproJavaBaseUrl + Constants.WS_CREAZIONE_QRCODE;

            var overrideAnagrafeService = ConfigurationManager.AppSettings["overrideAnagrafeServiceUrl"];

			var urlCreazioneAnagrafeService = sigeproJavaBaseUrl + Constants.WS_CREAZIONE_ANAGRAFE;

			if (!String.IsNullOrEmpty(overrideAnagrafeService))
				urlCreazioneAnagrafeService = overrideAnagrafeService;

			var tokenTimeout = GetValoreTokenTimeout();

			return new ParametriSigeproSecurity(/*url, username, password,*/
												urlAreaRiservataService, urlIstanzeService, urlAlboPretorioService,
												urlCreazioneAnagrafeService, urlConversioneFileService, urlVerificaFirmaService,
												urlOggettiService, tokenTimeout, _cacheParametri, urlLetturaMovimenti, urlSitService,
												urlAutorizzazioniService, urlServizioTaresBari, urlServizioConfigBari, pdfUtilsServiceUrl,
                                                urlBookmarks, urlWsModulistica, urlGenerazioneQrCode);
                                                
		}

		private string CaricaPdfutilsServiceUrl()
		{
			return GetValoreCache(Constants.WSHOSTURL_PDFUTILS, false);
		}

		private string GetValoreCache(string nomeParametro, bool throwIfNowFound = true)
		{
			var valore = ConfigurationManager.AppSettings[nomeParametro];

			if (!String.IsNullOrEmpty(valore))
			{
				_log.InfoFormat("il parametro {0} è stato letto dal web.config, valore={1}", nomeParametro, valore);
				return valore;
			}




			if (_cacheParametri.ContainsKey(nomeParametro))
			{
				valore = _cacheParametri[nomeParametro];

				if(throwIfNowFound && String.IsNullOrEmpty(valore))
					throw new ConfigurationErrorsException("IBCSECURITY non ha un valore per il parametro " + nomeParametro);

				_log.InfoFormat("il parametro {0} è stato letto dal servizio sigeprosecurity, valore={1}", nomeParametro, valore);

				return valore;
			}

			if(throwIfNowFound)
				throw new ConfigurationErrorsException("IBCSECURITY non contiene il parametro " + nomeParametro);

			return valore;
		}

		private int GetValoreTokenTimeout()
		{
			var valore = GetValoreCache(Constants.TOKEN_TIMEOUT);

			return String.IsNullOrEmpty(valore) ? 0 : Convert.ToInt32(valore);
		}


		private void BuildCacheParametri()
		{
			var appInfo = new SigeproSecurityProxy().GetApplicationInfo();

			for (int i = 0; i < appInfo.Length; i++)
			{
				_cacheParametri.Add(appInfo[i].param, appInfo[i].value);
			}
		}

		#endregion
	}
}
