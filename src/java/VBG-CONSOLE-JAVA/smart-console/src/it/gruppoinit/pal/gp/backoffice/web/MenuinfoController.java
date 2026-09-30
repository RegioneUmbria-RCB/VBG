package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Menuinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MenuinfoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

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
 * @author gianpaolot
 */
@Controller
@SessionAttributes("menuinfo")
public class MenuinfoController extends BaseController<Menuinfo> {

    @Autowired
    private MenuinfoService menuinfoService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Menuinfo> menuinfoList = menuinfoService.findAll(null, null);
	ModelMap model = new ModelMap(menuinfoList);
	boolean export = createJMesaExport(request, response, menuinfoList);
	if (export) {
	    return null;
	}
	model.addAttribute("menuinfoList", menuinfoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Menuinfo menuinfo = new Menuinfo();
	Integer ordineMax = menuinfoService.findOrdineMax();
	if (ordineMax != null) {
	    menuinfo.setOrdine(ordineMax + 1);
	} else {
	    menuinfo.setOrdine(1);
	}
	fixRenderEntityProperty(menuinfo);
	model.addAttribute("menuinfo", menuinfo);
	setPageAttributes(model);
	return "menuinfo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("menuinfo") Menuinfo menuinfo, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(menuinfo);
	try {
	    menuinfoService.insert(menuinfo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, menuinfo, e);
	    fixRenderEntityProperty(menuinfo);
	    model.addAttribute("menuinfo", menuinfo);
	    return "menuinfo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + menuinfo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Menuinfo menuinfo = menuinfoService.findById(id);
	fixRenderEntityProperty(menuinfo);
	model.addAttribute("menuinfo", menuinfo);
	setPageAttributes(model);
	return "menuinfo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("menuinfo") Menuinfo menuinfo, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(menuinfo);
	try {
	    menuinfoService.update(menuinfo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, menuinfo, e);
	    fixRenderEntityProperty(menuinfo);
	    model.addAttribute("menuinfo", menuinfo);
	    return "menuinfo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + menuinfo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("menuinfo") Menuinfo menuinfo, BindingResult result, SessionStatus status) {

	Menuinfo objToDelete = menuinfoService.findById(menuinfo.getId());
	try {
	    menuinfoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(menuinfo);
	    return "menuinfo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Menuinfo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Menuinfo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
