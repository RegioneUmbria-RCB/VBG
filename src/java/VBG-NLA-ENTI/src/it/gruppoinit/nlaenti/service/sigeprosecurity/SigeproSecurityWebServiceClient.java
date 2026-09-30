package it.gruppoinit.nlaenti.service.sigeprosecurity;

import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceLocator;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecuritySoap11Stub;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SigeproSecurityWebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityWebServiceClient.class);
    private String sigeproSecurityUrl;
    private String sigeproSecurityUserId;
    private String sigeproSecurityUserPassword;
    private Map<String, GetDbConnectionInfoResponse> connectionPropertiesCache = new HashMap<String, GetDbConnectionInfoResponse>();
    private String DB_CONN_PROPERTY_DRIVERNAME_PREFIX = "db.driver";
    private String DB_CONN_PROPERTYFILE_NAME = "drivers.properties";

    public String loginAPP(String idcomunealias) {

	String token = "";
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    LoginRequest loginRequest = new LoginRequest(idcomunealias, ContestoType.APP, "", "", "");
	    log.debug("login(): idcomunealias={}", idcomunealias);
	    LoginResponse loginResponse = sigeproSecurity.login(loginRequest);
	    token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		throw new RuntimeException("Token nullo");
	    }
	} catch (Exception e) {
	    log.error("login(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante la login al ws sigeprosecurity: " + e.getMessage());
	}
	return token;
    }

    public Properties getTokenInfo(String token) {

	log.debug("checkToken({})", token);
	Properties props = new Properties();
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(new CheckTokenRequest(token, true));
	    if (!checkTokenResponse.isValid()) {
		log.error("checkToken({}): token non valido", token);
		throw new RuntimeException("Token scaduto o non valido: " + token);
	    }
	    TokenInfoType tokenInfo = checkTokenResponse.getTokenInfo();
	    props.setProperty("idcomune", tokenInfo.getIdcomune());
	    props.setProperty("idcomunealias", tokenInfo.getAlias());
	    props.setProperty("userid", tokenInfo.getUserid());
	    props.setProperty("contesto", tokenInfo.getContesto().toString());
	    log.debug("checkToken({}) return: idcomune={}, alias={}, userid={}, contesto={}", new Object[] { token, tokenInfo.getIdcomune(),
		    tokenInfo.getAlias(), tokenInfo.getUserid(), tokenInfo.getContesto() });
	} catch (Exception e) {
	    log.error("checkToken({}): errore durante la verifica del token", token);
	    throw new RuntimeException("Errore durante la verifica del token: " + e.getMessage());
	}
	return props;
    }

    public Map<String, String> getParams(String param) {

	Map<String, String> params = new HashMap<String, String>();
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    GetApplicationInfoRequest req = new GetApplicationInfoRequest();
	    if (StringUtils.isNotBlank(param)) {
		req = new GetApplicationInfoRequest(param);
	    }
	    ApplicationInfoType[] applicationInfoTypes = sigeproSecurity.getApplicationInfo(req);
	    for (int i = 0; i < applicationInfoTypes.length; i++) {
		params.put(applicationInfoTypes[i].getParam(), applicationInfoTypes[i].getValue());
		log.debug("getParams(): param [{}={}]", applicationInfoTypes[i].getParam(), applicationInfoTypes[i].getValue());
	    }
	} catch (Exception e) {
	    log.error("getParams(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante il recupero dei parametri da sigeprosecurity");
	}
	return params;
    }

    private SigeproSecurity getSigeproSecurityWSDLPort() {

	InputStream is = SigeproSecurityWebServiceClient.class.getClassLoader().getResourceAsStream("deploy_client.wsdd");
	EngineConfiguration config = new FileProvider(is);
	SigeproSecurityService service = new SigeproSecurityServiceLocator(config);
	SigeproSecuritySoap11Stub port;
	try {
	    URL sigeprosecurtyUrl = new URL(sigeproSecurityUrl);
	    port = (SigeproSecuritySoap11Stub) service.getsigeproSecuritySoap11(sigeprosecurtyUrl);
	    port.setUsername(sigeproSecurityUserId);
	    port.setPassword(sigeproSecurityUserPassword);
	    port.setTimeout(5000);
	    return port;
	} catch (Exception e) {
	    log.error("getSigeproSecurityWSDLPort(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws sigeprosecurity: " + e.getMessage());
	}
    }

    public Connection getConnection(String idComuneAlias) {

	Connection conn = null;
	GetDbConnectionInfoResponse dbConnInfo = getConnectionProperties(idComuneAlias);
	Properties driverMappings = new Properties();
	String driverClassName = null;
	InputStream isProps = getClass().getClassLoader().getResourceAsStream(DB_CONN_PROPERTYFILE_NAME);
	try {
	    driverMappings.load(isProps);
	    String provider = dbConnInfo.getProvider();
	    driverClassName = driverMappings.getProperty(DB_CONN_PROPERTY_DRIVERNAME_PREFIX + "." + provider);
	    if (StringUtils.isNotEmpty(driverClassName)) {
		Class.forName(driverClassName);
		if (log.isDebugEnabled()) {
		    log.debug("getConnection() - driver {} caricato correttamente.", new Object[] { driverClassName });
		}
	    } else {
		String errMsg = MessageFormat.format("Nessun driver JDBC configurato nel file {0} per il provider {1}", new Object[] {
			DB_CONN_PROPERTYFILE_NAME, provider });
		log.error(errMsg);
		throw new RuntimeException(errMsg);
	    }
	    conn = DriverManager.getConnection(dbConnInfo.getConnectionString(), dbConnInfo.getDbUser(), dbConnInfo.getDbPassword());
	} catch (SQLException e) {
	    String errMsg = MessageFormat
		    .format("Errore durante la creazione della connessione al database di VBG per l''idcomunealias {0}. Connection URL={1}, user={2}, password={3}, provider={4}",
			    new Object[] { idComuneAlias, dbConnInfo.getConnectionString(), dbConnInfo.getDbUser(), dbConnInfo.getDbPassword(),
				    dbConnInfo.getProvider() });
	    log.error("getConnection() - {}", errMsg, e);
	    throw new RuntimeException(errMsg, e);
	} catch (IOException e) {
	    String errMsg = MessageFormat
		    .format("Errore durante la creazione della connessione al database per l''idcomunealias {0}. Impossibile trovare il file {1} con le definizioni dei drivers.",
			    new Object[] { idComuneAlias, DB_CONN_PROPERTYFILE_NAME });
	    log.error("getConnection() - {}", errMsg, e);
	    throw new RuntimeException(errMsg, e);
	} catch (ClassNotFoundException e) {
	    String errMsg = MessageFormat
		    .format("Errore durante la creazione della connessione al database per l''idcomunealias {0}. Il driver per previsto per la connessione non è disponibile, manca la classe Java {1}.",
			    new Object[] { idComuneAlias, driverClassName });
	    log.error("getConnection() - {}", errMsg, e);
	    throw new RuntimeException(errMsg, e);
	}
	return conn;
    }

    public GetDbConnectionInfoResponse getConnectionProperties(String idComuneAlias) {

	GetDbConnectionInfoResponse dbInfo = connectionPropertiesCache.get(idComuneAlias);
	if (dbInfo == null) {
	    try {
		SigeproSecurity securityService = getSigeproSecurityWSDLPort();
		GetDbConnectionInfoRequest req = new GetDbConnectionInfoRequest();
		req.setAlias(idComuneAlias);
		req.setAmbiente(AmbienteType.JAVA);
		dbInfo = securityService.getDbConnectionInfo(req);
		connectionPropertiesCache.put(idComuneAlias, dbInfo);
	    } catch (Exception e) {
		log.error("getConnectionProperties{) - errore durante la chiamata per il recupero dei parametri di connessione al database. {}", e);
		throw new RuntimeException("Errore durante la chiamata per il recupero dei parametri di connessione al database: " + e.getMessage(),
			e);
	    }
	}
	return dbInfo;
    }

    public String getSigeproSecurityUrl() {

	return sigeproSecurityUrl;
    }

    public void setSigeproSecurityUrl(String sigeproSecurityUrl) {

	this.sigeproSecurityUrl = sigeproSecurityUrl;
    }

    public String getSigeproSecurityUserId() {

	return sigeproSecurityUserId;
    }

    public void setSigeproSecurityUserId(String sigeproSecurityUserId) {

	this.sigeproSecurityUserId = sigeproSecurityUserId;
    }

    public String getSigeproSecurityUserPassword() {

	return sigeproSecurityUserPassword;
    }

    public void setSigeproSecurityUserPassword(String sigeproSecurityUserPassword) {

	this.sigeproSecurityUserPassword = sigeproSecurityUserPassword;
    }
}
