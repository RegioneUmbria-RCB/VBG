package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.RipristinaTestoRequest;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.SalvataggioTestoRequest;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayoutTestiDTO;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;

@Controller
@SessionAttributes("layouttesti")
public class LayoutTestiController extends BaseJsonController<Layouttesti> {

    private LayouttestiService layouttestiService;

    @Autowired
    public void setLayouttestiService(LayouttestiService layouttestiService) {

	this.layouttestiService = layouttestiService;
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<LayoutTestiDTO> testi = this.layouttestiService.findTesti();
	ModelMap model = new ModelMap(testi);
	model.addAttribute("testi", testi);
	return model;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Layouttesti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Layouttesti entity) {

    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonSalvaTesto(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    SalvataggioTestoRequest jsonRequest = fromJson(request.getInputStream(), SalvataggioTestoRequest.class);
	    this.layouttestiService.salvaTesto(jsonRequest);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonRipristinaTesto(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    RipristinaTestoRequest jsonRequest = fromJson(request.getInputStream(), RipristinaTestoRequest.class);
	    this.layouttestiService.ripristinaTesto(jsonRequest);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
}
