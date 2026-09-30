package it.gruppoinit.nlapec.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.sun.mail.imap.IMAPInputStream;
import com.sun.mail.imap.IMAPMessage;

public class AllegatiUtil {

    private static Logger log = LoggerFactory.getLogger(AllegatiUtil.class);
    private static String fileSeparator = System.getProperty("file.separator");

    public static DataHandler bytesToDataHandler(byte[] content) {

	try {
	    DataSource ds = new ByteArrayDataSource(content, "application/octet-stream");
	    return new DataHandler(ds);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public static InputStream getInputStreamModelloRiepilogo(String tmpPath, ArrayList<String> listaFileAttachment) {

	InputStream is = null;
	try {
	    String regexCF = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";
	    String regexPIVA = "^[0-9]{11}";
	    String regexData = "(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])02((19|[2-9]\\d)\\d{2}))|(2902((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))";
	    String regexOra = "(([0]?[0-9]|[1]?[0-9]|[2]?[0-3])([0-5][0-9]))";
	    String regexSeparator = "-";
	    String regexExtensionFileModelloRiepilogo = ".SUAP.xml$";
	    String regexModelloRiepilogoCF = regexCF + regexSeparator + regexData + regexSeparator + regexOra + regexExtensionFileModelloRiepilogo;
	    String regexModelloRiepilogoPIVA = regexPIVA + regexSeparator + regexData + regexSeparator + regexOra
		    + regexExtensionFileModelloRiepilogo;
	    Pattern myPatternModelloRiepilogoCF = Pattern.compile(regexModelloRiepilogoCF, Pattern.CASE_INSENSITIVE);
	    Pattern myPatternModelloRiepilogoPIVA = Pattern.compile(regexModelloRiepilogoPIVA, Pattern.CASE_INSENSITIVE);
	    if (listaFileAttachment != null) {
		boolean trovato = false;
		Iterator<String> it = listaFileAttachment.iterator();
		while (!trovato && it.hasNext()) {
		    String nomeFile = it.next();
		    if (myPatternModelloRiepilogoCF.matcher(nomeFile).matches() || myPatternModelloRiepilogoPIVA.matcher(nomeFile).matches()) {
			trovato = true;
			File f = new File(tmpPath + nomeFile);
			is = new FileInputStream(f);
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return is;
    }

    public static DataHandler getDataHandler(String tmpPath, String nomeFile, BigInteger dimensione) {

	try {
	    File f = new File(tmpPath + nomeFile);
	    FileDataSource fds = new FileDataSource(f);
	    return new DataHandler(fds);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	return null;
    }

    public static byte[] getBinaryData(String tmpPath, String nomeFile, BigInteger dimensione) {

	InputStream is = null;
	byte[] binaryData = new byte[0];
	try {
	    File f = new File(tmpPath + nomeFile);
	    is = new FileInputStream(f);
	    if (dimensione != null) {
		binaryData = new byte[dimensione.intValue()];
		is.read(binaryData);
	    } else {
		int length = (int) f.length();
		binaryData = new byte[length];
		is.read(binaryData);
	    }
	    is.close();
	} catch (Exception e) {
	}
	return binaryData;
    }

    public static String getCodicePratica(ArrayList<String> listaFileAttachment) {

	String nomeFile = null;
	String codicePratica = "";
	try {
	    String regexCF = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";
	    String regexPIVA = "^[0-9]{11}";
	    String regexData = "(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])02((19|[2-9]\\d)\\d{2}))|(2902((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))";
	    String regexOra = "(([0]?[0-9]|[1]?[0-9]|[2]?[0-3])([0-5][0-9]))";
	    String regexSeparator = "-";
	    String regexExtensionFileModelloRiepilogo = ".SUAP.xml$";
	    String regexModelloRiepilogoCF = regexCF + regexSeparator + regexData + regexSeparator + regexOra + regexExtensionFileModelloRiepilogo;
	    String regexModelloRiepilogoPIVA = regexPIVA + regexSeparator + regexData + regexSeparator + regexOra
		    + regexExtensionFileModelloRiepilogo;
	    Pattern myPatternModelloRiepilogoCF = Pattern.compile(regexModelloRiepilogoCF, Pattern.CASE_INSENSITIVE);
	    Pattern myPatternModelloRiepilogoPIVA = Pattern.compile(regexModelloRiepilogoPIVA, Pattern.CASE_INSENSITIVE);
	    boolean trovato = false;
	    Iterator<String> it = listaFileAttachment.iterator();
	    while (!trovato && it.hasNext()) {
		String nome = it.next();
		if (myPatternModelloRiepilogoCF.matcher(nome).matches() || myPatternModelloRiepilogoPIVA.matcher(nome).matches()) {
		    nomeFile = nome;
		    trovato = true;
		}
	    }
	    if (nomeFile != null) {
		codicePratica = nomeFile.substring(0, nomeFile.length() - ".SUAP.XML".length());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return codicePratica;
    }

    //    public static ArrayList<String> saveAttachment(Message message, String tmpPathFile) {
    //
    //	ArrayList<String> listaFile = new ArrayList<String>();
    //	try {
    //	    Multipart multipart = (Multipart) message.getContent();
    //	    for (int i = 0, n = multipart.getCount(); i < n; i++) {
    //		handlePart(multipart.getBodyPart(i), tmpPathFile, listaFile);
    //	    }
    //	} catch (Exception e) {
    //	    log.error("Errore nel recupero degli allegati : " + e.getMessage());
    //	}
    //	return listaFile;
    //    }
    public static ArrayList<String> saveAttachment(Message message, String tmpPathFile) {

	ArrayList<String> listaAllegati = new ArrayList<String>();
	try {
	    log.debug("inizio a salvare gli allegati");
	    Part bodyPart = getBodyMessageRfc882(message);
	    if (bodyPart == null) {
		bodyPart = message;
	    }
	    Object content = bodyPart.getContent();
	    MimeMessage msg = null;
	    if (content instanceof MimeMessage) {
		msg = (MimeMessage) content;
		content = msg.getContent();
	    }
	    if (content instanceof MimeMultipart) {
		log.debug("multipart instance ");
		for (int k = 0; k < ((MimeMultipart) content).getCount(); k++) {		   
		    BodyPart parte = ((MimeMultipart) content).getBodyPart(k);
		    log.debug("processo la part {} ", parte);
		    getAttachment(parte, tmpPathFile, listaAllegati);
		}
	    } else if (content instanceof InputStream) {
		log.debug("inputstream instance ");
		Part parte = (msg != null) ? msg : bodyPart;
		log.debug("processo la part {} ", parte);
		getAttachment(parte, tmpPathFile, listaAllegati);
	    }
	} catch (Exception e) {
	    log.error("Errore nel recupero degli allegati del messaggio PEC : " + e.getMessage(), e);
	    log.error("...provo a trattarlo come un messaggio non PEC");
	    try {
		getAttachment(message, tmpPathFile, listaAllegati);
		log.info((listaAllegati != null && listaAllegati.size() > 0) ? "trovati allegati" + listaAllegati.size() : "non trovati allegati");
	    } catch (Exception ex2) {
		log.error("errore nel recupero degli allegati del messaggio NON PEC");
	    }
	}
	return listaAllegati;
    }

    public static void getAttachment(Part part, String tmpPathFile, ArrayList<String> listaAllegati) throws MessagingException, IOException {

	//String disposition_ = part.getDisposition();
	//if ((disposition_ != null && (disposition_.equalsIgnoreCase(BodyPart.ATTACHMENT))) || (disposition_ == null && part.getFileName() != null)) {
	if (part.getFileName() != null) {
	    log.debug("salvo l'allegato con part name {}", part.getFileName());
	    String nomeFile = MimeUtility.decodeText(part.getFileName());
	    InputStream is = part.getInputStream();
	    File f = new File(tmpPathFile + nomeFile);
	    log.debug("salvo l'allegato {}", f.getCanonicalPath());
	    FileOutputStream fos = new FileOutputStream(f);
	    byte[] buf = new byte[part.getSize()];
	    int bytesRead;
	    while ((bytesRead = is.read(buf)) != -1) {
		fos.write(buf, 0, bytesRead);
	    }
	    fos.close();
	    is.close();
	    log.debug("allegato {} salvato", f.getCanonicalPath());
	    listaAllegati.add(nomeFile);
	} else {
	    Object content = part.getContent();
	    if (content instanceof MimeMessage) {
		MimeMessage msg = (MimeMessage) content;
		content = msg.getContent();
	    }
	    if (content instanceof MimeMultipart) {
		for (int k = 0; k < ((MimeMultipart) content).getCount(); k++) {
		    BodyPart part_ = ((MimeMultipart) content).getBodyPart(k);
		    getAttachment(part_, tmpPathFile, listaAllegati);
		}
	    }
	}
    }

    //    public static void handlePart(Part part, String tmpPathFile, ArrayList<String> listaFile) throws MessagingException, IOException {
    //
    //	String disposition = part.getDisposition();
    //	String contentType = part.getContentType();
    //	if (disposition == null) {
    //	    if (contentType.toLowerCase().indexOf("multipart/mixed;") != -1) {
    //		MimeMultipart mimeMultipart = (MimeMultipart) part.getContent();
    //		for (int i = 0, n = mimeMultipart.getCount(); i < n; i++) {
    //		    Part bodyPart = mimeMultipart.getBodyPart(i);
    //		    if (bodyPart.getContentType().toLowerCase().indexOf("message/rfc822") != -1) { // si tratta della della parte contenente la MAIL PEC vera e propria
    //			Object contentMail = bodyPart.getContent();
    //			if (contentMail instanceof MimeMessage) {
    //			    MimeMessage msg = (MimeMessage) contentMail;
    //			    Object content = msg.getContent();
    //			    if (content instanceof MimeMultipart) {
    //				for (int k = 0; k < ((MimeMultipart) content).getCount(); k++) {
    //				    BodyPart part_ = ((MimeMultipart) content).getBodyPart(k);
    //				    String disposition_ = part_.getDisposition();
    //				    Object content_ = part_.getContent();
    //				    if (disposition_ != null && (disposition_.equalsIgnoreCase(BodyPart.ATTACHMENT))) {
    //					String nomeFile = MimeUtility.decodeText(part_.getFileName());
    //					listaFile.add(nomeFile);
    //					InputStream is = part_.getInputStream();
    //					File f = new File(tmpPathFile + nomeFile);
    //					FileOutputStream fos = new FileOutputStream(f);
    //					byte[] buf = new byte[part_.getSize()];
    //					int bytesRead;
    //					while ((bytesRead = is.read(buf)) != -1) {
    //					    fos.write(buf, 0, bytesRead);
    //					}
    //					fos.close();
    //					is.close();
    //				    } else if (content_ instanceof MimeMultipart) {
    //					for (int z = 0; z < ((MimeMultipart) content_).getCount(); z++) {
    //					    BodyPart part__ = ((MimeMultipart) content_).getBodyPart(z);
    //					    String disposition__ = part__.getDisposition();
    //					    System.out.println("");
    //					}
    //				    }
    //				}
    //			    }
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //    }
    public static String getBodyTextNotifica(Message m) throws Exception {

	String ret = "";
	String textPlain = "";
	String textHTML = "";
	Multipart multipartMessagePEC = (Multipart) m.getContent();
	// Multipart mp = (Multipart)o;
	//    int count3 = mp.getCount();
	//    System.out.println("It has " + count3 + " BodyParts in it**");
	for (int j = 0; j < multipartMessagePEC.getCount(); j++) {
	    // Part are numbered starting at 0
	    BodyPart partMessage = multipartMessagePEC.getBodyPart(j);
	    String mimeType = partMessage.getContentType();
	    //System.out.println( "BodyPart " + (j + 1) + " is of MimeType " + mimeType2);
	    Object partMessageContent = partMessage.getContent();
	    if (partMessageContent instanceof String) {
		//System.out.println("**This is a String BodyPart**");
		ret += ((String) partMessageContent);
		if (partMessage.getContentType().toLowerCase().indexOf("text/plain") != -1) {
		    textPlain += (String) partMessageContent;
		} else if (partMessage.getContentType().toLowerCase().indexOf("text/html") != -1) {
		    textHTML += (String) partMessageContent;
		}
	    } else if (partMessageContent instanceof Multipart) {
		// System.out.print("**This BodyPart is a nested Multipart.  ");
		Multipart mp2 = (Multipart) partMessageContent;
		//int count2 = mp2.getCount();
		//System.out.println("It has " + count2 +" further BodyParts in it**");
		for (int k = 0; k < mp2.getCount(); k++) {
		    BodyPart bp2 = mp2.getBodyPart(k);
		    //String bodyText2 = MimeMessageHandler.getMessageBody(b2);
		    //System.out.println("BODY TEXT 2 --> :"+bodyText2);
		    // System.out.println("Content type : "+bp2.getContentType());
		    Object bp2Content = bp2.getContent();
		    if (bp2Content instanceof String) {
			// System.out.println("**This is a String BodyPart**");
			if (bp2.getContentType().toLowerCase().indexOf("text/plain") != -1) {
			    textPlain += (String) bp2Content;
			} else if (bp2.getContentType().toLowerCase().indexOf("text/html") != -1) {
			    textHTML += (String) bp2Content;
			}
		    } else if (bp2Content instanceof Multipart) {
			Multipart mp3 = (Multipart) bp2Content;
			for (int z = 0; z < mp3.getCount(); z++) {
			    BodyPart bp3 = mp3.getBodyPart(z);
			    Object o = bp3.getContent();
			    if (o instanceof IMAPInputStream) {
				ret = new String(readFully((IMAPInputStream) bp3.getContent()));
			    } else {
				ret += bp3.getContent().toString();
			    }
			    // System.out.println("Content type : "+bp3.getContentType());
			    if (bp3.getContentType().toLowerCase().indexOf("text/plain") != -1) {
				textPlain += ret;
			    } else if (bp3.getContentType().toLowerCase().indexOf("text/html") != -1) {
				textHTML += ret;
			    }
			}
		    }
		}
	    }
	}
	if ("".equalsIgnoreCase(textPlain)) {
	    return textHTML;
	} else {
	    return textPlain;
	}
    }

    public static String getBodyTextPEC(Part message) {

	String ret = "";
	try {
	    Part part = getBodyMessageRfc882(message);
	    boolean trovato = false;
	    MimeMessage mimeMessage = (MimeMessage) part.getContent();
	    Object obj = mimeMessage.getContent();
	    if (obj instanceof String) {
		ret = (String) obj;
	    } else if (obj instanceof MimeMultipart) {
		MimeMultipart mp = (MimeMultipart) obj;
		int index = 0;
		String text = null;
		while (!trovato && index < mp.getCount()) {
		    Part bp = mp.getBodyPart(index);
		    text = AllegatiUtil.getText(bp);
		    if (text != null) {
			ret = text;
			trovato = true;
		    }
		    index++;
		}
	    }
	} catch (Exception e) {
	    ret = "Errore nel recupero del corpo della mail";
	    log.error("Errore nel recupero del corpo della mail. {}", e);
	    try {
		ret = getBodyTextNotifica((Message) message);
	    } catch (Exception e1) {
		log.error("Errore nel recupero del corpo della mail. {}", e);
	    }
	}
	return ret;
    }

    private static Part getBodyMessageRfc882(Part message) throws Exception {

	Part ret = null;
	if (message != null && message.getContentType() != null) {
	    if (message.getContentType().toLowerCase().indexOf("message/rfc822") != -1) {
		log.debug("messaggio è instance of message/rfc822");
		ret = message;
	    } else {
		if (message.getContent() != null) {
		    if (message.getContent() instanceof MimeMultipart) {
			MimeMultipart multipart = (MimeMultipart) message.getContent();
			int indexA = 0;
			boolean trovato = false;
			while (indexA < multipart.getCount() && !trovato) {
			    Part part = multipart.getBodyPart(indexA);
			    // Object contentMail = part.getContent();			    
			    if (part.getContentType().toLowerCase().indexOf("multipart/mixed;") != -1) {
				log.debug("messaggio è multipart/mixed");
				MimeMultipart mimeMultipart = (MimeMultipart) part.getContent();
				int indexB = 0;
				while (indexB < mimeMultipart.getCount() && !trovato) {
				    BodyPart bodyPart = mimeMultipart.getBodyPart(indexB);
				    Object contentMail = bodyPart.getContent();
				    if (contentMail instanceof MimeMessage) {
					ret = getBodyMessageRfc882(bodyPart);
				    }
				    if (ret != null) {
					trovato = true;
				    } else {
					indexB++;
				    }
				}
			    }
			    indexA++;
			}
		    } else if (message.getContent() instanceof IMAPMessage) {
			log.error("Errore nel recupero del corpo della mail : message.getContent() NOT instanceof OF MimeMultipart!!!");
			throw new Exception("Errore nel recupero del corpo della mail : message.getContent() NOT instanceof OF MimeMultipart!!!");
		    }
		}
	    }
	}
	return ret;
    }

    public static String getText(Part p) throws MessagingException, IOException {

	if (p.isMimeType("text/*")) {
	    String s = (String) p.getContent();
	    return s;
	}
	if (p.isMimeType("multipart/alternative")) {
	    // prefer html text over plain text
	    Multipart mp = (Multipart) p.getContent();
	    String text = null;
	    for (int i = 0; i < mp.getCount(); i++) {
		Part bp = mp.getBodyPart(i);
		Object o = bp.getContent();
		if (bp.isMimeType("text/plain")) {
		    if (text == null)
			text = getText(bp);
		    continue;
		} else if (bp.isMimeType("text/html")) {
		    String s = getText(bp);
		    if (s != null)
			return s;
		} else {
		    return getText(bp);
		}
	    }
	    return text;
	} else if (p.isMimeType("multipart/*")) {
	    Multipart mp = (Multipart) p.getContent();
	    for (int i = 0; i < mp.getCount(); i++) {
		String s = getText(mp.getBodyPart(i));
		if (s != null)
		    return s;
	    }
	}
	return null;
    }

    private static byte[] readFully(IMAPInputStream inputStream) throws IOException {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buffer = new byte[1024];
	int length = 0;
	while ((length = inputStream.read(buffer)) != -1) {
	    baos.write(buffer, 0, length);
	}
	return baos.toByteArray();
    }

    public static Document getDatiCertXML(Message message) throws Exception {

	log.debug("getDatiCertXML()...");
	Document doc = null;
	MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
	mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	CommandMap.setDefaultCommandMap(mc);
	try {
	    Multipart multipart = (Multipart) message.getContent();
	    for (int j = 0; j < multipart.getCount(); j++) {
		Part part = multipart.getBodyPart(j);
		String disposition = part.getDisposition();
		String contentType = part.getContentType();
		if (disposition == null) {
		    if (contentType != null && contentType.toLowerCase().indexOf("multipart/mixed;") != -1) {
			MimeMultipart mimeMultipart = (MimeMultipart) part.getContent();
			for (int i = 0; i < mimeMultipart.getCount(); i++) {
			    Part bodyPart = mimeMultipart.getBodyPart(i);
			    if (bodyPart.getFileName() != null && bodyPart.getFileName().toLowerCase().equalsIgnoreCase("daticert.xml")) {
				InputStream is = bodyPart.getInputStream();
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				dbfac.setNamespaceAware(false);
				dbfac.setValidating(false);
				dbfac.setFeature("http://xml.org/sax/features/namespaces", false);
				dbfac.setFeature("http://xml.org/sax/features/validation", false);
				dbfac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				dbfac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
				doc = docBuilder.parse(is);
				is.close();
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw e;
	}
	log.debug("getDatiCertXML end");
	return doc;
    }

    public static String buildTmpPath(String tmpBasePath, String sessionTmpMsg) {

	File f = new File(tmpBasePath);
	if (!f.exists()) {
	    f.mkdir();
	}
	if (tmpBasePath.endsWith(fileSeparator)) {
	    File ff = new File(tmpBasePath + sessionTmpMsg + fileSeparator);
	    ff.mkdir();
	    return tmpBasePath + sessionTmpMsg + fileSeparator;
	} else {
	    File ff = new File(tmpBasePath + fileSeparator + sessionTmpMsg + fileSeparator);
	    ff.mkdir();
	    return tmpBasePath + fileSeparator + sessionTmpMsg + fileSeparator;
	}
    }

    public static boolean deleteEntireDirectory(File dir) {

	File[] fileArray = dir.listFiles();
	try {
	    if (fileArray != null) {
		for (int i = 0; i < fileArray.length; i++) {
		    if (fileArray[i].isFile()) {
			fileArray[i].delete();
		    } else {
			deleteEntireDirectory(fileArray[i]);
			fileArray[i].delete();
		    }
		}
	    }
	    return dir.delete();
	} catch (SecurityException se) {
	    log.warn("Errore durante la cancellazione della directory temporanea : " + se.getMessage());
	    return false;
	}
    }

    public static String[] getHeader(Message message, String headerName) {

	String[] headerValue = null;
	try {
	    headerValue = message.getHeader(headerName);
	    if (headerValue == null || headerName.length() == 0) {
		Multipart multipart = (Multipart) message.getContent();
		if (multipart != null && multipart.getCount() > 0) {
		    for (int i = 0; i < multipart.getCount(); i++) {
			String disposition = multipart.getBodyPart(i).getDisposition();
			String contentType = multipart.getBodyPart(i).getContentType();
			if (disposition == null) {
			    if (contentType.toLowerCase().indexOf("multipart/mixed;") != -1) {
				MimeMultipart mimeMultipart = (MimeMultipart) multipart.getBodyPart(i).getContent();
				for (int j = 0, n = mimeMultipart.getCount(); j < n; j++) {
				    Part bodyPart = mimeMultipart.getBodyPart(j);
				    if (bodyPart.getContentType().toLowerCase().indexOf("message/rfc822") != -1) { // si tratta della della parte contenente la MAIL PEC vera e propria
					Object contentMail = bodyPart.getContent();
					if (contentMail instanceof MimeMessage) {
					    MimeMessage msg = (MimeMessage) contentMail;
					    headerValue = msg.getHeader(headerName);
					    // String val = MimeUtility.decodeText(ref[0]);
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("Errore nel recupero dell'Header " + e.getMessage());
	}
	return headerValue;
    }

    public static String checkIfReplyVBG(String[] referencesHeader, String subject) {

	String idMovimentoMailVBG = null;
	// controllo se nell'header è presente il messagID della PEC inviata da VBG)
	if (referencesHeader != null && referencesHeader.length > 0) {
	    for (int i = 0; i < referencesHeader.length; i++) {
		String val = getVBGReferenceFromHeader(referencesHeader[i]);
		if (val != null) {
		    idMovimentoMailVBG = val;
		}
	    }
	}
	// se non trovato, provo a cercarlo nell'oggetto della mail)
	if (idMovimentoMailVBG == null) {
	    String val = getVBGReferenceFromSubject(subject);
	    if (val != null) {
		idMovimentoMailVBG = val;
	    }
	}
	return idMovimentoMailVBG;
    }

    private static String getVBGReferenceFromSubject(String subject) {

	String ret = null;
	try {
	    if (subject != null) {
		ret = extractVBGId(subject);
	    }
	} catch (Exception e) {
	}
	return ret;
    }

    private static String getVBGReferenceFromHeader(String value) {

	String ret = null;
	try {
	    ret = extractVBGId(value);
	    if (ret == null) {
		ret = extractVBGId(MimeUtility.decodeText(value));
	    }
	} catch (Exception e) {
	}
	return ret;
    }

    private static String extractVBGId(String value) {

	String ret = null;
	//String regex = "[A-Za-z0-9]{3,8}-[A-Z]{2}-[0-9]{4}-[0-9]{5}-[0-9]{13}";
	String regex = "[A-Za-z0-9]{3,8}-[A-Z]{2}-[0-9]{1,6}-[0-9]{1,8}-[0-9]{13}";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(value);
	if (matcher.find()) {
	    ret = matcher.group();
	}
	return ret;
    }
}
