package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BandiAlberoprocService;
import it.gruppoinit.pal.gp.core.service.BandiService;
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
@SessionAttributes("bandialberoproc")
public class BandiAlberoprocController extends BaseController<BandiAlberoproc> {

    @Autowired
    private BandiAlberoprocService bandialberoprocService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private BandiService bandiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceBandi") Integer codiceBandi, HttpServletRequest request, HttpServletResponse response) {

	List<BandiAlberoproc> bandialberoprocList = bandialberoprocService.findByBandi(codiceBandi);
	ModelMap model = new ModelMap(bandialberoprocList);
	boolean export = createJMesaExport(request, response, bandialberoprocList);
	if (export) {
	    return null;
	}
	Bandi bando = bandiService.findById(new PkId(codiceBandi));
	BandiAlberoproc bandialberoproc = new BandiAlberoproc();
	int max = bandialberoprocService.findMaxOrdine(bando.getId().getCodice());
	bandialberoproc.setOrdine(max + 1);
	bandialberoproc.setBandi(bando);
	fixRenderEntityProperty(bandialberoproc);
	model.addAttribute("bandialberoproc", bandialberoproc);
	model.addAttribute("bandialberoprocList", bandialberoprocList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	BandiAlberoproc bandialberoproc = new BandiAlberoproc();
	fixRenderEntityProperty(bandialberoproc);
	model.addAttribute("bandialberoproc", bandialberoproc);
	setPageAttributes(model);
	return "bandialberoproc/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("bandialberoproc") BandiAlberoproc bandialberoproc, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(bandialberoproc);
	try {
	    bandialberoprocService.insert(bandialberoproc);
	} catch (Exception e) {
	    List<BandiAlberoproc> bandialberoprocList = bandialberoprocService.findByBandi(bandialberoproc.getBandi().getId().getCodice());
	    copyErrorsToBindingResult(result, bandialberoproc, e);
	    fixRenderEntityProperty(bandialberoproc);
	    model.addAttribute("bandialberoproc", bandialberoproc);
	    model.addAttribute("bandialberoprocList", bandialberoprocList);
	    return "bandialberoproc/list";
	}
	status.setComplete();
	return "redirect:list.htm?codiceBandi=" + bandialberoproc.getBandi().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String upColumn(@RequestParam("codiceBandoAlberoprocSup") Integer codiceBandoAlberoprocSup,
	    @RequestParam("codiceBandoAlberoproc") Integer codiceBandoAlberoproc, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	BandiAlberoproc bandialberoproc = bandialberoprocService.findById(new PkId(codiceBandoAlberoproc));
	try {
	    bandialberoprocService.updateUpOrdine(codiceBandoAlberoproc, codiceBandoAlberoprocSup);
	} catch (Exception e) {
	    //log.error("Errore durante l'elaborazione dell'ordine : {}", e);
	    throw new RuntimeException("Errore durante l'elaborazione dell'ordine: " + e);
	}
	return "redirect:list.htm?codiceBandi=" + bandialberoproc.getBandi().getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String downColumn(@RequestParam("codiceBandoAlberoprocInf") Integer codiceBandoAlberoprocInf,
	    @RequestParam("codiceBandoAlberoproc") Integer codiceBandoAlberoproc, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	BandiAlberoproc bandiAlberoproc = bandialberoprocService.findById(new PkId(codiceBandoAlberoproc));
	try {
	    bandialberoprocService.updateDownOrdine(codiceBandoAlberoproc, codiceBandoAlberoprocInf);
	} catch (Exception e) {
	    // log.error("Errore durante l'elaborazione dell'ordine : {}", e);
	    throw new RuntimeException("Errore durante l'elaborazione dell'ordine: " + e);
	}
	return "redirect:list.htm?codiceBandi=" + bandiAlberoproc.getBandi().getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	BandiAlberoproc bandialberoproc = bandialberoprocService.findById(id);
	fixRenderEntityProperty(bandialberoproc);
	model.addAttribute("bandialberoproc", bandialberoproc);
	setPageAttributes(model);
	return "bandialberoproc/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("bandialberoproc") BandiAlberoproc bandialberoproc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(bandialberoproc);
	try {
	    bandialberoprocService.update(bandialberoproc);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bandialberoproc, e);
	    fixRenderEntityProperty(bandialberoproc);
	    return "bandialberoproc/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bandialberoproc.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("bandialberoproc") BandiAlberoproc bandialberoproc, BindingResult result, SessionStatus status) {

	BandiAlberoproc objToDelete = bandialberoprocService.findById(bandialberoproc.getId());
	try {
	    bandialberoprocService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bandialberoproc);
	    return "bandialberoproc/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String deletePubblicazioneFromCodice(@RequestParam("codiceBandialberoproc") Integer codiceBandialberoproc,
	    @ModelAttribute("bandialberoproc") BandiAlberoproc bandialberoproc, BindingResult result, SessionStatus status) {

	BandiAlberoproc objToDelete = bandialberoprocService.findById(new PkId(codiceBandialberoproc));
	try {
	    bandialberoprocService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bandialberoproc);
	    return "bandialberoproc/list";
	}
	status.setComplete();
	return "redirect:list.htm?codiceBandi=" + objToDelete.getBandi().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(BandiAlberoproc entity) {

    }

    @Override
    protected void fixRenderEntityProperty(BandiAlberoproc entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
