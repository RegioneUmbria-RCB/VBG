using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{
    public interface IDownloadOggettiSecretKeyService
    {
        string Secret { get; }
    }
}
