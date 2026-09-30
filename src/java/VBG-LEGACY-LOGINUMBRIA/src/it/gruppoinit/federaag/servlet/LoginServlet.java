package it.gruppoinit.federaag.servlet;

import it.cefriel.icar.inf3.ICARConstants;
import it.cefriel.icar.inf3.web.beans.AuthenticationSessionBean;
import it.gruppoinit.auth.domain.User;
import it.gruppoinit.auth.service.LoginService;
import it.gruppoinit.auth.service.LoginServiceFactory;
import it.gruppoinit.auth.service.RegistrationService;
import it.gruppoinit.auth.service.impl.RegistrationServiceImpl;
import it.gruppoinit.auth.service.impl.TokenLoginService;
import it.gruppoinit.auth.servlet.BaseServlet;
import it.gruppoinit.auth.util.AuthCostants;
import it.gruppoinit.sigepro.schemas.messages.anagrafe.AnagrafeType;
import it.gruppoinit.sigepro.schemas.messages.anagrafe.AnagrafeTypeSesso;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginServlet extends BaseServlet {

    public static final Integer ANONIMO = 0;
    public static final Integer NON_IDENTIFICATO = 1;
    public static final Integer IDENTIFICATO = 2;
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(LoginServlet.class);
    private static ResourceBundle messages;
    private static Properties props = new Properties();
    private static RegistrationService registrationService;
    private static LoginServiceFactory loginServiceFactory;
    private static final String SPID_CODE = "SpidCode";
    private static final String I_M_IDE_DIG_PRESENTATORE = "ISTANZA_METADATO_IDENTIFICATIVO_DIGITALE_PRESENTATORE";
    private static final String ASSERZIONE_SAML_AUTENTICAZIONE = "ASSERZIONE_SAML_AUTENTICAZIONE";
    private static final String CHARS_UTF_8 = "UTF-8";
    private static final String SHA_256 = "SHA-256";
    private static final String I_M_ASSERZIONE_SAML_AUTENTICAZIONE_HASH = "ISTANZA_METADATO_ASSERZIONE_SAML_AUTENTICAZIONE_HASH";

    public LoginServlet() {

	super();
	if (messages == null) {
	    if (log.isDebugEnabled()) {
		log.debug("loading messages.properties");
	    }
	    messages = ResourceBundle.getBundle("messages");
	}
	registrationService = new RegistrationServiceImpl();
	if (log.isDebugEnabled()) {
	    log.debug("loading federa.properties");
	}
	try {
	    InputStream is = this.getClass().getClassLoader().getResourceAsStream("federa.properties");
	    props.load(is);
	    is.close();
	} catch (IOException e) {
	    log.error("Error loading federa.properties: " + e);
	}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	if (log.isDebugEnabled()) {
	    log.debug("doPost...");
	}
	Map<String, String> userAttrs = null;
	String loginClassName = this.getInitParameter("login_class_name");
	loginServiceFactory = LoginServiceFactory.getLoginServiceFactory();
	try {
	    String idcomunealias = request.getParameter(AuthCostants.IDCOMUNE_ALIAS);
	    String contesto = request.getParameter(AuthCostants.CONTESTO);
	    String username = getUsername(request);
	    String clientIp = request.getRemoteAddr();
	    String returnTo = "";
	    LoginService loginService = loginServiceFactory.getLoginService(loginClassName, request);
	    Integer authLevel = this.getAuthLevel(request);
	    User user = new User(idcomunealias, username, null, null, contesto, clientIp, true, authLevel);
	    if (log.isDebugEnabled()) {
		log.debug("Execute login with LoginService for user: " + user.toString());
	    }
	    String[] arrForAssertion = new String[1]; //mi serve per estrarre il valore da dentro il metodo
	    userAttrs = this.getUserAttributes(request, arrForAssertion);
	    // userAttrs = this.getUserAttributes(request, user)
	    if (AuthCostants.CONTESTO_UTE.equals(contesto)) {
		boolean isUserRegistered = registrationService.isUserRegistered(username, idcomunealias, clientIp, AuthCostants.CONTESTO_UTE,
			request);
		log.debug("utente " + username + " registrato " + isUserRegistered);
		if (!isUserRegistered) {
		    if (log.isDebugEnabled()) {
			log.debug("Register user: " + username);
		    }
		    AnagrafeType anagrafeType = populateAnagrafeType(userAttrs, username);
		    BigInteger codiceAnagrafe = registrationService.regUser(anagrafeType, idcomunealias, clientIp);
		    if (log.isDebugEnabled()) {
			log.debug("User registered: " + username + ", codiceAnagrafe: " + codiceAnagrafe);
		    }
		}
	    }
	    Map<String, String> metadatis = null;
	    if (!StringUtils.isBlank(userAttrs.get(SPID_CODE))) {
		log.debug("adding spidcode to map");
		metadatis = new HashMap<String, String>();
		metadatis.put(I_M_IDE_DIG_PRESENTATORE, userAttrs.get(SPID_CODE));
		log.debug("added spidcode to map");
	    }
	    if (!StringUtils.isBlank(arrForAssertion[0])) {
		if (metadatis == null) {
		    log.debug("map is null so creating new map");
		    metadatis = new HashMap<String, String>();
		    log.debug("created new map");
		}
		log.debug("adding saml auth to map");
		metadatis.put(ASSERZIONE_SAML_AUTENTICAZIONE, arrForAssertion[0]);
		log.debug("added saml auth to map");
		log.debug("adding hash saml auth to map");
		MessageDigest md = MessageDigest.getInstance(SHA_256);
		byte[] digest = md.digest(arrForAssertion[0].getBytes(CHARS_UTF_8));
		String hash = Base64.encodeBase64String(digest);
		metadatis.put(I_M_ASSERZIONE_SAML_AUTENTICAZIONE_HASH, hash);
		log.debug("added hash saml auth to map");
	    }
	    if (loginService.doLogin(user, request, metadatis)) {
		if (log.isDebugEnabled()) {
		    log.debug("Login ok for user: " + user.toString());
		}
		if (!(loginService instanceof TokenLoginService)) {
		    TokenLoginService tokenLoginService = new TokenLoginService();
		    tokenLoginService.getToken(user, request);
		}
		returnTo = this.getReturnToUri(request.getParameter(AuthCostants.RETURN_TO), request.getParameter(AuthCostants.CONTESTO),
			user.getToken(), AuthCostants.AUTH_TYPE_LOGIN_SSO, null);
	    } else {
		if ("OPE".equals(contesto)) {
		    log.error(
			    "Utente non trovato nel database. userid: " + username + ", contesto: " + contesto + ", idcomunealias: " + idcomunealias);
		    throw new ServletException("L'utente [" + username +
					       "] non e' stato censito nell'applicativo.<br />Contattare il proprio amministratore dei sistemi informativi con il seguente riferimento: <p>[INTEGRAZIONE-LOGINUMBRIA: Errore nell'autenticazione per l'utente " +
					       username + ", contesto:" + contesto + ", alias=" + idcomunealias +
					       ", <br />Causa: Utente non censito nella base dati di backend]</p>");
		}
		returnTo = this.getReturnToUri(request.getParameter(AuthCostants.RETURN_TO), request.getParameter(AuthCostants.CONTESTO),
			user.getToken(), AuthCostants.AUTH_TYPE_REG_SSO, null);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Go to: " + returnTo);
	    }
	    Map<String, String> reqAttrs = setTokenToUserAttrs(user.getToken(), userAttrs);
	    request.setAttribute(AuthCostants.RETURN_TO, returnTo);
	    request.setAttribute(AuthCostants.RETURNTO_ATTRS, reqAttrs);
	    request.getRequestDispatcher(AuthCostants.RETURNTO_AUTOPOST_PAGE).forward(request, response);
	} catch (Exception e) {
	    log.error("doPost exception: " + e.getMessage());
	    throw new ServletException(e);
	}
    }

    @SuppressWarnings("rawtypes")
    private String getUsername(HttpServletRequest request) {

	HttpSession session = request.getSession();
	String username = "";
	Map serviceContextMap = (Map) session.getAttribute(ICARConstants.SERVICE_CONTEXT_MAP);
	String serviceURLPrefix = (String) session.getAttribute(ICARConstants.SERVICE_URL_PREFIX);
	if (serviceContextMap != null) {
	    AuthenticationSessionBean authBean = (AuthenticationSessionBean) serviceContextMap.get(serviceURLPrefix);
	    username = authBean.getUserID();
	}
	if (log.isDebugEnabled()) {
	    log.debug("getUsername: " + username);
	}
	return username;
    }

    private Integer getAuthLevel(HttpServletRequest request) {

	Map serviceContextMap = (Map) request.getSession().getAttribute(ICARConstants.SERVICE_CONTEXT_MAP);
	String serviceURLPrefix = (String) request.getSession().getAttribute(ICARConstants.SERVICE_URL_PREFIX);
	AuthenticationSessionBean authBean = (AuthenticationSessionBean) serviceContextMap.get(serviceURLPrefix);
	Map userAttrs = authBean.getAttributesMap();
	log.debug("verifico se identificato-->" + userAttrs);
	Set<String> chiavi = userAttrs.keySet();
	List<String> authenticatingAuthority = (List<String>) userAttrs.get("authenticatingAuthority");
	boolean trovatoLoginUmbria = false;
	if (authenticatingAuthority != null) {
	    for (String v : authenticatingAuthority) {
		if (StringUtils.defaultString(v).toLowerCase().indexOf("fedumbria") >= 0) {
		    trovatoLoginUmbria = true;
		}
	    }
	}
	if (!trovatoLoginUmbria) {
	    // comunque in caso di autenticazione tramite IdP SPID l’identificazione è implicita
	    log.debug("NON E' LOGINUMBRIA DOVREBBE ESSER SPID ESCO CON IDENTIFICATO");
	    return IDENTIFICATO;
	}
	if (chiavi != null && !chiavi.isEmpty()) {
	    for (String k : chiavi) {
		List<String> v = (List<String>) userAttrs.get(k);
		if (v != null) {
		    for (String value : v) {
			log.debug("valore -->  " + value + " della chiave " + k);
			if (StringUtils.defaultString(value).equalsIgnoreCase("aa-cittadini-identificati")) {
			    log.debug("trovato valore --> aa-cittadini-identificati dalla chiave " + k);
			    String chiavev2 = "Identificato_" + k;
			    List<String> v2 = (List<String>) userAttrs.get(chiavev2);
			    if (v2 != null) {
				for (String value2 : v2) {
				    log.debug("la ricerca per la chiave " + chiavev2 + " ha tornato il valore " + value2);
				    if (StringUtils.defaultString(value2).equalsIgnoreCase("S")) {
					return IDENTIFICATO;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return NON_IDENTIFICATO;
    }

    private AnagrafeType populateAnagrafeType(Map<String, String> userAttrs, String cf) {

	//
	AnagrafeType anagrafe = new AnagrafeType();
	anagrafe.setCodiceFiscale(cf);
	anagrafe.setNome(userAttrs.get("Nome"));
	anagrafe.setCognome(userAttrs.get("Cognome"));
	String sesso = decodeSessoFromCf(cf);
	AnagrafeTypeSesso s = AnagrafeTypeSesso.M;
	if ("F".equalsIgnoreCase(sesso)) {
	    s = AnagrafeTypeSesso.F;
	}
	anagrafe.setSesso(s);
	return anagrafe;
    }

    /**
     * Controlla se la string passata rappresenta un numero intero (positivo o negativo): nel controllo la stringa viene
     * ripulita degli spazi a destra e sinistra (TRIM)
     * 
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {

	if (StringUtils.isBlank(StringUtils.defaultString(str).trim())) {
	    return false;
	}
	return StringUtils.defaultString(str).trim().matches("^-?(\\d)+$");
    }

    public Map<String, String> getUserAttributes(HttpServletRequest request, String[] arrForAssertion) {

	return getUserAttributesP(request, arrForAssertion);
    }

    private Map<String, String> getUserAttributesP(HttpServletRequest request, String[] arrForAssertion) {

	Map serviceContextMap = (Map) request.getSession().getAttribute(ICARConstants.SERVICE_CONTEXT_MAP);
	String serviceURLPrefix = (String) request.getSession().getAttribute(ICARConstants.SERVICE_URL_PREFIX);
	AuthenticationSessionBean authBean = (AuthenticationSessionBean) serviceContextMap.get(serviceURLPrefix);
	if (arrForAssertion != null && arrForAssertion.length == 1) {
	    arrForAssertion[0] = authBean.getAuthenticationAssertion(); //mi serve come appoggio per estrarre la stringa fuori dal metodo
	}
	Map userAttrs = authBean.getAttributesMap();
	log.debug(userAttrs);
	Map convertedUserAttrs = new HashMap();
	String codiceFiscale = null;
	for (Map.Entry prop : props.entrySet()) {
	    List values = (List) userAttrs.get(prop.getValue());
	    if (values != null && !values.isEmpty()) {
		if (prop.getKey().equals("DataNascita")) {
		    // Per la data di nascita devo fare una conversione
		    // su alcuni providers mi arriva come 1991-10-20 e questo da errore in validazione su Area Riservata
		    //
		    String dataNascita = (String) values.get(0);
		    log.debug("dataNascita=" + dataNascita);
		    if (StringUtils.isNotBlank(dataNascita)) {
			GregorianCalendar dn = getDate(dataNascita, "dd/MM/yyyy");
			if (dn == null) {
			    dn = getDate(dataNascita, "yyyy-MM-dd");
			    if (dn != null) {
				dataNascita = formatDate(dn.getTime(), "dd/MM/yyyy");
			    }
			}
			convertedUserAttrs.put(prop.getKey(), dataNascita);
		    }
		} else if (prop.getKey().equals("CodiceFiscale")) {
		    codiceFiscale = (String) values.get(0);
		    convertedUserAttrs.put(prop.getKey(), codiceFiscale);
		} else {
		    convertedUserAttrs.put(prop.getKey(), values.get(0));
		}
	    }
	    log.debug("USER ATTR: KEY=" + prop.getKey() + ", VALUE=" + convertedUserAttrs.get(prop.getKey()));
	}
	String sesso = (String) convertedUserAttrs.get("Sesso");
	if (StringUtils.isBlank(sesso) && StringUtils.isNotBlank(codiceFiscale)) {
	    sesso = decodeSessoFromCf(codiceFiscale);
	    if (sesso != null) {
		convertedUserAttrs.put("Sesso", sesso);
	    }
	}
	return convertedUserAttrs;
    }

    public static GregorianCalendar getDate(String date, String format) {

	SimpleDateFormat sdf = null;
	GregorianCalendar gd = null;
	try {
	    sdf = new SimpleDateFormat(format);
	    if (StringUtils.isNotBlank(StringUtils.defaultString(date))) {
		gd = new GregorianCalendar();
		Date d = sdf.parse(date);
		gd.setTime(d);
	    }
	} catch (ParseException e) {
	    gd = null;
	}
	return gd;
    }

    public static String formatDate(Date d, String formatDateAndTimePattern) {

	String ld = "";
	if (d != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDateAndTimePattern);
		ld = sdf.format(d);
	    } catch (Exception e) {
		log.error("formatDate " + e.getMessage());
	    }
	}
	return ld;
    }
}
