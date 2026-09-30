package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.IVerticalizzazioneComportamentoComponenteFirmaService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione.FirmeRemoteService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.jmesa.DocumentiDaFirmareTable;
import it.gruppoinit.pal.gp.core.jmesa.DocumentiMessiAllaFirmaTable;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.MovAllegatiDaMettereAllaFirmaHelepr;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes("documentidafirmare")
public class DocumentiDaFirmareController extends BaseController<DocumentiDaFirmare> {

    private Logger log = LoggerFactory.getLogger(DocumentiDaFirmareController.class);
    @Autowired
    private DocumentiDaFirmareService documentidafirmareService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    public static final String NEW = "0";
    public static final String VIEW = "1";
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IVerticalizzazioneComportamentoComponenteFirmaService verticalizzazioneComportamentoComponenteFirmaService;
    @Autowired
    private FirmeRemoteService firmeRemoteService;

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	DocumentiDaFirmare documentidafirmare = documentidafirmareService.findById(new PkId(codice));
	Boolean showFirmaModel = Boolean.TRUE;
	if (documentidafirmare.getOggetti() != null) {
	    OggettiMetadatiId idOM = new OggettiMetadatiId(documentidafirmare.getOggetti().getId().getCodice(), WebConstants.OGGETTI_FILE_LOCKED_BY);
	    OggettiMetadati om = oggettiMetadatiService.findById(idOM);
	    if (om != null) {
		String codiceResponsabile = StringUtils.defaultString(om.getValore());
		Responsabili r = getCurrentlyAuthenticatedUserDetails();
		String codiceUtente = r.getId().getCodice().toString();
		if (!codiceUtente.equalsIgnoreCase(codiceResponsabile)) {
		    showFirmaModel = Boolean.FALSE;
		}
	    }
	}
	fixRenderEntityProperty(documentidafirmare);
	model.addAttribute("showFirmaModel", showFirmaModel);
	model.addAttribute("documentidafirmare", documentidafirmare);
	model.addAttribute("display", VIEW);
	setPageAttributes(model);
	return "documentidafirmare/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("documentidafirmare") DocumentiDaFirmare documentiDaFirmare, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    documentidafirmareService.update(documentiDaFirmare, Boolean.TRUE);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiDaFirmare, e);
	    fixRenderEntityProperty(documentiDaFirmare);
	    model.addAttribute("display", VIEW);
	    setPageAttributes(model);
	    return "redirect:view.htm?codice=" + documentiDaFirmare.getId().getCodice() + "&status_msg=02";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + documentiDaFirmare.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String updatesalvafirmamultipla(Model model, @ModelAttribute("documentidafirmare") DocumentiDaFirmare documentiDaFirmare,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	//FIXME 
	String[] id_doc_da_firmare = request.getParameterValues("id_doc_da_firmare");
	String lista_doc_da_firmare = "";
	try {
	    List<Integer> id_doc_da_firmare_i = new ArrayList<Integer>();
	    for (String cod : id_doc_da_firmare) {
		lista_doc_da_firmare += cod + ",";
		if (Utilities.isInteger(cod)) {
		    id_doc_da_firmare_i.add(Integer.parseInt(cod));
		}
	    }
	    documentidafirmareService.updateMutiplo(documentiDaFirmare.getFlagDaFirmare(), documentiDaFirmare.getAnnotazioniFirmatario(),
		    id_doc_da_firmare_i);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:mettiallafirmamultipla.htm?lista_doc_da_firmare=" + lista_doc_da_firmare.replaceAll(",$", "") + "&status_msg=02";
	}
	status.setComplete();
	return "redirect:firmacompleta.htm?status_msg=04";
    }

    @RequestMapping
    public String firmacompleta(HttpServletRequest request) {

	return "documentidafirmare/firmacompleta";
    }

