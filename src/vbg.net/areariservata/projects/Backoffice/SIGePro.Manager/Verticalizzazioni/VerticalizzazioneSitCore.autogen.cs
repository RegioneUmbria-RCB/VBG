
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SIT_CORE il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se 1 indica che il sit CORE è attivo.
    /// </summary>
    public partial class VerticalizzazioneSitCore : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SIT_CORE";

        public VerticalizzazioneSitCore()
        {

        }

        public VerticalizzazioneSitCore(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione del db le cui viste vengono interrogate o del db collegato tramite dblink alla base dati che ha le viste
        /// </summary>
        public string Connectionstring
        {
            get { return GetString("CONNECTIONSTRING"); }
            set { SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Nome del dblink 
        /// </summary>
        public string Dblink
        {
            get { return GetString("DBLINK"); }
            set { SetString("DBLINK", value); }
        }

        /// <summary>
        /// Nome del proprietario delle viste/tabelle del db a cui punta il dblink
        /// </summary>
        public string Owner
        {
            get { return GetString("OWNER"); }
            set { SetString("OWNER", value); }
        }

        /// <summary>
        /// Provider  del db le cui viste vengono interrogate  o del db collegato tramite dblink alla base dati che ha le viste
        /// </summary>
        public string Provider
        {
            get { return GetString("PROVIDER"); }
            set { SetString("PROVIDER", value); }
        }

        /// <summary>
        /// Url del web service rest che restituisce i dati relativi al catasto.
        /// </summary>
        public string UrlWsCatasto
        {
            get { return GetString("URL_WS_CATASTO"); }
            set { SetString("URL_WS_CATASTO", value); }
        }

        /// <summary>
        /// Codice Catastale del comune che effettua la chiamata al servizio SIT.
        /// </summary>
        public string CodiceEnte
        {
            get { return GetString("CODICEENTE"); }
            set { SetString("CODICEENTE", value); }
        }

    }
}
