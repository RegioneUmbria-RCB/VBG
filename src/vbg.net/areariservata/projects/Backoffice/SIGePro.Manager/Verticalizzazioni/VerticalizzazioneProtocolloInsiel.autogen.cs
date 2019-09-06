
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_INSIEL il 26/08/2014 17.28.00
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se attivato, consente di eseguire la protocollazione con il sistema di INSIEL (INSIEL è il fornitore del software), deve essere anche inserita la stringa PROTOCOLLO_INSIEL nel parametro TIPOPROTOCOLLO della verticalizzazione PROTOCOLLO_ATTIVO. L'integrazione con questo protocollo, al momento, è presente al Comune di Trieste e di Pordenone. Sviluppato nel 2012.
    /// </summary>
    public partial class VerticalizzazioneProtocolloInsiel : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_INSIEL";

        public VerticalizzazioneProtocolloInsiel()
        {

        }

        public VerticalizzazioneProtocolloInsiel(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// (OBSOLETO), utilizzare il campo UO dell'amministrazione per configurare il registro. Il protocollo Insiel ha la necessità di sapere in quale registro protocollare, è quindi un dato che il web service richiede in modo obbligatorio. Deve essere fornito da Insiel o da un amministratore di protocollo presso il comune. Nell'ambiente di test generico fornito dall'azienda Insiel (url: http://193.42.71.39:8081/WsProtocollo/services/protocollo <http://193.42.71.39:8081/WsProtocollo/services/protocollo> ) il codice è GEN (sta per protocollo generale).
        /// </summary>
        public string Codiceregistro
        {
            get { return GetString("CODICEREGISTRO"); }
            set { SetString("CODICEREGISTRO", value); }
        }

        /// <summary>
        /// E' l'utente con cui avviene l'autenticazione al web service che coincide con quello cui avviene la protocollazione; se non valorizzato il sistema andrà a cercare il valore nel parametro OPERATORE della verticalizzazione PROTOCOLLO_ATTIVO, in quanto, da questo parametro, è possibile utilizzare dei segnalibri nel caso in cui gli operatori del protocollo siano mappati con quelli del backoffice.
        /// </summary>
        public string Codiceutente
        {
            get { return GetString("CODICEUTENTE"); }
            set { SetString("CODICEUTENTE", value); }
        }

        /// <summary>
        /// Indica il codice ufficio operante del protocollo di Insiel DA NON CONFONDERE con il codice ufficio che va configurato sull'amministrazione nel campo RUOLO e va di pari passo con il registro.
        /// </summary>
        public string CodiceUfficioOperante
        {
            get { return GetString("CODICE_UFFICIO_OPERANTE"); }
            set { SetString("CODICE_UFFICIO_OPERANTE", value); }
        }

        /// <summary>
        /// Consente di disabilitare la possibilità di annullare il protocollo da backoffice. Il web service di Insiel espone i metodi abilitati ad annullare un protocollo, questo però potrebbe non essere accettato dai responsabili. Al Comune di Pordenone ad esempio è stato espressamente chiesto di non dare la possibilità agli operatori di backoffice di eseguire le operazioni di annullamente, che devono essere adibite solamente agli operatori di protocollo. Se valorizzato a 1 elimina la scritta Annulla presente sotto il numero di protocollo nell'istanza o movimento, se uguale a 0 o non valorizzato la stessa scritta sarà visualizzata con annessa possibilità di annullamento.
        /// </summary>
        public string DisabilitaAnnullaProtocollo
        {
            get { return GetString("DISABILITA_ANNULLA_PROTOCOLLO"); }
            set { SetString("DISABILITA_ANNULLA_PROTOCOLLO", value); }
        }

        /// <summary>
        /// Se selezionato consente di ignorare i dati di classifica inviati dal backoffice al componente che gestisce il protocollo Insiel, questo in quanto il protocollo considera la classifica come facoltativa mentre, lato backoffice, è un dato obbligatorio che deve necessariamente essere valorizzato per essere validato, il componente .net che fa da tramite tra le due applicazioni si prenderà carico di gestire (se = 0 o assente) o meno (se valorizzato a 1) la classifica in base al valore di questo parametro. Ad esempio il Comune di Trieste non interessa questo tipo di dato mentro quello di Pordenone si.
        /// </summary>
        public string EscludiClassifica
        {
            get { return GetString("ESCLUDI_CLASSIFICA"); }
            set { SetString("ESCLUDI_CLASSIFICA", value); }
        }

        /// <summary>
        /// E' la password dell'utente segnalato sul parametro CODICEUTENTE utilizzata per autenticarsi, va valorizzata nel caso in cui sia valorizzato il parametro CODICEUTENTE.
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Indica la modalità di recupero dati dei tipi documento. Questi possono essere recuperati dalla tabella di VBG Protocollo_TipiDocumento, SE VALORIZZATO A 0 OPPURE A NULL, oppure direttamente dal web service di Insiel, invocando il metodo getTipiDoc, SE VALORIZZATO A 1. Nel primo caso i dati saranno più gestibili, ad esempio indicando solamente i tipi documento necessari all'integrazione, nel secondo avremmo un allineamento certo dei dati in quanto vengono invocati direttamente dagli archivi del protocollo, ma potrebbe anche esserci una mole di dati enorme che renderebbe difficoltosa la gestione di questo dato agli operatori; ad esempio al Comune di Trieste sono presenti più di 200 tipi documento, mentre ne viene utilizzato solamente uno, almeno per le pratiche provenienti da VBG.
        /// </summary>
        public string TipiDocumentoWs
        {
            get { return GetString("TIPI_DOCUMENTO_WS"); }
            set { SetString("TIPI_DOCUMENTO_WS", value); }
        }

        /// <summary>
        /// Indicare l'url del web service o del wsdl per eseguire la protocollazione con il protocollo INSIEL. Ad esempio, per testare l'integrazione Insiel ci ha fornito un ambiente di test che risponde all'url http://193.42.71.39:8081/WsProtocollo/services/protocollo
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// Indica l'url del web service utilizzato per fare l'upload degli allegati utilizzando .net, il parametro è obbligatorio altrimenti la protocollazione andrebbe si a buon fine, ma non sarebbe possibile spedire gli allegati (verrà quindi restituita comunque un'eccezione anche se la protocollazione sarà eseguita). Ad esempio Insiel ci ha fornito un ambiente di test che risponde all'url http://193.42.71.39:8081/ProtocolloFilesDocWs/
        /// </summary>
        public string UrlUploadfile
        {
            get { return GetString("URL_UPLOADFILE"); }
            set { SetString("URL_UPLOADFILE", value); }
        }

        /// <summary>
        /// Questo parametro può essere utilizzato solo per la versione 3 del protocollo insiel, che è quella dove è possibile inviare una pec per un protocollo in partenza, e serve per specificare il format delle anagrafiche da inviare al web service. Può assumere i seguenti valori: PEC - Aggiunge alla descrizione la pec, va a fare ricerche per descrizione, se non presente ne inserisce una nuova, se presente ma la pec manca o non è presente quella passata, aggiorna l'anagrafica aggiungendo il nuovo indirizzo pec; CODICE_FISCALE: Aggiunge il codice fiscale alla descrizione, va a cercare per descrizione e se non presente ne inserisce una nuova, se presente ma la pec manca o non è presente quella passata, aggiorna l'anagrafica aggiungendo il nuovo indirizzo pec; NORMALE: Non aggiunge niente al nominativo, ricerca per descrizione e se non presente ne inserisce una nuova, se presente ma la pec manca o non è presente quella passata, aggiorna l'anagrafica aggiungendo il nuovo indirizzo pec. Se questo parametro non viene valorizzato o viene valorizzato con un valore diverso da PEC, CODICE_FISCALE, NORMALE, le funzionalità di gestione anagrafiche del web service, e di invio pec, saranno ignorate.
        /// </summary>
        public string TipoGestionePec
        {
            get { return GetString("TIPO_GESTIONE_PEC"); }
            set { SetString("TIPO_GESTIONE_PEC", value); }
        }

        /// <summary>
        /// Viene attivato se assume il valore 1, in tutti gli altri casi non viene attivato. Questo parametro indica se il componente deve seguire le regole imposte dal Comune di Monfalcone in ambito di protocollazione. Le regole riguardano la compilazione della denominazione anagrafica da inviare al protocollo insiel, e sono: <NOME> <COGNOME> <LOCALITA_RESIDENZA> (<LOCALITA_SEDE_RESIDENZA> se azienda), se la località coincide con una provincia allora va indicata la sigla, quindi <NOME> <COGNOME> <SIGLAPROVINCIA>, se la località coincide con MONFALCONE allora va indicato il valore CITTA', quindi <NOME> <COGNOME> CITTA'.
        /// </summary>
        public string AttivaMonf
        {
            get { return GetString("ATTIVA_MONF"); }
            set { SetString("ATTIVA_MONF", value); }
        }

        /// <summary>
        /// Questo parametro indica se il componente che adatta la richiesta da inviare al protocollo, deve utilizzare il valore indicato nella classifica come codice (valore a 0 o non valorizzato), oppure come livelli (valorizzare a 1), nel secondo caso va configurata una classifica a più livelli, separati da un punto ".", ad esempio 8.1.1.4, 8 è il primo livello, 1 il secondo, 1 il terzo e 4 il quarto, il tutto fino a 8 livelli.
        /// </summary>
        public string UsaLivelliClassifica
        {
            get { return GetString("USA_LIVELLI_CLASSIFICA"); }
            set { SetString("USA_LIVELLI_CLASSIFICA", value); }
        }

        /// <summary>
        /// Inserire in questo parametro il valore necessario a far avviare ITERATTI in fase di protocollazione, di norma viene inserito il parametro DITER
        /// </summary>
        public string TipoUfficioIteratti
        {
            get { return GetString("TIPO_UFFICIO_ITERATTI"); }
            set { SetString("TIPO_UFFICIO_ITERATTI", value); }
        }

        /// <summary>
        /// Indica la modalità di recupero dati relativamente alle voci di titolario (classificazione). Se valorizzato a 1 saranno recuperate tramite apposito metodo del web service, altrimenti sarà utilizzato il metodo classico, ossia o da tabella protocollo_classifiche oppure tramite testo libero.
        /// </summary>
        public string UsaWsClassifiche
        {
            get { return GetString("USA_WS_CLASSIFICHE"); }
            set { SetString("USA_WS_CLASSIFICHE", value); }
        }

        /// <summary>
        /// Questo parametro determina come deve essere aggiornata un''anagrafica quando si richiede un protocollo. Da notare che questo parametro viene utilizzato solamente per la versione 3 del PROTOCOLLO_INSIEL (valore PROTOCOLLO_INSIEL3 su parametro TIPOPROTOCOLLO della regola PROTOCOLLO_ATTIVO) e solo con il parametro TIPO_GESTIONE_PEC impostato a RICERCA_CODICE_FISCALE e solo se la ricerca dell''anagrafica per codice fiscale o partita iva viene soddisfatta, altrimenti inserisce una nuova anagrafica.
        /// I valori che può assumere questo parametro sono: 
        /// L''anagrafica trovata non viene aggiornata
        /// 0 (o non gestito) --> L''anagrafica trovata non viene mai aggiornata.
        /// 1 --> L''anagrafica trovata viene aggiornata sempre con il nuovo indirizzo pec che si va ad aggiungere a quelli già inseriti, con la differenza che il nuovo indirizzo diventa il principale; 
        /// 2 --> L''anagrafica trovata viene aggiornata solamente se sprovvista di indirizzo pec.');
        /// </summary>
        public string TipoAggiornamentoAnagrafica
        {
            get { return GetString("TIPO_AGGIORNAMENTO_ANAG"); }
            set { SetString("TIPO_AGGIORNAMENTO_ANAG", value); }
        }

        /// <summary>
        /// Valore 1 o 0, indica se deve essere richiesto anche l'invio di una pec per i protocolli in partenza. Valido solo per la versione 3 del PROTOCOLLO_INSIEL.
        /// </summary>
        public string InviaPec
        {
            get { return GetString("INVIA_PEC"); }
            set { SetString("INVIA_PEC", value); }
        }
    }
}
