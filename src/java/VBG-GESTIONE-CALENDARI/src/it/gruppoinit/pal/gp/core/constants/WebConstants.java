package it.gruppoinit.pal.gp.core.constants;

import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebConstants {

    private static final Logger log = LoggerFactory.getLogger(WebConstants.class);
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
     * idcomune
     */
    public static final String IDCOMUNE = "idcomune";
    /**
     * idcomunealias
     */
    public static final String IDCOMUNE_ALIAS = "idcomunealias";
    /**
     * Token
     */
    public static final String TOKEN = "Token";
    /**
     * url_first_request
     */
    public static final String URL_FIRST_REQUEST = "url_first_request";
    /**
     * ReturnTo
     */
    public static final String RETURNTO = "ReturnTo";
    /**
     * "hibernate.connection.url
     */
    public static final String HIBERNATE_CONN_URL = "hibernate.connection.url";
    /**
     * hibernate.default_schema
     */
    public static final String HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";
    /**
     * HIBERNATE_SESSION_FACTORY_KEY
     */
    public static final String HIBERNATE_SESSION_FACTORY_KEY = "HIBERNATE_SESSION_FACTORY_KEY";
    /**
     * Nome della chiave di Cache relativa ai ClassValidator di hibernate
     */
    public static final String CACHE_HIBERNATE_VALIDATORS = "CACHE_HIBERNATE_VALIDATORS";
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
     * userid
     */
    public static final String USER_ID = "userid";

    // ///////////****************************************////////////////////////////////////////////
    public static enum SecurityParams {
	AUTHENTICATION_GATEWAY_URL, AUTHENTICATION_GATEWAY_FO_URL, AUTHENTICATION_GATEWAY_URLCIE, AUDIT_SERVICE_URL, APP_ASP, APP_ASPNET, APP_JAVA, APP_AR_JAVA, BASE_URL, TOKEN_TIMEOUT, CHECK_TOKEN_TIMEOUT, WSHOSTURL_ASPNET, WSHOSTURL_EXPORT, WSHOSTURL_FILECONVERTER, WSHOSTURL_FIRMADIGITALE, WSHOSTURL_JAVA, WSHOSTURL_RENDER, WSHOSTURL_MAILSERVICE, WSHOSTURL_NLAPEC, WSHOSTURL_PDFUTILS, WSHOSTURL_FIRMA, PENTAHO_URL_CARTE, PENTAHO_EXP_PATH
    };

    private static Map<String, String> securtityParamsMap;

    public static Map<String, String> reloadSecurityParamsMap() {

	return loadSecurityParams();
    }

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

    public static enum SecurityMailParams {
	LOGINNAME, PASSWORD, MAILSERVER, SMTP_PORT, SENDER, USE_AUTHENTICATION, USE_SSL
    };

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
	    ApplicationInfoType[] applicationInfoTypes = security.getApplicationInfo(req);
	    if (applicationInfoTypes != null) {
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
}
