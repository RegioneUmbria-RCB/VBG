package it.gruppoinit.pal.gp.core.constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;

/**
 * Classe delle costanti
 * 
 * @author fabrizioc
 * 
 */
public class WebConstants {

    public static final String MAPPA_UTENTI_COLLEGATI_REQ_ATTR = "_mappaUtentiCollegati_";
    public static final String LOG_CANCELLAZIONE_FILE_REQ_ATTR = "_LOG_CANCELLAZIONE_FILE_";
    private static final Logger log = LoggerFactory.getLogger(WebConstants.class);
    /**
     * redirect:
     */
    public static final String SPRING_REDIRECT_TO = "redirect:";
    /**
     * nome della cartella contenente i file di configurazione (creata nel classpath dell'applicazione):
     * <b>gruppoinit-apps-config/</b><br />
     * (Attenzione! se si modifica il valore, modificare anche applicationContext.xml)
     */
    public static final String CONFIG_FILES_FOLDER = "";
    /**
     * nome del file di configurazione dell'applicazione: gp-backoffice-deploy.properties<br />
     * (Attenzione! se si modifica il valore, modificare anche applicationContext.xml)
     */
    public static final String DEPLOY_PROPS = "deploy.properties";
    /**
     * nome del file di configurazione per il db dell'applicazione: gp-backoffice-db.properties
     */
    public static final String DB_PROPS = "db.properties";
    /**
     * Costante che identifica se il link della tabella CL_MENU punta ad una funzione di Backoffice ASP
     */
    public static final String TIPO_ASP = "ASP";
    /**
     * Costante che identifica se il link della tabella CL_MENU punta ad una funzione di Backoffice NET
     */
    public static final String TIPO_NET = "NET";
    /**
     * Costante che identifica se il link della tabella CL_MENU punta ad una funzione di Backoffice JAVA
     */
    public static final String TIPO_JAVA = "JAVA";
    /**
     * Costante che identifica nelle authentication_info la proprietà software attivi ossia la lista dei moduli software
     * configurati per l'ente
     */
    public static final String AUTHENTICATION_INFO_SOFTWARE_ATTIVI = "AuthenticationInfoSoftwareAttivi";
    /**
     * Costante che identifica nelle authentication_info la proprietà software attivi per il frontoffice ossia la lista
     * dei moduli software configurati per l'ente visibili nella sezione di frontoffice
     */
    public static final String AUTHENTICATION_INFO_SOFTWARE_ATTIVI_FO = "AuthenticationInfoSoftwareAttiviFo";
    /**
     * Chiave della cache per contenere l'oggetto della classificazione ateco
     */
    public static final String CACHE_CLASSIFICAZIONE_ATECO_KEY = "CACHE_CLASSIFICAZIONE_ATECO_KEY";
    /**
     * chiave dell'elemento che contiene la lista della classificazione ateco nella cache
     */
    public static final Serializable CACHE_CLASSIFICAZIONE_ATECO_LIST = "CACHE_CLASSIFICAZIONE_ATECO_LIST";
    /**
     * Nome della chiave di Cache relativa ai menu degli operatori
     */
    public static final String CACHE_MENU_KEY = "CACHE_MENU_KEY";
    /**
     * 
     * Nome della chiave di Cache relativa ai nodi stc configurati
     */
    public static final String CACHE_NODI_STC_KEY = "CACHE_NODI_STC_KEY";
    /**
     * Nome della chiave di Cache relativa agli oggetti Disabilitati
     */
    public static final String CACHE_OGGETTIDISABILITATI_KEY = "CACHE_OGGETTIDISABILITATI_KEY";
    /**
     * Nome della chiave di Cache relativa ad albero procedimenti
     */
    public static final String CACHE_ALBEROPROC_KEY = "CACHE_ALBEROPROC_KEY";
    /**
     * Chiave della chiave di Cache che contiene relativa a CONTENTTYPES
     */
    public static final String CACHE_MIME_TYPES_KEY = "CACHE_MIME_TYPES_KEY";
    /**
     * Chiave dell'elemento che contiene la mappa degli elementi presenti nella tabella CONTENTYPES
     */
    public static final Serializable CACHE_MIME_TYPES_KEY_ELEMENTS = "CACHE_MIME_TYPES_KEY_ELEMENTS";
    /**
     * Nome della chiave di Cache relativa ai ClassValidator di hibernate
     */
    public static final String CACHE_HIBERNATE_VALIDATORS = "CACHE_HIBERNATE_VALIDATORS";
    /**
     * costante principale di gp-backoffice
     */
    public static final String IDCOMUNE = "idcomune";
    /**
     * costante per recuperare il vero idcomune registrato con un alias sul servizio ws token
     */
    public static final String IDCOMUNE_ALIAS = "idcomunealias";
    /**
     * 
     */
    public static final String STATUS_MSG_PARAM_NAME = "status_msg";
    /**
     * Nome della proprietà Contesto dei token INFO
     */
    public static final String TOKEN_INFO_CONTESTO = "contesto";
    /**
     * Contesto tipo Operatori
     */
    public static final String TOKEN_INFO_CONTESTO_OPE = "OPE";
    /**
     * Contesto tipo Applciazioone
     */
    public static final String TOKEN_INFO_CONTESTO_APP = "APP";
    /**
     * Contesto tipo Utenti
     */
    public static final String TOKEN_INFO_CONTESTO_UTE = "UTE";
    /**
     * property del servizio Security che contiene l'utente autenticato
     */
    public static final String USER_ID = "userid";
    /**
     * 
     */
    public static final String IS_POST_ACTION = "isPostAction_";
    /**
     * costante che identifica il nome della proprietà connection_string (utilizzata come chiave della mappa delle
     * sessionFactory di hibernate)
     */
    public static final String HIBERNATE_CONN_URL = "hibernate.connection.url";
    /**
     * 
     */
    public static final String HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";
    /**
     * 
     */
    public static final String HIBERNATE_SESSION_FACTORY_KEY = "HIBERNATE_SESSION_FACTORY_KEY";
    /**
     * valore LEGACY della proprietà di connessione PRODUCT_STYLE
     */
    public static final String PRODUCT_STYLE_LEGACY = "LEGACY";
    /**
     * nome dell'attributo di sessione che memorizza la descrizione del comune
     */
    public static final String COMUNE_DESC = "comune";
    /**
     * nome dell'attributo di sessione che memorizza la versione commerciale del prodotto verione.versioneProdotto
     */
    public static final String PRODUCT_VERSION = "app_version";
    /**
     * nome dell'attributo di sessione che memorizza la build di release del prodotto verione.versione
     */
    public static final String PRODUCT_BUILD = "db_version";
    /**
     * Costante che specifica un pattern per i valori numeri
     */
    public static final String NUMBER_FORMAT_PATTERN = "###,##0.00";
    public static final String NUMBER_FORMAT_PATTERN_TRE_DECIMALI = "###,###0.000";
    public static final String NUMBER_FORMAT_PATTERN_CINQUE_DECIMALI = "###.######";
    /**
     * Costanti NO e SI
     */
    public static final String NO = "NO";
    public static final String SI = "SI";
    /**
     * Costante che specifica un pattern di data compatta da utilizzare per i binder ad esempio
     * <code>"dd/MM/yyyy"</code>
     */
    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    /**
     * Costante che specifica un pattern di data al dettaglio di ore/minuti da utilizzare per i binder ad esempio
     * <code>"dd/MM/yyyy - HH:mm"</code>
     */
    public static final String DATE_WITH_TIME_FORMAT_PATTERN = "dd/MM/yyyy - HH:mm";
    /**
     * Costante che specifica un pattern di data al dettaglio di ore/minuti/secondi da utilizzare per i binder ad
     * esempio <code>"dd/MM/yyyy - HH:mm:ss"</code>
     */
    public static final String DATE_WITH_TIME_SEC_FORMAT_PATTERN = "dd/MM/yyyy - HH:mm:ss";
    /**
     * Pattern per la validazione degli indirizzi di posta elettronica
     */
    public static final String EMAIL_ADDRESS_VALIDATION_PATTERN = "^$|^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-]{2,})+\\.)+([a-zA-Z0-9]{2,})+$";
    /**
     * Variabile per il controllo se siamo nella jsp form in modalità Create
     */
    public static final String DISPATCH_CREATE = "create";
    /**
     * Variabile per il controllo se siamo nella jsp form in modalità View
     */
    public static final String DISPATCH_VIEW = "view";
    /**
     * Costante che identifica il nome del parametro per la url di ritorno ad una funzionalità JAVA
     */
    public static final String RETURNTO = "ReturnTo";
    /**
     * Costante che identifica il nome del parametro per la url utilizzata come prima chiamata all'applicativo
     * Utilizzata in caso di autenticazione esterna per eseguire una redirect corretta
     */
    public static final String URL_FIRST_REQUEST = "url_first_request";
    /**
     * Variabile per la corretta visualizzazione nelle pagine jsp delle sezioni legate ad una precisa azione es:
     * dispatch=create per l'inserimento dispatch=view per la modifica
     */
    public static final String DISPATCH = "dispatch";
    /**
     * Variabili per la gestione dell'interoperabilità
     */
    public static final String TOKEN = "Token";
    public static final String SOFTWARE = "software";
    public static final String COOKIEURL_SESSIONE_SCADUTA = "_COOKIEURL_SESSIONE_SCADUTA_";
    public static final String SOFTWARE_TT = "TT";
    public static final String SOFTWARE_CE = "CE";
    public static final String SOFTWARE_SU = "SU";
    public static final String SOFTWARE_PR = "PR";
    public static final String OGGETTI_METADATI_TIPODOCUMENTO = "TIPO_DOCUMENTO";
    public static final String OGGETTI_FILE_LOCKED_BY = "#INIT-FILE-LOCKED-BY#";
    public static final String OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE = "FILE_BLOCCATO_MODEL_ATTRIBUTE_";
    public static final String OGGETTI_FILE_BLOCCATO_DA_UTENTE_LOGGATO_MODEL_ATTRIBUTE = "FILE_BLOCCATO_DA_UTENTE_LOGGATO_MODEL_ATTRIBUTE_";
    public static final String OGGETTI_FILE_UID = "UID";
    public static final String VERTICALIZZAZIONE_AREARISERVATA_REDIRECT = "AREARISERVATA_REDIRECT";
    /**
     * lo stato di default dell'istanza aperta
     */
    public static final String STATO_ISTANZA_APERTA_DEFAULT = "AT";
    /**
     * Variabili per la gestione degli interventi Ateco
     */
    public static final String ATECO_CODICE_ROOT = "0";
    /**
     * Variabili per la gestione dei bandi
     */
    public static final String BANDI_TIPOCALCOLO_ELEMENTO = "ELEMENTO";
    public static final String BANDI_TIPOCALCOLO_VALORE = "VALORE";
    public static final String BANDI_TIPOCALCOLO_MERCATI = "MERCATI";
    public static final String BANDI_TIPOINPUT_VALORE = "VALORE";
    public static final String BANDI_TIPOINPUT_QUERY = "QUERY";
    public static final String BANDI_TIPOINPUT_MERCATI = "MERCATI";
    /**
     * Variabili per la gestione delle RegistrazioniInOut
     */
    public static final String REGISTRAZIONIINOUT_TIPO_E = "E";
    public static final String REGISTRAZIONIINOUT_TIPO_U = "U";
    public static final String REGISTRAZIONIINOUT_RICHIEDENTE_ANAGRAFE = "anagrafe";
    public static final String REGISTRAZIONIINOUT_RICHIEDENTE_AMMINISTRAZIONI = "amministrazioni";
    /**
     * Variabili per la gestione dei fogli di stile
     */
    public static final String CSS_DEFAULT_STYLE = "standard.css";
    public static final String CSS_USER_PREF_STYLE = "StileBO";
    /**
     * Variabili per la gestione dell'history back
     */
    public static final String HISTORY_BUFFER = "HistoryBuffer";
    public static final String GOTO = "GoTo";
    /**
     * Variabili che indicano il numero di giorni di un anno
     */
    public static final int NUMERO_GIORNI_ANNO = 365;
    public static final int NUMERO_GIORNI_ANNO_BISESTILE = 366;
    public static final String CANONE_MERCATO_FISSO = "PE";
    public static final String CANONE_MERCATO_CALCOLATO = "TE";
    /**
     * Variabili usate nella configurazione dei conti di un mercato o di un posteggio indicano se la riga di conto è
     * applicabile per le operazioni contabili di SPUNTISTI, CONCESSIONARI o TUTTI
     */
    public static final String MERCATO_CONTESTO_CONCESSIONARI = "Concessionari";
    public static final String MERCATO_CONTESTO_SPUNTISTI = "Spuntisti";
    public static final String MERCATO_CONTESTO_TUTTI = "Tutti";
    /**
     * Variabili usate per gestire la configurazione utente nella visualizzazione delle informazioni di un posteggio
     */
    public static final String VISUALIZZA_MERCEOLOGIA_MERCATI = "VISMERCEOLOGIEMERCATI";
    public static final String VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI = "VISALTREINFOMERCATI";
    public static final String VISUALIZZA_FILTRO_POSTEGGI = "VISFILTROPOSTEGGI";
    /**
     * Lista di voci ammesse per i contesti dei conti di mercato e/o posteggio serve a creare le combo box nella view
     * senza cerare la tabella
     */
    public static final List<String> MERCATI_CONTESTO = new ArrayList<String>();
    static {
	MERCATI_CONTESTO.add(0, MERCATO_CONTESTO_TUTTI);
	MERCATI_CONTESTO.add(1, MERCATO_CONTESTO_CONCESSIONARI);
	MERCATI_CONTESTO.add(2, MERCATO_CONTESTO_SPUNTISTI);
    }
    /**
     * Configurazione utente per visualizzare informazioni aggiuntive nella pagina dell'elaborazione
     */
    public static final String CONF_UTENTE_ATTMSGELABORAZIONE = "ATTMSGELABORAZIONE";
    /**
     * Configurazione utente per visualizzare i file con estensione xml nelle pagine: documenti dell'istanza e allegati
     * dei movimenti
     */
    public static final String CONF_UTENTE_VISUALIZZA_FILE_XML = "VISUALIZZA_FILE_XML";
    /**
     * Variabili per la visualizzazione Utente dei tab dello scadenzario
     */
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE = "SCAD_VISUALIZZA_MOV_DA_EFFETTUARE";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE = "SCAD_VISUALIZZA_MOV_DA_VISIONARE";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI = "SCAD_VISUALIZZA_MOV_NON_NOTIFICATI";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO = "SCAD_VISUALIZZA_RICHIESTE_FO";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI = "SCAD_VISUALIZZA_EVENTI_NON_LETTI";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC = "SCAD_VISUALIZZA_ISTANZE_STC";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NON_IMPORTATE = "CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NO_IMPOR";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_SISTEMA = "SCAD_VISUALIZZA_EVENTI_SISTEMA";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE = "SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE";
    public static final String CONF_UTENTE_SCAD_VISUALIZZA_SCADENZARIO_ONERI = "SCAD_VISUALIZZA_SCADENZARIO_ONERI";
    /**
     * Variabili per per la configurazione utente, visualizzazione colonne nelle tabelle delle scandezario "Movimenti
     * effettuati","Movimenti da visionare" "Movimenti visionati","Nuove domanda da STC"
     */
    public static final String CONF_UTENTE_SCAD_COLONNA_STATO = "COLONNA STATO";
    public static final String CONF_UTENTE_SCAD_COLONNA_SOFTWARE = "COLONNA SOFTWARE";
    public static final String CONF_UTENTE_SCAD_COLONNA_INTERVENTO = "COLONNA INTERVENTO";
    public static final String CONF_UTENTE_SCAD_COLONNA_PROCEDURA = "COLONNA PROCEDURA";
    public static final String CONF_UTENTE_SCAD_COLONNA_POS_ARCHIVIO = "COLONNA POS. ARCHIVIO";
    public static final String CONF_UTENTE_SCAD_COLONNA_ENDOPROCEDIMENTO = "COLONNA PROCEDIMENTO";
    public static final String CONF_UTENTE_SCAD_COLONNA_DATA_ISTANZA = "COLONNA DATA PRESENTAZIONE PRATICA";
    public static final String CONF_UTENTE_SCAD_COLONNA_TERMINE_PROCEDIMENTO = "COLONNA TERMINE PROCEDIMENTO";
    public static final String CONF_UTENTE_SCAD_COLONNA_OPERATORE = "COLONNA OPERATORE";
    public static final String CONF_UTENTE_SCAD_COLONNA_ISTRUTTORE = "COLONNA ISTRUTTORE";
    public static final String CONF_UTENTE_SCAD_COLONNA_AMMINISTRAZIONE = "COLONNA AMMINISTRAZIONE";
    public static final String CONF_UTENTE_SCAD_COLONNA_MOVIMENTO_FATTO = "COLONNA MOVIMENTO FATTO";
    public static final String CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE = "COLONNA RICHIEDENTE";
    /**
     * Variabili per la gestione degli incassi
     */
    public static final String CONF_UTENTE_REG_IO_DATADISTINTA = "REG_IO_DATADISTINTA";
    /**
     * Parametro della configurazione utente che identifica la quale tab visualizzare nella scheda contabile
     */
    public static final String CONF_UTENTE_SCHEDACONTAB_TAB = "SCHEDACONTAB_TAB";
    /**
     * parametro della configurazione utente che identifica se visualizzare tutte le registrazioni o solamente quelle
     * chiuse
     */
    public static final String CONF_UTENTE_SCHEDACONTAB_CHECK = "SCHEDACONTAB_CHECK";
    /**
     * parametro della configurazione utente che identifica se visualizzare tutte le registrazioni o solamente quelle
     * chiuse
     */
    public static final String CONF_UTENTE_REGISTRAZIONI_SHOW_INFO = "REGISTRAZIONI_SHOW_INFO";
    /**
     * parametro della configurazione utente che memorizza i filtri dell'ultima ricerca fatta sulle rate non pagate
     */
    public static final String CONF_UTENTE_RICERCA_RATE_NON_PAGATE = "RATE_NON_PAGATE";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione altri dati nella form di un
     * inventarioprocedimento
     */
    public static final String CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI = "CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no le normative nella form di un
     * inventarioprocedimento
     */
    public static final String CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE = "INVENTARIO_PROC_NORMATIVE";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dati
     * autorizzazioni/concessioni nella form di ricerca delle istanze
     */
    public static final String CONF_UTENTE_SEARCH_DATI_CONC_AUT = "CONF_UTENTE_SEARCH_DATI_CONC_AUT";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dati localizzazioni nella
     * form di ricerca delle istanze
     */
    public static final String CONF_UTENTE_SEARCH_DATI_LOCALIZZ = "CONF_UTENTE_SEARCH_DATI_LOCALIZZ";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dati progetto nella form di
     * ricerca delle istanze
     */
    public static final String CONF_UTENTE_SEARCH_DATI_PROGETTO = "CONF_UTENTE_SEARCH_DATI_PROGETTO";
    /**
     * parametro per la configurazione posizione della bottoniera della pagina istanze
     */
    public static final String CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI = "ISTANZE_POSIZIONE_BOTTONI";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dei documenti degli endo
     */
    public static final String CONF_UTENTE_DOCUMENTIISTANZA_VISDOCENDO_DIV = "DOCUMENTIISTANZA_VISDOCENDO_DIV";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dei documenti dei movimenti
     */
    public static final String CONF_UTENTE_DOCUMENTIISTANZA_VISDOCMOV_DIV = "DOCUMENTIISTANZA_VISDOCMOV_DIV";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dei documenti delle
     * autorizzazioni / concessioni
     */
    public static final String CONF_UTENTE_DOCUMENTIISTANZA_VISDOCAUT_DIV = "DOCUMENTIISTANZA_VISDOCAUT_DIV";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dei documenti delle CDS
     */
    public static final String CONF_UTENTE_DOCUMENTIISTANZA_VISDOCCDS_DIV = "DOCUMENTIISTANZA_VISDOCCDS_DIV";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dei documenti dei campi
     * dinamici
     */
    public static final String CONF_UTENTE_DOCUMENTIISTANZA_VISDOCDYN_DIV = "DOCUMENTIISTANZA_VISDOCDYN_DIV";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione dei documenti delle
     * anagrafiche dell'istanza
     */
    public static final String CONF_UTENTE_DOCUMENTIISTANZA_VISDOCANAGR_DIV = "DOCUMENTIISTANZA_VISDOCANAGR_DIV";
    /**
     * parametro della configurazione utente che memorizza se reindirizzare l'utente alla lista dell'elaborazione dopo
     * aver effettuato un movimento in modo corretto
     */
    public static final String CONF_UTENTE_MOVIMENTI_OK_CHIUDI = "MOVIMENTI_OK_CHIUDI";
    /**
     * Parametro per la visualizzazione della composizione del mercato a lista piuttosto che a Blocchi (1=Lista,
     * 0=Blocchi) default 0. Viene al momento utilizzata in mercatid/list.htm
     */
    public static final String CONF_UTENTE_MERCATI_VIS_POSTEGGILISTA = "MERCATI_VIS_POSTEGGILISTA";
    /**
     * parametro della configurazione utente che memorizza se cercare le istanze per altri indirizzi o no
     */
    public static final String CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI = "CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI";
    /**
     * parametro della configurazione utente che memorizza il tipo di ordinamento (Asc,Desc)
     */
    public static final String CONF_UTENTE_ORIDIMANENTO_ISTANZE = "CONF_UTENTE_ORIDIMANENTO_ISTANZE";
    /**
     * parametro della configurazione utente che memorizza il campo per cui ordiniamo le istanze ricercate
     */
    public static final String CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE = "CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE";
    /**
     * parametro della configurazione utente che memorizza lo stato della istanza come filtro di ricerca
     */
    public static final String CONF_UTENTE_VALORE_STATO_ISTANZA = "CONF_UTENTE_VALORE_STATO_ISTANZA";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la colonna autorizzazioni nella lista
     * delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI = "VISAUTORIZZAZIONI";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la colonna CODICE PRATICA TELEMATICA
     * nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISCODPRATEL = "VISCODPRATEL";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la colonna endoprocedimenti nella lista
     * delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI = "VISENDOPROCEDIMENTI";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la colonna altri indirizzi nella lista
     * delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA = "VISINDIRIZZIISTANZA";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la colonna altri sorteggi nella lista
     * delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE = "VISSORTEGGIATE";
    /**
     * configurazione utente visualizza lo stato istanza nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA = "LISTISTANZA_VISSTATOISTANZA";
    /**
     * configurazione utente visualizza posizione in archivio istanza nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO = "VISPOSIZIONEARCHIVIO";
    /**
     * configurazione utente visualizza il tipo archivio nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISARCHIVIO = "VISARCHIVIO";
    /**
     * configurazione utente visualizza la colonna lavori nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISLAVORI = "VISLAVORI";
    /**
     * configurazione utente visualizza la colonna procedura nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISPROCEDURA = "VISPROCEDURA";
    /**
     * configurazione utente visualizza la colonna intervento nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISINTERVENTO = "VISINTERVENTO";
    /**
     * configurazione utente visualizza la colonna istruttore nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISISTRUTTORE = "LISTISTANZA_VISISTRUTTORE";
    /**
     * configurazione utente visualizza la colonna localizzazione nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE = "VISLOCALIZZAZIONE";
    /**
     * configurazione utente visualizza la colonna operatore nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISOPERATORE = "LISTISTANZA_VISOPERATORE";
    /**
     * configurazione utente visualizza la colonna responsabile del procedimento nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISRESPPROC = "LISTISTANZA_VISRESPPROC";
    /**
     * configurazione utente visualizza la colonna richiedente nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE = "VISRICHIEDENTE";
    /**
     * configurazione utente visualizza la colonna richiedente storico nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO = "VISRICHIEDENTESTORICO";
    /**
     * configurazione utente visualizza la colonna TECNICO nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISTECNICO = "LISTISTANZA_VISTECNICO";
    /**
     * configurazione utente visualizza la colonna tipologia istanza nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA = "VISTIPOLOGIAISTANZA";
    /**
     * configurazione utente visualizza la colonna data protocollo nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO = "VISDATAPROTOCOLLO";
    /**
     * configurazione utente visualizza la colonna numeroprotocollo nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO = "VISNUMOPERATOREINCARICO";
    /**
     * configurazione utente visualizza la colonna numeroprotocollo nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO = "VISNUMPROTOCOLLO";
    /**
     * Configurazione utente visualizza il div preferenze di visualizzazioen
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISPREFERENZE_DIV = "LISTISTANZA_VISPREFERENZE_DIV";
    /**
     * Configurazione utente: se la lista delle istanze contiene solo un record viene visualizzata direttamente
     * l'istanza
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISISTANZA = "LISTISTANZA_VISISTANZA";
    /**
     * Configurazione utente: se la lista delle attivita contiene solo un record viene visualizzata direttamente
     * l'attivita
     */
    public static final String CONF_UTENTE_LISTATTIVITA_VISATTIVITA = "LISTATTIVITA_VISATTIVITA";
    /**
     * configurazione utente visualizza la colonna comune nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISCOMUNE = "LISTISTANZA_VISCOMUNE";
    /**
     * configurazione utente visualizza la colonna note (lavori estesi) nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI = "LISTISTANZA_LAVORIESTESI";
    /**
     * configurazione utente visualizza la colonna link documenti dell'istanza nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA = "VISLINKDOCISTANZA";
    /**
     * configurazione utente visualizza la colonna DENOMINAZIONE ATTIVITA' dell'istanza nella lista delle istanze
     */
    public static final String CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA = "VISDENOMINAZIONEATTIVITA";
    /**
     * parametro della configurazione utente che memorizza il tipo di ordinamento (Asc,Desc)
     */
    public static final String CONF_UTENTE_ORDINAMENTO_AUTORIZZAZIONI = "CONF_UTENTE_ORDINAMENTO_AUTORIZZAZIONI";
    /**
     * parametro della configurazione utente che memorizza il campo per cui ordiniamo le autorizzaioni ricercate
     */
    public static final String CONF_UTENTE_CAMPI_ORDINAMENTO_AUTORIZZAZIONI = "CONF_UTENTE_CAMPI_ORDINAMENTO_AUTORIZZAZIONI";
    /**
     * parametro della configurazione utente che memorizza il campo per cui ordiniamo le istanze acccesso atti
     */
    public static final String CONF_UTENTE_CAMPI_ORDINAMENTO_ISTANZEACCESSOATTI = "CONF_UTENTE_CAMPI_ORDINAMENTO_ISTANZEACCESSOATTI";
    /**
     * parametro della configurazione utente che memorizza il tipo di ordinamento (Asc,Desc)
     */
    public static final String CONF_UTENTE_ORDINAMENTO_ISTANZEACCESSOATTI = "CONF_UTENTE_ORDINAMENTO_ISTANZEACCESSOATTI";
    /**
     * parametro della configurazione utente che memorizza il tipo di ordinamento (Asc,Desc)
     */
    public static final String CONF_UTENTE_ORDINAMENTO_CONCESSIONI = "CONF_UTENTE_ORDINAMENTO_CONCESSIONI";
    /**
     * parametro della configurazione utente che memorizza il campo per cui ordiniamo le concessioni ricercate
     */
    public static final String CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI = "CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI";
    /**
     * Parametri per la gestione di espansione e compressione delle sezioni nella funzionalità permessi istanza
     */
    public static final String CONF_UTENTE_ISTANZA_AMMINISTRATORI_ABILITATI = "ISTANZA_AMMINISTRATORI_ABILITATI";
    public static final String CONF_UTENTE_ISTANZA_AMMINISTRATORI_SOFTWARE_ABILITATI = "ISTANZA_AMMINISTRATORI_SOFTWARE_ABILITATI";
    public static final String CONF_UTENTE_ISTANZA_RUOLI_ACCESSO = "UTENTE_ISTANZA_RUOLI_ACCESSO";
    public static final String CONF_UTENTE_ISTANZA_OPERATORI_ACCESSO = "ISTANZA_OPERATORI_ACCESSO";
    public static final String CONF_UTENTE_VIS_LOCALIZZAZIONI = "VIS_LOCALIZZAZIONI";
    public static final String CONF_UTENTE_VIS_MAPPALI = "VIS_MAPPALI";
    public static final String CONF_UTENTE_VIS_SOGGCOLL = "VIS_SOGGCOLL";
    public static final String CONF_UTENTE_VIS_DETTINFO = "VIS_DETTINFO";
    public static final String CONF_UTENTE_VIS_AUTORIZZAZIONI = "VIS_AUTORIZZAZIONI";
    public static final String CONF_UTENTE_VIS_CONCESSIONI = "VIS_CONCESSIONI";
    public static final String CONF_UTENTE_VIS_ISTANZE = "VIS_ISTANZE";
    public static final String CONF_UTENTE_VIS_ORARI = "VIS_ORARI";
    public static final String CONF_UTENTE_VIS_ENDOPROCEDIMENTI = "VIS_ENDOPROCEDIMENTI";
    public static final String CONF_UTENTE_VIS_CALCOLOCANONI = "VIS_CALCOLOCANONI";
    public static final String CONF_UTENTE_VIS_ONERI = "VIS_ONERI";
    public static final String CONF_UTENTE_VIS_VISSTORICOATTIVITA = "VISSTORICOATTIVITA";
    public static final String CONF_UTENTE_NUOVA_AUT_VIS_AUTORIZZAZIONI = "NUOVA_AUT_VIS_AUTORIZZAZIONI";
    /**
     * Parametri per la visulaizzazione dello storico di ogni singola sezione all'interno di un attività
     */
    public static final String CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI = "STORICO_ATTIVITA_LOCALIZZAZIONI";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI = "STORICO_ATTIVITA_SOGGETTI_COLLEGATI";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO = "STORICO_ATTIVITA_DETTAGLIO_INFO";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_ORARI = "STORICO_ATTIVITA_ORARI";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI = "STORICO_ATTIVITA_AUTORIZZAZIONI";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI = "STORICO_ATTIVITA_CONCESSIONI";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI = "STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI";
    public static final String CONF_UTENTE_STORICO_ATTIVITA_ONERI = "STORICO_ATTIVITA_ONERI";
    /**
     * 
     */
    public static final String CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA = "RAGGRUPPA_AUT_ATT_PER_ISTANZA";
    public static final String CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA = "RAGGRUPPA_CONC_ATT_PER_ISTANZA";
    /**
     * Parametri per la gestione di espansione e compressione delle sezioni nella funzionalità istanzeattivita
     */
    public static final String CONF_UTENTE_IST_ATT_VISUALIZZA = "CONF_UTENTE_IST_ATT_VISUALIZZA";
    public static final String CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT = "CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT";
    public static final String CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT = "CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT";
    public static final String CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT = "CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT";
    /**
     * Parametri per la gestione di espansione e compressione delle sezioni nella funzionalità sorteggitestata
     */
    public static final String CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV = "CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV";
    public static final String CONF_UTENTE_SORT_TEST_VISNONSORTEGGIATE_DIV = "CONF_UTENTE_SORT_TEST_VISNONSORTEGGIATE_DIV";
    public static final String CONF_UTENTE_SORT_TEST_VISFILTRI_DIV = "CONF_UTENTE_SORT_TEST_VISFILTRI_DIV";
    /**
     * Parametro per la configurazione utente sull'ultimo catasto scelto (Funzionalità di inserimentide mappali)
     */
    public static final String CONF_UTENTE_CATASTO_SCELTO = "CONF_UTENTE_CATASTO_SCELTO";
    /**
     * Parametro per la configurazione utente per lo stile dei css scelto dall'utente
     */
    public static final String CONF_UTENTE_STILE_BO = "StileBO";
    /**
     * Parametri di configurazione utente per i criteri di filtro della pagina PEC-INBOX
     */
    public static final String CONF_UTENTE_PECINBOX_LETTI = "CONF_UTENTE_PECINBOX_LETTI_NONLETTI";
    public static final String CONF_UTENTE_PECINBOX_LAVORATI = "CONF_UTENTE_PECINBOX_LAVORATI_NONLAVORATI";
    public static final String CONF_UTENTE_PECINBOX_ELABORATI = "CONF_UTENTE_PECINBOX_ELABORATI_NONELABORATI";
    public static final String CONF_UTENTE_PECINBOX_DATADA = "CONF_UTENTE_PECINBOX_DATADA";
    public static final String CONF_UTENTE_PECINBOX_DATAA = "CONF_UTENTE_PECINBOX_DATAA";
    public static final String CONF_UTENTE_PECINBOX_JMESA = "CONF_UTENTE_PECINBOX_JMESA";
    public static final String CONF_UTENTE_PECINBOX_RICEVUTE = "CONF_UTENTE_PECINBOX_MOSTRA_RICEVUTE";
    public static final String CONF_UTENTE_PECINBOX_SCOMPATTA_ALLEGATI = "CONF_UTENTE_PECINBOX_SCOMPATTA_ALLEGATI";
    /**
     * Parametro per salvare la configurazione sulla funzionalità documenti messi alla firma, permette di decidere se
     * filtrare la lista mostrando o no solo i documenti mandati alla firma da me
     */
    public static final String CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME = "CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME";
    /**
     * parametro che identifica se la manifestazione deve avere il comportamento di un mercato
     */
    public static final int MANIFESTAZIONE_COMPORTAMENTO_MERCATO = 1;
    /**
     * parametro che identifica se la manifestazione deve avere il comportamento di una fiera
     */
    public static final int MANIFESTAZIONE_COMPORTAMENTO_FIERA = 0;
    /**
     * parametri per inserimento massivo dei movimenti da graduatoria o ricerca istanze
     * 
     */
    public static final String MERCATO_ASSEGNATARI = "assegnatari";
    public static final String MERCATO_NONASSEGNATARI = "nonassegnatari";
    public static final String MERCATO_TUTTI = "tutti";
    public static final String PROGRESS_BAR = "progressbar";
    /**
     * Numero cifre decimali
     */
    public static final Integer NUMERO_CIFRE_DECIMALI = 2;
    /**
     * Numero cifre decimali per gli interessi contabili
     */
    public static final Integer NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI = 6;
    /**
     * Indica il numero di riferimento per determinare le date di scadenza delle rate
     */
    public static final Integer NUMERO_GIORNI_CONTABILI = 30;
    /**
     * Indica il numero minimo di rate non pagate nella ricerca rate non pagate
     */
    public static final String NUMERO_MINIMO_RATE_NON_PAGATE = "2";
    /**
     * parametro della configurazione utente che memorizza i filtri dell'ultima ricerca fatta su Letture contatore
     */
    public static final String CONF_UTENTE_RICERCA_LETTURE_CONTATORI = "LETTURE_CONTATORI";
    /**
     * parametro per il campo presunta di mercatidletture. p=Presunta
     */
    public static final String MERCATIDLETTURE_PRESUNTA_PRESUNTA = "p";
    /**
     * parametro per il campo presunta di mercatidletture. e=Effettiva
     */
    public static final String MERCATIDLETTURE_PRESUNTA_EFFETTIVA = "e";
    /**
     * Descrizione per le registrazioni delle letture contatori
     */
    public static final String DESCRIZIONE_REGISTRAZIONI_LETTURE_CONTATORI = "Letture contatore";
    /**
     * Password di protezione file excel in Mercatidletture
     */
    public static final String PWD_EXCEL_SHEET = "sigepro";
    /**
     * parametro della configurazione utente che memorizza la modalità di pagamento
     */
    public static final String CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO = "REG_IO_MODALITA_PAGAMENTO";
    /**
     * parametro della configurazione utente che memorizza la data incasso
     */
    public static final String CONF_UTENTE_REG_IO_DATA_INCASSO = "REG_IO_DATA_INCASSO";
    /**
     * parametro della configurazione utente che memorizza se verranno visualizzati i dettagli. Verranno visualizzate le
     * colonne Scadenza, Nr.registrazione, Causale, Manifestazione, Giorno, Posteggio, Nr.rata, Conto
     */
    public static final String CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI = "REG_IO_VISUALIZZA_DETTAGLI";
    /**
     * parametro per la configurazione della visulaizzarione raggruppata delle rate
     */
    public static final String CONF_UTENTE_REGISTRAZIONI_RATE_GROUPED = "REGISTRAZIONI_RATE_GROUPED";
    /**
     * Variabile da utilizzare come separatore tra item es. nell'ajax controller quando si vogliono far tornare più
     * valori (vedi findContieIva) in cui si vuole far tornare sia il codice del conto che il valore dell'IVA
     * configurata per qual conto si usa questa variabile per separare i valori
     */
    public static final String ITEM_SEPARATOR = "|";
    /**
     * separatore dei valori multipli associati ad un singolo campo dinamico
     */
    public static final String ISTANZEDYN2DATI_VALUE_SEPARATOR = ";";
    /**
     * parametro per la configurazione utente per l'aggiornameto delle notifiche ausl
     */
    public static final String CONF_UTENTE_UPDATE_NOTIFICHE_AUSL = "UPDATE_NOTIFICHE_AUSL";
    /**
     * parametri che rappresentano i codice delle amministrazioni nella tabella configurazioni
     */
    public static final Integer CODICELASTESSAAMMINISTRAZIONE = -2;
    public static final Integer CODICETUTTEAMMINISTRAZIONI = -1;
    public static final String VERTICALIZZAZIONE_API_SERVICE = "API_SERVICE";
    public static final String VERTICALIZZAZIONE_API_SERVICE_URL = "API_SERVICE_URL";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_GOOGLE_MAPS = "GOOGLE_MAPS";
    public static final String VERTICALIZZAZIONE_GOOGLE_MAPS_API_KEY = "API_KEY";
    public static final String VERTICALIZZAZIONE_AUTORIZ_ACCESSI = "AUTORIZ_ACCESSI";
    public static final String VERTICALIZZAZIONE_CONSOLE = "CONSOLE";
    public static final String VERTICALIZZAZIONE_CONSOLE_URL_APP_ALLINEAMENTO = "URL_APP_ALLINEAMENTO";
    public static final String VERTICALIZZAZIONE_CONSOLE_ALIAS_DATI_CONSOLE = "ALIAS_DATI_CONSOLE";
    public static final String VERTICALIZZAZIONE_CONSOLE_SOFTWARE_DATI_CONSOLE = "SOFTWARE_DATI_CONSOLE";
    public static final String VERTICALIZZAZIONE_CONSOLE_SC_CODICE_PARTENZA = "SC_CODICE_PARTENZA";
    public static final String VERTICALIZZAZIONE_CONSOLE_SERVIZI_CONSOLE_REMOTA = "SERVIZI_CONSOLE_REMOTA";
    public static final String VERTICALIZZAZIONE_EDIT_DOCS_APPLICATION = "EDIT_DOCS_APPLICATION";
    public static final String VERTICALIZZAZIONE_EDIT_DOCS_APPLICATION_URL_CODEBASE_FO = "URL_CODEBASE_FO";
    public static final String VERTICALIZZAZIONE_EDIT_DOCS_APPLICATION_URL_CODEBASE_BO = "URL_CODEBASE_BO";
    public static final String VERTICALIZZAZIONE_CENTER_PAGE_CENTERPAGE = "CENTERPAGE";
    public static final String VERTICALIZZAZIONE_CENTER_PAGE = "CENTER_PAGE";
    public static final String VERTICALIZZAZIONE_DBINFORMATICA = "DBINFORMATICA";
    public static final String VERTICALIZZAZIONE_DBINFORMATICA_WS_URL_DBENERGIA_QC = "WS_URL_DBENERGIA_QC";
    public static final String VERTICALIZZAZIONE_DBINFORMATICA_WS_CODICE_IMPIANTO_DEFAULT = "CODICE_IMPIANTO_DEFAULT";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE = "COMPORTAMENTI_ISTANZE";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_BLOCCA_DEL_MOVALL_OPE_MOV = "BLOCCA_DEL_MOVALL_OPE_MOV";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_COMPORTAMENTO_VIS_ICONA_A_IN_LISTAISTANZE = "VIS_ICONA_A_IN_LISTAISTANZE";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_PROPORRE_DATA = "MOV_NON_PROPORRE_DATA";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_IMPOSTARE_SCADENZA = "MOV_NON_IMPOSTARE_SCADENZA";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_ELAB_MOVIMENTI_PREC_CHIUS = "ELAB_MOVIMENTI_PREC_CHIUS";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_ELAB_MOVIMENTI_DOPO_DATA_MOV = "ELAB_MOVIMENTI_DOPO_DATA_MOV";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_NON_MOSTRARE_INVIOMAIL = "NON_MOSTRARE_INVIOMAIL";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_SEGNA_DOCUMENTO_FIRMATO = "SEGNA_DOCUMENTO_FIRMATO";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_DIS_CANC_MOV_PROTOCOLLATI = "DIS_CANC_MOV_PROTOCOLLATI";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_VISUALIZZA_CAMBIO_STATO_IST_COLLEGATE = "VIS_CAMB_STATO_IST_COLLEGATE";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_URL_WS_RIEPILOGO_PRATICA = "URL_WS_RIEPILOGO_PRATICA";
    public static final String VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI = "ASSEGNAZIONE_OPERATORI";
    public static final String VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_ESCLUDI_ASSEGNAZIONE_AUTO = "ESCLUDI_ASSEGNAZIONE_AUTO";
    public static final String VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_GRUPPO_ISTRUTTORI_DEFAULT = "GRUPPO_ISTRUTTORI_DEFAULT";
    public static final String VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_ASSEGNAZIONE_CAPIENZA_GRUPPO = "ASSEGNAZIONE_CAPIENZA_GRUPPO";
    public static final String VERTICALIZZAZIONE_ANTICORRUZIONE = "ANTI_CORRUZIONE";
    public static final String VERTICALIZZAZIONE_ANTICORRUZIONE_MAIL_TIPO_ASSEGNAZIONE = "MAIL_TIPO_ASSEGNAZIONE";
    public static final String VERTICALIZZAZIONE_ANTICORRUZIONE_TESTO_TIPO_PRESA_IN_CARICO = "TESTO_TIPO_PRESA_IN_CARICO";
    public static final String VERTICALIZZAZIONE_ANTICORRUZIONE_MAIL_TIPO_PRESA_IN_CARICO = "MAIL_TIPO_PRESA_IN_CARICO";
    public static final String VERTICALIZZAZIONE_ANTICORRUZIONE_MODIFICA_ISTR_DA_LISTA_COMPL = "MODIFICA_ISTR_DA_LISTA_COMPL";
    public static final String VERTICALIZZAZIONE_ANTICORRUZIONE_MAIL_TIPO_RIFIUTO_INCARICO = "MAIL_TIPO_RIFIUTO_INCARICO";
    /**
     * Parametri verticalizzazione - APPLICA_LAYER_PROT_PDF
     */
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF = "APPLICA_LAYER_PROT_PDF";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_X = "POSIZIONE_X";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_Y = "POSIZIONE_Y";
    // OBSOLETO                            
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_ETICHETTA_NUM_PROT = "ETICHETTA_NUM_PROT";
    // OBSOLETO                            
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_ETICHETTA_DATA_PROT = "ETICHETTA_DATA_PROT";
    // OBSOLETO                            
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NOME_LAYER = "NOME_LAYER";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_X = "DIM_RETTANGOLO_X";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_Y = "DIM_RETTANGOLO_Y";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NUM_PAGINA_ANNOTATION = "NUM_PAGINA_ANNOTATION";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COLORE_FONT_ANNOTATION = "COLORE_FONT_ANNOTATION";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COLORE_BACKGROUND_ANNOTATION = "COLORE_BACKGROUND_ANNOTATION";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_LOCK_ANNOTATION = "LOCK_ANNOTATION";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO = "COD_MAIL_TIPO";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO_MOV = "COD_MAIL_TIPO_MOV";
    public static final String VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_IS_BORDO_ANNOTAZIONE_ATTIVO = "IS_BORDO_ANNOTAZIONE_ATTIVO";
    /**
     * Parametri verticalizzazione - ACCESSO AGLI ATTI
     */
    public static final String VERTICALIZZAZIONE_ACCESSO_AGLI_ATTI = "ACCESSO_AGLI_ATTI";
    /**
     * 
     * 
     * Rappresenta il valore del modulo nelle verticalizzazioni per l'acquisizioni di file mediante SCANNER
     */
    public static final String VERTICALIZZAZIONE_MODULO_ACQUISIZIONE_TWAIN = "ACQUISIZIONE_TWAIN";
    /**
     * Nella pubblicazione dei messaggi di Audit tramite jms rappresenta quello che viene inviato come
     * AuditMessage.appChiamante
     */
    public static final String JMS_APPLICATION_ID = "SIGEPRO_BACKOFFICE";
    /**
     * Chiave del ResourceBundle da utilizzare per il messaggio di violazione chiave esterna
     */
    public static final String ALERT_FOREIGN_KEY = "alert.foreignkey.violated.causedby";
    /**
     * 
     */
    public static final String ALERT_CANCELLAZIONE_ISTANZE_ONERI_CON_POSIZIONE_DEBITORIA = "service_error.istanzeoneri.cancellazione.posizione_debitoria_non eliminabile";
    /**
     * 
     */
    public static final String ALERT_SERVICE_ERROR_PRESENTI_CONCESSIONI_PER_MERCATO = "service_error.sono_presenti_concessioni_per_il_mercato";
    /**
     * Chiave del ResourceBundle da utilizzare per il messaggio di una dipendenza di una natura con un altra.
     */
    public static final String ALERT_COMPATIBILE_IN = "alert.esiste.dipendenza";
    /**
     * Chiave del ResourceBundle da utilizzare per il messaggio di incompatibilità tra gli endo
     */
    public static final String ALERT_ENDO_INCOMPATIBILI = "alert.inventarioprocedimenti.incompatibili";
    /**
     * Chiave del ResourceBundle da utilizzare per il messaggio di errore invio email
     */
    public static final String ERROR_INVIO_EMAIL = "error.inviomail";
    /**
     * Chiave del Resource Bundle da utilizzare per il messaggio di parametri non validi per un metodo di classe.<br />
     * Utilizza due parametri {0} nome del metodo che lancia l'eccezione, {1} nome della classe che contiene il metodo
     */
    public static final String ALERT_SERVICE_ERROR_PARAMETERS_NOT_VALID = "service_error.parametri_non_validi_per_il_metodo_della_classe";
    /**
     * Chiave del bundle da utilizzare per istanza creata tramite sistema STC
     */
    public static final String ALERT_ISTANZA_CREATA_DA_STC = "service_error.istanza_creata_da_stc";
    /**
     * Chiave del bundle da utilizzare per movimento creato tramite sistema STC
     */
    public static final String ALERT_MOVIMENTO_CREATO_DA_STC = "service_error.movimento_creato_da_stc";
    /**
     * Chiave del bundle da utilizzare per indicare che ci sono oneri pagati esternamente
     */
    public static final String ALERT_ONERI_PAGATI_ESTERNAMENTE = "service_error.oneri_pagati_da_sistema_esterno";
    /**
     * Chiave del bundle da utilizzare per indicare che l'istanza presenta già un onere con quella causale
     */
    public static final String ALERT_ISTANZEONERI_PRESENTE = "service_error.istanzeoneri_presente";
    /**
     * Chiave del bundle da utilizzare per indicare che ci sono modelli collegati che non presentano il flag riga
     * multipla
     */
    public static final String ALERT_NO_FLAG_MULTIPLO = "service_error.no_flag_multiplo";
    /**
     * Constante STP - Scheda di Spiegazione Endo Tipo 1 - QUADRO STANDARD 5: download allegato.
     */
    public static final String STP_QUADRO5 = "STP_QUADRO5";
    /**
     * Constante STP - Scheda di Spiegazione Endo Tipo 1 - QUADRO STANDARD 6: download allegato.
     */
    public static final String STP_QUADRO6 = "STP_QUADRO6";
    /**
     * Constante STP - Scheda di Spiegazione Endo Tipo 1 - QUADRO STANDARD 5 ALLEGATI: download allegato.
     */
    public static final String STP_QUADRO5_ALLEGATI = "STP_QUADRO5_ALLEGATI";
    /**
     * Constante STP - Scheda di Spiegazione Endo Tipo 1 - QUADRO STANDARD 6 ALLEGATI: download allegato.
     */
    public static final String STP_QUADRO6_ALLEGATI = "STP_QUADRO6_ALLEGATI";
    /**
     * Rappresenta il valore della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART = "CART";
    public static final String VERTICALIZZAZIONE_CART_TIMEOUTWSCALL = "WSCALL_TIMEOUT";
    /**
     * Rappresenta la verticalizzazione per la creazione automaticamente delle schede
     */
    public static final String CREA_AUTOMATICAMENTE_SCHEDE = "CREA_AUTOMATICAMENTE_SCHEDE";
    /**
     * identifica che il campo categoria merceologica è utilizzato per le fiere
     */
    public static final String MERCATI_CAT_MERC_USO_FIERE = "F";
    /**
     * identifica che il campo categoria merceologica è utilizzato per i mercati
     */
    public static final String MERCATI_CAT_MERC_USO_MERCATI = "M";
    /**
     * identifica che il campo categoria merceologica è utilizzato per le fiere ed i mercati
     */
    public static final String MERCATI_CAT_MERC_USO_ENTRAMBI = "FM";
    /**
     * 
     */
    public static final int MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA = 1;
    /**
     * 
     */
    public static final int MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_SINGOLA = 2;
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_CMIS = "FILESYSTEM_CMIS";
    /**
     * URL di Pubblicazione ATOM
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_CMIS_ATOM_URL = "CMIS_ATOM_URL";
    /**
     * Password per l'autenticazione applicativa verso CMIS Server
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_CMIS_PASSWORD = "CMIS_PASSWORD";
    /**
     * id della cartella base sulla quale caricare i file
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_CMIS_DOCUMENT_ROOT_FOLDER = "CMIS_DOCUMENT_ROOT_FOLDER";
    /**
     * Username per l'autenticazione applicativa verso CMIS Server
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_CMIS_USERNAME = "CMIS_USERNAME";
    /**
     * Modulo verticalizzazione AREA_RISERVATA
     */
    public static final String VERTICALIZZAZIONE_AREA_RISERVATA = "AREA_RISERVATA";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_PROXY_ENABLED = "CART_PROXY_ENABLED";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_PROXY_HOST = "CART_PROXY_HOST";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_PROXY_PORT = "CART_PROXY_PORT";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_PROXY_USER_NAME = "PROXY_USER_NAME";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_PROXY_PASSWORD = "PROXY_PASSWORD";
    /**
     * Rappresenta il valore del parametro ROOT_ALBEROPROC_ID della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC = "ROOT_ALBEROPROC_ID";
    /**
     * Rappresenta il valore del parametro MITTENTE della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_MITTENTE = "MITTENTE";
    /**
     * Rappresenta il valore del parametro SERVIZIO della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_SERVIZIO = "SERVIZIO";
    /**
     * Rappresenta il valore del parametro TIPO_SERVIZIO della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_TIPO_SERVIZIO = "TIPO_SERVIZIO";
    /**
     * Rappresenta il valore del parametro PDD_LOCATION della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_PDD_LOCATION = "PDD_LOCATION";
    /**
     * Rappresenta il valore del parametro USER_NAME della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_USERNAME = "USER_NAME";
    /**
     * Rappresenta il valore del parametro PASSWORD della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_PASSWORD = "PASSWORD";
    /**
     * Rappresenta il valore del parametro INTEGRATION_MANAGER_URL della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_INTEGRATION_MANAGER_URL = "INTEGRATION_MANAGER_URL";
    /**
     * Rappresenta il valore del parametro ENDO_ALBEROPROC_ENDO.FKAZID della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_AZIONI = "ENDO_ALBEROPROC_ENDO.FKAZID";
    /**
     * Rappresenta il valore del parametro ENDO_INVENTARIOPROC.CODNATURA della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_CODICENATURA = "ENDO_INVENTARIOPROC.CODNATURA";
    /**
     * Rappresenta il valore del parametro ENDO_INVENTARIOPROC.TIPOMOV della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_TIPIMOVIMENTO = "ENDO_INVENTARIOPROC.TIPOMOV";
    /**
     * Rappresenta il valore del parametro ENDO_INVENTARIOPROC.TIPOMOV della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_AMMINISTRAZIONE = "ENDO2_INVENTARIOPROC.AMMIN";
    /**
     * Rappresenta il valore del parametro ENDO2_TIPIFAMIGLIEENDO.CODICE della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_FAMIGLIAENDO = "ENDO2_TIPIFAMIGLIEENDO.CODICE";
    /**
     * Rappresenta il valore del parametro ENDO2_INVENTARIOPROCEDIMENTI della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_INVENTARIO = "ENDO2_INVENTARIOPROCEDIMENTI";
    /**
     * Valore "S" ammesso per il parametro ENDO2_INVENTARIOPROCEDIMENTI della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO = "S";
    /**
     * Valore "N" ammesso per il parametro ENDO2_INVENTARIOPROCEDIMENTI della verticalizzazione per il CART
     */
    public static final String VERTICALIZZAZIONE_CART_VALORE_N_INVENTARIO = "N";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_TIPODESTINATARIO = "TIPODESTINATARIO";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_TIPOMITTENTE = "TIPOMITTENTE";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_DESTINATARIO = "DESTINATARIO";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_SERVIZIOAPPLICATIVO = "SERVIZIOAPPLICATIVO";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_TEMPIFICAZIONE = "TEMPIFICAZIONE";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_SUAP_ID = "SUAP_ID";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_CODICETIPOPROCEDURA_NUOVEISTANZE = "NUOVA_DOMANDA.ID_TIPOPROCEDURA";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_GENERA_ALLEGATI_NOTIFICA = "GENERA_ALLEGATI_NOTIFICA";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_CART_SYNC_DIZIONARIO_USA_STP_CODICE = "SYNC_DIZIONARIO_USA_STP_CODICE";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_DOC_AREA = "DOC_AREA";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM = "FILESYSTEM";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH = "SHAREDPATH";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_DIRECTORY_LOCALE = "DIRECTORY_LOCALE";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_READONLY = "READONLY";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_FILESYSTEM_ESEGUI_CHMOD_FILE = "ESEGUI_CHMOD_FILE";
    /**
     * 
     * 
     */
    public static final String VERTICALIZZAZIONE_GENERA_COD_PRATICA_TELEMATICA = "GENERA_COD_PRATICA_TELEMATICA";
    /**
     * "Permette di gestire la cancellazione dei dati sensibili di gp-backoffice impostando alcune password sulle
     * funzionalità di cancellazione. ( ad esempio la cancellazione di un'istanza ). Se non attivata, al momento della
     * cancellazione di questi dati, non verrà richiesta nessuna password di sicurezza."
     */
    public static final String VERTICALIZZAZIONE_GEST_CANCELLAZIONI = "GEST_CANCELLAZIONI";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_ISTANZE = "PWD_ISTANZE";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SORTEGGI = "PWD_SORTEGGI";
    /**
     * parametro che contiene la password richiesta ad un operatore per sbloccare una PEC bloccata da un altro operatore
     */
    public static final String VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SBLOCCO_PEC = "PASSWORD_SBLOCCO_PEC";
    /**
     * parametro che contiene la password richiesta ad un operatore per cancellare un Movimento.
     */
    public static final String VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_MOVIMENTO = "PWD_MOVIMENTO";
    /**
     * Modulo delle verticalizzazioni I_ATTIVITA Gestione delle attività
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA = "I_ATTIVITA";
    /**
     * Parametro QUERYDENOMINAZIONE
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_QUERYDENOMINAZIONE = "QUERYDENOMINAZIONE";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_INVERTI_RICHIEDENTE_STORICO = "INVERTI_RICHIEDENTE_STORICO";
    /**
     * Parametro PRECOMPILAINDIRIZZOCIVICO
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_PRECOMPILAINDIRIZZOCIVICO = "PRECOMPILAINDIRIZZOCIVICO";
    /**
     * Parametro AGGIORNADENOMINAZIONE
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_AGGIORNADENOMINAZIONE = "AGGIORNADENOMINAZIONE";
    /**
     * Parametro GRUPPOSOFTWARE
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_GRUPPOSOFTWARE = "GRUPPOSOFTWARE";
    /**
     * Indica il nome del campo dinamico che riporta la data di fine periodo di occupazione temporaneo
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_CAMPO_DYN_FINE_ATT = "CAMPO_DYN_FINE_ATT";
    /**
     * parametro per gestire il comportamento in fase di creazione attività o collegamento di un istanza all'attività.
     * Permette di decidere se il sistema deve considerare o no le istanze collegate all'istanza che sta creando o
     * modificando l'attività
     */
    public static final String VERTICALIZZAZIONE_I_ATTIVITA_NON_CONSIDERARE_ISTANZE_COLL = "NON_CONSIDERARE_ISTANZE_COLL";
    /**
     * Modulo verticalizzazione infocamera
     */
    public static final String VERTICALIZZAZIONE_INFOCAMERA = "INFOCAMERA";
    /**
     * Se attiva permette la modifica dell'intervento ( intervento - procedura - mov. avvio ) delle istanze escludendo
     * le regole attuali che ne consentono la modifica solamente se:
     * 
     * <pre>
     * - Non esistono altri movimenti ad eccezione di quello di avvio 
     * - Non esistono oneri collegati alla pratica 
     * - Non esistono endoprocedimenti attivati 
     * - Non esistono documenti allegati
     * - Non esistono dati dinamici valorizzati
     * </pre>
     */
    public static final String VERTICALIZZAZIONE_MODIFICA_INTERVENTO = "MODIFICA_INTERVENTO";
    /**
     * Modulo verticalizzazione REPLICAISTANZE
     */
    public static final String VERTICALIZZAZIONE_REPLICAISTANZE = "REPLICAISTANZE";
    /**
     * Modulo verticalizzazione LIVORNO_SERVIZI_CITTADINO
     */
    public static final String VERTICALIZZAZIONE_LIVORNO_SERVIZI_CITTADINO = "LIVORNO_SERVIZI_CITTADINO";
    /**
     * Modulo verticalizzazione PROCEDI_MARCHE
     */
    public static final String VERTICALIZZAZIONE_PROCEDI_MARCHE = "PROCEDI_MARCHE";
    public static final String VERTICALIZZAZIONE_PROCEDI_MARCHE_URL = "PROCEDI_MARCHE_URL";
    public static final String VERTICALIZZAZIONE_PROCEDI_MARCHE_USR = "PROCEDI_MARCHE_USR";
    public static final String VERTICALIZZAZIONE_PROCEDI_MARCHE_PWD = "PROCEDI_MARCHE_PWD";
    public static final String VERTICALIZZAZIONE_PROCEDI_MARCHE_CF_ENTE = "PROCEDI_MARCHE_CF_ENTE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_IRIDE = "PROTOCOLLO_IRIDE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_IRIDE2 = "PROTOCOLLO_IRIDE2";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_IRIDE_MOSTRA_METTI_ALLA_FIRMA = "MOSTRA_METTI_ALLA_FIRMA";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER = "PROTOCOLLO_DOCER";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_URL_LOGIN = "URL_LOGIN";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_URL_GESTIONE_DOCS = "URL_GESTIONE_DOCS";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_CODICE_ENTE = "CODICE_ENTE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_CODICE_AOO = "CODICE_AOO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_APPLICAZIONE = "APPLICAZIONE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_TIPO_INVIO_PEC = "TIPO_INVIO_PEC";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_DOCER_USA_LDAP_AUTH = "USA_LDAP_AUTH";
    /**
     * Modulo verticalizzazione PEOPLE
     */
    public static final String VERTICALIZZAZIONE_PEOPLE = "PEOPLE";
    /**
     * Modulo verticalizzazione SIEDER
     */
    public static final String VERTICALIZZAZIONE_SIEDER = "SIEDER";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_SIEDER_CODICECAUSALEONEREDEFAULT = "CODICECAUSALEONEREDEFAULT";
    public static final String VERTICALIZZAZIONE_SIEDER_URL_STATI_AMMISSIBILI = "URL_STATI_AMMISSIBILI";
    public static final String VERTICALIZZAZIONE_SIEDER_URL_STATO_PRATICA = "URL_STATO_PRATICA";
    public static final String VERTICALIZZAZIONE_SIEDER_URL_MODIFICA_STATO_PRATICA = "URL_MODIFICA_STATO_PRATICA";
    /**
     * parametro CODICECAUSALEONEREDEFAULT della verticalizzazione PEOPLE
     */
    public static final String VERTICALIZZAZIONE_PEOPLE_CODICECAUSALEONEREDEFAULT = "CODICECAUSALEONEREDEFAULT";
    /**
     * Modulo verticalizzazione AIDA
     */
    public static final String VERTICALIZZAZIONE_AIDA = "AIDA";
    /**
     * Modulo verticalizzazione SISTEMAPAGAMENTI_ATTIVO
     */
    public static final String VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO = "SISTEMAPAGAMENTI_ATTIVO";
    /**
     * Parametro NUMERODOCUMENTO della verticalizzazione SISTEMAPAGAMENTI_ATTIVO
     */
    public static final String VERTICALIZZAZIONE_SISTEMAPAGAMENTI_NUMERODOCUMENTO = "NUMERODOCUMENTO";
    /**
     * Parametro TIPOPAGAMENTO che indica il sistema di pagamento adottato
     */
    public static final String VERTICALIZZAZIONE_SISTEMAPAGAMENTI_TIPOPAGAMENTO = "TIPOPAGAMENTO";
    /**
     * TIPOPAGAMENTO adottato è REGULUS
     */
    public static final String VERTICALIZZAZIONE_SISTEMAPAGAMENTI_TIPOPAGAMENTO_REGULUS = "REGULUS";
    /**
     * Modulo delle verticalizzazioni SIT_ATTIVO
     */
    public static final String VERTICALIZZAZIONE_SIT_ATTIVO = "SIT_ATTIVO";
    /**
     * Parametro TIPOSIT della verticalizzazione SIT_ATTIVO
     */
    public static final String VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT = "TIPOSIT";
    /**
     * Parametro URL_WSSIT della verticalizzazione SIT_ATTIVO che indica quale url chiamare
     */
    public static final String VERTICALIZZAZIONE_SIT_ATTIVO_URL_WSSIT = "URL_WSSIT";
    /**
     * Modulo delle verticalizzazioni SIT_NAUTIUS
     */
    public static final String VERTICALIZZAZIONE_SIT_NAUTILUS = "SIT_NAUTILUS";
    public static final String VERTICALIZZAZIONE_SIT_URLCARTSTRADARIO = "URLCARTSTRADARIO";
    public static final String VERTICALIZZAZIONE_SIT_URLRUESTRADARIO = "URLRUESTRADARIO";
    public static final String VERTICALIZZAZIONE_SIT_URLPOCSTRADARIO = "URLPOCSTRADARIO";
    public static final String VERTICALIZZAZIONE_SIT_URLCARTMAPPALE = "URLCARTMAPPALE";
    public static final String VERTICALIZZAZIONE_SIT_URLRUEMAPPALE = "URLRUEMAPPALE";
    public static final String VERTICALIZZAZIONE_SIT_URLPOCMAPPALE = "URLPOCMAPPALE";
    /**
     * Modulo delle verticalizzazioni SIT_QUAESTIOFLORENZIA
     */
    public static final String VERTICALIZZAZIONE_SIT_QUAESTIOFLORENZIA = "SIT_QUAESTIOFLORENZIA";
    public static final String VERTICALIZZAZIONE_SIT_QUAESTIOFLORENZIA_URLCARTSTRADARIO = "URLCARTSTRADARIO";
    public static final String VERTICALIZZAZIONE_SIT_QUAESTIOFLORENZIA_URLCARTMAPPALE = "URLCARTMAPPALE";
    public static final String VERTICALIZZAZIONE_SIT_QUAESTIOFLORENZIA_ATTIVA_LINK_CARTOGRAFICO = "ATTIVA_LINK_CARTOGRAFICO";
    public static final String VERTICALIZZAZIONE_SIT_QUAESTIOFLORENZIA_URL_LINK_CARTOGRAFICO = "URL_LINK_CARTOGRAFICO";
    public static final String VERTICALIZZAZIONE_SIT_QUAESTIOFLORENZIA_URL_URL_BASE_LINK_BACK = "URL_BASE_LINK_BACK";
    /**
     * Modulo delle verticalizzazioni SIT_SIT_CTC
     */
    public static final String VERTICALIZZAZIONE_SIT_CTC = "SIT_CTC";
    public static final String VERTICALIZZAZIONE_SIT_CTC_URLCARTMAPPALE = "URLCARTMAPPALE";
    /**
     * Modulo delle verticalizzazioni SIT_SIT_7DBTL
     */
    public static final String VERTICALIZZAZIONE_SIT_7DBTL = "SIT_7DBTL";
    public static final String VERTICALIZZAZIONE_SIT_7DBTL_URLCARTSTRADARIO = "URLCARTSTRADARIO";
    public static final String VERTICALIZZAZIONE_SIT_7DBTL_URLCARTMAPPALE = "URLCARTMAPPALE";
    /**
     * Modulo delle verticalizzazioni SIT_SIT_INITMAPGUIDE
     */
    public static final String VERTICALIZZAZIONE_SIT_INITMAPGUIDE = "SIT_INITMAPGUIDE";
    public static final String VERTICALIZZAZIONE_SIT_INITMAPGUIDE_URLCARTSTRADARIO = "URLCARTSTRADARIO";
    /**
     * Modulo delle verticalizzazioni SIT_LDP
     */
    public static final String VERTICALIZZAIONE_SIT_LDP = "SIT_LDP";
    public static final String VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA = "URL_SERVIZIO_DOMANDE";
    public static final String VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_NSPACE = "URL_SERVIZIO_DOMANDE_NSPACE";
    public static final String VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_SNAME = "URL_SERVIZIO_DOMANDE_SNAME";
    public static final String VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_PNAME = "URL_SERVIZIO_DOMANDE_PNAME";
    public static final String VERTICALIZZAZIONE_SIT_LDP_URL_PRESENTAZIONE_DOMANDA_MNAME = "URL_SERVIZIO_DOMANDE_MNAME";
    public static final String VERTICALIZZAZIONE_SIT_LDP_PASSWORD = "PASSWORD";
    public static final String VERTICALIZZAZIONE_SIT_LDP_USERNAME = "USERNAME";
    public static final String VERTICALIZZAZIONE_SIT_LDP_NODI = "NODI_SERVIZIO_DOMANDE";
    public static final String VERTICALIZZAZIONE_SIT_LDP_URL_RITORNO_PRATICA_GIS = "URL_RITORNO_PRATICA_GIS";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_MODELLIT = "OSP_DYN2_MODELLIT";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_MODELLIT_RIPET = "OSP_DYN2_MODELLIT_RIPET";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_DATA = "OSP_DYN2_INIZIO_DATA";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_INIZIO_ORA = "OSP_DYN2_INIZIO_ORA";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_FINE_DATA = "OSP_DYN2_FINE_DATA";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_FINE_ORA = "OSP_DYN2_FINE_ORA";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_DETT_AREE_OCCUPATE = "OSP_DYN2_DETT_AREE_OCCUPATE";
    public static final String VERTICALIZZAZIONE_SIT_LDP_OSP_DYN2_OSP_FREQ_OCCUPAZ = "OSP_DYN2_OSP_FREQ_OCCUPAZ";
    public static final String VERTICALIZZAZIONE_SIT_LDP_GESTISCI_MOD_DEL_AREE = "GESTISCI_MOD_DEL_AREE";

