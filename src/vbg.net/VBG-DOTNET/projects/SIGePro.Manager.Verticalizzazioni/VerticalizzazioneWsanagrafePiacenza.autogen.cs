using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_PIACENZA il 26/08/2014 17.23.44
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Parametri di configurazione dell'oggetto responsabile per le ricerche anagrafiche del comune di PIACENZA
    /// </summary>
    public partial class VerticalizzazioneWsanagrafePiacenza : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_PIACENZA";
        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneWsanagrafePiacenza()
        {

        }

        public VerticalizzazioneWsanagrafePiacenza(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Stringa di connessione al database
        /// </summary>
        public string Connectionstring
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// Owner della vista da interrogare
        /// </summary>
        public string Owner
        {
            get { return this.GetString("OWNER"); }
            set { this.SetString("OWNER", value); }
        }

        /// <summary>
        /// Nome del provider da utilizzare nella personalLib per creare l'oggetto database
        /// </summary>
        public string Provider
        {
            get { return this.GetString("PROVIDER"); }
            set { this.SetString("PROVIDER", value); }
        }

        /// <summary>
        /// Vista da interrogare
        /// </summary>
        public string View
        {
            get { return this.GetString("VIEW"); }
            set { this.SetString("VIEW", value); }
        }


    }
}
