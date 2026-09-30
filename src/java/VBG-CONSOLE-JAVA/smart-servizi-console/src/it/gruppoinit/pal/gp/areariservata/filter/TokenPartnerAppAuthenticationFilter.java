package it.gruppoinit.pal.gp.areariservata.filter;

import it.gruppoinit.pal.gp.areariservata.security.LoggedUser;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AuthHashUsati;
import it.gruppoinit.pal.gp.core.domain.LogSistema;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AuthHashUsatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.LogSistemaService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.LogoutRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;
import it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.SpringSecurityMessageSource;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * Filtro per effettuare un'autenticazione silente tramite token.<br />
 * Il codice è stato realizzato tenendo in cosiderazione la classe AnonymousProcessingFilter di spring security. <br/>
 * Il metodo <code>
 * <pre>
 * public int getOrder() {
 * 	return FilterChainOrder.CAS_PROCESSING_FILTER;
 * }
 * </pre>   
 * </code> definisce l'ordine di esecuzione del filtro ( in questo caso BASIC_PROCESSING_FILTER ) <br />
 * Il filtro deve essere configurato su spring-security.xml come semplice bean con una proprietà custom-filter e va
 * specificato il parametro before<br />
 * per specificare che va eseguito come primo filtro nello stack - in questo caso va eseguito prima di eseguire
 * BASIC_PROCESSING_FILTER <code>
 * <pre>
 * &lt;bean id="tokenPartnerAppAuthenticationFilter" class="it.gruppoinit.pal.gp.backoffice.web.util.TokenPartnerAppAuthenticationFilter"&gt;
 * 	&lt;property name="externalDBResolver" ref="externalDBResolver" /&gt;
 *      &lt;security:custom-filter before="CAS_PROCESSING_FILTER"/&gt;
 * &lt;/bean&gt;
  </pre>	
 * </code>
 * 
 * <br>
 * L'ordine dei filtri viene valutato in spring security secondo la seguente tabella presente sulla reference di spring
 * security.
 * <p>
 * <b>Table&nbsp;2.1.&nbsp;Standard Filter Aliases and Ordering</b>
 * <table border="1" cellspacing="0" cellpadding="0">
 * <thead>
 * <tr>
 * <th>Alias</th>
 * <th>Filter Class</th>
 * </tr>
 * </thead>
 * <tr>
 * <td>CHANNEL_FILTER</td>
 * <td>ChannelProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>CONCURRENT_SESSION_FILTER</td>
 * <td>ConcurrentSessionFilter</td>
 * </tr>
 * <tr>
 * <td>SESSION_CONTEXT_INTEGRATION_FILTER</td>
 * <td>HttpSessionContextIntegrationFilter</td>
 * </tr>
 * <tr>
 * <td>LOGOUT_FILTER</td>
 * <td>LogoutFilter</td>
 * </tr>
 * <tr>
 * <td>X509_FILTER</td>
 * <td>X509PreAuthenticatedProcessigFilter</td>
 * </tr>
 * <tr>
 * <td>PRE_AUTH_FILTER</td>
 * <td>Subclass of AstractPreAuthenticatedProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>CAS_PROCESSING_FILTER</td>
 * <td>CasProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>AUTHENTICATION_PROCESSING_FILTER</td>
 * <td>AuthenticationProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>BASIC_PROCESSING_FILTER</td>
 * <td>BasicProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>SERVLET_API_SUPPORT_FILTER</td>
 * <td>classname</td>
 * </tr>
 * <tr>
 * <td>REMEMBER_ME_FILTER</td>
 * <td>RememberMeProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>ANONYMOUS_FILTER</td>
 * <td>AnonymousProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>EXCEPTION_TRANSLATION_FILTER</td>
 * <td>ExceptionTranslationFilter</td>
 * </tr>
 * <tr>
 * <td>NTLM_FILTER</td>
 * <td>NtlmProcessingFilter</td>
 * </tr>
 * <tr>
 * <td>FILTER_SECURITY_INTERCEPTOR</td>
 * <td>FilterSecurityInterceptor</td>
 * </tr>
 * <tr>
 * <td>SWITCH_USER_FILTER</td>
 * <td>SwitchUserProcessingFilter</td>
 * </tr>
 * </table>
 * </p>
 * 
 * @author Riccardo Bocci
 * @see org.springframework.security.providers.anonymous.AnonymousProcessingFilter
 */
