

using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneTriesteAccessoAtti : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "TRIESTE_ACCESSO_ATTI";
            public const string UrlTrasferimentoControllo = "AR_URL_TRASFERIMENTO_CONTROLLO";
            public const string UrlWebService = "AR_URL_WEB_SERVICE";
        }


        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;
        public VerticalizzazioneTriesteAccessoAtti()
        {

        }

        public VerticalizzazioneTriesteAccessoAtti(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public string UrlTrasferimentoControllo => this.GetString(Constants.UrlTrasferimentoControllo);
        public string UrlWebService => this.GetString(Constants.UrlWebService);

    }
}
