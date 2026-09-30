/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ProcedimentoProcediMarche;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ProcediMarcheCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.TipoProcedimentoSpecifico;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.ProcediMarcheProxyService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * @author francol
 *
 */
@Controller
@SessionAttributes("pmCommand")
public class ProcediMarcheController extends BaseController<ProcediMarcheCommand> {

    private static final Logger log = LoggerFactory.getLogger(ProcediMarcheController.class);
    @Autowired
    private ProcediMarcheProxyService procediMarcheProxyService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @ModelAttribute(value = "pmCommand")
    ProcediMarcheCommand getCommand(HttpSession ses) {

	ProcediMarcheCommand cmd = (ProcediMarcheCommand) ses.getAttribute("pmCommand");
	if (cmd == null) {
	    cmd = new ProcediMarcheCommand();
	    ses.setAttribute("pmCommand", cmd);
	}
	cmd.setCanEdit(this.userHasRole(false, RuoliUtentiEnum.GESTIONE_PROCEDIMARCHE.name()));
	return cmd;
    }

    @RequestMapping
    public String list(@RequestParam(value = "refresh", required = false) Boolean refresh, Model model,
	    @ModelAttribute("pmCommand") ProcediMarcheCommand command, BindingResult result, SessionStatus status, HttpServletRequest request) {

	String message = null;
	if ((command != null && (command.getElencoProcedimenti() == null || command.getElencoProcedimenti().isEmpty()))
		|| BooleanUtils.isTrue(refresh)) {
	    try {
		List<ProcedimentoProcediMarche> lista = this.procediMarcheProxyService.getListaProcedimenti();
		command.setElencoProcedimenti(lista);
	    } catch (Exception e) {
		message = "Si è verificato un errore che ha impedito il caricamento della lista: " + StringUtils.defaultString(e.getMessage());
		log.error("list - " + message, e);
	    }
	}
	//tornando alla lista il procedimento corrente viene reimpostato a null
	if (command.getProcedimento() != null) {
	    command.setProcedimento(null);
	}
	if (StringUtils.isNotBlank(message)) {
	    FlashMessages.getWarnings().add(message);
	}
	return "procedimarche/list";
    }

    @RequestMapping
    public String view(Model model, @RequestParam(value = "idProc") Integer idProc, @ModelAttribute("pmCommand") ProcediMarcheCommand command,
	    BindingResult result) {

	String message = null;
	ProcedimentoProcediMarche ppm = command.getProcedimento();
	//se si ricarica la stessa pagina o se si proviene dal redirect delle chiamate a collagaProcedimento i dati di PM sono già presenti e il servizio non viene più invocato
	if (ppm == null || ppm.getDatiProcedimento() == null || !idProc.equals(ppm.getDatiProcedimento().getId())) {
	    try {
		ppm = procediMarcheProxyService.getProcedimento(idProc);
		command.setProcedimento(ppm);
		this.fixRenderEntityProperty(command);
		this.popolaListe(command);
	    } catch (Exception e) {
		message = "Si è verificato un'errore nella lettura dei dati regionali del procedimento: " + StringUtils.defaultString(e.getMessage());
		log.error("view - " + message, e);
	    }
	}
	if (StringUtils.isNotBlank(message)) {
	    FlashMessages.getWarnings().add(message);
	}
	/*
	 * test visualizzazione dati locali TipoProcedimentoSpecifico tps = new TipoProcedimentoSpecifico();
	 * tps.setIdTipoProcedimentoGenerico(4); tps.setIdSerieArchivistica(3); ppm.setDatiSpecifici(tps);
	 *///end test
	model.addAttribute("pmCommand", command);
	return "procedimarche/form";
    }

