package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.AlberoprocFoTopService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
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

@Controller
@SessionAttributes("alberoprocFoTop")
public class AlberoprocFoTopController extends BaseController<AlberoprocFoTop> {

    @Autowired
    private AlberoprocFoTopService alberoprocFoTopService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<AlberoprocFoTop> list = alberoprocFoTopService.findAll(null, null);
	ModelMap model = new ModelMap(list);
	boolean export = createJMesaExport(request, response, list);
	if (export)
	    return null;
	model.addAttribute("list", list);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("alberoprocFoTop") AlberoprocFoTop alberoprocFoTop, BindingResult result, SessionStatus status) {

	AlberoprocFoTop objToDelete = alberoprocFoTopService.findById(alberoprocFoTop.getId());
	try {
	    alberoprocFoTopService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoprocFoTop);
	    return "alberoprocfotop/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoprocFoTop") AlberoprocFoTop alberoprocFoTop, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(alberoprocFoTop);
	try {
	    alberoprocFoTopService.insert(alberoprocFoTop);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocFoTop, e);
	    fixRenderEntityProperty(alberoprocFoTop);
	    return "alberoprocfotop/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocFoTop.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoprocFoTop") AlberoprocFoTop AlberoprocFoTop, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(AlberoprocFoTop);
	try {
	    alberoprocFoTopService.update(AlberoprocFoTop);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, AlberoprocFoTop, e);
	    fixRenderEntityProperty(AlberoprocFoTop);
	    return "alberoprocfotop/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + AlberoprocFoTop.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	AlberoprocFoTop alberoprocFoTop = new AlberoprocFoTop();
	Software s = softwareService.findById(ORMHelper.getSoftware());
	alberoprocFoTop.setSoftware(s);
	fixRenderEntityProperty(alberoprocFoTop);
	model.addAttribute("alberoprocFoTop", alberoprocFoTop);
	setPageAttributes(model);
	return "alberoprocfotop/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocFoTop AlberoprocFoTop = alberoprocFoTopService.findById(id);
	fixRenderEntityProperty(AlberoprocFoTop);
	model.addAttribute("alberoprocFoTop", AlberoprocFoTop);
	setPageAttributes(model);
	return "alberoprocfotop/form";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(AlberoprocFoTop entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AlberoprocFoTop entity) {

	if (entity != null) {
	    if (entity.getAlberoproc() == null) {
		entity.setAlberoproc(new Alberoproc());
	    }
	}
    }
}
