namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriTriesteAccessoAtti : IParametriConfigurazione
    {
        public readonly string? UrlTraferimentoControllo;
        public readonly string? UrlWebService;

        public ParametriTriesteAccessoAtti(string? urlTraferimentoControllo, string? urlWebService)
        {
            this.UrlTraferimentoControllo = urlTraferimentoControllo;
            this.UrlWebService = urlWebService;
        }
    }
}
