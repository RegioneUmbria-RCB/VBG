package it.gruppoinit.pal.gp.backoffice.web;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;

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
@SessionAttributes("tipiarchivioistanze")
public class TipiarchivioistanzeController extends BaseController<Tipiarchivioistanze> {

    @Autowired
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiarchivioistanze> tipiarchivioistanzeList = tipiarchivioistanzeService.findAll(null, null);
	ModelMap model = new ModelMap(tipiarchivioistanzeList);
	boolean export = createJMesaExport(request, response, tipiarchivioistanzeList);
	if (export)
	    return null;
	model.addAttribute("tipiarchivioistanzeList", tipiarchivioistanzeList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipiarchivioistanze") Tipiarchivioistanze tipiarchivioistanze, BindingResult result,
	    SessionStatus status) {

	Tipiarchivioistanze objToDelete = tipiarchivioistanzeService.findById(tipiarchivioistanze.getId());
	try {
	    tipiarchivioistanzeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "tipiarchivioistanze/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiarchivioistanze") Tipiarchivioistanze tipiarchivioistanze, BindingResult result, SessionStatus status) {

	// recupero il software corrente
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipiarchivioistanze.setSoftware(software);
	fixMergeEntityProperty(tipiarchivioistanze);
	try {
	    tipiarchivioistanzeService.insert(tipiarchivioistanze);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiarchivioistanze, e);
	    fixRenderEntityProperty(tipiarchivioistanze);
	    return "tipiarchivioistanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiarchivioistanze.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiarchivioistanze") Tipiarchivioistanze tipiarchivioistanze, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipiarchivioistanze);
	try {
	    tipiarchivioistanzeService.update(tipiarchivioistanze);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiarchivioistanze, e);
	    fixRenderEntityProperty(tipiarchivioistanze);
	    return "tipiarchivioistanze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiarchivioistanze.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipiarchivioistanze Tipiarchivioistanze = new Tipiarchivioistanze();
	fixRenderEntityProperty(Tipiarchivioistanze);
	model.addAttribute("tipiarchivioistanze", Tipiarchivioistanze);
	setPageAttributes(model);
	return "tipiarchivioistanze/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiarchivioistanze tipiarchivioistanze = tipiarchivioistanzeService.findById(id);
	fixRenderEntityProperty(tipiarchivioistanze);
	model.addAttribute("tipiarchivioistanze", tipiarchivioistanze);
	setPageAttributes(model);
	return "tipiarchivioistanze/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiarchivioistanze entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(" "))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipiarchivioistanze entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
