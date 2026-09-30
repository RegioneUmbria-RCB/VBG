package it.gruppoinit.sigeprosecurity.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.WSS4JConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gruppoinit.sigeprosecurity.schema.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetApplicationInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;

@Service
public class SigeproSecurityWSClient implements ISecurityClient {

    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityWSClient.class);
    private long timeout = 30000;
    private SigeproSecurity sigeproSecurityPort;

    @Override
    public String loginAPP(SecurityConfig config) {

	String token = "";
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort(config);
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(config.getAlias());
	    loginRequest.setContesto(config.getContesto());
	    loginRequest.setIpAddress("");
	    loginRequest.setPassword("");
	    loginRequest.setUsername("");
	    log.debug("login(): idcomunealias={}", config.getAlias());
	    LoginResponse loginResponse = sigeproSecurity.login(loginRequest);
	    token = loginResponse.getToken();
	    if (StringUtils.isBlank(token)) {
		throw new SecurityException("Token nullo");
	    }
	} catch (Exception e) {
	    log.error("login(): {}", e.getMessage());
	    throw new SecurityException("Errore durante la login al ws sigeprosecurity: " + e.getMessage(), e);
	}
	return token;
    }

    @Override
    public void checkToken(SecurityConfig config, String token) {

	log.debug("checkToken({})", token);
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort(config);
	    CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	    checkTokenRequest.setToken(token);
	    checkTokenRequest.setTokenInfo(false);
	    CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(checkTokenRequest);
	    if (!checkTokenResponse.isValid()) {
		log.error("checkToken({}): token non valido", token);
		throw new SecurityException("Token scaduto o non valido: " + token);
	    }
	} catch (Exception e) {
	    log.error("checkToken({}): errore durante la verifica del token: {}", token, e);
	    throw new SecurityException("Errore durante la verifica del token: " + e.getMessage(), e);
	}
    }

    @Override
    public TokenInfoType getTokenInfo(SecurityConfig config, String token) {

	TokenInfoType tokenInfo = null;
	log.debug("checkToken({})", token);
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort(config);
	    CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	    checkTokenRequest.setToken(token);
	    checkTokenRequest.setTokenInfo(true);
	    CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(checkTokenRequest);
	    if (!checkTokenResponse.isValid()) {
		log.error("checkToken({}): token non valido", token);
		throw new SecurityException("Token scaduto o non valido: " + token);
	    }
	    tokenInfo = checkTokenResponse.getTokenInfo();
	    log.debug("checkToken({}) return: idcomune={}, alias={}, userid={}, contesto={}", token, tokenInfo.getIdcomune(), tokenInfo.getAlias(),
		    tokenInfo.getUserid(), tokenInfo.getContesto());
	    return tokenInfo;
	} catch (Exception e) {
	    log.error("checkToken({}): errore durante la verifica del token", token);
	    throw new SecurityException("Errore durante la verifica del token: " + e.getMessage(), e);
	}
    }

    @Override
    public Map<String, String> getParams(SecurityConfig config, String param) {

	Map<String, String> params = new HashMap<>();
	try {
	    SigeproSecurity sigeproSecurity = getSigeproSecurityWSDLPort(config);
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
	    throw new SecurityException("Errore durante il recupero dei parametri da sigeprosecurity", e);
	}
	return params;
    }

    @Override
    public Map<String, String> getParams(SecurityConfig config) {

	return getParams(config, null);
    }

    private SigeproSecurity getSigeproSecurityWSDLPort(SecurityConfig config) {

	log.debug("getSigeproSecurityWSDLPort: url={}", config.getUrl());
	if (this.sigeproSecurityPort == null) {
	    SigeproSecurityService sigeproSecurityService = null;
	    try {
		sigeproSecurityService = new SigeproSecurityService();
		sigeproSecurityPort = sigeproSecurityService.getSigeproSecuritySoap11();
		Client proxy = ClientProxy.getClient(sigeproSecurityPort);
		BindingProvider bp = (BindingProvider) sigeproSecurityPort;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, config.getUrl());
		Endpoint cxfEndpoint = proxy.getEndpoint();
		Map<String, Object> outProps = new HashMap<>();
		outProps.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
		outProps.put(ConfigurationConstants.USER, config.getUserId());
		outProps.put(ConfigurationConstants.PASSWORD_TYPE, WSS4JConstants.PW_DIGEST);
		SecurityPwdCallBackHandler pwdCallback = new SecurityPwdCallBackHandler(config.getUserId(), config.getPassword());
		outProps.put(ConfigurationConstants.PW_CALLBACK_REF, pwdCallback);
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
		cxfEndpoint.getOutInterceptors().add(wssOut);
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(timeout);
		httpClientPolicy.setReceiveTimeout(timeout);
		conduit.setClient(httpClientPolicy);
		log.debug("getSigeproSecurityWSDLPort(): porta creata");
	    } catch (Exception e) {
		log.error("getSigeproSecurityWSDLPort(): {}", e.getMessage());
		throw new SecurityException("Errore durante l'inizializzazione della chiamata al ws sigeprosecurity: " + e.getMessage(), e);
	    }
	}
	return this.sigeproSecurityPort;
    }

    public long getTimeout() {

	return timeout;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }
}
