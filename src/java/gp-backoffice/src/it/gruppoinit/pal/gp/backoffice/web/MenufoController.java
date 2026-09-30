package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Menu;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.MenuService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
@SessionAttributes("menufo")
public class MenufoController extends BaseController<Menu> {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Menu> menuList = menuService.findAll(null, null);
	ModelMap model = new ModelMap(menuList);
	boolean export = createJMesaExport(request, response, menuList);
	if (export) {
	    return null;
	}
	model.addAttribute("menuList", menuList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Menu menufo = new Menu();
	Integer ordineMax = menuService.findOrdineMax();
	if (ordineMax != null) {
	    menufo.setOrdine(ordineMax + 1);
	} else {
	    menufo.setOrdine(1);
	}
	fixRenderEntityProperty(menufo);
	List<Software> listSoftware = softwareService.findSoftwareAttivi(true);
	model.addAttribute("menufo", menufo);
	model.addAttribute("listSoftware", listSoftware);
	setPageAttributes(model);
	return "menufo/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("menufo") Menu menufo, BindingResult result, SessionStatus status) {

	if (StringUtils.isNotBlank(menufo.getSoftware().getCodice())) {
	    Software software = softwareService.findById(menufo.getSoftware().getCodice());
	    menufo.setSoftware(software);
	}
	fixMergeEntityProperty(menufo);
	try {
	    menuService.insert(menufo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, menufo, e);
	    List<Software> listSoftware = softwareService.findSoftwareAttivi(true);
	    model.addAttribute("listSoftware", listSoftware);
	    model.addAttribute("menufo", menufo);
	    fixRenderEntityProperty(menufo);
	    return "menufo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + menufo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Menu menufo = menuService.findById(id);
	fixRenderEntityProperty(menufo);
	List<Software> listSoftware = softwareService.findSoftwareAttivi(false);
	model.addAttribute("listSoftware", listSoftware);
	model.addAttribute("menufo", menufo);
	setPageAttributes(model);
	return "menufo/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("menufo") Menu menufo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (StringUtils.isNotBlank(menufo.getSoftware().getCodice())) {
	    Software software = softwareService.findById(menufo.getSoftware().getCodice());
	    menufo.setSoftware(software);
	}
	fixMergeEntityProperty(menufo);
	try {
	    menuService.update(menufo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, menufo, e);
	    List<Software> listSoftware = softwareService.findSoftwareAttivi(false);
	    model.addAttribute("listSoftware", listSoftware);
	    model.addAttribute("menufo", menufo);
	    fixRenderEntityProperty(menufo);
	    return "menufo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + menufo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("menufo") Menu menufo, BindingResult result, SessionStatus status) {

	Menu objToDelete = menuService.findById(menufo.getId());
	try {
	    menuService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(menufo);
	    model.addAttribute("menufo", menufo);
	    return "menufo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Menu entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Menu entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
