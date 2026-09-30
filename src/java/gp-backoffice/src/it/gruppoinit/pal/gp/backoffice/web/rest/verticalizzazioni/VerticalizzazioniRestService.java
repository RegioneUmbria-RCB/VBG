package it.gruppoinit.pal.gp.backoffice.web.rest.verticalizzazioni;

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

import it.gruppoinit.pal.gp.core.features.rest.BaseRestService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioniOverrideService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.OverrideDelParametro;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.ParametroConOverride;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioneConOverride;

@Path("/verticalizzazioni/")
public class VerticalizzazioniRestService extends BaseRestService<OverrideDelParametro> {

    private static final Logger log = LoggerFactory.getLogger(VerticalizzazioniRestService.class);
    private IVerticalizzazioniOverrideService verticalizzazioniOverrideService;

    @Autowired
    public void setVerticalizzazioniOverrideService(IVerticalizzazioniOverrideService verticalizzazioniOverrideService) {

	this.verticalizzazioniOverrideService = verticalizzazioniOverrideService;
    }

    // GET
    // verifico se la verticalizzazione può avere degli override
    @GET
    @Path("/{modulo}/overridable")
    @Descriptions({ @Description(value = "Indica se la verticalizzazione può avere degli override configurati", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response isOverridable(@PathParam("modulo") String modulo) throws Exception {

	log.debug("isOverridable# modulo={}", modulo);
	return this.rispostaJsonSenzaRoot(VerticalizzazioneConOverride
		.fromSupportaOverride(this.verticalizzazioniOverrideService.findVerticalizzazioniConOverride().containsKey(modulo)), Status.OK);
    }

    @GET
    @Path("/{modulo}/{parametro}/override/{comune: .*}")
    @Descriptions({
	    @Description(value = "Ritorna, se presente, la lista degli override per la coppia modulo e parametro passati", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response findOverride(@PathParam("modulo") String modulo, @PathParam("parametro") String parametro, @PathParam("comune") String comune)
	    throws JAXBException {

	log.debug("getOverride# modulo={}, comune={}, parametro={}", new Object[] { modulo, comune, parametro });
	List<OverrideDelParametro> override = this.verticalizzazioniOverrideService.findOverride(modulo, comune, parametro);
	return this.listaJsonSenzaRoot(override, OverrideDelParametro.class, Status.OK);
    }

    @GET
    @Path("/{modulo}/{parametro}/overrided/{comune: .*}")
    @Descriptions({ @Description(value = "Ritorna, true se il parametro ha degli override configurati", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response isOverrided(@PathParam("modulo") String modulo, @PathParam("parametro") String parametro, @PathParam("comune") String comune)
	    throws JAXBException {

	log.debug("getOverride# modulo={}, comune={}, parametro={}", new Object[] { modulo, comune, parametro });
	return this.rispostaJsonSenzaRoot(
		ParametroConOverride.fromOverridePresenti(this.verticalizzazioniOverrideService.parametroConOvverride(modulo, comune, parametro)),
		Status.OK);
    }
}
