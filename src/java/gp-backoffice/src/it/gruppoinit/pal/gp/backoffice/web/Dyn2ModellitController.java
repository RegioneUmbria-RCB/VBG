package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScriptId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.Dyn2BasecontestiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModelliScriptService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService.EventoModelli;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
@SessionAttributes("dyn2modellit")
public class Dyn2ModellitController extends BaseController<Dyn2Modellit> {

    @Autowired
    private Dyn2ModellitService dyn2modellitService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private Dyn2BasecontestiService basecontestiService;
    @Autowired
    private Dyn2ModelliScriptService dyn2ModelliScriptService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Dyn2Modellit> dyn2modellitList = dyn2modellitService.findAll(null, null);
	ModelMap model = new ModelMap(dyn2modellitList);
	boolean export = createJMesaExport(request, response, dyn2modellitList);
	if (export) {
	    return null;
	}
	model.addAttribute("dyn2modellitList", dyn2modellitList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Dyn2Modellit dyn2modellit = new Dyn2Modellit();
	dyn2modellit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	fixRenderEntityProperty(dyn2modellit);
	model.addAttribute("dyn2modellit", dyn2modellit);
	model.addAttribute("basecontestis", basecontestis);
	setPageAttributes(model);
	return "dyn2modellit/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("dyn2modellit") Dyn2Modellit dyn2modellit, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(dyn2modellit);
	dyn2modellit.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    dyn2modellitService.insert(dyn2modellit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2modellit, e);
	    List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	    model.addAttribute("basecontestis", basecontestis);
	    fixRenderEntityProperty(dyn2modellit);
	    return "dyn2modellit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dyn2modellit.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Dyn2Modellit dyn2modellit = dyn2modellitService.findById(id);
	List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	fixRenderEntityProperty(dyn2modellit);
	model.addAttribute("dyn2modellit", dyn2modellit);
	model.addAttribute("basecontestis", basecontestis);
	setPageAttributes(model);
	return "dyn2modellit/form";
    }

    @RequestMapping
    public String viewFormule(@RequestParam("codice") Integer codice, @RequestParam(value = "evento", required = false) String evento, Model model,
	    HttpServletRequest request) {

	checkAccesso();
	PkId id = new PkId(codice);
	Dyn2Modellit dyn2modellit = dyn2modellitService.findById(id);
	fixRenderEntityProperty(dyn2modellit);
	model.addAttribute("dyn2modellit", dyn2modellit);
	if (StringUtils.isBlank(evento)) {
	    evento = EventoModelli.Caricamento.name();
	}
	Dyn2ModelliScript script = dyn2ModelliScriptService.findByModelloAndEvento(codice, evento);
	String scriptString = "";
	if (script != null) {
	    if (script.getScript() != null) {
		scriptString = new String(script.getScript());
	    }
	}
	model.addAttribute("scriptString", scriptString);
	model.addAttribute("evento", evento);
	model.addAttribute("codice", codice);
	setPageAttributes(model);
	return "dyn2modellit/formFormule";
    }

    @RequestMapping
    public String saveFormula(@RequestParam("codice") Integer codice, @RequestParam(value = "evento") String evento,
	    @RequestParam(value = "scriptString") String scriptString, Model model, HttpServletRequest request) {

	checkAccesso();
	Dyn2ModelliScriptId id = new Dyn2ModelliScriptId(codice, evento);
	Dyn2ModelliScript script = dyn2ModelliScriptService.findById(id);
	byte[] content = null;
	if (script == null) {
	    script = new Dyn2ModelliScript();
	    script.setId(id);
	    try {
		content = scriptString.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
		content = scriptString.getBytes();
	    }
	    script.setScript(content);
	    dyn2ModelliScriptService.insert(script);
	} else {
	    try {
		content = scriptString.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
		content = scriptString.getBytes();
	    }
	    script.setScript(content);
	    dyn2ModelliScriptService.update(script);
	}
	return "redirect:viewFormule.htm?codice=" + codice + "&evento=" + evento;
    }

    private void checkAccesso() {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(resp.getAmministratore(), "0").equalsIgnoreCase("0")) {
	    throw new SecurityException("Solo Amministratori possono accedere alla configurazione delle formule dei campi dinamici");
	}
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("dyn2modellit") Dyn2Modellit dyn2modellit, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(dyn2modellit);
	try {
	    dyn2modellitService.update(dyn2modellit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2modellit, e);
	    List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	    model.addAttribute("basecontestis", basecontestis);
	    fixRenderEntityProperty(dyn2modellit);
	    return "dyn2modellit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dyn2modellit.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("dyn2modellit") Dyn2Modellit dyn2modellit, BindingResult result, SessionStatus status) {

	Dyn2Modellit objToDelete = dyn2modellitService.findById(dyn2modellit.getId());
	try {
	    dyn2modellitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2modellit, e);
	    List<Dyn2Basecontesti> basecontestis = basecontestiService.findAll(null, null);
	    model.addAttribute("basecontestis", basecontestis);
	    fixRenderEntityProperty(dyn2modellit);
	    return "dyn2modellit/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Dyn2Modellit entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Dyn2Modellit entity) {

	if (entity.getBasecontesti() == null) {
	    entity.setBasecontesti(new Dyn2Basecontesti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
