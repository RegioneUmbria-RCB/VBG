package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.InventarioprocFoTop;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.InventarioprocFoTopService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
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
@SessionAttributes("inventarioprocFoTop")
public class InventarioprocFoTopController extends BaseController<InventarioprocFoTop> {

    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocFoTopService inventarioprocFoTopService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<InventarioprocFoTop> list = inventarioprocFoTopService.findAll(null, null);
	ModelMap model = new ModelMap(list);
	boolean export = createJMesaExport(request, response, list);
	if (export)
	    return null;
	model.addAttribute("list", list);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("inventarioprocFoTop") InventarioprocFoTop inventarioprocFoTop, BindingResult result, SessionStatus status) {

	InventarioprocFoTop objToDelete = inventarioprocFoTopService.findById(inventarioprocFoTop.getId());
	try {
	    inventarioprocFoTopService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(inventarioprocFoTop);
	    return "inventarioprocfotop/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("inventarioprocFoTop") InventarioprocFoTop inventarioprocFoTop, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(inventarioprocFoTop);
	try {
	    inventarioprocFoTopService.insert(inventarioprocFoTop);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocFoTop, e);
	    fixRenderEntityProperty(inventarioprocFoTop);
	    return "inventarioprocfotop/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + inventarioprocFoTop.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("inventarioprocFoTop") InventarioprocFoTop InventarioprocFoTop, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(InventarioprocFoTop);
	try {
	    inventarioprocFoTopService.update(InventarioprocFoTop);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, InventarioprocFoTop, e);
	    fixRenderEntityProperty(InventarioprocFoTop);
	    return "inventarioprocfotop/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + InventarioprocFoTop.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	InventarioprocFoTop inventarioprocFoTop = new InventarioprocFoTop();
	Software s = softwareService.findById(ORMHelper.getSoftware());
	inventarioprocFoTop.setSoftware(s);
	fixRenderEntityProperty(inventarioprocFoTop);
	model.addAttribute("inventarioprocFoTop", inventarioprocFoTop);
	setPageAttributes(model);
	return "inventarioprocfotop/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	InventarioprocFoTop InventarioprocFoTop = inventarioprocFoTopService.findById(id);
	fixRenderEntityProperty(InventarioprocFoTop);
	model.addAttribute("inventarioprocFoTop", InventarioprocFoTop);
	setPageAttributes(model);
	return "inventarioprocfotop/form";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(InventarioprocFoTop entity) {

	if (entity != null) {
	    if (entity.getInventarioprocedimenti() != null) {
		if (entity.getInventarioprocedimenti().getId() != null) {
		    if (entity.getInventarioprocedimenti().getId().getCodice() != null) {
			Inventarioprocedimenti inv = inventarioprocedimentiService.findById(new PkId(entity.getInventarioprocedimenti().getId()
				.getCodice()));
			entity.setInventarioprocedimenti(inv);
		    }
		}
	    }
	}
    }

    @Override
    protected void fixRenderEntityProperty(InventarioprocFoTop entity) {

	if (entity != null) {
	    if (entity.getInventarioprocedimenti() == null) {
		entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	    } else {
		if (entity.getInventarioprocedimenti().getId() != null) {
		    if (entity.getInventarioprocedimenti().getId().getCodice() != null) {
			Inventarioprocedimenti inv = inventarioprocedimentiService.findById(new PkId(entity.getInventarioprocedimenti().getId()
				.getCodice()));
			entity.setInventarioprocedimenti(inv);
		    }
		}
	    }
	}
    }
}
