package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MercatiDContiService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//DAELIMINARE @Controller
@SessionAttributes("mercatiDConti")
public class MercatiDContiController extends BaseController<MercatiDConti> {

    private static final Logger log = LoggerFactory.getLogger(MercatiDContiController.class);
    @Autowired
    private MercatiDContiService mercatiDContiService;
    @Autowired
    private MercatiDService mercatiDService;

    @RequestMapping
    public ModelMap list(@RequestParam("posteggio.id.codice") Integer codicePosteggio, HttpServletRequest request, HttpServletResponse response) {

	if (codicePosteggio == null) {
	    log.error("Il parametro codice posteggio è obbligatorio");
	    throw new SecurityException("Il parametro codice posteggio è obbligatorio");
	}
	MercatiD posteggio = mercatiDService.findById(new PkId(codicePosteggio));
	List<MercatiDConti> mercatiDContiList = mercatiDContiService.findByPosteggio(posteggio);
	ModelMap model = new ModelMap(mercatiDContiList);
	boolean export = createJMesaExport(request, response, mercatiDContiList);
	if (export)
	    return null;
	model.addAttribute("mercatiDContiList", mercatiDContiList);
	model.addAttribute("posteggio", posteggio);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mercatiDConti") MercatiDConti mercatiDConti, BindingResult result, SessionStatus status) {

	Integer codicePosteggio = mercatiDConti.getPosteggio().getId().getCodice();
	MercatiDConti objToDelete = mercatiDContiService.findById(mercatiDConti.getId());
	try {
	    mercatiDContiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", mercatiDConti.getId().getCodice().toString());
	    model.addAttribute("commandName", "mercatiDConti");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:list.htm?posteggio.id.codice=" + codicePosteggio;
    }

    @RequestMapping
    public String insert(@ModelAttribute("mercatiDConti") MercatiDConti mercatiDConti, BindingResult result, SessionStatus status) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(mercatiDConti);
	try {
	    mercatiDContiService.insert(mercatiDConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiDConti, e);
	    fixRenderEntityProperty(mercatiDConti);
	    return "mercatidconti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiDConti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("mercatiDConti") MercatiDConti mercatiDConti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(mercatiDConti);
	try {
	    mercatiDContiService.update(mercatiDConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiDConti, e);
	    fixRenderEntityProperty(mercatiDConti);
	    return "mercatidconti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiDConti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("posteggio.id.codice") Integer codicePosteggio, Model model) {

	MercatiDConti mercatiDConti = new MercatiDConti();
	PkId idPosteggio = new PkId(codicePosteggio);
	MercatiD posteggio = mercatiDService.findById(idPosteggio);
	mercatiDConti.setPosteggio(posteggio);
	fixRenderEntityProperty(mercatiDConti);
	model.addAttribute("mercatiDConti", mercatiDConti);
	setPageAttributes(model);
	return "mercatidconti/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiDConti mercatiDConti = mercatiDContiService.findById(id);
	fixRenderEntityProperty(mercatiDConti);
	model.addAttribute("mercatiDConti", mercatiDConti);
	setPageAttributes(model);
	return "mercatidconti/form";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiDConti entity) {

	if (entity.getConto() != null && entity.getConto().getId() != null && entity.getConto().getId().getCodice() == null) {
	    entity.setConto(null);
	}
	if (entity.getPosteggio() != null && entity.getPosteggio().getId() != null && entity.getPosteggio().getId().getCodice() == null) {
	    entity.setPosteggio(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatiDConti entity) {

	if (entity.getConto() == null) {
	    entity.setConto(new Conti());
	}
	if (entity.getPosteggio() == null) {
	    entity.setPosteggio(new MercatiD());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
