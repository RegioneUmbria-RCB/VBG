namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriServiziAreaRiservata : IParametriConfigurazione
    {
        public string UrlVisuraIstanzaConsole;
        public string UrlRicercaPraticaConsole;

        public ParametriServiziAreaRiservata(string urlVisuraIstanzaConsole, string urlRicercaPraticaConsole)
        {
            this.UrlVisuraIstanzaConsole = urlVisuraIstanzaConsole;
            this.UrlRicercaPraticaConsole = urlRicercaPraticaConsole;
        }
    }
}
