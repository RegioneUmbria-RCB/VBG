using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Microsoft.AspNetCore.Components;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneUrl
{
    public class StandardBaseUrl : IBaseUrl
    {
        private static class Constants
        {
            public const string ArCorePart = "/ar-core/";
            public const string ArFrameworkPart = "/areariservata";
        }

        private readonly IConfigurazione<ParametriAreaRiservataCore> _config;
        private readonly NavigationManager _navigationManager;

        public string BaseUrlFramework => this.GetBaseUrlFramework();

        public string BaseUrlCore => this.GetBaseUrlCore();

        private string GetBaseUrlCore()
        {
            return String.Empty;
        }

        public StandardBaseUrl(IConfigurazione<ParametriAreaRiservataCore> config, NavigationManager navigationManager)
        {
            this._config = config;
            this._navigationManager = navigationManager;
        }

        private string GetBaseUrlFramework()
        {
            if (!String.IsNullOrEmpty(this._config.Parametri.BaseUrlFramework))
            {
                return this._config.Parametri.BaseUrlFramework;
            }

            return this._navigationManager.BaseUri.Replace(Constants.ArCorePart, Constants.ArFrameworkPart);
        }
    }
}
