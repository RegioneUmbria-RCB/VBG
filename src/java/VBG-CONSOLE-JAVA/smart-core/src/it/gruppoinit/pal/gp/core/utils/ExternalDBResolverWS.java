package it.gruppoinit.pal.gp.core.utils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.exception.SessionTimeoutException;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.schema.LoginSSORequest;
import it.gruppoinit.sigeprosecurity.schema.LoginSSOResponse;
import it.gruppoinit.sigeprosecurity.schema.LogoutRequest;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe per il settaggio delle properties necessarie a creare una nuova SessionFactory di Hibernate.<br />
 * Questa implementazione contatta il web service Security per il recupero delle proprietà della connessione alla base
 * dati.
 * 
 * @author fabrizioc
 * 
 */
public class ExternalDBResolverWS implements ExternalDBResolver {

    private static final Logger log = LoggerFactory.getLogger(ExternalDBResolverWS.class);
    private Properties configurationProperties;
    private Properties dbProperties;
    private SecurityWSClient securityWSClient;

    public ExternalDBResolverWS() {

	InputStream in = null;
	try {
	    in = this.getClass().getClassLoader().getResourceAsStream(WebConstants.CONFIG_FILES_FOLDER + WebConstants.DB_PROPS);
	    dbProperties = new Properties();
	    dbProperties.load(in);
	} catch (Exception e) {
	    log.error("Errore durante il caricamento del file {}: {}", WebConstants.CONFIG_FILES_FOLDER + WebConstants.DB_PROPS, e.getMessage());
	    throw new RuntimeException("Errore durante il caricamento della configurazione del database: " + e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    /**
     * Properties necessarie per la configurazione della classe <code>
     * <pre>
     * ws.token.url = URL del web service 
     * ws.token.user = userid per la login 
     * ws.token.pwd = password per la login 
     * </pre></code>
     */
    public void setConfigurationProperties(Properties configurationProperties) {

	this.configurationProperties = configurationProperties;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.gruppoinit.pal.gp.core.util.ExternalDBResolver#getConnectionProperties(java.lang.String)
     */
    public Properties getConnectionProperties(String idcomune_alias) {

	if (log.isDebugEnabled()) {
	    log.debug("getConnectionProperties(idcomunealias={}) entering...", idcomune_alias);
	}
	Properties dbProperties = new Properties();
	String driver = "";
	String dialect = "";
	try {
	    // setto le props comuni
	    dbProperties.putAll(this.dbProperties);
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity security = getSecurityWSDLPort();
	    // effettuo l'autenticazione
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(idcomune_alias);
	    loginRequest.setContesto(ContestoType.APP);
	    loginRequest.setIpAddress("");
	    loginRequest.setPassword("");
	    loginRequest.setUsername("");
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(): prima di eseguire la login");
	    }
	    LoginResponse loginResponse = security.login(loginRequest);
	    String token = loginResponse.getToken();
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(): Login effettuata");
	    }
	    if (StringUtils.isBlank(token)) {
		throw new ServletException("method login for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' return null!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(): Token {}", token);
	    }
	    // recupero le informazioni per la connessione al db
	    GetDbConnectionInfoRequest connectionInfoRequest = new GetDbConnectionInfoRequest();
	    connectionInfoRequest.setAlias(idcomune_alias);
	    connectionInfoRequest.setAmbiente(AmbienteType.JAVA);
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(): Prima di chiamare sigeproSecurity.getDbConnectionInfo({},JAVA)", idcomune_alias);
	    }
	    GetDbConnectionInfoResponse info = security.getDbConnectionInfo(connectionInfoRequest);
	    if (info == null) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return null!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(): sigeproSecurity.getDbConnectionInfo chiamata effettuata");
	    }
	    // recupero la stringa del provider per selezionare il driver ed il dialect per hibernate
	    String provider = info.getProvider();
	    if (StringUtils.isBlank(provider)) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return provider null!");
	    }
	    driver = dbProperties.getProperty("db.driver." + provider);
	    if (StringUtils.isBlank(driver)) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return an unknown provider: " + provider);
	    }
	    dialect = dbProperties.getProperty("hibernate.dialect." + provider);
	    if (StringUtils.isBlank(dialect)) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return an unknown provider: " + provider);
	    }
	    // setto le properties aggiuntive
	    if (StringUtils.isBlank(info.getAlias())) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return " + WebConstants.IDCOMUNE_ALIAS + " null!");
	    }
	    dbProperties.setProperty(WebConstants.IDCOMUNE_ALIAS, info.getAlias());
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property idcomunealias={}", idcomune_alias, info.getAlias());
	    }
	    if (StringUtils.isBlank(info.getIdComune())) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return " + WebConstants.IDCOMUNE + " null!");
	    }
	    dbProperties.setProperty(WebConstants.IDCOMUNE, info.getIdComune());
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property idcomune={}", idcomune_alias, info.getIdComune());
	    }
	    if (StringUtils.isBlank(info.getConnectionString())) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return " + WebConstants.HIBERNATE_CONN_URL + " null!");
	    }
	    dbProperties.setProperty(WebConstants.HIBERNATE_CONN_URL, info.getConnectionString());
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property hibernate.connection.url={}", idcomune_alias,
			info.getConnectionString());
	    }
	    dbProperties.setProperty("hibernate.connection.driver_class", driver);
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property hibernate.connection.driver_class={}", idcomune_alias, driver);
	    }
	    if (StringUtils.isBlank(info.getDbUser())) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return DbUser null!");
	    }
	    dbProperties.setProperty("hibernate.connection.username", info.getDbUser());
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property hibernate.connection.username={}", idcomune_alias, info.getDbUser());
	    }
	    if (StringUtils.isBlank(info.getDbPassword())) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return DbPassword null!");
	    }
	    dbProperties.setProperty("hibernate.connection.password", info.getDbPassword());
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property hibernate.connection.password={}", idcomune_alias,
			info.getDbPassword());
	    }
	    dbProperties.setProperty("hibernate.dialect", dialect);
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property hibernate.dialect={}", idcomune_alias, dialect);
	    }
	    if (StringUtils.isBlank(info.getDbOwner())) {
		throw new ServletException("method getDbConnectionInfo for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' and token='"
			+ token + "' return DbOwner null!");
	    }
	    dbProperties.setProperty(WebConstants.HIBERNATE_DEFAULT_SCHEMA, info.getDbOwner().toUpperCase());
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property hibernate.default_schema={}", idcomune_alias, info.getDbOwner());
	    }
	    dbProperties.setProperty(WebConstants.TOKEN, token);
	    if (log.isDebugEnabled()) {
		log.debug("getConnectionProperties(idcomunealias={}) set property token={}", idcomune_alias, token);
	    }
	} catch (Exception e) {
	    String err = "";
	    err = e.getMessage();
	    Throwable t = e.getCause();
	    if (t != null) {
		err = t.getMessage();
	    }
	    log.error("getConnectionProperties(idcomunealias={}): {},{}", new Object[] { idcomune_alias, err, e });
	    throw new RuntimeException("Errore durante il recupero dei parametri di connessione al db: " + err, e);
	}
	return dbProperties;
    }

    @Override
    public Properties checkToken(String token) {

	if (log.isDebugEnabled()) {
	    log.debug("checkToken(token={})...", token);
	}
	CheckTokenResponse checkTokenResponse = null;
	try {
	    SigeproSecurity security = getSecurityWSDLPort();
	    CheckTokenRequest req = new CheckTokenRequest();
	    req.setToken(token);
	    req.setTokenInfo(true);
	    checkTokenResponse = security.checkToken(req);
	} catch (Exception e) {
	    log.error("checkToken({}): {}", token, e.getMessage());
	    String error_msg = "Errore durante la verifica del token: ";
	    throw new RuntimeException(error_msg + token);
	}
	if (!checkTokenResponse.isValid()) {
	    log.error("checkToken({}): Token scaduto o non valido!", token);
	    throw new SessionTimeoutException("Token scaduto o non valido: " + token);
	}
	TokenInfoType tokenInfo = checkTokenResponse.getTokenInfo();
	if (tokenInfo == null || StringUtils.isBlank(tokenInfo.getIdcomune())) {
	    log.error("checkToken({}): tokeninfo is null or idcomune is null!", token);
	    String error_msg = "Errore durante la verifica del token: ";
	    throw new RuntimeException(error_msg + token);
	}
	Properties props = new Properties();
	props.setProperty(WebConstants.IDCOMUNE, tokenInfo.getIdcomune());
	props.setProperty(WebConstants.IDCOMUNE_ALIAS, tokenInfo.getAlias());
	props.setProperty(WebConstants.USER_ID, tokenInfo.getUserid());
	props.setProperty(WebConstants.TOKEN_INFO_CONTESTO, tokenInfo.getContesto().toString());
	props.setProperty(WebConstants.TOKEN_INFO_CLIENT_IP, tokenInfo.getClientIp());
	if (log.isDebugEnabled()) {
	    log.debug("checkToken(token={})...ok, return props: idcomune={}, alias={}, userid={}, contesto={}",
		    new Object[] { token, tokenInfo.getIdcomune(), tokenInfo.getAlias(), tokenInfo.getUserid(), tokenInfo.getContesto() });
	}
	return props;
    }

    @Override
    public boolean checkTokenValidity(String token) {

	try {
	    if (log.isDebugEnabled()) {
		log.debug("checkTokenValidity(token={})...", token);
	    }
	    // se le chiamate sono tante potrebbe metterci troppo. dopo 2 secondi annullo la richiesta
	    SigeproSecurity port = getSecurityWSDLPort();
	    // port.setTimeout(2000); //// TODO
	    //	    Client proxy = ClientProxy.getClient(port);
	    //	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    //	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	    //	    httpClientPolicy.setConnectionTimeout(2000); // Line #2  
	    //	    httpClientPolicy.setReceiveTimeout(2000); // Line #3  
	    //	    conduit.setClient(httpClientPolicy);
	    CheckTokenRequest req = new CheckTokenRequest();
	    req.setToken(token);
	    req.setTokenInfo(false);
	    CheckTokenResponse checkTokenResponse = port.checkToken(req);
	    if (log.isDebugEnabled()) {
		log.debug("checkTokenValidity(token={})...{}", token, checkTokenResponse.isValid());
	    }
	    return checkTokenResponse.isValid();
	} catch (Exception e) {
	    log.warn("checkTokenValidity(token={}): {}", token, e.getMessage());
	    // se non riesce a validare il token torno comunque true
	    return true;
	}
    }

    @Override
    public String getToken(String idcomune_alias) {

	if (log.isDebugEnabled()) {
	    log.debug("getToken(idcomunealias={}) entering...", idcomune_alias);
	}
	try {
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity security = getSecurityWSDLPort();
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(idcomune_alias);
	    loginRequest.setContesto(ContestoType.APP);
	    loginRequest.setIpAddress("");
	    loginRequest.setPassword("");
	    loginRequest.setUsername("");
	    // effettuo l'autenticazione
	    LoginResponse loginResponse = security.login(loginRequest);
	    String token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		log.error("getToken(idcomunealias={}): return null!", idcomune_alias);
		throw new ServletException("method login for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' return null!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getToken(idcomunealias={}) return token: {}", idcomune_alias, token);
	    }
	    return token;
	} catch (Exception e) {
	    log.error("getToken(idcomunealias={}): {}", idcomune_alias, e.getMessage());
	    throw new RuntimeException("Errore durante il recupero del token: " + e.getMessage(), e);
	}
    }

    @Override
    public String getTokenDefaultAlias() {

	String default_alias = configurationProperties.getProperty("ws.token.default.alias");
	if (log.isDebugEnabled()) {
	    log.debug("getTokenDefaultAlias(idcomunealias={}) entering...", default_alias);
	}
	try {
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity security = getSecurityWSDLPort();
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(default_alias);
	    loginRequest.setContesto(ContestoType.APP);
	    loginRequest.setIpAddress("");
	    loginRequest.setPassword("");
	    loginRequest.setUsername("");
	    // effettuo l'autenticazione
	    LoginResponse loginResponse = security.login(loginRequest);
	    String token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		log.error("getTokenDefaultAlias(idcomunealias={}): return null!", default_alias);
		throw new ServletException("method login for " + WebConstants.IDCOMUNE_ALIAS + "='" + default_alias + "' return null!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getTokenDefaultAlias(idcomunealias={}) return token: {}", default_alias, token);
	    }
	    return token;
	} catch (Exception e) {
	    log.error("getTokenDefaultAlias(idcomunealias={}): {}", default_alias, e.getMessage());
	    throw new RuntimeException("Errore durante il recupero del token: " + e.getMessage());
	}
    }

    @Override
    public String getUserToken(String idcomune_alias, String username, String password, String ipAddress) {

	if (log.isDebugEnabled()) {
	    log.debug("getUserToken(idcomunealias={}, username={}, password=xxx, ipaddress={}) entering...", new Object[] { idcomune_alias, username,
		    ipAddress });
	}
	try {
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity security = getSecurityWSDLPort();
	    // effettuo l'autenticazione
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(idcomune_alias);
	    loginRequest.setContesto(ContestoType.OPE);
	    loginRequest.setIpAddress(ipAddress);
	    loginRequest.setPassword(password);
	    loginRequest.setUsername(username);
	    LoginResponse loginResponse = security.login(loginRequest);
	    String token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		log.error("getUserToken(idcomunealias={}, username={}, password=xxx, ipaddress={}): return null!", new Object[] { idcomune_alias,
			username, ipAddress });
		throw new ServletException("method login for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' return null!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getUserToken(idcomunealias={}, username={}, password=xxx, ipaddress={}) token ok: {}", new Object[] { idcomune_alias,
			username, ipAddress, token });
	    }
	    return token;
	} catch (Exception e) {
	    log.error("getUserToken(idcomunealias={}, username={}, password=xxx, ipaddress={}): {}", new Object[] { idcomune_alias, username,
		    ipAddress, e.getMessage() });
	    throw new RuntimeException("Errore durante il recupero del token utente: " + e.getMessage());
	}
    }

    public SigeproSecurity getSecurityWSDLPort() throws Exception {

	if (log.isDebugEnabled()) {
	    log.debug(
		    "getSecurityWSDLPort(): ws.token.url={}, ws.token.user={}, ws.token.pwd={}",
		    new Object[] { this.configurationProperties.getProperty("ws.token.url"),
			    this.configurationProperties.getProperty("ws.token.user"), this.configurationProperties.getProperty("ws.token.pwd") });
	}
	// 15 sec (ricavato da stress test su security)
	return securityWSClient.getWsPort();
	//	}
    }

    @Autowired
    public void setSecurityWSClient(SecurityWSClient securityWSClient) {

	this.securityWSClient = securityWSClient;
    }

    @Override
    public String getUserUTEToken(String idcomune_alias, String username, String ipAddress) {

	if (log.isDebugEnabled()) {
	    log.debug("getUserUTEToken(idcomunealias={}, username={}, ipaddress={}) entering...",
		    new Object[] { idcomune_alias, username, ipAddress });
	}
	try {
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity security = getSecurityWSDLPort();
	    // effettuo l'autenticazione
	    LoginSSORequest loginRequest = new LoginSSORequest();
	    loginRequest.setAlias(idcomune_alias);
	    loginRequest.setContesto(ContestoType.UTE);
	    loginRequest.setIpAddress(ipAddress);
	    loginRequest.setUsername(username);
	    LoginSSOResponse loginResponse = security.loginSSO(loginRequest);
	    String token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		log.error("getUserUTEToken(idcomunealias={}, username={}, ipaddress={}): return null!", new Object[] { idcomune_alias, username,
			ipAddress });
		throw new ServletException("method login for " + WebConstants.IDCOMUNE_ALIAS + "='" + idcomune_alias + "' return null!");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("getUserUTEToken(idcomunealias={}, username={}, ipaddress={}) token ok: {}", new Object[] { idcomune_alias, username,
			ipAddress, token });
	    }
	    return token;
	} catch (Exception e) {
	    log.error("getUserUTEToken(idcomunealias={}, username={}, ipaddress={}): {}",
		    new Object[] { idcomune_alias, username, ipAddress, e.getMessage() });
	    throw new RuntimeException("Errore durante il recupero del token utente: " + e.getMessage());
	}
    }

    @Override
    public void invalidateToken(String token) {

	try {
	    SigeproSecurity security = getSecurityWSDLPort();
	    LogoutRequest req = new LogoutRequest();
	    req.setToken(token);
	    security.logout(req);
	} catch (Exception e) {
	    log.error("{}", new Object[] { e });
	}
    }
}
