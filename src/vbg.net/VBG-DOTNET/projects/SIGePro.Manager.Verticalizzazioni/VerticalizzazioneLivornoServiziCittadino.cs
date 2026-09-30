
using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneLivornoServiziCittadino : Verticalizzazione
    {
        private static class Constants
        {
            public static string UrlWsModulisticaDrupal = "URL_WS_MODULISTICA_DRUPAL";
        }

        private const string NOME_VERTICALIZZAZIONE = "LIVORNO_SERVIZI_CITTADINO";


        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneLivornoServiziCittadino()
        {

        }

        public VerticalizzazioneLivornoServiziCittadino(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string UrlWsModulisticaDrupal
        {
            get { return this.GetString(Constants.UrlWsModulisticaDrupal); }
            set { this.SetString(Constants.UrlWsModulisticaDrupal, value); }
        }
    }
}
