package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Protocollo;
import it.gruppoinit.pal.gp.core.service.ProtocolloService;

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
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("protocollo")
public class ProtocolloController extends BaseController<Protocollo> {

    @Autowired
    private ProtocolloService protocolloService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Protocollo> protocolloList = protocolloService.findAll(null, null);
	ModelMap model = new ModelMap(protocolloList);
	boolean export = createJMesaExport(request, response, protocolloList);
	if (export) {
	    return null;
	}
	model.addAttribute("protocolloList", protocolloList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Protocollo protocollo = new Protocollo();
	fixRenderEntityProperty(protocollo);
	model.addAttribute("protocollo", protocollo);
	setPageAttributes(model);
	return "protocollo/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("protocollo") Protocollo protocollo, BindingResult result, SessionStatus status) {

	try {
	    protocolloService.insert(protocollo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, protocollo, e);
	    fixRenderEntityProperty(protocollo);
	    return "protocollo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + protocollo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Protocollo protocollo = protocolloService.findById(id);
	fixRenderEntityProperty(protocollo);
	model.addAttribute("protocollo", protocollo);
	setPageAttributes(model);
	return "protocollo/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("protocollo") Protocollo protocollo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    protocolloService.update(protocollo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, protocollo, e);
	    fixRenderEntityProperty(protocollo);
	    return "protocollo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + protocollo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("protocollo") Protocollo protocollo, BindingResult result, SessionStatus status) {

	Protocollo objToDelete = protocolloService.findById(protocollo.getId());
	try {
	    protocolloService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(protocollo);
	    return "protocollo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Protocollo entity) {

	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && entity.getOggetti().getId().getCodice() == null) {
	    entity.setOggetti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Protocollo entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
