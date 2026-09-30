using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    /// <summary>
    /// Abilita il protocollo con J-IRIDE.
    /// </summary>
    public partial class VerticalizzazioneProtocolloJIride : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_JIRIDE";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloJIride()
        {

        }

        public VerticalizzazioneProtocolloJIride(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Percorso completo del WS di fascicolazione.
        /// </summary>
        public string Urlfasc
        {
            get { return this.GetString("URLFASC"); }
            set { this.SetString("URLFASC", value); }
        }

        /// <summary>
        /// Parametro Si o No (valore 1 o altro), se valorizzato a 1, l''invio della pec tramite ws PosteWeb, sarà interoperabile, sarà quindi inserito l''elemento <InvioInteroperabile> valorizzato a S, questo consente l''invio della segnatura xml.
        /// </summary>
        public string PecInterop
        {
            get { return this.GetString("PEC_INTEROP"); }
            set { this.SetString("PEC_INTEROP", value); }
        }

        /// <summary>
        /// Parametro Si o No (valore 1 o altro), se valorizzato a 1, l''invio della pec tramite ws PosteWeb, sarà interoperabile, sarà quindi inserito l''elemento <InvioInteroperabile> valorizzato a S, questo consente l''invio della segnatura xml.
        /// </summary>
        public string ProtoOggettoPec
        {
            get { return this.GetString("PROTO_OGGETTO_PEC"); }
            set { this.SetString("PROTO_OGGETTO_PEC", value); }
        }

        /// <summary>
        /// Parametro che in J-Iride specifica se aggiornare la classifica del documento se diversa da quella del fascicolo
        /// </summary>
        public string Aggiornaclassifica
        {
            get { return this.GetString("AGGIORNACLASSIFICA"); }
            set { this.SetString("AGGIORNACLASSIFICA", value); }
        }

        /// <summary>
        /// Numero pratica da passare al WS Iride a Ravenna "9999999"
        /// </summary>
        public string Numeropratica
        {
            get { return this.GetString("NUMEROPRATICA"); }
            set { this.SetString("NUMEROPRATICA", value); }
        }

        /// <summary>
        /// Parametro che in Iride specifica se aggiornare le anagrafiche. Possibili valori S o N.
        /// </summary>
        public string Aggiornaanagrafiche
        {
            get { return this.GetString("AGGIORNAANAGRAFICHE"); }
            set { this.SetString("AGGIORNAANAGRAFICHE", value); }
        }

        /// <summary>
        /// Percorso completo del WS
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// Indica il codice di amministrazione da passare al web service di Iride, deve essere fornito dall'amministratore del protocollo. Questo dato indica su quale amministrazione protocollare, infatti Iride può avere un'installazione MultiDB, in questo caso va valorizzato questo dato che, tra le altre cose può indicare se si sta utilizzando un ambiente di Test o di Produzione.
        /// </summary>
        public string Codiceamministrazione
        {
            get { return this.GetString("CODICEAMMINISTRAZIONE"); }
            set { this.SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Consente di disabilitare la funzionalità di creazione delle copie su Iride (se = 1). Inserito in quanto l'invio della notifica tramite il movimento dell'istanza creava automaticamente anche la copia sul sistema di protocollazione Iride, e questo può creare alcuni disagi per i clienti che gestiscono manualmente le pratiche o che abbiano già creato il protocollo su Iride, come nel caso della Bassa Romagna. NB. L'utilizzo di questo parametro può portare a qualche problema durante la funzionalità di leggi protocollo, infatti se valorizzato a 1, i dati inviati tramite notifica STC sulla nuova istanza su nuovo software non saranno compresi di IDPROTOCOLLO, questo significa che, in presenza di più copie (su IRIDE) con stessa numerazione ma con ID diverso, nel momento della lettura di un protocollo senza ID può essere riportato quello con ID sbagliato, in teoria comunque i dati dei protocolli con diversi ID ma con stessa numerazione dovrebbero essere gli stessi.
        /// </summary>
        public string DisabilitaCreacopie
        {
            get { return this.GetString("DISABILITA_CREACOPIE"); }
            set { this.SetString("DISABILITA_CREACOPIE", value); }
        }

        /// <summary>
        /// Indica il mezzo di default da passare al web service di Iride, non è un dato obbligatorio quindi può essere omesso. Questo dato, se valorizzato, viene proposto di default nella maschera di protocollazione nel dropdown riguardante i mezzi e se lo stesso valore è presente nella tabella PROTOCOLLO_MEZZI, viene inviato al web service anche quando la protocollazione è automatica, quindi proveniente da un frontoffice o dal backoffice se impostata la configurazione di protocollazione automatica. NB. se la tabella PROTOCOLLO_MEZZI è vuota il drop down mezzi, nella maschera di protocollazione, non viene visualizzato, ignorando quindi il valore di questo parametro.
        /// </summary>
        public string MezzoDefault
        {
            get { return this.GetString("MEZZO_DEFAULT"); }
            set { this.SetString("MEZZO_DEFAULT", value); }
        }

        /// <summary>
        /// Attivando il pulsante c'è la possibilità di comunicare al software INTRANOS (Comune di Ravenna) che un documento deve essere firmato. Ogni successivo cambiamento di stato compiuto in INTRANOS + IRIDE viene comunicato al Backoffice tramite WebService (Es: "Il documento è stato firmato", "Il documento è stato protocollato", "Il documento è stato inviato"). I cambiamenti di stato vengono scritti nel backoffice nella sezione eventi del movimento e, nel caso di protocollazione, viene riportato in automatico il numero di protocollo nel movimento. Accetta valori S o N
        /// </summary>
        public string MostraMettiAllaFirma
        {
            get { return this.GetString("MOSTRA_METTI_ALLA_FIRMA"); }
            set { this.SetString("MOSTRA_METTI_ALLA_FIRMA", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint da utilizzare per usare il servizio di invio posta elettronica certificata di J-Iride, sarà proprio questo servizio che si occuperà di inviare la PEC
        /// </summary>
        public string UrlPec
        {
            get { return this.GetString("URL_PEC"); }
            set { this.SetString("URL_PEC", value); }
        }

        /// <summary>
        /// Indicare il mezzo da utilizzare se si vuole inviare la PEC richiamando il web service PosteWeb di Iride. Se il protocollo è in partenza e sono presenti dei destinatari esterni (che non abbiano scrivanie su Iride) è necessario indicare il mezzo per cui, al destinatario, venga inviata anche una PEC tramite il servizio PostePec di Iride indicato nel parametro URL_PEC, se sarà utilizzato quindi il mezzo indicato in questo parametro il sistema si occuperà di chiamare quindi il servizio di invio pec di iride
        /// </summary>
        public string MezzoPec
        {
            get { return this.GetString("MEZZO_PEC"); }
            set { this.SetString("MEZZO_PEC", value); }
        }

        /// <summary>
        /// Inserire in questo parametro il valore relativo al mittente che invia la mail pec in fase di protocollazione in partenza, questo parametro è legato all'altro parametro URL_PEC in quanto, se non presente quest'ultimo, MITTENTE_PEC non viene utilizzato
        /// </summary>
        public string MittentePec
        {
            get { return this.GetString("MITTENTE_PEC"); }
            set { this.SetString("MITTENTE_PEC", value); }
        }

        /// <summary>
        /// Serve per indicare in quale formato deve essere inviata la data di fascicolazione al web service. Maggioli ha creato un altro web service compatibile con Iride (ProtocolloSoap e DocWsFascicoli), solo che la data deve avere formato yyyy-MM-dd altrimenti non viene riconosciuta. Quindi, solo nel momento in cui vengono usati questi web service, il formato data deve essere impostato a yyyy-MM-dd, in generale, comunque, il componente PROTOCOLLO_IRIDE andrà ad impostare la data in base a quanto scritto in questo parametro, quindi, se viene impostato dd-MM-yyyy il sistema invierà ad esempio 23-10-2014, che nel caso specifico di questo web service, restituirà errore, evitare quindi di inserire ulteriori formati. Nel caso del vecchio Iride, non va impostato.
        /// </summary>
        public string FormatoDataFasc
        {
            get { return this.GetString("FORMATO_DATA_FASC"); }
            set { this.SetString("FORMATO_DATA_FASC", value); }
        }

        /// <summary>
        /// Inserire in questo parametro la stringa che viene restituita dal web service nn fase di protocollazione avvenuta correttamente. Iride restituisce un messaggio anche in caso di protocollazione avvenuta correttamente, il messaggio viene successivamente inserito nei warning quindi va eliminata la stringa restituita in caso di protocollazione OK.
        /// </summary>
        public string WarningDaEliminare
        {
            get { return this.GetString("WARNING_DA_ELIMINARE"); }
            set { this.SetString("WARNING_DA_ELIMINARE", value); }
        }

        /// <summary>
        /// Questo parametro serve per indicare al plugin del protocollo per capire se la lettura del protocollo deve essere fatta invocando il metodo LeggiProtocollo oppure LeggiDocumento, nel primo caso la chiamata viene fatta passando il numero e l'anno del protocollo, nel secondo l'id del documento, di default, se valorizzato il campo FKIDPROTOCOLLO delle ISTANZE o MOVIMENTO viene invocato il metodo LeggiDocumento, valorizzando questo parametro a 1 il sistema forza la chiamata sempre utilizzando il metodo LeggiProtocollo, questo perchè il nuovo ws retrocompatibile IRIDE di Maggioli ha un bug su leggi documento che non ritorna gli allegati.
        /// </summary>
        public string UsaNumAnnoLeggi
        {
            get { return this.GetString("USA_NUM_ANNO_LEGGI"); }
            set { this.SetString("USA_NUM_ANNO_LEGGI", value); }
        }

        /// <summary>
        /// Serve solamente per l''invio delle PEC in quanto il metodo utilizzato da J-IRIDE inviaMail lo richiede in modo necessario.
        /// </summary>
        public string Aoo
        {
            get { return this.GetString("AOO"); }
            set { this.SetString("AOO", value); }
        }

        /// <summary>
        /// Se valorizzato a 1 consente di visualizzare sempre un warning relativo all''invio PEC di una protocollazione in partenza, sia che vada a buon fine che vada in errore.
        /// </summary>
        public string Warning_Pec
        {
            get { return this.GetString("WARNING_PEC"); }
            set { this.SetString("WARNING_PEC", value); }
        }

        /// <summary>
        /// Indicare il codice UO (ufficio) che indica il mittente interno per le pratiche in arrivo, nel protocollo sta ad indicare lo smistamento, in pratica quale ufficio ha lavorato il documento (in arrivo).
        /// </summary>
        public string UoSmistamento
        {
            get { return this.GetString("UO_SMISTAMENTO"); }
            set { this.SetString("UO_SMISTAMENTO", value); }
        }

        /// <summary>
        /// Se valorizzato a 1 consente di non impostare il carico per le protocollazioni in partenza, come accade di default, quindi, se non valorizzato o valorizzato diverso da 1 la procedura sarà la medesima di default, quindi verrà impostato il carico con il dato del mittente.
        /// </summary>
        public string DisabilitaCaricoPartenza
        {
            get { return this.GetString("DISABILITA_CARICO_P"); }
            set { this.SetString("DISABILITA_CARICO_P", value); }
        }

        /// <summary>
        /// Indica la tipologia di recapito relativa alla Mail / Pec. Questo tipo di dato deve essere fornito dal gestore di protocollo perchè indica, appunto la tipologia che deve assumere il recapito, va indicato solo se, in accordo con l'ente, è stata aggiunta una tipologia nuova ( di default è impostata a EMAIL).
        /// </summary>
        public string TipoRecapitoEmail
        {
            get { return this.GetString("TIPORECAPITO_EMAIL"); }
            set { this.SetString("TIPORECAPITO_EMAIL", value); }

        }

        /// <summary>
        /// Impostare a 1 se si vuole visualizzare l'esito delle ricevute inviate via PEC dal sistema di protocollo, tra l'elenco degli allegati in fase di lettura di un protocollo.
        /// </summary>
        public string VisualizzaRicevutePec
        {
            get { return this.GetString("VISUALIZZA_RICEVUTE_PEC"); }
            set { this.SetString("VISUALIZZA_RICEVUTE_PEC", value); }

        }


        /// <summary>
        /// Impostare il valore che si desidera utilizzare per definire che tipo di assegnazione deve essere effettuata per la creazione di una copia, utilizzata soprattutto in ambito di protocollazione interna con più destinatari. I valore che possono essere indicati sono: COMPETENZA e CONOSCENZA, se non indicato niente o se verrà indicata una stringa non corretta (attenzione, va scritto in maiuscolo), di default verrà applicato il valore CONOSCENZA.
        /// </summary>
        public string TipoAssegnazioneCopiaDef
        {
            get { return this.GetString("TIPO_ASSEGNAZIONE_COPIA_DEF"); }
            set { this.SetString("TIPO_ASSEGNAZIONE_COPIA_DEF", value); }

        }
    }
}
