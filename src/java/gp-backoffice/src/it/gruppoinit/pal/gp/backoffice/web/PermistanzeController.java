package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeruoli;
import it.gruppoinit.pal.gp.core.domain.IstanzeruoliId;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PermistanzeId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeruoliService;
import it.gruppoinit.pal.gp.core.service.PermistanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("permistanze")
public class PermistanzeController extends BaseController<Permistanze> {

    @Autowired
    private PermistanzeService permistanzeService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private IstanzeruoliService istanzeruoliService;

    @RequestMapping
    public String list(Model model, @RequestParam("codiceIstanza") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = istanzeService.findById(new PkId(codice));
	List<Responsabili> amministratoriList = responsabiliService.findAmministratori(false);
	List<Responsabili> amministratoriSoftwareList = responsabiliService.findAmministratoriSoftware(false, "0", istanze.getSoftware().getCodice());
	List<Ruoli> ruoliList = ruoliService.findAllRuoliAndCheckAccessiInstaza(istanze);
	List<Responsabili> responsabiliList = responsabiliService.findAllResponsabiliAndCheckAccessiInstaza(false, istanze, istanze.getSoftware()
		.getCodice());
	model.addAttribute("istanze", istanze);
	model.addAttribute("amministratoriList", amministratoriList);
	model.addAttribute("amministratoriSoftwareList", amministratoriSoftwareList);
	model.addAttribute("ruoliList", ruoliList);
	model.addAttribute("responsabiliList", responsabiliList);
	setListAttributes(model, request);
	return "permistanze/list";
    }

    @RequestMapping
    public void ajaxAbilitaDisabilitaRuolo(@RequestParam("codiceistanza") String codiceistanza, @RequestParam("codiceruolo") String codiceruolo,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Istanzeruoli istanzeruoli = null;
	IstanzeruoliId id = new IstanzeruoliId(Integer.parseInt(codiceistanza), Integer.parseInt(codiceruolo));
	Istanze istanze = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	try {
	    checkAccessoInformazioni(istanze, true);
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	    return;
	}
	Ruoli ruoli = ruoliService.findById(new PkId(Integer.parseInt(codiceruolo)));
	istanzeruoli = istanzeruoliService.findById(id);
	try {
	    if (istanzeruoli != null) {
		istanzeruoliService.delete(istanzeruoli);
	    } else {
		istanzeruoli = new Istanzeruoli();
		istanzeruoli.setId(id);
		istanzeruoli.setIstanze(istanze);
		istanzeruoli.setRuolo(ruoli);
		istanzeruoliService.insert(istanzeruoli);
	    }
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	    return;
	}
    }

    @RequestMapping
    public void ajaxAbilitaDisabilitaOperatore(@RequestParam("codiceistanza") String codiceistanza,
	    @RequestParam("codiceoperatore") String codiceoperatore, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Permistanze permistanze = null;
	PermistanzeId id = new PermistanzeId(Integer.parseInt(codiceistanza), Integer.parseInt(codiceoperatore));
	Istanze istanze = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	try {
	    checkAccessoInformazioni(istanze, true);
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	    return;
	}
	Responsabili responsabili = responsabiliService.findById(new PkId(Integer.parseInt(codiceoperatore)));
	permistanze = permistanzeService.findById(id);
	try {
	    if (permistanze != null) {
		permistanzeService.delete(permistanze);
	    } else {
		permistanze = new Permistanze();
		permistanze.setId(id);
		permistanze.setIstanze(istanze);
		permistanze.setResponsabile(responsabili);
		permistanzeService.insert(permistanze);
	    }
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	    return;
	}
    }

    // @RequestMapping
    // public String create(Model model) {
    //
    // Permistanze permistanze = new Permistanze();
    // permistanze.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // fixRenderEntityProperty(permistanze);
    // model.addAttribute("permistanze", permistanze);
    // setPageAttributes(model);
    // return "permistanze/form";
    // }
    //
    // @RequestMapping
    // public String insert(@ModelAttribute("permistanze") Permistanze permistanze, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(permistanze);
    // permistanze.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // permistanzeService.insert(permistanze);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(permistanzeService.getValidationMessages(), result, permistanze, e.getMessage());
    // fixRenderEntityProperty(permistanze);
    // return "permistanze/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + permistanze.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // Permistanze permistanze = permistanzeService.findById(id);
    // fixRenderEntityProperty(permistanze);
    // model.addAttribute("permistanze", permistanze);
    // setPageAttributes(model);
    // return "permistanze/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("permistanze") Permistanze permistanze, BindingResult result, SessionStatus
    // status,
    // HttpServletRequest request) {
    //
    // fixMergeEntityProperty(permistanze);
    // try {
    // permistanzeService.update(permistanze);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(permistanzeService.getValidationMessages(), result, permistanze, e.getMessage());
    // fixRenderEntityProperty(permistanze);
    // return "permistanze/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + permistanze.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("permistanze") Permistanze permistanze, BindingResult result, SessionStatus
    // status) {
    //
    // Permistanze objToDelete = permistanzeService.findById(permistanze.getId());
    // try {
    // permistanzeService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(permistanzeService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(permistanze);
    // return "permistanze/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    private void setListAttributes(Model model, HttpServletRequest request) {

	// -----------------INIZIO GESTIONE PASSWORD AUTOMATICA-----------------
	ConfigurazioneId id = new ConfigurazioneId();
	Configurazione configurazione = configurazioneService.findById(id);
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ISTANZA_AMMINISTRATORI_ABILITATI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ISTANZA_AMMINISTRATORI_SOFTWARE_ABILITATI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ISTANZA_RUOLI_ACCESSO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ISTANZA_OPERATORI_ACCESSO, "1", request);
	model.addAttribute("configurazione", configurazione);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------- -----------
    }

    @Override
    protected void fixMergeEntityProperty(Permistanze entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Permistanze entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
