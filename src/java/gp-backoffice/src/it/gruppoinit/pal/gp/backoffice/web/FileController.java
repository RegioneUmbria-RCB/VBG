package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
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
import it.gruppoinit.pal.gp.core.domain.helper.ProcessedItemNewDSS;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione.FirmeRemoteService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiStoricoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettoStoricoBean;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.rest.client.DSSRestClient;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.FileOriginaleDSSBean;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.ValidationResultDTO;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;
import it.gruppoinit.pal.gp.core.service.TipologieoggettoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Controller
public class FileController extends BaseController<FileUpload> {

    private static final String FILE_NON_TROVATO_ID = "File non trovato. (id=";
    private static final String FILE_SENZA_NOME_ID = "File senza nome. (id=";
    private static final String MAX_AGE_0 = "max-age=0";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String PUBLIC = "public";
    private static final String PRAGMA = "Pragma";
    private static final String BINARY = "binary";
    private static final String CONTENT_TRANSFER_ENCODING = "Content-transfer-encoding";
    private static final String ATTACHMENT_FILENAME = "attachment; filename=\"";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String FILENAME = "filename";
    private Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiStoricoService oggettiStoricoService;
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
    @Autowired
    private FirmeRemoteService firmeRemoteService;

    @RequestMapping
    public String ajaxSearch() {

	return "file/uploadFile";
    }

    private String getDownloadLink(Oggetti oggettiLazy) {

	String filename = oggettiLazy.getNomefile();
	if (StringUtils.isNotBlank(filename)) {
	    try {
		String filePath = oggettiService.getSharedFileLink(oggettiLazy.getId().getCodice());
		if (StringUtils.isNotBlank(filePath)) {
		    return "file:///" + filePath;
		}
	    } catch (Exception e) {
		log.error("Errore nel recupero del file: ", e);
	    }
	}
	return "../file/ajaxDownload.htm?fileId=" + oggettiLazy.getId().getCodice();
    }

    @RequestMapping
    public void ajaxRipristinaFile(@RequestParam("id") Integer idOggettiStorico, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	this.oggettiStoricoService.ripristina(idOggettiStorico);
    }

