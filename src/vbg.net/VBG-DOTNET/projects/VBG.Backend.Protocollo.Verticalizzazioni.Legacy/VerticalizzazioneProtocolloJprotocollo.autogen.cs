using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_JPROTOCOLLO il 26/08/2014 17.28.00
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// JPROTOCOLLO è il protocollo di NETSPRING presente, al momento, al Comune di Grosseto. Sviluppato nel 2011.
    /// </summary>
    public partial class VerticalizzazioneProtocolloJprotocollo : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_JPROTOCOLLO";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloJprotocollo()
        {

        }

        public VerticalizzazioneProtocolloJprotocollo(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Codice dell'AOO (Area Organizzativa omogenea) deve essere comunicata dall'amministratore del protocollo.
        /// </summary>
        public string Aoo
        {
            get { return this.GetString("AOO"); }
            set { this.SetString("AOO", value); }
        }

        /// <summary>
        /// E' il codice ente per il quale protocollare, deve essere fornito dall'amministratore del protocollo.
        /// </summary>
        public string Codiceente
        {
            get { return this.GetString("CODICEENTE"); }
            set { this.SetString("CODICEENTE", value); }
        }

        /// <summary>
        /// Se il parametro MITTENTE_LIBERO è valorizzato a 1 allora sarà utilizzato il valore presente in questo parametro come SOGGETTO MITTENTE del protocollo.
        /// </summary>
        public string Mittente
        {
            get { return this.GetString("MITTENTE"); }
            set { this.SetString("MITTENTE", value); }
        }

        /// <summary>
        /// Indica il codice del mittente interno con cui avviene la protocollazione. Deve essere fornito dall'amministratore del protocollo.
        /// </summary>
        public string Mittenteinterno
        {
            get { return this.GetString("MITTENTEINTERNO"); }
            set { this.SetString("MITTENTEINTERNO", value); }
        }

        /// <summary>
        /// Indica quale tipologia di mittente deve essere usata durante la protocollazione, se = 1 allora sarà utilizzato il valore presente dentro il parametro MITTENTE che quindi dovrà essere obbligatoriamente valorizzato, altrimenti se = 0 o non utilizzato saranno utilizzati i valori dei parametri CODICEENTE/AOO/UO
        /// </summary>
        public string MittenteLibero
        {
            get { return this.GetString("MITTENTE_LIBERO"); }
            set { this.SetString("MITTENTE_LIBERO", value); }
        }

        /// <summary>
        /// E' il codice del tramite con cui avviene la protocollazione, deve essere fornito dall'amministratore del protocollo.
        /// </summary>
        public string Tramitedefault
        {
            get { return this.GetString("TRAMITEDEFAULT"); }
            set { this.SetString("TRAMITEDEFAULT", value); }
        }

        /// <summary>
        /// Codice dell'UO (Unità Operativa) deve essere comunicata dall'amministratore del protocollo.
        /// </summary>
        public string Uo
        {
            get { return this.GetString("UO"); }
            set { this.SetString("UO", value); }
        }

        /// <summary>
        /// (Obbligatorio) E' l'URL per invocare il protocollo. Non devono essere specificati i metodi.
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// Username utilizzato per invocare le funzionalità di protocollazione di JProtocollo, deve essere fornito dall'admin del Protocollo JProtocollo.
        /// </summary>
        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        /// <summary>
        /// Se impostato a 1 consente di accedere alla funzionalità di invio pec del web service per un protocollo in partenza, altrimenti non sarà invocato tale metodo del web service di protocollazione.
        /// </summary>
        public string InviaPec
        {
            get { return this.GetString("INVIA_PEC"); }
            set { this.SetString("INVIA_PEC", value); }
        }
    }
}
