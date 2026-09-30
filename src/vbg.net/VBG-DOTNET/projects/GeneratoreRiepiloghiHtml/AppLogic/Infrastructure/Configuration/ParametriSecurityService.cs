using VBG.SecurityLibrary.Services;

namespace GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration
{
    public class ParametriSecurityService
    {

        private static class Constants
        {
            public const string FileConverterUrl = "WSHOSTURL_FILECONVERTER";
            public const string BackendUrlVisura = "BACKEND_URL_VISURA";
            public const string WsHostUrlAspnetCore = "WSHOSTURL_ASPNET_CORE";
            public const string WsHostUrlAspnet = "WSHOSTURL_ASPNET";
            public const string WsHostUrlJava = "WSHOSTURL_JAVA";
        }

        private static class UrlConstants
        {
            public const string UrlVisura = "WebServices/WsAreaRiservata/WcfServices/Istanze/WsIstanzeService.svc";
            public const string UrlInterventi = "WebServices/WsAreaRiservata/WcfServices/Interventi/WsInterventi.svc";
            public const string UrlDatiDinamici = "WebServices/WsAreaRiservata/WcfServices/DatiDinamici/WsDatiDinamici.svc";
            public const string UrlOggetti = "services/oggetti?wsdl";
        }


        private readonly ISecurityService _securityService;

        public ParametriSecurityService(ISecurityService securityService)
        {
            this._securityService = securityService;
        }


        public ConfigurazioneEndpointUrl GetEndpointUrl()
        {
            return new ConfigurazioneEndpointUrl
            {
                FileConverterUrl = this.GetParameter(Constants.FileConverterUrl),
                UrlVisura = this.GetUrlServizioAspnet(UrlConstants.UrlVisura),
                UrlWsDatiDinamici = this.GetUrlServizioAspnet(UrlConstants.UrlDatiDinamici),
                UrlWsInterventi = this.GetUrlServizioAspnet(UrlConstants.UrlInterventi),
                OggettiServiceUrl = this.GetUrlServizioBackent(UrlConstants.UrlOggetti)
            };
        }

        private string GetParameterOrFallback(string parameterName, Func<string> fallbackCallback)
        {
            var value = this.GetParameter(parameterName);

            if (String.IsNullOrEmpty(value))
            {
                value = fallbackCallback();
            }

            return value;
        }

        private string GetUrlServizioBackent(string urlParziale)
        {
            var url = this.GetParameter(Constants.WsHostUrlJava);

            if (!url.EndsWith('/'))
            {
                url += "/";
            }

            return url + urlParziale;

        }

        private string GetUrlServizioAspnet(string urlParziale)
        {
            var url = this.GetParameterOrFallback(Constants.WsHostUrlAspnetCore, () => this.GetParameter(Constants.WsHostUrlAspnet));

            if (!url.EndsWith('/'))
            {
                url += "/";
            }

            return url + urlParziale;
        }

        private string GetParameter(string paramName)
        {
            return this._securityService.GetParameter(paramName) ?? "";
        }
    }
}
