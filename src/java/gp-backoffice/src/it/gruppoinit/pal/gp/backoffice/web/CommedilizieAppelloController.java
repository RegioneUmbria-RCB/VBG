package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import it.gruppoinit.pal.gp.backoffice.web.helper.CommEdilizieAppelloPraticheHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.models.CommedilizieAppelloComparator;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CommedilizieAppelloService;
import it.gruppoinit.pal.gp.core.service.CommedilizieCaricaService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("commedilizieappello")
public class CommedilizieAppelloController extends BaseController<CommedilizieAppello> {

    @Autowired
    private CommedilizieAppelloService commedilizieappelloService;
    @Autowired
    private CommissioniedilizieTService commissioniedilizieTService;
    @Autowired
    private CommedilizieCaricaService commedilizieCaricaService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @RequestMapping
    public ModelMap list(@RequestParam("codiceCommissione") Integer codiceCommissione, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.findById(new PkId(codiceCommissione));
	ModelMap model = new ModelMap(commissioniedilizieT.getCommedilizieAppellos());
	//	boolean export = createJMesaExport(request, response, commissioniedilizieT.getCommedilizieAppellos());
	//	if (export) {
	//	    return null;
	//	}
	model.addAttribute("commissioniedilizieT", commissioniedilizieT);
	List<CommedilizieAppello> appelli = new ArrayList<CommedilizieAppello>();
	appelli.addAll(commissioniedilizieT.getCommedilizieAppellos());
	Collections.sort(appelli, new CommedilizieAppelloComparator());
	model.addAttribute("commedilizieappelloList", appelli);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(@RequestParam("codiceCommissione") Integer codiceCommissione, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.findById(new PkId(codiceCommissione));
	CommedilizieAppello commedilizieappello = new CommedilizieAppello();
	commedilizieappello.setCommissioniedilizieT(commissioniedilizieT);
	String idCommissione = codiceCommissione.toString();
	String password = Utilities.generaPassword(10 - idCommissione.length());
	String pin = idCommissione + password;
	commedilizieappello.setPin(pin.toUpperCase());
	fixRenderEntityProperty(commedilizieappello);
	List<CommedilizieCarica> listCariche = commedilizieCaricaService.findAll(null, null);
	model.addAttribute("listCariche", listCariche);
	model.addAttribute("commedilizieappello", commedilizieappello);
	request.setAttribute("commissione", commedilizieappello);
	setPageAttributes(model);
	setPraticheModel(model, commedilizieappello, false);
	return "commedilizieappello/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("commedilizieappello") CommedilizieAppello commedilizieappello, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	if (EntityUtils.getNestedProperty(commedilizieappello.getResponsabile(), "id.codice") != null) {
	    Responsabili responsabili = responsabiliService.findById(commedilizieappello.getResponsabile().getId());
	    commedilizieappello.setResponsabile(responsabili);
	}
	if (EntityUtils.getNestedProperty(commedilizieappello.getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(commedilizieappello.getAmministrazioni().getId());
	    commedilizieappello.setAmministrazioni(amministrazioni);
	}
	if (EntityUtils.getNestedProperty(commedilizieappello.getAnagrafe(), "id.codice") != null) {
	    Anagrafe anagrafe = anagrafeService.findById(commedilizieappello.getAnagrafe().getId());
	    commedilizieappello.setAnagrafe(anagrafe);
	}
	if (EntityUtils.getNestedProperty(commedilizieappello.getCommedilizieCarica(), "id.codice") != null) {
	    CommedilizieCarica commedilizieCarica = commedilizieCaricaService.findById(commedilizieappello.getCommedilizieCarica().getId());
	    commedilizieappello.setCommedilizieCarica(commedilizieCarica);
	}
	if (commedilizieappello.getAmministrazioni().getId().getCodice() == null) {
	    commedilizieappello.setPin(null);
	}
	try {
	    List<Integer> l = recuperaCodiciCommEdilizieR(request);
	    commedilizieappelloService.insert(commedilizieappello, l);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizieappello, e);
	    fixRenderEntityProperty(commedilizieappello);
	    setPraticheModel(model, commedilizieappello, false);
	    List<CommedilizieCarica> listCariche = commedilizieCaricaService.findAll(null, null);
	    model.addAttribute("listCariche", listCariche);
	    request.setAttribute("commissione", commedilizieappello);
	    request.setAttribute("commissione", commedilizieappello);
	    return "commedilizieappello/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizieappello.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieAppello commedilizieappello = commedilizieappelloService.findById(id);
	List<CommedilizieCarica> listCariche = commedilizieCaricaService.findAll(null, null);
	fixRenderEntityProperty(commedilizieappello);
	model.addAttribute("commedilizieappello", commedilizieappello);
	model.addAttribute("listCariche", listCariche);
	request.setAttribute("commissione", commedilizieappello);
	setPageAttributes(model);
	setPraticheModel(model, commedilizieappello, true);
	return "commedilizieappello/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("commedilizieappello") CommedilizieAppello commedilizieappello, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	if (EntityUtils.getNestedProperty(commedilizieappello.getResponsabile(), "id.codice") != null) {
	    Responsabili responsabili = responsabiliService.findById(commedilizieappello.getResponsabile().getId());
	    commedilizieappello.setResponsabile(responsabili);
	}
	if (EntityUtils.getNestedProperty(commedilizieappello.getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(commedilizieappello.getAmministrazioni().getId());
	    commedilizieappello.setAmministrazioni(amministrazioni);
	}
	if (EntityUtils.getNestedProperty(commedilizieappello.getCommedilizieCarica(), "id.codice") != null) {
	    CommedilizieCarica commedilizieCarica = commedilizieCaricaService.findById(commedilizieappello.getCommedilizieCarica().getId());
	    commedilizieappello.setCommedilizieCarica(commedilizieCarica);
	}
	if (EntityUtils.getNestedProperty(commedilizieappello.getAnagrafe(), "id.codice") != null) {
	    CommedilizieCarica commedilizieCarica = commedilizieCaricaService.findById(commedilizieappello.getCommedilizieCarica().getId());
	    commedilizieappello.setCommedilizieCarica(commedilizieCarica);
	} else {
	    commedilizieappello.setAnagrafe(null);
	}
	try {
	    List<Integer> l = recuperaCodiciCommEdilizieR(request);
	    commedilizieappelloService.update(commedilizieappello, l);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizieappello, e);
	    fixRenderEntityProperty(commedilizieappello);
	    setPraticheModel(model, commedilizieappello, false);
	    List<CommedilizieCarica> listCariche = commedilizieCaricaService.findAll(null, null);
	    model.addAttribute("listCariche", listCariche);
	    fixRenderEntityProperty(commedilizieappello);
	    request.setAttribute("commissione", commedilizieappello);
	    return "commedilizieappello/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizieappello.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("commedilizieappello") CommedilizieAppello commedilizieappello, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	CommedilizieAppello objToDelete = commedilizieappelloService.findById(commedilizieappello.getId());
	try {
	    commedilizieappelloService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(commedilizieappello);
	    List<CommedilizieCarica> listCariche = commedilizieCaricaService.findAll(null, null);
	    setPraticheModel(model, commedilizieappello, false);
	    model.addAttribute("listCariche", listCariche);
	    request.setAttribute("commissione", commedilizieappello);
	    return "commedilizieappello/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceCommissione=" + objToDelete.getCommissioniedilizieT().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxAbilitaDisabilita(@RequestParam("codice") String codice, @RequestParam("abilita") Boolean abilita, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	CommedilizieAppello commedilizieAppello = commedilizieappelloService.findById(new PkId(Integer.parseInt(codice)));
	commedilizieAppello.setPresente(abilita);
	response.setContentType("text/plain");
	try {
	    commedilizieappelloService.update(commedilizieAppello);
	    response.getWriter().write("Dato aggiornato");
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write("Errore aggiornamento: " + e.getMessage() + "");
	}
	// §§§END§§§
    }

    @Override
    protected void fixMergeEntityProperty(CommedilizieAppello entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CommedilizieAppello entity) {

	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getResponsabile() == null) {
	    entity.setResponsabile(new Responsabili());
	}
	if (entity.getCommissioniedilizieT() == null) {
	    entity.setCommissioniedilizieT(new CommissioniedilizieT());
	}
	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getCommedilizieCarica() == null) {
	    entity.setCommedilizieCarica(new CommedilizieCarica());
	}
	if (entity.getAmministrazioni().getId().getCodice() != null && entity.getCommissioniedilizieT() != null && entity.getPin() == null) {
	    Integer codiceComm = entity.getCommissioniedilizieT().getId().getCodice();
	    entity.setPin(codiceComm + Utilities.generaPassword(10 - codiceComm.toString().length()).toUpperCase());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Autowired
    private CommissioniedilizieRService commissioniedilizieRService;

    protected void setPraticheModel(Model model, CommedilizieAppello appello, boolean isView) {

	model.addAttribute("PRATICHE_VIEW", Boolean.valueOf(isView));
	if (isView) {
	    List<CommedilizieAppelloPratiche> appelloPratiches = commedilizieappelloService
		    .findCommEdilizieAppelloPraticheByAppello(appello.getId().getCodice());
	    List<CommissioniedilizieR> commedilizieR = commissioniedilizieRService
		    .findIstanzeByCommissioneEdiliziaT(appello.getCommissioniedilizieT().getId().getCodice());
	    List<CommEdilizieAppelloPraticheHelper> fromCommissioniEdilizieRlist = CommEdilizieAppelloPraticheHelper
		    .fromAppelloPraticheAndAppelloList(commedilizieR, appelloPratiches, appello);
	    model.addAttribute("lista_pratiche", fromCommissioniEdilizieRlist);
	} else {
	    List<CommissioniedilizieR> istanze = commissioniedilizieRService
		    .findIstanzeByCommissioneEdiliziaT(appello.getCommissioniedilizieT().getId().getCodice());
	    List<CommEdilizieAppelloPraticheHelper> fromCommissioniEdilizieRlist = CommEdilizieAppelloPraticheHelper
		    .fromCommissioniEdilizieRlist(istanze, appello, !isView);
	    model.addAttribute("lista_pratiche", fromCommissioniEdilizieRlist);
	}
    }

    private List<Integer> recuperaCodiciCommEdilizieR(HttpServletRequest request) {

	String parameter[] = request.getParameterValues("commissioneEdiliziaR");
	ArrayList<Integer> in = new ArrayList<Integer>();
	if (parameter != null) {
	    for (String p : parameter) {
		in.add(Integer.parseInt(p));
	    }
	}
	return in;
    }
}
