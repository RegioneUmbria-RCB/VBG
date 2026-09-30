package it.gruppoinit.pdfutils.web;

import it.gruppoinit.pdfutils.Utilities;
import it.gruppoinit.pdfutils.schemas.messages.DatiPDFType;
import it.gruppoinit.pdfutils.schemas.messages.ObjectFactory;
import it.gruppoinit.pdfutils.schemas.messages.RecuperaDatiDaPDFResponseType;
import it.gruppoinit.pdfutils.service.ConfigurazioneService;
import it.gruppoinit.pdfutils.service.PDFWorkerService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @Autowired
    private PDFWorkerService pdfWorkerService;
    @Autowired
    private SigeproSecurityWebServiceClient securityWebServiceClient;
    @Autowired
    private ConfigurazioneService configurazioneService;

    @RequestMapping
    public String index(Model model) throws Exception {

	return "main/index";
    }

    @RequestMapping
    public void compilaPDF(@RequestParam("alias") String alias, @RequestParam("xmlFile") MultipartFile xmlFile,
	    @RequestParam("pdfFile") MultipartFile pdfFile, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	if (StringUtils.isBlank(alias)) {
	    throw new RuntimeException("alias non specificato");
	}
	request.getSession().setAttribute("alias", alias);
	String token = securityWebServiceClient.loginAPP(alias);
	File pdf = File.createTempFile("PDF-UTILS-", ".pdf");
	FileOutputStream fos = new FileOutputStream(pdf);
	IOUtils.copy(pdfFile.getInputStream(), fos);
	File xmlFileIn = File.createTempFile("PDF-UTILS-", ".xml");
	fos = new FileOutputStream(xmlFileIn);
	IOUtils.copy(xmlFile.getInputStream(), fos);
	pdfWorkerService.precompilaPDF(token, xmlFileIn, new File[] { pdf });
	byte[] b = IOUtils.toByteArray(new FileInputStream(pdf));
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + pdf.getName() + "\"");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType("application/pdf");
	response.setContentLength(b.length);
	ServletOutputStream sos = response.getOutputStream();
	sos.write(b);
	sos.flush();
    }

    @RequestMapping
    public void reversePDF(@RequestParam("alias") String alias, @RequestParam("pdfFile") MultipartFile pdfFile, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	if (StringUtils.isBlank(alias)) {
	    throw new RuntimeException("alias non specificato");
	}
	request.getSession().setAttribute("alias", alias);
	String token = securityWebServiceClient.loginAPP(alias);
	File pdf = File.createTempFile("PDF-UTILS-", pdfFile.getOriginalFilename());
	FileOutputStream fos = new FileOutputStream(pdf);
	IOUtils.copy(pdfFile.getInputStream(), fos);
	List<DatiPDFType> s = null;
	s = pdfWorkerService.pdfToModel(token, pdf);
	ObjectFactory factory = new ObjectFactory();
	RecuperaDatiDaPDFResponseType resp = factory.createRecuperaDatiDaPDFResponseType();
	if (s != null) {
	    resp.getDati().addAll(s);
	    String schedaType = Utilities.marshallDecodificaPDFResponseType(resp);
	    byte[] b = schedaType.getBytes("UTF-8");
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + pdf.getName() + ".xml\"");
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType("text/xml");
	    response.setContentLength(b.length);
	    ServletOutputStream sos = response.getOutputStream();
	    sos.write(b);
	    sos.flush();
	} else {
	    throw new RuntimeException("Non è stato possibile estrarre le informazioni dal PDF");
	}
    }

    @RequestMapping
    public String reloadPDFMapping(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	configurazioneService.reloadConfigurazione();
	model.addAttribute("CONFIGURAZIONE_UPDATED", Boolean.TRUE);
	return "main/index";
    }
}