    @RequestMapping
    public String updateMettiAllaFirma(@RequestParam(required = false, value = "from") Integer from, Model model,
	    @ModelAttribute("documentidafirmare") DocumentiDaFirmare documentiDaFirmare, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	String redirect = "redirect:popupViewDocumentoDaFirmare.htm?codiceMovAllegato=" +
		documentiDaFirmare.getMovimentiallegati().getId().getCodice() +
		"&status_msg=02";
	if (from == WebConstants.FROM_DOCAUTORIZZAZIONE_DOC_MESSI_ALLA_FIRMA) {
	    redirect = "redirect:popupViewMettiAllaFirma.htm?codice=" + documentiDaFirmare.getId().getCodice() + "&from=" + from + "&status_msg=02";
	}
	try {
	    if (documentiDaFirmare.getId().getCodice() == null) {
		documentidafirmareService.insert(documentiDaFirmare, Boolean.FALSE);
	    } else {
		documentidafirmareService.update(documentiDaFirmare, Boolean.FALSE);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiDaFirmare, e);
	    fixRenderEntityProperty(documentiDaFirmare);
	    model.addAttribute("display", VIEW);
	    setPageAttributes(model);
	    //	    return "redirect:popupViewDocumentoDaFirmare.htm?codiceMovAllegato=" + documentiDaFirmare.getMovimentiallegati().getId().getCodice()
	    //		    + "&status_msg=02";
	    return redirect;
	}
	status.setComplete();
	//	return "redirect:popupViewDocumentoDaFirmare.htm?codiceMovAllegato=" + documentiDaFirmare.getMovimentiallegati().getId().getCodice()
	//		+ "&status_msg=02";
	return redirect;
    }

    @RequestMapping
    public String annullaMettiAllaFirma(@RequestParam(required = false, value = "from") Integer from, Model model,
	    @ModelAttribute("documentidafirmare") DocumentiDaFirmare documentiDaFirmare, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (log.isDebugEnabled()) {
	    log.debug("annullaMettiAllaFirma# metti alla firma popup....");
	}
	DocumentiDaFirmare objToDelete = documentidafirmareService.findById(documentiDaFirmare.getId());
	try {
	    documentidafirmareService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    return "redirect:popupViewDocumentoDaFirmare.htm?codiceMovAllegato=" +
		    documentiDaFirmare.getMovimentiallegati().getId().getCodice() +
		    "&status_msg=02";
	}
	status.setComplete();
	return "redirect:popupViewDocumentoDaFirmare.htm?codiceMovAllegato=" +
		documentiDaFirmare.getMovimentiallegati().getId().getCodice() +
		"&status_msg=02";
    }

