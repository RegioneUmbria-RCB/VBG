package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
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

	logger.debug("doFilterHttp(): entering with uri: {}", request.getRequestURI());
	// silent login
	HttpSession session = request.getSession(false);
	if (session != null) {
	    logger.debug("doFilterHttp(): http session is not null");
	    logger.debug("doFilterHttp(): get authentication from security context...");
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth == null) {
		logger.debug("doFilterHttp(): get authentication...ko, get token from request...");
		String token = request.getParameter(WebConstants.TOKEN);
		logger.debug("doFilterHttp(): request token is '{}'", token);
		if (StringUtils.hasText(token)) {
		    try {
			logger.debug("doFilterHttp(): check token...");
			Properties tokenProps = externalDBResolver.checkToken(token);
			String userid = tokenProps.getProperty(WebConstants.USER_ID);
			if (!StringUtils.hasText(userid)) {
			    logger.error("doFilterHttp(): check token...ok but userid property is null!");
			    throw new UsernameNotFoundException(messages.getMessage("errors.unauthorized", "User " + userid + " not found on server"));
			}
			UsernamePasswordAuthenticationToken authToken = null;
			// se contesto=APP non eseguo l'autenticazione su db 
			// FIXME associo un utente con permessi di amministrazione
			if (tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO).equalsIgnoreCase(WebConstants.TOKEN_INFO_CONTESTO_APP)) {
			    GrantedAuthority[] auths = null;
			    auths = new GrantedAuthorityImpl[1];
			    auths[0] = new GrantedAuthorityImpl("ROLE_USER");
			    UserDetails user = userSecurityService.loadAdministratorUser();
			    logger.warn("L'applicazione [{}] ha avuto accesso come utente amministratore [{}]", userid, user.getUsername());
			    authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
			} else if (tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO).equalsIgnoreCase(WebConstants.TOKEN_INFO_CONTESTO_OPE)) {
			    // se il token è di contesto operatore (Contesto=OPE) allora lo ricerco nel db
			    UserDetails user = userSecurityService.loadUserByUsername(userid);
			    authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
			    authToken.setDetails(authenticationDetailsSource.buildDetails(request));
			    if (user instanceof LoggedUser) {
				String userLogged = ((LoggedUser) user).getResponsabile() + " [" + ((LoggedUser) user).getCodiceResponsabile() + "-"
					+ ORMHelper.getIdcomune() + "], Data Accesso: " + Calendar.getInstance().getTime() + ", Token: " + token;
				logger.warn("Accesso dell'operatore: " + userLogged);
				request.getSession().setAttribute("_UTENTE_LOGGATO_", userLogged);
			    }
			} else {
			    logger.error("doFilterHttp(): contesto non valido: {}", tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO));
			    throw new UsernameNotFoundException(messages.getMessage("errors.unauthorized",
				    "Contesto [" + tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO) + "]"));
			}
			logger.debug("doFilterHttp(): update security context, set authentication");
			// update the current context to the target user
			SecurityContextHolder.getContext().setAuthentication(authToken);
			logger.info("doFilterHttp(): Logged user: {} [{}]", userid, tokenProps.getProperty(WebConstants.TOKEN_INFO_CONTESTO));
		    } catch (AuthenticationException e) {
			logger.error("doFilterHttp(): {}", e.getMessage());
			throw e;
		    }
		}
	    }
	} else {
	    logger.error("doFilterHttp(): http session is null!");
	    throw new RuntimeException("Sessione utente non valida!");
	}
	chain.doFilter(request, response);
    }
}