    @RequestMapping
    public String collegaProcedimento(Model model, @RequestParam("idInvproc") Integer idInvproc,
	    @ModelAttribute("pmCommand") ProcediMarcheCommand command) {

	String message = "";
	ProcedimentoProcediMarche ppm = null;
	Integer idPm = null;
	TipoProcedimentoSpecifico tps = new TipoProcedimentoSpecifico();
	if (command.getProcedimento() != null && command.getProcedimento().getDatiProcedimento() != null
		&& command.getProcedimento().getDatiProcedimento().getId() != null) {
	    try {
		idPm = command.getProcedimento().getDatiProcedimento().getId();
		tps.setIdProcedimentoEnte(idInvproc.toString());
		tps.setIdTipoProcedimentoGenerico(idPm);
		ppm = this.procediMarcheProxyService.collegaProcedimento(tps);
		message = "Operazione effettuata correttamente";
	    } catch (Exception e) {
		String cause = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString();
		message = "Impossibile collegare il procedimento locale ai dati di ProcediMarche a causa dell'errore: " + cause;
		log.error("collegaProcedimento - " + message, e);
	    }
	    if (ppm != null) {
		command.getProcedimento().setDatiCollegamento(ppm.getDatiCollegamento());
		command.getProcedimento().setDatiSpecifici(ppm.getDatiSpecifici());
		//svuoto l'elenco procedimenti nel command in sessione per forzare il refresh della list.htm all'uscita
		command.setElencoProcedimenti(null);
	    }
	} else {
	    message = "Impossibile identificare il procedimento di ProcediMarche a cui collegare il procedimento VBG";
	}
	List<String> msgs = new ArrayList<String>();
	if (StringUtils.isNotBlank(message)) {
	    msgs.add(message);
	    if (ppm != null) {
		FlashMessages.setInfos(msgs);
	    } else {
		FlashMessages.setWarnings(msgs);
	    }
	}
	model.addAttribute("pmCommand", command);
	String landing = idPm == null ? "redirect:list.htm" : "redirect:view.htm?idProc=" + idPm;
	return landing;
    }

    @RequestMapping
    public String spubblicaProcedimento(Model model, @RequestParam("idStpEndo") Integer idStpEndo,
	    @ModelAttribute("pmCommand") ProcediMarcheCommand command) {

	String message = null;
	Integer idPm = null;
	if (command.getProcedimento() != null && command.getProcedimento().getDatiProcedimento() != null && idStpEndo != null) {
	    try {
		idPm = command.getProcedimento().getDatiProcedimento().getId();
		this.procediMarcheProxyService.spubblicaProcedimento(command.getProcedimento());
	    } catch (Exception e) {
		String cause = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString();
		message = "Impossibile scollegare il procedimento locale dai dati di ProcediMarche a causa dell'errore: " + cause;
		log.error("scollegaProcedimento - " + message, e);
	    }
	} else {
	    message = "Non è stato specificato il procedimento da eliminare";
	}
	List<String> msgs = new ArrayList<String>();
	if (message == null) {
	    msgs.add("Operazione effettuata correttamente");
	    FlashMessages.setInfos(msgs);
	} else {
	    msgs.add(message);
	    FlashMessages.setWarnings(msgs);
	}
	model.addAttribute("pmCommand", command);
	String landing = idPm == null ? "redirect:list.htm" : "redirect:view.htm?idProc=" + idPm;
	return landing;
    }

    @RequestMapping
    public String scollegaProcedimento(Model model, @RequestParam("idStpEndo") Integer idStpEndo,
	    @ModelAttribute("pmCommand") ProcediMarcheCommand command) {

	String message = null;
	Integer idPm = null;
	if (command.getProcedimento() != null && command.getProcedimento().getDatiProcedimento() != null && idStpEndo != null) {
	    try {
		idPm = command.getProcedimento().getDatiProcedimento().getId();
		this.procediMarcheProxyService.scollegaProcedimento(idStpEndo);
		command.getProcedimento().setDatiCollegamento(null);
		command.getProcedimento().setDatiSpecifici(null);
		//svuoto l'elenco procedimenti nel command in sessione per forzare il refresh della list.htm all'uscita
		command.setElencoProcedimenti(null);
	    } catch (Exception e) {
		String cause = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString();
		message = "Impossibile scollegare il procedimento locale dai dati di ProcediMarche a causa dell'errore: " + cause;
		log.error("scollegaProcedimento - " + message, e);
	    }
	} else {
	    message = "Non è stato specificato il collegamento da cancellare";
	}
	List<String> msgs = new ArrayList<String>();
	if (message == null) {
	    msgs.add("Operazione effettuata correttamente");
	    FlashMessages.setInfos(msgs);
	} else {
	    msgs.add(message);
	    FlashMessages.setWarnings(msgs);
	}
	model.addAttribute("pmCommand", command);
	String landing = idPm == null ? "redirect:list.htm" : "redirect:view.htm?idProc=" + idPm;
	return landing;
    }

