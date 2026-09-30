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
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.backoffice.definitions.nlapec.gestionemail.NlaGestioneMail;
import it.gruppoinit.pal.gp.backoffice.definitions.nlapec.gestionemail.NlaGestioneMailWSClient;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaMessaggioInviatoBinarioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaMessaggioInviatoBinarioResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IApplicaQRCodeService;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoTestataHelper;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("movimentiallegati")
public class MovimentiallegatiController extends BaseController<Movimentiallegati> {

    private static final Logger log = LoggerFactory.getLogger(MovimentiallegatiController.class);
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private PecInboxService pecInboxService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    private IApplicaQRCodeService applicaQRCodeService;
    public static final String NEW = "0";
    public static final String VIEW = "1";
    public static final String MOVIMENTO = "M";

    @RequestMapping
    public ModelMap list(@RequestParam("codiceMovimento") Integer codiceMovimento, HttpServletRequest request, HttpServletResponse response) {

	Movimenti movimento = new Movimenti();
	PkId id = new PkId(codiceMovimento);
	movimento.setId(id);
	movimento = movimentiService.bindDomainObject(movimento, PkId.class, "id.codice");
	List<Movimentiallegati> movimentiallegatiList = movimentiallegatiService.findByMovimento(codiceMovimento);
	MovimentiZipLogicoTestataHelper testata = this.movimentiZipLogicoService.findByCodiceMovimento(codiceMovimento);
	ModelMap model = new ModelMap(movimentiallegatiList);
	boolean export = createJMesaExport(request, response, movimentiallegatiList);
	if (export) {
	    return null;
	}
	setAttributesList(model);
	model.addAttribute("movimento", movimento);
	model.addAttribute("movimentiallegatiList", movimentiallegatiList);
	model.addAttribute("movinmentoziplogicotestata", testata);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VISUALIZZA_FILE_XML, "1", request);
	return model;
    }

    private void setAttributesList(ModelMap model) {

	model.addAttribute("isAttivaLayerProt", verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF));
	Boolean isAttivaQrCode = verticalizzazioniService.isAttiva(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE);
	if (isAttivaQrCode.booleanValue()) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		    VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_URL_DOWNLOAD);
	    if (vp == null || StringUtils.isBlank(StringUtils.defaultIfEmpty(vp.getValore(), "").trim())) {
		isAttivaQrCode = Boolean.FALSE;
	    }
	}
	model.addAttribute("isAttivaQrCode", isAttivaQrCode);
	boolean isAttivoLayerQRcode = new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune()).isAttivoTemplate();
	model.addAttribute("isAttivoLayerQRcode", isAttivoLayerQRcode);
    }

    @RequestMapping
    public String create(@RequestParam("codiceMovimento") Integer codiceMovimento,
	    @RequestParam(required = false, value = "isCaricamnetoMultiplo") Boolean isCaricamnetoMultiplo, Model model) {

	Movimenti movimento = new Movimenti();
	PkId id = new PkId(codiceMovimento);
	movimento.setId(id);
	movimento = movimentiService.bindDomainObject(movimento, PkId.class, "id.codice");
	Movimentiallegati movimentiallegati = new Movimentiallegati();
	movimentiallegati.setMovimento(movimento);
	if (EntityUtils.getNestedProperty(movimentiallegati.getMovimento(), "id.codice") != null) {
	    Boolean pubblica = movimentiallegati.getMovimento().getTipomovimento().getFlagPubblicaallegati();
	    if (pubblica == null) {
		pubblica = Boolean.FALSE;
	    }
	    movimentiallegati.setFlagPubblica(pubblica);
	}
	fixRenderEntityProperty(movimentiallegati);
	model.addAttribute("display", NEW);
	model.addAttribute("movimentiallegati", movimentiallegati);
	model.addAttribute("isCaricamnetoMultiplo", isCaricamnetoMultiplo);
	setPageAttributes(model);
	return "movimentiallegati/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("movimentiallegati") Movimentiallegati movimentiallegati, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	List<MultipartFile> lMultipartFiles = Utilities.getFileFromMultipartRequest(request);
	Integer codiceDocumento = null;
	fixMergeEntityProperty(movimentiallegati);
	try {
	    codiceDocumento = movimentiallegatiService.insertSingoloOrMultiFile(movimentiallegati, lMultipartFiles);
	    //  movimentiallegatiService.insert(movimentiallegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiallegati, e);
	    fixRenderEntityProperty(movimentiallegati);
	    model.addAttribute("display", NEW);
	    boolean isCaricamnetoMultiplo = !lMultipartFiles.isEmpty();
	    model.addAttribute("isCaricamnetoMultiplo", isCaricamnetoMultiplo);
	    setPageAttributes(model);
	    return "movimentiallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + codiceDocumento + "&status_msg=01";
    }

    @RequestMapping
    public String insertTrasformaInPdf(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Movimentiallegati movimentiallegati = movimentiallegatiService.findById(id);
	Integer codiceMovimento = movimentiallegati.getMovimento().getId().getCodice();
	fixRenderEntityProperty(movimentiallegati);
	String status_msg = "01";
	boolean eseguita = false;
	try {
	    movimentiallegatiService.insertTrasformaInPdf(codice);
	    eseguita = true;
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nella creazione del file PDF:" + e.getMessage());
	    status_msg = "03";
	}
	if (eseguita) {
	    Boolean isAttivaQrCodeAutomatico = verticalizzazioniService.isAttiva(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE);
	    if (isAttivaQrCodeAutomatico.booleanValue()) {
		Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
			VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
			VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_AUTOMATICO);
		if (vp == null || StringUtils.defaultIfEmpty(vp.getValore(), "N").trim().equalsIgnoreCase("N")) {
		    isAttivaQrCodeAutomatico = Boolean.FALSE;
		}
	    }
	    if (isAttivaQrCodeAutomatico) {
		try {
		    movimentiallegatiService.updateApplicaQRCode(codice);
		} catch (Exception e) {
		    FlashMessages.getWarnings().add("Errore nella apposizione del QRCODE nel PDF: " + e.getMessage());
		    status_msg = "03";
		}
	    }
	}
	return "redirect:list.htm?codiceMovimento=" + codiceMovimento + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public String insertQRCode(@RequestParam("codiceMovAllegati") Integer codiceMovAllegati, Model model, HttpServletRequest request) {

	PkId id = new PkId(codiceMovAllegati);
	Movimentiallegati movimentiallegati = movimentiallegatiService.findById(id);
	Integer codiceMovimento = movimentiallegati.getMovimento().getId().getCodice();
	// fixRenderEntityProperty(movimentiallegati);
	String status_msg = "01";
	try {
	    movimentiallegatiService.updateApplicaQRCode(codiceMovAllegati);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nella apposizione del QRCODE nel PDF: " + e.getMessage());
	    status_msg = "03";
	}
	return "redirect:list.htm?codiceMovimento=" + codiceMovimento + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public String applicaQrcodePdf(@RequestParam("codiceMovimentoAll") Integer codiceMovimentoAll,
	    @RequestParam("codiceOggetto") String codiceOggetto, Model model, HttpServletRequest request, HttpServletResponse response) {

	String status_msg = "01";
	PkId id = new PkId(codiceMovimentoAll);
	Movimentiallegati movimentiallegati = movimentiallegatiService.findById(id);
	Integer codiceMovimento = movimentiallegati.getMovimento().getId().getCodice();
	try {
	    Oggetti o = oggettiService.findById(new PkId(new Integer(codiceOggetto)));
	    applicaQRCodeService.applicaQRCode(codiceMovimento, o, false);
	    oggettiService.update(o);
	} catch (Exception ex) {
	    FlashMessages.getWarnings().add("Operazione eseguita: " + ex.getMessage());
	    status_msg = "03";
	}
	return "redirect:list.htm?codiceMovimento=" + codiceMovimento + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Movimentiallegati movimentiallegati = movimentiallegatiService.findById(id);
	fixRenderEntityProperty(movimentiallegati);
	model.addAttribute("movimentiallegati", movimentiallegati);
	model.addAttribute("display", VIEW);
	setPageAttributes(model);
	setAttributes(model);
	if (EntityUtils.getNestedProperty(movimentiallegati.getOggetto(), "id.codice") != null) {
	    Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	    oggettiService.updateFileRimuoviBloccoModifica(movimentiallegati.getOggetto().getId().getCodice(), codiceResponsabile);
	}
	return "movimentiallegati/form";
    }

    private void setAttributes(Model model) {

	model.addAttribute("isAttivaLayerProt", verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF));
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("movimentiallegati") Movimentiallegati movimentiallegati, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(movimentiallegati);
	try {
	    checkAccessoInformazioni(movimentiallegati.getMovimento().getIstanza(), true);
	    loggaCancellazioneOggettoIstanza(request);
	    movimentiallegatiService.update(movimentiallegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiallegati, e);
	    fixRenderEntityProperty(movimentiallegati);
	    model.addAttribute("display", VIEW);
	    setPageAttributes(model);
	    return "movimentiallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + movimentiallegati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("movimentiallegati") Movimentiallegati movimentiallegati, BindingResult result,
	    SessionStatus status) {

	Movimentiallegati objToDelete = movimentiallegatiService.findById(movimentiallegati.getId());
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.isNotBlank(objToDelete.getStcIdallegato()) || StringUtils.isNotBlank(objToDelete.getStcIddocumento())) {
	    boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false
		    : userlogged.getFlagCancelladocumentistc().booleanValue();
	    if (!isCancellaMail) {
		String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
		throw new SecurityException(messaggioErrore);
	    }
	}
	// Integer codiceMovimento = objToDelete.getMovimento().getId().getCodice();
	String responsabile = userlogged.toString();
	String descrizioneAllegato = objToDelete.toString();
	String descrizioneMovimento = objToDelete.getMovimento().toString();
	String descrizioneIstanza = ((Istanze) EntityUtils.getNestedProperty(objToDelete.getMovimento(), "istanza")).toString();
	try {
	    checkAccessoInformazioni(movimentiallegati.getMovimento().getIstanza(), true);
	    movimentiallegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    movimentiallegati = movimentiallegatiService.findById(movimentiallegati.getId());
	    fixRenderEntityProperty(movimentiallegati);
	    model.addAttribute("movimentiallegati", movimentiallegati);
	    setPageAttributes(model);
	    return "movimentiallegati/form";
	}
	LoggerCancellazioni.logCancellazioneMovimentiAllegati(responsabile, descrizioneAllegato, descrizioneMovimento, descrizioneIstanza);
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String deleteMovAllegati(Model model, @RequestParam("codice") Integer codice, @RequestParam("codiceMovimento") Integer codiceMovimento,
	    HttpServletRequest request) {

	PkId idMovall = new PkId(codice);
	Movimentiallegati objToDelete = movimentiallegatiService.findById(idMovall);
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.isNotBlank(objToDelete.getStcIdallegato()) || StringUtils.isNotBlank(objToDelete.getStcIddocumento())) {
	    boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false
		    : userlogged.getFlagCancelladocumentistc().booleanValue();
	    if (!isCancellaMail) {
		String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
		throw new SecurityException(messaggioErrore);
	    }
	}
	// Integer codiceMovimento = objToDelete.getMovimento().getId().getCodice();
	String responsabile = userlogged.toString();
	String descrizioneAllegato = objToDelete.toString();
	String descrizioneMovimento = objToDelete.getMovimento().toString();
	String descrizioneIstanza = ((Istanze) EntityUtils.getNestedProperty(objToDelete.getMovimento(), "istanza")).toString();
	try {
	    checkAccessoInformazioni(objToDelete.getMovimento().getIstanza(), true);
	    movimentiallegatiService.delete(objToDelete);
	    LoggerCancellazioni.logCancellazioneMovimentiAllegati(responsabile, descrizioneAllegato, descrizioneMovimento, descrizioneIstanza);
	    String messaggioCancellazione = getMessageFromBundle("05", new Object[] {});
	    FlashMessages.getInfos().add(messaggioCancellazione);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(new Movimentiallegati(), true, "movimentiAllegati", e);
	}
	return "redirect:list.htm?codiceMovimento=" + String.valueOf(codiceMovimento);
    }

    @RequestMapping
    public void ajaxChangeFlagPubblica(@RequestParam("codice") Integer codice,
	    @RequestParam(required = false, value = "checkPermessi") boolean checkPermessi, HttpServletResponse response) throws Exception {

	Movimentiallegati movallegato = movimentiallegatiService.findById(new PkId(codice));
	boolean isPermesso = true;
	// Se checkPermessi == true controllo se l'utente ha i permessi per fare operazione su quel movimento
	if (checkPermessi) {
	    isPermesso = movimentiService.checkPermessiMovimento(movallegato.getMovimento(),
		    (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails(), true);
	}
	if (isPermesso) {
	    if (BooleanUtils.isFalse(movallegato.getFlagPubblica())) {
		movallegato.setFlagPubblica(Boolean.TRUE);
	    } else {
		movallegato.setFlagPubblica(Boolean.FALSE);
	    }
	    movimentiallegatiService.update(movallegato);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} else {
	    response.getWriter().write(getMessageFromBundle("label.permessi_non_sufficienti_modifica", null));
	}
    }

    @RequestMapping
    public void ajaxChangeValueFieldValido(@RequestParam("codice") Integer codice, @RequestParam("valido") Integer valido, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Movimentiallegati movimentiallegati = movimentiallegatiService.findById(id);
	try {
	    checkAccessoInformazioni(movimentiallegati.getMovimento().getIstanza(), true);
	    movimentiallegati.setControllook(valido);
	    movimentiallegatiService.update(movimentiallegati);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxDownloadEml(@RequestParam("idmessage") String idmessage, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	String retError = null;
	Movimentiallegati movimentiallegati = null;
	Oggetti oggetti = null;
	movimentiallegati = movimentiallegatiService.findbyMessageId(idmessage);
	NlaGestioneMail pecWs = null;
	ScaricaMessaggioInviatoBinarioRequest scaricaMessaggioInviatoBinarioRequest = null;
	ScaricaMessaggioInviatoBinarioResponse scaricaMessaggioInviatoBinarioResponse = null;
	try {
	    log.debug("downloadEml# genero il port del ws....");
	    String wsUrl = getNlaGestioneMailWSURL();
	    NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(wsUrl, verticalizzazioneParametriSistemaService);
	    pecWs = nlaGestioneMailWSClient.getWSPort();
	    log.debug("downloadEml# Controllo se la verticalizzazione {} è attiva e se è configurato il parametro {}",
		    new Object[] { WebConstants.VERTICALIZZAIONE_MAIL_SERVICE, WebConstants.VERTICALIZZAIONE_MAIL_SERVICE_POSTA_USCITA_FOLDERNAME });
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_MAIL_SERVICE)) {
		String folder = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAIONE_MAIL_SERVICE,
			WebConstants.VERTICALIZZAIONE_MAIL_SERVICE_POSTA_USCITA_FOLDERNAME);
		if (StringUtils.isNotBlank(folder)) {
		    log.debug("downloadEml# cartella configurata: {}", folder);
		    scaricaMessaggioInviatoBinarioRequest = new ScaricaMessaggioInviatoBinarioRequest();
		    scaricaMessaggioInviatoBinarioRequest.setSoftware(ORMHelper.getSoftware());
		    scaricaMessaggioInviatoBinarioRequest.setToken(ORMHelper.getToken());
		    scaricaMessaggioInviatoBinarioRequest.setFolderName(folder);
		    scaricaMessaggioInviatoBinarioRequest.setIdentificativoBackofficeMessaggio(idmessage);
		    scaricaMessaggioInviatoBinarioResponse = pecWs.scaricaMessaggioInviatoBinario(scaricaMessaggioInviatoBinarioRequest);
		} else {
		    retError = "Attenzione, impossibile scaricare la mail come allegato. Nella verticalizzazione MAIL_SERVICE non è stata configurato il parametro POSTA_USCITA_FOLDERNAME" +
			    " da cui recuperare il messeggio";
		    throw new Exception(retError);
		}
	    }
	    pecWs.scaricaMessaggioInviatoBinario(scaricaMessaggioInviatoBinarioRequest);
	    if (scaricaMessaggioInviatoBinarioResponse != null) {
		byte[] b = Utilities.dataHandlerToBytes(scaricaMessaggioInviatoBinarioResponse.getContent());
		oggetti = new Oggetti();
		oggetti.setDimensioneFile(b.length);
		oggetti.setNomefile(scaricaMessaggioInviatoBinarioResponse.getNomeFile());
		oggetti.setOggetto(b);
		movimentiallegati.setOggetto(oggetti);
		oggettiService.insert(oggetti);
		movimentiallegatiService.update(movimentiallegati);
		response.setStatus(200);
		response.getWriter().write(oggetti.getId().getCodice().toString());
	    }
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(e.getMessage());
	    log.error("Attenzione, impossibile scaricare la mail come allegato: {}", e.getMessage());
	}
    }

    @RequestMapping
    public String applicaLayerProtocolloPdf(@RequestParam("codiceMovimento") Integer codiceMovimento,
	    @RequestParam("codiceOggetto") String codiceOggetto, @RequestParam(required = false, value = "codiceAllegato") Integer codiceAllegato,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    Movimenti mov = movimentiService.findById(new PkId(codiceMovimento));
	    Oggetti o = oggettiService.findById(new PkId(new Integer(codiceOggetto)));
	    o = movimentiallegatiService.applicaAnnotazioneProtocolloPdf(null, mov, o);
	    oggettiService.update(o);
	} catch (InvalidConfigurationException e) {
	    FlashMessages.getWarnings().add("Operazione eseguita: " + e.getMessage());
	    if (codiceAllegato != null) {
		return "redirect:view.htm?codice=" + codiceAllegato;
	    } else {
		return "redirect:list.htm?codiceMovimento=" + codiceMovimento;
	    }
	} catch (Exception ex) {
	    FlashMessages.getWarnings().add("Operazione eseguita: " + ex.getMessage());
	    if (codiceAllegato != null) {
		return "redirect:view.htm?codice=" + codiceAllegato;
	    } else {
		return "redirect:list.htm?codiceMovimento=" + codiceMovimento;
	    }
	}
	if (codiceAllegato != null) {
	    return "redirect:view.htm?codice=" + codiceAllegato + "&status_msg=02";
	} else {
	    return "redirect:list.htm?codiceMovimento=" + codiceMovimento + "&status_msg=02";
	}
    }

    private String getNlaGestioneMailWSURL() {

	return this.pecInboxService.getNlaGestioneMailWSURL();
	//return "http://10.20.45.2:8080/nla-pec/services/nlaGestioneMail.wsdl";
    }

    @Override
    protected void fixMergeEntityProperty(Movimentiallegati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Movimentiallegati entity) {

	if (EntityUtils.getNestedProperty(entity.getOggetto(), "id.codice") == null) {
	    entity.setOggetto(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("userlogged", resp);
    }
}