    public enum LDP_DECODIFICHE_CONTESTI {
	OCCUPAZIONE,
	PERIODO,
	GEOMETRIA
    }

    /**
     * Costanti per la gestione dei paramentri del paramentro 'VERTICALIZZAZIONE_SIT_LDP_URL_RITORNO_PRATICA_GIS'
     */
    public static final String SIT_LDP_URL_RITORNO_PRATICA_GIS_RIF_PRATICA = "<RIF_PRATICA>";
    public static final String SIT_LDP_URL_RITORNO_PRATICA_GIS_TOKEN = "<TOKEN>";
    /**
     * Se attiva consente di accedere alle funzionalità di Sorteggio L.R. 15/2013 Emilia Romagna
     */
    public static final String VERTICALIZZAZIONE_SORTEGGI = "SORTEGGI";
    public static final String VERTICALIZZAZIONE_PARAM_LR_152013_ER = "LR_152013_ER";
    /**
     * Modulo verticalizzazione SORTEGGIORAPIDO Se attiva consente all'istanza di accedere alle funzionalità di
     * SORTEGGIO RAPIDO, contiene dei parametri. La funzionalità nasconde tutti i pulsanti dell'istanza (tranne SALVA,
     * SCHEDE, ELIMINA e CHIUDI) fino a che il valore del campo dinamico specificato nel parametro CAMPO_DYN_SORTEGGIATA
     * non è diverso da "".
     */
    public static final String VERTICALIZZAZIONE_SORTEGGIORAPIDO = "SORTEGGIORAPIDO";
    /**
     * Indica il valore del campo NOMECAMPO presente dentro la tabella DYN2_CAMPI che fa riferimento al SORTEGGIORAPIDO
     */
    public static final String VERTICALIZZAZIONE_SORTEGGIORAPIDO_CAMPO_DYN_SORTEGGIATA = "CAMPO_DYN_SORTEGGIATA";
    /**
     * Se 0 allora il pulsante SCHEDE è visualizzato solo dopo la protocollazione dell'istanza, altrimenti il pulsante
     * SCHEDE è sempre visibile.
     */
    public static final String VERTICALIZZAZIONE_SORTEGGIORAPIDO_MOSTRA_SCHEDE_PROT = "MOSTRA_SCHEDE_PROT";
    /**
     * Modulo STC
     */
    public static final String VERTICALIZZAZIONE_STC = "STC";
    public static final String VERTICALIZZAZIONE_STC_USA_NUMISTANZA_ALTRO_SISTEMA = "USA_NUMISTANZA_ALTRO_SISTEMA";
    public static final String VERTICALIZZAZIONE_STC_USA_NUMISTANZA_ALTRO_SISTEMA_NODI = "USA_NUMISTANZA_AS_NODI";
    public static final String VERTICALIZZAZIONE_STC_TIPOMOV_DEFAULT_SOGG_ESTERNI = "TIPOMOV_DEFAULT_SOGG_ESTERNI";
    public static final String VERTICALIZZAZIONE_STC_TIPO_SOGGETTO_DEFAULT = "TIPO_SOGGETTO_DEFAULT";
    public static final String VERTICALIZZAZIONE_STC_ATTIVA_LETTURA_SCHEDE_DA_PDF = "ATTIVA_LETTURA_SCHEDE_DA_PDF";
    public static final String VERTICALIZZAZIONE_STC_NODI_NON_SOVR_DESC_ATTIVITA = "NODI_NON_SOVR_DESC_ATTIVITA";
    public static final String VERTICALIZZAZIONE_STC_LISTA_NODI_IA_NON_PROTOCOLLA = "LISTA_NODI_IA_NON_PROTOCOLLA";
    public static final String VERTICALIZZAZIONE_STC_IPRA_PROT_PRIMA_DI_INSERIRE = "IPRA_PROT_PRIMA_DI_INSERIRE";
    public static final String VERTICALIZZAZIONE_STC_IATT_PROT_PRIMA_DI_INSERIRE = "IATT_PROT_PRIMA_DI_INSERIRE";
    public static final String VERTICALIZZAZIONE_STC_IPRA_PROT_MAILTIPOOGGETTO = "IPRA_PROT_MAILTIPOOGGETTO";
    public static final String VERTICALIZZAZIONE_STC_IATT_PROT_MAILTIPOOGGETTO = "IATT_PROT_MAILTIPOOGGETTO";
    public static final String VERTICALIZZAZIONE_STC_NLA_IDNODO_ENTE_NON_LOCALE = "NLA_IDNODO_ENTE_NON_LOCALE";
    public static final String VERTICALIZZAZIONE_STC_AMM_DEST_INVIA_ZIP_PRATICA = "AMM_DEST_INVIA_ZIP_PRATICA";
    public static final String VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA = "DIS_MOD_DATA_NOTIFICA";
    public static final String VERTICALIZZAZIONE_STC_NLA_IDNODO_INFOCAMERE = "NLA_IDNODO_INFOCAMERE";
    public static final String VERTICALIZZAZIONE_STC_INSERISCI_FILE_ASSERZIONE_SAML = "INSERISCI_FILE_ASSERZIONE_SAML";
    public static final String VERTICALIZZAZIONE_SUAPER = "SUAPER";
    public static final String VERTICALIZZAZIONE_SUAPER_CODICEINTERVENTO_DEFAULT = "CODICEINTERVENTO_DEFAULT";
    public static final String VERTICALIZZAZIONE_SUAPER_LISTA_NODI_GRUPPIINTERVENTI = "LISTA_NODI_GRP_SMIST";
    public static final String VERTICALIZZAZIONE_SUAPER_VECCHIA_LOGICA_SE_UN_ENDO = "VECCHIA_LOGICA_SE_UN_ENDO";
    /**
     * Parametri della verticalizzazione di inserimento di una pratica da un sistema esterno
     */
    public static final String VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO = "PRATICA_DA_SISTEMA_ESTERNO";
    public static final String VERTICALIZZAZIONE_IDNODO_INFOCAMERE = "IDNODO_INFOCAMERE";
    public static final String VERTICALIZZAZIONE_NLA_IDNODO = "NLA_IDNODO";
    /**
     * Parametro della verticalizzazione STC che rappresenta la lista degli idnodi (separata da virgola ,) per i quali
     * attivare la regola di non aggiornare i campi della scheda anagrafica eventualmente trovati
     */
    public static final String VERTICALIZZAZIONE_STC_LISTA_NODI_NON_AGG_ANAGRAFE = "LISTA_NODI_NON_AGG_ANAGRAFE";
    /**
     * Parametro della verticalizzazione STC che rappresenta la lista degli idnodi (separata da virgola ,)i quali non si
     * vogliono inserire automaticamente nell'istanza gli oneri associati ad un endo . Es. 400,420,430
     */
    public static final String VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_NO_ONERI_ENDO = "LISTA_NODI_MITT_NO_ONERI_ENDO";
    /**
     * Parametro della verticalizzazione STC che rappresenta la lista degli idnodi (separata da virgola ,) per i quali
     * attivare la regola dell'elenco dei nodi destinatari ai quali inviare gli allegati fisici invece dei riferimenti.
     * Es. 410,420,430
     */
    public static final String VERTICALIZZAZIONE_STC_LISTA_LISTA_NODI_MITT_GET_ALLEGATI = "LISTA_NODI_MITT_GET_ALLEGATI";
    /**
     * Parametro della verticalizzazione STC che rappresenta la lista degli idnodi (separata da virgola ,) per i quali
     * attivare la regola dell'elenco dei nodi MITTENTI DAI QUALI SCARICARE SUBITO GLI ALLEGATI PASSATI COME
     * RIFERIMENTO. ES: AIDA-SMART Es. 410,420,430
     */
    public static final String VERTICALIZZAZIONE_STC_LISTA_NODI_MITT_DOWNLOAD_ALLS = "LISTA_NODI_MITT_DOWNLOAD_ALLS";
    /**
     * 'Indica il tempo di attesa in millisecondi per l''attivazione del componente che esegue le notifica automatiche
     * in maniera asincrona. Se non specificato o non valido di default è 20000 (20 secondi)
     */
    public static final String VERTICALIZZAZIONE_STC_NOTIFICHE_AUT_ATTESA_MILLIS = "NOTIFICHE_AUT_ATTESA_MILLIS";
    /**
     * Indica il tempo di attesa in millisecondi per la connessione del nodo di backoffice con il nodo STC. Se non
     * specificato o non valido di default è 120000 (120 secondi)
     */
    public static final String VERTICALIZZAZIONE_STC_TIMEOUT_CONNESSIONE = "TIMEOUT_CONNESSIONE";
    /**
     * Indica il tempo di attesa in millisecondi per la risposta del nodo di STC alla chiamata del nodo di backoffice.
     * Se non specificato o non valido di default è 120000 (120 secondi)
     */
    public static final String VERTICALIZZAZIONE_STC_TIMEOUT_RISPOSTA = "TIMEOUT_RISPOSTA";
    /**
     * Id del nodo NLA BACKOFFICE registrato su STC
     */
    public static final String VERTICALIZZAZIONE_STC_NLA_IDNODO = "NLA_IDNODO";
    /**
     * Se "S" allora in STC.inserimentopratica e STC.inserimentoattività non vengono copiati i riferimenti di
     * protocollazione causando la chiamata a protocollaistanza, protocollamovimento
     * 
     */
    public static final String VERTICALIZZAZIONE_STC_FORZAPROTOCOLLAZIONE = "FORZA_PROTOCOLLAZIONE";
    /**
     * Riporta la lista dei nodi per i quali copiare i riferimenti di protocollo se stessa direzione. Il comportamento
     * viene sovrascritto da FORZA_PROTOCOLLAZIONE. La lista dei nodi è separata da virgola (,) es 320,150
     * 
     */
    public static final String VERTICALIZZAZIONE_STC_LISTANODI_COPIA_PROTOCOLLO = "LISTANODI_COPIA_PROTOCOLLO";
    /**
     * Modulo verticalizzazione VIS_ONERIIMPORTOISTRUTTORIA
     */
    public static final String VERTICALIZZAZIONE_VIS_ONERIIMPORTOISTRUTTORIA = "VIS_ONERIIMPORTOISTRUTTORIA";
    /**
     * Modulo verticalizzazione DIS_ONERIIMPORTOISTRUTTORIA
     */
    public static final String VERTICALIZZAZIONE_DIS_ONERIIMPORTOISTRUTTORIA = "DIS_ONERIIMPORTOISTRUTTORIA";
    /**
     * Se abilitato al momento dell'upload del file il nome del file caricato sarà modificato in
     * IDCOMUNE_I&lt;CODICEISTANZA&gt;_O&lt;CODICEOGGETTO&gt;_&lt;nomefilecaricato&gt;. La sezione
     * I&lt;CODICEISTANZA&gt; comparirà solamente se relativi a file provenienti da istanze, ossia se sulla servlet che
     * carica i file è specificato il parametro codiceistanza. La seguente regola non è valida se attiva la
     * verticalizzazione FILESYSTEM
     */
    public static final String VERTICALIZZAZIONE_STANDARD_NOMENCLATURA_OGGETTI = "STANDARD_NOMENCLATURA_OGGETTI";
    /**
     * Nome della verticalizzazione che indica se l'installazione è di tipo ENTERPRISE o STANDARD
     */
    public static final String VERTICALIZZAZIONE_TIPO_INSTALLAZIONE = "TIPO_INSTALLAZIONE";
    /**
     * Nome del parametro della verticalizzazione che indica se l'installazione è di tipo ENTERPRISE o STANDARD
     */
    public static final String VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO = "TIPO";
    /**
     * Valore del parametro che indica se l'installazione è di tipo ENTERPRISE
     */
    public static final String VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_ENTERPRISE = "ENTERPRISE";
    /**
     * Valore del parametro che indica se l'installazione è di tipo STANDARD
     */
    public static final String VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_STANDARD = "STANDARD";
    /**
     * La lista degli identificativi dei menu, separati da virgola per i quali prendere il link ENTERPRISE al posto di
     * quello STANDARD
     */
    public static final String VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_OVERRIDE_MENU_STANDARD = "OVERRIDE_MENU_STANDARD";
    /**
     * Nome del parametro della verticalizzazione che indica se la pagina PER LA STAMPA DEI DOCUMENTI TIPO utilizza
     * l'implementazione JAVA o MICROSOFT
     */
    public static final String VERTICALIZZAZIONE_PARAM_PAGINA_DOCTIPO = "STAMPE_DOCTIPO";
    /**
     * Costanti per la gestione della verticalizzazione ALLEGATI_PEC
     */
    public static final String VERTICALIZZAZIONE_ALLEGATI_PEC = "ALLEGATI_PEC";
    public static final String VERTICALIZZAZIONE_PARAMETRI_INVIA_DOC_COME_LINK_IN_PROT = "INVIA_DOC_COME_LINK_IN_PROT";
    public static final String VERTICALIZZAZIONE_PARAMETRI_URL_SERVIZIO_RECUPERO_DOC = "URL_SERVIZIO_RECUPERO_DOC";
    public static final String VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN = "DOWNLOAD_SENZA_PIN";
    /**
     * Il valore nella colonna CLMENU_JAVA che indica se l'installazione è di tipo ENTERPRISE
     */
    public static final String CLMENU_JAVA_TIPO_FUNZIONALITA_ENTERPRISE = "E";
    /**
     * Il valore nella colonna CLMENU_JAVA che indica se l'installazione è di tipo STANDARD
     */
    public static final String CLMENU_JAVA_TIPO_FUNZIONALITA_STANDARD = "S";
    /**
     * Se attiva, nella pagina dell'inserimento dell'anagrafe, sarà visibile un pulsante per la ricerca di informazioni
     * relative a una specifica partita iva o codice fiscale. Le informazioni lette dal WS(specificato nei parametri)
     * saranno riportate nel form di inserimento
     */
    public static final String VERTICALIZZAZIONE_WSANAGRAFE = "WSANAGRAFE";
    public static final String ESCLUDI_RICERCA_PER_PF = "ESCLUDI_RICERCA_PER_PF";
    /**
     * parametri per la gestione del ws Parix (Visura imprese nazionale)
     */
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE = "WSANAGRAFE_PARIX";
    /**
     * parametri per la gestione del ws Parix Cloud(Visura imprese nazionale)
     */
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_PARIX_GATE_CLOUD = "WSANAGRAFE_PARIX_CLOUD";
    /**
     * parametri per la gestione del ws Adrier (Visura imprese dell'emilia romagna)
     */
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_ADRIER = "WSANAGRAFE_ADRIER";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_PASSWORD = "PASSWORD";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_USER = "USER";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_URL = "URL";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_CERCA_SOLO_CF = "CERCA_SOLO_CF";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_SWITCHCONTROL = "SWITCHCONTROL";
    /**
     * 
     */
    public static final String VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE = "RIC_ANAG_COLLEGATE";
    public static final String VERTICALIZZAZIONE_RIC_ANAG_COLLEGATE_COMPONENTE = "COMPONENTE";
    public static final String VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI = "RIC_ANAG_COLL_CSI";
    public static final String VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI_URL_WS = "URL_WS";
    public static final String VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI_WS_TIMEOUT = "WS_TIMEOUT";
    public static final String VERTICALIZZAZIONE_RIC_ANAG_COLL_CSI_DECODIFICHE_TABELLA_RAGGRUPP_LR = "DECODIFICHE_TABELLA_RAGGRUPP_LR";
    /**
     * Parametro che se popolato sul DB va a sovrascrivere il link che invoca il WS Anagrafe dotNet con quello passato
     */
    // Obsoleto
    public static final String WS_ANAGRAFE_URL = "URL";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PF = "URL_RICERCA_PF";
    public static final String VERTICALIZZAZIONE_WSANAGRAFE_URL_RICERCA_PG = "URL_RICERCA_PG";
    /**
     * Nome della verticalizzazione per l'anagrafe e nome dei parametri della verticalizzaione
     */
    public static final String VERTICALIZZAZIONE_ANAGRAFE = "ANAGRAFE";
    public static final String ANAGRAFE_PG_TO_PF = "ANAGRAFE_PG_TO_PF";
    /**
     * Nome valori dei parametri della verticalizzazione PEOPLE
     */
    public static final String PEOPLE_ID_SPORTELLO = "PEOPLE_ID_SPORTELLO";
    public static final String PEOPLE_ID_ENTE = "PEOPLE_ID_ENTE";
    /**
     * Nome valori dei parametri della verticalizzazione AREA RISERVATA
     */
    public static final String AREARISERVATA_ID_ENTE = "AREARISERVATA_ID_ENTE";
    public static final String AREARISERVATA_ID_SPORTELLO = "AREARISERVATA_ID_SPORTELLO";
    /**
     * Suffisso dell'id sportello per le chiamate STC che partono da PEC_CLIENT (idsportello = <software> + "#PEC#")
     */
    public static final String PEC_CLIENT_IDSPORTELLO_SUFFIX = "#PEC#";
    /**
     * Suffisso dell'id sportello per le chiamate STC che partono dalla funzionalità AZIONI PROTOCOLLO (idsportello =
     * <software> + "#PROTO#")
     */
    public static final String AZIONI_PROTOCOLLO_CLIENT_IDSPORTELLO_SUFFIX = "#PROTO#";
    public static final String AZIONI_PROTOCOLLO_SUAP_IN_RETE_CLIENT_IDSPORTELLO_SUFFIX = "#PRINRE#";
    public static final String VERTICALIZZAZIONE_NLA_ATTI = "NLA-ATTI";
    public static final String VERTICALIZZAZIONE_NLA_ATTI_PARAMETRI_URL_NLA_ATTI_WS = "URL_NLA_ATTI_WS";
    /**
     * Verticalizzazione FVG_SOL
     */
    public static final String VERTICALIZZAZIONE_FVG_SOL = "FVG_SOL";
    /**
     * Parametri Verticalizzazione FVG_SOL
     */
    public static final String VERTICALIZZAZIONE_FVG_SOL_WEB_SERVICE_PASSWORD = "WEB_SERVICE_PASSWORD";
    public static final String VERTICALIZZAZIONE_FVG_SOL_WEB_SERVICE_USERNAME = "WEB_SERVICE_USERNAME";
    public static final String VERTICALIZZAZIONE_FVG_SOL_WEB_SERVICE_URL = "WEB_SERVICE_URL";
    public static final String VERTICALIZZAZIONE_FVG_SOL_USA_BACKOFFICE_COME_CONSOLE = "USA_BACKOFFICE_COME_CONSOLE";
    public static final String VERTICALIZZAZIONE_FVG_SOL_TEMPIFICAZIONE_ENDO_DEFAULT = "TEMPIFICAZIONE_ENDO_DEFAULT";
    public static final String VERTICALIZZAZIONE_FVG_SOL_AMMINISTRAZIONE_ENDO_DEFAULT = "AMMINISTRAZIONE_ENDO_DEFAULT";
    public static final String VERTICALIZZAZIONE_FVG_SOL_CODICENATURA_ENDO_DEFAULT = "CODICENATURA_ENDO_DEFAULT";
    /**
     * Verticalizzazione FVG_SUAP_IN_RETE
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE = "FVG_SUAP_IN_RETE";
    /**
     * Parametro della Verticalizzazione FVG_SUAP_IN_RETE che permette di il valore del nodo
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_NODO_SUAP_INRETE = "NLA_NODO_SUAP_INRETE";
    /**
     * Parametro della Verticalizzazione FVG_SUAP_IN_RETE configura l'analisi dei file xml neidocumenti della pratica
     * per trasformli in schede dell'istanza
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ELABORA_DOCUMENTI_XML = "ELABORA_DOCUMENTI_XML";
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ELABORA_DOCUMENTI_XML_VALORI_S = "S";
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ELABORA_DOCUMENTI_XML_VALORI_N = "N";
    /**
     * Paramatro che in caso di ELABORA_DOCUMENTI_XML == S ci permette se posto a 1 di allineare le schede della console
     * SOL con le schede del backoffici in cui stiamo inserendo la pratica
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_ALLINEA_SCHEDE = "ALLINEA_SCHEDE";
    /**
     * Codice del procedimento generico che viene associato ai procedimenti presenti nella pratica suap in rete che non
     * comportano la creazione della pratica in VBG
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_COD_PROC_GENERICO = "COD_PROC_GENERICO";
    /**
     * Parametro per impostare il comportamento di spacchettamento pratica. Se attivo verrano create N pratiche quanti
     * sono i procedimenti collegati al commercio. Può assumere i valori 1 : ATTIVO o 0(NULL) : NON ATTIVO
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_SPACCHETTA_PRATICA_CO = "SPACCHETTA_PRATICA_CO";
    /**
     * gestisce l'attivazione del comportamento di copiare le schede dell'istanza nelle schede delle attività in fase di
     * creazione attività o aggiunta di istanza in attività
     */
    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_COLL_SCHEDE_ISTAN_IN_ATTIVITA = "COLL_SCHEDE_ISTAN_IN_ATTIVITA";
    /**
     * Verticalizzazioni per la gestione del comportamento del del componenete di firma
     */
    public static final String VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA = "COMPORTAMENTO_COMPONENTE_FIRMA";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_CONVERTI_IN_PDF = "CONVERTI_IN_PDF";
    public static final String VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_FIRMA_CADES_NO_EXT_MULTI_P7M = "FIRMA_CADES_NO_EXT_MULTI_P7M";
    /**
     * verticalizzazione per l'attivazione delle funzionalità enti terzi
     */
    public static final String VERTICALIZZAZIONE_SCRIVANIA_ENTI_TERZI = "SCRIVANIA_ENTI_TERZI";
    /**
     * Parametri fissi della funzionalità Tipi procedure, gestiscono le varie possibilità modalità di determinazione
     * inzio di un istanza
     * 
     */
    public static final String PD = "PD";
    public static final String UT = "UT";
    public static final String PG = "PG";
    /**
     * Parametri fissi della funzionalità Tipi procedure, gestiscono le varie possibilità modalità di Determinazione
     * della data di validità dell'istanza
     * 
     */
    public static final String MS = "MS";
    public static final String NC = "NC";
    public static final String MA = "MA";
    public static final String DP = "DP";
    /**
     * Parametri fissi della funzionalità Tipi procedure gestione allegati, parametri fissi che vengono presentati sulla
     * jsp per scegliere che formato verrà presentato sul frontoffice dell'allegato inserito
     * 
     */
    public static final String PDF = "pdf";
    public static final String RTF = "rtf";
    public static final String DOC = "doc";
    public static final String OPN = "opn";
    /**
     * Parametri fissi utilizzati nel TipoanagrafeFilterMatcher (filtro di jmesa) per fare il controllo se stiamo
     * cercando di filtrare idati che rappresentano un anagrafica di tipo giuridico o fisico
     * 
     */
    public static final String PERSONA_FISICA = "F";
    public static final String PERSONA_GIURIDICA = "G";
    /**
     * Parametri fissi utilizzati nelle jsp per selezionare il tipo di sesso (Maschio o Femmina)
     * 
     */
    public static final String MASCHIO = "M";
    public static final String FEMMINA = "F";
    /**
     * Parametri fissi utilizzati nelle jsp per selezionare il tipo di di categoria di scadenza
     * 
     */
    public static final String SCADENZA = "S";
    public static final String AVVISO = "A";
    public static final String INTERDETTI = "I";
    /**
     * Parametri fissi utilizzati nelle jsp. S sta per si, N sta per no
     * 
     */
    public static final String S = "S";
    public static final String N = "N";
    public static final String CONF_UTENTE_AZIONI_PROTOCOLLO_INVIAALLEGATI = "FX_AZIONI_PROTOCOLLO_INVIAALLEGATI";
    public static final String CONF_UTENTE_AZIONI_PROTOCOLLO_SCOMPATTAALLEGATICOMPRESSI = "FX_AZIONI_PROTOCOLLO_SCOMPATTAALLEGATICOMPRESSI";
    /**
     * Parametri di configurazione utente per la funzionalità alberoproc
     */
    public static final String CONF_UTENTE_ALBEROPROC_LIST_ATECO = "CONF_UTENTE_ALBEROPROC_LIST_ATECO";
    public static final String CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE = "CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE";
    /**
     * Parametri di configurazione utente per la funzionalità di anagrafe
     */
    public static final String CONF_UTENTE_ANAGRAFE_ALTRI_DATI = "CONF_UTENTE_ANAGRAFE_ALTRI_DATI";
    public static final String CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA = "CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA";
    public static final String CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA = "CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA";
    public static final String CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE = "CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE";
    public static final String CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE = "CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE";
    /**
     * Parametri di configurazione utente per la funzinalita configurazione backoffice
     */
    public static final String CONF_UTENTE_INTESTAZIONI_COMUNIASSOCIATI = "CONF_UTENTE_INTESTAZIONI_COMUNIASSOCIATI";
    public static final String CONF_UTENTE_GESTIONE_ISTANZE_NUMERAZIONE = "CONF_UTENTE_GESTIONE_ISTANZE_NUMERAZIONE";
    public static final String CONF_UTENTE_GESTIONE_ISTANZE_GENERALE = "CONF_UTENTE_GESTIONE_ISTANZE_GENERALE";
    public static final String CONF_UTENTE_GESTIONE_ISTANZE_PROTOCOLLO = "CONF_UTENTE_GESTIONE_ISTANZE_PROTOCOLLO";
    public static final String CONF_UTENTE_VARIE = "CONF_UTENTE_VARIE";
    /**
     * parametro della configurazione utente che memorizza il numero di record da visualizzare su ogni pagina delle
     * liste
     */
    public static final String CONF_UTENTE_NUMERO_RECORD_LISTE = "NUMRECORDLISTE";
    /**
     * Parametri di configurazione utente per la funzionalità Gestione ricerche (Es. sulla pagina ricerca istanze)
     */
    public static final String CONF_UTENTE_GESTIONE_RICERCHE = "CONF_UTENTE_GESTIONE_RICERCHE";
    /**
     * Parametri di configurazione utente per la funzionalità visualizzazioni oneri dell'istanza.
     */
    public static final String CONF_UTENTE_ONERI_RAGGRUPPATI = "ONERI_RAGGRUPPATI";
    /**
     * Stringa fissa che verrà inserita alla descrizione di un endo procedimento, quando questo viene creato come copia
     * di un altro
     */
    public static final String COPIA_PROCEDIMENTO = "COPIA DI ";
    /**
     * Stringa fissa che verrà inserita al codice scheda della scheda dinamica se il campo sarà lasciato vuoto.
     */
    public static final String SCHEDA = "SCHEDA";
    /**
     * Parametri per definire i tipi di campi dinamici che devono essere associati auna scheda dinamica
     */
    public static final String CAMPO_DINAMICO = "CAMPO DINAMICO";
    public static final String CAMPO_TESTO = "CAMPO TESTO";
    /**
     * Numero massimo di record delle ricerche ajax
     */
    public static final int NUM_MAX_RESULTS = 50;
    /**
     * Lunghezza di default per le password: 8 caratteri
     * 
     */
    public static final Integer LUNGHEZZA_PASSWORD = 8;
    /**
     * Parametri per filtrare il contesto le le mail tipo
     */
    public static final String CONFERENZE = "C";
    public static final String MAIL = "M";
    public static final String PARERI = "P";
    /**
     * Parametri utilizzati per historyback alla tabella scadenzario.Codificano i 5 Tab presenti nella pagina
     */
    public static final String TAB_SCADENZARIO = "tabScadenzario";
    public static final String TAB_MOV_NON_LETTI = "tabMovNonLetti";
    public static final String TAB_MOV_STC_NON_NOTIFICATI = "tabMovSTCNonNotificati";
    public static final String TAB_RICHIESTE_FO_NON_NON_LETTE = "tabRichiesteFoNonLette";
    public static final String TAB_EVENTI_NON_LETTI = "tabEventiNonLetti";
    public static final String TAB_ISTANZE_STC = "tabIstanzeStc";
    public static final String TAB_ISTANZE_STC_NON_IMPORTATE = "tabIstanzeStcNonImportate";
    public static final String TAB_EVENTI_SISTEMA = "tabEventiSistema";
    public static final String TAB_DOC_DA_FIRMARE = "tabDocumentiDaFirmare";
    public static final String TAB_SCADENZARIO_ONERI = "tabScadenzarioOneri";
    /**
     * Chiave del resource bundle per indicare che la data del movimento non sia successiva all'eventule movimento di
     * chiusura della procedura
     */
    public static final String ERROR_MOVIMENTO_SUCCESSIVO_CHIUSURA = "service_error.data_movimento_successiva_data_chiusura_istanza";
    public static final int ONERI_COMPORTAMENTO_IMPOSTA_SCADENZA = 1;
    public static final int ONERI_COMPORTAMENTO_RICHIEDE_PAGAMENTO = 2;
    public static final int ONERI_COMPORTAMENTO_INSERISCE_ONERE = 3;
    public static final int ONERI_COMPORTAMENTO_SPOSTA_IMPORTO1_SU_IMPORTO2 = 4;
    public static final String DESCRIZIONE_MOTIVO_INTERDIZIONE = "Interdizione comunicata dalla Questura";
    public static final String PEOPLE = "PEOPLE";
    private static Set<String> codiciInstallazioneMaster = null;
    /**
     * Parametri per filtare i flussi del protocollo
     */
    public static final String FLUSSO_ARRIVO = "A";
    public static final String FLUSSO_INTERNO = "I";
    public static final String FLUSSO_PARTENZA = "P";
    /**
     * Parametro per gestire la Business validation nell' inserimento di un record nella Messaggicfg
     */
    public static final String INVIO_ISTANZA_BACKOFFICE = "AR_INVIO";
    /**
     * Parametro che codifica una tipologia di protocollo utilizzato: SIGEPRO
     * 
     */
    public static final String TIPO_PROTOCOLLO_SIGEPRO = "PROTOCOLLO_SIGEPRO";
    /**
     * Parametri che rappresentano il contesto in cui andiamo a salvare in locale un file (Es. movimenti,doc Istan)
     */
    public static final String CONTESTO_ALLEGATI_DOCUMENTI = "DOCUMENTI";
    public static final String CONTESTO_ALLEGATI_MOVIMENTO = "MOVIMENTI";
    public static final String CONTESTO_ALLEGATI_ENDO = "ENDOPROCEDIMENTI";
    public static final String CONTESTO_ALLEGATI_PROCURE = "ISTANZEPROCURE";
    public static final String CONTESTO_TUTTI = "TUTTI";
    /**
     * Costante che rappresenta il contesto per cui vengono copiate le informazioni dalle istanze collegate
     */
    public static final String CONTESTO_COPIA_DA_ISTAN_COLL_LOCALIZZAZIONE = "COPIA_LOCALIZZAZIONE";
    /**
     * Il parametro indica il numero di istanze che per ciclo possono essere aggiornate quando viene richiamata la
     * funzionalità "Aggiorna" nella procedura. Tale funzionalità va ad effettuare un aggiornamento massivo del campo
     * dataInizio della tabella ISTANZE_TEMPISTICHE in base alla regola stabilita nella procedura.
     */
    public static final Integer NUMERO_ISTANZE_AGGIORNABILI_PER_CICLO_DALLA_PROCEDURA = 100;
    public static final String CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI = "SCADENZARIO_SOLO_SCADENZE_IMPORTANTI";
    public static final String CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA = "SCADENZARIO_ORDINAMENTO_DATA";
    /**
     * Nome della verticalizzazione per abilitare l'import/export dei procedimenti nel repertorio delle Regione Umbria
     * 
     */
    public static final String VERTICALIZZAZIONE_IMPORT_EXPORT_SIVBG = "IMPORT-EXPORT-SIVBG";
    /**
     * Parametro delle verticalizzazione "IMPORT-EXPORT-SIVBG" che contiene la url del web service da invocare per fare
     * import/export
     * 
     */
    public static final String IMPORT_EXPORT_SIVBG_URLWS = "URL_WS_REPERTORIO";
    /**
     * Parametro che mi indica che un se un campo di altri dati deve essere utilizzato per l'importazione dati schede
     * dinamiche ricevute da un movimento da area riservata. Il parametro è utilizzato nel metodo
     * NlaService.importazioneSchedeDinamicheDaMovimentoAreaRiservata(....)
     */
    public static final String FO_DYN2DATO = "FO_DYN2DATO";
    /**
     * Riporta il numero di record che devono essere visualizzati quando si cercano gli endo da attivare all'interno di
     * un istanza
     */
    public static final Integer NUMERO_RECORD_ENDOPROCEDIMENTI_ATTIVABILI_VISIBILI = 2;
    /**
     * Configurazione utente per preselezionare il flag tipologia ripartizione rate nella funzionalità di rateizzazione
     * di una registrazione
     */
    public static final String CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE = "CONTAB_RAT_RIPARTIZIONE";
    /**
     * Costanti per definire il tipo dei schede del cart SCHEDA_ENDO_TIPO_1 E SCHEDA_ENDO_TIPO_2
     */
    public static final String SCHEDA_TIPO_ENDO1 = "SET1";
    public static final String SCHEDA_TIPO_ENDO2 = "SET2";
    public static final String CONF_UTENTE_PAGINA_CENTRALE = "_PAGINA_CENTRALE_";
    /**
     * Costante per la gestione della configurazione utente, variabile che gestisce la visualizzazione della lista dei
     * soggetti collegati all'interno della pagina di dettaglio dell'istanza
     */
    public static final String CONF_UTENTE_MOSTRA_SOGG_COLL_ISTANZA = "MOSTRA_SOGG_COLL_ISTANZA";
    public static final String VERTICALIZZAZIONE_STC_MAPPATURE_SCHEDE = "MAPPATURE_SCHEDE";
    public static final String VERTICALIZZAZIONE_STC_PRATICHE_COLLEGATE_INSERISCI_STESSO_ISTRUTTORE = "PRAT_COL_INS_STESSO_ISTRUTTO";
    public static final String VERTICALIZZAZIONE_PAYER_ADAPTER = "PAYER_ADAPTER";
    /**
     * Parametro per la gestione della visualizzazione di tutti i campi o solamente del set ristretto dell'insermento
     * rapido di una istanza
     */
    public static final String ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI = "ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI";
    public static final String VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO_FO = "SISTEMAPAGAMENTI_ATTIVO_FO";
    public static final String VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO_FO_TIPOSISTEMA = "TIPOSISTEMA";
    /**
     * Variabili per la gestione delle comunicazioni delle graduatori bandi
     */
    public static final String COMUNICAZIONE_TUTTI_SOGGETTI_GRADUATORIA = "T";
    public static final String COMUNICAZIONE_SOLO_TITOLARI_CONCESSIONE = "C";
    public static final String COMUNICAZIONE_SOLO_NON_TITOLARI_CONCESSIONI = "N";
    /**
     * Modulo verticalizzazione pec inbox
     */
    public static final String VERTICALIZZAZIONE_PEC_CLIENT = "PEC_CLIENT";
    /**
     * verticalizzazione pec inbox - richiedente di default per creazione istanza da PEC
     */
    public static final String VERTICALIZZAZIONE_PEC_CLIENT_RICHIEDENTE_DEFAULT = "RICHIEDENTE_DEFAULT";
    /**
     * Variabili utlizzati per lo stato della funzionalità "Metti alla firma"
     */
    public static final String DOCUMENTI_DA_FIRMARE_ERRORE_CANCELLAZIONE = "service_error.documenti_da_firmare.errore_cancellazione_documento_da_firmare";
    public static final String ALERT_NON_POSSO_FIRMARE_MODELLI_MULTIPLI = "service_error.non_e_possibile_firmare_modelli_multipli";
    public static final String VERTICALIZZAZIONE_WSDURC = "WSDURC";
    public static final String VERTICALIZZAZIONE_WSDURC_SERVIZIO_ATTIVO = "SERVIZIO_ATTIVO";
    public static final String VERTICALIZZAZIONE_WSDURC_INFODURC = "WSDURC_INFODURC";
    //public static final String VERTICALIZZAZIONE_WSDURC_INFODURC_ = "INSTITUTECODE";
    public static final String VERTICALIZZAZIONE_WSDURC_INFODURC_INSTITUTE_CODE = "INSTITUTECODE";
    public static final String VERTICALIZZAZIONE_WSDURC_INFODURC_SOFTWARE_CODE_ID = "SOFTWARECODEID";
    public static final String VERTICALIZZAZIONE_WSDURC_INFODURC_USERID = "USERID";
    public static final String VERTICALIZZAZIONE_WSDURC_INFODURC_USERPASSWORD = "USERPASSWORD";
    public static final String VERTICALIZZAZIONE_WSDURC_INFODURC_URLWS_SERVIZIODURC = "URLWS_SERVIZIODURC";
    public static final String VERTICALIZZAZIONE_WSDURC_TIPODOC_ANAGRAFE = "TIPODOC_ANAGRAFE";
    /**
     * verticalizzazione per l'integrazione con il sistema di archiviazione documentale LegalDoc Folders di Infocert
     */
    public static final String VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC = "ARCHIVIAZIONE_DOC_LEGALDOC";
    public static final String VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC_ALGORITMO_ARCHIVIAZIONE = "ALGORITMO_ARCHIVIAZIONE";
    public static final String ALGORITMO_ARCHIVIAZIONE_MULTI_ISTANZE_LEGALDOC = "ARCHIVIAZIONE_MULTI_ISTANZE";
    public static final String ALGORITMO_ARCHIVIAZIONE_PER_OGGETTO_LEGALDOC = "ARCHIVIAZIONE_PER_OGGETTO";
    public static final String VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC_SOLO_FIRMATI = "SOLO_FIRMATI";
    /**
     * verticalizzazione per l'integrazione con il sistema DOSSIER
     */
    public static final String VERTICALIZZAZIONE_DOSSIER = "DOSSIER";
    public static final String VERTICALIZZAZIONE_DOSSIER_URL_NODO_DOSSIER = "URL_NODO_DOSSIER";
    /**
     * verticalizzazione per mantenere la retrocompatibilità di stampe ed exoprt, se attiva le modalità di stampa
     * statistiche e esportazione statistiche utilizzeranno la vecchia modalità
     */
    public static final String VERTICALIZZAIONE_OBS_EXPORT = "OBS_EXPORT";
    /**
     * Variabili per la gestione di alberoprocProtocolloHelper
     */
    public static final String PROTOCOLLAZIONE = "PROTOCOLLAZIONE";
    public static final String FASCICOLAZIONE = "FASCICOLAZIONE";
    /**
     * Variabile che rappresenta il metadato che blocca un oggetto
     */
    public static final String TAG_LOCKED_FILE = "#INIT-FILE-LOCKED-BY#";
    /**
     * Variabile che rappresenta il segnaposto nelle mail tipo, per inserire i link dei documenti sul corpo della mail.
     */
    public static final String LINKALLEGATI = "[LINKALLEGATI]";
    /**
     * Variabile che rappresenta il segnaposto nelle email, per inserire il link dello zip logico sul corpo della email.
     */
    public static final String LINKZIPLOGICO = "[LINKZIPLOGICO]";
    /**
     * COSTANTI CHE RIPORTANI I VALORI POSSIBILI PER LA TABELLA DI BASE DYN2_BASECONTESTI
     */
    public static final String DYN2_BASECONTESTI_ISTANZA = "IS";
    public static final String DYN2_BASECONTESTI_ATTIVITA = "AT";
    public static final String DYN2_BASECONTESTI_ANAGRAFE = "AN";
    /**
     * QUANDO SI RIMUOVONO I FILES UPLOADATI DA UN CAMPO DINAMICO DI TIPO UPLOAD I RECORD IN OGGETTI NON VENGONO
     * CANCELLATI FINO ALLA CONFERMA DEL SALVATAGGIO DELLA SCHEDA PERCIO' ALLA RIMOZIONE VENGONO MEMORIZZATI SESSIONE
     * ASSOCIATI A QUESTA CHIAVE. AL SALVATAGGIO DELLA SCHEDA VENGONO EFFETTIVAMENTE CANCELLATI DAL DB.
     */
    public static final String DYN2_CAMPO_UPLOAD_DELETEFILES_SESSION_KEY = "UPLOADED_FILES_TO_DELETE";
    /**
     * *****************************************************************************************************************
     * *********
     * ********************************************************************************************************
     * ****************** **********Costanti utilizzate per create la coppia chiave valore per il salvataggio delle
     * preferenze applicative**********
     * *********************************************************************************
     * *****************************************
     * ************************************************************************
     * **************************************************
     */
    /**
     * Costanti per la gestione delle configurazione applicative elaborazione dizionario cart
     */
    public static final String CONF_APPLICATIVE_CART = "CART";
    public static final String CONF_APPLICATIVE_DIZ = "DIZ";
    public static final String CONF_APPLICATIVE_AMM = "AMM";
    public static final String CONF_APPLICATIVE_MOV = "MOV";
    public static final String CONF_APPLICATIVE_MOV_SCIA = "MOV_SCIA";
    public static final String CONF_APPLICATIVE_MOV_ORDINARIO = "MOV_ORDINARIO";
    public static final String CONF_APPLICATIVE_MOV_COMUNICAZIONE = "MOV_COMUNICAZIONE";
    // 
    public static final String CONF_APPLICATIVE_CONSOLE = "CONSOLE";
    /**
     * Stringhe che rappresentano il tipo di autorizzazione
     */
    public static final String AUTORIZZAZIONE_DEHORS = "AUTORIZZAZIONE_DEHORS";
    /**
     * Rappresenta la stringa che identifica il movimento padre
     */
    public static final String MOVPADRE = "MOVPADRE";
    /**
     * Costanti che rappresentanop il tipo di funzionalità in VBG che può definire una comunicazione da parte di un
     * movimento
     */
    public static final String COMUNICAZIONI_TIPO_MOV_DOPO_FIRMA_DOC = "FIRMA_DOC";
    public static final String COMUNICAZIONI_TIPO_MOV_DOPO_INSERIMENTO_MOV = "INSERIMENTO_MOV";
    public static final String VERTICALIZZAZIONE_OSSERVATORIO_REGIONALE = "OSSERVATORIO_REGIONALE";
    public static final String VERTICALIZZAZIONE_OSSERVATORIO_FVG = "OSSERVATORIO_FVG";
    public static final String VERTICALIZZAIONE_RFC239 = "RFC239";
    public static final String VERTICALIZZAZIONE_RFC239_LISTA_NODI_NO_RICEVUTA = "LISTA_NODI_NO_RICEVUTA";
    public static final String VERTICALIZZAZIONE_RFC239_NOTIFICA_183_VIA_SEM_ID_NODO = "NOTIFICA_183_VIA_SEM_ID_NODO";
    /**
     * Costanti per la verticalizzaione MAIL_SERVICE
     */
    public static final String VERTICALIZZAIONE_MAIL_SERVICE = "MAIL_SERVICE";
    public static final String VERTICALIZZAIONE_MAIL_SERVICE_POSTA_USCITA_FOLDERNAME = "POSTA_USCITA_FOLDERNAME";
    /**
     * Tipo firma digitale
     */
    public static final String TIPO_FIRMA_PADES = "PADES";
    public static final String TIPO_FIRMA_CADES = "CADES";
    /**
     * Costanti per definire l'algoritmo del Sorteggio
     */
    public static final int SORTEGGIO_STANDARD = 0;
    public static final int SORTEGGIO_REGIONE_EMILIA_ROMAGNA = 1;
    public static final String TIPIARCHIVIOISTANZA_P0 = "P0";
    public static final String TIPIARCHIVIOISTANZA_P1 = "P1";
    public static final String TIPIARCHIVIOISTANZA_P2 = "P2";
    public static final int PRATICHE_OBBLIGATORIE = 0;
    public static final int PRATICHE_RESTANTI = 1;
    public static final int PRATICHE_RESTANTI_PESATE = 2;
    /**
     * VARIABILI PER LA GESTIONE DELLA FUNZIONALITA' DOCUMENTI_AUTORIZZAZIONI
     */
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione documenti istanza nella list
     * di documenti autorizzazione.
     */
    public static final String CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ISTANZA = "LIST_DOC_ISTANZA";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione documenti movimenti nella
     * list di documenti autorizzazione.
     */
    public static final String CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_MOVIMENTO = "LIST_DOC_MOVIMENTO";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione allegati istanza nella list
     * di documenti autorizzazione.
     */
    public static final String CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_ALLEGATI_ISTANZA = "LIST_ALLEGATI_ISTANZA";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione documenti procure nella list
     * di documenti autorizzazione.
     */
    public static final String CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_PROCURE = "LIST_PROCURE";
    /**
     * parametro della configurazione utente che memorizza se visualizzare o no la sezione documenti anagrafe nella list
     * di documenti autorizzazione.
     */
    public static final String CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ANAGRAFE = "LIST_DOC_ANAGRAFE";
    /**
     * Parametro per specificare che il codice inserito è relativo a Documentiistanza, usato in
     * DocumentiAutorizzazioneController nel metodo ajaxAggiungiDocumento.
     */
    public static final Integer DOCAUTORIZZAZIONE_CODICE_DOCUMENTIISTANZA = 0;
    /**
     * Parametro per specificare che il codice inserito è relativo a MovimentiAllegati, usato in
     * DocumentiAutorizzazioneController nel metodo ajaxAggiungiDocumento.
     */
    public static final Integer DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI = 1;
    /**
     * Parametro per specificare che il codice inserito è relativo a Istanzeallegati, usato in
     * DocumentiAutorizzazioneController nel metodo ajaxAggiungiDocumento.
     */
    public static final Integer DOCAUTORIZZAZIONE_CODICE_ISTANZEALLEGATI = 2;
    /**
     * Parametro per specificare che il codice inserito è relativo a Anagrafedocumenti, usato in
     * DocumentiAutorizzazioneController nel metodo ajaxAggiungiDocumento.
     */
    public static final Integer DOCAUTORIZZAZIONE_CODICE_ANAGRAFEDOCUMENTI = 3;
    /**
     * Parametro per specificare che il codice inserito è relativo a Istanzeprocure, usato in
     * DocumentiAutorizzazioneController nel metodo ajaxAggiungiDocumento.
     */
    public static final Integer DOCAUTORIZZAZIONE_CODICE_ISTANZEPROCURE = 4;
    /**
     * Parametro per verificare che il popup metti alla firma è stato aperto nella sezione documenti autorizzazione
     * (documenti da firmare).
     */
    public static final Integer FROM_DOCAUTORIZZAZIONE_DOC_MESSI_ALLA_FIRMA = 1;
    /**
     * Label per gli header della tabella per i documenti allegati al movimento dell'istanza.
     */
    public static final String DESCRIZIONE_FILE_HEADER = "DESCRIZIONE";
    public static final String NOME_FILE_HEADER = "NOME FILE";
    public static final String IMPRONTA_HASH_SHA256_HEADER = "IMPRONTA HASH FORMATO SHA256";

