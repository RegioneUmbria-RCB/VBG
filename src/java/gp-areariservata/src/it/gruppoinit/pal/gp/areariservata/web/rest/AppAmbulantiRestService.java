package it.gruppoinit.pal.gp.areariservata.web.rest;

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
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.IConfigurazioneAppAmbulantiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneAppAmbulanti;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/ambulanti/")
public class AppAmbulantiRestService extends BaseRestService {

    @Autowired
    private IConfigurazioneAppAmbulantiService appAmbulantiService;

    @GET
    @Path("/configurazione/{alias}/{software}")
    @Descriptions({ @Description(value = "Torna la configurazione generale app ambulanti", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response configurazione(@PathParam("alias") String alias, @PathParam("software") String software) throws Exception {

	setORMHelper(alias, software);
	try {
	    ConfigurazioneAppAmbulanti configurazione = appAmbulantiService.getConfigurazione();
	    String str = Utilities.marshalJsonObject(configurazione, configurazione.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (InvalidConfigurationException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }
}
