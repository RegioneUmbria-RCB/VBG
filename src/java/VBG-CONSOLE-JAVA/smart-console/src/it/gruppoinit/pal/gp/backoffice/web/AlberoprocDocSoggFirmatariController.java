package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
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
@SessionAttributes("alberoprocdocsoggfirmatari")
public class AlberoprocDocSoggFirmatariController extends BaseController<AlberoprocDocSoggFirmatari> {

    @Autowired
    private AlberoprocDocSoggFirmatariService alberoprocdocsoggfirmatariService;
    @Autowired
    private AlberoprocDocumentiService alberoprocDocumentiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codicedocumento") Integer codicedocumento, HttpServletRequest request, HttpServletResponse response) {

	List<AlberoprocDocSoggFirmatari> alberoprocdocsoggfirmatariList = alberoprocdocsoggfirmatariService.findByDocumento(codicedocumento, null,
		null);
	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.findById(new PkId(codicedocumento));
	ModelMap model = new ModelMap(alberoprocdocsoggfirmatariList);
	boolean export = createJMesaExport(request, response, alberoprocdocsoggfirmatariList);
	if (export) {
	    return null;
	}
	model.addAttribute("alberoprocdocsoggfirmatariList", alberoprocdocsoggfirmatariList);
	model.addAttribute("alberoprocDocumenti", alberoprocDocumenti);
	return model;
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codicedocumento") Integer codicedocumento) {

	AlberoprocDocSoggFirmatari alberoprocdocsoggfirmatari = new AlberoprocDocSoggFirmatari();
	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.findById(new PkId(codicedocumento));
	alberoprocdocsoggfirmatari.setAlberoprocDocumenti(alberoprocDocumenti);
	fixRenderEntityProperty(alberoprocdocsoggfirmatari);
	model.addAttribute("alberoprocdocsoggfirmatari", alberoprocdocsoggfirmatari);
	setPageAttributes(model);
	return "alberoprocdocsoggfirmatari/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoprocdocsoggfirmatari") AlberoprocDocSoggFirmatari alberoprocdocsoggfirmatari, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(alberoprocdocsoggfirmatari);
	try {
	    alberoprocdocsoggfirmatariService.insert(alberoprocdocsoggfirmatari);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocdocsoggfirmatari, e);
	    fixRenderEntityProperty(alberoprocdocsoggfirmatari);
	    return "alberoprocdocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicedocumento=" + alberoprocdocsoggfirmatari.getAlberoprocDocumenti().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocDocSoggFirmatari alberoprocdocsoggfirmatari = alberoprocdocsoggfirmatariService.findById(id);
	fixRenderEntityProperty(alberoprocdocsoggfirmatari);
	model.addAttribute("alberoprocdocsoggfirmatari", alberoprocdocsoggfirmatari);
	setPageAttributes(model);
	return "alberoprocdocsoggfirmatari/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoprocdocsoggfirmatari") AlberoprocDocSoggFirmatari alberoprocdocsoggfirmatari, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(alberoprocdocsoggfirmatari);
	try {
	    alberoprocdocsoggfirmatariService.update(alberoprocdocsoggfirmatari);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocdocsoggfirmatari, e);
	    fixRenderEntityProperty(alberoprocdocsoggfirmatari);
	    return "alberoprocdocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicedocumento=" + alberoprocdocsoggfirmatari.getAlberoprocDocumenti().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteByCodice(Model model, @RequestParam("codice") Integer codice) {

	AlberoprocDocSoggFirmatari objToDelete = alberoprocdocsoggfirmatariService.findById(new PkId(codice));
	try {
	    alberoprocdocsoggfirmatariService.delete(objToDelete);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, objToDelete, e);
	    //	    fixRenderEntityProperty(objToDelete);
	    List<String> error = new ArrayList<String>();
	    error.add(e.getMessage());
	    FlashMessages.setWarnings(error);
	    return "redirect:list.htm?codicedocumento=" + objToDelete.getAlberoprocDocumenti().getId().getCodice();
	}
	return "redirect:list.htm?codicedocumento=" + objToDelete.getAlberoprocDocumenti().getId().getCodice();
    }

    @RequestMapping
    public String delete(@ModelAttribute("alberoprocdocsoggfirmatari") AlberoprocDocSoggFirmatari alberoprocdocsoggfirmatari, BindingResult result,
	    SessionStatus status) {

	AlberoprocDocSoggFirmatari objToDelete = alberoprocdocsoggfirmatariService.findById(alberoprocdocsoggfirmatari.getId());
	try {
	    alberoprocdocsoggfirmatariService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoprocdocsoggfirmatari);
	    return "alberoprocdocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicedocumento=" + objToDelete.getAlberoprocDocumenti().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(AlberoprocDocSoggFirmatari entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AlberoprocDocSoggFirmatari entity) {

	if (entity.getAlberoprocDocumenti() == null) {
	    entity.setAlberoprocDocumenti(new AlberoprocDocumenti());
	}
	if (entity.getTipisoggetto() == null) {
	    entity.setTipisoggetto(new Tipisoggetto());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
