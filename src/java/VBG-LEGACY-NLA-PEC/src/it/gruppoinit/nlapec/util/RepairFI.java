package it.gruppoinit.nlapec.util;

import it.gruppoinit.nlapec.daticert.PecDaticert;
import it.gruppoinit.nlapec.daticert.PecDest;
import it.gruppoinit.nlapec.service.movimentimail.MovimentiMailWSClient;
import it.gruppoinit.nlapec.service.sigepro.SigeproService;
import it.gruppoinit.sigepro.schemas.messages.movimentimail.MovimentiMailResponse2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.SharedByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.QPDecoderStream;

public class RepairFI {

    private static Logger log = LoggerFactory.getLogger(RepairFI.class);

    public static void main(String[] args) throws Exception {

	try {
	    processaMessaggiEML("", "", null, null, null, "");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    // Metodo utilizzato una tantum per bonificare problema che si è verificato a firenze.
    @Deprecated
    public static void processaMessaggiEML(String tokenSTC, String sigeproSecurityToken, SigeproService sigeproService, Properties propertiesDB,
	    MovimentiMailWSClient movimentiMailWebServiceClient, String urlWsServiceNotificaMail) throws Exception {

	String path = "C:\\Users\\mirkoc\\Desktop\\Piacenza PEC\\";
	Properties props = System.getProperties();
	Session mailSession = Session.getDefaultInstance(props, null);
	ArrayList<String> listaMSG = getListaMessaggi(path);
	for (String nomeMSG : listaMSG) {
	    System.out.println(" Messaggio : " + nomeMSG);
	    log.info(" Messaggio : " + nomeMSG);
	    InputStream is = new FileInputStream(path + nomeMSG);
	    try {
		String messageID = "";
		String body = null;
		String daticertString = null;
		PecDaticert pecDatiCert = null;
		boolean isProcessed = false;
		MimeMessage message = new MimeMessage(mailSession, is);
		String[] headers = message.getHeader("Message-ID");
		if (headers != null && headers.length == 1) {
		    messageID = headers[0];
		} else {
		    isProcessed = true;
		}
		isProcessed = isProcessed || sigeproService.isProcessed(propertiesDB, "SS", messageID);
		if (!isProcessed) {
		    PECMessage pecMessage = new PECMessage();
		    PECVerifier verifier = new PECVerifier();
		    PECMessageInfos infos = verifier.verifyAnalizePEC(message);
		    PECBodyParts bodyParts = infos.getBodyParts();
		    Set<String> keys = bodyParts.getKeysContentType();
		    for (String key : keys) {
			BodyPart bodyPart = bodyParts.getBodyPart(key);
			String s = bodyPart.getContentType();
			if (s.indexOf("text/plain") != -1) {
			    body = (String) bodyPart.getContent();
			}
			if (s.indexOf("multipart") != -1) {
			    MimeMultipart content = (MimeMultipart) bodyPart.getContent();
			    int count = content.getCount();
			    for (int idx = 0; idx < count; idx++) {
				BodyPart bodyPart2 = content.getBodyPart(idx);
				if (bodyPart2.getContentType().indexOf("text/plain") != -1) {
				    body = (String) bodyPart2.getContent();
				}
			    }
			}
			if (s.indexOf("daticert.xml") != -1) {
			    Object o = bodyPart.getContent();
			    if (bodyPart.getContent() instanceof BASE64DecoderStream) {
				BASE64DecoderStream daticert = (BASE64DecoderStream) bodyPart.getContent();
				byte[] b = new byte[bodyPart.getSize()];
				daticert.read(b);
				daticertString = new String(b);
				pecDatiCert = buildDatiCert(daticertString);
				daticert.close();
			    } else if (bodyPart.getContent() instanceof SharedByteArrayInputStream) {
				SharedByteArrayInputStream sbais = (SharedByteArrayInputStream) bodyPart.getContent();
				byte[] b = new byte[bodyPart.getSize()];
				sbais.read(b);
				daticertString = new String(b);
				pecDatiCert = buildDatiCert(daticertString);
				sbais.close();
			    } else if (bodyPart.getContent() instanceof QPDecoderStream) {
				byte[] b = new byte[bodyPart.getSize()];
				QPDecoderStream qpDecoderStream = (QPDecoderStream) bodyPart.getContent();
				qpDecoderStream.read(b);
				daticertString = new String(b);
				pecDatiCert = buildDatiCert(daticertString);
				qpDecoderStream.close();
			    } else if (bodyPart.getContent() instanceof javax.xml.transform.stream.StreamSource) {
				byte[] b = new byte[bodyPart.getSize()];
				javax.xml.transform.stream.StreamSource streamSource = (javax.xml.transform.stream.StreamSource) bodyPart
					.getContent();
				InputStream streamSourceReader = streamSource.getInputStream();
				streamSourceReader.read(b);
				daticertString = new String(b);
				pecDatiCert = buildDatiCert(daticertString);
				streamSourceReader.close();
			    } else {
				java.io.ByteArrayInputStream bais = (java.io.ByteArrayInputStream) bodyPart.getContent();
				byte[] b = new byte[bodyPart.getSize()];
				bais.read(b);
				daticertString = new String(b);
				pecDatiCert = buildDatiCert(daticertString);
				bais.close();
			    }
			}
		    }
		    if (pecDatiCert != null && pecDatiCert.getErrore() != null && pecDatiCert.getErrore().equalsIgnoreCase("nessuno")) {
			pecMessage.setBody(body);
			pecMessage.setDate(pecDatiCert.getDati().getData());
			InternetAddress iaFrom = new InternetAddress();
			iaFrom.setAddress(pecDatiCert.getIntestazione().getMittente());
			Address[] addressMittente = new Address[1];
			addressMittente[0] = iaFrom;
			pecMessage.setFrom(addressMittente);
			pecMessage.setRifMsgId(pecDatiCert.getDati().getMsgid());
			//pecMessage.setOriginalMessage(null);
			pecMessage.setSubject(pecDatiCert.getIntestazione().getOggetto());
			pecMessage.setTipo(pecDatiCert.getTipo());
			Address[] addressDestinatari = new Address[pecDatiCert.getIntestazione().getDestinatari().size()];
			int indice = 0;
			for (PecDest destinatario : pecDatiCert.getIntestazione().getDestinatari()) {
			    InternetAddress iaTo = new InternetAddress();
			    iaTo.setAddress(destinatario.getEmail());
			    addressDestinatari[indice] = iaTo;
			    indice++;
			}
			pecMessage.setTo(addressDestinatari);
			System.out.println(" --> invoke movimentiMail... RIF-MSG-ID:" + pecMessage.getRifMsgId());
			log.info(" --> invoke movimentiMail... RIF-MSG-ID:" + pecMessage.getRifMsgId());
			//			MovimentiMailResponse response = movimentiMailWebServiceClient.movimentiMail(urlWsServiceNotificaMail, sigeproSecurityToken,
			//				"SS", pecMessage, null, null, null, null);
			MovimentiMailResponse2 response = movimentiMailWebServiceClient.movimentiMail2(urlWsServiceNotificaMail,
				sigeproSecurityToken, "SS", null, pecMessage, null, null, null, null);
			System.out.print("OK : ");
			try {
			    if (response != null && response.getId() != null) {
				System.out.println(response.getId().intValue());
				log.info(String.valueOf(response.getId().intValue()));
			    } else {
				System.out.println("null");
				log.info("OK null");
			    }
			} catch (Exception e) {
			}
			System.out.print(" --> TODO insert into pec_inbox... ");
			log.info(" --> TODO insert into pec_inbox... ");
			sigeproService.setProcessed(propertiesDB, "SS", messageID, pecMessage, "null", null);
			System.out.println("OK");
			log.info("OK");
		    }
		} else {
		    System.out.println(" --> ALERT : This message is already processed!!");
		    log.error(" --> ALERT : This message is already processed!!");
		}
		is.close();
	    } catch (Exception e) {
		e.printStackTrace();
		if (is != null) {
		    try {
			is.close();
		    } catch (Exception ex2) {
		    }
		}
		copyFile(path + nomeMSG, "C:\\Users\\mirkoc\\Desktop\\errorPecFI\\" + nomeMSG);
	    }
	}
	System.out.println("END");
	log.info("Fine processamento PEC");
    }

    private static void copyFile(String nomeFileOrigine, String nomefileDestinazione) {

	try {
	    File f1 = new File(nomeFileOrigine);
	    File f2 = new File(nomefileDestinazione);
	    InputStream in = new FileInputStream(f1);
	    //For Append the file.
	    //  OutputStream out = new FileOutputStream(f2,true);
	    //For Overwrite the file.
	    OutputStream out = new FileOutputStream(f2);
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
		out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	    System.out.println("File copied.");
	    log.info("File copied.");
	} catch (FileNotFoundException ex) {
	    System.out.println(ex.getMessage() + " in the specified directory.");
	    log.error(ex.getMessage() + " in the specified directory.");
	} catch (IOException e) {
	    System.out.println(e.getMessage());
	    log.error(e.getMessage() + " in the specified directory.");
	}
    }

    // movimentiMail(String url, String token, String software, PECMessage pecMessage, String tmpPath,ArrayList<String> listaFileAttachment) 
    public static void processaMessaggiMSG(String tokenSTC, String sigeproSecurityToken, SigeproService sigeproService, Properties propertiesDB,
	    MovimentiMailWSClient movimentiMailWebServiceClient, String urlWsServiceNotificaMail) throws Exception {

	String path = "C:\\Users\\mirkoc\\Desktop\\PEC Firenze2\\";
	Properties props = System.getProperties();
	Session mailSession = Session.getDefaultInstance(props, null);
	ArrayList<String> listaMSG = getListaMessaggi(path);
	for (String nomeMSG : listaMSG) {
	    System.out.println(" Messaggio : " + nomeMSG);
	    InputStream is = new FileInputStream(path + nomeMSG);
	    org.apache.poi.hsmf.MAPIMessage MAPI = new org.apache.poi.hsmf.MAPIMessage(is);
	    String messageID = "";
	    String body = null;
	    String daticertString = null;
	    PecDaticert pecDatiCert = null;
	    String[] headers = MAPI.getHeaders();
	    for (String header : headers) {
		if (header.startsWith("Message-ID")) {
		    messageID = getMessageID(header);
		    System.out.println("Message ID : " + messageID);
		}
	    }
	    boolean isProcessed = sigeproService.isProcessed(propertiesDB, "SS", messageID);
	    if (!isProcessed) {
		PECMessage pecMessage = new PECMessage();
		AttachmentChunks[] attach = MAPI.getAttachmentFiles();
		for (AttachmentChunks attachmentChunks : attach) {
		    if (attachmentChunks.attachFileName.getValue().equalsIgnoreCase("smime.p7m")) {
			//System.out.println(" --> smime.p7m present!");
			InputStream source = new ByteArrayInputStream(attachmentChunks.attachData.getValue());
			MimeMessage message = new MimeMessage(mailSession, source);
			PECVerifier verifier = new PECVerifier();
			PECMessageInfos infos = verifier.verifyAnalizePEC(message);
			PECBodyParts bodyParts = infos.getBodyParts();
			Set<String> keys = bodyParts.getKeysContentType();
			for (String key : keys) {
			    BodyPart bodyPart = bodyParts.getBodyPart(key);
			    String s = bodyPart.getContentType();
			    if (s.indexOf("text/plain") != -1) {
				body = (String) bodyPart.getContent();
			    }
			    if (s.indexOf("multipart") != -1) {
				MimeMultipart content = (MimeMultipart) bodyPart.getContent();
				int count = content.getCount();
				for (int idx = 0; idx < count; idx++) {
				    BodyPart bodyPart2 = content.getBodyPart(idx);
				    if (bodyPart2.getContentType().indexOf("text/plain") != -1) {
					body = (String) bodyPart2.getContent();
				    }
				}
			    }
			    if (s.indexOf("daticert.xml") != -1) {
				if (bodyPart.getContent() instanceof BASE64DecoderStream) {
				    BASE64DecoderStream daticert = (BASE64DecoderStream) bodyPart.getContent();
				    byte[] b = new byte[bodyPart.getSize()];
				    daticert.read(b);
				    daticertString = new String(b);
				    pecDatiCert = buildDatiCert(daticertString);
				    daticert.close();
				} else if (bodyPart.getContent() instanceof SharedByteArrayInputStream) {
				    SharedByteArrayInputStream sbais = (SharedByteArrayInputStream) bodyPart.getContent();
				    byte[] b = new byte[bodyPart.getSize()];
				    sbais.read(b);
				    daticertString = new String(b);
				    pecDatiCert = buildDatiCert(daticertString);
				    sbais.close();
				}
			    }
			}
			if (body == null) {
			    body = "";
			}
			source.close();
		    }
		}
		if (pecDatiCert != null && pecDatiCert.getErrore() != null && pecDatiCert.getErrore().equalsIgnoreCase("nessuno")) {
		    pecMessage.setBody(body);
		    pecMessage.setDate(pecDatiCert.getDati().getData());
		    InternetAddress iaFrom = new InternetAddress();
		    iaFrom.setAddress(pecDatiCert.getIntestazione().getMittente());
		    Address[] addressMittente = new Address[1];
		    addressMittente[0] = iaFrom;
		    pecMessage.setFrom(addressMittente);
		    pecMessage.setRifMsgId(pecDatiCert.getDati().getMsgid());
		    //pecMessage.setOriginalMessage(null);
		    //pecMessage.setRifMsgId(null);
		    pecMessage.setSubject(pecDatiCert.getIntestazione().getOggetto());
		    pecMessage.setTipo(pecDatiCert.getTipo());
		    Address[] addressDestinatari = new Address[pecDatiCert.getIntestazione().getDestinatari().size()];
		    int indice = 0;
		    for (PecDest destinatario : pecDatiCert.getIntestazione().getDestinatari()) {
			InternetAddress iaTo = new InternetAddress();
			iaTo.setAddress(destinatario.getEmail());
			addressDestinatari[indice] = iaTo;
			indice++;
		    }
		    pecMessage.setTo(addressDestinatari);
		    System.out.print(" --> invoke movimentiMail... ");
		    //		    movimentiMailWebServiceClient.movimentiMail(urlWsServiceNotificaMail, sigeproSecurityToken, "SS", pecMessage, null, null, null,
		    //			    null);
		    movimentiMailWebServiceClient.movimentiMail2(urlWsServiceNotificaMail, sigeproSecurityToken, "SS", null, pecMessage, null, null,
			    null, null);
		    System.out.println("OK");
		    System.out.print(" --> TODO insert into pec_inbox... ");
		    sigeproService.setProcessed(propertiesDB, "SS", messageID, pecMessage, "null", null);
		    System.out.println("OK");
		}
	    } else {
		System.out.println(" --> ALERT : This message is already processed!!");
	    }
	    is.close();
	    //System.out.println("#################################################");
	}
	System.out.println("END");
    }

    private static String getMessageID(String header) {

	if (header != null) {
	    String[] tokens = header.split(":");
	    if (tokens != null && tokens.length == 2 && tokens[1] != null) {
		return tokens[1].trim();
	    }
	}
	return null;
    }

    private static ArrayList<String> getListaMessaggi(String path) {

	ArrayList<String> messaggi = new ArrayList<String>();
	File dir = new File(path);
	String[] children = dir.list();
	if (children == null) {
	    // Either dir does not exist or is not a directory
	} else {
	    for (int i = 0; i < children.length; i++) {
		String filename = children[i];
		messaggi.add(filename);
	    }
	}
	return messaggi;
    }

    private static PecDaticert buildDatiCert(String xmlDatiCert) {

	PecDaticert pecDaticert = null;
	InputStream is = null;
	try {
	    final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    final DocumentBuilder parser = builderFactory.newDocumentBuilder();
	    is = new ByteArrayInputStream((xmlDatiCert.trim()).getBytes());
	    final Document domCert = parser.parse(is);
	    PECMessageInfos pecMessageInfos = new PECMessageInfos();
	    pecMessageInfos.setCertificate(domCert);
	    pecDaticert = pecMessageInfos.getDatiCertDaXML();
	    is.close();
	} catch (Exception e) {
	    try {
		if (is != null) {
		    is.close();
		}
	    } catch (Exception ex) {
	    }
	}
	return pecDaticert;
    }
}
