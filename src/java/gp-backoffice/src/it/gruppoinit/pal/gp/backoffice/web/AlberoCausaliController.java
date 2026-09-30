package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.service.AlberoCausaliService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;

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
@SessionAttributes("alberoCausali")
public class AlberoCausaliController extends BaseController<AlberoCausali> {

    @Autowired
    private AlberoCausaliService alberoCausaliService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;

    @RequestMapping
    public ModelMap list(@RequestParam("alberoproc.id.codice") Integer codiceOneri, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceOneri));
	AlberoCausali alberoCausali = new AlberoCausali();
	alberoCausali.setAlberoproc(alberoproc);
	List<AlberoCausali> alberoCausaliList = alberoCausaliService.findByAlberoProc(alberoCausali);
	ModelMap model = new ModelMap(alberoCausaliList);
	boolean export = createJMesaExport(request, response, alberoCausaliList);
	if (export)
	    return null;
	model.addAttribute("alberoCausaliList", alberoCausaliList);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public String delete(@RequestParam("alberoproc.id.codice") Integer codiceAlberoproc,
	    @ModelAttribute("alberoCausali") AlberoCausali alberoCausali, BindingResult result, SessionStatus status) {

	AlberoCausali objToDelete = alberoCausaliService.findById(alberoCausali.getId());
	try {
	    alberoCausaliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoCausali);
	    return "alberocausali/form";
	}
	status.setComplete();
	return "redirect:list.htm?alberoproc.id.codice=" + codiceAlberoproc;
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoCausali") AlberoCausali alberoCausali, BindingResult result, SessionStatus status) {

	RegistrazioniCausali registrazioniCausali = registrazioniCausaliService.findById(alberoCausali.getRegistrazioniCausali().getId());
	alberoCausali.setRegistrazioniCausali(registrazioniCausali);
	fixMergeEntityProperty(alberoCausali);
	try {
	    alberoCausaliService.insert(alberoCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoCausali, e);
	    fixRenderEntityProperty(alberoCausali);
	    return "alberocausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoCausali.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoCausali") AlberoCausali alberoCausali, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri
	// attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(alberoCausali);
	try {
	    alberoCausaliService.update(alberoCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoCausali, e);
	    fixRenderEntityProperty(alberoCausali);
	    return "alberocausali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoCausali.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("alberoproc.id.codice") Integer codiceAlberoproc, Model model) {

	AlberoCausali alberoCausali = new AlberoCausali();
	PkId idAlberoproc = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(idAlberoproc);
	alberoCausali.setAlberoproc(alberoproc);
	fixRenderEntityProperty(alberoCausali);
	model.addAttribute("alberoCausali", alberoCausali);
	setPageAttributes(model);
	return "alberocausali/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoCausali alberoCausali = alberoCausaliService.findById(id);
	fixRenderEntityProperty(alberoCausali);
	model.addAttribute("alberoCausali", alberoCausali);
	setPageAttributes(model);
	return "alberocausali/form";
    }

    @Override
    protected void fixMergeEntityProperty(AlberoCausali entity) {

	if (entity.getRegistrazioniCausali() != null && entity.getRegistrazioniCausali().getId() != null
		&& entity.getRegistrazioniCausali().getId().getCodice() == null) {
	    entity.setRegistrazioniCausali(null);
	}
	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(AlberoCausali entity) {

	if (entity.getRegistrazioniCausali() == null) {
	    entity.setRegistrazioniCausali(new RegistrazioniCausali());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
