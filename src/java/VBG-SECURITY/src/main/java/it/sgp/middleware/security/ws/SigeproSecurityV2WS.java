package it.sgp.middleware.security.ws;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

import it.sgp.middleware.security.domain.AmbienteEnum;
import it.sgp.middleware.security.domain.CheckTokenResult;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityParam;
import it.sgp.middleware.security.domain.ComunisecurityTpartnerapp;
import it.sgp.middleware.security.domain.ContestoEnum;
import it.sgp.middleware.security.domain.DBConnectionInfo;
import it.sgp.middleware.security.exceptions.AmbienteNonTrovatoException;
import it.sgp.middleware.security.exceptions.InvalidCredentialsException;
import it.sgp.middleware.security.service.ComunisecurityParamService;
import it.sgp.middleware.security.service.ComunisecurityService;
import it.sgp.middleware.security.service.ComunisecuritySessionService;
import it.sgp.middleware.security.service.ComunisecurityTpartnerappService;
import it.sgp.middleware.security.service.LoginService;
import it.sgp.middleware.security.ws.schema.v2.ApplicationInfoType;
import it.sgp.middleware.security.ws.schema.v2.CheckTokenRequest;
import it.sgp.middleware.security.ws.schema.v2.CheckTokenResponse;
import it.sgp.middleware.security.ws.schema.v2.ComunisecurityAttiviType;
import it.sgp.middleware.security.ws.schema.v2.ContestoType;
import it.sgp.middleware.security.ws.schema.v2.GetApplicationInfoRequest;
import it.sgp.middleware.security.ws.schema.v2.GetApplicationInfoResponse;
import it.sgp.middleware.security.ws.schema.v2.GetAuthLevelRequest;
import it.sgp.middleware.security.ws.schema.v2.GetAuthLevelResponse;
import it.sgp.middleware.security.ws.schema.v2.GetDbConnectionInfoRequest;
import it.sgp.middleware.security.ws.schema.v2.GetDbConnectionInfoResponse;
import it.sgp.middleware.security.ws.schema.v2.GetSecurityListRequest;
import it.sgp.middleware.security.ws.schema.v2.GetSecurityListResponse;
import it.sgp.middleware.security.ws.schema.v2.GetTokenPartnerAppPerComuneESoftwareRequest;
import it.sgp.middleware.security.ws.schema.v2.GetTokenPartnerAppPerComuneESoftwareResponse;
import it.sgp.middleware.security.ws.schema.v2.GetTokenPartnerAppRequest;
import it.sgp.middleware.security.ws.schema.v2.GetTokenPartnerAppResponse;
import it.sgp.middleware.security.ws.schema.v2.LoginRequest;
import it.sgp.middleware.security.ws.schema.v2.LoginResponse;
import it.sgp.middleware.security.ws.schema.v2.LoginSSORequest;
import it.sgp.middleware.security.ws.schema.v2.LoginSSOResponse;
import it.sgp.middleware.security.ws.schema.v2.LogoutRequest;
import it.sgp.middleware.security.ws.schema.v2.LogoutResponse;
import it.sgp.middleware.security.ws.schema.v2.SecurityListType;
import it.sgp.middleware.security.ws.schema.v2.SetAuthLevelRequest;
import it.sgp.middleware.security.ws.schema.v2.SetAuthLevelResponse;
import it.sgp.middleware.security.ws.schema.v2.SetTokenPartnerAppPerComuneESoftwareRequest;
import it.sgp.middleware.security.ws.schema.v2.SetTokenPartnerAppPerComuneESoftwareResponse;
import it.sgp.middleware.security.ws.schema.v2.SetTokenPartnerAppRequest;
import it.sgp.middleware.security.ws.schema.v2.SetTokenPartnerAppResponse;
import it.sgp.middleware.security.ws.schema.v2.TokenInfoType;
import jakarta.servlet.http.HttpServletRequest;

