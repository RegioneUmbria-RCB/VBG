using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriAidaSmart : IParametriConfigurazione
    {
        public readonly string CrossLoginUrl;
        public readonly string UrlNuovaDomanda;
        public readonly string UrlIstanzeInSospeso;


        public ParametriAidaSmart(string crossLoginUrl, string urlNuovaDomanda, string urlIstanzeInSospeso)
        {
            this.CrossLoginUrl = crossLoginUrl;
           

            this.UrlNuovaDomanda = urlNuovaDomanda;
            this.UrlIstanzeInSospeso = urlIstanzeInSospeso;
        }
    }
}
