package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.AllegatiDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.service.AllegatiDocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
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
@SessionAttributes("allegatidocsoggfirmatari")
public class AllegatiDocSoggFirmatariController extends BaseController<AllegatiDocSoggFirmatari> {

    @Autowired
    private AllegatiDocSoggFirmatariService allegatidocsoggfirmatariService;
    @Autowired
    private AllegatiService allagatiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceallegato") Integer codicedocumento, @RequestParam("idcomunerecord") String idcomunerecord,
	    HttpServletRequest request, HttpServletResponse response) {

	List<AllegatiDocSoggFirmatari> allegatidocsoggfirmatariList = allegatidocsoggfirmatariService.findByDocumento(codicedocumento,
		idcomunerecord, null, null);
	Allegati allegati = allagatiService.findById(new PkId(idcomunerecord, codicedocumento));
	ModelMap model = new ModelMap(allegatidocsoggfirmatariList);
	boolean export = createJMesaExport(request, response, allegatidocsoggfirmatariList);
	if (export) {
	    return null;
	}
	model.addAttribute("allegatidocsoggfirmatariList", allegatidocsoggfirmatariList);
	model.addAttribute("allegati", allegati);
	return model;
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codiceallegato") Integer codiceallegato, @RequestParam("idcomunerecord") String idcomunerecord) {

	Allegati allegato = allagatiService.findById(new PkId(idcomunerecord, codiceallegato));
	AllegatiDocSoggFirmatari allegatidocsoggfirmatari = new AllegatiDocSoggFirmatari();
	allegatidocsoggfirmatari.setAllegati(allegato);
	fixRenderEntityProperty(allegatidocsoggfirmatari);
	model.addAttribute("allegatidocsoggfirmatari", allegatidocsoggfirmatari);
	setPageAttributes(model);
	return "allegatidocsoggfirmatari/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("allegatidocsoggfirmatari") AllegatiDocSoggFirmatari allegatidocsoggfirmatari, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(allegatidocsoggfirmatari);
	try {
	    allegatidocsoggfirmatariService.insert(allegatidocsoggfirmatari);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, allegatidocsoggfirmatari, e);
	    fixRenderEntityProperty(allegatidocsoggfirmatari);
	    return "allegatidocsoggfirmatari/form";
	}
	status.setComplete();
	//return "redirect:view.htm?codice=" + allegatidocsoggfirmatari.getId().getCodice() + "&status_msg=01";
	return "redirect:list.htm?codiceallegato=" + allegatidocsoggfirmatari.getAllegati().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AllegatiDocSoggFirmatari allegatidocsoggfirmatari = allegatidocsoggfirmatariService.findById(id);
	fixRenderEntityProperty(allegatidocsoggfirmatari);
	model.addAttribute("allegatidocsoggfirmatari", allegatidocsoggfirmatari);
	setPageAttributes(model);
	return "allegatidocsoggfirmatari/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("allegatidocsoggfirmatari") AllegatiDocSoggFirmatari allegatidocsoggfirmatari, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(allegatidocsoggfirmatari);
	try {
	    allegatidocsoggfirmatariService.update(allegatidocsoggfirmatari);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, allegatidocsoggfirmatari, e);
	    fixRenderEntityProperty(allegatidocsoggfirmatari);
	    return "allegatidocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + allegatidocsoggfirmatari.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteByCodice(Model model, @RequestParam("codice") Integer codice) {

	AllegatiDocSoggFirmatari objToDelete = allegatidocsoggfirmatariService.findById(new PkId(codice));
	try {
	    allegatidocsoggfirmatariService.delete(objToDelete);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, objToDelete, e);
	    //	    fixRenderEntityProperty(objToDelete);
	    List<String> error = new ArrayList<String>();
	    error.add(e.getMessage());
	    FlashMessages.setWarnings(error);
	    return "redirect:list.htm?codiceallegato=" + objToDelete.getAllegati().getId().getCodice();
	}
	return "redirect:list.htm?codiceallegato=" + objToDelete.getAllegati().getId().getCodice();
    }

    @RequestMapping
    public String delete(@ModelAttribute("allegatidocsoggfirmatari") AllegatiDocSoggFirmatari allegatidocsoggfirmatari, BindingResult result,
	    SessionStatus status) {

	AllegatiDocSoggFirmatari objToDelete = allegatidocsoggfirmatariService.findById(allegatidocsoggfirmatari.getId());
	try {
	    allegatidocsoggfirmatariService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(allegatidocsoggfirmatari);
	    return "allegatidocsoggfirmatari/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(AllegatiDocSoggFirmatari entity) {

    }

    @Override
    protected void fixRenderEntityProperty(AllegatiDocSoggFirmatari entity) {

	if (entity.getAllegati() == null) {
	    entity.setAllegati(new Allegati());
	}
	if (entity.getTipisoggetto() == null) {
	    entity.setTipisoggetto(new Tipisoggetto());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
