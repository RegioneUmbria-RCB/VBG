package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.decodifiche.DecodificheService;
import it.gruppoinit.pal.gp.core.features.decodifiche.ListaDecodifiche;
import it.gruppoinit.pal.gp.core.features.rest.BaseRestService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;

@Path("/decodifiche/")
public class DecodificheRestService extends BaseRestService<ListaDecodifiche> {

    private static final Logger log = LoggerFactory.getLogger(DecodificheRestService.class);
    private DecodificheService decodificheService;
    private UserSecurityService userSecurityService;
    private ExternalDBResolver externalDBResolver;

    @Autowired
    public void setDecodificheService(DecodificheService decodificheService) {

	this.decodificheService = decodificheService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    @GET
    @Path("/{alias}/{tabella}")
    @Descriptions({ @Description(value = "Ritorna l'elenco delle decodifiche attive per una certa tabella", target = DocTarget.METHOD) })
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response getDecodificheAttive(@PathParam("alias") String alias, @PathParam("tabella") String tabella) throws Exception {

	log.debug("getDecodificheAttive# tabella={}", tabella);
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	String authToken = getTokenAdmin(alias);
	ORMHelper.setToken(authToken);
	ListaDecodifiche decodifiche = this.decodificheService.findByTabella(tabella);
	return this.rispostaJsonSenzaRoot(decodifiche, Status.OK);
    }

    private String getTokenAdmin(String alias) {

	Properties deployProps = WebConstants.getDeployProperties();
	ExternalDBResolverWS externalDBResolver = new ExternalDBResolverWS();
	externalDBResolver.setConfigurationProperties(deployProps);
	SecurityWSClient client = new SecurityWSClient();
	client.setWsUrl(deployProps.getProperty("ws.token.url"));
	client.setUsername(deployProps.getProperty("ws.token.user"));
	client.setPassword(deployProps.getProperty("ws.token.pwd"));
	client.setTimeout(10000);
	externalDBResolver.setSecurityWSClient(client);
	UserDetails user = userSecurityService.loadAdministratorUser();
	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authToken);
	return externalDBResolver.getToken(alias);
    }

    private void setORMHelper(String idcomunealias, String software) {

	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
    }
}
