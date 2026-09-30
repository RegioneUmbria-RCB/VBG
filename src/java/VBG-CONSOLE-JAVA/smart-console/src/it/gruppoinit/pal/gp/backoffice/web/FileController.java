package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.dss.wsclient.WsDocument;
import it.gruppoinit.dss.wsclient.WsValidationReport;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipologieoggetto;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.ProcessedItem;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;
import it.gruppoinit.pal.gp.core.service.TipologieoggettoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.DSSWSClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.List;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController extends BaseController<FileUpload> {

    private Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiinfoService oggettiinfoService;
    @Autowired
    private TipologieoggettoService tipologieoggettoService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;

    @RequestMapping
    public String ajaxSearch() {

	return "file/uploadFile";
    }

    private String getDownloadLink(Oggetti oggettiLazy) throws UnsupportedEncodingException, IOException {

	String filename = oggettiLazy.getNomefile();
	if (StringUtils.isNotBlank(filename)) {
	    try {
		String filePath = oggettiService.getSharedFileLink(oggettiLazy.getId().getIdcomune(), oggettiLazy.getId().getCodice());
		if (StringUtils.isNotBlank(filePath)) {
		    String redirect = "file:///" + filePath;
		    return redirect;
		}
	    } catch (Exception e) {
		log.error("Errore nel recupero del file: {}", e);
	    }
	}
	return "../file/ajaxDownload.htm?fileId=" + oggettiLazy.getId().getCodice() + "&idComuneOggetto=" + oggettiLazy.getId().getIdcomune();
    }

    @RequestMapping
    public String ajaxUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile mpFile)
	    throws IOException {

	byte[] fileByteArray = mpFile.getBytes();
	Oggetti oggetto = new Oggetti();
	oggetto.setNomefile(mpFile.getOriginalFilename());
	oggetto.setOggetto(fileByteArray);
	oggettiService.insert(oggetto);
	return "redirect:ajaxUploadResult.htm?fileId=" + oggetto.getId().getCodice() + "&idComuneOggetto=" + oggetto.getId().getIdcomune();
    }

    @RequestMapping
    public void ajaxUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id,
	    @RequestParam("fileUpload") MultipartFile mpFile) throws IOException {

	byte[] fileByteArray = mpFile.getBytes();
	if (fileByteArray == null || fileByteArray.length == 0) {
	    throw new RuntimeException("Il file da salvare è vuoto");
	}
	Oggetti oggetto = oggettiService.findById(new PkId(id));
	if (oggetto != null) {
	    oggetto.setOggetto(fileByteArray);
	    oggettiService.update(oggetto);
	}
	// return "redirect:ajaxUploadResult.htm?fileId=" + oggetto.getId().getCodice();
    }

    @RequestMapping
    public String ajaxUploadResult(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id)
	    throws IOException {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(id));
	model.addAttribute("filename", oggetto.getNomefile());
	model.addAttribute("size", oggetto.getDimensioneFileLeggibile());
	model.addAttribute("id", id);
	return "file/uploadFileResult";
    }

    @RequestMapping
    public String ajaxDelete(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id) throws IOException {

	Oggetti oggetto = new Oggetti(new PkId(id));
	oggettiService.delete(oggetto);
	return "redirect:ajaxSearch.htm";
    }

    @RequestMapping
    public void ajaxModificaDocumento(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id)
	    throws IOException {

	ajaxDownload(id, ORMHelper.getIdcomune(), true, request, response);
    }

    @RequestMapping
    public void ajaxDownload(@RequestParam("fileId") Integer id, @RequestParam("idComuneOggetto") String idComuneOggetto,
	    @RequestParam(value = "modifica", required = false) Boolean isModifica, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Oggetti oggetto = oggettiService.findById(new PkId(idComuneOggetto, id));
	boolean isModificaDocumento = isModifica == null ? false : isModifica.booleanValue();
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (StringUtils.isNotBlank(filename)) {
		if (!isModificaDocumento) {
		    OggettiMetadatiId idom = new OggettiMetadatiId(idComuneOggetto, id, WebConstants.OGGETTI_FILE_LOCKED_BY);
		    OggettiMetadati omdt = oggettiMetadatiService.findById(idom);
		    if (omdt == null) { // se il file non è bloccato verifico se rilasciare il link file:///.....
			String filePath = oggettiService.getSharedFileLink(idComuneOggetto, id);
			if (StringUtils.isNotBlank(filePath)) {
			    String redirect = "file:///" + filePath;
			    // String fileName2 = URLEncoder.encode(filename, "Latin1");
			    // redirect = redirect.replaceFirst(filename, fileName2);
			    // response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			    // response.setHeader("Location", redirect);
			    response.sendRedirect(redirect);
			    return;
			}
		    }
		}
		byte[] b = oggetto.getOggetto();
		if (b != null) {
		    response.setHeader("Pragma", "public");
		    response.setHeader("Cache-Control", "max-age=0");
		    if (request.getParameter("no_dialog") == null) {
			// BOCCI 2012-08-13
			// Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
			// When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
			// While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
			// According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
			// most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
			// Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		    }
		    response.setHeader("Content-transfer-encoding", "binary");
		    String cType = contenttypesService.findMimeTypeByFileName(filename);
		    response.setContentType(cType);
		    response.setContentLength(b.length);
		    ServletOutputStream out = response.getOutputStream();
		    out.write(b);
		    out.flush();
		} else {
		    log.error("File vuoto. (id={})", id);
		    throw new RuntimeException("File vuoto. (id=" + id + ")");
		}
	    } else {
		log.error("File senza nome. (id={})", id);
		throw new RuntimeException("File senza nome. (id=" + id + ")");
	    }
	} else {
	    log.error("File non trovato. (id={})", id);
	    throw new RuntimeException("File non trovato. (id=" + id + ")");
	}
    }

    @RequestMapping
    public String popupViewSignedFileInfo(Model model, @RequestParam("fileId") Integer id, @RequestParam("idComuneOggetto") String idComuneOggetto,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled()) {
	    log.debug("popupViewSignedFileInfo# Inizio controllo firma....");
	}
	Oggetti oggetto = oggettiService.findById(new PkId(idComuneOggetto, id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		// Il metodo processa il file invocandoil ws del servizio DSS, e ritorna un oggetto di appoggio
		// ProcessedItem usato per la visulaizzazioni delle informazioni sulla jsp
		if (log.isDebugEnabled()) {
		    log.debug("popupViewSignedFileInfo# Invoco il metoche che processa il file tramite il DSS (codice oggetto:{} [{}])",
			    new Object[] { filename, oggetto.getId().getCodice() });
		}
		ProcessedItem processedItem = processFile(oggetto);
		// Caso che il ws ritorna il content invoco il metodo che scarica il file in chiaro
		//if (processedItem.getReport().getContent() != null) {
		if (log.isDebugEnabled()) {
		    log.debug("popupViewSignedFileInfo# Il report ritorna il content ");
		}
		String linkSignedFile = "downloadSignedFileClearContent.htm?fileId=" + oggetto.getId().getCodice() + "&idComuneOggetto="
			+ idComuneOggetto;
		model.addAttribute("downloadLink", linkSignedFile);
		model.addAttribute("pi", processedItem);
		return "file/validazione";
	    } else {
		log.error("File senza nome.Codice oggetto {}", id);
		throw new RuntimeException("File senza nome. (id=" + id + ")");
	    }
	} else {
	    log.error("File non trovato.Codice oggetto {}", id);
	    throw new RuntimeException("File non trovato. (id=" + id + ")");
	}
    }

    private ProcessedItem processFile(Oggetti file) {

	if (log.isDebugEnabled()) {
	    log.debug("processFile#Inizio a precessare il file {}[{}] ", new Object[] { file.getNomefile(), file.getId().getCodice() });
	}
	ProcessedItem pi = null;
	pi = new ProcessedItem(file.getNomefile());
	try {
	    pi.setSize(file.getOggetto().length);
	    DataHandler dh = Utilities.bytesToDataHandler(file.getOggetto());
	    WsValidationReport report = DSSWSClient.validateDocument(dh, file.getNomefile(), false);
	    pi.setReport(report);
	    WsDocument content = pi.getReport().getContent();
	    if (content != null) {
		pi.setContentFileName(content.getName());
		byte[] contentBinary = Utilities.dataHandlerToBytes(content.getBinary());
		ByteArrayInputStream bais = new ByteArrayInputStream(contentBinary);
		String writedContent = getMD5Checksum(bais);
		pi.setContentWritedFileName(writedContent);
	    } else {
		pi.setContentFileName(file.getNomefile());
	    }
	} catch (Exception e) {
	    log.error("Errore nell'inizializzazione della verifica: {}", e.getMessage());
	    pi.getErrors().add("Errore nell'inizializzazione della verifica: " + e.getMessage());
	}
	if (log.isDebugEnabled()) {
	    log.debug("processFile#Fine a precessare il file {}[{}] ", new Object[] { file.getNomefile(), file.getId().getCodice() });
	}
	return pi;
    }

    private static String getMD5Checksum(InputStream fis) throws Exception {

	byte[] b = createChecksum(fis);
	String result = "";
	for (int i = 0; i < b.length; i++) {
	    result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
	}
	return result;
    }

    private static byte[] createChecksum(InputStream fis) throws Exception {

	byte[] buffer = new byte[1024];
	MessageDigest complete = MessageDigest.getInstance("MD5");
	int numRead;
	do {
	    numRead = fis.read(buffer);
	    if (numRead > 0) {
		complete.update(buffer, 0, numRead);
	    }
	} while (numRead != -1);
	fis.close();
	return complete.digest();
    }

    @RequestMapping
    public String editDocApplet(Model model, @RequestParam("fileId") Integer id, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		model.addAttribute("oggetto", oggetto);
		return "file/editDocs";
	    } else {
		throw new RuntimeException("File senza nome. (id=" + id + ")");
	    }
	} else {
	    throw new RuntimeException("File non trovato. (id=" + id + ")");
	}
    }

    @RequestMapping
    public String editDocApplication(Model model, @RequestParam("fileId") Integer id, @RequestParam("idComuneOggetto") String idComuneOggetto,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		String uri = getCodeBase(request);
		model.addAttribute("uri", uri);
		model.addAttribute("fileId", id);
		model.addAttribute("idComuneOggetto", idComuneOggetto);
		model.addAttribute("token", ORMHelper.getToken());
		return "file/editDocsJnlp";
	    } else {
		throw new RuntimeException("File senza nome. (id=" + id + ")");
	    }
	} else {
	    throw new RuntimeException("File non trovato. (id=" + id + ")");
	}
    }

    public String getCodeBase(HttpServletRequest request) {

	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_EDIT_DOCS_APPLICATION)) {
	    Verticalizzazioniparametri url = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_EDIT_DOCS_APPLICATION, WebConstants.VERTICALIZZAZIONE_EDIT_DOCS_APPLICATION_URL_CODEBASE_BO);
	    if (url != null && StringUtils.isNotBlank(url.getValore())) {
		return url.getValore().trim();
	    }
	}
	String uri = request.getRequestURL().toString();
	uri = uri.substring(0, uri.indexOf("/file/"));
	return uri;
    }

    @RequestMapping
    public String downloadSignedFileClearContent(Model model, @RequestParam("fileId") Integer id,
	    @RequestParam("idComuneOggetto") String idComuneOggetto, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Oggetti oggetto = oggettiService.findById(new PkId(idComuneOggetto, id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		DataHandler dh = Utilities.bytesToDataHandler(oggetto.getOggetto());
		WsValidationReport report = DSSWSClient.validateDocument(dh, filename, true);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		filename = report.getContent().getName();
		// filename = filename.substring(0, filename.lastIndexOf('.'));
		// BOCCI 2012-08-13
		// Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
		// When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
		// While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
		// According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
		// most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
		// Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		// response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("Content-transfer-encoding", "binary");
		String cType = contenttypesService.findMimeTypeByFileName(filename);
		response.setContentType(cType);
		byte[] contentBinary = Utilities.dataHandlerToBytes(report.getContent().getBinary());
		response.setContentLength(contentBinary.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(contentBinary);
		out.flush();
		return null;
	    } else {
		throw new RuntimeException("File senza nome. (id=" + id + ")");
	    }
	} else {
	    throw new RuntimeException("File non trovato. (id=" + id + ")");
	}
    }

    @RequestMapping
    public String ajaxViewOggetto(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String fileId = request.getParameter("fileId");
	String idComuneOggetto = request.getParameter("idComuneOggetto");
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	if (!(fileId == null || fileId.equals(""))) {
	    Integer id = new Integer(fileId);
	    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(idComuneOggetto, id));
	    String downloadFileLink = getDownloadLink(oggetto);
	    model.addAttribute("downloadFileLink", downloadFileLink);
	    model.addAttribute("filename", oggetto.getNomefile());
	    model.addAttribute("size", oggetto.getDimensioneFileLeggibile());
	    model.addAttribute("id", id);
	    model.addAttribute("idComuneOggetto", idComuneOggetto);
	    addToModelMetadatiFile(idComuneOggetto, id, model, userlogged);
	    Oggettiinfo o = oggettiinfoService.findById(oggetto.getId());
	    model.addAttribute("libreria_oggetti", Boolean.valueOf(o != null));
	}
	Verticalizzazioni vert = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_MODULO_ACQUISIZIONE_TWAIN);
	boolean seScanner = false;
	if (null != vert) {
	    seScanner = (vert.getAttivo().intValue() == 1) ? true : false;
	}
	model.addAttribute("vertScanner", seScanner);
	model.addAttribute("userlogged", userlogged);
	String stcIdDocumento = request.getParameter("stcIddocumento");
	String stcIdAllegato = request.getParameter("stcIdallegato");
	boolean isDocumentoSTC = StringUtils.isNotBlank(stcIdDocumento) || StringUtils.isNotBlank(stcIdAllegato);
	model.addAttribute("isDocumentoSTC", Boolean.valueOf(isDocumentoSTC));
	return "file/viewOggettoInfo";
    }

    private void addToModelMetadatiFile(String idComuneOggetto, Integer codiceOggetto, Model model, Responsabili userLogged) {

	OggettiMetadatiId idom = new OggettiMetadatiId(idComuneOggetto, codiceOggetto, WebConstants.OGGETTI_FILE_LOCKED_BY);
	OggettiMetadati omdt = oggettiMetadatiService.findById(idom);
	Boolean fileBloccato = Boolean.FALSE;
	Boolean operatoreLoggatoBloccaFile = Boolean.FALSE;
	if (omdt != null) {
	    fileBloccato = Boolean.TRUE;
	    String codiceRespCheBloccaIlFile = StringUtils.defaultString(omdt.getValore()).trim();
	    String codiceOpeLoggato = userLogged.getId().getCodice().toString();
	    if (codiceOpeLoggato.equalsIgnoreCase(codiceRespCheBloccaIlFile)) {
		operatoreLoggatoBloccaFile = Boolean.TRUE;
	    }
	}
	model.addAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + codiceOggetto.intValue(), fileBloccato);
	model.addAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_DA_UTENTE_LOGGATO_MODEL_ATTRIBUTE + codiceOggetto.intValue(),
		operatoreLoggatoBloccaFile);
	String uid = "non trovato";
	if (BooleanUtils.toBoolean(getCurrentlyAuthenticatedUserDetails().getReadonly())) {
	    OggettiMetadati omdUID = oggettiMetadatiService.findById(new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_UID));
	    if (omdUID != null && omdUID.getId() != null && omdUID.getId().getCodiceoggetto() != null && StringUtils.isNotBlank(omdUID.getValore())) {
		uid = omdUID.getValore();
	    }
	} else {
	    uid = oggettiService.insertOrGetUID(codiceOggetto, idComuneOggetto);
	}
	if (StringUtils.isNotBlank(uid)) {
	    String link = "/public_json/download/" + ORMHelper.getIdcomuneAlias() + "/TT/" + uid;
	    model.addAttribute("linkServiziRest", link);
	}
    }

    @RequestMapping
    public String ajaxViewOggettoList(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String fileId = request.getParameter("fileId");
	String idComuneOggetto = request.getParameter("idComuneOggetto");
	if (!(fileId == null || fileId.equals(""))) {
	    Integer id = new Integer(fileId);
	    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(idComuneOggetto, id));
	    String downloadFileLink = getDownloadLink(oggetto);
	    model.addAttribute("downloadFileLink", downloadFileLink);
	    model.addAttribute("filename", oggetto.getNomefile());
	    model.addAttribute("size", oggetto.getDimensioneFileLeggibile());
	    model.addAttribute("id", id);
	    model.addAttribute("idComuneOggetto", idComuneOggetto);
	    String mostralabel = request.getParameter("mostralabel");
	    String readonly = request.getParameter("readonly");
	    String estensioneFile = Utilities.getFileExtension(oggetto.getNomefile());
	    String tipoFileAttr = "";
	    if (estensioneFile.equalsIgnoreCase("pdf")) {
		tipoFileAttr = "PDF";
	    }
	    if (estensioneFile.equalsIgnoreCase("rtf") || estensioneFile.equalsIgnoreCase("odt") || estensioneFile.equalsIgnoreCase("doc")
		    || estensioneFile.equalsIgnoreCase("docx")) {
		tipoFileAttr = "WORD";
	    }
	    model.addAttribute("tipoFileAttr", tipoFileAttr);
	    model.addAttribute("mostralabel", mostralabel);
	    model.addAttribute("readonly", readonly);
	    addToModelMetadatiFile(idComuneOggetto, id, model, getCurrentlyAuthenticatedUserDetails());
	}
	return "file/viewOggettoInfoList";
    }

    @RequestMapping
    public String ajaxShowUpload(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<Tipologieoggetto> listaTipologie = tipologieoggettoService.findAll(null, null);
	model.addAttribute("listaTipologie", listaTipologie);
	Verticalizzazioni vert = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_MODULO_ACQUISIZIONE_TWAIN);
	boolean seScanner = false;
	if (null != vert) {
	    seScanner = (vert.getAttivo().intValue() == 1) ? true : false;
	}
	model.addAttribute("vertScanner", seScanner);
	return "file/showUpload";
    }

    @RequestMapping
    public String ajaxUploadCall(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile mpFile)
	    throws IOException {

	String addToLibreria = (String) request.getParameter("libreriachk");
	if (log.isDebugEnabled()) {
	    log.debug("Aggiungi il file a libreria oggetti valore parametro: {}", addToLibreria);
	}
	String codiceIstanza = (String) request.getParameter("codiceIstanza");
	if (log.isDebugEnabled()) {
	    log.debug("codiceIstanza: {}", codiceIstanza);
	}
	byte[] fileByteArray = mpFile.getBytes();
	Oggetti oggetto = new Oggetti();
	oggetto.setNomefile(mpFile.getOriginalFilename());
	oggetto.setOggetto(fileByteArray);
	addToLibreria = addToLibreria == null ? "false" : addToLibreria;
	try {
	    oggettiService.insert(oggetto);
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes("UTF-8"));
	    return null;
	}
	boolean verticalizzazioniFS = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM);
	if (!verticalizzazioniFS) {
	    boolean isNomenclatura = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STANDARD_NOMENCLATURA_OGGETTI);
	    if (isNomenclatura) {
		String nomeFile = ORMHelper.getIdcomune();
		if (StringUtils.isNotBlank(codiceIstanza)) {
		    nomeFile = nomeFile + "_I" + codiceIstanza;
		}
		nomeFile = nomeFile + "_O" + oggetto.getId().getCodice() + "_" + oggetto.getNomefile();
		oggetto.setNomefile(nomeFile);
		oggettiService.update(oggetto);
	    }
	}
	if ((addToLibreria.equalsIgnoreCase("true"))) {
	    String descrizione = request.getParameter("descrizione");
	    if (log.isDebugEnabled()) {
		log.debug("Aggiungi il file a libreria oggetti descrizione: {}", descrizione);
	    }
	    String tipologiaOggetto = request.getParameter("tipologiaOggetto");
	    if (log.isDebugEnabled()) {
		log.debug("Aggiungi il file a libreria oggetti tipologia: {}", tipologiaOggetto);
	    }
	    PkId tipologiePkId = new PkId(new Integer(tipologiaOggetto));
	    Tipologieoggetto tipologieoggetto = tipologieoggettoService.findById(tipologiePkId);
	    Oggettiinfo oggettiinfo = new Oggettiinfo();
	    oggettiinfo.getId().setCodice(oggetto.getId().getCodice());
	    oggettiinfo.setDescrizione(descrizione);
	    oggettiinfo.setTipologieoggetto(tipologieoggetto);
	    oggettiinfo.setNomefile(oggetto.getNomefile());
	    oggettiinfoService.insert(oggettiinfo);
	}
	return "redirect:ajaxUploadResponse.htm?fileId=" + oggetto.getId().getCodice();
    }

    @RequestMapping
    public String ajaxUploadResponse(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id)
	    throws IOException {

	model.addAttribute("id", id);
	return "file/uploadFileResponse";
    }

    @RequestMapping
    public void ajaxUnlockFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam("codiceoggetto") Integer[] codiceoggetto)
	    throws IOException {

	Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	oggettiService.updateFilesRimuoviBloccoModifica(codiceoggetto, codiceResponsabile);
    }

    @Override
    protected void fixMergeEntityProperty(FileUpload entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FileUpload entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
