
using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_ATTIVO il 26/08/2014 17.28.01
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se attivato consente di visualizzare i parametri di configurazione riguardanti il protocollo, come ad esempio i "PARAMETRI PROTOCOLLO" presenti negli interventi; una volta attivato però andranno configurati anche tutti i parametri necessari al funzionamento dell'integrazione, quindi i parametri di questa verticalizzazione e di quella relativa al protocollo (ad esempio PROTOCOLLO_IRIDE per l'integrazione con IRIDE).
    /// </summary>
    public partial class VerticalizzazioneProtocolloAttivo : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ATTIVO";

        public VerticalizzazioneProtocolloAttivo()
        {

        }

        public VerticalizzazioneProtocolloAttivo(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Se 1 nella pagina di dettaglio della pratica compare il tasto Stampa qualora il protocollo sia stato già realizzato
        /// </summary>
        public string Visualizzabottonestampa
        {
            get { return GetString("VISUALIZZABOTTONESTAMPA"); }
            set { SetString("VISUALIZZABOTTONESTAMPA", value); }
        }

        /// <summary>
        /// Se 1 nella pagina di dettaglio della pratica compare il tasto Leggi qualora il protocollo sia stato già realizzato
        /// </summary>
        public string Visualizzabottoneleggi
        {
            get { return GetString("VISUALIZZABOTTONELEGGI"); }
            set { SetString("VISUALIZZABOTTONELEGGI", value); }
        }

        /// <summary>
        /// Url della pagina da utilizzare per stampare il numero e data di protocollo. Se non impostato utilizza l'indirizzo di default cl_ProtocolloStampa.asp
        /// </summary>
        public string VisualizzabottonestampaUrl
        {
            get { return GetString("VISUALIZZABOTTONESTAMPA_URL"); }
            set { SetString("VISUALIZZABOTTONESTAMPA_URL", value); }
        }

        /// <summary>
        /// Specifica l'operatore da utilizzare per l'inserimento di un protocollo. E' possibilie utilizzare il segnaposto $CODICEOPERATORE$ che a runtime verrà sostituito con il codice utente correntemente loggato oppure il segnaposto $SIGEPRO_USERID$ che verrà sostituito con la user id presente nell'anagrafica degli operatori
        /// </summary>
        public string Operatore
        {
            get { return GetString("OPERATORE"); }
            set { SetString("OPERATORE", value); }
        }

        /// <summary>
        /// Questo parametro va utilizzato solamente nel caso in cui nel parametro OPERATORE sia specificato un 
        /// valore segnalibro, ossia un valore che inizia e termina con il carattere -$-, solo in questo caso questo parametro 
        /// sarà preso in considerazione e il suo valore sarà cercato tra i codici operatori di SIGEPRO 
        /// (quindi va passato necessariamente un valore numerico) che, una volta trovato, andrà a valorizzare il dato da passare 
        /// al web service tramite le regole descritte appunto nel parametro OPERATORE che indicano come individuarne il 
        /// campo esatto tramite il segnalibro
        /// </summary>
        public string Codoperatorefo
        {
            get { return GetString("CODOPERATOREFO"); }
            set { SetString("CODOPERATOREFO", value); }
        }

        /// <summary>
        /// Se impostato a 1 aggiunge al numero protocollo anche l'anno. ( Es. 44/2005 )
        /// </summary>
        public string Aggiungianno
        {
            get { return GetString("AGGIUNGIANNO"); }
            set { SetString("AGGIUNGIANNO", value); }
        }

        /// <summary>
        /// E’ il parametro che permette di stabilire se nella protocollazione automatica da FO occorre gestire gli allegati o meno, di default gli allegati sono gestiti. Es. 1=Allegati non gestiti, 0=Allegati gestiti
        /// </summary>
        public string Noallegatifo
        {
            get { return GetString("NOALLEGATIFO"); }
            set { SetString("NOALLEGATIFO", value); }
        }

        /// <summary>
        /// Se 1 vengono eliminati gli 0 alla sinistra del numero del protocollo. Se 0 non accade nulla
        /// </summary>
        public string Modificanumero
        {
            get { return GetString("MODIFICANUMERO"); }
            set { SetString("MODIFICANUMERO", value); }
        }

        /// <summary>
        /// Se 1 viene gestita la fascicolazione se 0 no. Non tutti i protocolli sono integrati con la funzionalità di fascicolazione (Iride=si, DocsPa=si) 
        /// </summary>
        public string GestisciFascicolazione
        {
            get { return GetString("GESTISCI_FASCICOLAZIONE"); }
            set { SetString("GESTISCI_FASCICOLAZIONE", value); }
        }

        /// <summary>
        /// Se 1 allora è possibile selezionare i destinatari per conoscenza. Valore di default 0
        /// </summary>
        public string Destinataricc
        {
            get { return GetString("DESTINATARICC"); }
            set { SetString("DESTINATARICC", value); }
        }

        /// <summary>
        /// Se 1 allora in fase di richiesta di un protocollo è possibile inserire il numero e la data del protocollo mittente. Valore di default 0
        /// </summary>
        public string Numdataprotmitt
        {
            get { return GetString("NUMDATAPROTMITT"); }
            set { SetString("NUMDATAPROTMITT", value); }
        }

        /// <summary>
        /// E' il tipo protocollo attivato (IRIDE,PINDARO,SIGEPRO,GEPROT... vedi l'enumerazione 
        /// </summary>
        public string Tipoprotocollo
        {
            get { return GetString("TIPOPROTOCOLLO"); }
            set { SetString("TIPOPROTOCOLLO", value); }
        }

        /// <summary>
        /// E' la tipologia di documento da utilizzare per default per protocollare le istanze o i movimenti pervenuti dal front-office. Il codice dipende dal protocollo attivato. Es. 1=Lettera oppure LET=Lettera...a seconda del protocollo in esame
        /// </summary>
        public string Tipodocumentodefault
        {
            get { return GetString("TIPODOCUMENTODEFAULT"); }
            set { SetString("TIPODOCUMENTODEFAULT", value); }
        }

        /// <summary>
        /// E' il flusso di protocollo utilizzato per default per richieste provenienti da FO. Es. A=Arrivo,U=Uscita;I=Interno.
        /// </summary>
        public string Flussodefault
        {
            get { return GetString("FLUSSODEFAULT"); }
            set { SetString("FLUSSODEFAULT", value); }
        }

        /// <summary>
        /// E' la tipologia di documento da utilizzare per default per protocollare le istanze immesse da back-office tramite inserimento rapido,inserimento normale automaticamente protocollate e protocollazione massiva di movimenti. Il codice dipende dal protocollo attivato. Es. 1=Lettera oppure LET=Lettera...
        /// </summary>
        public string Tipodocumentodefaultbo
        {
            get { return GetString("TIPODOCUMENTODEFAULTBO"); }
            set { SetString("TIPODOCUMENTODEFAULTBO", value); }
        }

        /// <summary>
        /// può assumere il valore di PROT_UO o PROT_RUOLO. Nei combo relativi ai mittenti e destinatari, qualora si tratti di amministrazioni con PROT_UO e/o PROT_RUOLO impostati,  non saranno visualizzate le amministrazioni di SIGePro ma il campo indicato nel parametro (PROT_UO o PROT_RUOLO) raggruppati per group by. Es. anzichè mostrare le amministrazioni "Ufficio Copia" e "Ufficio Istruttoria" sarà visualizzato "CASTER" che è il nome della PROT_UO associata ad entrambe le amministrazioni
        /// </summary>
        public string Visualizzauoraggruppate
        {
            get { return GetString("VISUALIZZAUORAGGRUPPATE"); }
            set { SetString("VISUALIZZAUORAGGRUPPATE", value); }
        }

        /// <summary>
        /// E’ il parametro che permette di stabilire se nella pagina di richiesta protocollo sia presente o meno la sezione per inviare gli allegati della pratica e dei movimenti.Inoltre permette di stabilire se gestire gli allegati o meno nel caso di protocollazione automatiche da BO (Inserimento normale, inserimento rapido e protocollazione massiva di movimenti.  Es. 1=Sezione non presente, 0=Sezione presente
        /// </summary>
        public string Noallegati
        {
            get { return GetString("NOALLEGATI"); }
            set { SetString("NOALLEGATI", value); }
        }

        /// <summary>
        /// Se 1 allora nella pagina di richiesta protocollo da movimento non verranno precompilati mittenti e/o destinatari
        /// </summary>
        public string MovnoprecompilamittDest
        {
            get { return GetString("MOVNOPRECOMPILAMITT_DEST"); }
            set { SetString("MOVNOPRECOMPILAMITT_DEST", value); }
        }

        /// <summary>
        /// Può assumere tre valori: 
        /// 0 (o non presente) Il nome file da mandare al protocollo è quello indicato nelle descrizioni presenti nel backoffice (es. ALBEROPROC_DOCUMENTI.DESCRIZIONE, DOCUMENTIISTANZA.DOCUMENTO…). Potrebbe contenere accenti, lettere accentate o caratteri non convenzionali per un nome file (es. :,?,*) in quanto si tratta di descrizioni libere generate o modificate dagli operatori. 
        /// 1 Il nome file da mandare al protocollo è quello che è stato caricato in upload dall’operatore. Anche in questo caso potrebbe contenere accenti e lettere accentate che alcuni software di protocollo non accettano (ad esempio IRIDE).
        /// 2 Il nome file da mandare al protocollo viene generato nella forma IDCOMUNE-CODICEOGGETTO.ESTENSIONE (Es. DEF-1821.pdf), scelta sicura ma da verificare insieme al cliente se il formato è di suo gradimento.
        /// 3 Il nome file da mandare al protocollo viene generato nella forma CODICECOMUNE-CODICEOGGETTO-DESCRIZIONE(primi trenta caratteri).ESTENSIONE, da verificare insieme al cliente se il formato è di suo gradimento.
        /// NOTA BENE: 
        /// 1) in tutti i casi dove è presente la descrizione (valore 0 e 3) saranno considerati solamente i primi trenta caratteri.
        /// 2) in tutti i casi dove è presente la descrizione (valore 0 e 3) e il nome file (valore 1) saranno eliminati (dalla descrizione o dal nome file) i caratteri presenti nel parametro LISTA_CARATTERI_DA_ELIMINARE della verticalizzazione PROTOCOLLO_ATTIVO.
        /// </summary>
        public string NomefileOrigine
        {
            get { return GetString("NOMEFILE_ORIGINE"); }
            set { SetString("NOMEFILE_ORIGINE", value); }
        }

        /// <summary>
        /// Consente, in fase di protocollazione istanza o movimento da backoffice, di abilitare o meno la modifica della classifica. Se valorizzato a 1 allora abilita la modifica della classifica altrimenti se valorizzato a 0 o non valorizzato non consente la modifica della classifica.
        /// </summary>
        public string ModificaClassifica
        {
            get { return GetString("MODIFICA_CLASSIFICA"); }
            set { SetString("MODIFICA_CLASSIFICA", value); }
        }

        /// <summary>
        /// Consente, in fase di protocollazione istanza o movimento da backoffice, di abilitare o meno la modifica della classifica della fascicolazione. Se valorizzato a 1 allora abilita la modifica della classifica della fascicolazione altrimenti se valorizzato a 0 o non valorizzato non consente la modifica della classifica della fascicolazione.
        /// </summary>
        public string ModificaClassificafasc
        {
            get { return GetString("MODIFICA_CLASSIFICAFASC"); }
            set { SetString("MODIFICA_CLASSIFICAFASC", value); }
        }

        /// <summary>
        /// Indica i caratteri da eliminare nei nomi file in un eventuale passaggio di allegati al protocollo. Alcuni web service di protocollo restituiscono un'eccezione nel caso in cui nel nome file dei documenti passati siano presenti alcuni caratteri, come ad esempio Iride che non accetta i caratteri accentati e gli apici singoli nel nome file. I caratteri inseriti in questo parametro non devono essere divisi da nessun separatore ma inseriti in modo sequenziale (ad esempio àèìòù'&).
        /// </summary>
        public string ListaCaratteriDaEliminare
        {
            get { return GetString("LISTA_CARATTERI_DA_ELIMINARE"); }
            set { SetString("LISTA_CARATTERI_DA_ELIMINARE", value); }
        }

        /// <summary>
        /// Indicare la stringa con un valore di encoding valido con cui sarà passato il file xml al web service di protocollazione, se non presente non sarà passato alcun encoding con il problema che nei casi in cui venga creato un file segnatura.xml e lo stesso venga passato al sistema di protocollazione sotto formato stringa, si verifichino dei problemi con alcuni tipi di carattere come per esempio quelli accentati. Ad esempio GeProt ha la necessità di avere un encoding utf-8 per accettare caratteri accentati.
        /// </summary>
        public string Encoding
        {
            get { return GetString("ENCODING"); }
            set { SetString("ENCODING", value); }
        }

        /// <summary>
        /// Indica la modalita di trasmissione di default da passare al web service, un esempio di dati può essere: PER CONOSCENZA, FORMA DIRETTA.... Questo parametro è stato creato per consentire il passaggio dell'elemento riferito a -TRASMESSO PER- associato ai mittenti (protocollo in entrata) o ai destinatari (protocollo in uscita) dove i web service lo consentano (ad esempio INSIEL). Se valorizzato, viene proposto di default nella maschera di protocollazione nel dropdown riguardante la modalità di trasmissione dei mittenti / destinatari, e se lo stesso valore è presente nella tabella PROTOCOLLO_MODALITATRASMISSIONE. Viene inviato al web service anche quando la protocollazione è automatica, quindi proveniente da un frontoffice o dal backoffice se impostata la configurazione di protocollazione automatica. NB. se la tabella PROTOCOLLO_MEZZI è vuota il drop down mezzi, nella maschera di protocollazione, non viene visualizzato, ignorando quindi il valore di questo parametro.
        /// </summary>
        public string ModalitaTrasmissioneDefault
        {
            get { return GetString("MODALITA_TRASMISSIONE_DEFAULT"); }
            set { SetString("MODALITA_TRASMISSIONE_DEFAULT", value); }
        }

        /// <summary>
        /// Indica il mezzo di default da passare al web service. Questo parametro è stato creato per consentire il passaggio dell'elemento riferito a -A MEZZO DI- associato ai mittenti (protocollo in entrata) o ai destinatari (protocollo in uscita) dove i web service lo consentano (ad esempio IRIDE). Se valorizzato, viene proposto di default nella maschera di protocollazione, nel dropdown riguardante i mezzi dei mittenti / destinatari, e se lo stesso valore è presente nella tabella PROTOCOLLO_MEZZI. Viene inviato al web service anche quando la protocollazione è automatica, quindi proveniente da un frontoffice o dal backoffice se impostata la configurazione di protocollazione automatica. NB. se la tabella PROTOCOLLO_MEZZI è vuota il drop down mezzi, nella maschera di protocollazione, non viene visualizzato, ignorando quindi il valore di questo parametro.
        /// </summary>
        public string MezzoDefault
        {
            get { return GetString("MEZZO_DEFAULT"); }
            set { SetString("MEZZO_DEFAULT", value); }
        }

        /// <summary>
        /// Indicare il numero massimo di caratteri utilizzabili nel campo oggetto, se vuoto o non presente non sarà fatto alcun controllo. 
        /// In alcuni casi può capitare che l'oggetto abbia una limitazione di caratteri, ad esempio nel PROTOCOLLO HALLEY. 
        /// Nel caso in cui ci sia una limitazione in questo senso indicare il numero di caratteri utilizzabili, il sistema, durante la fase di protocollazione, 
        /// controllerà, se questo parametro è presente e valorizzato, se il numero di caratteri è inferiore o uguale alla cifra indicata in questo parametro, 
        /// se il risultato sarà negativo allora il sistema restituirà un'eccezione.
        /// </summary>
        public string NumCaratteriOggetto
        {
            get { return GetString("NUM_CARATTERI_OGGETTO"); }
            set { SetString("NUM_CARATTERI_OGGETTO", value); }
        }

        /// <summary>
        /// Il parametro permette di decidere se in fase di lettura protocollo devono essere visualizzati i campi 'Dati protocollo' che restituiscono un valore vuoto. Il parametro può assumere i valori 0 ed 1. 0 non mostrare valori vuoti,1
        /// mostra i valori vuoti.
        /// </summary>
        public string MostraDatiProtocolloNulli
        {
            get { return GetString("MOSTRA_DATI_PROTOCOLLO_NULLI"); }
            set { SetString("MOSTRA_DATI_PROTOCOLLO_NULLI", value); }
        }

        /// <summary>
        /// E' il codice della voce d'albero degli interventi che la funzionalita' "Pannello Gestione PEC" usa per recuperare la root per la creazione del controllo di selezione Intervento. Se non indicato o nullo vengono selezionate tutte le voci.
        /// </summary>
        public string CodAlberoRootProtocoll
        {
            get { return GetString("COD_ALBERO_ROOT_PROTOCOLL"); }
            set { SetString("COD_ALBERO_ROOT_PROTOCOLL", value); }
        }

        /// <summary>
        /// E' la classifica di default che viene recuperata nelle maschere di protocollazione quando non ne viene recuperata nessun'altra(ad esempio tramite la configurazione dell'albero degli interventi). Usato ad esempio per il Pannello Gestione PEC che non può recuperare la configurazione dall'intervento
        /// </summary>
        public string ClassificadefaultBo
        {
            get { return GetString("CLASSIFICADEFAULT_BO"); }
            set { SetString("CLASSIFICADEFAULT_BO", value); }
        }

        /// <summary>
        /// E' la classifica di default per la <b>FASCICOLAZIONE</b> che viene recuperata nelle maschere di protocollazione quando non ne viene recuperata nessun'altra(ad esempio tramite la configurazione dell'albero degli interventi). Usato ad esempio per il Pannello Gestione PEC che non può recuperare la configurazione dall'intervento
        /// </summary>
        public string ClassificaFascDefaultBo
        {
            get { return GetString("CLASSIFICA_FASC_DEFAULT_BO"); }
            set { SetString("CLASSIFICA_FASC_DEFAULT_BO", value); }
        }

        /// <summary>
        /// Valorizzare questo parametro nel momento il server che ospita VBG esca tramite un proxy, percui sia necessario specificare l'indirizzo per raggiungere il web service, come ad esempio succede sulla server farm di Linea Comune. In questo parametro va quindi specificato l'url del proxy. Precedentemente questo parametro era valorizzato dentro il binding del web service presente dentro il file bindings.config, quindi il sistema andrà a cercare il valore inserito dentro il binding nel caso in cui questo parametro sia assente o non valorizzato. IMPORTANTE: Questo parametro è utilizzabile solo con web service in cui lo stub sia stato creato con WCF altrimenti non è possibile indicare l'url del proxy
        /// </summary>
        public string ProxyAddress
        {
            get { return GetString("PROXY_ADDRESS"); }
            set { SetString("PROXY_ADDRESS", value); }
        }

        /// <summary>
        /// Solo se valorizzato a 1, il componente .net Protocollo Manager trasforma la stringa dell'oggetto tutta in maiuscolo, oggetto quindi che viene successivamente inviato al sistema di protocollo in maiuscolo anche se sulla maschera di protocollazione viene scritto in minuscolo
        /// </summary>
        public string OggettoUppercase
        {
            get { return GetString("OGGETTO_UPPERCASE"); }
            set { SetString("OGGETTO_UPPERCASE", value); }
        }

        /// <summary>
        /// Indica quale tipo di smistamento utilizzare nel caso in cui arrivino pratiche da online e la voce da selezionare di default nella maschera di protocollazione sia istanza che movimento alla voce TIPO SMISTAMENTO. Va inserito codice presente nella tabella PROTOCOLLO_SMISTAMENTI
        /// </summary>
        public string Tiposmistamentodefault
        {
            get { return GetString("TIPOSMISTAMENTODEFAULT"); }
            set { SetString("TIPOSMISTAMENTODEFAULT", value); }
        }

        /// <summary>
        /// Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, se una protocollazione è in arrivo allora il soggetto interessato sarà il mittente, se la protocollazione automatica sarà in partenza allora il soggetto sarà il destinatario. Può assumere i seguenti valori: non valorizzato o se valorizzato a 0 il sistema indicherà come mittente / destinatario il Richiedente e l'Azienda, se valorizzato a 1 verrà proposto solo il Richiedente,se valorizzato a 2 il sistema indicherà come Mittenti solo l'Azienda, se, in questo caso l'Azienda non è presente indicherà solamente il Richiedente.
        /// </summary>
        public string TipoMittDestAuto
        {
            get { return GetString("TIPO_MITTDEST_AUTO"); }
            set { SetString("TIPO_MITTDEST_AUTO", value); }
        }

        /// <summary>
        /// Indicare, con un valore intero, il numero massimo di caratteri, compresa l''estensione, che può contenere il nome file degli allegati che vengono inviati al protocollo. In caso di omissione di questo parametro il comportamento sarà quello indicato dal parametro NOMEFILE_ORIGINE di questa regola (PROTOCOLLO_ATTIVO), in caso di valorizzazione questo parametro avrà la precedenza. Ad esempio, se il parametro NOMEFILE_ORIGINE è impostato a 3 (quindi limitazione a 30 caratteri della descrizione), e questo parametro a 40, il nome file avrà la lunghezza di 40 caratteri
        /// </summary>
        public string NomeFileMaxLength
        {
            get { return GetString("NOMEFILE_MAXLENGTH"); }
            set { SetString("NOMEFILE_MAXLENGTH", value); }
        }

        /// <summary>
        /// Indicare, con un valore intero, il numero massimo di caratteri, compresa l''estensione, che può contenere il nome file degli allegati che vengono inviati al protocollo. In caso di omissione di questo parametro il comportamento sarà quello indicato dal parametro NOMEFILE_ORIGINE di questa regola (PROTOCOLLO_ATTIVO), in caso di valorizzazione questo parametro avrà la precedenza. Ad esempio, se il parametro NOMEFILE_ORIGINE è impostato a 3 (quindi limitazione a 30 caratteri della descrizione), e questo parametro a 40, il nome file avrà la lunghezza di 40 caratteri
        /// </summary>
        public string MetadatoRiepilogoDomanda
        {
            get { return GetString("METADATO_RIEPILOGODOMANDA"); }
            set { SetString("METADATO_RIEPILOGODOMANDA", value); }
        }
    }
}
