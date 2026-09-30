package it.gruppoinit.pal.gp.core.ws;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;

import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.validation.Schema;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/dizionario/")
public class DizionarioRestService extends BaseEnvironment {

    private static final Logger log = LoggerFactory.getLogger(DizionarioRestService.class);
    @Autowired
    private SdeproxyService sdeproxyService;
    @Autowired
    private ExternalDBResolver externalDBResolver;

    @GET
    @Path("download/{idente}/{software}/{codicecomunerfc53}")
    @Produces(MediaType.TEXT_XML + "; charset=UTF-8")
    public Response scaricadizionario(@PathParam("idente") String idente, @PathParam("software") String software,
	    @PathParam("codicecomunerfc53") String codicecomunerfc53) {

	Properties deployProperties = WebConstants.getDeployProperties();
	String aliasDefault = deployProperties.getProperty("servizi.idcomunealias");
	setORMHelper(aliasDefault);
	Sdeproxy sde = sdeproxyService.findById(idente);
	setORMHelperSoftware(sde.getAliasEnte(), software);
	ORMHelper.setIdcomunebase(sde.getIdcomunebase());
	log.info("richiesto dizionario {}-{}-{}", new Object[] { idente, software, codicecomunerfc53 });
	// InvioDizionario dizionarioAggiornato = dizionarioService.getDizionarioAggiornato(ORMHelper.getIdcomunebase(), codicecomunerfc53);
	String content = "";//;Utilities.marshallObject(dizionarioAggiornato);
	log.debug("inizio validazione dell'xml: recupero lo schema");
	Schema schema = getSchemaForMessage();
	log.debug("Lo schema è stato recuperato effettuo la validazione");
	XmlUtils.validaXml(content, schema);
	log.debug("La validazione è stata effettuata con successo");
	return rispostaWs(content, Status.OK);
    }

    private Schema getSchemaForMessage() {

	//V case RFC184_ComunicazionifromBDR_invioDizionario_ERO:
	String xsdName = "_e12.xsd";
	xsdName = "cart/schema/RFC184_ComunicazionifromBDR_invioDizionario_ERO" + xsdName;
	log.debug("schemaLocation: " + xsdName);
	return XmlUtils.getSchemaForMessage(xsdName);
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
}
