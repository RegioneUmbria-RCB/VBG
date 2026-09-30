package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.RegistrazioniDataComparator;
import it.gruppoinit.pal.gp.core.domain.helper.RegistrazioniImportiScadenzaComparator;
import it.gruppoinit.pal.gp.core.domain.web.SchedaContabileCommand;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(value = { "schedaContabileCommand" })
public class SchedaContabileController extends BaseController<SchedaContabileCommand> {

    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private RegistrazioniImportiService registrazioniImportiService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private ResponsabiliService responsabiliService;

    @RequestMapping
    public String search(Model model) {

	// §§§BEGIN§§§
	SchedaContabileCommand schedaContabileCommand = new SchedaContabileCommand();
	model.addAttribute("schedaContabileCommand", schedaContabileCommand);
	return "schedacontabile/search";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("entity.id.codice") Integer codiceAnagrafe, Model model,
	    @ModelAttribute("schedaContabileCommand") SchedaContabileCommand schedaContabileCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	String tabParameter = SchedaContabileCommand.TAB_RAGGRUPPATO;
	String showParameter = SchedaContabileCommand.SHOW_ALL;
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId tabConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.CONF_UTENTE_SCHEDACONTAB_TAB);
	Configurazioneutente tabConf = configurazioneutenteService.findById(tabConfId);
	if (tabConf == null) {
	    // .. inserisco i valori di default
	    tabConf = new Configurazioneutente();
	    tabConf.setId(tabConfId);
	    tabConf.setResponsabile(responsabile);
	    tabConf.setValore(tabParameter);
	    configurazioneutenteService.insert(tabConf);
	} else {
	    tabParameter = tabConf.getValore();
	}
	ConfigurazioneutenteId showConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.CONF_UTENTE_SCHEDACONTAB_CHECK);
	Configurazioneutente showConf = configurazioneutenteService.findById(showConfId);
	if (showConf == null) {
	    // .. inserisco i valori di default
	    showConf = new Configurazioneutente();
	    showConf.setId(showConfId);
	    showConf.setResponsabile(responsabile);
	    showConf.setValore(showParameter);
	    configurazioneutenteService.insert(showConf);
	} else {
	    showParameter = showConf.getValore();
	}
	PkId idAnagrafe = new PkId(codiceAnagrafe);
	Anagrafe entity = anagrafeService.findById(idAnagrafe);
	schedaContabileCommand = new SchedaContabileCommand();
	schedaContabileCommand.setEntity(entity);
	RegistrazioniFilter filter = new RegistrazioniFilter();
	filter.setAnagrafe(entity);
	List<Registrazioni> registrazioniList = registrazioniService.findByRegistrazioniFilter(filter, null, null);
	if (showParameter.equalsIgnoreCase(SchedaContabileCommand.SHOW_ALL)) {
	    List<RegistrazioniImporti> registrazioniImportiList = new ArrayList<RegistrazioniImporti>();
	    for (Registrazioni registrazioni : registrazioniList) {
		registrazioniImportiList.addAll(registrazioni.getRegistrazioniImportis());
	    }
	    Collections.sort(registrazioniList, new RegistrazioniDataComparator());
	    Collections.sort(registrazioniImportiList, new RegistrazioniImportiScadenzaComparator());
	    schedaContabileCommand.setRegistrazioniList(registrazioniList);
	    schedaContabileCommand.setRegistrazioniImportiList(registrazioniImportiList);
	} else {
	    // .. quando showParameter.equalsIgnoreCase(SchedaContabileCommand.SHOW_CLOSED
	    // .. tiro fuori solamente quelle chiuse ossia quelle per le quali non
	    // .. esistono delle scadenze non assegnate
	    List<Registrazioni> tempRegistrazioniList = new ArrayList<Registrazioni>();
	    List<RegistrazioniImporti> tempRegistrazioniImportiList = new ArrayList<RegistrazioniImporti>();
	    for (Registrazioni registrazioni : registrazioniList) {
		boolean addRegistrazione = false;
		Set<RegistrazioniImporti> registrazioniImportis = registrazioni.getRegistrazioniImportis();
		for (RegistrazioniImporti registrazioniImporti : registrazioniImportis) {
		    // escludo le righe di importo che non prevedono incasso
		    if (registrazioniImporti.isNonPrevedeIncassi() == false) {
			// escludo le righe di importo la cui rimanenza è == 0
			if (!(registrazioniImporti.getRimanenza().compareTo(new BigDecimal(0)) == 0)) {
			    addRegistrazione = true;
			    break;
			}
		    }
		}
		if (addRegistrazione) {
		    tempRegistrazioniList.add(registrazioni);
		    tempRegistrazioniImportiList.addAll(registrazioni.getRegistrazioniImportis());
		}
	    }
	    Collections.sort(tempRegistrazioniList, new RegistrazioniDataComparator());
	    Collections.sort(tempRegistrazioniImportiList, new RegistrazioniImportiScadenzaComparator());
	    schedaContabileCommand.setRegistrazioniList(tempRegistrazioniList);
	    schedaContabileCommand.setRegistrazioniImportiList(tempRegistrazioniImportiList);
	}
	model.addAttribute("tutteLeRegistrazioni", registrazioniList);
	model.addAttribute("schedaContabileCommand", schedaContabileCommand);
	model.addAttribute("tabParameter", tabParameter);
	model.addAttribute("showParameter", showParameter);
	return "schedacontabile/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteRegistrazioniImporti(@RequestParam("codiceImporto") Integer codiceImporto, Model model,
	    @ModelAttribute("schedaContabileCommand") SchedaContabileCommand schedaContabileCommand, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	PkId codiceRigaImporto = new PkId(codiceImporto);
	RegistrazioniImporti registrazioniImporti = registrazioniImportiService.findById(codiceRigaImporto);
	try {
	    registrazioniImportiService.delete(registrazioniImporti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniImporti, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("entity.id.codice", schedaContabileCommand.getEntity().getId().getCodice().toString());
	    model.addAttribute("commandName", "schedaContabileCommand");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:view.htm?entity.id.codice=" + schedaContabileCommand.getEntity().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String salvaRigaImporto(@RequestParam("codiceImporto") Integer codiceImporto, @RequestParam("importo") BigDecimal importo, Model model,
	    @ModelAttribute("schedaContabileCommand") SchedaContabileCommand schedaContabileCommand, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	PkId codiceRigaImporto = new PkId(codiceImporto);
	RegistrazioniImporti registrazioniImporti = registrazioniImportiService.findById(codiceRigaImporto);
	// BigDecimal oldImporto = registrazioniImporti.getImporto();
	registrazioniImporti.setImporto(importo);
	// .. ricavo il vecchio importo
	try {
	    registrazioniImportiService.update(registrazioniImporti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniImporti, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("entity.id.codice", schedaContabileCommand.getEntity().getId().getCodice().toString());
	    model.addAttribute("commandName", "schedaContabileCommand");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:view.htm?entity.id.codice=" + schedaContabileCommand.getEntity().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(SchedaContabileCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(SchedaContabileCommand entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
