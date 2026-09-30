using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione STC il 26/08/2014 17.23.46
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se attivo (1) indica che è attivata la comunicazione con il Sistema Territoriale di Comunicazione (STC) ed il back-office diventa un Nodo Locale Applicativo (NLA).
    /// </summary>
    public partial class VerticalizzazioneStc : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "STC";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneStc()
        {

        }

        public VerticalizzazioneStc(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// E' l'identificativo dell'albero degli interventi che verrà utilizzato se il sistema NLA non riesce a mappare il tag dell'xml IDPROCEDIMENTO con un intervento di SIGePro.
        /// </summary>
        public string Alberoproc_scId
        {
            get { return this.GetString("ALBEROPROC.SC_ID"); }
            set { this.SetString("ALBEROPROC.SC_ID", value); }
        }

        /// <summary>
        /// Se S significa che SIGePro è integrato con un protocollo generale unico nel comune e quindi negli xml di comunicazione tra NLA e STC i tag NUMEROPROTOCOLLOGENERALE e DATAPROTOCOLLOGENERALE verranno compilati e scritti. Se N o non presente significa che SIGePro non è integrato con nessun protocollo o ha un protocollo di direzione. In questo caso i riferimenti a numero e data protocollo vengono compilati e scritti solamente nel caso che gli sportelli mittente/destinatario abbiano configurato in STC lo stesso identificativo di direzione.
        /// </summary>
        public string Protocollogenerale
        {
            get { return this.GetString("PROTOCOLLOGENERALE"); }
            set { this.SetString("PROTOCOLLOGENERALE", value); }
        }

        /// <summary>
        /// Utente per il login al sistema STC
        /// </summary>
        public string NlaUsername
        {
            get { return this.GetString("NLA_USERNAME"); }
            set { this.SetString("NLA_USERNAME", value); }
        }

        /// <summary>
        /// Password per il login al sistema STC
        /// </summary>
        public string NlaPassword
        {
            get { return this.GetString("NLA_PASSWORD"); }
            set { this.SetString("NLA_PASSWORD", value); }
        }

        /// <summary>
        /// Rappresenta l'url del servizio STC da utilizzare per le chiamate WS dei nodi NLA
        /// </summary>
        public string StcWsUrl
        {
            get { return this.GetString("STC_WS_URL"); }
            set { this.SetString("STC_WS_URL", value); }
        }

        /// <summary>
        /// E' il valore della colonna CODICESTRADARIO della tabella STRADARIO che verrà utilizzato se il sistema NLA non trova uno stradario nell' xml in arrivo.
        /// </summary>
        public string Stradario_codicestradario
        {
            get { return this.GetString("STRADARIO.CODICESTRADARIO"); }
            set { this.SetString("STRADARIO.CODICESTRADARIO", value); }
        }

        /// <summary>
        /// Id del nodo NLA BACKOFFICE registrato su STC
        /// </summary>
        public string NlaIdnodo
        {
            get { return this.GetString("NLA_IDNODO"); }
            set { this.SetString("NLA_IDNODO", value); }
        }

        /// <summary>
        /// Il codice del tipo soggetto di default da associare alle domande provenienti da STC qualora fallisse la logica di Lookup a partire dall'Xml ricevuto
        /// </summary>
        public string TipoSoggettoDefault
        {
            get { return this.GetString("TIPO_SOGGETTO_DEFAULT"); }
            set { this.SetString("TIPO_SOGGETTO_DEFAULT", value); }
        }

        /// <summary>
        /// Id del nodo NLA CHE IDENTICA IL PROTOCOLLO SIGEPRO registrato su STC (utilizzato per individuare le chiamate provenienti da protocollo sigepro)
        /// </summary>
        public string NlaIdnodoProtocollosigepro
        {
            get { return this.GetString("NLA_IDNODO_PROTOCOLLOSIGEPRO"); }
            set { this.SetString("NLA_IDNODO_PROTOCOLLOSIGEPRO", value); }
        }

        /// <summary>
        /// Id del nodo NLA PEOPLE registrato su STC (utilizzato per individuare le chiamate provenienti da people)
        /// </summary>
        public string NlaIdnodoPeople
        {
            get { return this.GetString("NLA_IDNODO_PEOPLE"); }
            set { this.SetString("NLA_IDNODO_PEOPLE", value); }
        }

        /// <summary>
        /// Id del nodo NLA AREARISERVATA registrato su STC (utilizzato per individuare le chiamate provenienti dall'area riservata)
        /// </summary>
        public string NlaIdnodoAreariservata
        {
            get { return this.GetString("NLA_IDNODO_AREARISERVATA"); }
            set { this.SetString("NLA_IDNODO_AREARISERVATA", value); }
        }

        /// <summary>
        /// Id del nodo NLA PEC MUTA registrato su STC (utilizzato per individuare le chiamate provenienti dal nodo NLA PEC MUTA)
        /// </summary>
        public string NlaIdnodoPecMuta
        {
            get { return this.GetString("NLA_IDNODO_PEC_MUTA"); }
            set { this.SetString("NLA_IDNODO_PEC_MUTA", value); }
        }

        /// <summary>
        /// Contiene la lista degli identificativi dei nodi che durante l'inserimento di una pratica tramite STC non devono aggiornare i dati presenti nell'
        ///     anagrafica (se esistente). Il carattere di separazione è la virgola (,)
        /// </summary>
        public string ListaNodiNonAggAnagrafe
        {
            get { return this.GetString("LISTA_NODI_NON_AGG_ANAGRAFE"); }
            set { this.SetString("LISTA_NODI_NON_AGG_ANAGRAFE", value); }
        }

        /// <summary>
        ///  Id del nodo NLA NLA-CART-COMUNICAZIONIINTERNE registrato su STC (utilizzato per individuare le chiamate provenienti da frontend CART compliant) 
        /// </summary>
        public string NlaIdnodoCartCominterne
        {
            get { return this.GetString("NLA_IDNODO_CART_COMINTERNE"); }
            set { this.SetString("NLA_IDNODO_CART_COMINTERNE", value); }
        }

        /// <summary>
        /// Id del nodo NLA AIDA registrato su STC (utilizzato per individuare le chiamate provenienti da AIDA)
        /// </summary>
        public string NlaIdnodoAida
        {
            get { return this.GetString("NLA_IDNODO_AIDA"); }
            set { this.SetString("NLA_IDNODO_AIDA", value); }
        }

        /// <summary>
        /// Indica il tempo di attesa in millisecondi per l'attivazione del componente che esegue le notifica automatiche in maniera asincrona. Se non specificato o non valido di default è 20000 (20 secondi)
        /// </summary>
        public string NotificheAutAttesaMillis
        {
            get { return this.GetString("NOTIFICHE_AUT_ATTESA_MILLIS"); }
            set { this.SetString("NOTIFICHE_AUT_ATTESA_MILLIS", value); }
        }

        /// <summary>
        /// Indica il tempo di attesa in millisecondi per la connessione del nodo di backoffice con il nodo STC. Se non specificato o non valido di default è 120000 (120 secondi)
        /// </summary>
        public string TimeoutConnessione
        {
            get { return this.GetString("TIMEOUT_CONNESSIONE"); }
            set { this.SetString("TIMEOUT_CONNESSIONE", value); }
        }

        /// <summary>
        /// Indica il tempo di attesa in millisecondi per la risposta del nodo di STC alla chiamata del nodo di backoffice. Se non specificato o non valido di default è 120000 (120 secondi)
        /// </summary>
        public string TimeoutRisposta
        {
            get { return this.GetString("TIMEOUT_RISPOSTA"); }
            set { this.SetString("TIMEOUT_RISPOSTA", value); }
        }

        /// <summary>
        /// Durante la creazione di un'istanza proveniente da STC, se impostato a 0 e il campo dinamico oggetto della mappatura non è presente tra le schede già collegate all'istanza allora vengono collegate all'istanza tutte le schede che lo contengono. Se impostato a 1 ed il campo dinamico oggetto della mappatura non è presente nelle schede dell'istanza non viene agganciata nessuna scheda all'istanza (e di fatto il valore del dato si perde). Le schede dinamiche che vengono agganciate all'istanza durante l’inserimento sono quelle configurate per l'albero, gli endo e la procedura.
        /// </summary>
        public string MappatureSchede
        {
            get { return this.GetString("MAPPATURE_SCHEDE"); }
            set { this.SetString("MAPPATURE_SCHEDE", value); }
        }

        /// <summary>
        /// Quando un nodo NLA processa un'integrazione documentale la gestisce come notifica attività verso il backoffice questo
        ///       cerca di individuare tra i movimenti della pratica quello con flag 'Front Office Richiedenti' e se non lo trova rilancia un errore.
        ///       Questo codice rappresenta il tipo movimento da utilizzare nel caso in cui il controllo precedente fallisca
        /// </summary>
        public string TipomovDefaultSoggEsterni
        {
            get { return this.GetString("TIPOMOV_DEFAULT_SOGG_ESTERNI"); }
            set { this.SetString("TIPOMOV_DEFAULT_SOGG_ESTERNI", value); }
        }

        /// <summary>
        /// Indicare l'elenco dei nodi mittenti per i quali recuperare gli allegati fisici invece dei riferimenti. Es. 410,420,430
        /// </summary>
        public string ListaNodiMittGetAllegati
        {
            get { return this.GetString("LISTA_NODI_MITT_GET_ALLEGATI"); }
            set { this.SetString("LISTA_NODI_MITT_GET_ALLEGATI", value); }
        }

        /// <summary>
        /// Determina in caso di pratiche o movimenti in ingresso inseriti tramite STC la chiamata al servizio di protocollazione. In questi casi anche se la direzione è la stessa la pratica - o il movimento - verranno protocollati ( sempre secondo le logiche definite nelle configurazioni). Accetta valori S o N (se vuoto allora N).
        /// </summary>
        public string ForzaProtocollazione
        {
            get { return this.GetString("FORZA_PROTOCOLLAZIONE"); }
            set { this.SetString("FORZA_PROTOCOLLAZIONE", value); }
        }


    }
}
