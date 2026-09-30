package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipibandoService;

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
@SessionAttributes("tipibando")
@SecuredComponent(key = "annotations.tipibando.component", parentMenuId = 3)
public class TipibandoController extends BaseController<Tipibando> {

    @Autowired
    private TipibandoService tipibandoService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private SoftwareService softwareService;

    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 1, key = "annotations.security.method.list")
    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipibando> tipibandoList = tipibandoService.findAll(null, null);
	ModelMap model = new ModelMap(tipibandoList);
	boolean export = createJMesaExport(request, response, tipibandoList);
	if (export)
	    return null;
	model.addAttribute("tipibandoList", tipibandoList);
	return model;
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 6, key = "annotations.security.method.delete")
    public String delete(Model model, @ModelAttribute("tipibando") Tipibando tipibando, BindingResult result, SessionStatus status) {

	Tipibando objToDelete = tipibandoService.findById(tipibando.getId());
	try {
	    tipibandoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipibando);
	    List<Dyn2Modellit> modellitList = dyn2ModellitService.findAll(null, null);
	    model.addAttribute("modellitList", modellitList);
	    return "tipibando/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 3, key = "annotations.security.method.insert")
    public String insert(Model model, @ModelAttribute("tipibando") Tipibando tipibando, BindingResult result, SessionStatus status) {

	// recupero il software corrente e lo setto in tipibando
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipibando.setSoftware(software);
	// recupero il dyn2Modellit selezionato e lo setto in tipibando
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(tipibando.getDyn2Modellit().getId());
	tipibando.setDyn2Modellit(dyn2Modellit);
	fixMergeEntityProperty(tipibando);
	try {
	    tipibandoService.insert(tipibando);
	} catch (Exception e) {
	    tipibando.getId().setCodice(null);
	    copyErrorsToBindingResult(result, tipibando, e);
	    fixRenderEntityProperty(tipibando);
	    // estraggo tutti i modelli
	    List<Dyn2Modellit> modellitList = dyn2ModellitService.findAll(null, null);
	    modellitList.size();
	    model.addAttribute("modellitList", modellitList);
	    return "tipibando/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipibando.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 5, key = "annotations.security.method.update")
    public String update(Model model, @ModelAttribute("tipibando") Tipibando tipibando, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il software corrente e lo setto in tipibando
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipibando.setSoftware(software);
	// recupero il dyn2Modellit selezionato e lo setto in tipibando
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(tipibando.getDyn2Modellit().getId());
	tipibando.setDyn2Modellit(dyn2Modellit);
	fixMergeEntityProperty(tipibando);
	try {
	    tipibandoService.update(tipibando);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibando, e);
	    fixRenderEntityProperty(tipibando);
	    // estraggo tutti i modelli
	    List<Dyn2Modellit> modellitList = dyn2ModellitService.findAll(null, null);
	    modellitList.size();
	    model.addAttribute("modellitList", modellitList);
	    return "tipibando/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipibando.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.create")
    public String create(Model model) {

	// estraggo tutti i modelli
	List<Dyn2Modellit> modellitList = dyn2ModellitService.findAll(null, null);
	Tipibando tipibando = new Tipibando();
	fixRenderEntityProperty(tipibando);
	model.addAttribute("tipibando", tipibando);
	model.addAttribute("modellitList", modellitList);
	setPageAttributes(model);
	return "tipibando/form";
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 4, key = "annotations.security.method.view")
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipibando tipibando = tipibandoService.findById(id);
	// estraggo tutti i modelli
	List<Dyn2Modellit> modellitList = dyn2ModellitService.findAll(null, null);
	fixRenderEntityProperty(tipibando);
	model.addAttribute("tipibando", tipibando);
	model.addAttribute("modellitList", modellitList);
	setPageAttributes(model);
	return "tipibando/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipibando entity) {

	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(" "))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipibando entity) {

	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
