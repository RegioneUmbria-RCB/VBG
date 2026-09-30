package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
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

import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiR;
import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti.IAlberoCoefficientiRService;
import it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti.IAlberoCoefficientiTService;

@Controller
@SessionAttributes(value = { "alberoCoefficientiT", "alberoCoefficientiR" })
public class AlberoCoefficientiTController extends BaseController<AlberoCoefficientiT> {

    @Autowired
    private IAlberoCoefficientiTService alberoCoefficientiTService;
    @Autowired
    private IAlberoCoefficientiRService alberoCoefficientiRService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<AlberoCoefficientiT> alberoCoefficientiTList = alberoCoefficientiTService
		.findAllByResponsabile(getCurrentlyAuthenticatedUserDetails().getId().getCodice());
	ModelMap model = new ModelMap(alberoCoefficientiTList);
	boolean export = createJMesaExport(request, response, alberoCoefficientiTList);
	if (export)
	    return null;
	model.addAttribute("alberoCoefficientiTList", alberoCoefficientiTList);
	return model;
    }

    @RequestMapping
    public void ajaxDeleteTestata(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	if (!alberoCoefficientiTService.responsabileHaPermessiSuConfigurazione(getCurrentlyAuthenticatedUserDetails().getId().getCodice(), codice)) {
	    throw new InvalidConfigurationException("Il responsabile non può accedere alla configurazione");
	}
	AlberoCoefficientiT objToDelete = alberoCoefficientiTService.findById(new PkId(codice));
	try {
	    alberoCoefficientiTService.delete(objToDelete);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @RequestMapping
    public void ajaxGestAttivoTestata(Model model, @RequestParam("codice") Integer codice, @RequestParam("attiva") Boolean attiva,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (!alberoCoefficientiTService.responsabileHaPermessiSuConfigurazione(getCurrentlyAuthenticatedUserDetails().getId().getCodice(), codice)) {
	    throw new InvalidConfigurationException("Il responsabile non può accedere alla configurazione");
	}
	AlberoCoefficientiT testata = alberoCoefficientiTService.findById(new PkId(codice));
	try {
	    testata.setAttivo(attiva);
	    alberoCoefficientiTService.update(testata);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoCoefficientiT") AlberoCoefficientiT alberoCoefficientiT, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(alberoCoefficientiT);
	try {
	    alberoCoefficientiTService.insert(alberoCoefficientiT);
	    if (alberoCoefficientiT.getCodiceCopiaTestata() != 0) {
		inserisciCodiciEsistentiInTestata(alberoCoefficientiT.getCodiceCopiaTestata(), alberoCoefficientiT.getId().getCodice());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoCoefficientiT, e);
	    fixRenderEntityProperty(alberoCoefficientiT);
	    return "alberocoefficientit/form";
	}
	status.setComplete();
	return "redirect:list.htm" + "?status_msg=01";
    }

    private void inserisciCodiciEsistentiInTestata(int codiceCopiaTestata, Integer codice) {

	//Estraggo la lista dei coefficienti collegati alla testata già inserita 
	//e li inserisco 
	AlberoCoefficientiT t = alberoCoefficientiTService.findById(new PkId(codice));
	List<AlberoCoefficientiR> listR = alberoCoefficientiRService.findAllRigheByTestata(codiceCopiaTestata);
	for (AlberoCoefficientiR alberoCoefficientiR : listR) {
	    AlberoCoefficientiR newCoeffR = new AlberoCoefficientiR();
	    newCoeffR.setAlberoCoefficientiT(t);
	    newCoeffR.setCodiceCoefficente(alberoCoefficientiR.getCodiceCoefficente());
	    newCoeffR.setDescrizione(alberoCoefficientiR.getDescrizione());
	    newCoeffR.setNote(alberoCoefficientiR.getNote());
	    newCoeffR.setTipo(alberoCoefficientiR.getTipo());
	    newCoeffR.setValore(alberoCoefficientiR.getValore());
	    newCoeffR.setVisibile(alberoCoefficientiR.getVisibile());
	    try {
		alberoCoefficientiRService.insert(newCoeffR);
	    } catch (Exception e) {
		e.getMessage();
	    }
	}
    }

    private void fixRenderEntityProperty(AlberoCoefficientiR alberoCoefficientiR) {

	// TODO Auto-generated method stub
    }

    private void fixMergeEntityProperty(AlberoCoefficientiR alberoCoefficientiR) {

	// TODO Auto-generated method stub
    }

    @RequestMapping
    public String create(Model model) {

	AlberoCoefficientiT alberoCoefficientiT = new AlberoCoefficientiT();
	fixRenderEntityProperty(alberoCoefficientiT);
	List<AlberoCoefficientiT> list = alberoCoefficientiTService.findAllByResponsabile(getCurrentlyAuthenticatedUserDetails().getId().getCodice());
	model.addAttribute("alberoCoefficientiT", alberoCoefficientiT);
	model.addAttribute("listaTestata", list);
	setPageAttributes(model);
	return "alberocoefficientit/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	if (!alberoCoefficientiTService.responsabileHaPermessiSuConfigurazione(getCurrentlyAuthenticatedUserDetails().getId().getCodice(), codice)) {
	    throw new InvalidConfigurationException("Il responsabile non può accedere alla configurazione");
	}
	PkId id = new PkId(codice);
	List<AlberoCoefficientiR> righeCoeff = alberoCoefficientiRService.findAllRigheByTestata(codice);
	AlberoCoefficientiT alberoCoefficientiT = alberoCoefficientiTService.findById(id);
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	boolean isAmministratore = StringUtils.defaultIfEmpty(responsabili.getAmministratore(), "0").equals("1");
	model.addAttribute("isAmministratore", isAmministratore);
	model.addAttribute("righeCoeff", righeCoeff);
	model.addAttribute("alberoCoefficientiT", alberoCoefficientiT);
	setPageAttributes(model);
	return "alberocoefficientit/form";
    }

    @RequestMapping
    public String viewRiga(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoCoefficientiR alberoCoefficientiR = alberoCoefficientiRService.findById(id);
	model.addAttribute("alberoCoefficientiR", alberoCoefficientiR);
	setPageAttributes(model);
	return "alberocoefficientit/forminserimento";
    }

    @RequestMapping
    public String createRiga(@RequestParam("codiceTestata") Integer codiceTestata, Model model) {

	AlberoCoefficientiT testata = alberoCoefficientiTService.findById(new PkId(codiceTestata));
	AlberoCoefficientiR riga = new AlberoCoefficientiR();
	riga.setAlberoCoefficientiT(testata);
	model.addAttribute("alberoCoefficientiT", testata);
	model.addAttribute("alberoCoefficientiR", riga);
	return "alberocoefficientit/forminserimento";
    }

    @RequestMapping
    public String insertRiga(@ModelAttribute("alberoCoefficientiR") AlberoCoefficientiR alberoCoefficientiR, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(alberoCoefficientiR);
	try {
	    alberoCoefficientiRService.insert(alberoCoefficientiR);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoCoefficientiR, e);
	    fixRenderEntityProperty(alberoCoefficientiR);
	    return "alberocoefficientit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoCoefficientiR.getAlberoCoefficientiT().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String updateRiga(@ModelAttribute("alberoCoefficientiR") AlberoCoefficientiR alberoCoefficientiR, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(alberoCoefficientiR);
	try {
	    if (request.getParameter("visibile") == null) {
		alberoCoefficientiR.setVisibile(Boolean.FALSE);
	    }
	    alberoCoefficientiRService.update(alberoCoefficientiR);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoCoefficientiR, e);
	    fixRenderEntityProperty(alberoCoefficientiR);
	    return "alberocoefficientit/forminserimento";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoCoefficientiR.getAlberoCoefficientiT().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteRiga(@RequestParam("codiceRiga") Integer codiceRiga,
	    @ModelAttribute("alberoCoefficientiT") AlberoCoefficientiT alberoCoefficientiT, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	AlberoCoefficientiR objToDelete = alberoCoefficientiRService.findById(new PkId(codiceRiga));
	Integer codiceTestata = objToDelete.getAlberoCoefficientiT().getId().getCodice();
	try {
	    alberoCoefficientiRService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(objToDelete);
	    return "alberocoefficientit/form";
	}
	status.setComplete();
	return "redirect:../alberocoefficientit/view.htm?codice=" + codiceTestata;
    }

    @RequestMapping
    public void ajaxInsertRiga(Model model, @RequestParam("codiceCoeff") String codiceCoeff, @RequestParam("descrizione") String descrizione,
	    @RequestParam("valore") String valore, @RequestParam("codiceTestata") Integer codiceTestata, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	AlberoCoefficientiR alberoCoefficientiR = new AlberoCoefficientiR();
	alberoCoefficientiR.setCodiceCoefficente(codiceCoeff);
	alberoCoefficientiR.setDescrizione(descrizione);
	alberoCoefficientiR.setValore(valore);
	alberoCoefficientiR.setVisibile(true);
	//TODO che tipo setto??
	alberoCoefficientiR.setTipo(" ");
	AlberoCoefficientiT alberoCoefficientiT = alberoCoefficientiTService.findById(new PkId(codiceTestata));
	alberoCoefficientiR.setAlberoCoefficientiT(alberoCoefficientiT);
	try {
	    alberoCoefficientiRService.insert(alberoCoefficientiR);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @RequestMapping
    public void ajaxSalvaRiga(Model model, @RequestParam("codice") Integer codice, @RequestParam("valore") String valore, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	AlberoCoefficientiR r = alberoCoefficientiRService.findById(new PkId(codice));
	try {
	    r.setValore(valore);
	    alberoCoefficientiRService.update(r);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(AlberoCoefficientiT entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(AlberoCoefficientiT entity) {

	// TODO Auto-generated method stub
    }
}
