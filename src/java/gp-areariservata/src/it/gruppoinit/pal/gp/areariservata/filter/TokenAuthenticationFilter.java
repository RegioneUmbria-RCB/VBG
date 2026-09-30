package it.gruppoinit.pal.gp.areariservata.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.Authentication;
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
import org.springframework.util.StringUtils;

import it.gruppoinit.pal.gp.areariservata.security.LoggedUser;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

/**
 * Filtro per effettuare un'autenticazione silente tramite token.<br />
 * Il codice è stato realizzato tenendo in cosiderazione la classe AnonymousProcessingFilter di spring security. <br/>
 * Il metodo <code>
 * <pre>
 * public int getOrder() {
 * 	return FilterChainOrder.BASIC_PROCESSING_FILTER;
 * }
 * </pre>   
 * </code> definisce l'ordine di esecuzione del filtro ( in questo caso BASIC_PROCESSING_FILTER ) <br />
 * Il filtro deve essere configurato su spring-security.xml come semplice bean con una proprietà custom-filter e va
 * specificato il parametro before<br />
 * per specificare che va eseguito come primo filtro nello stack - in questo caso va eseguito prima di eseguire
 * BASIC_PROCESSING_FILTER <code>
 * <pre>
 * &lt;bean id="tokenAuthenticationFilter" class="it.gruppoinit.pal.gp.backoffice.web.util.TokenAuthenticationFilter"&gt;
 * 	&lt;property name="externalDBResolver" ref="externalDBResolver" /&gt;
 *      &lt;security:custom-filter before="BASIC_PROCESSING_FILTER"/&gt;
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
public class TokenAuthenticationFilter extends SpringSecurityFilter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

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

	return FilterChainOrder.BASIC_PROCESSING_FILTER;
    }

    @Override
    protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

	logger.debug("autenticazione: url={}, qs={}", request.getRequestURI(), request.getQueryString());
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if (auth == null) {
	    logger.debug("autenticazione...");
	    autenticazione(request);
	}
	logger.debug("autenticazione terminata, chiamo il resto dei filtri");
	chain.doFilter(request, response);
    }

    private void autenticazione(HttpServletRequest request) throws ServletException {

	String token = (String) request.getSession().getAttribute(WebConstants.TOKEN);
	try {
	    if (StringUtils.hasText(token)) {
		logger.debug("autenticazione silente con token di sessione: {}", token);
		logger.debug("check token={}", token);
		Properties tokenProps = externalDBResolver.checkToken(token);
		String userid = tokenProps.getProperty(WebConstants.USER_ID);
		if (!StringUtils.hasText(userid)) {
		    logger.error("userid is null for token={}", token);
		    throw new UsernameNotFoundException(messages.getMessage("errors.unauthorized"));
		}
		UsernamePasswordAuthenticationToken auth = null;
		if (tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO).equalsIgnoreCase(WebConstants.TOKEN_INFO_CONTESTO_UTE)
			|| tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO).equalsIgnoreCase("UTEG")) {
		    UserDetails user = userSecurityService.loadUserByUsername(userid);
		    auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		    auth.setDetails(authenticationDetailsSource.buildDetails(request));
		    // insert logged user in session for tomcat manager guessed user field
		    request.getSession().setAttribute("userName", getUsernameDescription(user));
		    logger.info("Accesso utente: userid={}, token={}, idcomunealias={}",
			    new Object[] { userid, token, ORMHelper.getIdcomuneAlias() });
		} else {
		    logger.error("contesto non valido: token={}, contesto={}", token, tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO));
		    throw new UsernameNotFoundException(messages.getMessage("errors.unauthorized"));
		}
		setSessionAttributes(request);
		// update the current context to the target user
		SecurityContextHolder.getContext().setAuthentication(auth);
	    } else {
		logger.debug("token nullo, non eseguo l'autenticazione");
	    }
	} catch (Exception e) {
	    logger.error("errore durante l'autenticazione: token={}, err={}", new Object[] { token, e.getMessage(), e });
	    throw new ServletException("Errore durante l'autenticazione");
	}
    }

    private void setDescrizioneComuneESoftware(HttpServletRequest request) {

	try {
	    ConfigurazioneId idTT = new ConfigurazioneId(ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT);
	    Configurazione confTT = configurazioneService.findById(idTT);
	    if (confTT != null) {
		logger.debug("setComuneESoftware: denominazioneComune={}", confTT.getDenominazione());
		request.getSession().setAttribute("denominazioneComune", confTT.getDenominazione());
	    }
	    ConfigurazioneId idSW = new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware());
	    Configurazione confSW = configurazioneService.findById(idSW);
	    if (confSW != null) {
		logger.debug("setComuneESoftware: denominazioneSportello={}", confSW.getDenominazione());
		request.getSession().setAttribute("denominazioneSportello", confSW.getDenominazione());
	    }
	} catch (Exception e) {
	    logger.error("setDescrizioneComuneESoftware: {}", e.getMessage(), e);
	}
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

    private void setSessionAttributes(HttpServletRequest request) {

	request.getSession().setAttribute("CENTRO_SERVIZI", this.isCentroServizi());
	request.getSession().setAttribute("CENTRO_SERVIZI_URL_BREVI", this.isUrlBrevi());
	setDescrizioneComuneESoftware(request);
    }

    private Boolean isCentroServizi() {

	boolean isCentroServizi = false;
	Verticalizzazioniparametri vertParam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		"CENTRO_SERVIZI");
	if (vertParam != null && vertParam.getValore() != null && vertParam.getValore().equals("1")) {
	    isCentroServizi = true;
	}
	logger.debug("isCentroServizi: {}", isCentroServizi);
	return isCentroServizi;
    }

    private Boolean isUrlBrevi() {

	boolean isUrlBrevi = false;
	Verticalizzazioniparametri vertParam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		"CENTRO_SERVIZI_URL_BREVI");
	if (vertParam != null && vertParam.getValore() != null && vertParam.getValore().equals("1")) {
	    isUrlBrevi = true;
	}
	logger.debug("isUrlBrevi: {}", isUrlBrevi);
	return isUrlBrevi;
    }
}
