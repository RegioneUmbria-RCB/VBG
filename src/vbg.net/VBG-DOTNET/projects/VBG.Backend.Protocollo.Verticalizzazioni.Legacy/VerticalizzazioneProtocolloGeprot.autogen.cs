using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_GEPROT il 26/08/2014 17.28.01
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// GEPROT è il protocollo di Sistematica utilizzato nei Comuni Umbri
    /// </summary>
    public partial class VerticalizzazioneProtocolloGeprot : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_GEPROT";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloGeprot()
        {

        }

        public VerticalizzazioneProtocolloGeprot(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Codice dell'amministrazione
        /// </summary>
        public string Codiceamministrazione
        {
            get { return this.GetString("CODICEAMMINISTRAZIONE"); }
            set { this.SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Codice dell'AOO
        /// </summary>
        public string Codiceaoo
        {
            get { return this.GetString("CODICEAOO"); }
            set { this.SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// Denominazione dell'amministrazione
        /// </summary>
        public string Denominazioneamministrazione
        {
            get { return this.GetString("DENOMINAZIONEAMMINISTRAZIONE"); }
            set { this.SetString("DENOMINAZIONEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Denominazione dell'AOO
        /// </summary>
        public string Denominazioneaoo
        {
            get { return this.GetString("DENOMINAZIONEAOO"); }
            set { this.SetString("DENOMINAZIONEAOO", value); }
        }

        /// <summary>
        /// Indirizzo telematico dell'amministrazione
        /// </summary>
        public string Indirizzotelematico
        {
            get { return this.GetString("INDIRIZZOTELEMATICO"); }
            set { this.SetString("INDIRIZZOTELEMATICO", value); }
        }

        /// <summary>
        /// E' l'operatore da utilizzare per protocollare con GEPROT, deve essere fornito dall'amministratore del protocollo GEPROT.
        /// </summary>
        public string Operatore
        {
            get { return this.GetString("OPERATORE"); }
            set { this.SetString("OPERATORE", value); }
        }

        /// <summary>
        /// E' la password per protocollare in GEPROT, deve essere fornita dall'amministratore del protocollo GEPROT.
        /// </summary>
        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Doctype di validazione della segnatura inviata per la protocollazione
        /// </summary>
        public string Protdoctype
        {
            get { return this.GetString("PROTDOCTYPE"); }
            set { this.SetString("PROTDOCTYPE", value); }
        }

        /// <summary>
        /// E' l'URL per invocare il protocollo. Non devono essere specificati i metodi.
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// Parametro che indica se deve essere attivata la funzionalità di invio pec per le protocollazioni in partenza, valorizzare a 1 se si desidera fare in modo che il sistema invii una pec per conto del sistema di protocollo, valorizzare con qualsiasi altro valore (meglio se 0 o non valorizzato) se si desidera che il sistema non invii una pec. Nel caso in cui sia attivo questo parametro (valore 1) il componente andrà ad invocare il metodo inviaEMail del web service di protocollo messo a disposizione da Sistematica.
        /// </summary>
        public string InvioPec
        {
            get { return this.GetString("INVIO_PEC"); }
            set { this.SetString("INVIO_PEC", value); }
        }

        /// <summary>
        /// Parametro che serve per mantenere la compatibilità con vecchie versioni, serve per indicare se il componente deve associare l''indirizzo e-mail dell''anagrafica sull''indirizzo telematico (in questo caso lasciare vuoto), oppure lasciare la gestione al parametro GESTIONE_PEC della regola PROTOCOLLO_ATTIVO (in questo caso valorizzare a 1.
        /// </summary>
        public string GestioneIndirizzoTelemAnag
        {
            get { return this.GetString("GESTIONE_INDIRIZZO_TELEM_ANAG"); }
            set { this.SetString("GESTIONE_INDIRIZZO_TELEM_ANAG", value); }
        }

    }
}
