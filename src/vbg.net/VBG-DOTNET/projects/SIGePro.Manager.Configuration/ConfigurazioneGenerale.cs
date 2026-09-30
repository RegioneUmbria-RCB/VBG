using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Const;
using Init.SIGePro.Manager.Configuration.Const;
using System;

namespace Init.SIGePro.Manager.Configuration
{
    public class ConfigurazioneGenerale : IConfigurazioneGenerale
    {
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;

        private static class Constants
        {
            public const string AnagrafeServiceUrlPart = "services/anagrafe";
            public const string OggettiServiceUrlPart = "services/oggetti";
            public const string RateizzazioneServiceUrlPart = "services/rateizzazioni";
            public const string MercatiServiceUrlPart = "services/mercati";
            public const string EventiAttivitaServiceUrlPart = "services/attivita";
            public const string IstanzeOneriServiceUrlPart = "services/istanzeoneri";
            public const string IstanzeServiceUrlPart = "services/istanze";
            public const string IstanzeStradarioJsonUrlPart = "/stradario";
            public const string IstanzeJsonUrlPart = "services/rest-private/istanze";
            public const string PosizioniDebitorieServiceUrlPart = "services/posizionidebitorie";
            // public const string ContiServiceUrlPart = "";
        }

        public string WsHostUrlJava { get; protected set; }
        public string WsHostUrlAspNet { get; protected set; }
        public string WsHostUrlAspExport { get; protected set; }
        public string WsHostUrlFirmaDigitale { get; protected set; }
        public string WsHostUrlFileConverter { get; protected set; }
        public string WsHostUrlRender { get; protected set; }
        public string WsHostUrlNlaPec { get; protected set; }
        public string WsHostUrlApiBackend { get; protected set; }
        public string WsHostUrlSIT { get; protected set; }
        public string WsAnagrafeServiceUrl => this.WsHostUrlJava + Constants.AnagrafeServiceUrlPart;
        public string WsOggettiServiceUrl => this.WsHostUrlJava + Constants.OggettiServiceUrlPart;
        public string WsRateizzazioniServiceUrl => this.WsHostUrlJava + Constants.RateizzazioneServiceUrlPart;
        public string WsMercatiServiceUrl => this.WsHostUrlJava + Constants.MercatiServiceUrlPart;
        public string WsEventiAttivitaServiceUrl => this.WsHostUrlJava + Constants.EventiAttivitaServiceUrlPart;
        public string WsIstanzeOneriServiceUrl => this.WsHostUrlJava + Constants.IstanzeOneriServiceUrlPart;
        public string WsIstanzeServiceUrl => this.WsHostUrlJava + Constants.IstanzeServiceUrlPart;
        public string RestIstanzeJsonUrl => this.WsHostUrlJava + Constants.IstanzeJsonUrlPart;
        public string RestIstanzeStradarioJsonUrl => this.RestIstanzeJsonUrl + Constants.IstanzeStradarioJsonUrlPart;

        public string WsPosizioniDebitorieServiceUrl => this.WsHostUrlJava + Constants.PosizioniDebitorieServiceUrlPart;
        //public string WsContiServiceUrl => this.WsHostUrlJava + Constants.ContiServiceUrlPart;


        public string BaseUrl { get; protected set; }
        public string AppJava { get; protected set; }
        public string AppAsp { get; protected set; }
        public string AppAspNet { get; protected set; }
        public string AuthenticationGatewayUrl { get; protected set; }
        public string AuditServiceUrl { get; protected set; }
        public int TokenTimeout { get; protected set; }
        public string WsUrlProtocollo { get; protected set; }

        public ConfigurazioneGenerale(SigeproSecurityProxy sigeproSecurityProxy)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;

            this.WsHostUrlAspExport = this.GetParam(ConfParamNames.WSHOSTURL_EXPORT);
            this.WsHostUrlAspNet = this.GetParam(ConfParamNames.WSHOSTURL_ASPNET);
            this.WsHostUrlFileConverter = this.GetParam(ConfParamNames.WSHOSTURL_FILECONVERTER);
            this.WsHostUrlFirmaDigitale = this.GetParam(ConfParamNames.WSHOSTURL_FIRMADIGITALE);
            this.WsHostUrlJava = this.GetParam(ConfParamNames.WSHOSTURL_JAVA);
            this.WsHostUrlRender = this.GetParam(ConfParamNames.WSHOSTURL_RENDER);
            this.WsHostUrlNlaPec = this.GetParam(ConfParamNames.WSHOSTURL_NLAPEC);
            this.WsHostUrlApiBackend = this.GetParam(ConfParamNames.WSHOSTURL_APIBACKEND);
            this.WsHostUrlSIT = this.GetParam(ConfParamNames.WSHOSTURL_SIT);

            this.BaseUrl = this.GetParam(ConfParamNames.BASE_URL);

            this.AppAsp = this.GetParam(ConfParamNames.APP_ASP);
            this.AppAspNet = this.GetParam(ConfParamNames.APP_ASPNET);
            this.AppJava = this.GetParam(ConfParamNames.APP_JAVA);

            if (!String.IsNullOrEmpty(this.AppJava) && !this.AppJava.StartsWith("/"))
            {
                this.AppJava = "/" + this.AppJava;
            }

            this.AuthenticationGatewayUrl = this.GetParam(AuthParamNames.AUTHENTICATION_GATEWAY_URL);
            this.AuditServiceUrl = this.GetParam(AuthParamNames.AUDIT_SERVICE_URL);

            if (!this.WsHostUrlJava.EndsWith("/"))
                this.WsHostUrlJava += "/";

            var overrideJavaBaseUrl = this.GetParam(AuthParamNames.OVERRIDE_JAVA_BASE_URL);

            if (!String.IsNullOrEmpty(overrideJavaBaseUrl))
                this.WsHostUrlJava = overrideJavaBaseUrl;

            var tokenTimeout = this.GetParam(AuthParamNames.CHECK_TOKEN_TIMEOUT);

            this.TokenTimeout = String.IsNullOrEmpty(tokenTimeout) ? 0 : Convert.ToInt32(tokenTimeout);

            this.WsUrlProtocollo = this.GetParam(ConfParamNames.WS_URL_PROTOCOLLO);
        }

        private string GetParam(string nomeParametro)
        {
            return this.GetParam(nomeParametro, String.Empty);
        }
        private string GetParam(string nomeParametro, string valoreDefault)
        {
            var cfgVal = FileBasedConfiguration.GetSetting(nomeParametro);

            if (!String.IsNullOrEmpty(cfgVal))
            {
                return cfgVal;
            }

            var val = this._sigeproSecurityProxy.GetValoreParametro(nomeParametro);

            return val ?? valoreDefault;
        }

        public string GetApplicationInfoValue(string param)
        {
            return this.GetParam(param, String.Empty);
        }
    }
}
