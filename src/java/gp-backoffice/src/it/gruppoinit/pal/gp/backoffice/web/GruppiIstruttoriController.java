package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "gruppiistruttori", "istanza" })
public class GruppiIstruttoriController extends BaseController<GruppiIstruttori> {

    private static final Logger log = LoggerFactory.getLogger(GruppiIstruttoriController.class);
    @Autowired
    private GruppiIstruttoriService gruppiistruttoriService;
    @Autowired
    private GruppiIstruttoriRespService gruppiIstruttoriRespService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private MailtipoService mailtipoService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<GruppiIstruttori> gruppiistruttoriList = gruppiistruttoriService.findAll(null, null);
	ModelMap model = new ModelMap(gruppiistruttoriList);
	boolean export = createJMesaExport(request, response, gruppiistruttoriList);
	if (export) {
	    return null;
	}
	model.addAttribute("gruppiistruttoriList", gruppiistruttoriList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	GruppiIstruttori gruppiistruttori = new GruppiIstruttori();
	gruppiistruttori.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(gruppiistruttori);
	model.addAttribute("gruppiistruttori", gruppiistruttori);
	setPageAttributes(model);
	return "gruppiistruttori/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("gruppiistruttori") GruppiIstruttori gruppiistruttori, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(gruppiistruttori);
	gruppiistruttori.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    gruppiistruttoriService.insert(gruppiistruttori);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiistruttori, e);
	    fixRenderEntityProperty(gruppiistruttori);
	    return "gruppiistruttori/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + gruppiistruttori.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	GruppiIstruttori gruppiistruttori = gruppiistruttoriService.findById(id);
	fixRenderEntityProperty(gruppiistruttori);
	model.addAttribute("gruppiistruttori", gruppiistruttori);
	setPageAttributes(model);
	return "gruppiistruttori/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("gruppiistruttori") GruppiIstruttori gruppiistruttori, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(gruppiistruttori);
	try {
	    gruppiistruttoriService.update(gruppiistruttori);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, gruppiistruttori, e);
	    fixRenderEntityProperty(gruppiistruttori);
	    return "gruppiistruttori/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + gruppiistruttori.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("gruppiistruttori") GruppiIstruttori gruppiistruttori, BindingResult result, SessionStatus status) {

