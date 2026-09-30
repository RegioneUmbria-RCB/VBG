package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.domain.web.SettoriCommand;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SettoriavvisiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
 * @author gianpaolot
 * 
 */
@Controller
@SessionAttributes(value = { "settori", "settoriavvisi" })
public class SettoriController extends BaseController<Settori> {

    @Autowired
    private SettoriService sectoriService;
    @Autowired
    private TipiunitamisuraService tipiunitamisuraService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private SettoriavvisiService sectoriavvisiService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Settori> settoriList = sectoriService.findAll(null, null);
	ModelMap model = new ModelMap(settoriList);
	boolean export = createJMesaExport(request, response, settoriList);
	if (export)
	    return null;
	model.addAttribute("settoriList", settoriList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("settori") SettoriCommand settori, BindingResult result, SessionStatus status) {

	Settori objToDelete = sectoriService.findById(settori.getEntity().getId());
	try {
	    sectoriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, settori.getEntity(), true, e);
	    settori.setEntity(objToDelete);
	    fixRenderEntityProperty(settori.getEntity());
	    settori.setDisplayMode(SettoriCommand.VIEW);
	    model.addAttribute("settori", settori);
	    return "settori/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("settori") SettoriCommand settori, BindingResult result, SessionStatus status) {

	Settori entity = settori.getEntity();
	// recupero il software
	Software software = softwareService.findById(ORMHelper.getSoftware());
	entity.setSoftware(software);
	if (settori.getEntity().getTipiunitamisura().getId() != null && settori.getEntity().getTipiunitamisura().getId().getCodice() != null) {
	    Tipiunitamisura tipiunitamisura = tipiunitamisuraService.findById(new PkId(settori.getEntity().getTipiunitamisura().getId().getCodice()));
	    entity.setTipiunitamisura(tipiunitamisura);
	}
	fixMergeEntityProperty(settori.getEntity());
	try {
	    sectoriService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, settori.getEntity(), true, e);
	    fixRenderEntityProperty(settori.getEntity());
	    settori.setEntity(entity);
	    settori.setDisplayMode(SettoriCommand.NEW);
	    model.addAttribute("settori", settori);
	    return "settori/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodicesettore() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("settori") SettoriCommand settori, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Settori entity = settori.getEntity();
	settori.setEntity(entity);
	fixMergeEntityProperty(settori.getEntity());
	try {
	    sectoriService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, settori.getEntity(), true, e);
	    fixRenderEntityProperty(settori.getEntity());
	    settori.setEntity(entity);
	    settori.setDisplayMode(SettoriCommand.VIEW);
	    model.addAttribute("settori", settori);
	    return "settori/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodicesettore() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	SettoriCommand settori = new SettoriCommand();
	Settori entity = new Settori();
	fixRenderEntityProperty(settori.getEntity());
	settori.setEntity(entity);
	settori.setDisplayMode(SettoriCommand.NEW);
	model.addAttribute("settori", settori);
	setPageAttributes(model);
	return "settori/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	SettoriId id = new SettoriId(codice);
	Settori entity = sectoriService.findById(id);
	SettoriCommand settori = new SettoriCommand();
	settori.setEntity(entity);
	settori.setDisplayMode(SettoriCommand.VIEW);
	fixRenderEntityProperty(settori.getEntity());
	model.addAttribute("settori", settori);
	setPageAttributes(model);
	return "settori/form";
    }

