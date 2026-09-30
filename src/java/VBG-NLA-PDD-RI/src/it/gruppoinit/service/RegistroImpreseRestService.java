package it.gruppoinit.service;

import it.gruppoinit.pdd.ri.service.RegistroImpreseService;
import it.gruppoinit.pdd.ri.service.impl.VERSIONE_PRATICA_SUAP;
import it.gruppoinit.pdd.utils.TIPO_PRATICA;

import java.math.BigInteger;

import javax.ws.rs.GET;
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

@Path("/registroimprese/")
public class RegistroImpreseRestService {

    private static Logger log = LoggerFactory.getLogger(RegistroImpreseRestService.class);
    private RegistroImpreseService registroImpreseService;

    public void setRegistroImpreseService(RegistroImpreseService registroImpreseService) {

	this.registroImpreseService = registroImpreseService;
    }

    @GET
    @Path("/compilaPraticaSUAPXML/alias/{alias}/codiceIstanza/{codiceIstanza}/effettuaValidazione/{effettuaValidazione}")
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    public Response compilaPraticaSUAPXML(@PathParam("alias") String idComuneAlias, @PathParam("codiceIstanza") String codiceIstanza,
	    @PathParam("effettuaValidazione") String effettuaValidazione, @QueryParam("versione") String versione,
	    @QueryParam("tipo_pratica") String tipoPratica) {

	boolean isValida = true;
	if ("N".equals(effettuaValidazione)) {
	    isValida = false;
	}
	log.debug("PraticaSUAP alias={}, codiceIstanza={} versionePraticaSUAP={}, pratica={}",
		new Object[] { idComuneAlias, codiceIstanza, versione, tipoPratica });
	TIPO_PRATICA pratica = TIPO_PRATICA.REGIONE_TOSCANA; // IL DEFAULT ERA QUESTO
	if (StringUtils.isNotBlank(tipoPratica)) {
	    pratica = TIPO_PRATICA.valueOf(tipoPratica);
	}
	VERSIONE_PRATICA_SUAP versionePraticaSUAP = VERSIONE_PRATICA_SUAP.V_1_0;
	if (StringUtils.isNotBlank(versione)) {
	    versionePraticaSUAP = VERSIONE_PRATICA_SUAP.valueOf(versione);
	}
	String str = "";
	BigInteger cod = new BigInteger(codiceIstanza);
	log.debug("PraticaSUAP alias={}, codiceIstanza={} versionePraticaSUAP={}, pratica={}",
		new Object[] { idComuneAlias, codiceIstanza, versionePraticaSUAP, pratica });
	str = registroImpreseService.compilaPraticaSUAPComeStringa(idComuneAlias, cod, isValida, versionePraticaSUAP, pratica);
	log.debug("PraticaSUAP = {}", str);
	if (StringUtils.contains(str, "[KO]")) {
	    return rispostaWs("<errore>" + str + "</errore>", Status.INTERNAL_SERVER_ERROR);
	}
	return rispostaWs(str, Status.OK);
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
