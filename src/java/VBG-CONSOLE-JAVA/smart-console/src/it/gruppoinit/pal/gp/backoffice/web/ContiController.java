/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.ContiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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
//DAELIMINARE @Controller
@SessionAttributes("conti")
public class ContiController extends BaseController<Conti> {

    @Autowired
    private ContiService contiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Conti> contiList = contiService.findAll(null, null);
	ModelMap model = new ModelMap(contiList);
	boolean export = createJMesaExport(request, response, contiList);
	if (export)
	    return null;
	model.addAttribute("contiList", contiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("conti") Conti conti, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Conti objToDelete = contiService.findById(conti.getId());
	try {
	    contiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(conti);
	    return "conti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("conti") Conti conti, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(conti);
	try {
	    contiService.insert(conti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, conti, e);
	    fixRenderEntityProperty(conti);
	    return "conti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + conti.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("conti") Conti conti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(conti);
	try {
	    contiService.update(conti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, conti, e);
	    fixRenderEntityProperty(conti);
	    return "conti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + conti.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Conti conti = new Conti();
	// conti.setIva(WebConstants.CONST_IVA);
	conti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(conti);
	model.addAttribute("conti", conti);
	setPageAttributes(model);
	return "conti/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Conti conti = contiService.findById(id);
	fixRenderEntityProperty(conti);
	model.addAttribute("conti", conti);
	setPageAttributes(model);
	return "conti/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Conti entity) {

	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& entity.getAmministrazioni().getId().getCodice() == null) {
	    entity.setAmministrazioni(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Conti entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
