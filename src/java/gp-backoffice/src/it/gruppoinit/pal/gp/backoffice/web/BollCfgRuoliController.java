package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.domain.BollCfgRuoli;
import it.gruppoinit.pal.gp.core.domain.BollCfgRuoliId;
import it.gruppoinit.pal.gp.core.service.BollCfgRuoliService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("bllcfgruoli")
public class BollCfgRuoliController extends BaseController<BollCfgRuoli> {

    @Autowired
    private BollCfgRuoliService bllcfgruoliService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgRuoli> bllcfgruoliList = bllcfgruoliService.findAll(null, null);
	ModelMap model = new ModelMap(bllcfgruoliList);
	boolean export = createJMesaExport(request, response, bllcfgruoliList);
	if (export) {
	    return null;
	}
	model.addAttribute("bllcfgruoliList", bllcfgruoliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	BollCfgRuoli bllcfgruoli = new BollCfgRuoli();
	fixRenderEntityProperty(bllcfgruoli);
	model.addAttribute("bllcfgruoli", bllcfgruoli);
	setPageAttributes(model);
	return "bllcfgruoli/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("bllcfgruoli") BollCfgRuoli bllcfgruoli, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(bllcfgruoli);
	try {
	    bllcfgruoliService.insert(bllcfgruoli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bllcfgruoli, e);
	    fixRenderEntityProperty(bllcfgruoli);
	    return "bllcfgruoli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bllcfgruoli.getId().toString() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("fk_bollcfgtipo_id") Integer codice, @RequestParam("fk_ruoli_id") Integer ruoli, Model model,
	    HttpServletRequest request) {

	BollCfgRuoliId id = new BollCfgRuoliId(codice, ruoli);
	BollCfgRuoli bllcfgruoli = bllcfgruoliService.findById(id);
	fixRenderEntityProperty(bllcfgruoli);
	model.addAttribute("bllcfgruoli", bllcfgruoli);
	setPageAttributes(model);
	return "bllcfgruoli/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("bllcfgruoli") BollCfgRuoli bllcfgruoli, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(bllcfgruoli);
	try {
	    bllcfgruoliService.update(bllcfgruoli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bllcfgruoli, e);
	    fixRenderEntityProperty(bllcfgruoli);
	    return "bllcfgruoli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bllcfgruoli.getId().toString() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("bllcfgruoli") BollCfgRuoli bllcfgruoli, BindingResult result, SessionStatus status) {

	BollCfgRuoli objToDelete = bllcfgruoliService.findById(bllcfgruoli.getId());
	try {
	    bllcfgruoliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bllcfgruoli);
	    return "bllcfgruoli/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(BollCfgRuoli entity) {

    }

    @Override
    protected void fixRenderEntityProperty(BollCfgRuoli entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
