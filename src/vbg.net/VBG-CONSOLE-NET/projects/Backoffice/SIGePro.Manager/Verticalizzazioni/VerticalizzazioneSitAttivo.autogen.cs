
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_ATTIVO il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che c'è un SIT attivato (ESC,CORE,NAUTILUS...)
    /// </summary>
    public partial class VerticalizzazioneSitAttivo : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_ATTIVO";
            public const string Tiposit = "TIPOSIT";
            public const string UrlWsSit = "URL_WSSIT";
        }

        public VerticalizzazioneSitAttivo()
        {

        }

        public VerticalizzazioneSitAttivo(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }


        /// <summary>
        /// E' il tipo sit attivato (ESC,CORE,NAUTILUS...) vedi l'enumerazione 
        /// </summary>
        public string Tiposit => GetString(Constants.Tiposit);

        /// <summary>
        /// URL completo del componente WS Sit per l'integrazione tra il backoffice e i sistemi SIT esposti, se presente andrà a sovrascrivere quello di default che punta al componente doNet. Es. http://<ip-servert>:<port>/webapp/services/name_services?wsdl (http://devel9:8080/wssit/services/sit?wsdl)
        /// </summary>
        public string UrlWssit => GetString(Constants.UrlWsSit);
    }
}
