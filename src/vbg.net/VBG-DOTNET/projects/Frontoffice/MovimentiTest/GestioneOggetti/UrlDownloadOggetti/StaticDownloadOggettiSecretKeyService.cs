using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneOggetti.UrlDownloadOggetti
{
    public class StaticDownloadOggettiSecretKeyService : IDownloadOggettiSecretKeyService
    {
        private readonly string _secret;

        public StaticDownloadOggettiSecretKeyService(string secret = "secret")
        {
            _secret = secret;
        }
        public string Secret => _secret;
    }
}