    @RequestMapping
    public String ajaxUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile mpFile)
	    throws IOException {

	byte[] fileByteArray = mpFile.getBytes();
	Oggetti oggetto = new Oggetti();
	oggetto.setNomefile(mpFile.getOriginalFilename());
	oggetto.setOggetto(fileByteArray);
	oggettiService.insert(oggetto);
	return WebConstants.SPRING_REDIRECT_TO + "ajaxUploadResult.htm?fileId=" + oggetto.getId().getCodice();
    }

    @RequestMapping
    public void ajaxUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id,
	    @RequestParam("fileUpload") MultipartFile mpFile) throws IOException {

	byte[] fileByteArray = mpFile.getBytes();
	if (fileByteArray == null || fileByteArray.length == 0) {
	    throw new IllegalArgumentException("Il file da salvare è vuoto");
	}
	Oggetti oggetto = oggettiService.findById(new PkId(id));
	if (oggetto != null) {
	    oggetto.setOggetto(fileByteArray);
	    oggettiService.update(oggetto);
	}
    }

    @RequestMapping
    public String ajaxUploadResult(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id) {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(id));
	model.addAttribute(FILENAME, oggetto.getNomefile());
	model.addAttribute("size", oggetto.getDimensioneFileLeggibile());
	model.addAttribute("id", id);
	return "file/uploadFileResult";
    }

    @RequestMapping
    public String ajaxDelete(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id) {

	Oggetti oggetto = new Oggetti(new PkId(id));
	oggettiService.delete(oggetto);
	return WebConstants.SPRING_REDIRECT_TO + "ajaxSearch.htm";
    }

    @RequestMapping
    public void ajaxModificaDocumento(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id)
	    throws IOException {

	ajaxDownload(request, response, id, true);
    }

    @RequestMapping
    public void ajaxAnteprimaPdf(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id) throws IOException {

	Oggetti obj = oggettiService.findById(new PkId(id));
	String contentType = checkExtensionallowed(obj.getNomefile());
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest req = new ConvertBinaryRequest();
	req.setToken(ORMHelper.getToken());
	req.setConversionType(FileConverterWsClient.ConversionType.PDF.name());
	req.setContentType(contentType);
	req.setBinaryData(obj.getOggetto());
	ConvertBinaryResponse resp = null;
	try {
	    resp = fileConverterWsClient.convertBinary(req);
	    byte[] b = resp.getBinaryData();
	    if (b != null) {
		response.setHeader(PRAGMA, PUBLIC);
		response.setHeader(CACHE_CONTROL, MAX_AGE_0);
		if (request.getParameter("no_dialog") == null) {
		    // BOCCI 2012-08-13
		    // Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
		    // When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
		    // While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
		    // According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
		    // most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
		    // Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		    response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + resp.getFileName() + "\"");
		}
		response.setHeader(CONTENT_TRANSFER_ENCODING, BINARY);
		response.setContentType(resp.getMimeType());
		response.setContentLength(b.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(b);
		out.flush();
	    } else {
		log.error("File vuoto. (id={})", id);
		throw new IllegalArgumentException("File vuoto. (id=" + id + ")");
	    }
	} catch (Exception e) {
	    log.error("Errore durante la conversione del file in PDF: ", e);
	    throw new IllegalArgumentException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	}
    }

    private String checkExtensionallowed(String nomeFile) {

	if (StringUtils.isNotBlank(nomeFile)) {
	    if (nomeFile.indexOf(".") >= 0) {
		String extension = nomeFile.substring(nomeFile.lastIndexOf("."));
		extension = extension.replace(".", "").toUpperCase();
		if (extension.equalsIgnoreCase("RTF") || // 
			extension.equalsIgnoreCase("TXT") || // 
			extension.equalsIgnoreCase("DOC") || //
			extension.equalsIgnoreCase("ODT") || //
			extension.equalsIgnoreCase("HTML")) {
		    return extension;
		}
	    }
	    return contenttypesService.findMimeTypeByFileName(nomeFile);
	}
	return "TXT";
    }

    @RequestMapping
    public void ajaxDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id,
	    @RequestParam(value = "modifica", required = false) Boolean isModifica) throws IOException {

	Oggetti oggetto = oggettiService.findById(new PkId(id));
	boolean isModificaDocumento = isModifica == null ? false : isModifica.booleanValue();
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (StringUtils.isNotBlank(filename)) {
		if (!isModificaDocumento) {
		    OggettiMetadatiId idom = new OggettiMetadatiId(id, WebConstants.OGGETTI_FILE_LOCKED_BY);
		    OggettiMetadati omdt = oggettiMetadatiService.findById(idom);
		    if (omdt == null) { // se il file non è bloccato verifico se rilasciare il link file:///.....
			String filePath = oggettiService.getSharedFileLink(id);
			if (StringUtils.isNotBlank(filePath)) {
			    String redirect = "file:///" + filePath;
			    response.sendRedirect(redirect);
			    return;
			}
		    }
		}
		byte[] b = oggetto.getOggetto();
		if (b != null) {
		    response.setHeader(PRAGMA, PUBLIC);
		    response.setHeader(CACHE_CONTROL, MAX_AGE_0);
		    if (request.getParameter("no_dialog") == null) {
			// BOCCI 2012-08-13
			// Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
			// When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
			// While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
			// According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
			// most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
			// Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + filename + "\"");
		    }
		    response.setHeader(CONTENT_TRANSFER_ENCODING, BINARY);
		    String cType = contenttypesService.findMimeTypeByFileName(filename);
		    response.setContentType(cType);
		    response.setContentLength(b.length);
		    ServletOutputStream out = response.getOutputStream();
		    out.write(b);
		    out.flush();
		} else {
		    log.error("File vuoto. (id={})", id);
		    throw new IllegalArgumentException("File vuoto. (id=" + id + ")");
		}
	    } else {
		log.error("File senza nome. (id={})", id);
		throw new IllegalArgumentException(FILE_SENZA_NOME_ID + id + ")");
	    }
	} else {
	    log.error("File non trovato. (id={})", id);
	    throw new IllegalArgumentException(FILE_NON_TROVATO_ID + id + ")");
	}
    }

    @RequestMapping
    public String popupViewSignedFileInfo(Model model, @RequestParam("fileId") Integer id, HttpServletRequest request, HttpServletResponse response) {

	if (log.isDebugEnabled()) {
	    log.debug("popupViewSignedFileInfo# Inizio controllo firma....");
	}
	Oggetti oggetto = oggettiService.findById(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		// Il metodo processa il file invocandoil ws del servizio DSS, e ritorna un oggetto di appoggio
		// ProcessedItem usato per la visulaizzazioni delle informazioni sulla jsp
		if (log.isDebugEnabled()) {
		    log.debug("popupViewSignedFileInfo# Invoco il metoche che processa il file tramite il DSS (codice oggetto:{} [{}])",
			    new Object[] { filename, oggetto.getId().getCodice() });
		}		
		    
		boolean verificaalladata = true;
		    String verificaAllaDataParam = request.getParameter("verificaalladata");
		    if (!StringUtils.isBlank(request.getParameter("verificaalladata"))) {
			verificaalladata = Boolean.parseBoolean(verificaAllaDataParam);
		    }
		    ProcessedItemNewDSS processedItem = processFileNewDSS(oggetto, verificaalladata);
		    List<ProcessedItemNewDSS> processedItems = new ArrayList<ProcessedItemNewDSS>();
		    processedItems.add(processedItem);
		    model.addAttribute("processedItems", processedItems);
		    model.addAttribute("downloadLink", "downloadSignedFileClearContent.htm?fileId=" + oggetto.getId().getCodice());
		    return "file/validazioneNewDSS";
		    
	    } else {
		log.error("File senza nome.Codice oggetto {}", id);
		throw new IllegalArgumentException(FILE_SENZA_NOME_ID + id + ")");
	    }
	} else {
	    log.error("File non trovato.Codice oggetto {}", id);
	    throw new IllegalArgumentException(FILE_NON_TROVATO_ID + id + ")");
	}
    }
    
    private ProcessedItemNewDSS processFileNewDSS(Oggetti file, boolean verificalladata){
	if (log.isDebugEnabled()) {
	    log.debug("processFile#Inizio a precessare il file {}[{}] ", new Object[] { file.getNomefile(), file.getId().getCodice() });
	}
	ProcessedItemNewDSS pi = new ProcessedItemNewDSS(file.getNomefile(), true);
	
	try {
	    pi.setSize(file.getOggetto().length);
	    DataHandler dh = Utilities.bytesToDataHandler(file.getOggetto());
	    ValidationResultDTO result = new DSSRestClient().checkFirmaReport(dh, pi.getFieldName(), verificalladata, false);
	    
	    pi.setValidationResult(result);
	    if (result.hasError()) {
		pi.getErrors().add(result.getValidationErrorMessage());
	    } else if (result.getExtractedContent() != null && result.getExtractedContent().length > 0) {
		pi.setContentFileName(result.getExtractedContentFileName());
		ByteArrayInputStream bais = new ByteArrayInputStream(result.getExtractedContent());
		String writedContent = getMD5Checksum(bais);		
		pi.setContentWritedFileName(writedContent);
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
	StringBuilder result = new StringBuilder();
	for (int i = 0; i < b.length; i++) {
	    result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
	}
	return result.toString();
    }

    private static byte[] createChecksum(InputStream fis) throws NoSuchAlgorithmException, IOException {

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
    public String editDocApplet(Model model, @RequestParam("fileId") Integer id, HttpServletRequest request, HttpServletResponse response) {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		model.addAttribute("oggetto", oggetto);
		return "file/editDocs";
	    } else {
		throw new IllegalArgumentException(FILE_SENZA_NOME_ID + id + ")");
	    }
	} else {
	    throw new IllegalArgumentException(FILE_NON_TROVATO_ID + id + ")");
	}
    }

    @RequestMapping
    public String editDocApplication(Model model, @RequestParam("fileId") Integer id, HttpServletRequest request, HttpServletResponse response) {

	Oggetti oggetto = oggettiService.findByIdLazy(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {
		String uri = getCodeBase(request);
		model.addAttribute("uri", uri);
		model.addAttribute("fileId", id);
		model.addAttribute("token", ORMHelper.getToken());
		return "file/editDocsJnlp";
	    } else {
		throw new IllegalArgumentException(FILE_SENZA_NOME_ID + id + ")");
	    }
	} else {
	    throw new IllegalArgumentException(FILE_NON_TROVATO_ID + id + ")");
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
    public String downloadSignedFileClearContent(Model model, @RequestParam("fileId") Integer id, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Oggetti oggetto = oggettiService.findById(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (filename != null && !filename.equals("")) {		    
		    byte[] contentBinary;		    			
			if(!filename.toLowerCase().endsWith(".p7m")){
			    log.debug("Il file in input non risulta essere un p7m, dunque si restituisce il file in oggetto {}", filename);
			    contentBinary = oggetto.getOggetto();
			    response.setContentType(contenttypesService.findMimeTypeByFileName(filename));
			}else{
			    DataHandler dh = Utilities.bytesToDataHandler(oggetto.getOggetto());
			    FileOriginaleDSSBean bean = new DSSRestClient().checkFirmaScaricaFileNonFirmato(dh, filename);
			    filename = bean.getFileName();
			    response.setContentType(bean.getContentType());
			    contentBinary = bean.getContent();
			}
									
//			response.setHeader(PRAGMA, PUBLIC);
//			response.setHeader(CACHE_CONTROL, MAX_AGE_0);			
			response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + filename + "\"");
			response.setHeader(CONTENT_TRANSFER_ENCODING, BINARY);					    		    		    
		    response.setContentLength(contentBinary.length);
		    ServletOutputStream out = response.getOutputStream();
		    out.write(contentBinary);
		    out.flush();
		    return null;		
	    } else {
		throw new IllegalArgumentException(FILE_SENZA_NOME_ID + id + ")");
	    }
	} else {
	    throw new IllegalArgumentException(FILE_NON_TROVATO_ID + id + ")");
	}
    }

    @RequestMapping
    public void getOggetto(Model model, @RequestParam(value = "fileId", required = true) Integer id,
	    @RequestParam(value = "download", required = false) Boolean isDownload, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Oggetti oggetto = oggettiService.findById(new PkId(id));
	if (oggetto != null) {
	    String filename = oggetto.getNomefile();
	    if (StringUtils.isNotEmpty(filename)) {
		try {
		    
		    byte[] contentBinary;
		    if (!filename.toLowerCase().endsWith(".p7m")) {
			log.debug("Il file in input non risulta essere un p7m, dunque si restituisce il file in oggetto {}", filename);
			contentBinary = oggetto.getOggetto();
			response.setContentType(contenttypesService.findMimeTypeByFileName(filename));
		    } else {
			DataHandler dh = Utilities.bytesToDataHandler(oggetto.getOggetto());
			FileOriginaleDSSBean bean = new DSSRestClient().checkFirmaScaricaFileNonFirmato(dh, filename);
			filename = bean.getFileName();
			response.setContentType(bean.getContentType());
			contentBinary = bean.getContent();
		    }
		    
		    response.setHeader(PRAGMA, PUBLIC);
		    response.setHeader(CACHE_CONTROL, MAX_AGE_0);
		    // BOCCI 2012-08-13
		    // Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
		    // When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
		    // While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
		    // According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
		    // most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
		    // Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		    response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + filename + "\"");
		    response.setHeader(CONTENT_TRANSFER_ENCODING, filename);
		    String cType = contenttypesService.findMimeTypeByFileName(filename);
		    response.setContentType(cType);
		    response.setContentLength(contentBinary.length);
		    ServletOutputStream out = response.getOutputStream();
		    out.write(contentBinary);
		    out.flush();
		} catch (Exception e) {
		    log.error("getOggetto: ", e);
		}
	    } else {
		throw new IllegalArgumentException(FILE_SENZA_NOME_ID + id + ")");
	    }
	} else {
	    throw new IllegalArgumentException(FILE_NON_TROVATO_ID + id + ")");
	}
    }

    @RequestMapping
    public String ajaxViewOggetto(Model model, @RequestParam(value = "fileId", required = false) Integer fileId, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	boolean isStoricizzato = false;
	boolean isFirmaRemotaAttiva = this.firmeRemoteService.almenoUnaFirmaRemotaAttiva();
	log.debug("ajaxViewOggetto# Firma remota attiva: {}", isFirmaRemotaAttiva);
	if (fileId != null) {
	    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(fileId));
	    String downloadFileLink = getDownloadLink(oggetto);
	    model.addAttribute("downloadFileLink", downloadFileLink);
	    model.addAttribute(FILENAME, oggetto.getNomefile());
	    model.addAttribute("size", oggetto.getDimensioneFileLeggibile());
	    model.addAttribute("id", fileId);
	    addToModelMetadatiFile(fileId, model, userlogged, oggetto);
	    isStoricizzato = oggettiStoricoService.isStoricizzato(fileId);
	}
	Verticalizzazioni vert = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_MODULO_ACQUISIZIONE_TWAIN);
	boolean seScanner = false;
	if (null != vert) {
	    seScanner = (vert.getAttivo().intValue() == 1);
	}
	model.addAttribute("vertScanner", seScanner);
	model.addAttribute("userlogged", userlogged);
	model.addAttribute("isStoricizzato", isStoricizzato);
	model.addAttribute("isFirmaRemotaAttiva", isFirmaRemotaAttiva);
	String stcIdDocumento = request.getParameter("stcIddocumento");
	String stcIdAllegato = request.getParameter("stcIdallegato");
	boolean isDocumentoSTC = StringUtils.isNotBlank(stcIdDocumento) || StringUtils.isNotBlank(stcIdAllegato);
	model.addAttribute("isDocumentoSTC", Boolean.valueOf(isDocumentoSTC));
	return "file/viewOggettoInfo";
    }

    private void addToModelMetadatiFile(Integer codiceOggetto, Model model, Responsabili userLogged, Oggetti oggettoLazy) {

	OggettiMetadatiId idom = new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_LOCKED_BY);
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
	    uid = oggettiService.insertOrGetUID(codiceOggetto);
	}
	if (StringUtils.isNotBlank(uid)) {
	    String link = "/public_json/download/" + ORMHelper.getIdcomuneAlias() + "/TT/" + uid;
	    model.addAttribute("linkServiziRest", link);
	}
	Boolean documentoFirmato = checkLegacyDocFirmato(oggettoLazy.getNomefile());
	omdt = oggettiMetadatiService.findById(new OggettiMetadatiId(codiceOggetto, OggettiMetadatiService.CHIAVE_FIRMA_DIGITALE_PRESENTE));
	if (omdt != null) {
	    documentoFirmato = Boolean.valueOf(
		    StringUtils.defaultString(omdt.getValore(), "-1").equalsIgnoreCase(OggettiMetadatiService.VALORE_FIRMA_DIGITALE_PRESENTE_TRUE));
	}
	model.addAttribute("documentoFirmato", documentoFirmato);
	OggettiMetadati datoSensibile = oggettiMetadatiService.findById(new OggettiMetadatiId(codiceOggetto, OggettiMetadatiService.DATO_SENSIBILE));
	Boolean datoSensibileVal = Boolean.FALSE;
	if (datoSensibile != null && StringUtils.isNotBlank(datoSensibile.getValore())) {
	    datoSensibileVal = StringUtils.defaultString(datoSensibile.getValore(), "0").equals("1");
	}
	model.addAttribute(OggettiMetadatiService.DATO_SENSIBILE, datoSensibileVal);
    }

    @RequestMapping
    public void ajaxModificaMetadato(HttpServletRequest request, HttpServletResponse response, @RequestParam("codiceoggetto") Integer codiceoggetto,
	    @RequestParam("metadato") String metadato, @RequestParam("valore") String valore) {

	OggettiMetadatiId id = new OggettiMetadatiId(codiceoggetto, metadato);
	OggettiMetadati md = oggettiMetadatiService.findById(id);
	if (md == null) {
	    md = new OggettiMetadati();
	    md.setId(id);
	    md.setValore(valore);
	    oggettiMetadatiService.insert(md);
	} else {
	    md.setValore(valore);
	    oggettiMetadatiService.update(md);
	}
	LoggerModificheIstanze.log(oggettiMetadatiService.getMessaggioModificaMetadato(codiceoggetto, metadato, valore));
    }

    private Boolean checkLegacyDocFirmato(String nomefile) {

	return StringUtils.defaultString(nomefile).toLowerCase().endsWith(".p7m")
		|| StringUtils.defaultString(nomefile).toLowerCase().endsWith(".pdf")
		|| StringUtils.defaultString(nomefile).toLowerCase().endsWith(".xml")
		|| StringUtils.defaultString(nomefile).toLowerCase().endsWith(".tsd")
		|| StringUtils.defaultString(nomefile).toLowerCase().endsWith(".m7m");
    }

    @RequestMapping
    public String ajaxViewOggettoList(Model model, @RequestParam(value = "fileId", required = false) Integer fileId, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (fileId != null) {
	    boolean isFirmaRemotaAttiva = this.firmeRemoteService.almenoUnaFirmaRemotaAttiva();
	    log.debug("ajaxViewOggettoList# Firma remota attiva: {}", isFirmaRemotaAttiva);
	    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(fileId));
	    String downloadFileLink = getDownloadLink(oggetto);
	    model.addAttribute("downloadFileLink", downloadFileLink);
	    model.addAttribute(FILENAME, oggetto.getNomefile());
	    model.addAttribute("size", oggetto.getDimensioneFileLeggibile());
	    model.addAttribute("id", fileId);
	    String mostralabel = request.getParameter("mostralabel");
	    String readonly = request.getParameter("readonly");
	    boolean mostraStorico = Boolean.parseBoolean(StringUtils.defaultString(request.getParameter("mostrastorico"), "true"));
	    String estensioneFile = Utilities.getFileExtension(oggetto.getNomefile());
	    String tipoFileAttr = "";
	    if (estensioneFile.equalsIgnoreCase("pdf")) {
		tipoFileAttr = "PDF";
	    }
	    if (estensioneFile.equalsIgnoreCase("rtf") || estensioneFile.equalsIgnoreCase("odt") || estensioneFile.equalsIgnoreCase("doc")
		    || estensioneFile.equalsIgnoreCase("docx")) {
		tipoFileAttr = "WORD";
	    }
	    boolean isStoricizzato = oggettiStoricoService.isStoricizzato(fileId);
	    model.addAttribute("tipoFileAttr", tipoFileAttr);
	    model.addAttribute("mostralabel", mostralabel);
	    model.addAttribute("readonly", readonly);
	    model.addAttribute("isStoricizzato", mostraStorico && isStoricizzato);
	    model.addAttribute("isFirmaRemotaAttiva", isFirmaRemotaAttiva);
	    addToModelMetadatiFile(fileId, model, getCurrentlyAuthenticatedUserDetails(), oggetto);
	}
	return "file/viewOggettoInfoList";
    }

    @RequestMapping
    public String ajaxShowUpload(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<Tipologieoggetto> listaTipologie = tipologieoggettoService.findAll(null, null);
	model.addAttribute("listaTipologie", listaTipologie);
	Verticalizzazioni vert = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_MODULO_ACQUISIZIONE_TWAIN);
	boolean seScanner = false;
	if (null != vert) {
	    seScanner = (vert.getAttivo().intValue() == 1);
	}
	model.addAttribute("vertScanner", seScanner);
	return "file/showUpload";
    }

    @RequestMapping
    public String ajaxUploadCall(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileUpload") MultipartFile mpFile)
	    throws IOException {

	String addToLibreria = request.getParameter("libreriachk");
	log.debug("Aggiungi il file a libreria oggetti valore parametro: {}", addToLibreria);
	String codiceIstanza = request.getParameter("codiceIstanza");
	log.debug("codiceIstanza: {}", codiceIstanza);
	byte[] fileByteArray = mpFile.getBytes();
	Oggetti oggetto = new Oggetti();
	oggetto.setNomefile(mpFile.getOriginalFilename());
	oggetto.setOggetto(fileByteArray);
	addToLibreria = addToLibreria == null ? "false" : addToLibreria;
	try {
	    oggettiService.insert(oggetto);
	} catch (Exception e) {
	    log.error("ajaxUploadCall", e);
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
    public String ajaxUploadResponse(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("fileId") Integer id) {

	model.addAttribute("id", id);
	return "file/uploadFileResponse";
    }

    @RequestMapping
    public void ajaxUnlockFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam("codiceoggetto") Integer[] codiceoggetto) {

	Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	oggettiService.updateFilesRimuoviBloccoModifica(codiceoggetto, codiceResponsabile);
    }

    @RequestMapping
    public String ajaxListaOggettiStorico(@RequestParam("codiceOggetto") Integer codiceOggetto, @RequestParam("idElemento") String idElemento,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	List<OggettoStoricoBean> list = oggettiStoricoService.findStoricoBy(codiceOggetto);
	if (!list.isEmpty()) {
	    list.get(0).setRipristinabile(true);
	}
	model.addAttribute("oggettiStorici", list);
	model.addAttribute("idElemento", idElemento);
	return "file/viewOggettoStoricoList";
    }

    @Override
    protected void fixMergeEntityProperty(FileUpload entity) {

	// niente da gestire
    }

    @Override
    protected void fixRenderEntityProperty(FileUpload entity) {

	// niente da gestire
    }

    @Override
    protected void setPageAttributes(Model model) {

	// niente da gestire
    }
}
