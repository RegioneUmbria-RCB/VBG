using SIGePro.Manager.VerticalizzazioniBase;

namespace Init.SIGePro.Manager.Authentication
{
    public class VerticalizzazioneUrlServiziConsole : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVertivcalizzazione = "AR_URL_SERVIZI_CONSOLE";
            public const string CrossLoginUrl = "CROSS_LOGIN_URL";
            public const string UrlIstanzeInSospeso = "URL_ISTANZE_IN_SOSPESO";
            public const string UrlNuovaDomanda = "URL_NUOVA_DOMANDA";
        }

        public override string NomeVerticalizzazione => Constants.NomeVertivcalizzazione;


        public VerticalizzazioneUrlServiziConsole()
        {

        }
        public VerticalizzazioneUrlServiziConsole(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, Constants.NomeVertivcalizzazione, software, codiceComune)
        {
        }

        public string CrossLoginUrl => this.GetString(Constants.CrossLoginUrl);

        public string UrlIstanzeInSospeso => this.GetString(Constants.UrlIstanzeInSospeso);

        public string UrlNuovaDomanda => this.GetString(Constants.UrlNuovaDomanda);
    }
}
