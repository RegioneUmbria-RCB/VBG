package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.Dyn2MetadatiRestBean;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.Dyn2MetadatiRestBeanList;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.Dyn2MetadatiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/generici/")
public class GenericiRestService extends BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(GenericiRestService.class);
    @Autowired
    Dyn2MetadatiService dyn2MetadatiService;

    @GET
    @Path("/{alias}/datidinamici/metadati/{contesto}")
    @Descriptions({ @Description(value = "torna le configurazioni delle tabelle DYN2_METADAT_CONTESTI", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getDyn2Metadati(@PathParam("alias") String alias, @PathParam("contesto") String dyn2CampoContesto) {

	try {
	    log.debug("Accede al metodo getDyn2Metadati");
	    setORMHelper(alias, WebConstants.SOFTWARE_TT);
	    List<Dyn2MetadatiRestBean> listDyn2MetadatiRestBean = dyn2MetadatiService.findByDyn2MetadatiContesto(dyn2CampoContesto);
	    log.debug("chiamato il servizio dyn2MetadatiService dimensione lista tornato {}", listDyn2MetadatiRestBean.size());
	    String response = Utilities.marshalJsonObject(listDyn2MetadatiRestBean, Dyn2MetadatiRestBeanList.class, false,
		    Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(response, Status.OK);
	} catch (JAXBException e) {
	    log.error("Errore nell marshalling della classe Dyn2MetadatiRestBean {} ", e.getMessage());
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }
}
