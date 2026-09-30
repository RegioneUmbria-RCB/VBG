package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AttivitaCommand;
import it.gruppoinit.pal.gp.core.domain.web.SettoriCommand;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
 * @author gianpaolot
 */
@Controller
@SessionAttributes("attivita")
public class AttivitaController extends BaseController<Attivita> {

    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private SettoriService settoriService;

    @RequestMapping
    public ModelMap list(@RequestParam(value = "codicesectore", required = false) String codicesettore, HttpServletRequest request,
	    HttpServletResponse response) {

	// si valuta se il codice settore è stringa vuota o no
	// nel caso sia stringa vuota si presenza la normale lista di tutte le attivita
	// altrimenti si presentano solo le attività associate a quel settore
	ModelMap model = null;
	boolean export = false;
	if (codicesettore != null && !codicesettore.equals("")) {
	    Settori settori = settoriService.findById(new SettoriId(codicesettore));
	    Set<Attivita> attivitaSet = settori.getAttivitas();
	    model = new ModelMap(attivitaSet);
	    model.addAttribute("attivitaList", attivitaSet);
	    model.addAttribute("settori", settori);
	    export = createJMesaExport(request, response, attivitaSet);
	} else {
	    List<Attivita> attivitaList = attivitaService.findAll(null, null);
	    model = new ModelMap(attivitaList);
	    model.addAttribute("attivitaList", attivitaList);
	    export = createJMesaExport(request, response, attivitaList);
	}
	if (export) {
	    return null;
	}
	model.addAttribute("codicesectore", codicesettore);
	return model;
    }

