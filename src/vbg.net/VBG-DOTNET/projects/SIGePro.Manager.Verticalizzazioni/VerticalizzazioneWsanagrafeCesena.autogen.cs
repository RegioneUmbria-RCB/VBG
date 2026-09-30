using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_CESENA il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Parametri di configurazione dell'oggetto responsabile per le ricerche anagrafiche del comune di CESENA
    /// </summary>
    public partial class VerticalizzazioneWsanagrafeCesena : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_CESENA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneWsanagrafeCesena()
        {

        }

        public VerticalizzazioneWsanagrafeCesena(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


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
