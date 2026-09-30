using SIGePro.Manager.VerticalizzazioniBase;

namespace Init.SIGePro.Manager.Logic.GestioneConfigurazione.Verticalizzazioni
{
    public class VerticalizzazioneSitAttivo : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_ATTIVO";
            public const string Tiposit = "TIPOSIT";
            public const string UrlWsSit = "URL_WSSIT";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneSitAttivo()
        {

        }

        public VerticalizzazioneSitAttivo(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }


        /// <summary>
        /// E' il tipo sit attivato (ESC,CORE,NAUTILUS...) vedi l'enumerazione 
        /// </summary>
        public string Tiposit => this.GetString(Constants.Tiposit);

        /// <summary>
        /// URL completo del componente WS Sit per l'integrazione tra il backoffice e i sistemi SIT esposti, se presente andrà a sovrascrivere quello di default che punta al componente doNet. Es. http://<ip-servert>:<port>/webapp/services/name_services?wsdl (http://devel9:8080/wssit/services/sit?wsdl)
        /// </summary>
        public string UrlWssit => this.GetString(Constants.UrlWsSit);
    }
}
