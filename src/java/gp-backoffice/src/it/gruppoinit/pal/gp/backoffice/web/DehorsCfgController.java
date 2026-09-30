package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.DehorsCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.service.DehorsCfgService;
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
@SessionAttributes("dehorscfg")
public class DehorsCfgController extends BaseController<DehorsCfg> {

    @Autowired
    private DehorsCfgService dehorscfgService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<DehorsCfg> dehorscfgList = dehorscfgService.findAll(null, null);
	ModelMap model = new ModelMap(dehorscfgList);
	boolean export = createJMesaExport(request, response, dehorscfgList);
	if (export) {
	    return null;
	}
	model.addAttribute("dehorscfgList", dehorscfgList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	DehorsCfg dehorscfg = new DehorsCfg();
	dehorscfg.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(dehorscfg);
	model.addAttribute("dehorscfg", dehorscfg);
	setPageAttributes(model);
	return "dehorscfg/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("dehorscfg") DehorsCfg dehorscfg, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(dehorscfg);
	dehorscfg.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    dehorscfgService.insert(dehorscfg);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dehorscfg, e);
	    fixRenderEntityProperty(dehorscfg);
	    return "dehorscfg/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dehorscfg.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	DehorsCfg dehorscfg = dehorscfgService.findById(id);
	fixRenderEntityProperty(dehorscfg);
	model.addAttribute("dehorscfg", dehorscfg);
	setPageAttributes(model);
	return "dehorscfg/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("dehorscfg") DehorsCfg dehorscfg, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(dehorscfg);
	try {
	    dehorscfgService.update(dehorscfg);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dehorscfg, e);
	    fixRenderEntityProperty(dehorscfg);
	    return "dehorscfg/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dehorscfg.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("dehorscfg") DehorsCfg dehorscfg, BindingResult result, SessionStatus status) {

	DehorsCfg objToDelete = dehorscfgService.findById(dehorscfg.getId());
	try {
	    dehorscfgService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(dehorscfg);
	    return "dehorscfg/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(DehorsCfg entity) {

    }

    @Override
    protected void fixRenderEntityProperty(DehorsCfg entity) {

	if (entity.getTipologiaregistri() == null) {
	    entity.setTipologiaregistri(new Tipologiaregistri());
	}
	if (entity.getConcessionicausali() == null) {
	    entity.setConcessionicausali(new Concessionicausali());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
