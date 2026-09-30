package it.gruppoinit.pal.gp.core.constants;

public class FACCTConstants {

    //chiavi utilizzate per archiviare i dati nella HttpSession o nel Model di Spring
    public static final String SESSION_KEY_MODULISTICA = "modulistica";
    public static final String SESSION_KEY_DATI_UTENTE = "dati_utente";
    public static final String SESSION_KEY_MODELLO_RIEPILOGO = "modello_riepilogo";
    public static final String SESSION_KEY_ALLEGATI_UTENTE = "allegati_utente";
    public static final String SESSION_KEY_MDA_MODULI = "moduli";
    public static final String SESSION_KEY_HELPER_MODELLI_DINAMICI = "helperScheda";
    public static final String SESSION_KEY_ERRORI_GENERA_ALLEGATI_NOTIFICA = "erroriAllegatiNotificaCART";
    //chiavi per il VelocityContext utilizzato nel rendering dei moduli
    public static final String VELOCITY_CONTEXT_KEY_MODULO_HELPER = "helper";
    public static final String VELOCITY_CONTEXT_KEY_PRIMO_MODULO = "primoModulo";
    public static final String VELOCITY_CONTEXT_KEY_PRIMO_QUADRO = "primoQuadro";
    public static final String VELOCITY_CONTEXT_KEY_CONTEXT_PATH = "contextPath";//
    public static final String VELOCITY_CONTEXT_KEY_PDF_OUTPUT = "pdfOutput";
    public static final String VELOCITY_CONTEXT_KEY_PDF_STYLES = "pdfStyles";
    public static final String VELOCITY_CONTEXT_KEY_DYNMODELLO_HTML = "modellithtml";
    public static final String VELOCITY_CONTEXT_KEY_DYNMODELLO_TITLE = "titoloScheda";
    public static final String VELOCITY_CONTEXT_KEY_ID_DOMANDA = "idDomanda";
    public static final String VELOCITY_CONTEXT_KEY_PDF_PAGE_ORIENTATION = "pageOrientation";
    public static final String VELOCITY_CONTEXT_KEY_IS_FRONT_OFFICE = "isFrontOffice";
    public static final String VELOCITY_CONTEXT_KEY_CODICE_ISTANZA = "codiceIstanza";
    //costanti utilizzate per la generazione di files e directories
    public static final String PRESENTAZIONE_DOMANDA_TEMP_SUBDIR_NAME = "presentazionedomanda";
    public static final String PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR = "_row_";
    public static final String PRESENTAZIONE_DOMANDA_INDEXED_TABLE_NAME_SEPARATOR = "_i_";
    public static final String PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR = "_CARTUSERFILE_";
    public static final String PRESENTAZIONE_DOMANDA_DYN2MODELLIT_TEMP_SUBDIR_NAME = "schededinamiche";
    public static final String PRESENTAZIONE_DOMANDA_MODELLO_RIEPILOGO_SUFFIX = ".SUAP.XML";
    public static final String PRESENTAZIONE_DOMANDA_DISTINTA_MODELLO_RIEPILOGO_SUFFIX = ".SUAP.PDF";
    public static final String PRESENTAZIONE_DOMANDA_MODELLO_ATTIVITA_SUFFIX = ".MDA.XML";
    public static final String PRESENTAZIONE_DOMANDA_MODELLO_STD0_SUFFIX = ".MDA.STANDARD 0.XML";
    public static final String PRESENTAZIONE_DOMANDA_MODELLO_STD2_SUFFIX = ".MDA.STANDARD 2.XML";
    public static final String ZIP_FILE_SUFFIX = ".SUAP.ZIP";
    public static final String ZIP_CONTENT_TYPE = "application/zip";
    public static final String PDF_CONTENT_TYPE = "application/pdf";
    public static final String GENERA_ALLEGATI_NOTIFICA_TEMP_SUBDIR_NAME = "generaAllegatiNotifica";
    //stringhe utilizzate per la generazione dinamica di nomi di variabili javascript
    public static final String JS_VAR_TABS_QUADRI_MODULO_PREFIX = "tabs_modulo_";
    public static final String JS_VAR_INFO_QUADRI_MODULO_PREFIX = "info_quadri_modulo_";
    public static final String JS_VAR_ALLEGATI_ISTANZA = "allegatiIstanza";
    //stringhe utilizzate per la generazione dinamica di nomi e id degli elementi html
    public static final String HTML_ID_TAB_QUADRO_PEREFIX = "tab_quadro_";
    public static final String HTML_UNIQUE_ID_SEPARATOR = ".";
    //default charset
    public static final String DEFAULT_CHARSET = "UTF-8";
    //nome del file che contiene i mapping degli id semantici sugli attributi del messaggio di presentazione domanda
    public static final String CFG_FILE_MAPPING_IDSEMANTICI = "mapping_id_semantici.properties";
    //nome del file che contiene LA CONFIGURAZIONE DEI CONTROLLI A COMPILAZIONE AUTOMATICA
    public static final String CFG_FILE_AUTOCOMPILERS = "autocompiler-config.xml";
    //nome del file che contiene i mapping degli id semantici sugli attributi statici o dinamici di un'istanza VBG
    public static final String CFG_FILE_MAPPING_CART_VBG = "cart-mappings.xml";
    public static final String CFG_FILE_MAPPING_CART_VBG_STD0_BO = "cart-mappings-standard0-bo.xml";
    //codice attività BDR utilizzato per ottenere la modulistica STANDARD_00_BO
    public static final String CODICE_ATTIVITA_STD_0_BO = "99.1R";
    public static final String AZIONE_STD_0_BO = "STAR-StandardBO";
    public static final String MODULISTICA_STD_0_BO = "MODULO_STD_0_BO.XML";
    //stringhe che definiscono i tipi di standard previsti per l'invio dei messaggi
    public static final String STANDARD_2 = "STANDARD 2";
    public static final String STANDARD_0 = "STANDARD 0";
    //nome del modello fittizio che memorizza i valori degli id semantici per i campi delle schede dinamiche
    public static final String MODELLO_INIT_DATI_DINAMICI = "DYN2DATI";
    //id semantici custom INIT
    public static final String ID_SEMANTICO_INIT_CODICE_INTERVENTO = "INIT.CODICE_INTERVENTO";
    public static final String ID_SEMANTICO_INIT_ENDO_NOCART = "INIT-CODICEPROCEDIMENTO";
    public static final String ID_SEMANTICO_INIT_SCHEDEDINAMICHE = "INIT-SD-MODELLI";
    public static final String ID_SEMANTICO_INIT_WARNINGS_FIRMADIGITALE = "INIT-WARNINGS-FIRMADIGITALE";
    public static final String ID_SEMANTICO_INIT_CAMPISCHEDA_PREFIX = "INIT-SD-CAMPI-";
    public static final String ID_SEMANTICO_INIT_VALORECAMPO_PREFIX = "INIT-SD-";
    public static final String ID_SEMANTICO_INIT_VALOREDECODIFICATO_SUFFIX = "-V";
    //id semantici referenziati direttamente in parti di codice non dinamiche
    public static final String ID_SEMANTICO_COMUNE_SUAP = "COMUNE_SUAP";
    public static final String ID_SEMANTICO_ATTIVITA_INDIRIZZO_COMUNE = "ATTIVITA.INDIRIZZO.COMUNE";
    public static final String ID_SEMANTICO_ATTIVITA_INDIRIZZO_PROVINCIA = "ATTIVITA.INDIRIZZO.PROVINCIA";
    public static final String ID_SEMANTICO_ATTIVITA_INDIRIZZO_CAP = "ATTIVITA.INDIRIZZO.CAP";
    public static final String ID_SEMANTICO_CODICE_ATTIVITA_REGIONALE = "CODICE_ATTIVITA_REGIONALE";
    public static final String ID_SEMANTICO_DESCR_ATTIVITA_REGIONALE = "DESCR_ATTIVITA_REGIONALE";
    public static final String ID_SEMANTICO_ALLEGATI_MODULO_CODICE = "ALLEGATI.MODULO-CODICE";
    public static final String ID_SEMANTICO_ALLEGATI_MODULO_ALLEGATO = "ALLEGATI.MODULO-ALLEGATO";
    public static final String ID_SEMANTICO_ALLEGATI_MODULO_DESCRIZIONE = "ALLEGATI.MODULO-DESCRIZIONE";
    public static final String ID_SEMANTICO_ALLEGATI_ENDOLOCALE_CODICE = "ALLEGATI.ENDOLOCALE-CODICE";
    public static final String ID_SEMANTICO_ALLEGATI_ENDOLOCALE_ALLEGATI = "ALLEGATI.ENDOLOCALE-ALLEGATI";
    public static final String ID_SEMANTICO_ALLEGATI_ENDOLOCALE_DESCRIZIONE = "ALLEGATI.ENDOLOCALE-DESCRIZIONE";
    public static final String ID_SEMANTICO_DATA_COMPILAZIONE = "DATA_COMPILAZIONE";
    public static final String ID_SEMANTICO_DATA_NOTIFICA = "DATA_NOTIFICA";
    public static final String ID_SEMANTICO_NUMERO_ALLEGATI = "NUMERO_ALLEGATI";
    public static final String ID_SEMANTICO_IDENTIFICATIVO_PRATICA = "IDENTIFICATIVO_PRATICA";
    //parametri request utilizzati dalle pagine della modulistica e presentazione domanda
    public static final String REQUEST_PARAM_ID_MODULO = "modulo_attivo";
    public static final String REQUEST_PARAM_RIFERIMENTO_MODULO = "riferimento_modulo_attivo";
    public static final String REQUEST_PARAM_ID_QUADRO = "quadro_attivo";
    public static final String REQUEST_PARAM_ID_DOMANDA_FO = "idDomandaFo";
    public static final String REQUEST_PARAM_FIRMA_ALLEGATI_UTENTE = "firmaAllegatiUtente";
    public static final String REQUEST_PARAM_PRESENTA_DOMANDA = "presenta_domanda";
    public static final String REQUEST_PARAM_CODICE_MODELLO = "codiceModello";
    public static final String REQUEST_PARAM_IDSEM_DESC_ALLEGATO = "idSemDescAllegato";
    public static final String REQUEST_PARAM_IDSEM_FILE_ALLEGATO = "idSemFileAllegato";
    public static final String REQUEST_PARAM_ROW_INDEX = "rowIndex";
    public static final String REQUEST_PARAM_DATA_INDEX = "dataIndex";
    public static final String REQUEST_PARAM_DOCUMENT_WIDTH = "docWidth";
    public static final String REQUEST_PARAM_VALIDATE_DIGITAL_SIGNATURE = "validateDs";
    public static final String REQUEST_ATTR_PARSED_PARAMETERS = "parsed_parameters";
    //stringhe utilizzate per l'implementazione delle logiche intrinseche alla validazione e all'attivazione dinamica di parti della modulistica
    public static final String RFC186_RANGE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String RFC186_UNBOUNDED_RANGE = "unbounded";
    public static final String RFC186_TODAY_RANGE = "oggi";
    //valori dell'esito della validazione della firma digitale
    public static final String ESITO_VALIDAZIONE_FIRMA_OK = "OK";
    public static final String ESITO_VALIDAZIONE_FIRMA_KO = "KO";
    //pattern per espressioni regolari:
    //per la codifica degli allegati degli endoprocedimenti non CART
    public static final String REGEX_ALLEGATINOCART_IDPROCEDIMENTO = "^E\\[(\\d+)\\]";
    //per la codifica degli allegati degli endoprocedimenti non CART
    public static final String REGEX_ALLEGATINOCART_IDALBEROPROCDOC = "^P\\[(\\d+)\\]";
    public static final String REGEX_ALLEGATINOCART_DESCALLEGATO = "-(.*)";
    //per il match dei mapping dei valori VBG su valori CART
    public static final String REGEX_ALL_WORDS_MATCH = ".";
    //parametri di configurazione aggiuntivi per alcuni controlli a completamento automatico
    public static final String AUTOCOMP_CFG_IDSEMANTICOENDO = "idSemanticoEndo";
    public static final String AUTOCOMP_CFG_IDSEMANTICOALLEGATO = "idSemanticoAllegato";
    //costanti usate dalla procedura di generazione degli allegati per notifica CART di istanze non CART
}
