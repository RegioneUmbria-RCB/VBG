package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
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

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.BollettazioneLettereService;

@Path("/bollettazione/")
public class BollettazioneRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(BollettazioneRestService.class);
    @Autowired
    private BollettazioneLettereService bollettazioneLettereService;

    @GET
    @Path("/lettera-accompagnamento/{cf_ente_creditore}/{id_posizione_debitoria}")
    @Descriptions({
	    @Description(value = "Produce, a partire dall'identificativo dell posizione debitoria del sistema di pagamenti la lettera di accompagnamento, se configurata per la tipologia di bollettazione", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response letteraAccompagnamento(@PathParam("cf_ente_creditore") String cf_ente_creditore,
	    @PathParam("id_posizione_debitoria") Integer rifIdPosizioneDebitoria) {

	log.debug("lettera-accompagnamento: accedo il metodo");
	InputStream lettera = bollettazioneLettereService.generaLetteraAccompagnamento(cf_ente_creditore, rifIdPosizioneDebitoria, true);
	List<CodiceDescrizioneBean> headerAggiuntivi = new ArrayList<CodiceDescrizioneBean>();
	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	cdb.setCodice("Content-Disposition");
	cdb.setDescrizione("attachment; filename=\"lettera-accompagnamento.pdf\"");
	headerAggiuntivi.add(cdb);
	cdb = new CodiceDescrizioneBean();
	cdb.setCodice("Content-Type");
	cdb.setDescrizione("application/pdf");
	headerAggiuntivi.add(cdb);
	return rispostaWs(lettera, Status.OK, headerAggiuntivi);
    }
}
