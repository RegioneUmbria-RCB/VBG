package it.gruppoinit.pal.gp.areariservata.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.firma.FileInfo;
import it.gruppoinit.pal.gp.areariservata.ws.client.FirmaWSClient;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;

// @Controller
public class FirmaDigitale2Controller extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(FirmaDigitale2Controller.class);
    @Autowired
    private FirmaWSClient firmaWSClient;
    @Autowired
    private OggettiService oggettiService;
    //    @RequestMapping
    //    public void ajaxMettiAllaFirma(@RequestParam("listaCodiciOggetto") String listaCodiciOggetto, HttpServletResponse resp) {
    //
    //	try {
    //	    log.info("ajaxMettiAllaFirma: listaCodiciOggetto={}", listaCodiciOggetto);
    //	    StringTokenizer st = new StringTokenizer(listaCodiciOggetto, ",");
    //	    String sessionId = "";
    //	    List<FileInfoExtended> fileCaricati = new ArrayList<FileInfoExtended>();
    //	    while (st.hasMoreElements()) {
    //		String codiceOggetto = (String) st.nextElement();
    //		Oggetti ogg = oggettiService.findById(new PkId(Integer.valueOf(codiceOggetto)));
    //		DataHandler dh = Utilities.bytesToDataHandler(ogg.getOggetto());
    //		SetFileToSignRequest setFileToSignRequest = new SetFileToSignRequest();
    //		setFileToSignRequest.setBinaryData(dh);
    //		setFileToSignRequest.setFileName(ogg.getNomefile());
    //		setFileToSignRequest.setSessionId(sessionId);
    //		SetFileToSignResponse setFileToSignResponse = firmaWSClient.setFileToSign(setFileToSignRequest);
    //		// setto il session id per aggiungere i file alla stessa sessione
    //		sessionId = setFileToSignResponse.getSessionId();
    //		FileInfoExtended fileInfo = new FileInfoExtended(sessionId, setFileToSignResponse.getFileId(), ogg.getNomefile(), codiceOggetto);
    //		fileCaricati.add(fileInfo);
    //	    }
    //	    resp.setContentType("text/plain");
    //	    PrintWriter out = resp.getWriter();
    //	    out.println(getJSON(fileCaricati));
    //	    out.close();
    //	} catch (Exception e) {
    //	    log.error("ajaxMettiAllaFirma", e);
    //	    throw new RuntimeException(e.getMessage());
    //	}
    //    }
    //
    //    @RequestMapping
    //    public void ajaxScaricaFileFirmato(@RequestParam("sessionId") String sessionId, @RequestParam("fileId") String fileId,
    //	    @RequestParam("codiceOggetto") String codiceOggetto, HttpServletResponse resp) {
    //
    //	try {
    //	    log.info("ajaxScaricaFileFirmato: sessionId={}, fileId={}, codiceOggetto={}", new Object[] { sessionId, fileId, codiceOggetto });
    //	    GetSignedFileRequest getSignedFileRequest = new GetSignedFileRequest();
    //	    getSignedFileRequest.setSessionId(sessionId);
    //	    getSignedFileRequest.setFileId(fileId);
    //	    GetSignedFileResponse getSignedFileResponse = firmaWSClient.getSignedFile(getSignedFileRequest);
    //	    Oggetti ogg = oggettiService.findById(new PkId(Integer.valueOf(codiceOggetto)));
    //	    byte[] contentBytes = Utilities.dataHandlerToBytes(getSignedFileResponse.getBinaryData());
    //	    ogg.setNomefile(getSignedFileResponse.getFileName());
    //	    ogg.setOggetto(contentBytes);
    //	    oggettiService.update(ogg);
    //	    FileInfoExtended fileInfo = new FileInfoExtended(sessionId, fileId, getSignedFileResponse.getFileName(), codiceOggetto);
    //	    resp.setContentType("text/plain");
    //	    PrintWriter out = resp.getWriter();
    //	    out.println(getJSON(fileInfo));
    //	    out.close();
    //	} catch (Exception e) {
    //	    log.error("ajaxScaricaFileFirmato", e);
    //	    throw new RuntimeException(e.getMessage());
    //	}
    //    }
    //
    //    /**
    //     * {"files":[{"sessionId":"...","fileId":"...","fileName":"...", "codiceOggetto":"..."}]}
    //     * 
    //     * @param list
    //     * @return
    //     */
    //    protected String getJSON(List<FileInfoExtended> list) {
    //
    //	StringBuffer b = new StringBuffer("{\"files\":[");
    //	if (!list.isEmpty()) {
    //	    for (FileInfoExtended fileInfo : list) {
    //		b.append("{\"sessionId\":\"").append(fileInfo.getSessionId());
    //		b.append("\",\"fileId\":\"").append(fileInfo.getFileId());
    //		b.append("\",\"fileName\":\"").append(fileInfo.getFileName());
    //		b.append("\",\"codiceOggetto\":\"").append(fileInfo.getCodiceOggetto());
    //		b.append("\"},");
    //	    }
    //	    b.deleteCharAt(b.length() - 1);
    //	}
    //	b.append("]}");
    //	String json = b.toString();
    //	log.info(json);
    //	return json;
    //    }
    //
    //    /**
    //     * [{"sessionId":"...","fileId":"...","fileName":"...","codiceOggetto":"..."}]
    //     * 
    //     * @param fileInfo
    //     * @return
    //     */
    //    protected String getJSON(FileInfoExtended fileInfo) {
    //
    //	StringBuffer b = new StringBuffer("{");
    //	b.append("\"sessionId\":\"").append(fileInfo.getSessionId());
    //	b.append("\",\"fileId\":\"").append(fileInfo.getFileId());
    //	b.append("\",\"fileName\":\"").append(fileInfo.getFileName());
    //	b.append("\",\"codiceOggetto\":\"").append(fileInfo.getCodiceOggetto());
    //	b.append("\"}");
    //	String json = b.toString();
    //	log.info(json);
    //	return json;
    //    }
}

class FileInfoExtended extends FileInfo {

    private String codiceOggetto;

    public FileInfoExtended(String sessionId, String fileId, String fileName, String codiceOggetto) {

	super(sessionId, fileId, fileName);
	this.codiceOggetto = codiceOggetto;
    }

    public String getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(String codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    @Override
    public String toString() {

	return super.toString() + ", codiceOggetto=" + codiceOggetto;
    }
}
