package it.gruppoinit.sigeprosecurity.ws;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ComunisecurityAttiviType;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;

public class SigeproSecurityWSClient {

    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityWSClient.class);
    private String sigeproSecurityUrl;
    private String sigeproSecurityUserId;
    private String sigeproSecurityUserPassword;
    private long timeout = 15000;
    private SigeproSecurity sigeproSecurityPort;

    public String loginAPP(String alias) {

	String token = "";
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(alias);
	    loginRequest.setContesto(ContestoType.APP);
	    loginRequest.setIpAddress("");
	    loginRequest.setPassword("");
	    loginRequest.setUsername("");
	    log.debug("login(): idcomunealias={}", alias);
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

    public void checkToken(String token) {

	log.debug("checkToken({})", token);
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	    checkTokenRequest.setToken(token);
	    checkTokenRequest.setTokenInfo(false);
	    CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(checkTokenRequest);
	    if (!checkTokenResponse.isValid()) {
		log.error("checkToken({}): token non valido", token);
		throw new RuntimeException("Token scaduto o non valido: " + token);
	    }
	} catch (Exception e) {
	    log.error("checkToken({}): errore durante la verifica del token: {}", token, e);
	    throw new RuntimeException("Errore durante la verifica del token: " + e.getMessage(), e);
	}
    }

    public TokenInfoType getTokenInfo(String token) {

	TokenInfoType tokenInfo = null;
	log.debug("checkToken({})", token);
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	    checkTokenRequest.setToken(token);
	    checkTokenRequest.setTokenInfo(true);
	    CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(checkTokenRequest);
	    if (!checkTokenResponse.isValid()) {
		log.error("checkToken({}): token non valido", token);
		throw new RuntimeException("Token scaduto o non valido: " + token);
	    }
	    tokenInfo = checkTokenResponse.getTokenInfo();
	    log.debug("checkToken({}) return: idcomune={}, alias={}, userid={}, contesto={}",
		    new Object[] { token, tokenInfo.getIdcomune(), tokenInfo.getAlias(), tokenInfo.getUserid(), tokenInfo.getContesto() });
	    return tokenInfo;
	} catch (Exception e) {
	    log.error("checkToken({}): errore durante la verifica del token", token);
	    throw new RuntimeException("Errore durante la verifica del token: " + e.getMessage());
	}
    }

    public Map<String, String> getParams(String param) {

	Map<String, String> params = new HashMap<String, String>();
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    GetApplicationInfoRequest req = new GetApplicationInfoRequest();
	    if (StringUtils.isNotBlank(param)) {
		req.setParam(param);
	    }
	    GetApplicationInfoResponse applicationInfoResponse = sigeproSecurity.getApplicationInfo(req);
	    List<ApplicationInfoType> applicationInfoTypes = applicationInfoResponse.getApplicationInfo();
	    for (ApplicationInfoType applicationInfoType : applicationInfoTypes) {
		params.put(applicationInfoType.getParam(), applicationInfoType.getValue());
		log.debug("getParams(): param [{}={}]", applicationInfoType.getParam(), applicationInfoType.getValue());
	    }
	} catch (Exception e) {
	    log.error("getParams(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante il recupero dei parametri da sigeprosecurity");
	}
	return params;
    }

    private SigeproSecurity getSigeproSecurityWSDLPort() {

	log.debug("getStcWsPort: url={}", sigeproSecurityUrl);
	if (this.sigeproSecurityPort == null) {
	    try {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(SigeproSecurity.class);
		factory.setAddress(sigeproSecurityUrl);
		this.sigeproSecurityPort = (SigeproSecurity) factory.create();
		Client proxy = ClientProxy.getClient(sigeproSecurityPort);
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
		BindingProvider bp = (BindingProvider) sigeproSecurityPort;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, sigeproSecurityUrl);
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
		httpClientPolicy.setConnectionTimeout(this.timeout); // Line #2  
		httpClientPolicy.setReceiveTimeout(this.timeout); // Line #3  
		conduit.setClient(httpClientPolicy);
		Endpoint securityEndpoint = proxy.getEndpoint();
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest"); // WSConstants.PASSWORD_DIGEST ???
		outProps.put(WSHandlerConstants.USER, this.sigeproSecurityUserId);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new SecurityPwdCallBackHandler(sigeproSecurityUserId, sigeproSecurityUserPassword));
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		securityEndpoint.getOutInterceptors().add(wssOut);
		return sigeproSecurityPort;
	    } catch (Exception e) {
		log.error("getSigeproSecurityWSDLPort(): {}", e.getMessage());
		throw new RuntimeException("Errore durante l'inizializzazione della chiamata al ws sigeprosecurity: " + e.getMessage());
	    }
	}
	return this.sigeproSecurityPort;
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
		String errMsg = MessageFormat.format("Nessun driver JDBC configurato nel file {0} per il provider {1}",
			new Object[] { DB_CONN_PROPERTYFILE_NAME, provider });
		log.error(errMsg);
		throw new RuntimeException(errMsg);
	    }
	    conn = DriverManager.getConnection(dbConnInfo.getConnectionString(), dbConnInfo.getDbUser(), dbConnInfo.getDbPassword());
	} catch (SQLException e) {
	    String errMsg = MessageFormat.format(
		    "Errore durante la creazione della connessione al database di VBG per l''idcomunealias {0}. Connection URL={1}, user={2}, password={3}, provider={4}",
		    new Object[] { idComuneAlias, dbConnInfo.getConnectionString(), dbConnInfo.getDbUser(), dbConnInfo.getDbPassword(),
			    dbConnInfo.getProvider() });
	    log.error("getConnection() - {}", errMsg, e);
	    throw new RuntimeException(errMsg, e);
	} catch (IOException e) {
	    String errMsg = MessageFormat.format(
		    "Errore durante la creazione della connessione al database per l''idcomunealias {0}. Impossibile trovare il file {1} con le definizioni dei drivers.",
		    new Object[] { idComuneAlias, DB_CONN_PROPERTYFILE_NAME });
	    log.error("getConnection() - {}", errMsg, e);
	    throw new RuntimeException(errMsg, e);
	} catch (ClassNotFoundException e) {
	    String errMsg = MessageFormat.format(
		    "Errore durante la creazione della connessione al database per l''idcomunealias {0}. Il driver per previsto per la connessione non è disponibile, manca la classe Java {1}.",
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

    public List<SecurityListType> getSecuritylist(String alias) {

	try {
	    log.debug("getSecurityList()");
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    GetSecurityListRequest getSecurityListRequest = new GetSecurityListRequest();
	    getSecurityListRequest.setAlias(alias);
	    getSecurityListRequest.setTipo(ComunisecurityAttiviType.ATTIVI);
	    GetSecurityListResponse securityList = sigeproSecurity.getSecurityList(getSecurityListRequest);
	    List<SecurityListType> risultato = securityList.getSecurity();
	    if (risultato == null) {
		return new ArrayList<SecurityListType>();
	    } else {
		return risultato;
	    }
	} catch (Exception e) {
	    log.error("getSecurityList()", e.getMessage());
	    throw new RuntimeException("Errore durante getSecurityList al ws sigeprosecurity: " + e.getMessage());
	}
    }

    private Map<String, GetDbConnectionInfoResponse> connectionPropertiesCache = new HashMap<String, GetDbConnectionInfoResponse>();
    private String DB_CONN_PROPERTY_DRIVERNAME_PREFIX = "db.driver";
    private String DB_CONN_PROPERTYFILE_NAME = "db.properties";

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
