package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercatiId;
import it.gruppoinit.pal.gp.core.service.BollCfgMercatiService;
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
 * @author
 */
@Controller
@SessionAttributes("bollcfgmercati")
public class BollCfgMercatiController extends BaseController<BollCfgMercati> {

    @Autowired
    private BollCfgMercatiService bollcfgmercatiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgMercati> bollcfgmercatiList = bollcfgmercatiService.findAll(null, null);
	ModelMap model = new ModelMap(bollcfgmercatiList);
	boolean export = createJMesaExport(request, response, bollcfgmercatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("bollcfgmercatiList", bollcfgmercatiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	BollCfgMercati bollcfgmercati = new BollCfgMercati();
	fixRenderEntityProperty(bollcfgmercati);
	model.addAttribute("bollcfgmercati", bollcfgmercati);
	setPageAttributes(model);
	return "bollcfgmercati/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("bollcfgmercati") BollCfgMercati bollcfgmercati, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(bollcfgmercati);
	try {
	    bollcfgmercatiService.insert(bollcfgmercati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bollcfgmercati, e);
	    fixRenderEntityProperty(bollcfgmercati);
	    return "bollcfgmercati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bollcfgmercati.getId().toString() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("fk_bollcfgtipo_id") Integer codice, @RequestParam("fk_codicemercato") Integer mercato, Model model,
	    HttpServletRequest request) {

	BollCfgMercatiId id = new BollCfgMercatiId(codice, mercato);
	BollCfgMercati bollcfgmercati = bollcfgmercatiService.findById(id);
	fixRenderEntityProperty(bollcfgmercati);
	model.addAttribute("bollcfgmercati", bollcfgmercati);
	setPageAttributes(model);
	return "bollcfgmercati/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("bollcfgmercati") BollCfgMercati bollcfgmercati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(bollcfgmercati);
	try {
	    bollcfgmercatiService.update(bollcfgmercati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bollcfgmercati, e);
	    fixRenderEntityProperty(bollcfgmercati);
	    return "bollcfgmercati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bollcfgmercati.getId().toString() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("bollcfgmercati") BollCfgMercati bollcfgmercati, BindingResult result, SessionStatus status) {

	BollCfgMercati objToDelete = bollcfgmercatiService.findById(bollcfgmercati.getId());
	try {
	    bollcfgmercatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bollcfgmercati);
	    return "bollcfgmercati/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(BollCfgMercati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(BollCfgMercati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
