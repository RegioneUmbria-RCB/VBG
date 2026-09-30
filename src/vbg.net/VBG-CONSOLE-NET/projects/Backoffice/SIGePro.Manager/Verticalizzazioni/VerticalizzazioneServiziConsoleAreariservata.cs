using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    // SERVIZI_CONSOLE_AREARISERVATA
    //  'URL_RICERCA_PRATICA_CONSOLE'
    //  'URL_VISURA_ISTANZA_CONSOLE'
    public class VerticalizzazioneServiziConsoleAreariservata : Verticalizzazione
    {

        private static class Constants
        {
            public const string UrlRicercaPraticaConsole = "URL_RICERCA_PRATICA_CONSOLE";
            public const string UrlVisuraIstanzaConsole = "URL_VISURA_ISTANZA_CONSOLE";
        }

        private const string NOME_VERTICALIZZAZIONE = "SERVIZI_CONSOLE_AREARISERVATA";

        public VerticalizzazioneServiziConsoleAreariservata()
        {

        }

        public VerticalizzazioneServiziConsoleAreariservata(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string UrlVisuraIstanzaConsole => this.GetString(Constants.UrlVisuraIstanzaConsole);
        public string UrlRicercaPraticaConsole => this.GetString(Constants.UrlRicercaPraticaConsole);

    }
}