    /**
     * Ritorna il valore del parametro configurato in deploy-properties.
     * {@link WebConstants#TIPOLOGIA_INSTALLAZIONE_PROP}
     * 
     * @return
     */
    public static Set<String> getCODICI_INSTALLAZIONE_MASTER() {

	if (codiciInstallazioneMaster == null) {
	    codiciInstallazioneMaster = new HashSet<String>();
	    Properties deployProps = getDeployProperties();
	    String tipoInstallazione = (String) deployProps.get("installazione.master.idcomune.codici");
	    if (StringUtils.isNotBlank(tipoInstallazione)) {
		log.info("get_CODICI_INSTALLAZIONE_MASTER: i seguenti codici individuano una installazione MASTER {}", tipoInstallazione);
		String[] codiciInstallazione = tipoInstallazione.split(",");
		for (String codice : codiciInstallazione) {
		    if (StringUtils.isNotBlank(codice)) {
			codiciInstallazioneMaster.add(codice.trim());
		    }
		}
	    }
	}
	return codiciInstallazioneMaster;
    }

    public enum SecurityParams {
	AUTHENTICATION_GATEWAY_URL,
	AUTHENTICATION_GATEWAY_FO_URL,
	AUTHENTICATION_GATEWAY_URLCIE,
	AUDIT_SERVICE_URL, // 
	APP_ASP,
	APP_ASPNET,
	APP_JAVA,
	APP_AR_JAVA,
	BASE_URL,
	TOKEN_TIMEOUT,
	CHECK_TOKEN_TIMEOUT,
	WSHOSTURL_ASPNET, //
	WSHOSTURL_EXPORT,
	WSHOSTURL_FILECONVERTER,
	WSHOSTURL_FIRMADIGITALE_REST,
	WSHOSTURL_JAVA,
	WSHOSTURL_RENDER, //
	WSHOSTURL_MAILSERVICE,
	WSHOSTURL_NLAPEC,
	WSHOSTURL_PDFUTILS,
	WSHOSTURL_FIRMA,
	PENTAHO_URL_CARTE, //
	PENTAHO_EXP_PATH,
	PRODUCT_NAME,
	PRODUCT_STYLE,
	WSHOSTURL_APIBACKEND,
	WS_URL_PROTOCOLLO,
	RABBIT_HOSTNAME,
	RABBIT_PORT,
	RABBIT_USERNAME,
	RABBIT_PASSWORD,
	RABBIT_EXCHANGE_NAME,
	RABBIT_MESSAGGI_BACKEND_SERVICE,
	WSHOSTURL_RICALCOLOAREE,
	WSHOSTURL_CARTOGRAFICO,
	WSHOSTURL_GENERATORE_RIEPILOGHI,
	WSHOSTURL_SIT
    }