    @RequestMapping
    public void ajaxAbilitaDisabilita(@RequestParam("codice") String codice, @RequestParam("abilita") Boolean abilita, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	SettoriId settoriId = new SettoriId(codice);
	Settori settori = sectoriService.findById(settoriId);
	settori.setFlagDisabilitato(abilita);
	fixMergeEntityProperty(settori);
	try {
	    sectoriService.update(settori);
	    response.getWriter().write(getMessageFromBundle("attivita.label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("attivita.label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public ModelMap listsettoriavvisi(@RequestParam("codicesettore") String codicesettore, HttpServletRequest request, HttpServletResponse response) {

	// creo il filtro recuperando dal codicesettore l'oggetto settore associato
	Settoriavvisi filter = new Settoriavvisi();
	Settori settore = sectoriService.findById(new SettoriId(codicesettore));
	filter.setSettore(settore);
	List<Settoriavvisi> settoriavvisiList = sectoriavvisiService.findByFilter(filter);
	ModelMap model = new ModelMap(settoriavvisiList);
	boolean export = createJMesaExport(request, response, settoriavvisiList);
	if (export)
	    return null;
	model.addAttribute("settoriavvisiList", settoriavvisiList);
	model.addAttribute("settore", settore);
	return model;
    }

    @RequestMapping
    public String createSettoriavvisi(@RequestParam("codicesettore") String codicesettore, Model model) {

	Settoriavvisi settoriavvisi = new Settoriavvisi();
	Settori settori = sectoriService.findById(new SettoriId(codicesettore));
	settoriavvisi.setSettore(settori);
	fixRenderSettoriavvisiProperty(settoriavvisi);
	model.addAttribute("settoriavvisi", settoriavvisi);
	model.addAttribute("settore", settori);
	setPageAttributes(model);
	return "settori/formSettoriavvisi";
    }

    @RequestMapping
    public String viewSettoriavvisi(@RequestParam("codiceavviso") Short codiceavviso, @RequestParam("codicesettore") String codicesettore, Model model) {

	PkId id = new PkId(codiceavviso.intValue());
	Settoriavvisi settoriavvisi = sectoriavvisiService.findById(id);
	Settori settori = sectoriService.findById(new SettoriId(codicesettore));
	fixRenderSettoriavvisiProperty(settoriavvisi);
	model.addAttribute("settoriavvisi", settoriavvisi);
	model.addAttribute("settore", settori);
	setPageAttributes(model);
	return "settori/formSettoriavvisi";
    }

    @RequestMapping
    public String insertSettoreavvisi(Model model, @ModelAttribute("settoriavvisi") Settoriavvisi settoriavvisi, BindingResult result,
	    SessionStatus status) {

	// recupero l'oggetto corrente
	Oggetti oggetto = oggettiService.findById(settoriavvisi.getOggetto().getId());
	settoriavvisi.setOggetto(oggetto);
	Software software = softwareService.findById(ORMHelper.getSoftware());
	settoriavvisi.setSoftware(software);
	// recupero il settore corrente
	Settori settori = sectoriService.findById(settoriavvisi.getSettore().getId());
	settoriavvisi.setSettore(settori);
	try {
	    sectoriavvisiService.insert(settoriavvisi);
	} catch (Exception e) {
	    fixRenderSettoriavvisiProperty(settoriavvisi);
	    copyErrorsToBindingResult(result, settoriavvisi, e);
	    model.addAttribute("settore", settori);
	    return "settori/formSettoriavvisi";
	}
	status.setComplete();
	return "redirect:viewSettoriavvisi.htm?codiceavviso=" + settoriavvisi.getId().getCodice() + "&codicesettore="
		+ settori.getId().getCodicesettore() + "&status_msg=01";
    }

    @RequestMapping
    public String updateSettoreavvisi(Model model, @ModelAttribute("settoriavvisi") Settoriavvisi settoriavvisi, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero l'oggetto corrente e lo inserisco in albopoubblicazioniallegati
	if (settoriavvisi.getOggetto().getId() != null) {
	    Oggetti oggetto = oggettiService.findById(settoriavvisi.getOggetto().getId());
	    settoriavvisi.setOggetto(oggetto);
	}
	// recupero il settore corrente
	Settori settori = sectoriService.findById(settoriavvisi.getSettore().getId());
	settoriavvisi.setSettore(settori);
	try {
	    sectoriavvisiService.update(settoriavvisi);
	} catch (Exception e) {
	    fixRenderSettoriavvisiProperty(settoriavvisi);
	    copyErrorsToBindingResult(result, settoriavvisi, e);
	    model.addAttribute("settore", settori);
	    return "settori/formSettoriavvisi";
	}
	status.setComplete();
	return "redirect:viewSettoriavvisi.htm?codiceavviso=" + settoriavvisi.getId().getCodice() + "&codicesettore="
		+ settori.getId().getCodicesettore() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteSettoreavvisi(Model model, @ModelAttribute("settoriavvisi") Settoriavvisi settoriavvisi, BindingResult result,
	    SessionStatus status) {

	Settoriavvisi objToDelete = sectoriavvisiService.findById(settoriavvisi.getId());
	try {
	    sectoriavvisiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "settori/formSettoriavvisi";
	}
	status.setComplete();
	return "redirect:listsettoriavvisi.htm?codicesettore=" + objToDelete.getSettore().getId().getCodicesettore();
    }

    @RequestMapping
    public ModelMap listattivita(@RequestParam("codicesettore") String codicesettore, HttpServletRequest request, HttpServletResponse response) {

	Settori settori = sectoriService.findById(new SettoriId(codicesettore));
	Set<Attivita> attivitaList = settori.getAttivitas();
	ModelMap model = new ModelMap(attivitaList);
	boolean export = createJMesaExport(request, response, attivitaList);
	if (export)
	    return null;
	model.addAttribute("settore", settori);
	model.addAttribute("attivitaList", attivitaList);
	return model;
    }

    @Override
    protected void fixMergeEntityProperty(Settori entity) {

	if (entity.getTipiunitamisura() != null && entity.getTipiunitamisura().getId() != null
		&& entity.getTipiunitamisura().getId().getCodice() == null) {
	    entity.setTipiunitamisura(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Settori entity) {

	if (entity.getTipiunitamisura() == null) {
	    entity.setTipiunitamisura(new Tipiunitamisura());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    protected void fixRenderSettoriavvisiProperty(Settoriavvisi entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getSettore() == null) {
	    entity.setSettore(new Settori());
	}
    }

    protected void fixMergeSettoriavvisiProperty(Settoriavvisi entity) {

	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
	if (entity.getSettore() != null && entity.getSettore().getId() != null && entity.getSettore().getId().getCodicesettore() == null) {
	    entity.setSettore(null);
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
