using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.WsSigeproSecurity;
using System.Configuration;

namespace Init.SIGePro.Manager.Configuration
{
	public class ConfigurazioneGenerale : HandlerConfigurazione
	{
		private static class Constants
		{
			public const string AnagrafeServiceUrlPart = "services/anagrafe";
			public const string OggettiServiceUrlPart  = "services/oggetti";
			public const string RateizzazioneServiceUrlPart  = "services/rateizzazioni";
			public const string MercatiServiceUrlPart  = "services/mercati";
			public const string EventiAttivitaServiceUrlPart  = "services/attivita";
			public const string IstanzeOneriServiceUrlPart = "services/istanzeoneri";
		}

		public string WsHostUrlJava { get; protected set; }
		public string WsHostUrlAspNet { get; protected set; }
		public string WsHostUrlAspExport { get; protected set; }
		public string WsHostUrlFirmaDigitale { get; protected set; }
		public string WsHostUrlFileConverter { get; protected set; }
		public string WsHostUrlRender { get; protected set; }
        public string WsHostUrlNlaPec { get; protected set; }

		public string WsAnagrafeServiceUrl { get; protected set; }
		public string WsOggettiServiceUrl { get; protected set; }
		public string WsRateizzazioniServiceUrl { get; protected set; }
		public string WsMercatiServiceUrl { get; protected set; }
		public string WsEventiAttivitaServiceUrl { get; protected set; }
		public string WsIstanzeOneriServiceUrl { get; protected set; }

		public string BaseUrl { get; protected set; }

		public string AppJava { get; protected set; }
		public string AppAsp { get; protected set; }
		public string AppAspNet { get; protected set; }

		public string AuthenticationGatewayUrl { get; protected set; }
		public string AuditServiceUrl { get; protected set; }

		public int TokenTimeout { get; protected set; }

		//public string JavaServiceUrl { get; protected set; }

		//public ConfigurazioneMail Mail { get; protected set; }

		internal ConfigurazioneGenerale ( ApplicationInfoType[] parametri ): base( parametri )
		{
			WsHostUrlAspExport		= GetParam("WSHOSTURL_EXPORT");
			WsHostUrlAspNet			= GetParam("WSHOSTURL_ASPNET");
			WsHostUrlFileConverter	= GetParam("WSHOSTURL_FILECONVERTER");
			WsHostUrlFirmaDigitale	= GetParam("WSHOSTURL_FIRMADIGITALE");
			WsHostUrlJava			= GetParam("WSHOSTURL_JAVA");
			WsHostUrlRender			= GetParam("WSHOSTURL_RENDER");
            WsHostUrlNlaPec         = GetParam("WSHOSTURL_NLAPEC");

			BaseUrl	= GetParam("BASE_URL");

			AppAsp		= GetParam("APP_ASP");
			AppAspNet	= GetParam("APP_ASPNET");
			AppJava		= GetParam("APP_JAVA");

			AuthenticationGatewayUrl = GetParam("AUTHENTICATION_GATEWAY_URL");
			AuditServiceUrl			 = GetParam("AUDIT_SERVICE_URL");

			if (!WsHostUrlJava.EndsWith("/"))
				WsHostUrlJava += "/";

			//JavaServiceUrl = WsHostUrlJava + "services/";

			var overrideJavaBaseUrl = ConfigurationManager.AppSettings["override-java-base-url"];

			if (!String.IsNullOrEmpty(overrideJavaBaseUrl))
				this.WsHostUrlJava = overrideJavaBaseUrl;

			this.WsAnagrafeServiceUrl		= this.WsHostUrlJava + Constants.AnagrafeServiceUrlPart;
			this.WsOggettiServiceUrl		= this.WsHostUrlJava + Constants.OggettiServiceUrlPart;
			this.WsRateizzazioniServiceUrl	= this.WsHostUrlJava + Constants.RateizzazioneServiceUrlPart;
			this.WsMercatiServiceUrl		= this.WsHostUrlJava + Constants.MercatiServiceUrlPart;
			this.WsEventiAttivitaServiceUrl = this.WsHostUrlJava + Constants.EventiAttivitaServiceUrlPart;
			this.WsIstanzeOneriServiceUrl   = this.WsHostUrlJava + Constants.IstanzeOneriServiceUrlPart;

			var tokenTimeout = GetParam("CHECK_TOKEN_TIMEOUT");

			this.TokenTimeout = String.IsNullOrEmpty(tokenTimeout) ? 0 : Convert.ToInt32(tokenTimeout);
		}




	}
}
