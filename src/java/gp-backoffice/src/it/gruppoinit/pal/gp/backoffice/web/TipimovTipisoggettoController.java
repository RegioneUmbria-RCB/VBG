package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.gruppoinit.pal.gp.core.domain.TipimovTipiSoggetto;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.soggetti.ITipimovTipiSoggettoService;

@Controller
public class TipimovTipisoggettoController extends BaseJsonController<TipimovTipiSoggetto> {

    @XmlRootElement(name = "aggiungi")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonAggiungiRequest {

	@XmlElement(name = "idTipoMovimento")
	public String idTipoMovimento;
	@XmlElement(name = "idTipoSoggetto")
	public int idTipoSoggetto;
    }

    @XmlRootElement(name = "aggiungi")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonAggiungiResponse {

	@XmlElement(name = "id")
	public int id;

	public JsonAggiungiResponse() {

	}

	public JsonAggiungiResponse(int id) {

	    this.id = id;
	}
    }

    @XmlRootElement(name = "elimina")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonEliminaRequest {

	@XmlElement(name = "id")
	public int id;

	public JsonEliminaRequest() {

	}

	public JsonEliminaRequest(int id) {

	    this.id = id;
	}
    }

    @Autowired
    private ITipimovTipiSoggettoService tipimovTipiSoggettoService;

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonAggiungi(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    JsonAggiungiRequest aggiungiRequest = fromJson(request.getInputStream(), JsonAggiungiRequest.class);
	    int nuovoId = this.tipimovTipiSoggettoService.aggiungi(aggiungiRequest.idTipoMovimento, aggiungiRequest.idTipoSoggetto);
	    JsonAggiungiResponse jsonResponse = new JsonAggiungiResponse(nuovoId);
	    response.setContentType("application/json");
	    response.getOutputStream().write(toJsonBytes(jsonResponse));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonElimina(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    JsonEliminaRequest eliminaRequest = fromJson(request.getInputStream(), JsonEliminaRequest.class);
	    this.tipimovTipiSoggettoService.elimina(eliminaRequest.id);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
}
