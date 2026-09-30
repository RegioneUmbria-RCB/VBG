/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;
import it.gruppoinit.pal.gp.core.service.TipibandoService;
import it.gruppoinit.pal.gp.core.service.TipibandoinputService;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author lucap
 * 
 */
@Controller
@SessionAttributes("tipibandoinput")
@SecuredComponent(key = "annotations.tipibandoinput.component", parentClass = TipibandoController.class, rootElement = false)
public class TipibandoinputController extends BaseController<Tipibandoinput> {

    private static final Logger log = LoggerFactory.getLogger(TipibandoinputController.class);
    @Autowired
    private TipibandoinputService tipibandoinputService;
    @Autowired
    private TipibandoService tipibandoService;

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.list", order = 1)
    public Model list(Model modello, @RequestParam("tipibando.id.codice") Integer codiceBando, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId tipibandoPk = new PkId();
	tipibandoPk.setCodice(codiceBando);
	Tipibando tipibando = tipibandoService.findById(tipibandoPk);
	Tipibandoinput filter = new Tipibandoinput();
	filter.setTipibando(tipibando);
	// List<Tipibandoinput> tipibandoinputList =
	// tipibandoinputService.findByFilterTipibando(filter);
	List<Tipibandoinput> tipibandoinputList = tipibandoinputService.findByFilterTipobando(tipibando);
	// ModelMap model = new ModelMap(tipibandoinputList);
	boolean export = createJMesaExport(request, response, tipibandoinputList);
	if (export)
	    return null;
	modello.addAttribute("tipibandoinputList", tipibandoinputList);
	modello.addAttribute("tipibando", tipibando);
	Tipibandoinput tipibandoinput = createInputVuoto(tipibando);
	// model.addAttribute("tipibandoinput", tipibandoinput);
	modello.addAttribute("tipibandoinput", tipibandoinput);
	return modello;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Tipibandoinput createInputVuoto(Tipibando tipibando) {

	// per la creazione di un nuovo record nella pagina di lista	
	Tipibandoinput tipibandoinput = new Tipibandoinput();
	tipibandoinput.setTipibando(tipibando);
	// esistono solamente di tipo valore
	tipibandoinput.setTipoinput(WebConstants.BANDI_TIPOINPUT_VALORE);
	fixRenderEntityProperty(tipibandoinput);
	return tipibandoinput;
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.delete", methodType = ReadWriteMethod.WRITE, order = 6)
    public String delete(Model model, @ModelAttribute("tipibandoinput") Tipibandoinput tipibandoinput, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Integer codice = tipibandoinput.getId().getCodice();
	Tipibandoinput objToDelete = tipibandoinputService.findById(tipibandoinput.getId());
	try {
	    tipibandoinputService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codice.toString());
	    model.addAttribute("commandName", "tipibandoinput");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm?tipibando.id.codice=" + tipibandoinput.getTipibando().getId().getCodice();
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.insert", methodType = ReadWriteMethod.WRITE, order = 3)
    public String insert(@ModelAttribute("tipibandoinput") Tipibandoinput tipibandoinput, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(tipibandoinput);
	try {
	    tipibandoinputService.insert(tipibandoinput);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibandoinput, e);
	    fixRenderEntityProperty(tipibandoinput);
	    return "tipibandoinput/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipibandoinput.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.insert", methodType = ReadWriteMethod.WRITE, order = 3)
    public String insertDaLista(Model model, @RequestParam("tipibando.id.codice") Integer codiceBando, @RequestParam("etichetta") String etichetta) {

	PkId tipibandoPk = new PkId();
	tipibandoPk.setCodice(codiceBando);
	Tipibando tipibando = tipibandoService.findById(tipibandoPk);
	Tipibandoinput tipibandoinput = createInputVuoto(tipibando);
	// §§§BEGIN§§§
	tipibandoinput.setEtichetta(etichetta);
	fixMergeEntityProperty(tipibandoinput);
	try {
	    tipibandoinputService.insert(tipibandoinput);
	} catch (Exception e) {
	    // TODO
	    fixRenderEntityProperty(tipibandoinput);
	    return "tipibandoinput/form";
	}
	// §§§END§§§
	// return "redirect:view.htm?codice=" + tipibandoinput.getId().getCodice() + "&status_msg=01";
	return "redirect:list.htm?tipibando.id.codice=" + tipibandoinput.getTipibando().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.update", methodType = ReadWriteMethod.WRITE, order = 5)
    public String update(@ModelAttribute("tipibandoinput") Tipibandoinput tipibandoinput, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(tipibandoinput);
	try {
	    tipibandoinputService.update(tipibandoinput);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibandoinput, e);
	    fixRenderEntityProperty(tipibandoinput);
	    return "tipibandoinput/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipibandoinput.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.create", methodType = ReadWriteMethod.WRITE, order = 2)
    public String create(@RequestParam("tipibando.id.codice") Integer codiceBando, Model model) {

	// §§§BEGIN§§§
	PkId id = new PkId(codiceBando);
	Tipibando tipibando = tipibandoService.findById(id);
	Tipibandoinput tipibandoinput = new Tipibandoinput();
	tipibandoinput.setTipibando(tipibando);
	fixRenderEntityProperty(tipibandoinput);
	model.addAttribute("tipibandoinput", tipibandoinput);
	setPageAttributes(model);
	// §§§END§§§
	return "tipibandoinput/form";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.view", methodType = ReadWriteMethod.READ, order = 4)
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Tipibandoinput tipibandoinput = tipibandoinputService.findById(id);
	fixRenderEntityProperty(tipibandoinput);
	model.addAttribute("tipibandoinput", tipibandoinput);
	setPageAttributes(model);
	// §§§END§§§
	return "tipibandoinput/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipibandoinput entity) {

	if (entity.getTipibando() != null && entity.getTipibando().getId() != null && entity.getTipibando().getId().getCodice() == null) {
	    entity.setTipibando(null);
	}
	if (entity.getTipoinput().equals(WebConstants.BANDI_TIPOINPUT_VALORE) || entity.getTipoinput().equals(WebConstants.BANDI_TIPOINPUT_MERCATI)) {
	    entity.setQuery(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipibandoinput entity) {

	if (entity.getTipibando() == null) {
	    entity.setTipibando(new Tipibando());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
