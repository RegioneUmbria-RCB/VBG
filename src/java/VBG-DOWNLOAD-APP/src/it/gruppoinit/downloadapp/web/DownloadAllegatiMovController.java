package it.gruppoinit.downloadapp.web;

import it.gruppoinit.downloadapp.Utilities;
import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadAllegatiMovController extends BaseController {

    @Autowired
    private OggettiWSClient oggettiWSClient;
    @Autowired
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    private String secretKey;

    @RequestMapping
    public void downloadDaQrcode(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String qs = request.getQueryString();
	String[] qsArr = qs.split("/");
	String idcomuneAlias = qsArr[0];
	String software = qsArr[1];
	String uuid = qsArr[2];
	if (uuid.isEmpty() || idcomuneAlias.isEmpty() || software.isEmpty()) {
	    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    // SecurityException e = new SecurityException();
	    response.getWriter().write("Non si dispone dei permessi per visualizzare la risorsa");
	}
	/*verificare che sia presente il token tra i parametri.
	  se non presente allora chiamo authentication gateway preso dai parametri della security per gli operatori di backoffice
	  e imposto la return to alla chiamata che mi hanno fatto
	*/
	if (request.getParameter("Token") == null) {
	    String urlReturnTo = request.getRequestURL().toString();
	    urlReturnTo = urlReturnTo + "?" + qs;
	    urlReturnTo = URLEncoder.encode(urlReturnTo.toString(), "UTF-8");
	    Map<String, String> paramAuthentication = sigeproSecurityWebServiceClient.getParams("AUTHENTICATION_GATEWAY_URL");
	    String redirectUrl = paramAuthentication.get("AUTHENTICATION_GATEWAY_URL") + "?return_to=" + urlReturnTo + "&idcomunealias="
		    + idcomuneAlias + "&contesto=OPE";
	    response.sendRedirect(redirectUrl);
	} else {
	    String token = request.getParameter("Token");
	    try {
		TokenInfoType tokenInfo = sigeproSecurityWebServiceClient.getTokenInfo(token);
		ContestoType contestoType = tokenInfo.getContesto();
		if (contestoType.equals(ContestoType.OPE)) {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		    secretKey = tokenInfo.getClientIp() + sdf.format(new Date()) + uuid;
		    qs = qs + "/" + token;
		    String magic = Utilities.encrypt(secretKey, qs);
		    request.getSession().setAttribute("magic", magic);
		    response.sendRedirect("../downloadallegatimov/linkScarica.htm");
		}
	    } catch (Exception e) {
		throw new SecurityException(e.getMessage());
	    }
	}
    }

    @RequestMapping
    public String linkScarica(Model model, HttpServletRequest request, HttpServletResponse response) {

	return "downloadAllegato/scaricafile";
    }

    @RequestMapping
    public void mostraFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

	String magic = request.getParameter("magic");
	String qs = Utilities.decrypt(secretKey, magic);
	String[] qsArr = qs.split("/");
	if (qsArr.length == 4) {
	    String software = qsArr[1];
	    String uuid = qsArr[2];
	    String token = qsArr[3];
	    sigeproSecurityWebServiceClient.checkToken(token);
	    recuperaOggetto(uuid, token, software, response);
	} else {
	    throw new SecurityException("Errore nel generare la chiave");
	}
    }

    // METODO DEL CONTROLLER CON QUALCHE PROTEZIONE CHE RICHIAMA IL DOWNLOAD DEL FILE
    private void recuperaOggetto(String uid, String token, String software, HttpServletResponse response) throws IOException {

	OggettiFindResponse oggetto = oggettiWSClient.findbyuid(uid, token, software);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType(oggetto.getMimeType());
	response.setHeader("Content-Disposition", "attachment; filename=\"" + oggetto.getFileName() + "\"");
	// response.setContentLength(b.length);
	ServletOutputStream out = response.getOutputStream();
	InputStream is = oggetto.getBinaryData().getInputStream();
	IOUtils.copy(oggetto.getBinaryData().getInputStream(), out);
	try {
	    is.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	try {
	    sigeproSecurityWebServiceClient.logout(token);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping
    public void test(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	recuperaOggetto("767d42fc-101b-472c-8b3a-077399071852", "72eab2a8-c6fe-4cb5-b7af-a4fcb7f1ca55", "CG", response);
    }
}
