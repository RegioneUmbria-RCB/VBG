package it.gruppoinit.service;

import it.gruppoinit.service.helper.StatusHelper;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/configurazionioffline/")
public class ConfigurazioniOFFLineRestService {

    private static final Logger activityLog = LoggerFactory.getLogger("registrazione_attivita");
    @Autowired
    private SigeproSecurityWSClient sigeproSecurityWSClient;
    @Autowired
    private DeployProperties deployProperties;

    @GET
    @Path("token/{token}/{aliasOrigine}/{softwareOrigine}/{aliasDestinazione}/{softwareDestinazione}/copia/{idoperazione}/escludiDisabilitati/{escludiDisabilitati}")
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    public Response copia(@PathParam("token") String token, @PathParam("aliasOrigine") String aliasOrigine,
	    @PathParam("softwareOrigine") String softwareOrigine, @PathParam("aliasDestinazione") String aliasDestinazione,
	    @PathParam("softwareDestinazione") String softwareDestinazione, @PathParam("idoperazione") String idoperazione,
	    @PathParam("escludiDisabilitati") Boolean escludiDisabilitati, @QueryParam("scCodice") String scCodice) throws Exception {

	String esito = verificaToken(token, aliasDestinazione);
	if (StringUtils.isNotBlank(esito)) {
	    return rispostaWs(esito, Status.UNAUTHORIZED);
	}
	BackendOFFLineService bs = new BackendOFFLineService(aliasOrigine, aliasDestinazione, softwareOrigine, softwareDestinazione,
		sigeproSecurityWSClient, idoperazione, escludiDisabilitati, scCodice, deployProperties.getConsoleOffLineWsUrl());
	bs.copiaVoceAlbero();
	String str = "<status>operazione terminata</status>";
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("token/{token}/verifica/{idoperazione}")
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response verificastatus(@PathParam("token") String token, @PathParam("idoperazione") String idoperazione) throws Exception {

	String attivita = StatusHelper.attivita(idoperazione);
	String str = attivita;
	return rispostaWs(str, Status.OK);
    }

    @GET
    @Path("token/{token}/{aliasOrigine}/amministrazioni")
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response amministrazioni(@PathParam("token") String token, @PathParam("aliasOrigine") String aliasOrigine,
	    @QueryParam("scCodice") String scCodice, @QueryParam("softwareOrigine") String softwareOrigine) throws Exception {

	if (StringUtils.isBlank(softwareOrigine)) {
	    softwareOrigine = null;
	}
	ConsoleRestClientService rs = new ConsoleRestClientService(aliasOrigine, softwareOrigine, String.valueOf(System.currentTimeMillis()), token,
		deployProperties.getConsoleOffLineWsUrl());
	String result = rs.getAmministrazioni(scCodice);
	return rispostaWs(result, Status.OK);
    }

    private String verificaToken(String token, String aliasDestinazione) {

	TokenInfoType tokenInfo = sigeproSecurityWSClient.getTokenInfo(token);
	if (!tokenInfo.getAlias().equalsIgnoreCase(aliasDestinazione)) {
	    return "Operazione non consentita";
	}
	if (!tokenInfo.getContesto().equals(ContestoType.OPE)) {
	    return "Operazione non consentita";
	}
	return null;
    }

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
}
