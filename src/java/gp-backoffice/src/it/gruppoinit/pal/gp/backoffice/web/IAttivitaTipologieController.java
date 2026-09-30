package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.IAttivitaTipologieService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.io.IOException;
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
@SessionAttributes("iAttivitaTipologie")
public class IAttivitaTipologieController extends BaseController<IAttivitaTipologie> {

    @Autowired
    private IAttivitaTipologieService iAttivitaTipologieService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<IAttivitaTipologie> list = iAttivitaTipologieService.findAll(null, null);
	ModelMap model = new ModelMap(list);
	boolean export = createJMesaExport(request, response, list);
	if (export)
	    return null;
	model.addAttribute("list", list);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("iAttivitaTipologie") IAttivitaTipologie iAttivitaTipologie, BindingResult result,
	    SessionStatus status) {

	IAttivitaTipologie objToDelete = iAttivitaTipologieService.findById(iAttivitaTipologie.getId());
	try {
	    iAttivitaTipologieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(iAttivitaTipologie);
	    return "iattivitatipologie/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("iAttivitaTipologie") IAttivitaTipologie iAttivitaTipologie, BindingResult result,
	    SessionStatus status) {

	// recupero il software corrente e lo setto in tipibando
	Software software = softwareService.findById(ORMHelper.getSoftware());
	iAttivitaTipologie.setSoftware(software);
	// recupero il dyn2Modellit selezionato e lo setto in tipibando
	fixMergeEntityProperty(iAttivitaTipologie);
	try {
	    iAttivitaTipologieService.insert(iAttivitaTipologie);
	} catch (Exception e) {
	    iAttivitaTipologie.getId().setCodice(null);
	    copyErrorsToBindingResult(result, iAttivitaTipologie, e);
	    fixRenderEntityProperty(iAttivitaTipologie);
	    return "iattivitatipologie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + iAttivitaTipologie.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("iAttivitaTipologie") IAttivitaTipologie iAttivitaTipologie, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il software corrente e lo setto in tipibando
	Software software = softwareService.findById(ORMHelper.getSoftware());
	iAttivitaTipologie.setSoftware(software);
	// recupero il dyn2Modellit selezionato e lo setto in tipibando
	fixMergeEntityProperty(iAttivitaTipologie);
	try {
	    iAttivitaTipologieService.update(iAttivitaTipologie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, iAttivitaTipologie, e);
	    fixRenderEntityProperty(iAttivitaTipologie);
	    return "iattivitatipologie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + iAttivitaTipologie.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	IAttivitaTipologie iAttivitaTipologie = new IAttivitaTipologie();
	fixRenderEntityProperty(iAttivitaTipologie);
	model.addAttribute("iAttivitaTipologie", iAttivitaTipologie);
	setPageAttributes(model);
	return "iattivitatipologie/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IAttivitaTipologie tipibando = iAttivitaTipologieService.findById(id);
	model.addAttribute("iAttivitaTipologie", tipibando);
	setPageAttributes(model);
	return "iattivitatipologie/form";
    }

    @RequestMapping
    public void ajaxFindIAttivitaTipologie(@RequestParam("textToSearch") String textToSearch, @RequestParam("paramSoftware") String paramSoftware,
	    HttpServletResponse response) throws IOException {

	List<IAttivitaTipologie> list = iAttivitaTipologieService.findByDescrizioneAndSoftware(textToSearch, paramSoftware);
	renderHTMLResponse(response, list, "id.codice", "descrizione");
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(IAttivitaTipologie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IAttivitaTipologie entity) {

	if (entity != null) {
	    if (entity.getSoftware() == null) {
		Software software = softwareService.findById(ORMHelper.getSoftware());
		entity.setSoftware(software);
	    }
	}
    }
}
