using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{
    public class DownloadOggettiSecretKeyService : IDownloadOggettiSecretKeyService
    {
        private readonly IAppConfigurationReader _appConfigurationReader;

        private static class Constants
        {
            public const string ConfigurationKey = "DownloadOggetti.Secret";
            public const string DefaultSecret = "5tr1ng453gr371551m4";
        }

        public DownloadOggettiSecretKeyService(IAppConfigurationReader appConfigurationReader)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public string Secret
        {
            get
            {
                var str = this._appConfigurationReader.GetSetting(Constants.ConfigurationKey);
                return string.IsNullOrEmpty(str) ? Constants.DefaultSecret : str;
            }
        }
    }
}
