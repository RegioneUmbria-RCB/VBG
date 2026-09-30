package it.gruppoinit.nlapec.service.sigeprosecurity;

import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ComunisecurityAttiviType;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceLocator;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecuritySoap11Stub;

import java.io.InputStream;
import java.net.URL;
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

    public String loginAPP(String idcomunealias) {

	log.debug("login(): idcomunealias={}", idcomunealias);
	String token = "";
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    LoginRequest loginRequest = new LoginRequest(idcomunealias, ContestoType.APP, "", "", "");
	    LoginResponse loginResponse = sigeproSecurity.login(loginRequest);
	    token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		throw new RuntimeException("Token nullo");
	    }
	} catch (Exception e) {
	    log.error("loginAPP({}) : {}", new Object[] { idcomunealias, e.getMessage() });
	    log.error("", e);
	    throw new RuntimeException("Errore durante la login al ws sigeprosecurity: " + e.getMessage());
	}
	return token;
    }

    public String refresToken(String token, String idcomunealias) {

	String newToken = token;
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(new CheckTokenRequest(token, true));
	    if (!checkTokenResponse.isValid()) {
		LoginRequest loginRequest = new LoginRequest(idcomunealias, ContestoType.APP, "", "", "");
		LoginResponse loginResponse = sigeproSecurity.login(loginRequest);
		newToken = loginResponse.getToken();
	    }
	} catch (Exception e) {
	    log.error("refresToken({}) : {}", new Object[] { idcomunealias, e.getMessage() });
	    log.error("", e);
	}
	return newToken;
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

    public GetDbConnectionInfoResponse getConnectionInfo(String idcomunealias, String token) {

	log.debug("getConnectionInfo({},{})", new Object[] { idcomunealias, token });
	GetDbConnectionInfoResponse info = null;
	try {
	    // recupero le properties di base
	    Properties dbProps = new Properties();
	    dbProps.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    // recupero le informazioni per la connessione al db
	    info = sigeproSecurity.getDbConnectionInfo(new GetDbConnectionInfoRequest(idcomunealias, AmbienteType.JAVA));
	    // recupero la stringa del provider per selezionare il driver ed il dialect per hibernate
	    return info;
	} catch (Exception e) {
	    log.error("getConnectionInfo({},{}): {}", new Object[] { idcomunealias, token, e.getMessage() });
	    log.error("", e);
	    throw new RuntimeException("getConnectionInfo(): " + e.getMessage());
	}
    }

    public Properties getConnectionProperties(String idcomunealias, String token) {

	log.debug("getConnectionProperties({},{})", new Object[] { idcomunealias, token });
	Properties props = new Properties();
	try {
	    // recupero le properties di base
	    Properties dbProps = new Properties();
	    dbProps.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
	    // inizializzo lo stub per la chiamata al ws
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    // recupero le informazioni per la connessione al db
	    GetDbConnectionInfoResponse info = sigeproSecurity.getDbConnectionInfo(new GetDbConnectionInfoRequest(idcomunealias, AmbienteType.JAVA));
	    // recupero la stringa del provider per selezionare il driver ed il dialect per hibernate
	    String driver = dbProps.getProperty("db.driver." + info.getProvider());
	    // setto le properties per la connessione al db
	    props.setProperty("db_idcomune", info.getIdComune());
	    props.setProperty("db_cnnstring", info.getConnectionString());
	    props.setProperty("db_driver", driver);
	    props.setProperty("db_username", info.getDbUser());
	    props.setProperty("db_password", info.getDbPassword());
	    props.setProperty("db_owner", info.getDbOwner().toUpperCase());
	} catch (Exception e) {
	    log.error("getConnectionProperties({},{}): {}", new Object[] { idcomunealias, token, e.getMessage() });
	    log.error("", e);
	    throw new RuntimeException("getConnectionProperties(): " + e.getMessage());
	}
	return props;
    }

    public Map<String, String> getParams(String param) {

	log.debug("getParams({})", param);
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
	    log.error("getParams({}): {}", new Object[] { param, e.getMessage() });
	    log.error("", e);
	    throw new RuntimeException("Errore durante il recupero dei parametri da sigeprosecurity : " + e.getMessage());
	}
	return params;
    }

    public Map<String, String> getListaComuniAttivi() {

	log.debug("getListaComuniAttivi()");
	Map<String, String> listaComuni = new HashMap<String, String>();
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort();
	    GetSecurityListRequest request = new GetSecurityListRequest();
	    request.setTipo(ComunisecurityAttiviType.ATTIVI);
	    SecurityListType[] listaComuniAttivi = sigeproSecurity.getSecurityList(request);
	    for (SecurityListType comuneAttivo : listaComuniAttivi) {
		listaComuni.put(comuneAttivo.getAlias(), comuneAttivo.getDescrizione());
	    }
	} catch (Exception e) {
	    log.error("getListaComuniAttivi(): errore durante il recupero della lista dei comuni attivi : {}", new Object[] { e.getMessage() });
	    log.error("", e);
	    throw new RuntimeException("Errore durante il recupero della lista dei comuni attivi: " + e.getMessage());
	}
	return listaComuni;
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
