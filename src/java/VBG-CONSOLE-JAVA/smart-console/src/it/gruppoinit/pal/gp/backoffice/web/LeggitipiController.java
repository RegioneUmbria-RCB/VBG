/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LeggitipiService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("leggitipi")
public class LeggitipiController extends BaseController<Leggitipi> {

    @Autowired
    private LeggitipiService leggitipiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Leggitipi> leggitipiList = leggitipiService.findAll(null, null);
	ModelMap model = new ModelMap(leggitipiList);
	boolean export = createJMesaExport(request, response, leggitipiList);
	if (export)
	    return null;
	model.addAttribute("leggitipiList", leggitipiList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("leggitipi") Leggitipi leggitipi, BindingResult result, SessionStatus status) {

	Leggitipi objToDelete = leggitipiService.findById(leggitipi.getId());
	try {
	    leggitipiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(leggitipi);
	    return "leggitipi/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("leggitipi") Leggitipi leggitipi, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(leggitipi);
	try {
	    leggitipiService.insert(leggitipi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, leggitipi, e);
	    fixRenderEntityProperty(leggitipi);
	    return "leggitipi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + leggitipi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("leggitipi") Leggitipi leggitipi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(leggitipi);
	try {
	    leggitipiService.update(leggitipi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, leggitipi, e);
	    fixRenderEntityProperty(leggitipi);
	    return "leggitipi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + leggitipi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Leggitipi leggitipi = new Leggitipi();
	fixRenderEntityProperty(leggitipi);
	model.addAttribute("leggitipi", leggitipi);
	setPageAttributes(model);
	return "leggitipi/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Leggitipi leggitipi = leggitipiService.findById(id);
	fixRenderEntityProperty(leggitipi);
	model.addAttribute("leggitipi", leggitipi);
	setPageAttributes(model);
	return "leggitipi/form";
    }

    @Override
    protected void fixMergeEntityProperty(Leggitipi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Leggitipi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