    private static Map<String, String> securtityParamsMap;

    public static Map<String, String> reloadSecurityParamsMap() {

	return loadSecurityParams();
    }

    /**
     * Costanti per definire il tipo rateizzaizoni
     */
    public static final String TIPO_RATEIZZAZIONE_DEFAULT = "DEFAULT";
    public static final String TIPO_RATEIZZAZIONE_AMMORTAMENTO_FRANCESE = "AMMORTAMENTO_FRANCESE";
    /**
     * Costanti per definire la periodicita
     */
    public static final Integer PERIODICITA_RATA_MENSILE = 30;
    public static final Integer PERIODICITA_RATA_BIMESTRALE = 60;
    public static final Integer PERIODICITA_RATA_TRIMESTRALE = 90;
    public static final Integer PERIODICITA_RATA_QUADRIMESTRALE = 120;
    public static final Integer PERIODICITA_RATA_SEMESTRALE = 180;
    public static final Integer PERIODICITA_RATA_ANNUALE = 365;
    /**
     * 
     * Costanti per definire la ricerca delle istanze
     */
    public static final Integer SEARCH_ISTANZE = 0;
    public static final Integer SEARCH_ISTANZE_COLLEGATE = 1;
    public static final Integer SEARCH_ISTANZE_ACCESSO_ATTI = 2;
    public static final Integer SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA = 3;
    /**
     * Costanti per gestione della ricerca delle autorizzazioni
     */
    public static final Integer SEARCH_AUT_DEFAULT = 0;
    public static final Integer SEARCH_CONC_DEFAULT = 0;
    public static final Integer SEARCH_AUT_PER_GESTIONE_SPUNTA = 1;
    public static final Integer SEARCH_CONC_PER_GESTIONE_SPUNTA = 1;
    public static final String COMUNIASSOCIATI_REQUEST_VARIABLE = "_COMUNIASSOCIATI_";
    /**
     * 
     */
    public static final String API_SERVICE_ALLEGATI_ZIP_FILE = "allegati-pratica-backoffice.zip";
    /**
     * 
     * ZIP LOGICO Constants
     */
    public static final String CARTELLA_ZIP_DEI_DOCUMENTI_NOMEFILE = "documentazione.zip";
    public static final String CARTELLA_ZIP_DEI_DOCUMENTI_DESCRIZIONE = "Cartella zip dei documenti";
    /**
     * 
     * Verticalizzazione Condivisione Documentale
     */
    public static final String VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE = "CONDIVISIONE_DOCUMENTALE";
    public static final String VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE_ID_ACCOUNT_FTP = "ID_ACCOUNT_FTP";

