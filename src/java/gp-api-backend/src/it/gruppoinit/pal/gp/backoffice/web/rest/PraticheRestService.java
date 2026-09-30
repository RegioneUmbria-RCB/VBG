package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.ApiProducerService;
import it.gruppoinit.pal.gp.core.service.exception.PraticheRestException;
import it.gruppoinit.pal.gp.core.service.helper.DownloadPraticaZipHelper;
import it.gruppoinit.pal.gp.core.service.helper.PraticaRestBean;
import it.gruppoinit.pal.gp.core.service.helper.PraticheRestBeanResult;
import it.gruppoinit.pal.gp.core.service.helper.ProblemResult;
import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonParserException;
import net.sf.sojo.interchange.json.JsonSerializer;

@Path("/pratiche/")
public class PraticheRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(PraticheRestService.class);
    @Autowired
    private ApiProducerService apiProducerService;
    private Serializer serializer;

    @POST
    @Path("/crea-pratica-da-zip/{numero_protocollo}/{data_protocollo}")
    @Descriptions({
	    @Description(value = "Inoltra una pratica inbustata in uno zip file. " +
				 "I parametri numero_protocollo e data_protocollo sono obbligatori e si riferiscono ai dati di protocollazione della pratica nell'ente." +
				 "Il formato della data ammesso è yyyy-MM-dd", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes("multipart/mixed")
    public Response creaPraticaDaZip(MultipartBody body, @PathParam("numero_protocollo") String numero_protocollo,
	    @PathParam("data_protocollo") String data_protocollo) {

	GregorianCalendar dataP = Utilities.getDate(data_protocollo, "yyyy-MM-dd");
	if (dataP == null) {
	    throw new RuntimeException("Parametro data non corretto");
	}
	log.debug("crea-pratica-da-zip: accedo il metodo");
	Serializer serializer = getSerializer();
	InputStream fileZip = body.getAttachmentObject(WebConstants.API_SERVICE_ALLEGATI_ZIP_FILE, InputStream.class);
	RiferimentiPraticaSTCRestBean result = apiProducerService.creaPraticaDaZip(fileZip, numero_protocollo, dataP.getTime());
	String output = (String) serializer.serialize(result);
	log.debug("crea-pratica-da-zip: result {}", result);
	return rispostaWs(output, Status.OK);
    }

    @GET
    @Path("/utente/{codiceFiscaleUtente}")
    @Descriptions({ @Description(value = "Ottiene la lista delle pratiche associate ad uno specifico utente", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getPraticheUtente(@PathParam("codiceFiscaleUtente") String codiceFiscale, @QueryParam("limit") Integer limit,
	    @QueryParam("offset") Integer offset, @QueryParam("software") String software) throws Exception {

	log.debug("getPraticheUtente: ");
	setLocalORMHelper();
	Status retVal = Status.OK;
	Serializer serializer = getSerializer();
	ProblemResult result = new ProblemResult();
	try {
	    PraticheRestBeanResult praticheResult = apiProducerService.findPraticheByCF(codiceFiscale, software, offset, limit);
	    log.debug("getPraticheUtente: Lista Pratiche utente {} ", praticheResult.getResults());
	    String output = (String) serializer.serialize(praticheResult);
	    return rispostaWs(output, retVal);
	} catch (PraticheRestException ex) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result = ex.getProblem();
	} catch (JsonParserException ex) {
	    retVal = Status.INTERNAL_SERVER_ERROR;
	    result.setDetail(ex.getMessage());
	    result.setTitle(ex.getClass().toString());
	    result.setStatus(retVal.getStatusCode());
	}
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, retVal);
    }

    private void setLocalORMHelper() {

	Properties deployProperties = WebConstants.getDeployProperties();
	String idcomuneAlias = deployProperties.getProperty("default.idcomunealias");
	setORMHelper(idcomuneAlias, WebConstants.SOFTWARE_TT);
    }

    protected Serializer getSerializer() {

	this.serializer = new JsonSerializer();
	serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

	    @Override
	    public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		if (arg0 == new PraticaRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PraticaRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new ProblemResult().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ProblemResult.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new PraticheRestBeanResult().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PraticheRestBeanResult.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		return null;
	    }
	});
	return this.serializer;
    }

    @GET
    @Path("/scarica-zip-pratica/{alias}/{uuid}")
    @Descriptions({ @Description(value = "scarica i documenti di una pratica in uno zip file", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response downloadPraticaDaZip(@PathParam("alias") String alias, @PathParam("uuid") String uuid) {

	log.debug("crea-pratica-da-zip: accedo il metodo");
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	boolean includiDocumentiDeiMovimenti = false;
	List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	String messaggioErrore = "";
	try {
	    DownloadPraticaZipHelper hlp = apiProducerService.scaricaZipPratica(uuid, includiDocumentiDeiMovimenti);
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Disposition");
	    cdb.setDescrizione("attachment; filename=\"" + hlp.getNomeFile() + "\"");
	    headerAggiuntivi.add(cdb);
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Type");
	    cdb.setDescrizione("application/zip");
	    headerAggiuntivi.add(cdb);
	    return rispostaWs(hlp.getContenuto(), Status.OK, headerAggiuntivi);
	} catch (Exception e) {
	    messaggioErrore = e.getMessage();
	}
	CodiceDescrizioneBean b = new CodiceDescrizioneBean();
	b.setCodice("500");
	b.setDescrizione(messaggioErrore);
	String output = (String) getSerializer().serialize(b);
	return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
    }

    @GET
    @Path("/scarica-zip-pratica-base-64/{alias}/{uuid}")
    @Descriptions({ @Description(value = "scarica i documenti di una pratica in uno zip file", target = DocTarget.METHOD) })
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response downloadPraticaDaZipInBase64(@PathParam("alias") String alias, @PathParam("uuid") String uuid) {

	log.debug("crea-pratica-da-zip-base64: accedo il metodo");
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	boolean includiDocumentiDeiMovimenti = false;
	List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	String messaggioErrore = "";
	try {
	    DownloadPraticaZipHelper hlp = apiProducerService.scaricaZipPratica(uuid, includiDocumentiDeiMovimenti);
	    String praticaEncB64 = Base64.encodeBase64String(IOUtils.toByteArray(hlp.getContenuto()));
	    headerAggiuntivi.add(new CodiceDescrizioneBean("Content-Type", "text/plain"));
	    return rispostaWs(praticaEncB64, Status.OK, headerAggiuntivi);
	} catch (Exception e) {
	    messaggioErrore = e.getMessage();
	}
	CodiceDescrizioneBean b = new CodiceDescrizioneBean();
	b.setCodice("500");
	b.setDescrizione(messaggioErrore);
	String output = (String) getSerializer().serialize(b);
	return rispostaWs(output, Status.INTERNAL_SERVER_ERROR);
    }

    @GET
    @Path("/status")
    @Descriptions({ @Description(value = "Se il servizio è raggiungibile", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response status() throws Exception {

	String result = "{";
	result += "  \"detail\": \"Service is up\",";
	result += "  \"status\": 200,";
	result += "  \"title\": \"Servizio pratiche\"";
	result += "}";
	return rispostaWs(result, Status.OK);
    }
}
