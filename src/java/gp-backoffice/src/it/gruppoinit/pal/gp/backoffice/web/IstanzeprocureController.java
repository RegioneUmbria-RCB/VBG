package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
 * 
 * @author
 */
@Controller
@SessionAttributes("istanzeprocure")
public class IstanzeprocureController extends BaseController<Istanzeprocure> {

    @Autowired
    private IstanzeprocureService istanzeprocureService;
    @Autowired
    private IstanzeService istanzeService;

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	checkAccessoInformazioni(istanza, true);
	Istanzeprocure istanzeprocure = new Istanzeprocure();
	istanzeprocure.setIstanze(istanza);
	fixRenderEntityProperty(istanzeprocure);
	model.addAttribute("istanzeprocure", istanzeprocure);
	setDisplay(model, BaseCommand.NEW);
	setPageAttributes(model);
	return "istanzeprocure/form";
    }

    private void setDisplay(Model model, int mode) {

	BaseCommand c = new BaseCommand();
	c.setDisplayMode(mode);
	model.addAttribute("displayCommand", c);
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzeprocure") Istanzeprocure istanzeprocure, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(istanzeprocure);
	checkAccessoInformazioni(istanzeprocure.getIstanze(), true);
	try {
	    istanzeprocureService.insert(istanzeprocure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeprocure, e);
	    fixRenderEntityProperty(istanzeprocure);
	    setDisplay(model, BaseCommand.NEW);
	    return "istanzeprocure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeprocure.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Istanzeprocure istanzeprocure = istanzeprocureService.findById(id);
	checkAccessoInformazioni(istanzeprocure.getIstanze(), false);
	fixRenderEntityProperty(istanzeprocure);
	model.addAttribute("istanzeprocure", istanzeprocure);
	setPageAttributes(model);
	setDisplay(model, BaseCommand.EDIT);
	return "istanzeprocure/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("istanzeprocure") Istanzeprocure istanzeprocure, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	checkAccessoInformazioni(istanzeprocure.getIstanze(), true);
	fixMergeEntityProperty(istanzeprocure);
	try {
	    loggaCancellazioneOggettoIstanza(request);
	    istanzeprocureService.update(istanzeprocure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeprocure, e);
	    fixRenderEntityProperty(istanzeprocure);
	    setDisplay(model, BaseCommand.EDIT);
	    return "istanzeprocure/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeprocure.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("istanzeprocure") Istanzeprocure istanzeprocure, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanzeprocure objToDelete = istanzeprocureService.findById(istanzeprocure.getId());
	checkAccessoInformazioni(istanzeprocure.getIstanze(), true);
	if (StringUtils.isNotBlank(objToDelete.getStcIdAllegato()) || StringUtils.isNotBlank(objToDelete.getStcIdDocumento())) {
	    Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	    boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false : userlogged.getFlagCancelladocumentistc()
		    .booleanValue();
	    if (!isCancellaMail) {
		String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
		throw new SecurityException(messaggioErrore);
	    }
	}
	// Integer codiceIstanza = istanzeprocure.getIstanze().getId().getCodice();
	String responsabile = getCurrentlyAuthenticatedUserDetails().toString();
	String descrizioneIstanza = objToDelete.getIstanze().toString();
	String descrizioneProcura = getDescrizioneProcura(objToDelete);
	try {
	    istanzeprocureService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeprocure, e);
	    fixRenderEntityProperty(istanzeprocure);
	    setDisplay(model, BaseCommand.EDIT);
	    return "istanzeprocure/form";
	}
	LoggerCancellazioni.logCancellazioneProcura(responsabile, descrizioneProcura, descrizioneIstanza);
	status.setComplete();
	return "redirect:../history/back.htm?GoTo=%2F";
    }

    @RequestMapping
    public void ajaxChangeValueFieldValido(@RequestParam("codice") Integer codice, @RequestParam("valido") Integer valido, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Istanzeprocure istanzeprocure = istanzeprocureService.findById(id);
	try {
	    //checkAccessoInformazioni(documentiistanza.getIstanza(), true);
	    istanzeprocure.setControllook(valido);
	    istanzeprocureService.update(istanzeprocure);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxAbilitaDisabilitaNecessario(@RequestParam("codice") Integer codice, @RequestParam("necessario") Boolean necessario, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Istanzeprocure istanzeprocure = istanzeprocureService.findById(id);
	try {
	    // checkAccessoInformazioni(istanzeallegati.getIstanza(), true);
	    istanzeprocure.setNecessario(necessario);
	    istanzeprocureService.update(istanzeprocure);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    /**
     * Il metodo attraverso una chiamata ajax va a modificare il checkbox selzionato
     * 
     * @param model
     * @param presentato
     * @param verificato
     * @param valido
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public void ajxaChangeCheckboxvalue(Model model, @RequestParam(value = "codice") String codice,
	    @RequestParam(value = "presentato", required = false) String presentato, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Istanzeprocure istanzeprocure = istanzeprocureService.findById(new PkId(Integer.parseInt(codice)));
	try {
	    //checkAccessoInformazioni(istanzeallegati.getIstanza(), true);
	    // Logica: se arriva il flag diverso da null allora se:
	    // 1 - E' false significa che il cambiamento deve essere a true
	    // 2 - E' true significa che il cambiamento deve essere a false
	    //(il valore che ci arriva dalla jsp è il valore che è sul DB quindi se noi siamo nella funzionalità di cambiamento
	    //significa che dovremmo andare asalvare il valore opposto)
	    if (StringUtils.isNotBlank(presentato)) {
		boolean value = (presentato.equals("true") ? false : true);
		istanzeprocure.setPresente(Boolean.valueOf(value));
	    }
	    istanzeprocureService.update(istanzeprocure);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    private String getDescrizioneProcura(Istanzeprocure istanzeprocure) {

	String descrizioneProcura = "";
	if (istanzeprocure.getAnagrafeProcuratore() != null) {
	    descrizioneProcura += "P: " + istanzeprocure.getAnagrafeProcuratore().getDescrizioneRichiedente();
	}
	if (istanzeprocure.getAnagrafeRappresentato() != null) {
	    descrizioneProcura += "-R: " + istanzeprocure.getAnagrafeRappresentato().getDescrizioneRichiedente();
	}
	if (istanzeprocure.getOggetti() != null) {
	    descrizioneProcura += "-F: " + istanzeprocure.getOggetti().getNomefile();
	}
	if (StringUtils.isNotBlank(istanzeprocure.getStcIdAllegato())) {
	    descrizioneProcura += "-SIA: " + istanzeprocure.getStcIdAllegato();
	}
	if (StringUtils.isNotBlank(istanzeprocure.getStcIdDocumento())) {
	    descrizioneProcura += "-SID: " + istanzeprocure.getStcIdDocumento();
	}
	return descrizioneProcura;
    }

    @Override
    protected void fixMergeEntityProperty(Istanzeprocure entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeprocure entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
	if (entity.getIstanze() == null) {
	    entity.setIstanze(new Istanze());
	}
	if (entity.getAnagrafeRappresentatoStorico() == null) {
	    entity.setAnagrafeRappresentatoStorico(new Anagrafestorico());
	}
	if (entity.getAnagrafeProcuratoreStorico() == null) {
	    entity.setAnagrafeProcuratoreStorico(new Anagrafestorico());
	}
	if (entity.getAnagrafeProcuratore() == null) {
	    entity.setAnagrafeProcuratore(new Anagrafe());
	}
	if (entity.getAnagrafeRappresentato() == null) {
	    entity.setAnagrafeRappresentato(new Anagrafe());
	}
	if (entity.getOggettiDocIdent() == null) {
	    entity.setOggettiDocIdent(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("userlogged", resp);
    }
}