public class TokenPartnerAppAuthenticationFilter extends SpringSecurityFilter implements InitializingBean {

    private static String TOKEN_PARTNER_APP = "tokenPartnerApp";
    private static String SMARTCARD_REGISTRATION = "AUTH_TYPE";
    private static final Logger logger = LoggerFactory.getLogger(TokenPartnerAppAuthenticationFilter.class);
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private AuthHashUsatiService authHashUsatiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SecurityWSClient securityWSClient;
    private String publicKeyCertFile;
    @Autowired
    private LogSistemaService logSistemaService;

    public void setSecurityWSClient(SecurityWSClient securityWSClient) {

	this.securityWSClient = securityWSClient;
    }

    public void setPublicKeyCertFile(String publicKeyCertFile) {

	this.publicKeyCertFile = publicKeyCertFile;
    }

    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    /**
     * Inietta il resource bundle con la convention over configuration
     * 
     * @param messageSource
     */
    public void setMessageSource(MessageSource messageSource) {

	this.messages = new MessageSourceAccessor(messageSource);
    }

    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

    /**
     * Inietta l' authenticationDetailsSource con la convention over configuration. <br />
     * Serve a popolare i dettagli dell'autenticazione con informazioni aggiuntive derivanti dalla sorgente di
     * autenticazione. <br />
     * In questo caso visto che la sorgente è web saranno WebAuthenticationDetails, quindi <b>Remote address e
     * SessionId</b>
     * 
     * @param authenticationDetailsSource
     * @see org.springframework.security.ui.WebAuthenticationDetails
     * @see org.springframework.security.ui.AuthenticationDetailsSource
     */
    public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) {

	this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

	Assert.notNull(externalDBResolver, "ExternalDbResolver must be set");
	Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
	Assert.notNull(messages, "A message source must be set");
    }

    @Override
    public int getOrder() {

	return FilterChainOrder.CAS_PROCESSING_FILTER;
    }

    @Override
    protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

	logger.trace("autenticazione: url={}, qs={}", request.getRequestURI(), request.getQueryString());
	autenticazione(request);
	logger.trace("autenticazione terminata, chiamo il resto dei filtri");
	chain.doFilter(request, response);
    }

    private void autenticazione(HttpServletRequest request) throws ServletException {

	String token = (String) request.getParameter(TOKEN_PARTNER_APP);
	String smartCardReg = (String) request.getParameter(SMARTCARD_REGISTRATION);
	if (StringUtils.isNotBlank(token)) {
	    tokenPartnerAppAuthentication(request, token);
	} else if (StringUtils.isNotBlank(smartCardReg)) {
	    if (smartCardReg.equals("AUTH_TYPE_REG_SC") || smartCardReg.equals("AUTH_TYPE_REG")) {
		String registrationToken = request.getParameter("RegistrationToken");
		if (StringUtils.isBlank(registrationToken)) {
		    return;
		}
		if (checkregistrationToken(registrationToken)) {
		    if (!checkParametro(request, "username")) {
			smartCardAuthentication(request, smartCardReg);
		    }
		}
	    }
	} else {
	    logger.trace("token nullo, non eseguo l'autenticazione");
	}
    }

    private boolean checkregistrationToken(String registrationToken) throws ServletException {

	SigeproSecurity s;
	try {
	    s = securityWSClient.getWsPort();
	    CheckTokenRequest arg0 = new CheckTokenRequest();
	    arg0.setToken(registrationToken);
	    arg0.setTokenInfo(false);
	    CheckTokenResponse b = s.checkToken(arg0);
	    if (b != null) {
		if (b.isValid()) {
		    LogoutRequest l = new LogoutRequest();
		    l.setToken(registrationToken);
		    s.logout(l);
		    return true;
		}
	    }
	    return false;
	} catch (Exception e) {
	    logger.error("checkregistrationToken(String registrationToken) : {}", registrationToken, e);
	    throw new ServletException("Errore in fase di registrazione: " + e.getMessage(), e);
	}
    }

    private String loginUte(String alias, String username, HttpServletRequest request) {

	logger.debug("stacco il token security per l'utente {}", username);
	String token = externalDBResolver.getUserUTEToken(alias, username, request.getRemoteHost());
	request.getSession().setAttribute(WebConstants.TOKEN, token);
	logger.debug("Token security ottenuto per l'utente {}", username);
	return token;
    }

    private void smartCardAuthentication(HttpServletRequest request, String smartCardReg) throws ServletException {

	verificaParametriObbligatoriSmartCard(request);
	String nome = request.getParameter(NOME);
	String cognome = request.getParameter(COGNOME);
	String cf = request.getParameter(CODICE_FISCALE);
	String sesso = request.getParameter(SESSO);
	if (StringUtils.isBlank(sesso)) {
	    sesso = sessoFromCf(cf);
	}
	sesso = verificaSesso(sesso);
	try {
	    // se non trovato registra nella tabella anagrafe
	    UsernamePasswordAuthenticationToken auth = null;
	    UserDetails user = null;
	    try {
		user = userSecurityService.loadUserByUsername(cf);
	    } catch (UsernameNotFoundException e) {
		Anagrafe entity = new Anagrafe();
		entity.setNome(nome);
		entity.setNominativo(cognome);
		entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		entity.setCodicefiscale(cf);
		entity.setSesso(sesso);
		anagrafeService.insert(entity);
		user = userSecurityService.loadUserByUsername(cf);
	    }
	    auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    auth.setDetails(authenticationDetailsSource.buildDetails(request));
	    // insert logged user in session for tomcat manager guessed user field
	    request.getSession().setAttribute("userName", getUsernameDescription(user));
	    logger.info("Accesso utente: userid={}, idcomunealias={}", new Object[] { cf, ORMHelper.getIdcomuneAlias() });
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    loginUte(ORMHelper.getIdcomuneAlias(), cf, request);
	} catch (Exception e) {
	    logger.error("errore durante l'autenticazione: err={}", new Object[] { e.getMessage(), e });
	    throw new ServletException("Errore durante l'autenticazione");
	}
    }

    private void verificaParametriObbligatoriSmartCard(HttpServletRequest request) {

	boolean ok = false;
	ok = checkParametro(request, CODICE_FISCALE) && checkParametro(request, NOME) && checkParametro(request, COGNOME);
	if (!ok) {
	    logger.error("errore durante l'autenticazione partnerAppToken: qs={}, err=parametri di chiamata non corretti", request.getQueryString());
	    throw new RuntimeException("Errore durante l'autenticazione, parametri di chiamata non corretti");
	}
    }

    private void tokenPartnerAppAuthentication(HttpServletRequest request, String token) throws ServletException {

	//recupera i parametri di chiamata e ne verifica la presenza
	verificaParamertiObbligatoriPartnerApp(request);
	String nome = request.getParameter("nome");
	String cognome = request.getParameter("cognome");
	String cf = request.getParameter("cf");
	String sesso = request.getParameter("sesso");
	String time = request.getParameter("time");
	logger.debug("qs: {}", request.getQueryString());
	// decodifica del parametro check (base64.decode) per recuperare il digest firmato
	// calcola il digest dei parametri ricevuti e previsti concatenandoli come da specifiche tramite l'algoritmo SHA-256
	String qsDaVerificare = "cf=" + cf + "&nome=" + nome + "&cognome=" + cognome + "&sesso=" + sesso + "&time=" + time;
	logger.debug("hash origine: {}", qsDaVerificare);
	if (verifyToken(qsDaVerificare, token)) {
	    logger.debug("autentica l'utente: cf_{}, nome_{}, cognome_{}", new Object[] { cf, nome, cognome });
	    try {
		// se non trovato registra nella tabella anagrafe
		UsernamePasswordAuthenticationToken auth = null;
		UserDetails user = null;
		try {
		    user = userSecurityService.loadUserByUsername(cf);
		} catch (UsernameNotFoundException e) {
		    Anagrafe entity = new Anagrafe();
		    entity.setNome(nome);
		    entity.setNominativo(cognome);
		    entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		    entity.setCodicefiscale(cf);
		    if (StringUtils.isBlank(sesso)) {
			sesso = sessoFromCf(cf);
		    }
		    sesso = verificaSesso(sesso);
		    entity.setSesso(sesso);
		    anagrafeService.insert(entity);
		    user = userSecurityService.loadUserByUsername(cf);
		}
		auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		// insert logged user in session for tomcat manager guessed user field
		request.getSession().setAttribute("userName", getUsernameDescription(user));
		logger.info("Accesso utente: userid={}, token={}, idcomunealias={}", new Object[] { cf, token, ORMHelper.getIdcomuneAlias() });
		// update the current context to the target user
		SecurityContextHolder.getContext().setAuthentication(auth);
		loginUte(ORMHelper.getIdcomuneAlias(), cf, request);
		LogSistema log = creaLog(request, cf);
		logSistemaService.registraLogSistemaInNewTransaction(log);
	    } catch (Exception e) {
		logger.error("errore durante l'autenticazione: token={}, err={}", new Object[] { token, e.getMessage(), e });
		throw new ServletException("Errore durante l'autenticazione");
	    }
	} else {
	    logger.error("Token non verificato:qs={},token={}", qsDaVerificare, token);
	}
    }

    private LogSistema creaLog(HttpServletRequest request, String cf) {

	LogSistema log = new LogSistema();
	log.setUtenteConnesso(cf);
	log.setCodiceEvento(LogSistemaService.CODICI_EVENTO.ACCESSO.name());
	String descrizioneEvento = "L'utente identificato con " + cf + " ha avuto accesso dall'indirizzo " + request.getRemoteAddr()
		+ " alla seguente url: " + request.getServletPath() + "?" + request.getQueryString();
	log.setDescrizioneEvento(descrizioneEvento);
	log.setEvento(descrizioneEvento);
	log.setAttore(AttoreReteSuap.FACCT.name());
	return log;
    }

    private String verificaSesso(String sesso) {

	if (StringUtils.isBlank(sesso)) {
	    return "M";
	}
	if (sesso.equalsIgnoreCase("M") || sesso.equalsIgnoreCase("F")) {
	    return sesso.toUpperCase();
	}
	return "M";
    }

    private boolean verifyToken(String qsDaVerificare, String token) {

	byte[] tokenDecoded = Base64.decodeBase64(token);
	logger.debug("tokenDecoded: {}", tokenDecoded);
	String hash = Utilities.getHashText(qsDaVerificare, Utilities.ALGORITHM_SHA256, false);
	logger.debug("hash SHA-256: " + hash);
	boolean ok = verifiyCert(tokenDecoded, hash.getBytes());
	if (ok) {
	    AuthHashUsati s = authHashUsatiService.findById(hash);
	    if (s == null) {
		AuthHashUsati entity = new AuthHashUsati();
		entity.setHashCode(hash);
		entity.setDati(getDati(qsDaVerificare));
		authHashUsatiService.insert(entity);
	    } else {
		throw new SecurityException("Autenticazione non valida! Il token è stato già usato.");
	    }
	}
	return ok;
    }

    private String getDati(String qsDaVerificare) {

	return StringUtils.left(StringUtils.defaultString(qsDaVerificare), 200);
    }

    private boolean verifiyCert(byte[] signatureData, byte[] dataFile) {

	boolean ok = false;
	/* Verify a DSA signature */
	try {
	    /* import encoded public key */
	    InputStream inStream = null;
	    X509Certificate cert = null;
	    try {
		// inStream = new FileInputStream(publicKeyCertFile);
		inStream = this.getClass().getClassLoader().getResourceAsStream(WebConstants.CONFIG_FILES_FOLDER + publicKeyCertFile);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		cert = (X509Certificate) cf.generateCertificate(inStream);
	    } finally {
		if (inStream != null) {
		    inStream.close();
		}
	    }
	    /* input the signature bytes */
	    ByteArrayInputStream sigfis = new ByteArrayInputStream(signatureData);
	    byte[] sigToVerify = new byte[sigfis.available()];
	    sigfis.read(sigToVerify);
	    sigfis.close();
	    /* create a Signature object and initialize it with the public key */
	    Signature sig = Signature.getInstance("SHA1withRSA");
	    sig.initVerify(cert);
	    /* Update and verify the data */
	    sig.update(dataFile);
	    ok = sig.verify(sigToVerify);
	    logger.debug("signature verifies: " + ok);
	} catch (Exception e) {
	    logger.error("Caught exception " + e.toString());
	}
	return ok;
    }

    /**
     * Obbligatori parametri:
     * <ul>
     * <li>cf</li>
     * <li>nome</li>
     * <li>cognome</li>
     * <li>sesso</li>
     * <li>time</li>
     * </ul>
     * 
     * @param request
     */
    private void verificaParamertiObbligatoriPartnerApp(HttpServletRequest request) {

	boolean ok = false;
	ok = checkParametro(request, "cf") && checkParametro(request, "nome") && checkParametro(request, "cognome")
		&& checkParametro(request, "time");
	if (!ok) {
	    logger.error("errore durante l'autenticazione partnerAppToken: qs={}, err=parametri di chiamata non corretti", request.getQueryString());
	    throw new RuntimeException("Errore durante l'autenticazione, parametri di chiamata non corretti");
	}
    }

    private boolean checkParametro(HttpServletRequest request, String parametro) {

	String p = StringUtils.defaultString(request.getParameter(parametro));
	return StringUtils.isNotBlank(p);
    }

    private String getUsernameDescription(UserDetails user) {

	LoggedUser _user = (LoggedUser) user;
	StringBuffer descrizione = new StringBuffer();
	descrizione.append(_user.getUsername());
	descrizione.append(" - ");
	descrizione.append(_user.getAnagrafe());
	descrizione.append(" - ");
	descrizione.append(_user.getCf());
	return descrizione.toString();
    }

    public final static String NOME = "Nome";
    public final static String COGNOME = "Cognome";
    public final static String CODICE_FISCALE = "CodiceFiscale";
    public final static String SESSO = "Sesso";
    public final static String DATA_NASCITA = "DataNascita";
    public final static String INDIRIZZO_RESIDENZA = "IndirizzoResidenza";
    public final static String COMUNE_RESIDENZA = "ComuneResidenza";
    public final static String COMUNE_NASCITA = "ComuneNascita";
    public final static String COMUNE_RESIDENZA_DESC = "ComuneResidenzaDesc";
    public final static String COMUNE_NASCITA_DESC = "ComuneNascitaDesc";
    public final static String COMUNE_EMITTENTE = "ComuneEmittente";
    public final static String DATA_EMISSIONE = "DataEmissione";
    public final static String DATA_SCADENZA = "DataScadenza";
    public final static String STATURA = "Statura";
    public final static String CODICE_CITTADINANZA = "CodiceCittadinanza";
    public final static String STATO_ESTERO_NASCITA = "StatoEsteroNascita";
    public final static String ESTREMI_ATTO_NASCITA = "EstremiAttoNascita";
    public final static String VALIDITA_ESPATRIO = "ValiditaEspatrio";
    public final static String EMAIL = "Email";

    private String sessoFromCf(String codiceFiscale) {

	if (StringUtils.isNotBlank(codiceFiscale)) {
	    if (codiceFiscale.length() == 16) {
		String giornonascita = codiceFiscale.substring(9, 11);
		if (Utilities.isInteger(giornonascita)) {
		    Integer gg = Integer.valueOf(giornonascita);
		    if (gg.intValue() > 31) {
			return "F";
		    }
		}
	    }
	}
	return "M";
    }
}