@Endpoint
public class SigeproSecurityV2WS {

    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityV2WS.class);
    private static final String MESSAGES_NAMESPACE = "http://sigeprosecurity.retelit.it/schema";
    private static final String LOGIN_SSO = "LoginSSORequest";
    private static final String LOGIN = "LoginRequest";
    private static final String CHECK_TOKEN = "CheckTokenRequest";
    private static final String SET_TOKEN_PARTNER_APP = "SetTokenPartnerAppRequest";
    private static final String GET_TOKEN_PARTNER_APP = "GetTokenPartnerAppRequest";
    private static final String SET_AUTH_LEVEL = "SetAuthLevelRequest";
    private static final String GET_AUTH_LEVEL = "GetAuthLevelRequest";
    private static final String LOGOUT = "LogoutRequest";
    private static final String DB_CONNECTION_INFO = "GetDbConnectionInfoRequest";
    private static final String APPLICATION_INFO = "GetApplicationInfoRequest";
    private static final String SECURITY_LIST = "GetSecurityListRequest";
    private static final String SET_TOKEN_PARTNER_APP_PER_COMUNE_SOFTWARE = "SetTokenPartnerAppPerComuneESoftwareRequest";
    private static final String GET_TOKEN_PARTNER_APP_PER_COMUNE_SOFTWARE = "GetTokenPartnerAppPerComuneESoftwareRequest";
    private ComunisecurityParamService comunisecurityParamService;
    private ComunisecurityService comunisecurityService;
    private ComunisecuritySessionService comunisecuritySessionService;
    private LoginService loginService;
    private ComunisecurityTpartnerappService comunisecurityTpartnerappService;

    @Autowired
    public void setComunisecurityTpartnerappService(ComunisecurityTpartnerappService comunisecurityTpartnerappService) {

	this.comunisecurityTpartnerappService = comunisecurityTpartnerappService;
    }

    @Autowired
    public void setComunisecurityParamService(ComunisecurityParamService comunisecurityParamService) {

	this.comunisecurityParamService = comunisecurityParamService;
    }

    @Autowired
    public void setComunisecurityService(ComunisecurityService comunisecurityService) {

	this.comunisecurityService = comunisecurityService;
    }

    @Autowired
    public void setLoginService(LoginService loginService) {

	this.loginService = loginService;
    }

    @Autowired
    public void setComunisecuritySessionService(ComunisecuritySessionService comunisecuritySessionService) {

	this.comunisecuritySessionService = comunisecuritySessionService;
    }

    @PayloadRoot(localPart = LOGIN, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload /* BOOT-DOC-ADD AGGIUNGERE RESPONSEPAYLOAD */
    public LoginResponse login(@RequestPayload /* BOOT-DOC-ADD AGGIUNGERE REQUESTPAYLOAD */ LoginRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("LoginRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	LoginResponse response = new LoginResponse();
	String token = "";
	try {
	    ContestoEnum contesto = Enum.valueOf(ContestoEnum.class, request.getContesto().toString());
	    if (contesto.compareTo(ContestoEnum.APP) == 0) {
		token = loginService.loginApp(request.getAlias(), getClientIpAddress(request.getIpAddress()));
	    } else {
		token = loginService.login(request.getAlias(), contesto, request.getUsername(), request.getPassword(),
			getClientIpAddress(request.getIpAddress()), false);
	    }
	} catch (InvalidCredentialsException e) {
	    log.warn("login():{}, username:{}, password:{}", new Object[] { e.getMessage(), request.getUsername(), request.getPassword() });
	}
	response.setToken(token);
	return response;
    }

    @PayloadRoot(localPart = LOGIN_SSO, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public LoginSSOResponse loginSSO(@RequestPayload LoginSSORequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("LoginSSORequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	LoginSSOResponse response = new LoginSSOResponse();
	String token = "";
	try {
	    token = loginService.login(request.getAlias(), Enum.valueOf(ContestoEnum.class, request.getContesto().toString()), request.getUsername(),
		    null, getClientIpAddress(request.getIpAddress()), true);
	} catch (InvalidCredentialsException e) {
	    log.warn("loginSSO():{}, username:{}", e.getMessage(), request.getUsername());
	}
	response.setToken(token);
	return response;
    }

    @PayloadRoot(localPart = CHECK_TOKEN, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public CheckTokenResponse checkToken(@RequestPayload CheckTokenRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("CheckTokenRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	CheckTokenResponse response = new CheckTokenResponse();
	CheckTokenResult result = loginService.checkToken(request.getToken());
	response.setValid(result.isValid());
	if (request.isTokenInfo() && result.getSession() != null) {
	    TokenInfoType tokenInfoType = new TokenInfoType();
	    tokenInfoType.setAlias(result.getSession().getAlias());
	    tokenInfoType.setClientIp(result.getSession().getClientIp());
	    tokenInfoType.setContesto(Enum.valueOf(ContestoType.class, result.getSession().getContesto().toString()));
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	    tokenInfoType.setFirstrequest(sdf.format(result.getSession().getFirstrequest()));
	    tokenInfoType.setLastrequest(sdf.format(result.getSession().getLastrequest()));
	    tokenInfoType.setIdcomune(result.getSession().getIdcomune());
	    tokenInfoType.setUserid(result.getSession().getUserid());
	    response.setTokenInfo(tokenInfoType);
	}
	return response;
    }

    @PayloadRoot(localPart = LOGOUT, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public LogoutResponse logout(@RequestPayload LogoutRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("LogoutRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	LogoutResponse response = new LogoutResponse();
	boolean success = false;
	try {
	    success = loginService.logout(request.getToken());
	} catch (Exception e) {
	    log.error("logout(): {}", e.getMessage());
	}
	response.setSuccess(success);
	return response;
    }

    @PayloadRoot(localPart = DB_CONNECTION_INFO, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetDbConnectionInfoResponse getDbConnectionInfo(@RequestPayload GetDbConnectionInfoRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("GetDbConnectionInfoRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	GetDbConnectionInfoResponse response = new GetDbConnectionInfoResponse();
	DBConnectionInfo info = null;
	try {
	    info = loginService.getDBConnectionInfo(request.getAlias(), Enum.valueOf(AmbienteEnum.class, request.getAmbiente().toString()));
	    response.setAlias(info.getAlias());
	    response.setConnectionString(info.getConnectionString());
	    response.setDbMsName(info.getDbMSName());
	    response.setDbOwner(info.getDbOwner());
	    response.setDbPassword(info.getDbPassword());
	    response.setDbUser(info.getDbUser());
	    response.setIdComune(info.getIdComune());
	    response.setProvider(info.getProvider());
	} catch (AmbienteNonTrovatoException e) {
	    log.error("getDbConnectionInfo(): " + e.getMessage(), e);
	}
	return response;
    }

    @PayloadRoot(localPart = APPLICATION_INFO, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetApplicationInfoResponse getApplicationInfo(@RequestPayload GetApplicationInfoRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("GetApplicationInfoRequest(param:{})", request.getParam());
	}
	GetApplicationInfoResponse response = new GetApplicationInfoResponse();
	String param = request.getParam();
	if (StringUtils.isNotBlank(param)) {
	    ComunisecurityParam comunisecurityParam = comunisecurityParamService.findById(param);
	    if (null != comunisecurityParam) {
		ApplicationInfoType elem = new ApplicationInfoType();
		elem.setParam(comunisecurityParam.getId());
		elem.setValue(comunisecurityParam.getValue());
		response.getApplicationInfo().add(elem);
	    }
	} else {
	    List<ComunisecurityParam> params = loginService.getApplicationInfo();
	    if (null != params) {
		for (ComunisecurityParam comunisecurityParam : params) {
		    ApplicationInfoType elem = new ApplicationInfoType();
		    elem.setParam(comunisecurityParam.getId());
		    elem.setValue(comunisecurityParam.getValue());
		    response.getApplicationInfo().add(elem);
		}
	    }
	}
	return response;
    }

    @PayloadRoot(localPart = SECURITY_LIST, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetSecurityListResponse getSecurityList(@RequestPayload GetSecurityListRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("GetSecurityListRequest:(tipo={})", request.getTipo());
	}
	GetSecurityListResponse response = new GetSecurityListResponse();
	if (StringUtils.isNotBlank(request.getAlias())) {
	    Comunisecurity comunisecurity = comunisecurityService.findById(request.getAlias());
	    SecurityListType attivazione = new SecurityListType();
	    attivazione.setAlias(comunisecurity.getId());
	    attivazione.setAttivo(comunisecurity.getAttivo());
	    attivazione.setDescrizione(comunisecurity.getDescrizione());
	    response.getSecurity().add(attivazione);
	} else {
	    List<Comunisecurity> attivazioni = loginService.getSecurityList();
	    ComunisecurityAttiviType tipo = request.getTipo();
	    if (null == tipo) {
		tipo = ComunisecurityAttiviType.TUTTI;
	    }
	    boolean addtoList = false;
	    if (null != attivazioni) {
		for (Comunisecurity comunisecurity : attivazioni) {
		    addtoList = false;
		    if (tipo.equals(ComunisecurityAttiviType.ATTIVI) && BooleanUtils.isTrue(comunisecurity.getAttivo())) {
			addtoList = true;
		    } else if (tipo.equals(ComunisecurityAttiviType.DISATTIVATI) && BooleanUtils.isFalse(comunisecurity.getAttivo())) {
			addtoList = true;
		    } else if (tipo.equals(ComunisecurityAttiviType.TUTTI)) {
			addtoList = true;
		    }
		    if (addtoList) {
			SecurityListType attivazione = new SecurityListType();
			attivazione.setAlias(comunisecurity.getId());
			attivazione.setAttivo(comunisecurity.getAttivo());
			attivazione.setDescrizione(comunisecurity.getDescrizione());
			response.getSecurity().add(attivazione);
		    }
		}
	    }
	}
	return response;
    }

    @PayloadRoot(localPart = SET_TOKEN_PARTNER_APP, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public SetTokenPartnerAppResponse setTokenPartnerApp(@RequestPayload SetTokenPartnerAppRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("SetTokenPartnerAppRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	SetTokenPartnerAppResponse response = new SetTokenPartnerAppResponse();
	try {
	    comunisecuritySessionService.setTokenPartnerApp(request.getToken(), request.getTokenPartnerApp());
	    response.setCode(BigInteger.ZERO);
	} catch (Exception e) {
	    response.setCode(BigInteger.valueOf(500));
	    response.setMessage("Si è verificato un errore nel salvataggio dell'informazione. " + e.getMessage());
	    log.error("SetTokenPartnerAppRequest(): {}", e.getMessage());
	}
	return response;
    }

    @PayloadRoot(localPart = GET_TOKEN_PARTNER_APP, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetTokenPartnerAppResponse getTokenPartnerApp(@RequestPayload GetTokenPartnerAppRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("getTokenPartnerAppRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	GetTokenPartnerAppResponse response = new GetTokenPartnerAppResponse();
	String success = null;
	try {
	    success = comunisecuritySessionService.getTokenPartnerApp(request.getToken());
	} catch (Exception e) {
	    log.error("getTokenPartnerAppRequest(): {}", e.getMessage());
	    throw new RuntimeException(e);
	}
	response.setTokenPartnerApp(success);
	return response;
    }

    @PayloadRoot(localPart = SET_AUTH_LEVEL, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public SetAuthLevelResponse setAuthLevel(@RequestPayload SetAuthLevelRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("setAuthLevel: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	try {
	    SetAuthLevelResponse response = new SetAuthLevelResponse();
	    comunisecuritySessionService.setAuthLevel(request.getToken(), request.getAuthlevel().intValue());
	    response.setSuccess(true);
	    return response;
	} catch (Exception e) {
	    log.error("setAuthLevel", e);
	    throw new RuntimeException("[SECURITY] Errore durante l'esecuzione del metodo setAuthLevel.");
	}
    }

    @PayloadRoot(localPart = GET_AUTH_LEVEL, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetAuthLevelResponse getAuthLevel(@RequestPayload GetAuthLevelRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("getAuthLevel: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	try {
	    GetAuthLevelResponse response = new GetAuthLevelResponse();
	    Integer authLevel = comunisecuritySessionService.getAuthLevel(request.getToken());
	    if (authLevel == null) {
		authLevel = Integer.valueOf(1);// "Utente non identificato"
	    }
	    response.setAuthlevel(BigInteger.valueOf(authLevel.intValue()));
	    return response;
	} catch (Exception e) {
	    log.error("getAuthLevel", e);
	    throw new RuntimeException("[SECURITY] Errore durante l'esecuzione del metodo getAuthLevel.");
	}
    }

    private String getClientIpAddress(String ipAddress) {

	if (StringUtils.isBlank(ipAddress)) {
	    TransportContext context = TransportContextHolder.getTransportContext();
	    HttpServletConnection connection = (HttpServletConnection) context.getConnection();
	    HttpServletRequest request = connection.getHttpServletRequest();
	    return request.getRemoteAddr();
	}
	return ipAddress;
    }

    @PayloadRoot(localPart = SET_TOKEN_PARTNER_APP_PER_COMUNE_SOFTWARE, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public SetTokenPartnerAppPerComuneESoftwareResponse setTokenPartnerAppPerComuneESoftwareRequest(
	    @RequestPayload SetTokenPartnerAppPerComuneESoftwareRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("SetTokenPartnerAppPerComuneESoftwareRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	SetTokenPartnerAppPerComuneESoftwareResponse response = new SetTokenPartnerAppPerComuneESoftwareResponse();
	try {
	    validaRequest(request);
	    List<ComunisecurityTpartnerapp> tapps = comunisecurityTpartnerappService.findByTokenComuneESoftware(request.getToken(),
		    request.getCodicecomune(), request.getSoftware());
	    if (tapps.size() > 0) {
		ComunisecurityTpartnerapp entity = tapps.get(0);
		entity.setTokenpartnerapp(request.getTokenPartnerApp());
		comunisecurityTpartnerappService.update(entity);
	    } else {
		ComunisecurityTpartnerapp entity = new ComunisecurityTpartnerapp();
		entity.setToken(request.getToken());
		entity.setCodicecomune(request.getCodicecomune());
		entity.setSoftware(request.getSoftware());
		entity.setTokenpartnerapp(request.getTokenPartnerApp());
		comunisecurityTpartnerappService.insert(entity);
	    }
	    response.setCode(BigInteger.ZERO);
	} catch (Exception e) {
	    response.setCode(BigInteger.valueOf(500));
	    response.setMessage("Si è verificato un errore. " + e.getMessage());
	    log.error("SetTokenPartnerAppPerComuneESoftwareRequest(): {}", e.getMessage());
	}
	return response;
    }

    private void validaRequest(SetTokenPartnerAppPerComuneESoftwareRequest request) {

	if (StringUtils.isBlank(request.getToken())) {
	    throw new RuntimeException("Token obbligatorio");
	}
	if (StringUtils.isBlank(request.getTokenPartnerApp())) {
	    throw new RuntimeException("Token partner app obbligatorio");
	}
	if (StringUtils.isBlank(request.getSoftware())) {
	    throw new RuntimeException("Software obbligatorio");
	}
    }

    @PayloadRoot(localPart = GET_TOKEN_PARTNER_APP_PER_COMUNE_SOFTWARE, namespace = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetTokenPartnerAppPerComuneESoftwareResponse getTokenPartnerAppPerComuneESoftwareRequest(
	    @RequestPayload GetTokenPartnerAppPerComuneESoftwareRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("getTokenPartnerAppPerComuneESoftwareRequest: {}", ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
	}
	GetTokenPartnerAppPerComuneESoftwareResponse response = new GetTokenPartnerAppPerComuneESoftwareResponse();
	String success = null;
	try {
	    success = comunisecurityTpartnerappService.getTokenPartnerAppPerComuneESoftware(request.getToken(), request.getCodicecomune(),
		    request.getSoftware());
	    if (StringUtils.isBlank(success)) {
		success = comunisecurityTpartnerappService.getTokenPartnerAppPerComuneESoftware(request.getToken(), request.getCodicecomune(), "TT");
	    }
	    if (StringUtils.isBlank(success)) {
		success = comunisecurityTpartnerappService.getTokenPartnerAppPerComuneESoftware(request.getToken(), null, request.getSoftware());
	    }
	    if (StringUtils.isBlank(success)) {
		success = comunisecurityTpartnerappService.getTokenPartnerAppPerComuneESoftware(request.getToken(), null, "TT");
	    }
	} catch (Exception e) {
	    log.error("getTokenPartnerAppPerComuneESoftwareRequest(): {}", e.getMessage());
	    throw new RuntimeException(e);
	}
	response.setTokenPartnerApp(success);
	return response;
    }
}
