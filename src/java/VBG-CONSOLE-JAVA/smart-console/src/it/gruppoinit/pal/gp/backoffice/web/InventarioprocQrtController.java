package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.InventarioprocQrt;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.InventarioprocQrtService;
import it.gruppoinit.pal.gp.core.service.InventarioprocQrtService.STATO_PUBBLICAZIONE;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
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

@Controller
@SessionAttributes("inventarioprocQrt")
public class InventarioprocQrtController extends BaseController<InventarioprocQrt> {

    @Autowired
    private InventarioprocQrtService inventarioprocQrtService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;

    @RequestMapping
    public ModelMap list(@RequestParam("inventarioproc.id.codice") Integer codiceinventario, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceinventario));
	List<InventarioprocQrt> inventarioprocQrtList = inventarioprocQrtService.findByCodiceInventario(inventarioprocedimenti.getId().getIdcomune(),
		codiceinventario, STATO_PUBBLICAZIONE.TUTTI);
	ModelMap model = new ModelMap(inventarioprocQrtList);
	boolean export = createJMesaExport(request, response, inventarioprocQrtList);
	if (export)
	    return null;
	model.addAttribute("list", inventarioprocQrtList);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return model;
    }

    @RequestMapping
    public String delete(@RequestParam("inventarioproc.id.codice") Integer codiceInventarioprocedimenti,
	    @ModelAttribute("inventarioprocQrt") InventarioprocQrt inventarioprocQrt, BindingResult result, SessionStatus status) {

	InventarioprocQrt objToDelete = inventarioprocQrtService.findById(inventarioprocQrt.getId());
	try {
	    inventarioprocQrtService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocQrt, true, "", e);
	    fixRenderEntityProperty(inventarioprocQrt);
	    return "inventarioprocqrt/form";
	}
	status.setComplete();
	return "redirect:list.htm?inventarioproc.id.codice=" + codiceInventarioprocedimenti;
    }

    @RequestMapping
    public String insert(@ModelAttribute("inventarioprocQrt") InventarioprocQrt inventarioprocQrt, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(inventarioprocQrt);
	try {
	    inventarioprocQrtService.insert(inventarioprocQrt);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocQrt, true, "", e);
	    fixRenderEntityProperty(inventarioprocQrt);
	    return "inventarioprocqrt/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + inventarioprocQrt.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("inventarioprocQrt") InventarioprocQrt inventarioprocQrt, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri
	// attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(inventarioprocQrt);
	try {
	    inventarioprocQrtService.update(inventarioprocQrt);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocQrt, true, "", e);
	    fixRenderEntityProperty(inventarioprocQrt);
	    return "inventarioprocqrt/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + inventarioprocQrt.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("inventarioproc.id.codice") Integer codiceInventarioprocedimenti, Model model) {

	InventarioprocQrt inventarioprocQrt = new InventarioprocQrt();
	PkId idInventarioprocedimenti = new PkId(codiceInventarioprocedimenti);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(idInventarioprocedimenti);
	inventarioprocQrt.setInventarioprocedimento(inventarioprocedimenti);
	fixRenderEntityProperty(inventarioprocQrt);
	model.addAttribute("inventarioprocQrt", inventarioprocQrt);
	setPageAttributes(model);
	return "inventarioprocqrt/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	InventarioprocQrt inventarioprocQrt = inventarioprocQrtService.findById(id);
	fixRenderEntityProperty(inventarioprocQrt);
	model.addAttribute("inventarioprocQrt", inventarioprocQrt);
	setPageAttributes(model);
	return "inventarioprocqrt/form";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(InventarioprocQrt entity) {

	// IMPLEMENTATO NEL SERVICE
    }

    @Override
    protected void fixRenderEntityProperty(InventarioprocQrt entity) {

	if (entity != null) {
	    if (entity.getInventarioprocedimento() == null) {
		entity.setInventarioprocedimento(new Inventarioprocedimenti());
	    } else {
		if (entity.getInventarioprocedimento().getId() != null) {
		    if (entity.getInventarioprocedimento().getId().getCodice() != null) {
			Inventarioprocedimenti inv = inventarioprocedimentiService.findById(new PkId(entity.getInventarioprocedimento().getId()
				.getCodice()));
			entity.setInventarioprocedimento(inv);
		    }
		}
	    }
	    if (entity.getOggetti() == null) {
		entity.setOggetti(new Oggetti());
	    } else {
		if (entity.getOggetti().getId() != null) {
		    if (entity.getOggetti().getId().getCodice() != null) {
			Oggetti inv = oggettiService.findById(new PkId(entity.getOggetti().getId().getCodice()));
			entity.setOggetti(inv);
		    }
		}
	    }
	}
    }
}
