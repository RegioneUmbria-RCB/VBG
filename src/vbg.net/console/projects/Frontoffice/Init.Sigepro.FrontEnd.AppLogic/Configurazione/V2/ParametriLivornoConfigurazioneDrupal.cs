using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriLivornoConfigurazioneDrupal : IParametriConfigurazione
    {
        public readonly string UrlWebServiceDatiDrupal;

        public ParametriLivornoConfigurazioneDrupal(string urlWebServiceDatiDrupal)
        {
            this.UrlWebServiceDatiDrupal = urlWebServiceDatiDrupal;
        }
    }
}
