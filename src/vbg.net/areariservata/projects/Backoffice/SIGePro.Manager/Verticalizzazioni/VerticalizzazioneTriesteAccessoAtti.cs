using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneTriesteAccessoAtti : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "TRIESTE_ACCESSO_ATTI";
            public const string UrlTrasferimentoControllo = "AR_URL_TRASFERIMENTO_CONTROLLO";
            public const string UrlWebService = "AR_URL_WEB_SERVICE";
        }



        public VerticalizzazioneTriesteAccessoAtti()
        {

        }

        public VerticalizzazioneTriesteAccessoAtti(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public string UrlTrasferimentoControllo => GetString(Constants.UrlTrasferimentoControllo);
        public string UrlWebService => GetString(Constants.UrlWebService);

    }
}
