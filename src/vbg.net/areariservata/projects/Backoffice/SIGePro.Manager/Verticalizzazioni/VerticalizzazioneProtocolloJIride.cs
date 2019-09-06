
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /// <summary>
    /// Abilita il protocollo con J-IRIDE.
    /// </summary>
    public partial class VerticalizzazioneProtocolloJIride : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_JIRIDE";

        public VerticalizzazioneProtocolloJIride()
        {

        }

        public VerticalizzazioneProtocolloJIride(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Percorso completo del WS di fascicolazione.
        /// </summary>
        public string Urlfasc
        {
            get { return GetString("URLFASC"); }
            set { SetString("URLFASC", value); }
        }

        /// <summary>
        /// Parametro Si o No (valore 1 o altro), se valorizzato a 1, l''invio della pec tramite ws PosteWeb, sarà interoperabile, sarà quindi inserito l''elemento <InvioInteroperabile> valorizzato a S, questo consente l''invio della segnatura xml.
        /// </summary>
        public string PecInterop
        {
            get { return GetString("PEC_INTEROP"); }
            set { SetString("PEC_INTEROP", value); }
        }

        /// <summary>
        /// Parametro Si o No (valore 1 o altro), se valorizzato a 1, l''invio della pec tramite ws PosteWeb, sarà interoperabile, sarà quindi inserito l''elemento <InvioInteroperabile> valorizzato a S, questo consente l''invio della segnatura xml.
        /// </summary>
        public string ProtoOggettoPec
        {
            get { return GetString("PROTO_OGGETTO_PEC"); }
            set { SetString("PROTO_OGGETTO_PEC", value); }
        }

        /// <summary>
        /// Parametro che in J-Iride specifica se aggiornare la classifica del documento se diversa da quella del fascicolo
        /// </summary>
        public string Aggiornaclassifica
        {
            get { return GetString("AGGIORNACLASSIFICA"); }
            set { SetString("AGGIORNACLASSIFICA", value); }
        }

        /// <summary>
        /// Numero pratica da passare al WS Iride a Ravenna "9999999"
        /// </summary>
        public string Numeropratica
        {
            get { return GetString("NUMEROPRATICA"); }
            set { SetString("NUMEROPRATICA", value); }
        }

        /// <summary>
        /// Parametro che in Iride specifica se aggiornare le anagrafiche. Possibili valori S o N.
        /// </summary>
        public string Aggiornaanagrafiche
        {
            get { return GetString("AGGIORNAANAGRAFICHE"); }
            set { SetString("AGGIORNAANAGRAFICHE", value); }
        }

        /// <summary>
        /// Percorso completo del WS
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// Indica il codice di amministrazione da passare al web service di Iride, deve essere fornito dall'amministratore del protocollo. Questo dato indica su quale amministrazione protocollare, infatti Iride può avere un'installazione MultiDB, in questo caso va valorizzato questo dato che, tra le altre cose può indicare se si sta utilizzando un ambiente di Test o di Produzione.
        /// </summary>
        public string Codiceamministrazione
        {
            get { return GetString("CODICEAMMINISTRAZIONE"); }
            set { SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// Consente di disabilitare la funzionalità di creazione delle copie su Iride (se = 1). Inserito in quanto l'invio della notifica tramite il movimento dell'istanza creava automaticamente anche la copia sul sistema di protocollazione Iride, e questo può creare alcuni disagi per i clienti che gestiscono manualmente le pratiche o che abbiano già creato il protocollo su Iride, come nel caso della Bassa Romagna. NB. L'utilizzo di questo parametro può portare a qualche problema durante la funzionalità di leggi protocollo, infatti se valorizzato a 1, i dati inviati tramite notifica STC sulla nuova istanza su nuovo software non saranno compresi di IDPROTOCOLLO, questo significa che, in presenza di più copie (su IRIDE) con stessa numerazione ma con ID diverso, nel momento della lettura di un protocollo senza ID può essere riportato quello con ID sbagliato, in teoria comunque i dati dei protocolli con diversi ID ma con stessa numerazione dovrebbero essere gli stessi.
        /// </summary>
        public string DisabilitaCreacopie
        {
            get { return GetString("DISABILITA_CREACOPIE"); }
            set { SetString("DISABILITA_CREACOPIE", value); }
        }

        /// <summary>
        /// Indica il mezzo di default da passare al web service di Iride, non è un dato obbligatorio quindi può essere omesso. Questo dato, se valorizzato, viene proposto di default nella maschera di protocollazione nel dropdown riguardante i mezzi e se lo stesso valore è presente nella tabella PROTOCOLLO_MEZZI, viene inviato al web service anche quando la protocollazione è automatica, quindi proveniente da un frontoffice o dal backoffice se impostata la configurazione di protocollazione automatica. NB. se la tabella PROTOCOLLO_MEZZI è vuota il drop down mezzi, nella maschera di protocollazione, non viene visualizzato, ignorando quindi il valore di questo parametro.
        /// </summary>
        public string MezzoDefault
        {
            get { return GetString("MEZZO_DEFAULT"); }
            set { SetString("MEZZO_DEFAULT", value); }
        }

        /// <summary>
        /// Attivando il pulsante c'è la possibilità di comunicare al software INTRANOS (Comune di Ravenna) che un documento deve essere firmato. Ogni successivo cambiamento di stato compiuto in INTRANOS + IRIDE viene comunicato al Backoffice tramite WebService (Es: "Il documento è stato firmato", "Il documento è stato protocollato", "Il documento è stato inviato"). I cambiamenti di stato vengono scritti nel backoffice nella sezione eventi del movimento e, nel caso di protocollazione, viene riportato in automatico il numero di protocollo nel movimento. Accetta valori S o N
        /// </summary>
        public string MostraMettiAllaFirma
        {
            get { return GetString("MOSTRA_METTI_ALLA_FIRMA"); }
            set { SetString("MOSTRA_METTI_ALLA_FIRMA", value); }
        }

        /// <summary>
        /// Indicare in questo parametro l'endpoint da utilizzare per usare il servizio di invio posta elettronica certificata di J-Iride, sarà proprio questo servizio che si occuperà di inviare la PEC
        /// </summary>
        public string UrlPec
        {
            get { return GetString("URL_PEC"); }
            set { SetString("URL_PEC", value); }
        }

        /// <summary>
        /// Indicare il mezzo da utilizzare se si vuole inviare la PEC richiamando il web service PosteWeb di Iride. Se il protocollo è in partenza e sono presenti dei destinatari esterni (che non abbiano scrivanie su Iride) è necessario indicare il mezzo per cui, al destinatario, venga inviata anche una PEC tramite il servizio PostePec di Iride indicato nel parametro URL_PEC, se sarà utilizzato quindi il mezzo indicato in questo parametro il sistema si occuperà di chiamare quindi il servizio di invio pec di iride
        /// </summary>
        public string MezzoPec
        {
            get { return GetString("MEZZO_PEC"); }
            set { SetString("MEZZO_PEC", value); }
        }

        /// <summary>
        /// Inserire in questo parametro il valore relativo al mittente che invia la mail pec in fase di protocollazione in partenza, questo parametro è legato all'altro parametro URL_PEC in quanto, se non presente quest'ultimo, MITTENTE_PEC non viene utilizzato
        /// </summary>
        public string MittentePec
        {
            get { return GetString("MITTENTE_PEC"); }
            set { SetString("MITTENTE_PEC", value); }
        }

        /// <summary>
        /// Serve per indicare in quale formato deve essere inviata la data di fascicolazione al web service. Maggioli ha creato un altro web service compatibile con Iride (ProtocolloSoap e DocWsFascicoli), solo che la data deve avere formato yyyy-MM-dd altrimenti non viene riconosciuta. Quindi, solo nel momento in cui vengono usati questi web service, il formato data deve essere impostato a yyyy-MM-dd, in generale, comunque, il componente PROTOCOLLO_IRIDE andrà ad impostare la data in base a quanto scritto in questo parametro, quindi, se viene impostato dd-MM-yyyy il sistema invierà ad esempio 23-10-2014, che nel caso specifico di questo web service, restituirà errore, evitare quindi di inserire ulteriori formati. Nel caso del vecchio Iride, non va impostato.
        /// </summary>
        public string FormatoDataFasc
        {
            get { return GetString("FORMATO_DATA_FASC"); }
            set { SetString("FORMATO_DATA_FASC", value); }
        }

        /// <summary>
        /// Inserire in questo parametro la stringa che viene restituita dal web service nn fase di protocollazione avvenuta correttamente. Iride restituisce un messaggio anche in caso di protocollazione avvenuta correttamente, il messaggio viene successivamente inserito nei warning quindi va eliminata la stringa restituita in caso di protocollazione OK.
        /// </summary>
        public string WarningDaEliminare
        {
            get { return GetString("WARNING_DA_ELIMINARE"); }
            set { SetString("WARNING_DA_ELIMINARE", value); }
        }

        /// <summary>
        /// Questo parametro serve per indicare al plugin del protocollo per capire se la lettura del protocollo deve essere fatta invocando il metodo LeggiProtocollo oppure LeggiDocumento, nel primo caso la chiamata viene fatta passando il numero e l'anno del protocollo, nel secondo l'id del documento, di default, se valorizzato il campo FKIDPROTOCOLLO delle ISTANZE o MOVIMENTO viene invocato il metodo LeggiDocumento, valorizzando questo parametro a 1 il sistema forza la chiamata sempre utilizzando il metodo LeggiProtocollo, questo perchè il nuovo ws retrocompatibile IRIDE di Maggioli ha un bug su leggi documento che non ritorna gli allegati.
        /// </summary>
        public string UsaNumAnnoLeggi
        {
            get { return GetString("USA_NUM_ANNO_LEGGI"); }
            set { SetString("USA_NUM_ANNO_LEGGI", value); }
        }

        /// <summary>
        /// Serve solamente per l''invio delle PEC in quanto il metodo utilizzato da J-IRIDE inviaMail lo richiede in modo necessario.
        /// </summary>
        public string Aoo
        {
            get { return GetString("AOO"); }
            set { SetString("AOO", value); }
        }

        /// <summary>
        /// Se valorizzato a 1 consente di visualizzare sempre un warning relativo all''invio PEC di una protocollazione in partenza, sia che vada a buon fine che vada in errore.
        /// </summary>
        public string Warning_Pec
        {
            get { return GetString("WARNING_PEC"); }
            set { SetString("WARNING_PEC", value); }
        }

        /// <summary>
        /// Indicare il codice UO (ufficio) che indica il mittente interno per le pratiche in arrivo, nel protocollo sta ad indicare lo smistamento, in pratica quale ufficio ha lavorato il documento (in arrivo).
        /// </summary>
        public string UoSmistamento
        {
            get { return GetString("UO_SMISTAMENTO"); }
            set { SetString("UO_SMISTAMENTO", value); }
        }

        /// <summary>
        /// Se valorizzato a 1 consente di non impostare il carico per le protocollazioni in partenza, come accade di default, quindi, se non valorizzato o valorizzato diverso da 1 la procedura sarà la medesima di default, quindi verrà impostato il carico con il dato del mittente.
        /// </summary>
        public string DisabilitaCaricoPartenza
        {
            get { return GetString("DISABILITA_CARICO_P"); }
            set { SetString("DISABILITA_CARICO_P", value); }
        }

        /// <summary>
        /// Indica la tipologia di recapito relativa alla Mail / Pec. Questo tipo di dato deve essere fornito dal gestore di protocollo perchè indica, appunto la tipologia che deve assumere il recapito, va indicato solo se, in accordo con l'ente, è stata aggiunta una tipologia nuova ( di default è impostata a EMAIL).
        /// </summary>
        public string TipoRecapitoEmail
        {
            get { return GetString("TIPORECAPITO_EMAIL"); }
            set { SetString("TIPORECAPITO_EMAIL", value); }

        }
    }
}
