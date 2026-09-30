package it.gruppoinit.pal.gp.core.constants;

import java.util.ArrayList;
import java.util.List;

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
    public static final String VELOCITY_CONTEXT_KEY_CONTEXT_PATH = "contextPath";
    public static final String VELOCITY_CONTEXT_KEY_ANTEPRIMA = "preview";
    public static final String VELOCITY_CONTEXT_KEY_FUNZIONE_ANTEPRIMA = "FUNZIONE_ANTEPRIMA";
    public static final String VELOCITY_CONTEXT_KEY_PDF_OUTPUT = "pdfOutput";
    public static final String VELOCITY_CONTEXT_KEY_PDF_STYLES = "pdfStyles";
    public static final String VELOCITY_CONTEXT_KEY_DYNMODELLO_HTML = "modellithtml";
    public static final String VELOCITY_CONTEXT_KEY_DYNMODELLO_TITLE = "titoloScheda";
    public static final String VELOCITY_CONTEXT_KEY_ID_DOMANDA = "idDomanda";
    public static final String VELOCITY_CONTEXT_KEY_PDF_PAGE_ORIENTATION = "pageOrientation";
    public static final String VELOCITY_CONTEXT_KEY_IS_FRONT_OFFICE = "isFrontOffice";
    public static final String VELOCITY_CONTEXT_KEY_CODICE_ISTANZA = "codiceIstanza";
    public static final String VELOCITY_CONTEXT_KEY_MODULISTICANAZIONALE_ENABLED = "isModulisticaNazionaleEnabled";
    public static final String VELOCITY_CONTEXT_KEY_MODULISTICANAZIONALE_VALORE = "MODULISTICANAZIONALE_VALORE";
    public static final String VELOCITY_CONTEXT_KEY_LISTA_ENDOPROCEDIMENTI = "listaEndoProcedimenti";
    public static final String VELOCITY_CONTEXT_KEY_IDENTIFICATIVO_DOMANDA_GENERATO = "IDENTIFICATIVO_DOMANDA_GENERATO";
    //costanti utilizzate per la generazione di files e directories
    public static final String PRESENTAZIONE_DOMANDA_TEMP_SUBDIR_NAME = "presentazionedomanda";
    public static final String PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR = "_row_";
    public static final String PRESENTAZIONE_DOMANDA_INDEXED_TABLE_NAME_SEPARATOR = "_i_";
    public static final String PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR = "_CARTUSERFILE_";
    public static final String PRESENTAZIONE_DOMANDA_DYN2MODELLIT_TEMP_SUBDIR_NAME = "schededinamiche";
    public static final String PRESENTAZIONE_DOMANDA_RFC239_COPERTINA_FILENAME_PREFIX = "COPERTINA-";
    public static final String PRESENTAZIONE_DOMANDA_CODICE_ENDOLCALE_PREFIX = "LOC-";
    public static final String ZIP_FILE_SUFFIX = ".SUAP.ZIP";
    public static final String ZIP_CONTENT_TYPE = "application/zip";
    public static final String PDF_CONTENT_TYPE = "application/pdf";
    public static final String XML_CONTENT_TYPE = "text/xml";
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
    //nome del file che contiene i mapping degli id semantici sugli attributi del messaggio di presentazione domanda per rfc 183
    public static final String CFG_FILE_MAPPING_IDSEMANTICI_RFC183 = "mapping_id_semantici_rfc183.properties";
    //nome del file che contiene i mapping degli id semantici sugli attributi del messaggio di copertina per rfc 239
    public static final String CFG_FILE_MAPPING_IDSEMANTICI_RFC239 = "mapping_id_semantici_rfc239.properties";
    //nome del file che contiene LA CONFIGURAZIONE DEI CONTROLLI A COMPILAZIONE AUTOMATICA
    public static final String CFG_FILE_AUTOCOMPILERS = "autocompiler-config.xml";
    //nome del file che contiene i mapping degli id semantici sugli attributi statici o dinamici di un'istanza VBG
    public static final String CFG_FILE_MAPPING_CART_VBG = "cart-mappings.xml";
    //nome del file che contiene LA definizione XML del quadro altri allegati
    public static final String XML_FILE_QUADRO_ALLEGATI = "q_allegati.xml";
    //codice attività BDR utilizzato per ottenere la modulistica STANDARD_0
    public static final String CODICE_ATTIVITA_STD_0 = "01.12";
    //stringhe che definiscono i tipi di standard previsti per l'invio dei messaggi
    public static final String STANDARD_2 = "STANDARD 2";
    public static final String STANDARD_8 = "STANDARD 8";
    public static final String STANDARD_0 = "STANDARD 0";
    public static final String STANDARD_10 = "STANDARD 10";
    //nome del modello fittizio che memorizza i valori degli id semantici per i campi delle schede dinamiche
    public static final String MODELLO_INIT_DATI_DINAMICI = "RT-DYN2DATI";
    //id semantici custom INIT
    public static final String ID_SEMANTICO_INIT_CODICE_INTERVENTO = "RT.CODICE_INTERVENTO";
    public static final String ID_SEMANTICO_INIT_ENDO_NOCART = "RT-CODICEPROCEDIMENTO";// DA MANTENERE PER COMPATIBILITA' VECCHI NODI NLA-CART-COMUNICAZIONIINTERNE    
    public static final String ID_SEMANTICO_INIT_SCHEDEDINAMICHE = "RT-SD-MODELLI";
    public static final String ID_SEMANTICO_INIT_WARNINGS_FIRMADIGITALE = "STAR-WARNINGS-FIRMADIGITALE";
    public static final String ID_SEMANTICO_INIT_CAMPISCHEDA_PREFIX = "RT-SD-CAMPI-";
    public static final String ID_SEMANTICO_INIT_VALORECAMPO_PREFIX = "RT-SD-";
    public static final String ID_SEMANTICO_INIT_VALOREDECODIFICATO_SUFFIX = "-V";
    //id semantici referenziati direttamente in parti di codice non dinamiche
    public static final String ID_SEMANTICO_COMUNE_SUAP = "COMUNE_SUAP";
    public static final String ID_SEMANTICO_ATTIVITA_INDIRIZZO_COMUNE = "ATTIVITA.INDIRIZZO.COMUNE";
    public static final String ID_SEMANTICO_ATTIVITA_INDIRIZZO_PROVINCIA = "ATTIVITA.INDIRIZZO.PROVINCIA";
    public static final String ID_SEMANTICO_ATTIVITA_INDIRIZZO_CAP = "ATTIVITA.INDIRIZZO.CAP";
    public static final String ID_SEMANTICO_CODICE_ATTIVITA_REGIONALE = "CODICE_ATTIVITA_REGIONALE";
    public static final String ID_SEMANTICO_DESCR_ATTIVITA_REGIONALE = "DESCR_ATTIVITA_REGIONALE";
    public static final String ID_SEMANTICO_ALLEGATI_MODULO_CODICE = "ALLEGATI.MODULO-CODICE";
    public static final String ID_SEMANTICO_ENDO_ALLEGATI_CODICE = "ENDO.RPL_CODICEENDOREGIONALE.ALLEGATI.MODULO-CODICE";
    public static final String ID_SEMANTICO_ALLEGATI_MODULO_ALLEGATO = "ALLEGATI.MODULO-ALLEGATO";
    public static final String ID_SEMANTICO_ALLEGATI_ENDOLOCALE_CODICE = "ALLEGATI.ENDOLOCALE-CODICE";
    public static final String ID_SEMANTICO_ALLEGATI_ENDOLOCALE_ALLEGATI = "ALLEGATI.ENDOLOCALE-ALLEGATI";
    public static final String ID_SEMANTICO_ALLEGATI_ENDOLOCALE_DESCRIZIONE = "ALLEGATI.ENDOLOCALE-DESCRIZIONE";
    public static final String ID_SEMANTICO_PRATICA_AZIONE = "PRATICA_AZIONE";
    public static final String ID_SEMANTICO_DATA_COMPILAZIONE = "DATA_COMPILAZIONE";
    public static final String ID_SEMANTICO_SOTTOSCRITTO_NOME = "SOTTOSCRITTO.NOME";
    public static final String ID_SEMANTICO_SOTTOSCRITTO_COGNOME = "SOTTOSCRITTO.COGNOME";
    public static final String ID_SEMANTICO_SOTTOSCRITTO_CF = "SOTTOSCRITTO.CF";
    public static final String ID_SEMANTICO_RICHIEDENTE_NOME = "RICHIEDENTE.NOME";
    public static final String ID_SEMANTICO_RICHIEDENTE_COGNOME = "RICHIEDENTE.COGNOME";
    public static final String ID_SEMANTICO_RICHIEDENTE_CF = "RICHIEDENTE.CF";
    public static final String ID_SEMANTICO_ISTANZA_TIPOPROCEDIMENTO = "ISTANZA.TIPO_PROCEDIMENTO";
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
    public static final String REQUEST_PARAM_DISABLED_FIELD_PREFIX = "_DIS_";
    public static final String REQUEST_PARAM_NOMEFILE_SUFFIX = "_filename";
    public static final String REQUEST_ATTR_PARSED_PARAMETERS = "parsed_parameters";
    //stringhe utilizzate per l'implementazione delle logiche intrinseche alla validazione e all'attivazione dinamica di parti della modulistica
    public static final String RFC186_RANGE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String RFC186_UNBOUNDED_RANGE = "unbounded";
    public static final String RFC186_TODAY_RANGE = "oggi";
    public static final String RFC239_ALLOWED_EXTENSIONS = ".pdf;.pdf.p7m;.xml;.dwf;.dwf.p7m;.svg;.svg.p7m;.jpg;.jpg.p7m";
    //valori dell'esito della validazione della firma digitale
    public static final String ESITO_VALIDAZIONE_FIRMA_OK = "OK";
    public static final String ESITO_VALIDAZIONE_FIRMA_KO = "KO";
    //pattern per espressioni regolari:
    //per la codifica degli allegati degli endoprocedimenti non CART
    public static final String REGEX_ALLEGATINOCART_IDPROCEDIMENTO = "^E\\[([A-Za-z]*\\d*\\|\\d+)\\]"; // "^E\\[(\\d+)\\]";
    //per la codifica degli allegati degli endoprocedimenti non CART
    public static final String REGEX_ALLEGATINOCART_IDALBEROPROCDOC = "^P\\[(\\d+)\\]";
    public static final String REGEX_ALLEGATINOCART_DESCALLEGATO = "-(.*)";
    //per il match dei mapping dei valori VBG su valori CART
    public static final String REGEX_ALL_WORDS_MATCH = ".";
    //parametri di configurazione aggiuntivi per alcuni controlli a completamento automatico
    public static final String AUTOCOMP_CFG_IDSEMANTICOENDO = "idSemanticoEndo";
    public static final String AUTOCOMP_CFG_IDSEMANTICOALLEGATO = "idSemanticoAllegato";
    public static final String AUTOCOMP_CFG_PRATICA_AZIONE_VALUE = "praticaAzione";
    //costanti usate dalla procedura di generazione degli allegati per notifica CART di istanze non CART
    public static final String STAR_CESSAZIONE = "STAR-Cessazione";
    //formati allegati convertibili in pdf
    public static final List<String> CONVERTIBLE_EXTENSIONS = new ArrayList<String>();
    static {
	CONVERTIBLE_EXTENSIONS.add("HTML");
	CONVERTIBLE_EXTENSIONS.add("HTM");
	CONVERTIBLE_EXTENSIONS.add("RTF");
	CONVERTIBLE_EXTENSIONS.add("ODT");
	CONVERTIBLE_EXTENSIONS.add("DOC");
	CONVERTIBLE_EXTENSIONS.add("DOCX");
    }
    //costanti per generazione id semantici degli oneri
    public static final String ONERI_FIELD_CAUSALE = "CAUSALE";
    public static final String ONERI_FIELD_IMPORTO = "IMPORTO";
    public static final String ONERI_FIELD_RICEVUTA = "RICEVUTA";
    public static final String ONERI_FIELD_STATO_PAGAMENTO = "STATO_PAGAMENTO";
    public static final String ONERI_FIELD_TIPO_PAGAMENTO = "TIPO_PAGAMENTO";
    public static final String ONERI_FIELD_DATA_PAGAMENTO = "DATA_PAGAMENTO";
    public static final String ONERI_FIELD_RIF_PAGAMENTO = "RIFERIMENTI_PAGAMENTO";
}
