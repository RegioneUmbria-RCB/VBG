package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;
import it.gruppoinit.pal.gp.core.service.AreeService;
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
@SessionAttributes("aree")
public class AreeController extends BaseController<Aree> {

    @Autowired
    private AreeService areeService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Aree> areeList = areeService.findAll(null, null);
	ModelMap model = new ModelMap(areeList);
	boolean export = createJMesaExport(request, response, areeList);
	if (export) {
	    return null;
	}
	model.addAttribute("areeList", areeList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Aree aree = new Aree();
	aree.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(aree);
	model.addAttribute("aree", aree);
	setPageAttributes(model);
	return "aree/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("aree") Aree aree, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(aree);
	aree.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    areeService.insert(aree);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, aree, e);
	    fixRenderEntityProperty(aree);
	    return "aree/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + aree.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Aree aree = areeService.findById(id);
	fixRenderEntityProperty(aree);
	model.addAttribute("aree", aree);
	setPageAttributes(model);
	return "aree/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("aree") Aree aree, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(aree);
	try {
	    areeService.update(aree);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, aree, e);
	    fixRenderEntityProperty(aree);
	    return "aree/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + aree.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("aree") Aree aree, BindingResult result, SessionStatus status) {

	Aree objToDelete = areeService.findById(aree.getId());
	try {
	    areeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(aree);
	    return "aree/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Aree entity) {

	if (entity.getComune() != null && entity.getComune().getCodicecomune() == null) {
	    entity.setComune(null);
	}
	if (entity.getTipiaree() != null && entity.getTipiaree().getId() != null && entity.getTipiaree().getId().getCodice() == null) {
	    entity.setTipiaree(null);
	}
	if (entity.getSoftware() != null && entity.getSoftware().getCodice() == null) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Aree entity) {

	if (entity.getTipiaree() == null) {
	    entity.setTipiaree(new Tipiaree());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
