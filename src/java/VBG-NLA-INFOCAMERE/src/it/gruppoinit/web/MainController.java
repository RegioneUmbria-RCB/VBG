package it.gruppoinit.web;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.InfoSchema;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.Intestazione;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gruppoinit.impresainungiorno.PddServiceSUAPWS;
import it.gruppoinit.service.DeployProperties;
import it.gruppoinit.service.MappingElementICToBOService;
import it.gruppoinit.sigepro.definitions.eventi.EventiWSClient;
import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigepro.definitions.regole.RegoleWSClient;
import it.gruppoinit.sigepro.schemas.messages.regole.ParametroType;
import it.gruppoinit.sigepro.schemas.messages.regole.RegolaRequest;
import it.gruppoinit.sigepro.schemas.messages.regole.RegolaResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.XsdStcVersion;
import it.init.sigepro.rte.types.XsdTypesVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 *
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private OggettiWSClient oggettiWSClient;
    @Autowired
    private SigeproSecurityWSClient sigeproSecurityWSClient;
    @Autowired
    private DeployProperties deployProperties;
    @Autowired
    private EventiWSClient eventiWSClient;
    @Autowired
    private RegoleWSClient regoleWSClient;
    @Autowired
    private PddServiceSUAPWS pddServiceSUAPWS;
    @Autowired
    private MappingElementICToBOService mappingElementICToBOService;

    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {

	Map<String, String> manifestMap = getVersionFromMANIFEST(request);
	request.setAttribute("webapp_version", manifestMap.get("Specification-Version") + " [" + manifestMap.get("Implementation-Version") + "]");
	request.setAttribute("webapp_name", manifestMap.get("Specification-Title"));
	request.setAttribute("stc_xsd_version", XsdStcVersion.V_1_13);
	request.setAttribute("nla_xsd_version", XsdNlaVersion.V_1_13);
	request.setAttribute("types_xsd_version", XsdTypesVersion.V_1_13);
	return new ModelAndView("main");
    }

    @RequestMapping
    public ModelAndView processaFileMDA(@RequestParam("mdaFile") MultipartFile mdaFile, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	File _mdaFile = File.createTempFile("NLA-INFOCAMERE", mdaFile.getOriginalFilename());
	FileOutputStream fos = new FileOutputStream(_mdaFile);
	IOUtils.copy(mdaFile.getInputStream(), fos);
	///
	//File campi = File.createTempFile("NLA-INFOCAMERE", "NomeCampi");
	//		FileOutputStream fos = new FileOutputStream(p7m);
	//		IOUtils.copy(pdfFile.getInputStream(), fos);
	//
	String nomeFile = "StrutturaCampi.txt";
	try {
	    InputStream targetStream = new FileInputStream(_mdaFile);
	    Map<String, String> m = mappingElementICToBOService.normalizzaFileXml(targetStream);
	    StringBuffer bufferString = new StringBuffer();
	    for (Map.Entry<String, String> entry : m.entrySet()) {
		if (!entry.getKey().equals("root")) {
		    bufferString = bufferString.append(entry.getKey()).append("\n");
		} else {
		    nomeFile = entry.getValue() + ".txt";
		}
	    }
	    String fileContent = bufferString.toString();
	    //	    RandomAccessFile stream = new RandomAccessFile(campi, "rw");
	    //	    FileChannel channel = stream.getChannel();
	    byte[] strBytes = fileContent.getBytes();
	    ByteBuffer buffer = ByteBuffer.allocate(fileContent.length());
	    buffer.put(strBytes);
	    buffer.flip();
	    //	    channel.write(buffer);
	    //	    stream.close();
	    //	    channel.close();
	    //	   
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	    response.setHeader("Content-transfer-encoding", "binary");
	    //response.setContentType("text/pdf");
	    response.setContentLength(strBytes.length);
	    ServletOutputStream sos = response.getOutputStream();
	    sos.write(strBytes);
	    sos.flush();
	    model.addAttribute("result", "OK");
	} catch (Exception e) {
	    model.addAttribute("result", e.getMessage());
	}
	///
	return new ModelAndView("main");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////// CONTROLLER DI TEST NON ESPOSTI ///////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/getRegole.htm", method = RequestMethod.GET)
    public ModelAndView getRegole(HttpServletRequest request, HttpServletResponse response) {

	String tokenApp = sigeproSecurityWSClient.loginAPP("E256");
	RegolaRequest regolaRequest = new RegolaRequest();
	regolaRequest.setRecuperaParametri(true);
	regolaRequest.setNomeRegola("NLA_INFOCAMERE");
	regolaRequest.setToken(tokenApp);
	regolaRequest.setSoftware("CO");
	try {
	    RegolaResponse r = regoleWSClient.getRegole(regolaRequest);
	    List<it.gruppoinit.sigepro.schemas.messages.regole.ParametroType> p = r.getListaParametri();
	    String ris = "";
	    for (ParametroType parametroType : p) {
		ris = parametroType.getValore() + "-" + (String) parametroType.getDescrizione();
		System.out.println(ris);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Map<String, String> manifestMap = getVersionFromMANIFEST(request);
	request.setAttribute("webapp_version", manifestMap.get("Specification-Version") + " [" + manifestMap.get("Implementation-Version") + "]");
	request.setAttribute("webapp_name", manifestMap.get("Specification-Title"));
	request.setAttribute("stc_xsd_version", XsdStcVersion.V_1_13);
	request.setAttribute("nla_xsd_version", XsdNlaVersion.V_1_13);
	request.setAttribute("types_xsd_version", XsdTypesVersion.V_1_13);
	return new ModelAndView("main");
    }

    @RequestMapping(value = "/checkws.htm", method = RequestMethod.GET)
    public ModelAndView checkws(HttpServletRequest request, HttpServletResponse response) {

	CooperazioneEnteSUAP cooperazioneEnteSUAP = new CooperazioneEnteSUAP();
	InfoSchema infoSchema = new InfoSchema();
	infoSchema.setData(Utilities.getXMLGregorianCalendar(new Date()));
	infoSchema.setVersione("1.1.0");
	cooperazioneEnteSUAP.setInfoSchema(infoSchema);
	Intestazione intestazione = new Intestazione();
	intestazione.setCodicePratica("TDNGPL81L04M082M-26092019-1018");
	EstremiEnte estremiEnte = new EstremiEnte();
	estremiEnte.setCodiceAmministrazione("ND");
	estremiEnte.setCodiceAoo("ND");
	estremiEnte.setPec("pi@.it");
	estremiEnte.setValue("Backoffice");
	intestazione.setEnteMittente(estremiEnte);
	EstremiSuap estremiSuap = new EstremiSuap();
	estremiSuap.setCodiceAmministrazione("c_d969");
	estremiSuap.setCodiceAoo("GE-SUPRO");
	estremiSuap.setIdentificativoSuap(new BigInteger("227"));
	estremiSuap.setValue("Suap in delega alla CCIAA di GENOVA");
	intestazione.setSuapCompetente(estremiSuap);
	intestazione.setTestoComunicazione("Richiesta integrazione");
	OggettoCooperazione oc = new OggettoCooperazione();
	oc.setValue("Integrazione documentale");
	oc.setTipoCooperazione("SINOLTRO");
	intestazione.setOggettoComunicazione(oc);
	cooperazioneEnteSUAP.setIntestazione(intestazione);
	//pddServiceSUAPWS.inviaEnteSUAP(cooperazioneEnteSUAP);
	Map<String, String> manifestMap = getVersionFromMANIFEST(request);
	request.setAttribute("webapp_version", manifestMap.get("Specification-Version") + " [" + manifestMap.get("Implementation-Version") + "]");
	request.setAttribute("webapp_name", manifestMap.get("Specification-Title"));
	request.setAttribute("stc_xsd_version", XsdStcVersion.V_1_13);
	request.setAttribute("nla_xsd_version", XsdNlaVersion.V_1_13);
	request.setAttribute("types_xsd_version", XsdTypesVersion.V_1_13);
	return new ModelAndView("main");
    }

    private Map<String, String> getVersionFromMANIFEST(HttpServletRequest request) {

	Map<String, String> manifestMap = new HashMap<String, String>();
	ServletContext sContext = request.getSession().getServletContext();
	InputStream is = null;
	Manifest m = null;
	try {
	    is = sContext.getResourceAsStream("/META-INF/MANIFEST.MF");
	    m = new Manifest(is);
	    Attributes attrs = m.getMainAttributes();
	    if (attrs != null) {
		manifestMap.put("Specification-Title", attrs.getValue("Specification-Title"));
		manifestMap.put("Specification-Version", attrs.getValue("Specification-Version"));
		manifestMap.put("Specification-Vendor", attrs.getValue("Specification-Vendor"));
		manifestMap.put("Implementation-Title", attrs.getValue("Implementation-Title"));
		manifestMap.put("Implementation-Version", attrs.getValue("Implementation-Version"));
		manifestMap.put("Implementation-Vendor", attrs.getValue("Implementation-Vendor"));
	    }
	} catch (Exception e) {
	    log.error("getMANIFEST: {}", e.getMessage());
	} finally {
	    if (is != null)
		try {
		    is.close();
		} catch (IOException e) {
		}
	}
	return manifestMap;
    }
}