    @RequestMapping
    public void ajaxAnnullaMettiAllaFirma(Model model, @ModelAttribute("documentidafirmare") DocumentiDaFirmare documentiDaFirmare,
	    BindingResult bindingResult, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String result = "OK";
	if (log.isDebugEnabled()) {
	    log.debug("annullaMettiAllaFirma# metti alla firma popup....");
	}
	DocumentiDaFirmare objToDelete = documentidafirmareService.findById(documentiDaFirmare.getId());
	try {
	    documentidafirmareService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(bindingResult, objToDelete, true, e);
	    //result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio: " + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String popupCreate(@RequestParam("codiceMovAllegato") Integer codiceMovAllegato, Model model, HttpServletRequest request) {

	DocumentiDaFirmare oggettoDaFirmare = new DocumentiDaFirmare();
	Movimentiallegati movimentiAllegati = movimentiallegatiService.findById(new PkId(codiceMovAllegato));
	if (movimentiAllegati.getOggetto() != null) {
	    oggettoDaFirmare = new DocumentiDaFirmare();
	    oggettoDaFirmare.setOggetti(movimentiAllegati.getOggetto());
	    oggettoDaFirmare.setMovimentiallegati(movimentiAllegati);
	    oggettoDaFirmare.setFirmatario(new Responsabili());
	    oggettoDaFirmare.setIstanze(movimentiAllegati.getMovimento().getIstanza());
	} else {
	    log.error("Il movimento allegato con codice {} non contiene un oggetto e non può essere messo alla firma.", codiceMovAllegato);
	    throw new IllegalArgumentException(
		    "Il movimento allegato con codice " + codiceMovAllegato + " non contiene un oggetto e non può essere messo alla firma.");
	}
	model.addAttribute("convertibileInPdf", Boolean
		.valueOf(verticalizzazioneComportamentoComponenteFirmaService.convertibileInPdf(movimentiAllegati.getOggetto().getNomefile())));
	model.addAttribute("documentidafirmare", oggettoDaFirmare);
	return "documentidafirmare/formmettiallafirma";
    }

    @RequestMapping
    public String popupViewMettiAllaFirma(@RequestParam("codice") Integer codice, @RequestParam(required = false, value = "from") Integer from,
	    Model model, HttpServletRequest request) {

	DocumentiDaFirmare documentidafirmare = documentidafirmareService.findById(new PkId(codice));
	fixRenderEntityProperty(documentidafirmare);
	Responsabili utenteLoggato = getCurrentlyAuthenticatedUserDetails();
	Boolean stessoFirmatarioRichiedente = Boolean.TRUE;
	if (documentidafirmare.getRichiedente() != null) {
	    if (documentidafirmare.getRichiedente().getId() != null) {
		if (documentidafirmare.getRichiedente().getId().getCodice() != null) {
		    if (!documentidafirmare.getRichiedente().getId().getCodice().equals(utenteLoggato.getId().getCodice())) {
			stessoFirmatarioRichiedente = Boolean.FALSE;
		    }
		}
	    }
	}
	//Il parametro from mi permette di controllare se ho aperto il popup da una pagina per la quale voglio che il popup si comporti in maniera diversa
	//da quella di default.
	Boolean fromDocAutorizzazione = Boolean.FALSE;
	if (from == WebConstants.FROM_DOCAUTORIZZAZIONE_DOC_MESSI_ALLA_FIRMA) {
	    //ho aperto la lista documenti da firmare dalla pagina di documenti autorizzazione. 
	    fromDocAutorizzazione = Boolean.TRUE;
	}
	model.addAttribute("stessoFirmatarioRichiedente", stessoFirmatarioRichiedente);
	model.addAttribute("documentidafirmare", documentidafirmare);
	model.addAttribute("fromDocAut", fromDocAutorizzazione);
	return "documentidafirmare/formmettiallafirma";
    }

    @RequestMapping
    public String mettiallafirmamultipla(@RequestParam("lista_doc_da_firmare") String lista_doc_da_firmare,
	    @RequestParam(required = false, value = "isOperazioneEffettuata") String isOperazioneEffettuata, Model model,
	    HttpServletRequest request) {

	lista_doc_da_firmare = StringUtils.defaultString(lista_doc_da_firmare).trim();
	model.addAttribute("lista_doc_da_firmare", lista_doc_da_firmare);
	if (StringUtils.isBlank(lista_doc_da_firmare)) {
	    throw new SecurityException("La lista dei documenti da firmare non può essere vuota");
	}
	String[] arlddf = lista_doc_da_firmare.split(",");
	Set<Integer> doc_da_firmare = new HashSet<Integer>();
	for (String cod : arlddf) {
	    cod = StringUtils.defaultString(cod).trim();
	    if (Utilities.isInteger(cod)) {
		doc_da_firmare.add(Integer.parseInt(cod));
	    }
	}
	String codiceoggettoReqParam = "";
	List<DocumentiDaFirmare> documentidafirmare = new ArrayList<DocumentiDaFirmare>();
	String codiceoggetto = "";
	Set<Integer> file_da_firmare = new HashSet<Integer>();
	for (Integer codice : doc_da_firmare) {
	    DocumentiDaFirmare doc = documentidafirmareService.findById(new PkId(codice));
	    if (doc.getOggetti() != null) {
		String ext = StringUtils.substringAfterLast(doc.getOggetti().getNomefile(), ".");
		doc.setEstensioneFile(ext.toLowerCase());
		file_da_firmare.add(doc.getOggetti().getId().getCodice());
		codiceoggetto += doc.getOggetti().getId().getCodice() + ",";
		// Controllo se il ogetti.codice.id==movimentiallegati.oggetti.codice.id
		boolean isCheckOggettoUguale = false;
		if (EntityUtils.getNestedProperty(doc.getMovimentiallegati(), "id.codice") != null
			&& EntityUtils.getNestedProperty(doc.getMovimentiallegati().getOggetto(), "id.codice") != null
			&& doc.getMovimentiallegati().getOggetto().getId().getCodice() == doc.getOggetti().getId().getCodice()) {
		    isCheckOggettoUguale = true;
		}
		doc.setIsCheckOggettoUguale(isCheckOggettoUguale);
		documentidafirmare.add(doc);
	    }
	}
	for (Integer co : file_da_firmare) {
	    codiceoggettoReqParam += "&codiceoggetto=" + co.toString();
	}
	model.addAttribute("codicioggetto_file_da_firmare", file_da_firmare);
	DocumentiDaFirmare documentidafirmareCommand = new DocumentiDaFirmare();
	codiceoggetto = codiceoggetto.replaceAll(",$", "");
	model.addAttribute("listaCodicioggettoReqParam", codiceoggettoReqParam);
	model.addAttribute("codiceoggetto", codiceoggetto);
	model.addAttribute("documentidafirmares", documentidafirmare);
	model.addAttribute("documentidafirmare", documentidafirmareCommand);
	if (StringUtils.isNotBlank(isOperazioneEffettuata) && isOperazioneEffettuata.equals("1")) {
	    model.addAttribute("display", VIEW);
	} else {
	    model.addAttribute("display", NEW);
	}
	boolean isSegnaComeFirmatoAttivo = false;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_SEGNA_DOCUMENTO_FIRMATO);
	    if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
		isSegnaComeFirmatoAttivo = vp.getValore().equals("1") ? true : false;
	    }
	}
	boolean isFirmaRemotaAttiva = this.firmeRemoteService.almenoUnaFirmaRemotaAttiva();
	log.debug("documentiDaFirmareList# Firma remota attiva: {}", isFirmaRemotaAttiva);
	model.addAttribute("isFirmaRemotaAttiva", isFirmaRemotaAttiva);
	model.addAttribute("isSegnaComeFirmatoAttivo", isSegnaComeFirmatoAttivo);
	return "documentidafirmare/formmettiallafirmamultipla";
    }

    @RequestMapping
    public String segnaDocumentoComeFirmato(@RequestParam("codiceDocumento") Integer codiceDocumento,
	    @RequestParam("lista_doc_da_firmare") String lista_doc_da_firmare, Model model, HttpServletRequest request) {

	String status_msg = "01";
	try {
	    documentidafirmareService.updateSegnaComeFirmato(codiceDocumento);
	    // dalla lista devo togliere il codice del documento che abbiamo segnato come firmato.
	    lista_doc_da_firmare = StringUtils.replace(lista_doc_da_firmare, codiceDocumento.toString(), "");
	    lista_doc_da_firmare = StringUtils.replace(lista_doc_da_firmare, ",,", "");
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore mentre si stava segnando il documento come firmato: " + e.getMessage());
	    status_msg = "03";
	}
	if (StringUtils.isBlank(lista_doc_da_firmare)) {
	    lista_doc_da_firmare = ",";
	    //return getHistoryBack();
	}
	return "redirect:mettiallafirmamultipla.htm?lista_doc_da_firmare=" + lista_doc_da_firmare + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public String insertTrasformaInPdf(@RequestParam("codiceOggetto") Integer codiceOggetto,
	    @RequestParam("codiceMovimentoallegato") Integer codiceMovimentoallegato,
	    @RequestParam("lista_doc_da_firmare") String lista_doc_da_firmare, Model model, HttpServletRequest request) {

	String status_msg = "01";
	try {
	    movimentiallegatiService.insertTrasformaInPdf(codiceMovimentoallegato, codiceOggetto);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nella creazione del file PDF:" + e.getMessage());
	    status_msg = "03";
	}
	return "redirect:mettiallafirmamultipla.htm?lista_doc_da_firmare=" + lista_doc_da_firmare + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public String updateRigettafirmamultipla(@RequestParam("id_doc_da_firmare") String lista_doc_da_firmare,
	    @RequestParam("annotazione_firmatario") String annotazione_firmatario, Model model, HttpServletRequest request) {

	lista_doc_da_firmare = StringUtils.defaultString(lista_doc_da_firmare).trim();
	if (StringUtils.isBlank(lista_doc_da_firmare)) {
	    throw new SecurityException("La lista dei documenti da firmare non può essere vuota");
	}
	try {
	    String[] codicidocumenti = lista_doc_da_firmare.split(",");
	    List<Integer> doc_da_firmare = new ArrayList<Integer>();
	    for (String cod : codicidocumenti) {
		cod = StringUtils.defaultString(cod).trim();
		if (Utilities.isInteger(cod)) {
		    doc_da_firmare.add(Integer.parseInt(cod));
		}
	    }
	    documentidafirmareService.updateMutiplo(StatiDocumentiDaFirmare.FIRMA_NEGATA.name(), annotazione_firmatario, doc_da_firmare);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.toString());
	    return "redirect:mettiallafirmamultipla.htm?lista_doc_da_firmare=" + lista_doc_da_firmare.replaceAll(",$", "") + "&status_msg=03";
	}
	FlashMessages.getInfos().add("Per tutti i documenti è stata negata la firma");
	return "redirect:mettiallafirmamultipla.htm?lista_doc_da_firmare=" +
		lista_doc_da_firmare.replaceAll(",$", "") +
		"&isOperazioneEffettuata=" +
		VIEW +
		"&status_msg=02";
    }

    @RequestMapping
    public String ajaxViewOggettoList(@RequestParam("codiceMovAllegato") Integer codiceMovAllegato, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String icona = "";
	int richiesti = documentidafirmareService.countDocumentiPerMovimentiAllegati(codiceMovAllegato,
		StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name());
	int completi = documentidafirmareService.countDocumentiPerMovimentiAllegati(codiceMovAllegato, StatiDocumentiDaFirmare.FIRMA_COMPLETA.name());
	int negati = documentidafirmareService.countDocumentiPerMovimentiAllegati(codiceMovAllegato, StatiDocumentiDaFirmare.FIRMA_NEGATA.name());
	int tot = richiesti + completi + negati;
	if (tot > 0) {
	    icona = "_richiesta";
	    if (completi == tot) {
		icona = "_completa";
	    }
	    if (negati > 0) {
		icona = "_negata";
	    }
	}
	model.addAttribute("tipoicona", icona);
	model.addAttribute("codiceMovAllegato", codiceMovAllegato);
	return "documentidafirmare/viewOggettoInfoList";
    }

    @RequestMapping
    public String popupViewDocumentoDaFirmare(@RequestParam("codiceMovAllegato") Integer codiceMovAllegato, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled()) {
	    log.debug("popupViewSignedFileInfo# metti alla firma popup....");
	}
	List<DocumentiDaFirmare> documentidafirmareList = documentidafirmareService.findByMovimentiallegati(codiceMovAllegato);
	model.addAttribute("documentidafirmareList", documentidafirmareList);
	model.addAttribute("codiceMovAllegato", codiceMovAllegato);
	return "documentidafirmare/list";
    }

    /**
     * Metodo permette di mettere alla frma N documenti dello stesso movimento all'interno della lista degli allegati di
     * un movimento
     * 
     * @param codiciMovAllegato
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String createMettiAllaFirmaMultipli(@RequestParam("codiciMovAllegato") String codiciMovAllegato, Model model, HttpServletRequest request) {

	preparateViewMessoAllaFirmaMultiplo(model, codiciMovAllegato);
	return "documentidafirmare/formMultipliOggettiMessiAllaFirma";
    }

    private void preparateViewMessoAllaFirmaMultiplo(Model model, String codiciMovAllegato) {

	DocumentiDaFirmare oggettoDaFirmare = new DocumentiDaFirmare();
	fixRenderEntityProperty(oggettoDaFirmare);
	List<MovAllegatiDaMettereAllaFirmaHelepr> listMovAllegati = new ArrayList<MovAllegatiDaMettereAllaFirmaHelepr>();
	//	StringBuffer codMovBonificati = new StringBuffer();
	String[] codMovAll = StringUtils.split(codiciMovAllegato, ",");
	MovAllegatiDaMettereAllaFirmaHelepr movAllegatiDaMettereAllaFirmaHelepr = null;
	for (int i = 0; i < codMovAll.length; i++) {
	    movAllegatiDaMettereAllaFirmaHelepr = new MovAllegatiDaMettereAllaFirmaHelepr();
	    Movimentiallegati movimentiAllegati = movimentiallegatiService.findById(new PkId(Integer.parseInt(codMovAll[i])));
	    movAllegatiDaMettereAllaFirmaHelepr.setMovimentiallegati(movimentiAllegati);
	    if (EntityUtils.getNestedProperty(movimentiAllegati.getOggetto(), "id.codice") != null) {
		listMovAllegati.add(movAllegatiDaMettereAllaFirmaHelepr);
		movAllegatiDaMettereAllaFirmaHelepr.setOggettoPresente(true);
		List<DocumentiDaFirmare> listDocDaFirmare = documentidafirmareService.findByMovimentiallegati(Integer.parseInt(codMovAll[i]));
		movAllegatiDaMettereAllaFirmaHelepr.setDocumentiDaFirmares(listDocDaFirmare);
		//		codMovBonificati.append(codMovAll[i]).append(",");
	    } else {
		log.debug(
			"popupCreateMultipli# Il record movimento allegati con codice {}, non ha un oggetto collegato. Impossibile metterlo alla firma",
			codMovAll[i]);
		movAllegatiDaMettereAllaFirmaHelepr.setOggettoPresente(false);
		listMovAllegati.add(movAllegatiDaMettereAllaFirmaHelepr);
	    }
	}
	//String _codMovBonificati = StringUtils.substringBeforeLast(codMovBonificati.toString(), ",");
	model.addAttribute("documentidafirmare", oggettoDaFirmare);
	model.addAttribute("codiciMovAllegato", codiciMovAllegato);
	model.addAttribute("listMovAllegati", listMovAllegati);
    }

    @RequestMapping
    public String updateMettiAllaFirmaMultipli(@RequestParam("codiciMovAllegato") String codiciMovAllegato, Model model,
	    @ModelAttribute("documentidafirmare") DocumentiDaFirmare documentiDaFirmare, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    if (EntityUtils.getNestedProperty(documentiDaFirmare.getFirmatario(), "id.codice") != null) {
		String[] codMovAll = StringUtils.split(codiciMovAllegato, ",");
		documentidafirmareService.insertMultipli(codMovAll, documentiDaFirmare.getFirmatario(),
			documentiDaFirmare.getAnnotazioniRichiedente());
	    } else {
		String m = getMessageFromBundle("documentidafirmare.error.reposonsabile_obbligatorio", null);
		FlashMessages.getWarnings().add(m);
		preparateViewMessoAllaFirmaMultiplo(model, codiciMovAllegato);
		fixRenderEntityProperty(documentiDaFirmare);
		setPageAttributes(model);
		return "documentidafirmare/formMultipliOggettiMessiAllaFirma";
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiDaFirmare, e);
	    preparateViewMessoAllaFirmaMultiplo(model, codiciMovAllegato);
	    fixRenderEntityProperty(documentiDaFirmare);
	    setPageAttributes(model);
	    return "documentidafirmare/formMultipliOggettiMessiAllaFirma";
	}
	status.setComplete();
	//	return "redirect:../movimentiallegati/list.htm?codiceMovimento=" + codMov + "&status_msg=02";
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F&status_msg=02";
    }

    @RequestMapping
    public String documentiDaFirmareList(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled()) {
	    log.debug("documentiDaFirmareList# Documenti da firmare....");
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////  TABELLA LISTA DOCUMENTI DA FIRMARE /////////////////////////////////////////////////////////////////////
	Responsabili loggedUser = getCurrentlyAuthenticatedUserDetails();
	GenerateTable<DocumentiDaFirmare> documentiTable = new DocumentiDaFirmareTable(loggedUser, documentidafirmareService, oggettiMetadatiService,
		responsabiliService);
	String documentiDaFirmareHtmlTable = documentiTable.createJMesaList(request, response, "label.documenti_da_firmare",
		"documenti_da_firmare_id", false);
	if (documentiDaFirmareHtmlTable == null) {
	    return null;
	}
	model.addAttribute("documentiDaFirmareHtmlTable", documentiDaFirmareHtmlTable);
	return "documentidafirmare/documentiDaFirmareList";
    }

    @RequestMapping
    public String documentiMessiAllaFirmaList(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled()) {
	    log.debug("documentiMessiAllaFirmaList# Documenti messi alla firma....");
	}
	// verifico se devo filtrare solo per l'utente loggato andando a verficare la configurzione utente
	String valoreVisSoloIMiei = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME, "1", request);
	Boolean isSoloQuelliMessiDallutenteLoggato = StringUtils.isNotBlank(valoreVisSoloIMiei) && "1".equals(valoreVisSoloIMiei) ? true : false;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////  TABELLA LISTA DOCUMENTI MESSI ALLA FIRMA /////////////////////////////////////////////////////////////////////
	Responsabili loggedUser = getCurrentlyAuthenticatedUserDetails();
	GenerateTable<DocumentiDaFirmare> documentiTable = new DocumentiMessiAllaFirmaTable(loggedUser, isSoloQuelliMessiDallutenteLoggato,
		documentidafirmareService, oggettiMetadatiService, responsabiliService);
	String documentiMessiAllaFirmaHtmlTable = documentiTable.createJMesaList(request, response, "label.lista_documenti_messi_alla_firma.title",
		"documenti_messi_alla_firma_id", false);
	if (documentiMessiAllaFirmaHtmlTable == null) {
	    return null;
	}
	model.addAttribute("CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME_CHECKED", Utilities.toCheckedString(valoreVisSoloIMiei));
	model.addAttribute("documentiMessiAllaFirmaHtmlTable", documentiMessiAllaFirmaHtmlTable);
	return "documentidafirmare/documentiMessiAllaFirmaList";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(DocumentiDaFirmare entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(DocumentiDaFirmare entity) {

	if (entity.getFirmatario() == null) {
	    entity.setFirmatario(new Responsabili());
	}
	if (entity.getMovimentiallegati() == null) {
	    entity.setMovimentiallegati(new Movimentiallegati());
	}
    }

    public DocumentiDaFirmareService getDocumentidafirmareService() {

	return documentidafirmareService;
    }

    public void setDocumentidafirmareService(DocumentiDaFirmareService documentidafirmareService) {

	this.documentidafirmareService = documentidafirmareService;
    }
}
