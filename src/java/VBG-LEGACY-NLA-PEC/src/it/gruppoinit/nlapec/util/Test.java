package it.gruppoinit.nlapec.util;

import it.gruppoinit.nlapec.service.stc.NlaWebService;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

	//	PECMessageInfos pecMessageInfos = new PECMessageInfos();
	//	pecMessageInfos.setCertificate(getDatiCertXMLTest());
	//	PecDaticert pecDaticert = pecMessageInfos.getDatiCertDaXML();
	//	System.out.println("");
	//display(new File("C:\\Users\\mirkoc\\Desktop\\PEC Firenze\\CONSEGNA. Accreditamento_40915EF32.eml.msg"));
	File f = new File("C:\\tmp\\postacert.eml");
	display(f);
	//test();
    }

    public static Document getDatiCertXMLTest() throws Exception {

	Document doc = null;
	FileInputStream fis = new FileInputStream("C:\\tmp\\daticert-4.xml");
	//		BufferedInputStream bis = new BufferedInputStream(fis);
	//
	//		ByteArrayOutputStream buf = new ByteArrayOutputStream();
	//		int result = bis.read();
	//		while(result != -1) {
	//		    byte b = (byte)result;
	//		    buf.write(b);
	//		    result = bis.read();
	//		}
	//
	//		String r = buf.toString();
	//		InputStream iss = new StringBufferInputStream(r);
	DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	dbfac.setNamespaceAware(false);
	dbfac.setValidating(false);
	dbfac.setFeature("http://xml.org/sax/features/namespaces", false);
	dbfac.setFeature("http://xml.org/sax/features/validation", false);
	dbfac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	dbfac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	System.out.println(docBuilder.isValidating());
	doc = docBuilder.parse(fis, "");
	fis.close();
	return doc;
    }

    public static void display(File emlFile) throws Exception {

	Properties props = System.getProperties();
	props.put("mail.host", "smtp.dummydomain.com");
	props.put("mail.transport.protocol", "smtp");
	String tmpPathFile = "c:\\tmp\\1372085669918\\";
	Session mailSession = Session.getDefaultInstance(props, null);
	InputStream source = new FileInputStream(emlFile);
	MimeMessage message = new MimeMessage(mailSession, source);
	//ArrayList lista = AllegatiUtil.saveAttachment(message, "c:\\tmp\\1372085669918\\");
	ArrayList listaFile = new ArrayList<String>();
	try {
	    Multipart multipart = (Multipart) message.getContent();
	    // System.out.println(multipart.getCount());
	    for (int i = 0; i < multipart.getCount(); i++) {
		BodyPart bodyPart = multipart.getBodyPart(i);
		if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && !StringUtils.isNotBlank(bodyPart.getFileName())) {
		    continue; // dealing with attachments only
		}
		InputStream is = bodyPart.getInputStream();
		File f = new File(tmpPathFile + bodyPart.getFileName());
		FileOutputStream fos = new FileOutputStream(f);
		byte[] buf = new byte[4096];
		int bytesRead;
		while ((bytesRead = is.read(buf)) != -1) {
		    fos.write(buf, 0, bytesRead);
		}
		fos.close();
		listaFile.add(f);
	    }
	} catch (Exception e) {
	    System.out.println("Errore nel recupero degli allegati : " + e.getMessage());
	}
	// return listaFile;
	//System.out.println("Subject : " + message.getSubject());
	//	System.out.println("From : " + message.getFrom()[0]);
	System.out.println("--------------");
	//	System.out.println("Body : " + message.getContent());
    }

    public static void test() throws Exception {

	JAXBContext context = JAXBContext.newInstance(InserimentoPraticaNLARequest.class);
	Unmarshaller um = context.createUnmarshaller();
	InserimentoPraticaNLARequest request = (InserimentoPraticaNLARequest) um.unmarshal(new File(
		"c:\\tmp\\domanda00338140395-H199540-5897040_1.xml"));
	NlaWebService nlaWebService = new NlaWebService();
	nlaWebService.inserimentoPratica(request);
	System.out.println("END.");
    }
}
