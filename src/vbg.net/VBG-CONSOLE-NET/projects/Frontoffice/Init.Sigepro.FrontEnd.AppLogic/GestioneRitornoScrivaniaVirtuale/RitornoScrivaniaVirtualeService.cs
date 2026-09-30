using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRitornoScrivaniaVirtuale
{
    public class RitornoScrivaniaVirtualeService
    {
        private static class Constants
        {
            public const string SessionKey = "RitornoScrivaniaVirtualeService.SessionKey";
        }


        public void SalvaUrlRitorno(string url)
        {
            SessionHelper.AddEntry(Constants.SessionKey, url);
        }

        public bool UrlRitornoDefinito => GetUrlRitorno(false) != String.Empty;

        public string GetUrlRitorno(bool cancellaUrlSalvato)
        {
            var urlRitorno = SessionHelper.GetEntry(Constants.SessionKey, () => string.Empty);

            if (cancellaUrlSalvato)
            {
                SessionHelper.RemoveEntry(Constants.SessionKey);
            }

            return urlRitorno;
        }
    }
}
