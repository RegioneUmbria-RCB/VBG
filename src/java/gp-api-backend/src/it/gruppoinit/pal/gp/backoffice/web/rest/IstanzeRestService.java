package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.DocumentiIstanzaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import net.sf.sojo.interchange.Serializer;

@Path("/pratiche/")
public class IstanzeRestService extends BaseRestService {

    private static final String NOME_CID_DOCUMENTO_BLOB = "documento.blob";
    private static final String NOME_CID_PARAMETRI_JSON = "parametri.json";
    private static final Logger log = LoggerFactory.getLogger(IstanzeRestService.class);
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;

    @POST
    @Path("/documenti/{codice-istanza}")
    @Descriptions({ @Description(value = "", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes("multipart/mixed")
    public Response aggiungiDocumento(MultipartBody body, @PathParam("codice-istanza") Integer codiceIstanza) throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    throw new SecurityException("Pratica non trovata");
	}
	// RECUPERARE IL SOFTWARE DELLA PRATICA SETTARE ORMHELPER DEL SOFTWARE DELLA PRATICA
	ORMHelper.setSoftware(istanza.getSoftware().getCodice());
	// VERIFICARE I PERMESSI
	TipoAccessoEnum checkAccessoIstanza = istanzeService.checkAccessoIstanza(istanza,
		(Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
	if (!TipoAccessoEnum.CONSENTITO.equals(checkAccessoIstanza)) {
	    throw new SecurityException("Non si dispone delle autorizzazioni per accedere ai dati della pratica");
	}
	Serializer srlzr = getSerializer();
	InputStream json = body.getAttachmentObject(NOME_CID_PARAMETRI_JSON, InputStream.class);
	StringWriter writer = new StringWriter();
	try {
	    IOUtils.copy(json, writer, "UTF-8");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	String jsonFile = writer.toString();
	log.debug("aggiungiDocumento: {} ==>  {}", NOME_CID_PARAMETRI_JSON, jsonFile);
	DocumentiIstanzaRestHelper helper = (DocumentiIstanzaRestHelper) srlzr.deserialize(jsonFile, DocumentiIstanzaRestHelper.class);
	InputStream documento = body.getAttachmentObject(NOME_CID_DOCUMENTO_BLOB, InputStream.class);
	// INSERIRE NELLA TABELLA DOCUMENTIISTANZA CON NUOVO METODO CHE ACCETTA UN INPUT STREAM
	Integer codiceOggetto = documentiistanzaService.insertDocumentoDaHelper(codiceIstanza, helper, documento);
	// RITORNARE IL CODICEOGGETTO 
	String output = "{codiceoggetto: " + codiceOggetto + "}";
	log.debug("aggiungiDocumento: result {}", output);
	return rispostaWs(output, Status.OK);
    }
}
