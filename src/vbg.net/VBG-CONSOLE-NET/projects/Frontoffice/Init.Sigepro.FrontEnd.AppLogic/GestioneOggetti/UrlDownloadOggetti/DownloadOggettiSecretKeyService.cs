using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{
    public class DownloadOggettiSecretKeyService : IDownloadOggettiSecretKeyService
    {
        private static class Constants
        {
            public const string ConfigurationKey = "DownloadOggetti.Secret";
            public const string DefaultSecret = "5tr1ng453gr371551m4";
        }

        public string Secret
        {
            get
            {
                var str = ConfigurationManager.AppSettings[Constants.ConfigurationKey];
                return string.IsNullOrEmpty(str) ? Constants.DefaultSecret : str;
            }
        }
    }
}