	GruppiIstruttori objToDelete = gruppiistruttoriService.findById(gruppiistruttori.getId());
	try {
	    gruppiistruttoriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(gruppiistruttori);
	    return "gruppiistruttori/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxCreateRicercaIstruttore(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(required = false, value = "isModifica") Boolean modifica, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// Controllo se sono in fase di assegazione manuale da gruppo o 
	//stiamo modificando l'istruttore dopo l'assegnazione
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	List<GruppiIstruttoriResp> listIstruttori = gruppiIstruttoriRespService
		.findByGruppoIstruttori(istanza.getGruppiIstruttori().getId().getCodice(), true, true, null, null);
	response.setContentType("text/plain");
	model.addAttribute("listIstruttori", listIstruttori);
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("istanza", istanza);
	model.addAttribute("isModifica", BooleanUtils.toBoolean(modifica));
	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE);
	boolean modifica_istr_da_lista = false;
	Verticalizzazioniparametri verticalizzazioniparametri = null;
	if (isAttiva) {
	    verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE,
		    WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE_MODIFICA_ISTR_DA_LISTA_COMPL, ORMHelper.getSoftware());
	}
	if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
	    if (verticalizzazioniparametri.getValore().equals("1")) {
		modifica_istr_da_lista = true;
	    }
	}
	model.addAttribute("modifica_istr_da_lista", modifica_istr_da_lista);
	return "gruppiistruttori/createSearchIstruttori";
    }

    //    @RequestMapping
    //    public String ajaxCreateRicercaIstruttoreDaInteraLista(@RequestParam("codiceIstanza") Integer codiceIstanza,
    //	    @RequestParam(required = false, value = "isModifica") Boolean modifica, Model model, HttpServletRequest request,
    //	    HttpServletResponse response) throws IOException {
    //
    //	// Controllo se sono in fase di assegazione manuale da gruppo o 
    //	//stiamo modificando l'istruttore dopo l'assegnazione
    //	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
    //	List<Responsabili> listIstruttori = responsabiliService.findResponsabiliIstruttoria(new Responsabili());
    //	//List<GruppiIstruttoriResp> listIstruttori = gruppiIstruttoriRespService.findByGruppoIstruttori(istanza.getGruppiIstruttori().getId()
    //	//	.getCodice(), true, true, null, null);
    //	response.setContentType("text/plain");
    //	model.addAttribute("listIstruttori", listIstruttori);
    //	model.addAttribute("istanza", istanza);
    //	model.addAttribute("codiceIstanza", codiceIstanza);
    //	model.addAttribute("isModifica", BooleanUtils.toBoolean(modifica));
    //	return "gruppiistruttori/createSearchIstruttoriDaInteraLista";
    //    }
    @RequestMapping
    public String assegnaIstruttoreTempAdIstanza(@RequestParam("codiceIstanza") String codiceIstanza,
	    @RequestParam("codiceIstruttore") Integer codiceIstruttore, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(codiceIstanza)));
	Responsabili istruttoreTemp = responsabiliService.findById(new PkId(codiceIstruttore));
	if (!BooleanUtils.toBoolean(istanza.getGrpFlagAccettazione())) {
	    // Non è richiesta l'accettazione, quindi lo metto come istruttore
	    istanza.setIstruttore(istruttoreTemp);
	}
	istanza.setIstruttoreTemp(istruttoreTemp);
	try {
	    istanzeService.update(istanza);
	    istanzeService.sendEmailNoticheFunzionalitaAntiCorruzione(istanza, istruttoreTemp, false, false, true);
	} catch (Exception e) {
	    log.error("assegnaIstruttoreTempAdIstanza# Errore durante l'invio della email di assegnazione pratica ad istruttore: {}[{}]",
		    new Object[] { e.getMessage(), e });
	    FlashMessages.getWarnings().add("Errore durante l'invio della email di assegnazione pratica ad istruttore:" + e.getMessage());
	    return "redirect:../istanze/view.htm?codice=" + Integer.parseInt(codiceIstanza);
	}
	return "redirect:../istanze/view.htm?codice=" + Integer.parseInt(codiceIstanza);
    }

    @RequestMapping
    public String modificaIstruttoreAdIstanza(@RequestParam("codiceIstanza") String codiceIstanza,
	    @RequestParam("codiceIstruttore") Integer codiceIstruttore, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Istanze istanze = null;
	Responsabili istruttore = null;
	Integer _codiceIstanza = Integer.parseInt(codiceIstanza);
	try {
	    istanzeService.updateIstruttore(_codiceIstanza, codiceIstruttore, true);
	} catch (RuntimeException re) {
	    log.error("modificaIstruttoreAdIstanza# Errore durante l'aggiornamento dell' istruttore: {}[{}]", new Object[] { re.getMessage(), re });
	    FlashMessages.getWarnings().add(" Errore durante l'aggiornamento dell' istruttore:" + re.getMessage());
	    return "redirect:../istanze/view.htm?codice=" + Integer.parseInt(codiceIstanza);
	}
	try {
	    istanze = istanzeService.findById(new PkId(_codiceIstanza));
	    istruttore = responsabiliService.findById(new PkId(codiceIstruttore));
	    istanzeService.sendEmailNoticheFunzionalitaAntiCorruzione(istanze, istruttore, false, false, true);
	} catch (Exception e) {
	    log.error("modificaIstruttoreAdIstanza# Errore durante l'invio della email di assegnazione pratica ad istruttore: {}[{}]",
		    new Object[] { e.getMessage(), e });
	    FlashMessages.getWarnings().add("Errore durante l'invio della email di assegnazione pratica ad istruttore:" + e.getMessage());
	    return "redirect:../istanze/view.htm?codice=" + Integer.parseInt(codiceIstanza);
	}
	return "redirect:../istanze/view.htm?codice=" + Integer.parseInt(codiceIstanza);
    }

    @RequestMapping
    public String ajaxAccettazioneruoloIstruttore(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	Verticalizzazioniparametri verticalizzazioniparametri = null;
	boolean isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE);
	Mailtipo mTipo = new Mailtipo();
	if (isAttiva) {
	    verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE,
		    WebConstants.VERTICALIZZAZIONE_ANTICORRUZIONE_TESTO_TIPO_PRESA_IN_CARICO, ORMHelper.getSoftware());
	}
	if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
	    try {
		mTipo = mailtipoService.findById(new PkId(Integer.parseInt(verticalizzazioniparametri.getValore())));
	    } catch (NumberFormatException ne) {
		log.error("ajaxAccettazioneruoloIstruttore#Il codice salvato in verticalizzazione non è un intero ");
	    }
	    if (EntityUtils.getNestedProperty(mTipo, "id.codice") != null) {
		mTipo = mailtipoService.replaceOggettoCorpo(mTipo, istanza, null);
	    }
	}
	if (mTipo != null && StringUtils.isBlank(mTipo.getCorpo())) {
	    String messDefault = "Attenzione, non è stato configurato il testo da visualizzare. " +
		    "Andare nella sezione regole e impostare il codice <b>del testo " +
		    "tipo</b> nel parametro <b>TESTO_TIPO_PRESA_IN_CARICO</b> della regola <b>ANTI_CORRUZIONE</b>";
	    mTipo.setCorpo(messDefault);
	}
	response.setContentType("text/plain");
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("mTipo", mTipo);
	return "gruppiistruttori/createAccettazioneRuoloIstruttore";
    }

    @RequestMapping
    public String accettaOrRigettaAssegnazione(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("accetta") Boolean accetta,
	    @RequestParam("rigetta") Boolean rigetta, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    istanzeService.accettaOrRifiutaRuoloIstruttore(codiceIstanza, getCurrentlyAuthenticatedUserDetails(), accetta, rigetta);
	} catch (RuntimeException e) {
	    log.error("accettaOrRigettaAssegnazione# Errore durante l'operazione di accettazione: {}[{}]", new Object[] { e.getMessage(), e });
	    FlashMessages.getWarnings().add("Errore durante l'operazione di accettazione:" + e.getMessage());
	    return "redirect:../istanze/view.htm?codice=" + codiceIstanza;
	}
	if (accetta) {
	    return "redirect:../istanze/view.htm?codice=" + codiceIstanza;
	}
	return "redirect:../istanze/searchIstanze.htm";
    }

    @Override
    protected void fixMergeEntityProperty(GruppiIstruttori entity) {

    }

    @Override
    protected void fixRenderEntityProperty(GruppiIstruttori entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
