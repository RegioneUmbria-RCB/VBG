package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.service.TipibandoService;
import it.gruppoinit.pal.gp.core.service.TipibandoinputService;
import it.gruppoinit.pal.gp.core.service.TipibandooutputService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;

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

@Controller
@SessionAttributes("tipibandooutput")
@SecuredComponent(key = "annotations.tipibandooutput.component", parentClass = TipigraduatorietController.class, rootElement = false)
public class TipibandooutputController extends BaseController<Tipibandooutput> {

    private static final Logger log = LoggerFactory.getLogger(TipibandooutputController.class);
    @Autowired
    private TipibandooutputService tipibandooutputService;
    @Autowired
    private TipibandoService tipibandoService;
    @Autowired
    private TipibandoinputService tipibandoinputService;
    @Autowired
    private TipigraduatorietService tipigraduatorietService;

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.list", order = 1)
    public ModelMap list(@RequestParam("tipigraduatoriet.id.codice") Integer codiceGraduatoriet, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	// genero un filtro per visualizzare tipibandocampigraduat
	// solo del tipibandi a cui è collegato a partire dal codice
	// tipibando sulla request
	PkId tipigraduatorietPk = new PkId(codiceGraduatoriet);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(tipigraduatorietPk);
	Tipibandooutput filter = new Tipibandooutput();
	filter.setTipigraduatoriet(tipigraduatoriet);
	List<Tipibandooutput> tipibandooutputList = tipibandooutputService.findByTipibando(filter);
	ModelMap model = new ModelMap(tipibandooutputList);
	boolean export = createJMesaExport(request, response, tipibandooutputList);
	if (export)
	    return null;
	model.addAttribute("tipigraduatoriet", tipigraduatoriet);
	model.addAttribute("tipibandooutputList", tipibandooutputList);
	PkId tipibandoPk = new PkId();
	tipibandoPk.setCodice(tipigraduatoriet.getTipibando().getId().getCodice());
	Tipibando tipibando = tipibandoService.findById(tipibandoPk);
	model.addAttribute("tipibando", tipibando);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.delete", methodType = ReadWriteMethod.WRITE, order = 6)
    public String delete(Model model, @ModelAttribute("tipibandooutput") Tipibandooutput tipibandooutput, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Integer codice = tipibandooutput.getId().getCodice();
	Tipibandooutput objToDelete = tipibandooutputService.findById(tipibandooutput.getId());
	try {
	    tipibandooutputService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codice.toString());
	    model.addAttribute("commandName", "tipibandooutput");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm?tipigraduatoriet.id.codice=" + tipibandooutput.getTipigraduatoriet().getId().getCodice();
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.insert", methodType = ReadWriteMethod.WRITE, order = 3)
    public String insert(Model model, @ModelAttribute("tipibandooutput") Tipibandooutput tipibandooutput, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	// ricavo dal codice il tipibandoinput selezionato nella serch
	// lo setto allinterno di tipibandooutput
	Tipibandoinput tipibandoinput = tipibandoinputService.findById(tipibandooutput.getTipibandoinput().getId());
	tipibandooutput.setTipibandoinput(tipibandoinput);
	fixMergeEntityProperty(tipibandooutput);
	try {
	    tipibandooutputService.insert(tipibandooutput);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibandooutput, e);
	    // ritorno la lista per la select
	    List<Tipibandoinput> listTipibandoinput = tipibandoinputService.findByFilterTipobando(tipibandoinput.getTipibando());
	    listTipibandoinput.size();
	    model.addAttribute("listTipibandoinput", listTipibandoinput);
	    fixRenderEntityProperty(tipibandooutput);
	    return "tipibandooutput/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipibandooutput.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.update", methodType = ReadWriteMethod.WRITE, order = 5)
    public String update(Model model, @ModelAttribute("tipibandooutput") Tipibandooutput tipibandooutput, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	Tipibandoinput tipibandoinput = tipibandoinputService.findById(tipibandooutput.getTipibandoinput().getId());
	tipibandooutput.setTipibandoinput(tipibandoinput);
	fixMergeEntityProperty(tipibandooutput);
	try {
	    tipibandooutputService.update(tipibandooutput);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipibandooutput, e);
	    // ritorno la lista per la select
	    List<Tipibandoinput> listTipibandoinput = tipibandoinputService.findByFilterTipobando(tipibandoinput.getTipibando());
	    listTipibandoinput.size();
	    model.addAttribute("listTipibandoinput", listTipibandoinput);
	    fixRenderEntityProperty(tipibandooutput);
	    return "tipibandooutput/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipibandooutput.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.create", methodType = ReadWriteMethod.WRITE, order = 2)
    public String create(@RequestParam("tipigraduatoriet.id.codice") Integer codiceGraduatoriet, Model model) {

	// §§§BEGIN§§§
	// creo un nuovo tipibandocampigraduat a partire dal codice tipibando
	// ricavato dalla request
	PkId tipigraduatorietPk = new PkId(codiceGraduatoriet);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(tipigraduatorietPk);
	PkId id = new PkId(tipigraduatoriet.getTipibando().getId().getCodice());
	Tipibando tipibando = tipibandoService.findById(id);
	List<Tipibandoinput> listTipibandoinput = tipibandoinputService.findByFilterTipobando(tipibando);
	Tipibandooutput tipibandooutput = new Tipibandooutput();
	tipibandooutput.setTipigraduatoriet(tipigraduatoriet);
	fixRenderEntityProperty(tipibandooutput);
	model.addAttribute("tipibandooutput", tipibandooutput);
	model.addAttribute("listTipibandoinput", listTipibandoinput);
	setPageAttributes(model);
	// §§§END§§§
	return "tipibandooutput/form";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.view", methodType = ReadWriteMethod.READ, order = 4)
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Tipibandooutput tipibandooutput = tipibandooutputService.findById(id);
	// ricavo tipo bando e estraggo la lista dei tipibandoinputi da
	// presentare
	// sulla select del form
	Tipibando tipibando = tipibandoService.findById(tipibandooutput.getTipigraduatoriet().getTipibando().getId());
	List<Tipibandoinput> listTipibandoinput = tipibandoinputService.findByFilterTipobando(tipibando);
	fixRenderEntityProperty(tipibandooutput);
	model.addAttribute("tipibandooutput", tipibandooutput);
	model.addAttribute("listTipibandoinput", listTipibandoinput);
	setPageAttributes(model);
	// §§§END§§§
	return "tipibandooutput/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipibandooutput entity) {

	if (entity.getTipigraduatoriet() != null && entity.getTipigraduatoriet().getId() != null
		&& entity.getTipigraduatoriet().getId().getCodice() == null) {
	    entity.setTipigraduatoriet(null);
	}
	if (entity.getDyn2CampiRif() != null && entity.getDyn2CampiRif().getId() != null && entity.getDyn2CampiRif().getId().getCodice() == null) {
	    entity.setDyn2CampiRif(null);
	}
	if (entity.getDyn2CampiOut() != null && entity.getDyn2CampiOut().getId() != null && entity.getDyn2CampiOut().getId().getCodice() == null) {
	    entity.setDyn2CampiOut(null);
	}
	if (entity.getTipocalcolo().equals(WebConstants.BANDI_TIPOCALCOLO_ELEMENTO)) {
	    entity.setDyn2CampiRif(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipibandooutput entity) {

	if (entity.getTipigraduatoriet() == null) {
	    entity.setTipigraduatoriet(new Tipigraduatoriet());
	}
	if (entity.getDyn2CampiRif() == null) {
	    entity.setDyn2CampiRif(new Dyn2Campi());
	}
	if (entity.getDyn2CampiOut() == null) {
	    entity.setDyn2CampiOut(new Dyn2Campi());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
