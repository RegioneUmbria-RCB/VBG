using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneConsole.UrlAccesso
{
    public class UrlConsole
    {
        public string CrossLoginUrl { get; set; }
        public string UrlIstanzeInSospeso { get; set; }
        public string UrlNuovaDomanda { get; set; }
    }

    public class UrlConsolePerComune: UrlConsole
    {
        public string CodiceComune { get; set; }
    }

    public class ConfigurazioneUrlConsole
    {
        public bool UrlLocalizzatiAttivi { get; set; }

        public UrlConsole Default { get; set; }

        public UrlConsolePerComune[] UrlLocalizzati { get; set; } = new UrlConsolePerComune[0];
    }
}