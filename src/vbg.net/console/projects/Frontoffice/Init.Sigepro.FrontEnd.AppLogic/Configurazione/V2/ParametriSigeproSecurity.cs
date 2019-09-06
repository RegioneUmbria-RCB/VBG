using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriSigeproSecurity : IParametriConfigurazione
	{
		public readonly string ServiceUrl;
		public readonly string Username;
		public readonly string Md5Password;

		public readonly string UrlPdfUtilsService;
		public readonly string UrlAreaRiservataService;
		public readonly string UrlIstanzeService;
		public readonly string UrlAlboPretorioService;
		public readonly string UrlCreazioneAnagrafeService;
		public readonly string UrlConversioneFileService;
		public readonly string UrlVerificaFirmaService;
		public readonly string UrlOggettiService;
		public readonly string UrlWebServiceMovimenti;
		public readonly string UrlWebServiceSit;
        public readonly string UrlWebServiceBookmarks;
		public readonly Dictionary<string,string> AltriParametri;
		public readonly int TokenTimeout;
		public readonly Uri UrlAutorizzazioniMercatiService;
		public readonly string UrlServizioTares;
		public readonly string UrlServizioConfigBari;
        public readonly string UrlWsModulisticaFrontoffice;
        public readonly string UrlGenerazioneQrCode;

		internal ParametriSigeproSecurity(/*string serviceUrl, string username, string md5password, */string urlAreaRiservata, string urlIstanze, string urlAlboPretorio,
										string urlCreazioneAnagrafe, string urlConversioneFiles, string urlVerificaFirma,
										string urlOggetti, int tokenTimeout, Dictionary<string, string> altriParametri,
										string urlWebServiceMovimenti, string urlWebServiceSit, string urlAutorizzazioniMercatiService,
                                        string urlServizioTares, string urlServizioConfigBari, string urlPdfUtilsService,
                                        string urlWebServiceBookmarks, string urlWsModulistica, string urlGenerazioneQrCode)
		{
			/*if (String.IsNullOrEmpty(serviceUrl))
				throw new ArgumentNullException("serviceUrl");

			if (String.IsNullOrEmpty(username))
				throw new ArgumentNullException("username");

			if (String.IsNullOrEmpty(md5password))
				throw new ArgumentNullException("md5password");*/

			if (String.IsNullOrEmpty(urlAreaRiservata))
				throw new ArgumentNullException("urlAreaRiservata");

			if (String.IsNullOrEmpty(urlIstanze))
				throw new ArgumentNullException("urlIstanze");

			if (String.IsNullOrEmpty(urlAlboPretorio))
				throw new ArgumentNullException("urlAlboPretorio");

			if (String.IsNullOrEmpty(urlCreazioneAnagrafe))
				throw new ArgumentNullException("urlCreazioneAnagrafe");

			if (String.IsNullOrEmpty(urlOggetti))
				throw new ArgumentNullException("urlOggetti");

			if (String.IsNullOrEmpty(urlWebServiceMovimenti))
				throw new ArgumentNullException("urlWebServiceMovimenti");

			if (String.IsNullOrEmpty(urlWebServiceSit))
				throw new ArgumentNullException("urlWebServiceSit");

			this.UrlAlboPretorioService			= urlAlboPretorio;
			this.UrlIstanzeService				= urlIstanze;
			this.UrlAreaRiservataService		= urlAreaRiservata;
			this.UrlCreazioneAnagrafeService	= urlCreazioneAnagrafe;
			this.UrlConversioneFileService		= urlConversioneFiles;
			this.UrlVerificaFirmaService		= urlVerificaFirma;
			this.UrlOggettiService				= urlOggetti;
			this.AltriParametri					= altriParametri;
			this.TokenTimeout					= tokenTimeout;
			this.UrlWebServiceMovimenti			= urlWebServiceMovimenti;
			this.UrlWebServiceSit				= urlWebServiceSit;
			this.UrlAutorizzazioniMercatiService = new Uri(urlAutorizzazioniMercatiService);
			this.UrlServizioTares				= urlServizioTares;
			this.UrlServizioConfigBari			= urlServizioConfigBari;
			this.UrlPdfUtilsService				= urlPdfUtilsService;
            this.UrlWebServiceBookmarks         = urlWebServiceBookmarks;
            this.UrlWsModulisticaFrontoffice    = urlWsModulistica;
            this.UrlGenerazioneQrCode = urlGenerazioneQrCode;
		}

	}
}