    @RequestMapping
    public String salvaProcedimento(Model model, @RequestParam(value = "pubblica", required = false) Boolean pubblica,
	    @ModelAttribute(value = "pmCommand") ProcediMarcheCommand command) {

	String message = null;
	String action = "pubblicare su ProcediMarche";
	Integer idPm = null;
	ProcedimentoProcediMarche ppm = command.getProcedimento();
	if (ppm != null && ppm.getDatiSpecifici() != null) {
	    if (ppm.getDatiProcedimento() != null) {
		idPm = ppm.getDatiProcedimento().getId();
	    }
	    try {
		if (pubblica) {
		    message = this.validaDatiMinimiPubblicazione(ppm.getDatiSpecifici());
		    if (StringUtils.isBlank(message)) {
			this.procediMarcheProxyService.pubblicaProcedimento(ppm);
		    } else {
			this.procediMarcheProxyService.salvaDatiLocaliProcedimento(ppm);
			message = "Impossibile " + action + " il procedimento locale a causa dell'errore: " + message;
		    }
		} else {
		    action = "salvare";
		    this.procediMarcheProxyService.salvaDatiLocaliProcedimento(ppm);
		}
	    } catch (Exception e) {
		String cause = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.toString();
		message = "Impossibile " + action + " il procedimento locale a causa dell'errore: " + cause;
		log.error("pubblicaProcedimento - " + message, e);
	    }
	} else {
	    message = "Non sono stati trasmessi i dati del procedimento da " + action;
	}
	List<String> msgs = new ArrayList<String>();
	if (message == null) {
	    msgs.add("Operazione effettuata correttamente");
	    FlashMessages.setInfos(msgs);
	} else {
	    msgs.add(message);
	    FlashMessages.setWarnings(msgs);
	}
	model.addAttribute("pmCommand", command);
	String landing = idPm == null ? "redirect:list.htm" : "redirect:view.htm?idProc=" + idPm;
	return landing;
    }

    private String validaDatiMinimiPubblicazione(TipoProcedimentoSpecifico datiProc) {

	List<String> errors = new ArrayList<String>();
	//procedimarche solleva obiezioni se non si passa il tipo fascicolazione
	if (datiProc != null) {
	    if (datiProc.getIdTipoFascicolo() == null) {
		errors.add("tipo fascicolazione obbligatoria");
	    }
	    if (StringUtils.isBlank(datiProc.getNomeProcedimentoEnte())) {
		errors.add("nome del procedimento obbligatorio");
	    }
	    if (StringUtils.isBlank(datiProc.getUoCompetenzaIstruttoria())) {
		errors.add("stuttura responsabile dell'istruttoria obbligatoria");
	    }
	    if (StringUtils.isBlank(datiProc.getUoRecapitiIstruttoria())) {
		errors.add("recapiti del responsabile dell'istruttoria");
	    }
	    if (StringUtils.isBlank(datiProc.getResponsabileNome())) {
		errors.add("nome del responsabile del procedimento obbligatorio");
	    }
	    if (StringUtils.isBlank(datiProc.getResponsabileCognome())) {
		errors.add("cognome del responsabile del procedimento obbligatorio");
	    }
	    if (StringUtils.isBlank(datiProc.getLinkServizio())) {
		errors.add("link al servizio online obbligatorio");
	    }
	}
	if (!errors.isEmpty()) {
	    return StringUtils.join(errors.iterator(), ", ");
	} else {
	    return null;
	}
    }

    @RequestMapping
    public void findDescrizioneEstesaAmministrazione(@RequestParam("codiceAmministrazione") Integer codiceAmministrazione, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	log.debug("findDescrizioneEstesaAmministrazione({})", codiceAmministrazione);
	Amministrazioni amm = amministrazioniService.findById(new PkId(codiceAmministrazione));
	String val = "";
	if (amm != null) {
	    val = amm.getDescrizioneEstesa();
	}
	//response.setContentType("text/html");
	response.setContentType("text/plain");
	response.getWriter().write(val);
    }