    public static synchronized String getSecurityParamValue(SecurityParams paramName) {

	if (securtityParamsMap == null || securtityParamsMap.isEmpty()) {
	    loadSecurityParams();
	}
	String paramValue = securtityParamsMap.get(paramName.name());
	if (paramValue == null) {
	    paramValue = "";
	}
	return paramValue;
    }

    public enum SecurityMailParams {
	LOGINNAME,
	PASSWORD,
	MAILSERVER,
	SMTP_PORT,
	SENDER,
	USE_AUTHENTICATION,
	USE_SSL
    }

    public static synchronized Map<String, String> getSecurityMailParams() {

	Map<String, String> mailParams = new HashMap<String, String>();
	if (securtityParamsMap == null || securtityParamsMap.isEmpty()) {
	    loadSecurityParams();
	}
	mailParams.put(SecurityMailParams.LOGINNAME.name(), securtityParamsMap.get("MAIL.LOGINNAME"));
	mailParams.put(SecurityMailParams.PASSWORD.name(), securtityParamsMap.get("MAIL.PASSWORD"));
	mailParams.put(SecurityMailParams.MAILSERVER.name(), securtityParamsMap.get("MAIL.MAILSERVER"));
	mailParams.put(SecurityMailParams.SMTP_PORT.name(), securtityParamsMap.get("MAIL.SMTP_PORT"));
	mailParams.put(SecurityMailParams.SENDER.name(), securtityParamsMap.get("MAIL.SENDER"));
	mailParams.put(SecurityMailParams.USE_AUTHENTICATION.name(), securtityParamsMap.get("MAIL.USE_AUTHENTICATION"));
	mailParams.put(SecurityMailParams.USE_SSL.name(), securtityParamsMap.get("MAIL.USE_SSL"));
	return mailParams;
    }

