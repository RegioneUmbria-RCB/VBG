package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.TestoDaEtichettaRequest;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.TestoDaEtichettaResponse;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;

@Path("/layout/")
public class LayoutRestService {

    @Autowired
    private LayouttestiService layoutTestiService;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private UserSecurityService userSecurityService;

    @POST
    @Path("/testi")
    @Descriptions({ @Description(value = "Recupera il testo da visualizzare in base all'etichetta", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response testoDaEtichetta(String json) throws Exception {

	TestoDaEtichettaRequest request = Utilities.unMarshallJsonString(json, TestoDaEtichettaRequest.class, Utilities.JAXB_ENCODING_UTF_8, true);
	setORMHelper(request.getAlias(), request.getSoftware());
	String authToken = getTokenAdmin(ORMHelper.getIdcomuneAlias());
	ORMHelper.setToken(authToken);
	List<TestoDaEtichettaResponse> response = new ArrayList<TestoDaEtichettaResponse>();
	for (String etichetta : request.getEtichette()) {
	    response.add(new TestoDaEtichettaResponse(etichetta, this.layoutTestiService.testoDaEtichetta(etichetta, request.getSoftware())));
	}
	String output = Utilities.marshalJsonObject(response, TestoDaEtichettaResponse.class, true, Utilities.JAXB_ENCODING_UTF_8);
	return rispostaWs(output, Status.OK);
    }

    private Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    private void setORMHelper(String idcomunealias, String software) {

	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
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
}
