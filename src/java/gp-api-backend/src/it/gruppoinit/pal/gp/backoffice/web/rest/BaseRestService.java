package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.List;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.userdetails.UserDetails;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.LoginSSORequest;
import it.gruppoinit.sigeprosecurity.schema.LoginSSOResponse;
import it.gruppoinit.sigeprosecurity.schema.LogoutRequest;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

public class BaseRestService {

    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    private Serializer serializer;
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
    @Autowired
    private UserSecurityService userSecurityService;
    @Context
    private HttpServletRequest servletRequest;

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus, List<CodiceDescrizioneBean> headerAggiuntivi) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (headerAggiuntivi != null) {
	    for (CodiceDescrizioneBean cdb : headerAggiuntivi) {
		builder.header(cdb.getCodice(), cdb.getDescrizione());
	    }
	}
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Response rispostaWsWithProblemResult(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (entity != null) {
	    builder.entity(entity);
	}
	if (returnStatus == Status.OK) {
	    // builder.sta
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Response rispostaWsFile(Object entity, Status returnStatus, String nomeFile) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Response rispostaWsFile(Object entity, Status returnStatus, String nomeFile, List<CodiceDescrizioneBean> headerAggiuntivi) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	if (headerAggiuntivi != null) {
	    for (CodiceDescrizioneBean cdb : headerAggiuntivi) {
		builder.header(cdb.getCodice(), cdb.getDescrizione());
	    }
	}
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected CodiceDescrizioneBean newNVBean(String descrizione, String codice) {

	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setDescrizione(descrizione);
	result.setCodice(codice);
	return result;
    }

    protected void setORMHelper(String idcomunealias, String software) {

	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	UserDetails user = userSecurityService.loadAdministratorUser();
	UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	// update the current context to the target user
	SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    protected void setORMHelperFromCodiceServizio(String codiceServizio) {

	String[] aliasSoftware = codiceServizio.split("-");
	String idcomunealias = aliasSoftware[0];
	String software = aliasSoftware[1];
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
    }

    protected CheckTokenResponse getTokenInfo(String token) throws Exception {

	CheckTokenRequest req = new CheckTokenRequest();
	req.setToken(token);
	req.setTokenInfo(true);
	CheckTokenResponse ti = securityWSClient.getWsPort().checkToken(req);
	return ti;
    }

    protected String refreshToken(String token) throws Exception {

	LoginSSORequest lsso = new LoginSSORequest();
	CheckTokenResponse ti = getTokenInfo(token);
	lsso.setAlias(ti.getTokenInfo().getAlias());
	lsso.setContesto(ti.getTokenInfo().getContesto());
	lsso.setUsername(ti.getTokenInfo().getUserid());
	lsso.setIpAddress(ti.getTokenInfo().getClientIp());
	LoginSSOResponse loginSSO = securityWSClient.getWsPort().loginSSO(lsso);
	LogoutRequest lo = new LogoutRequest();
	lo.setToken(token);
	securityWSClient.getWsPort().logout(lo);
	return loginSSO.getToken();
    }

    protected void checkrequest(Serializer serializer, CheckTokenResponse ti) throws Exception {

	if (!ti.isValid() || ti.getTokenInfo() == null) {
	    throw new RuntimeException("Richiesta non valida");
	}
	if (!(ti.getTokenInfo().getContesto().equals(ContestoType.UTE) || ti.getTokenInfo().getContesto().equals(ContestoType.UTEG))) {
	    throw new RuntimeException("Richiesta non valida");
	}
	String cf = ti.getTokenInfo().getUserid();
	if (StringUtils.isBlank(cf)) {
	    throw new RuntimeException("Richiesta non valida");
	}
    }

    protected Serializer getSerializer() {

	if (true /*this.serializer == null*/) {
	    this.serializer = new JsonSerializer();
	    serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

		@Override
		public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		    ClassPropertyFilter cpf = new ClassPropertyFilter(arg0.getClass());
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
	    });
	}
	return this.serializer;
    }

    private void setORMHelperTest(HttpServletRequest request) {

	Properties connProps = externalDBResolver.getConnectionProperties("L219");
	ORMHelper.setIdcomune("L219");
	ORMHelper.setIdcomuneAlias("L219");
	ORMHelper.setSoftware("CO");
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	UsernamePasswordAuthenticationToken authToken = null;
	UserDetails user = userSecurityService.loadUserByUsername("admin");
	authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	authToken.setDetails(authenticationDetailsSource.buildDetails(request));
	SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    protected void setORMHelperFromtToken(String token) {

	Properties props = externalDBResolver.checkToken(token);
	Properties dbProps = externalDBResolver.getConnectionProperties(props.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setIdcomune(props.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(props.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(dbProps));
	ORMHelper.setToken(dbProps.getProperty(WebConstants.TOKEN));
	UserDetails user = userSecurityService.loadUserByUsername(props.getProperty(WebConstants.USER_ID));
	UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	// update the current context to the target user
	SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    protected void setOrmHelperFromToken(String token, String software) throws SecurityException {

	if (token == null) {
	    throw new SecurityException("Errore nell'autenticazione");
	}
	try {
	    CheckTokenResponse r = getTokenInfo(token);
	    boolean autenticato = (r != null && r.getTokenInfo() != null && r.isValid());
	    if (autenticato) {
		Properties connProps = externalDBResolver.getConnectionProperties(r.getTokenInfo().getAlias());
		ORMHelper.setToken(token);
		ORMHelper.setIdcomune(r.getTokenInfo().getIdcomune());
		ORMHelper.setIdcomuneAlias(r.getTokenInfo().getAlias());
		ORMHelper.setSoftware(software);
		ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
		UsernamePasswordAuthenticationToken authToken = null;
		UserDetails user = null;
		if (r.getTokenInfo().getContesto().equals(ContestoType.APP)) {
		    user = userSecurityService.loadAdministratorUser();
		} else {
		    user = userSecurityService.loadUserByUsername(r.getTokenInfo().getUserid());
		}
		authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	    } else {
		throw new SecurityException("Errore nell'autenticazione");
	    }
	} catch (Exception e) {
	    throw new SecurityException("Errore nell'autenticazione");
	}
    }
}