    /**
     * COSTANT
     */
    public static final String METADATO_CONSERVAZIONE_DOC_SOSPESA = "CONSERVAZIONE_DOC_SOSPESA";
    private static Properties DEPLOY_PROPERTIES;

    /**
     * Torna le properties caricate dal file deploy.properties
     * 
     * @return
     */
    public static synchronized Properties getDeployProperties() {

	if (DEPLOY_PROPERTIES == null) {
	    InputStream in = null;
	    try {
		in = WebConstants.class.getClassLoader().getResourceAsStream(CONFIG_FILES_FOLDER + DEPLOY_PROPS);
		DEPLOY_PROPERTIES = new Properties();
		DEPLOY_PROPERTIES.load(in);
		return DEPLOY_PROPERTIES;
	    } catch (Exception e) {
		log.error("getDeployProperties(): error loading {}: {}", CONFIG_FILES_FOLDER + DEPLOY_PROPS, e.getMessage());
		throw new RuntimeException("Errore durante il caricamento del file di configurazione: " + e);
	    } finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (IOException e) {
			log.warn("getDeployProperties(): {}", e.getMessage());
		    }
		}
	    }
	} else {
	    return DEPLOY_PROPERTIES;
	}
    }

    private static Map<String, String> loadSecurityParams() {

	securtityParamsMap = new HashMap<String, String>();
	Properties deployProps = getDeployProperties();
	ExternalDBResolverWS externalDBResolver = new ExternalDBResolverWS();
	externalDBResolver.setConfigurationProperties(deployProps);
	try {
	    SecurityWSClient client = new SecurityWSClient();
	    client.setWsUrl(deployProps.getProperty("ws.token.url"));
	    client.setUsername(deployProps.getProperty("ws.token.user"));
	    client.setPassword(deployProps.getProperty("ws.token.pwd"));
	    client.setTimeout(10000);
	    externalDBResolver.setSecurityWSClient(client);
	    SigeproSecurity security = externalDBResolver.getSecurityWSDLPort();
	    GetApplicationInfoRequest req = new GetApplicationInfoRequest();
	    GetApplicationInfoResponse res = security.getApplicationInfo(req);
	    if (res != null) {
		List<ApplicationInfoType> applicationInfoTypes = res.getApplicationInfo();
		for (ApplicationInfoType applicationInfoType : applicationInfoTypes) {
		    securtityParamsMap.put(applicationInfoType.getParam(), applicationInfoType.getValue());
		    log.debug("loadSecurityParams() param: {}={}", applicationInfoType.getParam(), applicationInfoType.getValue());
		}
	    }
	} catch (Exception e) {
	    log.error("loadSecurityParams(): {}", e.getMessage());
	}
	return securtityParamsMap;
    }

    public static String getFirmaContextPath() {

	String wsFirmaUrl = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMA);
	wsFirmaUrl = wsFirmaUrl.substring(0, wsFirmaUrl.indexOf("/services"));
	return wsFirmaUrl.substring(wsFirmaUrl.lastIndexOf("/"));
    }
}
