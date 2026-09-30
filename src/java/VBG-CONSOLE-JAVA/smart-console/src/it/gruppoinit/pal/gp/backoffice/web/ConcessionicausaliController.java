package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
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
 * 
 * @author Riccardo Bocci
 */
@Controller
@SessionAttributes("concessionicausali")
public class ConcessionicausaliController extends BaseController<Concessionicausali> {

    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Concessionicausali> concessionicausaliList = concessionicausaliService.findAll(null, null);
	ModelMap model = new ModelMap(concessionicausaliList);
	boolean export = createJMesaExport(request, response, concessionicausaliList);
	if (export) {
	    return null;
	}
	model.addAttribute("concessionicausaliList", concessionicausaliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Concessionicausali concessionicausali = new Concessionicausali();
	concessionicausali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(concessionicausali);
	model.addAttribute("concessionicausali", concessionicausali);
	setPageAttributes(model);
	return "concessionicausali/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("concessionicausali") Concessionicausali concessionicausali, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(concessionicausali);
	concessionicausali.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    concessionicausaliService.insert(concessionicausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, concessionicausali, e);
	    fixRenderEntityProperty(concessionicausali);
	    return "concessionicausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + concessionicausali.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Concessionicausali concessionicausali = concessionicausaliService.findById(id);
	fixRenderEntityProperty(concessionicausali);
	model.addAttribute("concessionicausali", concessionicausali);
	setPageAttributes(model);
	return "concessionicausali/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("concessionicausali") Concessionicausali concessionicausali, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(concessionicausali);
	try {
	    concessionicausaliService.update(concessionicausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, concessionicausali, e);
	    fixRenderEntityProperty(concessionicausali);
	    return "concessionicausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + concessionicausali.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("concessionicausali") Concessionicausali concessionicausali, BindingResult result, SessionStatus status) {

	Concessionicausali objToDelete = concessionicausaliService.findById(concessionicausali.getId());
	try {
	    concessionicausaliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(concessionicausali);
	    return "concessionicausali/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Concessionicausali entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Concessionicausali entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
