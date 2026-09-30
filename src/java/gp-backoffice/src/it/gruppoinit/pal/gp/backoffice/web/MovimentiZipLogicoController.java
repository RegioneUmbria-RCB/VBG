package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

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

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.MovimentiZipLogicoCommand;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoTestataHelper;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.ZipLogicoLinkHelper;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes("movimentiZipLogicoCommand")
public class MovimentiZipLogicoController extends BaseController<MovimentiZipLogico> {

    private static final Logger log = LoggerFactory.getLogger(MovimentiZipLogicoController.class);
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    private DocumentiHelperService documentiHelperService;
    @Autowired
    private MovimentiService movimentiService;

    @RequestMapping
    public String list(@RequestParam("codicemovimento") Integer codiceMovimento, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	MovimentiZipLogicoCommand command = new MovimentiZipLogicoCommand();
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	checkAccessoMovimento(movimento);
	Integer codiceistanza = movimento.getIstanza().getId().getCodice();
	DocumentiHelper documentiHelper = null;
	Boolean zipLogicoEsistente = this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codiceMovimento);
	if (zipLogicoEsistente) {
	    documentiHelper = documentiHelperService.findDocumentiDaAggiungereAZipLogico(codiceistanza, codiceMovimento);
	} else {
	    documentiHelper = documentiHelperService.findDocumentiDownloadZip(codiceistanza);
	}
	command.setDisplayMode(MovimentiZipLogicoCommand.NEW);
	command.getEntity().setMovimenti(movimento);
	command.setDocumentiHelper(documentiHelper);
	model.addAttribute("movimentiZipLogicoCommand", command);
	model.addAttribute("zipLogicoEsistente", zipLogicoEsistente);
	return "movimentiziplogico/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("movimentiZipLogicoCommand") MovimentiZipLogicoCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {

