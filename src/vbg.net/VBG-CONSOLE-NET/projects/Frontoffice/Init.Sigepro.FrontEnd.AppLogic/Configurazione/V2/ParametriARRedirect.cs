using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriARRedirect : IParametriConfigurazione
    {
        public readonly bool VerticalizzazioneAttiva;
        public readonly string NomeFile;
        public readonly string UrlRedirect;
        public readonly string UrlPaginaVisuraIstanza;

        public ParametriARRedirect(bool verticalizzazioneAttiva, string nomeFile, string urlRedirect, string urlPaginaVisuraIstanza)
        {
            this.VerticalizzazioneAttiva = verticalizzazioneAttiva;
            this.NomeFile = nomeFile;
            this.UrlRedirect = urlRedirect;
            this.UrlPaginaVisuraIstanza = urlPaginaVisuraIstanza;
        }
    }
}
