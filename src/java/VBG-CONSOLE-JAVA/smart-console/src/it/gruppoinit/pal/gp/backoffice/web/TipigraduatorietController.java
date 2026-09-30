package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoried;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.TipibandoService;
import it.gruppoinit.pal.gp.core.service.TipigraduatoriedService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@SessionAttributes("tipigraduatoriet")
@SecuredComponent(key = "annotations.tipigraduatoriet.component", parentClass = TipibandoController.class, rootElement = false)
public class TipigraduatorietController extends BaseController<Tipigraduatoriet> {

    private static final Logger log = LoggerFactory.getLogger(TipigraduatorietController.class);
    @Autowired
    private TipigraduatorietService tipigraduatorietService;
    @Autowired
    private TipibandoService tipibandoService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private TipigraduatoriedService tipigraduatoriedService;

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.list", order = 1)
    public ModelMap list(@RequestParam("tipibando.id.codice") Integer codiceBando, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId tipibandoPk = new PkId();
	tipibandoPk.setCodice(codiceBando);
	Tipibando tipibando = tipibandoService.findById(tipibandoPk);
	Tipigraduatoriet filter = new Tipigraduatoriet();
	filter.setTipibando(tipibando);
	// filtro tipigraduatoriet per il tipo bando
	List<Tipigraduatoriet> tipigraduatorietList = tipigraduatorietService.findByTipibando(filter);
	ModelMap model = new ModelMap(tipigraduatorietList);
	boolean export = createJMesaExport(request, response, tipigraduatorietList);
	if (export)
	    return null;
	model.addAttribute("tipigraduatorietList", tipigraduatorietList);
	model.addAttribute("tipibando", tipibando);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.delete", methodType = ReadWriteMethod.WRITE, order = 6)
    public String delete(Model model, @ModelAttribute("tipigraduatoriet") Tipigraduatoriet tipigraduatoriet, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	Integer codiceTipiGraduatoriet = tipigraduatoriet.getId().getCodice();
	Tipigraduatoriet objToDelete = tipigraduatorietService.findById(tipigraduatoriet.getId());
	try {
	    tipigraduatorietService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codiceTipiGraduatoriet.toString());
	    model.addAttribute("commandName", "tipigraduatoriet");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm?tipibando.id.codice=" + tipigraduatoriet.getTipibando().getId().getCodice();
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.insert", methodType = ReadWriteMethod.WRITE, order = 3)
    public String insert(@ModelAttribute("tipigraduatoriet") Tipigraduatoriet tipigraduatoriet, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	try {
	    tipigraduatorietService.insert(tipigraduatoriet);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigraduatoriet, e);
	    fixRenderEntityProperty(tipigraduatoriet);
	    return "tipigraduatoriet/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipigraduatoriet.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.update", methodType = ReadWriteMethod.WRITE, order = 5)
    public String update(Model model, @ModelAttribute("tipigraduatoriet") Tipigraduatoriet tipigraduatoriet, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(tipigraduatoriet);
	try {
	    tipigraduatorietService.update(tipigraduatoriet);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigraduatoriet, e);
	    Integer idModello = tipigraduatoriet.getTipibando().getDyn2Modellit().getId().getCodice();
	    List<Dyn2Campi> dyn2CampiList = dyn2CampiService.findByFilterAndIdModello(new Dyn2Campi(), idModello);
	    model.addAttribute("dyn2CampiList", dyn2CampiList);
	    fixRenderEntityProperty(tipigraduatoriet);
	    return "tipigraduatoriet/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipigraduatoriet.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.create", methodType = ReadWriteMethod.WRITE, order = 2)
    public String create(@RequestParam("tipibando.id.codice") Integer codiceBando, Model model) {

	// §§§BEGIN§§§
	PkId id = new PkId(codiceBando);
	Tipibando tipibando = tipibandoService.findById(id);
	Integer idModello = tipibando.getDyn2Modellit().getId().getCodice();
	List<Dyn2Campi> dyn2CampiList = dyn2CampiService.findByFilterAndIdModello(new Dyn2Campi(), idModello);
	Tipigraduatoriet tipigraduatoriet = new Tipigraduatoriet();
	tipigraduatoriet.setTipibando(tipibando);
	fixRenderEntityProperty(tipigraduatoriet);
	model.addAttribute("tipigraduatoriet", tipigraduatoriet);
	model.addAttribute("dyn2CampiList", dyn2CampiList);
	setPageAttributes(model);
	// §§§END§§§
	return "tipigraduatoriet/form";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.view", order = 4)
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(id);
	fixRenderEntityProperty(tipigraduatoriet);
	model.addAttribute("tipigraduatoriet", tipigraduatoriet);
	Integer idModello = tipigraduatoriet.getTipibando().getDyn2Modellit().getId().getCodice();
	Dyn2Campi filter = new Dyn2Campi();
	List<Dyn2Campi> dyn2CampiList = dyn2CampiService.findByFilterAndIdModello(filter, idModello);
	model.addAttribute("dyn2CampiList", dyn2CampiList);
	setPageAttributes(model);
	// §§§END§§§
	return "tipigraduatoriet/form";
    }

    @RequestMapping
    @SecuredMethod(key = "annotations.security.method.deletegraduatoriad", methodType = ReadWriteMethod.WRITE, order = 7)
    public String deleteGraduatoriad(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("tipigraduatoriet") Tipigraduatoriet tipigraduatoriet, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	Integer codiceTipiGraduatoriet = tipigraduatoriet.getId().getCodice();
	PkId codicegraduatoriad = new PkId(codice);
	Tipigraduatoried tipigraduatoried = tipigraduatoriedService.findById(codicegraduatoriad);
	try {
	    tipigraduatoriedService.delete(tipigraduatoried);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipigraduatoried, e);
	    log.error(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codiceTipiGraduatoriet.toString());
	    model.addAttribute("commandName", "tipigraduatoriet");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + tipigraduatoriet.getId().getCodice();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void fixMergeEntityProperty(Tipigraduatoriet entity) {

	if (entity.getTipibando().getId() != null && entity.getTipibando().getId() != null && entity.getTipibando().getId().getCodice() == null) {
	    entity.setTipibando(null);
	}
	// Quando la lista di graduatorid � non nulla
	// La riordina in modo da togliere il record vuoto "Seleziona"
	// per non creare problemi con la validazione
	// (Nota: Siccome dentro l'if ho creato un ogetto tipograguatoriad nuovo
	// sono obbligato a settare dinuovo esplicitamente sia la graduatoriat che
	// dyn2campi)
	if (entity.getTipigraduatorieds() != null) {
	    Set<Tipigraduatoried> out = new HashSet<Tipigraduatoried>();
	    Set<Tipigraduatoried> list = entity.getTipigraduatorieds();
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		Tipigraduatoried tipigraduatoried = (Tipigraduatoried) iterator.next();
		if (tipigraduatoried.getDyn2Campi() != null && tipigraduatoried.getDyn2Campi().getId().getCodice() != null) {
		    out.add(tipigraduatoried);
		    tipigraduatoried.setTipigraduatoriet(entity);
		    Dyn2Campi dyn2Campi = dyn2CampiService.findById((tipigraduatoried.getDyn2Campi().getId()));
		    tipigraduatoried.setDyn2Campi(dyn2Campi);
		}
	    }
	    entity.setTipigraduatorieds(out);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipigraduatoriet entity) {

	if (entity.getTipibando() == null) {
	    entity.setTipibando(new Tipibando());
	}
	if (!(entity.getTipigraduatorieds() == null || entity.getTipigraduatorieds().isEmpty())) {
	    Set<Tipigraduatoried> out = new LinkedHashSet<Tipigraduatoried>();
	    Set<Tipigraduatoried> list = entity.getTipigraduatorieds();
	    for (Tipigraduatoried tipigraduatoried : list) {
		out.add(tipigraduatoried);
	    }
	    Tipigraduatoried tgd = new Tipigraduatoried();
	    tgd.setTipigraduatoriet(entity);
	    out.add(tgd);
	    entity.setTipigraduatorieds(out);
	} else {
	    if (entity.getId().getCodice() != null) {
		Set<Tipigraduatoried> list = new HashSet<Tipigraduatoried>(0);
		Tipigraduatoried tgd = new Tipigraduatoried();
		tgd.setTipigraduatoriet(entity);
		list.add(tgd);
		entity.setTipigraduatorieds(list);
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