    @RequestMapping
    public void ajaxAbilitaDisabilita(@RequestParam("codice") String codice, @RequestParam("abilita") Boolean abilita, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Fa una chiamata ajax che fa a modificare dinamicamente il flag_disabilita
	AttivitaId attivitaId = new AttivitaId(codice);
	Attivita attivita = attivitaService.findById(attivitaId);
	attivita.setFlagDisabilitato(abilita);
	fixMergeEntityProperty(attivita);
	try {
	    attivitaService.update(attivita);
	    response.getWriter().write(getMessageFromBundle("attivita.label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("attivita.label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public String create(@RequestParam("codicesectore") String codicesettore, Model model) {

	AttivitaCommand attivita = new AttivitaCommand();
	Attivita entity = new Attivita();
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(attivita.getEntity());
	attivita.setEntity(entity);
	attivita.setDisplayMode(AttivitaCommand.NEW);
	model.addAttribute("attivita", attivita);
	// servirà per la logica di visualizzazione della jsp
	model.addAttribute("codicesectore", codicesettore);
	if (codicesettore != null && !codicesettore.equals("")) {
	    Settori settori = settoriService.findById(new SettoriId(codicesettore));
	    model.addAttribute("settori", settori);
	}
	setPageAttributes(model);
	return "attivita/form";
    }

    @RequestMapping
    public String insert(@RequestParam("codicesectore") String codicesettore, Model model, @ModelAttribute("attivita") AttivitaCommand attivita,
	    BindingResult result, SessionStatus status) {

	Attivita entity = attivita.getEntity();
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	// recupero il settore, se sto inserendo un attività per uno specifico settore
	// eseguirò l'if e ricavo il settore dal codice passato sulla request
	// se il codice settore sulla request =null o a "" esegue l'esle
	if (codicesettore != null && !codicesettore.equals("")) {
	    Settori settori = settoriService.findById(new SettoriId(codicesettore));
	    entity.setSettori(settori);
	} else {// controllo che ho popolato il capo settore ,se si lo ricavo dal DB
	    if (entity.getSettori().getId() != null && entity.getSettori().getId().getCodicesettore() != null
		    && !entity.getSettori().getId().getCodicesettore().equals("")) {
		Settori settori = settoriService.findById(new SettoriId(entity.getSettori().getId().getCodicesettore()));
		entity.setSettori(settori);
	    }
	}
	attivita.setEntity(entity);
	fixMergeEntityProperty(attivita.getEntity());
	try {
	    attivitaService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, attivita.getEntity(), true, e);
	    fixRenderEntityProperty(entity);
	    attivita.setEntity(entity);
	    attivita.setDisplayMode(AttivitaCommand.NEW);
	    model.addAttribute("codicesectore", codicesettore);
	    if (codicesettore != null && !codicesettore.equals("")) {
		Settori settori = settoriService.findById(new SettoriId(codicesettore));
		model.addAttribute("settori", settori);
	    }
	    return "attivita/form";
	}
	status.setComplete();
	if (codicesettore != null && !codicesettore.equals("")) {
	    return "redirect:view.htm?codice=" + entity.getId().getCodiceistat() + "&codicesectore=" + codicesettore + "&status_msg=01";
	} else {
	    return "redirect:view.htm?codice=" + entity.getId().getCodiceistat() + "&status_msg=01";
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") String codice, @RequestParam(value = "codicesectore", required = false) String codicesettore,
	    Model model, HttpServletRequest request) {

	AttivitaId id = new AttivitaId(codice);
	AttivitaCommand attivita = new AttivitaCommand();
	Attivita entity = attivitaService.findById(id);
	attivita.setEntity(entity);
	attivita.setDisplayMode(SettoriCommand.VIEW);
	fixRenderEntityProperty(attivita.getEntity());
	model.addAttribute("attivita", attivita);
	// servirà per la logica di visualizzazione della jsp
	model.addAttribute("codicesectore", codicesettore);
	if (codicesettore != null && !codicesettore.equals("")) {
	    Settori settori = settoriService.findById(new SettoriId(codicesettore));
	    model.addAttribute("settori", settori);
	}
	setPageAttributes(model);
	return "attivita/form";
    }

    @RequestMapping
    public String update(@RequestParam("codicesectore") String codicesettore, Model model, @ModelAttribute("attivita") AttivitaCommand attivita,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	Attivita entity = attivita.getEntity();
	// recupero il settore
	//	entity.getSettori() entity.getSettori().getId() != null && !entity.getSettori().getId().getCodicesettore().equals("")
	if (EntityUtils.getNestedProperty(entity.getSettori(), "id.codicesettore") != null) {
	    Settori settori = settoriService.findById(new SettoriId(entity.getSettori().getId().getCodicesettore()));
	    entity.setSettori(settori);
	}
	attivita.setEntity(entity);
	fixMergeEntityProperty(attivita.getEntity());
	try {
	    attivitaService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, attivita.getEntity(), true, e);
	    fixRenderEntityProperty(attivita.getEntity());
	    attivita.setEntity(entity);
	    attivita.setDisplayMode(SettoriCommand.VIEW);
	    model.addAttribute("attivita", attivita);
	    model.addAttribute("codicesectore", codicesettore);
	    if (codicesettore != null && !codicesettore.equals("")) {
		Settori settori = settoriService.findById(new SettoriId(codicesettore));
		model.addAttribute("settori", settori);
	    }
	    return "attivita/form";
	}
	//model.addAttribute("codicesectore", codicesettore);
	status.setComplete();
	if (codicesettore != null && !codicesettore.equals("")) {
	    return "redirect:view.htm?codice=" + entity.getId().getCodiceistat() + "&codicesectore=" + codicesettore + "&status_msg=02";
	} else {
	    return "redirect:view.htm?codice=" + entity.getId().getCodiceistat() + "&status_msg=02";
	}
    }

    @RequestMapping
    public String delete(@RequestParam("codicesectore") String codicesettore, Model model, @ModelAttribute("attivita") AttivitaCommand attivita,
	    BindingResult result, SessionStatus status) {

	Attivita objToDelete = attivitaService.findById(attivita.getEntity().getId());
	try {
	    attivitaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    Attivita attivitaBean = attivitaService.findById(attivita.getEntity().getId());
	    attivita.setEntity(attivitaBean);
	    attivita.setDisplayMode(SettoriCommand.VIEW);
	    fixRenderEntityProperty(attivita.getEntity());
	    model.addAttribute("attivita", attivita);
	    model.addAttribute("codicesectore", codicesettore);
	    if (codicesettore != null && !codicesettore.equals("")) {
		Settori settori = settoriService.findById(new SettoriId(codicesettore));
		model.addAttribute("settori", settori);
	    }
	    return "attivita/form";
	}
	status.setComplete();
	if (codicesettore != null && !codicesettore.equals("")) {
	    return "redirect:list.htm?codicesectore=" + codicesettore;
	} else {
	    return "redirect:list.htm?codicesectore=";
	}
    }

    @RequestMapping()
    public void ajaxFindAttivitaById(@RequestParam("codice") String codice, HttpServletRequest request, HttpServletResponse response) {

	Attivita attivita = attivitaService.findById(new AttivitaId(codice));
	try {
	    if (StringUtils.isNotBlank(attivita.getNote())) {
		response.getWriter().write(attivita.getNote());
	    } else {
		response.getWriter().write("");
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void fixMergeEntityProperty(Attivita entity) {

	if (entity.getSettori() != null && entity.getSettori().getId() != null && entity.getSettori().getId().getCodicesettore() != null
		&& entity.getSettori().getId().getCodicesettore().equals("")) {
	    entity.setSettori(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Attivita entity) {

	if (entity.getSettori() == null) {
	    entity.setSettori(new Settori());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
