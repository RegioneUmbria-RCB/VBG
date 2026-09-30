package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.service.PosteggitipospazioService;
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

//DAELIMINARE @Controller
@SessionAttributes("posteggitipospazio")
public class PosteggitipospazioController extends BaseController<Posteggitipospazio> {

    @Autowired
    private PosteggitipospazioService posteggitipospazioService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Posteggitipospazio> posteggitipospazioList = posteggitipospazioService.findAll(null, null);
	ModelMap model = new ModelMap(posteggitipospazioList);
	boolean export = createJMesaExport(request, response, posteggitipospazioList);
	if (export)
	    return null;
	model.addAttribute("posteggitipospazioList", posteggitipospazioList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("posteggitipospazio") Posteggitipospazio posteggitipospazio, BindingResult result, SessionStatus status) {

	Posteggitipospazio objToDelete = posteggitipospazioService.findById(posteggitipospazio.getId());
	try {
	    posteggitipospazioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(posteggitipospazio);
	    return "posteggitipospazio/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("posteggitipospazio") Posteggitipospazio posteggitipospazio, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(posteggitipospazio);
	posteggitipospazio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    posteggitipospazioService.insert(posteggitipospazio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, posteggitipospazio, e);
	    fixRenderEntityProperty(posteggitipospazio);
	    return "posteggitipospazio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + posteggitipospazio.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("posteggitipospazio") Posteggitipospazio posteggitipospazio, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(posteggitipospazio);
	try {
	    posteggitipospazioService.update(posteggitipospazio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, posteggitipospazio, e);
	    fixRenderEntityProperty(posteggitipospazio);
	    return "posteggitipospazio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + posteggitipospazio.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Posteggitipospazio posteggitipospazio = new Posteggitipospazio();
	posteggitipospazio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(posteggitipospazio);
	model.addAttribute("posteggitipospazio", posteggitipospazio);
	setPageAttributes(model);
	return "posteggitipospazio/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Posteggitipospazio posteggitipospazio = posteggitipospazioService.findById(id);
	fixRenderEntityProperty(posteggitipospazio);
	model.addAttribute("posteggitipospazio", posteggitipospazio);
	setPageAttributes(model);
	return "posteggitipospazio/form";
    }

    @Override
    protected void fixMergeEntityProperty(Posteggitipospazio entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Posteggitipospazio entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
