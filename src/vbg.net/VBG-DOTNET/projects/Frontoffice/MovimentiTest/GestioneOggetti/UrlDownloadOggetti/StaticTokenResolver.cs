using Init.Sigepro.FrontEnd.AppLogic.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneOggetti.UrlDownloadOggetti
{
    public class StaticTokenResolver : ITokenResolver
    {
        private readonly string _token;

        public StaticTokenResolver(string token = "token")
        {
            _token = token;
        }
        public string Token => _token;
    }
}