    @RequestMapping
    public void findRecapitiIstruttoriaAmministrazione(@RequestParam("codiceAmministrazione") Integer codiceAmministrazione,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("findRecapitiIstruttoriaAmministrazione({})", codiceAmministrazione);
	Amministrazioni amm = amministrazioniService.findById(new PkId(codiceAmministrazione));
	AmministrazioniHelper ah = new AmministrazioniHelper();
	ah.setAmministrazioni(amm);
	String info = ah.getRecapitiReferenteIstruttoria();
	response.setContentType("text/plain");
	response.getWriter().write(info);
    }

    @RequestMapping
    public void findResponsabile(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	log.debug("findResponsabile({})", codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	StringBuffer buffer = new StringBuffer();
	if (!EntityUtils.isNestedPropertyBlank(alberoproc, "responsabile.responsabile")) {
	    buffer.append(alberoproc.getResponsabile().getResponsabile());
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findTermineConclusione(@RequestParam("codiceInventarioProc") Integer codiceInventarioProc, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	log.debug("findTermineConclusione({})", codiceInventarioProc);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceInventarioProc));
	StringBuffer buffer = new StringBuffer();
	if (!EntityUtils.isNestedPropertyBlank(inventarioprocedimenti, "tempificazione.tempificazione")) {
	    buffer.append(inventarioprocedimenti.getTempificazione().getTempificazione());
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findNomeProcedimento(@RequestParam("codiceInventarioProc") Integer codiceInventarioProc, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	log.debug("findNomeProcedimento({})", codiceInventarioProc);
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceInventarioProc));
	String nomeProc = StringUtils.defaultString(inventarioprocedimenti.getProcedimento());
	response.setContentType("text/plain");
	response.getWriter().write(nomeProc);
    }

    @RequestMapping
    public void findMaxGiorniTermine(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	log.debug("findMaxGiorniTermine({})", codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	StringBuffer buffer = new StringBuffer();
	if (!EntityUtils.isNestedPropertyBlank(alberoproc, "tipoProcedura.giorni")) {
	    buffer.append(alberoproc.getTipoProcedura().getGiorni());
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findUrlProcedimentoFrontOffice(@RequestParam("idproc") Integer idProc, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	log.debug("findUrlProcedimentoFrontOffice({})", idProc);
	response.setContentType("text/plain");
	response.getWriter().write(this.procediMarcheProxyService.buildUrlNuovaIstanzaFrontOffice(idProc));
    }

    private void popolaListe(ProcediMarcheCommand cmd) {

	if (cmd != null) {
	    if (cmd.getSerieArchivistiche() == null || cmd.getSerieArchivistiche().isEmpty()) {
		cmd.setSerieArchivistiche(this.procediMarcheProxyService.getSerieArchivistiche());
	    }
	    if (cmd.getTipiFascicolo() == null || cmd.getTipiFascicolo().isEmpty()) {
		cmd.setTipiFascicolo(this.procediMarcheProxyService.getTipiFascicolo());
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(ProcediMarcheCommand cmd) {

    }

    @Override
    protected void fixRenderEntityProperty(ProcediMarcheCommand cmd) {

	if (cmd.getProcedimento() != null) {
	    if (cmd.getProcedimento().getDatiCollegamento() != null) {
		Inventarioprocedimenti iproc = cmd.getProcedimento().getDatiCollegamento().getInventarioprocedimenti();
		if (iproc != null) {
		    if (iproc.getAmministrazioni() == null) {
			iproc.setAmministrazioni(new Amministrazioni());
		    } else if (iproc.getAmministrazioni().getId() == null) {
			iproc.getAmministrazioni().setId(new PkId());
		    }
		}
		Alberoproc aproc = cmd.getProcedimento().getDatiCollegamento().getAlberoproc();
		// è sufficiente invocare .getAlberoproc() per far sì che hibernate inizializzi il proxy
		/*
		if(aproc != null) {
		    aproc.getDescrizioneCompleta();
		}
		*/
	    }
	}
    }
}
