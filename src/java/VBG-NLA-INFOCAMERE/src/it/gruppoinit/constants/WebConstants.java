package it.gruppoinit.constants;

/**
 * Classe delle costanti
 * 
 * @author fabrizioc
 * 
 */
public class WebConstants {

    public static final String DEPLOY_PROPS = "deploy.properties";
    /**
     * nome del file di configurazione per il db dell'applicazione: gp-backoffice-db.properties
     */
    public static final String DB_PROPS = "db.properties";
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
    ////////////////////////////////////// /////////////////////////////////////////////////////////
    public static final String PREFIX_ALLEGATO_SUAP = "SUAP";
    public static final String PREFIX_ALLEGATO_FALDONE_TELEMATICO = "Faldone_Telematico";
    public static final String ESTENSIONE_XML = "XML";
}
