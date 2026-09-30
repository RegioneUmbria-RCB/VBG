package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiaperturaService;

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
 * @author lucap
 */
@Controller
@SessionAttributes("tipiapertura")
public class TipiaperturaController extends BaseController<Tipiapertura> {

    @Autowired
    private TipiaperturaService tipiaperturaService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiapertura> tipiaperturaList = tipiaperturaService.findAll(null, null);
	ModelMap model = new ModelMap(tipiaperturaList);
	boolean export = createJMesaExport(request, response, tipiaperturaList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipiaperturaList", tipiaperturaList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipiapertura tipiapertura = new Tipiapertura();
	tipiapertura.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipiapertura);
	model.addAttribute("tipiapertura", tipiapertura);
	setPageAttributes(model);
	return "tipiapertura/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiapertura") Tipiapertura tipiapertura, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipiapertura);
	tipiapertura.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    tipiaperturaService.insert(tipiapertura);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiapertura, e);
	    fixRenderEntityProperty(tipiapertura);
	    return "tipiapertura/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiapertura.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiapertura tipiapertura = tipiaperturaService.findById(id);
	fixRenderEntityProperty(tipiapertura);
	model.addAttribute("tipiapertura", tipiapertura);
	setPageAttributes(model);
	return "tipiapertura/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiapertura") Tipiapertura tipiapertura, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipiapertura);
	try {
	    tipiaperturaService.update(tipiapertura);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiapertura, e);
	    fixRenderEntityProperty(tipiapertura);
	    return "tipiapertura/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiapertura.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipiapertura") Tipiapertura tipiapertura, BindingResult result, SessionStatus status) {

	Tipiapertura objToDelete = tipiaperturaService.findById(tipiapertura.getId());
	try {
	    tipiaperturaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipiapertura);
	    return "tipiapertura/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiapertura entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipiapertura entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