	Integer codiceMovimento = command.getEntity().getMovimenti().getId().getCodice();
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	checkAccessoMovimento(movimento);
	try {
	    movimentiZipLogicoService.insertZipLogico(codiceMovimento, command.getDocumentiHelper());
	    String msg = "#ZIP-LOGICO-INSERIMENTO# L'operatore [" + getCurrentlyAuthenticatedUserDetails().getResponsabile() +
			 "] ha effettuato un inserimento di uno o più documenti oppure dell'intero zip logico del movimento" +
			 command.getEntity().getMovimenti() + " in data " + new Date();
	    LoggerCancellazioni.log(msg);
	} catch (Exception ex) {
	    log.error("insert#: Errore di inserimento dello zip logico {}", ex);
	    copyErrorsToBindingResult(result, command.getEntity(), true, ex);
	    fixRenderEntityProperty(command.getEntity());
	    movimento = movimentiService.findById(new PkId(codiceMovimento));
	    DocumentiHelper documentihelper = null;
	    Boolean zipLogicoEsistente = this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codiceMovimento);
	    if (zipLogicoEsistente) {
		documentihelper = documentiHelperService.findDocumentiDaAggiungereAZipLogico(movimento.getIstanza().getId().getCodice(),
			codiceMovimento);
	    } else {
		documentihelper = documentiHelperService.findDocumentiDownloadZip(movimento.getIstanza().getId().getCodice());
	    }
	    command.setDisplayMode(MovimentiZipLogicoCommand.NEW);
	    command.getEntity().setMovimenti(movimento);
	    command.setDocumentiHelper(documentihelper);
	    model.addAttribute("movimentiZipLogicoCommand", command);
	    model.addAttribute("zipLogicoEsistente", zipLogicoEsistente);
	    return "movimentiziplogico/form";
	}
	return "redirect:view.htm?codicemovimento=" + codiceMovimento + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codicemovimento") Integer codiceMovimento, Model model, HttpServletRequest request) throws Exception {

	MovimentiZipLogicoTestataHelper testata = this.movimentiZipLogicoService.findByCodiceMovimento(codiceMovimento);
	MovimentiZipLogicoCommand command = new MovimentiZipLogicoCommand();
	DocumentiHelper documentiHelper = movimentiZipLogicoService.findDocumentiZipLogicoToDisplay(codiceMovimento);
	Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
	command.getEntity().setMovimenti(movimenti);
	command.setDisplayMode(MovimentiZipLogicoCommand.VIEW);
	command.setDocumentiHelper(documentiHelper);
	command.setTestata(testata);
	setPageAttributes(model, command);
	return "movimentiziplogico/form";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("movimentiZipLogicoCommand") MovimentiZipLogicoCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// 1. Recupero la lista dei documenti dello zip da eliminare 
	Integer codicemovimento = command.getEntity().getMovimenti().getId().getCodice();
	String redirectTo = "view.htm?codicemovimento=" + codicemovimento;
	Movimenti movimento = movimentiService.findById(new PkId(codicemovimento));
	checkAccessoMovimento(movimento);
	try {
	    this.movimentiZipLogicoService.deleteDettagli(codicemovimento, command.getDocumentiHelper());
	    if (!this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codicemovimento)) {
		redirectTo = "list.htm?codicemovimento=" + codicemovimento;
	    }
	    String msg = "#ZIP-LOGICO-ELIMINAZIONE# L'operatore [" + getCurrentlyAuthenticatedUserDetails().getResponsabile() +
			 "] ha effettuato l'eliminazione di uno o più documenti oppure dell'intero zip logico del movimento " +
			 command.getEntity().getMovimenti() + " in data " + new Date();
	    LoggerCancellazioni.log(msg);
	} catch (Exception ex) {
	    log.error("delete#: Errore di eliminazione dello zip logico {}", ex);
	    copyErrorsToBindingResult(result, command.getEntity(), true, ex);
	    fixRenderEntityProperty(command.getEntity());
	    DocumentiHelper documentihelper = movimentiZipLogicoService.findDocumentiZipLogicoToDisplay(codicemovimento);
	    command.setDisplayMode(MovimentiZipLogicoCommand.VIEW);
	    Movimenti movimenti = movimentiService.findById(new PkId(codicemovimento));
	    command.getEntity().setMovimenti(movimenti);
	    command.setDocumentiHelper(documentihelper);
	    setPageAttributes(model, command);
	    return "movimentiziplogico/form";
	}
	return "redirect:" + redirectTo;
    }

    @RequestMapping
    public String disassociaAllegato(Model model, @ModelAttribute("movimentiZipLogicoCommand") MovimentiZipLogicoCommand command,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {

	Integer codicemovimento = command.getEntity().getMovimenti().getId().getCodice();
	String redirectTo = "view.htm?codicemovimento=" + codicemovimento;
	Movimenti movimento = movimentiService.findById(new PkId(codicemovimento));
	checkAccessoMovimento(movimento);
	MovimentiZipLogicoTestata testata = movimentiZipLogicoService.findTestataByCodiceMovimento(codicemovimento);
	boolean checkIsModificabile = movimentiZipLogicoService.checkIsModificabile(codicemovimento);
	String status_msg = "02";
	if (checkIsModificabile && StringUtils.isBlank(testata.getGuidCollegato())) {
	    String msg = "#ZIP-LOGICO-DISASSOCIA-ALLEGATO#L'operatore " + getCurrentlyAuthenticatedUserDetails() + " ha disassociato l'allegato " +
			 testata.getCodiceoggettoDocAll() + " dallo zip logico del movimento " + movimento;
	    movimentiZipLogicoService.updateDocAllegatoTestata(codicemovimento, null);
	    LoggerCancellazioni.log(msg);
	} else {
	    status_msg = "03";
	    FlashMessages.getWarnings().add("Lo zip Logico non è modificabile");
	}
	return "redirect:" + redirectTo + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public void ajaxDownloadDocumentiZipLogico(@ModelAttribute("movimentiZipLogicoCommand") MovimentiZipLogicoCommand command,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Integer codicemovimento = command.getEntity().getMovimenti().getId().getCodice();
	Movimenti movimento = command.getEntity().getMovimenti();
	ByteArrayOutputStream zipFile = movimentiService.downloadDocumentiZipLogico(codicemovimento);
	String filename = "";
	if (StringUtils.isNotBlank(movimento.getMovimento())) {
	    filename = "documentazione_" + movimento.getMovimento().replaceAll("\\s", "") + ".zip";
	} else {
	    filename = "Allegati_zip_logico_movimento_" + movimento.getDescrizioneMovimento().replaceAll("\\s", "") + ".zip";
	}
	if (zipFile != null) {
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    if (request.getParameter("no_dialog") == null) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	    }
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType("application/zip");
	    response.setContentLength(zipFile.size());
	    ServletOutputStream out = response.getOutputStream();
	    zipFile.writeTo(out);
	    out.flush();
	} else {
	    log.error("File vuoto.");
	    throw new RuntimeException("File vuoto.");
	}
    }

    @RequestMapping
    public void ajaxLinkZipLogico(@RequestParam("guid_zip_logico") String guidZipLogico, @RequestParam("codiceMovimento") Integer codiceMovimento,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	MovimentiZipLogicoTestataHelper zips = movimentiZipLogicoService.findByCodiceMovimento(codiceMovimento);
	String guid = zips.getGuid();
	ZipLogicoLinkHelper zipHelper = new ZipLogicoLinkHelper();
	if (StringUtils.defaultString(guid).equalsIgnoreCase(guidZipLogico)) {
	    response.setContentType("application/json");
	    zipHelper = movimentiZipLogicoService.creaLinkZipLogico(codiceMovimento);
	}
	String ret = Utilities.marshalJsonObject(zipHelper, zipHelper.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	response.getOutputStream().write(ret.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void setPageAttributes(Model model, MovimentiZipLogicoCommand command) {

	model.addAttribute("movimentiZipLogicoCommand", command);
	model.addAttribute("isZipLogicoModificabile",
		movimentiZipLogicoService.checkIsModificabile(command.getEntity().getMovimenti().getId().getCodice()));
	model.addAttribute("ifThereAreDocumentsToAdd", ifThereAreDocumentsToAddToZipLogico(command));
    }

    private Boolean ifThereAreDocumentsToAddToZipLogico(MovimentiZipLogicoCommand command) {

	Boolean result = Boolean.FALSE;
	Integer codiceistanza = command.getEntity().getMovimenti().getIstanza().getId().getCodice();
	Integer codicemovimento = command.getEntity().getMovimenti().getId().getCodice();
	DocumentiHelper docHelper = documentiHelperService.findDocumentiDaAggiungereAZipLogico(codiceistanza, codicemovimento);
	if (!docHelper.getDocumentiIstanzaList().isEmpty()) {
	    result = Boolean.TRUE;
	}
	if (!docHelper.getDocumentiAltriMovimentiList().isEmpty()) {
	    result = Boolean.TRUE;
	}
	if (!docHelper.getDocumentiEndoprocedimentiList().isEmpty()) {
	    result = Boolean.TRUE;
	}
	if (!docHelper.getCdsattiList().isEmpty()) {
	    result = Boolean.TRUE;
	}
	if (!docHelper.getDocumentiAnagrafeList().isEmpty()) {
	    result = Boolean.TRUE;
	}
	if (!docHelper.getIstanzeprocureList().isEmpty()) {
	    result = Boolean.TRUE;
	}
	return result;
    }

    @Override
    protected void fixMergeEntityProperty(MovimentiZipLogico entity) {

	if (entity.getMovimenti() == null) {
	    entity.setMovimenti(new Movimenti());
	}
    }

    @Override
    protected void fixRenderEntityProperty(MovimentiZipLogico entity) {

	// TODO Auto-generated method stub
    }
}
