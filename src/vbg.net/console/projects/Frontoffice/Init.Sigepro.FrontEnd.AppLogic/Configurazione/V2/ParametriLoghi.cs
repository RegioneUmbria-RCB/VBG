using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriLoghi : IParametriConfigurazione
    {
        public readonly int? CodiceOggettoLogoComune;
        public readonly int? CodiceOggettoLogoRegione;
        public readonly string UrlLogo;

        public ParametriLoghi(int? logoComune, int? logoRegione, string urlLogo)
        {
            this.CodiceOggettoLogoComune = logoComune;
            this.CodiceOggettoLogoRegione = logoRegione;
            this.UrlLogo = urlLogo;
        }
    }
}
