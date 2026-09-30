package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.InventarioprocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

import java.util.ArrayList;
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
@SessionAttributes("inventarioprocsoggfirmatari")
public class InventarioprocSoggFirmatariController extends BaseController<InventarioprocSoggFirmatari> {

    @Autowired
    private InventarioprocSoggFirmatariService inventarioprocsoggfirmatariService;
    @Autowired
    private DocumentiService documentiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codicedocumento") Integer codicedocumento, HttpServletRequest request, HttpServletResponse response) {

	List<InventarioprocSoggFirmatari> inventarioprocsoggfirmatariList = inventarioprocsoggfirmatariService.findByDocumento(codicedocumento, null,
		null);
	Documenti documenti = documentiService.findById(new PkId(codicedocumento));
	ModelMap model = new ModelMap(inventarioprocsoggfirmatariList);
	boolean export = createJMesaExport(request, response, inventarioprocsoggfirmatariList);
	if (export) {
	    return null;
	}
	model.addAttribute("inventarioprocsoggfirmatariList", inventarioprocsoggfirmatariList);
	model.addAttribute("documenti", documenti);
	return model;
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codicedocumento") Integer codicedocumento) {

	InventarioprocSoggFirmatari inventarioprocsoggfirmatari = new InventarioprocSoggFirmatari();
	Documenti documenti = documentiService.findById(new PkId(codicedocumento));
	inventarioprocsoggfirmatari.setDocumenti(documenti);
	fixRenderEntityProperty(inventarioprocsoggfirmatari);
	model.addAttribute("inventarioprocsoggfirmatari", inventarioprocsoggfirmatari);
	setPageAttributes(model);
	return "inventarioprocsoggfirmatari/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("inventarioprocsoggfirmatari") InventarioprocSoggFirmatari inventarioprocsoggfirmatari,
	    BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(inventarioprocsoggfirmatari);
	try {
	    inventarioprocsoggfirmatariService.insert(inventarioprocsoggfirmatari);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocsoggfirmatari, e);
	    fixRenderEntityProperty(inventarioprocsoggfirmatari);
	    return "inventarioprocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicedocumento=" + inventarioprocsoggfirmatari.getDocumenti().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	InventarioprocSoggFirmatari inventarioprocsoggfirmatari = inventarioprocsoggfirmatariService.findById(id);
	fixRenderEntityProperty(inventarioprocsoggfirmatari);
	model.addAttribute("inventarioprocsoggfirmatari", inventarioprocsoggfirmatari);
	setPageAttributes(model);
	return "inventarioprocsoggfirmatari/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("inventarioprocsoggfirmatari") InventarioprocSoggFirmatari inventarioprocsoggfirmatari,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(inventarioprocsoggfirmatari);
	try {
	    inventarioprocsoggfirmatariService.update(inventarioprocsoggfirmatari);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocsoggfirmatari, e);
	    fixRenderEntityProperty(inventarioprocsoggfirmatari);
	    return "inventarioprocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicedocumento=" + inventarioprocsoggfirmatari.getDocumenti().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteByCodice(Model model, @RequestParam("codice") Integer codice) {

	InventarioprocSoggFirmatari objToDelete = inventarioprocsoggfirmatariService.findById(new PkId(codice));
	try {
	    inventarioprocsoggfirmatariService.delete(objToDelete);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, objToDelete, e);
	    //	    fixRenderEntityProperty(objToDelete);
	    List<String> error = new ArrayList<String>();
	    error.add(e.getMessage());
	    FlashMessages.setWarnings(error);
	    return "redirect:list.htm?codicedocumento=" + objToDelete.getDocumenti().getId().getCodice();
	}
	return "redirect:list.htm?codicedocumento=" + objToDelete.getDocumenti().getId().getCodice();
    }

    @RequestMapping
    public String delete(@ModelAttribute("inventarioprocsoggfirmatari") InventarioprocSoggFirmatari inventarioprocsoggfirmatari,
	    BindingResult result, SessionStatus status) {

	InventarioprocSoggFirmatari objToDelete = inventarioprocsoggfirmatariService.findById(inventarioprocsoggfirmatari.getId());
	try {
	    inventarioprocsoggfirmatariService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(inventarioprocsoggfirmatari);
	    return "inventarioprocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicedocumento=" + objToDelete.getDocumenti().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(InventarioprocSoggFirmatari entity) {

    }

    @Override
    protected void fixRenderEntityProperty(InventarioprocSoggFirmatari entity) {

	if (entity.getDocumenti() == null) {
	    entity.setDocumenti(new Documenti());
	}
	if (entity.getTipisoggetto() == null) {
	    entity.setTipisoggetto(new Tipisoggetto());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
