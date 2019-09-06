
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

        private static class Constants
        {
            public const string ValorizDataRicSpediz = "VALORIZZADATARIC_SPEDIZ";
            public const string Visualizzabottonestampa = "VISUALIZZABOTTONESTAMPA";
            public const string Visualizzabottoneleggi = "VISUALIZZABOTTONELEGGI";
            public const string VisualizzabottonestampaUrl = "VISUALIZZABOTTONESTAMPA_URL";
            public const string Operatore = "OPERATORE";
            public const string Codoperatorefo = "CODOPERATOREFO";
            public const string Aggiungianno = "AGGIUNGIANNO";
            public const string Noallegatifo = "NOALLEGATIFO";
            public const string Modificanumero = "MODIFICANUMERO";
            public const string GestisciFascicolazione = "GESTISCI_FASCICOLAZIONE";
            public const string Destinataricc = "DESTINATARICC";
            public const string Numdataprotmitt = "NUMDATAPROTMITT";
            public const string Tipoprotocollo = "TIPOPROTOCOLLO";
            public const string Tipodocumentodefault = "TIPODOCUMENTODEFAULT";
            public const string Flussodefault = "FLUSSODEFAULT";
            public const string Tipodocumentodefaultbo = "TIPODOCUMENTODEFAULTBO";
            public const string Visualizzauoraggruppate = "VISUALIZZAUORAGGRUPPATE";
            public const string Noallegati = "NOALLEGATI";
            public const string MovnoprecompilamittDest = "MOVNOPRECOMPILAMITT_DEST";
            public const string NomefileOrigine = "NOMEFILE_ORIGINE";
            public const string ModificaClassifica = "MODIFICA_CLASSIFICA";
            public const string ModificaClassificafasc = "MODIFICA_CLASSIFICAFASC";
            public const string ListaCaratteriDaEliminare = "LISTA_CARATTERI_DA_ELIMINARE";
            public const string Encoding = "ENCODING";
            public const string ModalitaTrasmissioneDefault = "MODALITA_TRASMISSIONE_DEFAULT";
            public const string MezzoDefault = "MEZZO_DEFAULT";
            public const string NumCaratteriOggetto = "NUM_CARATTERI_OGGETTO";
            public const string MostraDatiProtocolloNulli = "MOSTRA_DATI_PROTOCOLLO_NULLI";
            public const string CodAlberoRootProtocoll = "COD_ALBERO_ROOT_PROTOCOLL";
            public const string ClassificadefaultBo = "CLASSIFICADEFAULT_BO";
            public const string ClassificaFascDefaultBo = "CLASSIFICA_FASC_DEFAULT_BO";
            public const string ProxyAddress = "PROXY_ADDRESS";
            public const string OggettoUppercase = "OGGETTO_UPPERCASE";
            public const string Tiposmistamentodefault = "TIPOSMISTAMENTODEFAULT";
            public const string TipoMittDestAuto = "TIPO_MITTDEST_AUTO";
            public const string NomeFileMaxLength = "NOMEFILE_MAXLENGTH";
            public const string MetadatoRiepilogoDomanda = "METADATO_RIEPILOGODOMANDA";
            public const string CreaCopiaFile = "CREA_COPIA_FILE";
            public const string CreaCopiaDescrFile = "CREACOPIA_DESCR_FILE";
            public const string InviaEmlPecArrivo = "INVIA_EML_PEC_ARRIVO";
            public const string AllegatiObbligatori = "ALLEGATI_OBBLIGATORI";
            public const string DescrFileMaxLength = "DESCRFILE_MAXLENGTH";
            public const string EstraiEml = "ESTRAI_EML";
            public const string EscludiFilesDaEml = "ESCLUDI_FILES_DA_EML";
            public const string EstraiZip = "ESTRAI_ZIP";
            public const string ExtFileZip = "EXT_FILE_ZIP";
            public const string TipoMovRicevuta = "TIPOMOV_RICEVUTA";
            public const string AllegaXmlDomandaStc = "ALLEGA_XMLDOMANDASTC";
            public const string GestionePec = "GESTIONE_PEC";
        }

        public VerticalizzazioneProtocolloAttivo()
        {

        }

        public VerticalizzazioneProtocolloAttivo(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Se impostato a 1 aggiunge al numero protocollo anche l'anno. ( Es. 44/2005 )
        /// </summary>
        public string Aggiungianno => GetString(Constants.Aggiungianno);

        /// <summary>
        /// E’ il parametro che permette di stabilire se nella protocollazione automatica da FO occorre gestire gli allegati o meno, di default gli allegati sono gestiti. Es. 1=Allegati non gestiti, 0=Allegati gestiti
        /// </summary>
        public string Noallegatifo => GetString(Constants.Noallegatifo);

        /// <summary>
        /// Se 1 vengono eliminati gli 0 alla sinistra del numero del protocollo. Se 0 non accade nulla
        /// </summary>
        public string Modificanumero => GetString(Constants.Modificanumero);

        /// <summary>
        /// Se 1 viene gestita la fascicolazione se 0 no. Non tutti i protocolli sono integrati con la funzionalità di fascicolazione (Iride=si, DocsPa=si) 
        /// </summary>
        public string GestisciFascicolazione => GetString(Constants.GestisciFascicolazione);

        /// <summary>
        /// Se 1 allora è possibile selezionare i destinatari per conoscenza. Valore di default 0
        /// </summary>
        public string Destinataricc => GetString(Constants.Destinataricc);

        /// <summary>
        /// Se 1 allora in fase di richiesta di un protocollo è possibile inserire il numero e la data del protocollo mittente. Valore di default 0
        /// </summary>
        public string Numdataprotmitt => GetString(Constants.Numdataprotmitt);

        /// <summary>
        /// E' il tipo protocollo attivato (IRIDE,PINDARO,SIGEPRO,GEPROT... vedi l'enumerazione 
        /// </summary>
        public string Tipoprotocollo => GetString(Constants.Tipoprotocollo);

        /// <summary>
        /// E' la tipologia di documento da utilizzare per default per protocollare le istanze o i movimenti pervenuti dal front-office. Il codice dipende dal protocollo attivato. Es. 1=Lettera oppure LET=Lettera...a seconda del protocollo in esame
        /// </summary>
        public string Tipodocumentodefault => GetString(Constants.Tipodocumentodefault);

        /// <summary>
        /// E' il flusso di protocollo utilizzato per default per richieste provenienti da FO. Es. A=Arrivo,U=Uscita;I=Interno.
        /// </summary>
        public string Flussodefault => GetString(Constants.Flussodefault);

        /// <summary>
        /// E' la tipologia di documento da utilizzare per default per protocollare le istanze immesse da back-office tramite inserimento rapido,inserimento normale automaticamente protocollate e protocollazione massiva di movimenti. Il codice dipende dal protocollo attivato. Es. 1=Lettera oppure LET=Lettera...
        /// </summary>
        public string Tipodocumentodefaultbo => GetString(Constants.Tipodocumentodefaultbo);

        /// <summary>
        /// può assumere il valore di PROT_UO o PROT_RUOLO. Nei combo relativi ai mittenti e destinatari, qualora si tratti di amministrazioni con PROT_UO e/o PROT_RUOLO impostati,  non saranno visualizzate le amministrazioni di SIGePro ma il campo indicato nel parametro (PROT_UO o PROT_RUOLO) raggruppati per group by. Es. anzichè mostrare le amministrazioni "Ufficio Copia" e "Ufficio Istruttoria" sarà visualizzato "CASTER" che è il nome della PROT_UO associata ad entrambe le amministrazioni
        /// </summary>
        public string Visualizzauoraggruppate => GetString(Constants.Visualizzauoraggruppate);

        /// <summary>
        /// E’ il parametro che permette di stabilire se nella pagina di richiesta protocollo sia presente o meno la sezione per inviare gli allegati della pratica e dei movimenti.Inoltre permette di stabilire se gestire gli allegati o meno nel caso di protocollazione automatiche da BO (Inserimento normale, inserimento rapido e protocollazione massiva di movimenti.  Es. 1=Sezione non presente, 0=Sezione presente
        /// </summary>
        public string Noallegati => GetString(Constants.Noallegati);

        /// <summary>
        /// Se 1 allora nella pagina di richiesta protocollo da movimento non verranno precompilati mittenti e/o destinatari
        /// </summary>
        public string MovnoprecompilamittDest => GetString(Constants.MovnoprecompilamittDest);

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
        public string NomefileOrigine => GetString(Constants.NomefileOrigine);

        /// <summary>
        /// Consente, in fase di protocollazione istanza o movimento da backoffice, di abilitare o meno la modifica della classifica. Se valorizzato a 1 allora abilita la modifica della classifica altrimenti se valorizzato a 0 o non valorizzato non consente la modifica della classifica.
        /// </summary>
        public string ModificaClassifica => GetString(Constants.ModificaClassifica);

        /// <summary>
        /// Consente, in fase di protocollazione istanza o movimento da backoffice, di abilitare o meno la modifica della classifica della fascicolazione. Se valorizzato a 1 allora abilita la modifica della classifica della fascicolazione altrimenti se valorizzato a 0 o non valorizzato non consente la modifica della classifica della fascicolazione.
        /// </summary>
        public string ModificaClassificafasc => GetString(Constants.ModificaClassificafasc);

        /// <summary>
        /// Indica i caratteri da eliminare nei nomi file in un eventuale passaggio di allegati al protocollo. Alcuni web service di protocollo restituiscono un'eccezione nel caso in cui nel nome file dei documenti passati siano presenti alcuni caratteri, come ad esempio Iride che non accetta i caratteri accentati e gli apici singoli nel nome file. I caratteri inseriti in questo parametro non devono essere divisi da nessun separatore ma inseriti in modo sequenziale (ad esempio àèìòù'&).
        /// </summary>
        public string ListaCaratteriDaEliminare => GetString(Constants.ListaCaratteriDaEliminare);

        /// <summary>
        /// Indicare la stringa con un valore di encoding valido con cui sarà passato il file xml al web service di protocollazione, se non presente non sarà passato alcun encoding con il problema che nei casi in cui venga creato un file segnatura.xml e lo stesso venga passato al sistema di protocollazione sotto formato stringa, si verifichino dei problemi con alcuni tipi di carattere come per esempio quelli accentati. Ad esempio GeProt ha la necessità di avere un encoding utf-8 per accettare caratteri accentati.
        /// </summary>
        public string Encoding => GetString(Constants.Encoding);

        /// <summary>
        /// Indica la modalita di trasmissione di default da passare al web service, un esempio di dati può essere: PER CONOSCENZA, FORMA DIRETTA.... Questo parametro è stato creato per consentire il passaggio dell'elemento riferito a -TRASMESSO PER- associato ai mittenti (protocollo in entrata) o ai destinatari (protocollo in uscita) dove i web service lo consentano (ad esempio INSIEL). Se valorizzato, viene proposto di default nella maschera di protocollazione nel dropdown riguardante la modalità di trasmissione dei mittenti / destinatari, e se lo stesso valore è presente nella tabella PROTOCOLLO_MODALITATRASMISSIONE. Viene inviato al web service anche quando la protocollazione è automatica, quindi proveniente da un frontoffice o dal backoffice se impostata la configurazione di protocollazione automatica. NB. se la tabella PROTOCOLLO_MEZZI è vuota il drop down mezzi, nella maschera di protocollazione, non viene visualizzato, ignorando quindi il valore di questo parametro.
        /// </summary>
        public string ModalitaTrasmissioneDefault => GetString(Constants.ModalitaTrasmissioneDefault);

        /// <summary>
        /// Indica il mezzo di default da passare al web service. Questo parametro è stato creato per consentire il passaggio dell'elemento riferito a -A MEZZO DI- associato ai mittenti (protocollo in entrata) o ai destinatari (protocollo in uscita) dove i web service lo consentano (ad esempio IRIDE). Se valorizzato, viene proposto di default nella maschera di protocollazione, nel dropdown riguardante i mezzi dei mittenti / destinatari, e se lo stesso valore è presente nella tabella PROTOCOLLO_MEZZI. Viene inviato al web service anche quando la protocollazione è automatica, quindi proveniente da un frontoffice o dal backoffice se impostata la configurazione di protocollazione automatica. NB. se la tabella PROTOCOLLO_MEZZI è vuota il drop down mezzi, nella maschera di protocollazione, non viene visualizzato, ignorando quindi il valore di questo parametro.
        /// </summary>
        public string MezzoDefault => GetString(Constants.MezzoDefault);

        /// <summary>
        /// Indicare il numero massimo di caratteri utilizzabili nel campo oggetto, se vuoto o non presente non sarà fatto alcun controllo. 
        /// In alcuni casi può capitare che l'oggetto abbia una limitazione di caratteri, ad esempio nel PROTOCOLLO HALLEY. 
        /// Nel caso in cui ci sia una limitazione in questo senso indicare il numero di caratteri utilizzabili, il sistema, durante la fase di protocollazione, 
        /// controllerà, se questo parametro è presente e valorizzato, se il numero di caratteri è inferiore o uguale alla cifra indicata in questo parametro, 
        /// se il risultato sarà negativo allora il sistema restituirà un'eccezione.
        /// </summary>
        public string NumCaratteriOggetto => GetString(Constants.NumCaratteriOggetto);

        /// <summary>
        /// Il parametro permette di decidere se in fase di lettura protocollo devono essere visualizzati i campi 'Dati protocollo' che restituiscono un valore vuoto. Il parametro può assumere i valori 0 ed 1. 0 non mostrare valori vuoti,1
        /// mostra i valori vuoti.
        /// </summary>
        public string MostraDatiProtocolloNulli => GetString(Constants.MostraDatiProtocolloNulli);

        /// <summary>
        /// E' il codice della voce d'albero degli interventi che la funzionalita' "Pannello Gestione PEC" usa per recuperare la root per la creazione del controllo di selezione Intervento. Se non indicato o nullo vengono selezionate tutte le voci.
        /// </summary>
        public string CodAlberoRootProtocoll => GetString(Constants.CodAlberoRootProtocoll);

        /// <summary>
        /// E' la classifica di default che viene recuperata nelle maschere di protocollazione quando non ne viene recuperata nessun'altra(ad esempio tramite la configurazione dell'albero degli interventi). Usato ad esempio per il Pannello Gestione PEC che non può recuperare la configurazione dall'intervento
        /// </summary>
        public string ClassificadefaultBo => GetString(Constants.ClassificadefaultBo);

        /// <summary>
        /// E' la classifica di default per la <b>FASCICOLAZIONE</b> che viene recuperata nelle maschere di protocollazione quando non ne viene recuperata nessun'altra(ad esempio tramite la configurazione dell'albero degli interventi). Usato ad esempio per il Pannello Gestione PEC che non può recuperare la configurazione dall'intervento
        /// </summary>
        public string ClassificaFascDefaultBo => GetString(Constants.ClassificaFascDefaultBo);

        /// <summary>
        /// Valorizzare questo parametro nel momento il server che ospita VBG esca tramite un proxy, percui sia necessario specificare l'indirizzo per raggiungere il web service, come ad esempio succede sulla server farm di Linea Comune. In questo parametro va quindi specificato l'url del proxy. Precedentemente questo parametro era valorizzato dentro il binding del web service presente dentro il file bindings.config, quindi il sistema andrà a cercare il valore inserito dentro il binding nel caso in cui questo parametro sia assente o non valorizzato. IMPORTANTE: Questo parametro è utilizzabile solo con web service in cui lo stub sia stato creato con WCF altrimenti non è possibile indicare l'url del proxy
        /// </summary>
        public string ProxyAddress => GetString(Constants.ProxyAddress);

        /// <summary>
        /// Solo se valorizzato a 1, il componente .net Protocollo Manager trasforma la stringa dell'oggetto tutta in maiuscolo, oggetto quindi che viene successivamente inviato al sistema di protocollo in maiuscolo anche se sulla maschera di protocollazione viene scritto in minuscolo
        /// </summary>
        public string OggettoUppercase => GetString(Constants.OggettoUppercase);

        /// <summary>
        /// Indica quale tipo di smistamento utilizzare nel caso in cui arrivino pratiche da online e la voce da selezionare di default nella maschera di protocollazione sia istanza che movimento alla voce TIPO SMISTAMENTO. Va inserito codice presente nella tabella PROTOCOLLO_SMISTAMENTI
        /// </summary>
        public string Tiposmistamentodefault => GetString(Constants.Tiposmistamentodefault);

        /// <summary>
        /// Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, se una protocollazione è in arrivo allora il soggetto interessato sarà il mittente, se la protocollazione automatica sarà in partenza allora il soggetto sarà il destinatario. Può assumere i seguenti valori: non valorizzato o se valorizzato a 0 il sistema indicherà come mittente / destinatario il Richiedente e l'Azienda, se valorizzato a 1 verrà proposto solo il Richiedente,se valorizzato a 2 il sistema indicherà come Mittenti solo l'Azienda, se, in questo caso l'Azienda non è presente indicherà solamente il Richiedente.
        /// </summary>
        public string TipoMittDestAuto => GetString(Constants.TipoMittDestAuto);

        /// <summary>
        /// Indicare, con un valore intero, il numero massimo di caratteri, compresa l''estensione, che può contenere il nome file degli allegati che vengono inviati al protocollo. In caso di omissione di questo parametro il comportamento sarà quello indicato dal parametro NOMEFILE_ORIGINE di questa regola (PROTOCOLLO_ATTIVO), in caso di valorizzazione questo parametro avrà la precedenza. Ad esempio, se il parametro NOMEFILE_ORIGINE è impostato a 3 (quindi limitazione a 30 caratteri della descrizione), e questo parametro a 40, il nome file avrà la lunghezza di 40 caratteri
        /// </summary>
        public string NomeFileMaxLength => GetString(Constants.NomeFileMaxLength);

        /// <summary>
        /// Indicare, con un valore intero, il numero massimo di caratteri, compresa l''estensione, che può contenere il nome file degli allegati che vengono inviati al protocollo. In caso di omissione di questo parametro il comportamento sarà quello indicato dal parametro NOMEFILE_ORIGINE di questa regola (PROTOCOLLO_ATTIVO), in caso di valorizzazione questo parametro avrà la precedenza. Ad esempio, se il parametro NOMEFILE_ORIGINE è impostato a 3 (quindi limitazione a 30 caratteri della descrizione), e questo parametro a 40, il nome file avrà la lunghezza di 40 caratteri
        /// </summary>
        public string MetadatoRiepilogoDomanda => GetString(Constants.MetadatoRiepilogoDomanda);

        /// <summary>
        /// In fase di protocollazione, indica al sistema se deve controllare che due file abbiano lo stesso nome, in quel caso modifica il nome del secondo file inserendo il valore '[indice]' (per indice si intende il valore 1, 2, 3, 4.... in base alle copie), ad esempio prova.txt diventerebbe prova [1].txt. In questo modo si evitano dei problemi nel caso in cui qualche sistema di protocollazione non accetti nomi uguali (ad esempio GEPROT), tuttavia il controllo potrebbe leggermente rallentare il procedimento di protocollazione. VALORE = 1, fai la verifica e in caso di omonimia crea una copia rinominando il secondo file, ALTRI VALORI = non fa la verifica. ATTENZIONE: questo parametro potrebbe entrare in conflitto con il parametro NOMEFILE_MAXLENGTH in quanto, quando si crea una copia inevitabilmente il numero dei caratteri del nome file aumenta, anche se di poco, quindi potenzialmente si potrebbe superare la lunghezza indicata in quel parametro, in caso di errore tenere presente questa indicazione.
        /// </summary>
        public string CreaCopiaFile => GetString(Constants.CreaCopiaFile);

        /// <summary>
        /// Valorizzare questo parametro a 1 nel momento in cui si renda necessario fare in modo che le descrizioni dei file, recuperate dal campo Descrizione, dei documenti dell'istanza, 
        /// non debbano essere uguali. Qualche protocollo (i DocArea di ADS e MAGGIOLI ad esempio), non accettano doppioni sulle descrizioni dei file.
        /// </summary>
        public string CreaCopiaDescrFile => GetString(Constants.CreaCopiaDescrFile);
        
        /// <summary>
        //Parametro che sta ad indicare se la protocollazione automatica in arrivo deve inviare l''allegato .eml relativo alla pec inviata dal sistema di frontoffice (ad esempio SUAPER) invece degli allegati dell''istanza
        /// </summary>
        public string InviaEmlPecArrivo => GetString(Constants.InviaEmlPecArrivo);
        
        /// <summary>
        //  Valorizzare a 1 se il protocollo richiede necessariamente almeno un allegato, in caso di assenza sarà visualizzato un errore e non sarà possibile la protocollazione fino a quando non sarà allegato almento un documento. Tutti i valori diversi da 1 (o assenza del parametro) renderanno facoltativo l''inserimento di allegati.
        /// </summary>
        public string AllegatiObbligatori => GetString(Constants.AllegatiObbligatori);
        
        /// <summary>
        //  Indicare, con un valore intero, il numero massimo di caratteri della DESCRIZIONE del file degli allegati che vengono inviati al protocollo. Per descrizione del file si intende il contenuto presente dentro la gestione dei documenti dell''istanza / movimenti alla voce Descrizione. In caso di omissione non sarà applicato alcun controllo.
        /// </summary>
        public string DescrFileMaxLength => GetString(Constants.DescrFileMaxLength);

        /// <summary>
        //  Indica come deve essere gestito l''indirizzo pec delle anagrafiche; se non valorizzato, o valorizzato a 0 questo parametro, allora la logica sarà la stessa di prima, ossia le anagrafiche relative a Richiedente e Titolare Legale avranno come indirizzo PEC il Domicilio Elettronico della pratica, se non presente sarà valorizzato l''indirizzo PEC dell''anagrafica stessa. Se valorizzato a 1 allora verranno recuperati solo gli indirizzi PEC delle anagrafiche, se valorizzato a 2 allora la logica sarà la stessa indicata con il valore 1, con la differenza che, se non presenti, la logica tornerà ad essere la stessa con il valore 0; se valorizzato a 3 invece, la logica sarà la stessa del valore 2, con la differenza che la logica varrà solo per il titolare legale, qualsiasi altra anagrafica infatti, se vuoto l''indirizzo PEC, lo stesso rimarrà vuoto.
        /// </summary>
        public string GestionePec => String.IsNullOrEmpty(GetString(Constants.GestionePec)) ? "0" : GetString(Constants.GestionePec);

        /// <summary>
        //  Questo parametro, se valorizzato a 1, consente, in fase di lettura di un protocollo (quindi è valildo solo per i protocolli che espongono tale funzionalità), di estrarre un eventuale file .eml, visualizzando quindi gli allegati e il testo della mail sotto forma di testo e/o html.
        /// </summary>
        public string EstraiEml => GetString(Constants.EstraiEml);

        /// <summary>
        //  In questo parametro vanno indicati i nomi di files che non devono essere visualizzati in fase di lettura protocollo estraendo un file EML. La stringa inserita all''interno del parametro sarà trattata come un array, è quindi possibile inserire più nomi file, che dovranno essere separati dal carattere pipe ("|"). I nomi non saranno case sensitive. Tale parametro è strettamente collegato con l''altro parametro ESTRAI_EML, che deve essere impostato a 1 per rendere valida la funzionalità di questo parametro, deve inoltre essere possibile leggere i dati da un protocollo, quindi anche il parametro VISUALIZZABOTTONELEGGI deve essere impostato a 1.
        /// </summary>
        public string EscludiFilesDaEml => GetString(Constants.EscludiFilesDaEml);

        /// <summary>
        //  Questo parametro, se valorizzato a 1, consente, in fase di lettura di un protocollo (quindi è valildo solo per i protocolli che espongono tale funzionalità), di estrarre un eventuale file zippato (zip, rar, 7z), visualizzando quindi i file all''interno di esso e non più il file zippato.
        /// </summary>
        public string EstraiZip => GetString(Constants.EstraiZip);

        /// <summary>
        //  Indicare in questo parametro la lista di estensioni (comprese di punto e separate da virgola) dei file compressi che devono essere scompattati in fase di lettura protocollo, ove previsto. Questo parametro viene va di pari passo con ESTRAI_ZIP, che deve essere valorizzato a 1. In pratica qui vanno indicate le estensioni dei file zip che si vuole estrarre in fase di lettura protocollo.
        /// </summary>
        public string ExtFileZip => GetString(Constants.ExtFileZip);

        /// <summary>
        //  Indicare in questo parametro il tipo movimento relativo alla ricevuta automatica che genera un protocollo in partenza, in caso di utilizzo di tale sistema, ossia quindi della protocollazione automatica in partenza della ricevuta generata da un movimento che viene eseguito all'avvio dell'istanza, queseto parametro è fondamentale per verificare se la protocollazione automatica in arrivo è andata a buon fine, in caso negativo infatti va bloccata anche la protocollazione automatica in partenza.
        /// </summary>
        public string TipoMovRicevuta => GetString(Constants.TipoMovRicevuta);

        /// <summary>
        //  Se impostato a 1 consentirà di allegare il file XML relativo alla domanda STC per le pratiche in arrivo. Ovviamente vale solo per le istanze in arrivo e dove è presente tale file.
        /// </summary>
        public string AllegaXmlDomandaStc => GetString(Constants.TipoMovRicevuta);

        /// <summary>
        /// Valori: 0 (default) e 1. 
        /// Se impostato a 1 valorizza la proprietà ValorizzaDataRicezioneSpedizione del protocollo di base per poterla poi utilizzare nelle implementazioni dei protocolli. 
        /// Ad esempio con PROTOCOLLO_INSIEL ( versione 3 ) viene utilizzato per passare la data dell'istanza o la data del movimento o la data di ricezione PEC al protocollo tramite 
        /// il parametro dataRicezioneSpedizione del WS del fornitore del protocollo
        /// </summary>
        public bool ValorizDataRicSpediz => GetString(Constants.ValorizDataRicSpediz) == "1";

        /// <summary>
        /// Se 1 nella pagina di dettaglio della pratica compare il tasto Stampa qualora il protocollo sia stato già realizzato
        /// </summary>
        public string Visualizzabottonestampa => GetString(Constants.Visualizzabottonestampa);

        /// <summary>
        /// Se 1 nella pagina di dettaglio della pratica compare il tasto Leggi qualora il protocollo sia stato già realizzato
        /// </summary>
        public string Visualizzabottoneleggi => GetString(Constants.Visualizzabottoneleggi);

        /// <summary>
        /// Url della pagina da utilizzare per stampare il numero e data di protocollo. Se non impostato utilizza l'indirizzo di default cl_ProtocolloStampa.asp
        /// </summary>
        public string VisualizzabottonestampaUrl => GetString(Constants.VisualizzabottonestampaUrl);

        /// <summary>
        /// Specifica l'operatore da utilizzare per l'inserimento di un protocollo. E' possibilie utilizzare il segnaposto $CODICEOPERATORE$ che a runtime verrà sostituito con il codice utente correntemente loggato oppure il segnaposto $SIGEPRO_USERID$ che verrà sostituito con la user id presente nell'anagrafica degli operatori
        /// </summary>
        public string Operatore {
            get { return GetString(Constants.Operatore); }
            set { SetString(Constants.Operatore, value); }
        }

        /// <summary>
        /// Questo parametro va utilizzato solamente nel caso in cui nel parametro OPERATORE sia specificato un 
        /// valore segnalibro, ossia un valore che inizia e termina con il carattere -$-, solo in questo caso questo parametro 
        /// sarà preso in considerazione e il suo valore sarà cercato tra i codici operatori di SIGEPRO 
        /// (quindi va passato necessariamente un valore numerico) che, una volta trovato, andrà a valorizzare il dato da passare 
        /// al web service tramite le regole descritte appunto nel parametro OPERATORE che indicano come individuarne il 
        /// campo esatto tramite il segnalibro
        /// </summary>
        public string Codoperatorefo => GetString(Constants.Codoperatorefo);

    }
}
