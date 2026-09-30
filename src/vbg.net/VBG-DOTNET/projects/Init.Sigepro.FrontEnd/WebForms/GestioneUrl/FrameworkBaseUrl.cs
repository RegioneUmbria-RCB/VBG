using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.GestioneUrl
{
    public class FrameworkBaseUrl : IBaseUrl
    {

        private static class Constants
        {
            public const string ArCorePart = "/ar-core/";
            public const string ArFrameworkPart = "/areariservata";
        }

        private readonly IConfigurazione<ParametriAreaRiservataCore> _config;

        public string BaseUrlFramework => this.GetBaseUrlFramework();

        public string BaseUrlCore => this.GetBaseUrlCore();

        private string GetBaseUrlCore()
        {
            if (!String.IsNullOrEmpty(this._config.Parametri.BaseUrlCore))
            {
                return this._config.Parametri.BaseUrlCore;
            }

            var pathCompleto = "//" + HttpContext.Current.Request.ServerVariables["SERVER_NAME"];

            if (HttpContext.Current.Request.ServerVariables["SERVER_PORT"] != "80" && HttpContext.Current.Request.ServerVariables["SERVER_PORT"] != "443")
            {
                pathCompleto += ":" + HttpContext.Current.Request.ServerVariables["SERVER_PORT"];
            }
            pathCompleto += HttpContext.Current.Request.ApplicationPath;

            var idx = pathCompleto.IndexOf(Constants.ArFrameworkPart, StringComparison.OrdinalIgnoreCase);

            if (idx != -1)
            {
                return pathCompleto.Substring(0, idx) + Constants.ArCorePart;
            }

            return pathCompleto;
        }

        public FrameworkBaseUrl(IConfigurazione<ParametriAreaRiservataCore> config)
        {
            this._config = config;
        }

        private string GetBaseUrlFramework()
        {
            return "~";
        }

    }
}